package com.flash.toolbar.omp.common.export;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


import jxl.Cell;
import jxl.CellType;
import jxl.CellView;
import jxl.Hyperlink;
import jxl.Image;
import jxl.LabelCell;
import jxl.Range;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.write.WritableCell;
import jxl.write.WritableHyperlink;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.flash.toolbar.omp.common.io.TempFileProvider;


public class RemoteWritableWorkbook extends WritableWorkbook {

	private WritableWorkbook currentWorkbook;
	private int maxRows = -1;
	private List<File> files = new ArrayList<File>();// element [0] is root xls
	private int currentFileIndex = -1;
	// file
	private WorkbookSettings settings;

	protected RemoteWritableWorkbook(List<File> files, WorkbookSettings settings, int maxRows) {
		this.files.addAll(files);
		this.settings = settings;
		this.maxRows = maxRows;
		this.switchFile(0);
	}

	protected RemoteWritableWorkbook(WritableWorkbook workbook, File file) {
		this(workbook, file, RemoteWorkbook.DEFAULT_MAX_ROWS);
	}

	protected RemoteWritableWorkbook(WritableWorkbook workbook, File file, int maxRows) {
		if (workbook == null) {
			throw new NullPointerException("workbook is null");
		}

		this.currentWorkbook = workbook;
		this.maxRows = maxRows;
		this.files.add(file);
		this.currentFileIndex = 0;

		try {
			Method method = this.currentWorkbook.getClass().getDeclaredMethod("getSettings");
			if (method != null) {
				if (!method.isAccessible())
					method.setAccessible(true);

				settings = (WorkbookSettings) method.invoke(this.currentWorkbook);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public WorkbookSettings getSettings() {
		return settings;
	}

	@Override
	public void addNameArea(final String name, final WritableSheet sheet, final int firstCol, final int firstRow,
			final int lastCol, final int lastRow) {
		this.workInFiles(new FileWorker() {

			public void work(WritableWorkbook currentWorkbook, int fileIndex) {
				currentWorkbook.addNameArea(name, sheet, firstCol, firstRow, lastCol, lastRow);
			}

		});
	}


	@Override
	public void copySheet(final int s, final String name, final int index) {
		this.workInFiles(new FileWorker() {

			public void work(WritableWorkbook currentWorkbook, int fileIndex) {
				currentWorkbook.copySheet(s, name, index);
			}

		});
	}

	@Override
	public void copySheet(final String s, final String name, final int index) {
		this.workInFiles(new FileWorker() {

			public void work(WritableWorkbook currentWorkbook, int fileIndex) {
				currentWorkbook.copySheet(s, name, index);
			}

		});

	}

	@Override
	public WritableSheet createSheet(final String name, final int index) {
		this.workInFiles(new FileWorker() {

			public void work(WritableWorkbook currentWorkbook, int fileIndex) {
				currentWorkbook.createSheet(name, index);
			}

		});

		return new RemoteWritableSheet(this.currentWorkbook.getSheet(index));
	}

	@Override
	public Range[] findByName(final String name) {
		final List<Range> ranges = new ArrayList<Range>();
		this.workInFiles(new FileWorker() {

			public void work(WritableWorkbook currentWorkbook, int fileIndex) {
				Range[] rs = currentWorkbook.findByName(name);
				if (!ArrayUtils.isEmpty(rs))
					ranges.addAll(Arrays.asList(rs));
			}

		});

		return ranges.toArray(new Range[] {});
	}

	@Override
	public WritableCell findCellByName(final String name) {
		WritableCell found = currentWorkbook.findCellByName(name);
		if (this.maxRows > 0 && found != null) {
			int fileIdx = found.getRow() / this.maxRows;
			// int current = this.currentFileIndex;
			this.switchFile(fileIdx);
			found = currentWorkbook.findCellByName(name);
			// this.switchFile(current);
		}

		return found;
	}

	@Override
	public int getNumberOfSheets() {
		return currentWorkbook.getNumberOfSheets();
	}

	@Override
	public String[] getRangeNames() {
		return currentWorkbook.getRangeNames();
	}

	@Override
	public WritableSheet getSheet(int index) throws IndexOutOfBoundsException {
		WritableSheet sheet = currentWorkbook.getSheet(index);
		return sheet == null ? null : new RemoteWritableSheet(sheet);
	}

	@Override
	public WritableSheet getSheet(String name) {
		WritableSheet sheet = currentWorkbook.getSheet(name);
		return sheet == null ? null : new RemoteWritableSheet(sheet);
	}

	@Override
	public String[] getSheetNames() {
		return currentWorkbook.getSheetNames();
	}

	@Override
	public WritableSheet[] getSheets() {
		WritableSheet[] sheets = currentWorkbook.getSheets();
		if (!ArrayUtils.isEmpty(sheets)) {
			for (int i = 0; i < sheets.length; i++) {
				sheets[i] = sheets[i] == null ? null : new RemoteWritableSheet(sheets[i]);
			}
		}
		return sheets;
	}

	@Override
	public WritableCell getWritableCell(String loc) {
		WritableCell found = currentWorkbook.getWritableCell(loc);
		if (this.maxRows > 0 && found != null) {
			int fileIdx = found.getRow() / this.maxRows;
			// int current = this.currentFileIndex;
			this.switchFile(fileIdx);
			found = currentWorkbook.getWritableCell(loc);
			// this.switchFile(current);
		}
		return found;
	}

	@Override
	public WritableSheet importSheet(final String name, final int index, final Sheet s) {
		this.workInFiles(new FileWorker() {

			public void work(WritableWorkbook currentWorkbook, int fileIndex) {
				WritableSheet imported = currentWorkbook.importSheet(name, index, s);
				if (maxRows > 0) {
					for (int r = imported.getRows() - 1; r >= 0; r--) {
						if (r >= fileIndex * maxRows && r < (fileIndex + 1) * maxRows) {
							// reserve
						} else {
							imported.removeRow(r);
						}
					}
				}
			}

		});

		return new RemoteWritableSheet(this.currentWorkbook.getSheet(index));
	}

	@Override
	public WritableSheet moveSheet(final int fromIndex, final int toIndex) {
		this.workInFiles(new FileWorker() {

			public void work(WritableWorkbook currentWorkbook, int fileIndex) {
				currentWorkbook.moveSheet(fromIndex, toIndex);

			}

		});

		return new RemoteWritableSheet(this.currentWorkbook.getSheet(toIndex));
	}

	@Override
	public void removeRangeName(final String name) {
		this.workInFiles(new FileWorker() {

			public void work(WritableWorkbook currentWorkbook, int fileIndex) {
				currentWorkbook.removeRangeName(name);

			}

		});

	}

	@Override
	public void removeSheet(final int index) {
		this.workInFiles(new FileWorker() {

			public void work(WritableWorkbook currentWorkbook, int fileIndex) {
				currentWorkbook.removeSheet(index);

			}

		});

	}

	@Override
	public void setColourRGB(final Colour c, final int r, final int g, final int b) {
		this.workInFiles(new FileWorker() {

			public void work(WritableWorkbook currentWorkbook, int fileIndex) {
				currentWorkbook.setColourRGB(c, r, g, b);

			}

		});

	}

	@Override
	public void setOutputFile(final File fileName) throws IOException {
		this.workInFiles(new FileWorker() {

			public void work(WritableWorkbook currentWorkbook, int fileIndex) {
				try {
					if (fileIndex == 0) {
						currentWorkbook.setOutputFile(fileName);
					} else {
						String prefix = FilenameUtils.getBaseName(fileName.getPath());
						String suffix = "." + FilenameUtils.getExtension(fileName.getPath());
						currentWorkbook.setOutputFile(TempFileProvider.createTempFile(prefix, suffix));
					}

				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}

		});

	}

	@Override
	public void setProtected(final boolean prot) {
		this.workInFiles(new FileWorker() {

			public void work(WritableWorkbook currentWorkbook, int fileIndex) {
				currentWorkbook.setProtected(prot);

			}

		});

	}

	@Override
	public void write() throws IOException {
		currentWorkbook.write();
	}

	public WritableWorkbook getCurrentWorkbook() {
		return currentWorkbook;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public List<File> getFiles() {
		return files;
	}

	public void addFile(File file) {
		try {
			files.add(file);
			String[] sheetNames = this.currentWorkbook.getSheetNames();

			if (this.currentWorkbook != null) {
				this.currentWorkbook.write();
				this.currentWorkbook.close();
			}

			this.currentWorkbook = Workbook.createWorkbook(file, this.settings);
			this.currentFileIndex = files.size() - 1;
			for (String name : sheetNames) {
				this.currentWorkbook.createSheet(name, this.currentWorkbook.getNumberOfSheets());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void addFile() {
		String rootFileName = files.get(0).getPath();
		File newFile = TempFileProvider.createTempFile(FilenameUtils.getBaseName(rootFileName), "."
				+ FilenameUtils.getExtension(rootFileName));
		this.addFile(newFile);
	}

	public void switchFile(int idx) {
		try {
			if (idx == this.currentFileIndex)
				return;

			File file = files.get(idx);

			if (this.currentWorkbook != null) {
				this.currentWorkbook.write();
				this.currentWorkbook.close();
			}

			this.currentWorkbook = Workbook.createWorkbook(file, Workbook.getWorkbook(file, this.settings),
					this.settings);
			this.currentFileIndex = idx;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void workInFiles(FileWorker worker) {
		this.workInFiles(worker, 0);
	}

	private void workInFiles(FileWorker worker, int startFileIndex) {
		this.workInFiles(worker, startFileIndex, files.size());
	}

	private void workInFiles(FileWorker worker, int startFileIndex, int endFileIndex) {
		if (worker == null)
			return;

		if (startFileIndex < 0)
			startFileIndex = 0;
		if (endFileIndex > files.size())
			endFileIndex = files.size();

		int fileIdx = this.currentFileIndex;
		for (int i = startFileIndex; i < endFileIndex; i++) {

			this.switchFile(i);
			worker.work(this.currentWorkbook, i);
		}
		this.switchFile(fileIdx);
	}

	private void workInRootFile(FileWorker worker) {
		if (worker == null)
			return;

		int fileIdx = this.currentFileIndex;
		this.switchFile(0);
		worker.work(this.currentWorkbook, 0);
		this.switchFile(fileIdx);
	}

	interface FileWorker {
		public void work(WritableWorkbook workbook, int fileIndex);
	}

	// Remote WritableSheet impl
	class RemoteWritableSheet implements WritableSheet {

		// private WritableSheet wrappedSheet;
		private String name;

		public RemoteWritableSheet(WritableSheet sheet) {
			if (sheet == null)
				throw new NullPointerException("sheet is null");

			this.name = sheet.getName();
		}

		public void addCell(WritableCell cell) throws WriteException, RowsExceededException {
			this.switchSheetFile(cell.getRow());

			getWrappedSheet().addCell(cell);
		}

		public void switchSheetFile(int row) {
			if (getMaxRows() > 0 && this.isRowsExceeded(row)) {
				try {
					int fileIdx = row / getMaxRows();
					// String sheetName = this.wrappedSheet.getName();

					if (fileIdx >= getFiles().size()) {
						addFile();
					} else {
						switchFile(fileIdx);
					}

					// this.wrappedSheet = currentWorkbook.getSheet(sheetName);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		public boolean isRowsExceeded(int row) {
			return row < currentFileIndex * maxRows || row >= (currentFileIndex + 1) * maxRows;
		}

		public void addColumnPageBreak(final int col) {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).addColumnPageBreak(col);

				}

			});

		}

		public void addHyperlink(WritableHyperlink h) throws WriteException, RowsExceededException {
			this.switchSheetFile(h.getRow());

			getWrappedSheet().addHyperlink(h);
		}

		// root file only
		public void addImage(final WritableImage image) {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).addImage(image);
				}

			});

		}

		public void addRowPageBreak(final int row) {
			this.switchSheetFile(row);
			getWrappedSheet().addRowPageBreak(row);
			// workInRootFile(new FileWorker() {
			//
			// public void work(WritableWorkbook currentWorkbook, int fileIndex)
			// {
			// currentWorkbook.getSheet(wrappedSheet.getName()).addRowPageBreak(row);
			// }
			//
			// });
		}

		// root file only
		public WritableImage getImage(final int i) {
			final WritableImage[] image = new WritableImage[] { null };
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					image[0] = currentWorkbook.getSheet(name).getImage(i);
				}

			});
			return image[0];
		}

		// root file only
		public int getNumberOfImages() {
			final int[] cnt = new int[] { 0 };
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					cnt[0] = currentWorkbook.getSheet(name).getNumberOfImages();
				}

			});
			return cnt[0];
		}

		public WritableCell getWritableCell(String loc) {
			WritableCell found = getWrappedSheet().getWritableCell(loc);
			if (maxRows > 0 && found != null) {
				// int fileIdx = found.getRow() / maxRows;
				// int current = currentFileIndex;
				// switchFile(fileIdx);
				// found =
				// currentWorkbook.getSheet(wrappedSheet.getName()).getWritableCell(loc);
				// switchFile(current);
				this.switchSheetFile(found.getRow());
				found = getWrappedSheet().getWritableCell(loc);
			}
			return found;
		}

		public WritableCell getWritableCell(int column, int row) {
			this.switchSheetFile(row);

			return getWrappedSheet().getWritableCell(column, row);
		}

		public WritableHyperlink[] getWritableHyperlinks() {
			final List<WritableHyperlink> links = new ArrayList<WritableHyperlink>();
			workInFiles(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					WritableHyperlink[] lnks = currentWorkbook.getSheet(name).getWritableHyperlinks();
					if (!ArrayUtils.isEmpty(lnks))
						links.addAll(Arrays.asList(lnks));

				}

			});
			return links.toArray(new WritableHyperlink[] {});
		}

		public void insertColumn(final int col) {
			workInFiles(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).insertColumn(col);

				}

			});
		}

		public void insertRow(final int row) {
			if (maxRows > 0) {
				workInFiles(new FileWorker() {

					public void work(WritableWorkbook currentWorkbook, int fileIndex) {
						currentWorkbook.getSheet(name).insertColumn(row);

					}

				}, row / maxRows + 1);
			}

			this.switchSheetFile(row);
			getWrappedSheet().insertRow(row);
		}

		// root file only
		public Range mergeCells(final int col1, final int row1, final int col2, final int row2) throws WriteException,
				RowsExceededException {
			final Range[] range = new Range[] { null };
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					try {
						range[0] = currentWorkbook.getSheet(name).mergeCells(col1, row1, col2, row2);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

				}

			});
			return range[0];
		}

		public void removeColumn(final int col) {
			workInFiles(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).removeColumn(col);

				}

			});
		}

		public void removeHyperlink(WritableHyperlink h) {
			this.switchSheetFile(h.getRow());

			getWrappedSheet().removeHyperlink(h);
		}

		public void removeHyperlink(WritableHyperlink h, boolean preserveLabel) {
			this.switchSheetFile(h.getRow());

			getWrappedSheet().removeHyperlink(h, preserveLabel);
		}

		// root file only
		public void removeImage(final WritableImage wi) {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).removeImage(wi);

				}

			});
		}

		public void removeRow(final int row) {
			if (maxRows > 0) {
				workInFiles(new FileWorker() {

					public void work(WritableWorkbook currentWorkbook, int fileIndex) {
						currentWorkbook.getSheet(name).removeRow(row);

					}

				}, row / maxRows + 1);
			}

			this.switchSheetFile(row);
			getWrappedSheet().removeRow(row);
		}

		// root file only
		public void setColumnGroup(final int col1, final int col2, final boolean collapsed) throws WriteException,
				RowsExceededException {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					try {
						currentWorkbook.getSheet(name).setColumnGroup(col1, col2, collapsed);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}

				}
			});
		}

		// root file only
		public void setColumnView(final int col, final int width) {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).setColumnView(col, width);

				}
			});
		}

		// root file only
		public void setColumnView(final int col, final CellView view) {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).setColumnView(col, view);

				}
			});
		}

		/**
		 * @deprecated
		 */
		// root file only
		public void setColumnView(final int col, final int width, final CellFormat format) {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).setColumnView(col, width, format);

				}
			});
		}

		/**
		 * @deprecated
		 */
		// root file only
		public void setFooter(final String l, final String c, final String r) {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).setFooter(l, c, r);

				}
			});
		}

		/**
		 * @deprecated
		 */
		// root file only
		public void setHeader(final String l, final String c, final String r) {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).setHeader(l, c, r);

				}
			});
		}

		/**
		 * @deprecated
		 */
		public void setHidden(final boolean hidden) {
			workInFiles(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).setHidden(hidden);

				}
			});
		}

		public void setName(final String name) {
			final String oldName = this.name;
			workInFiles(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(oldName).setName(name);

				}

			});
			this.name = name;
		}

		// root file only
		public void setPageSetup(final PageOrientation p) {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).setPageSetup(p);

				}
			});
		}

		// root file only
		public void setPageSetup(final PageOrientation p, final double hm, final double fm) {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).setPageSetup(p, hm, fm);

				}
			});
		}

		// root file only
		public void setPageSetup(final PageOrientation p, final PaperSize ps, final double hm, final double fm) {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).setPageSetup(p, ps, hm, fm);

				}
			});
		}

		/**
		 * @deprecated
		 */
		public void setProtected(final boolean prot) {
			workInFiles(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).setProtected(prot);

				}
			});
		}

		// root file only
		public void setRowGroup(final int row1, final int row2, final boolean collapsed) throws WriteException,
				RowsExceededException {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					try {
						currentWorkbook.getSheet(name).setRowGroup(row1, row2, collapsed);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			});
		}

		// root file only
		public void setRowView(final int row, final int height) throws RowsExceededException {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					try {
						currentWorkbook.getSheet(name).setRowView(row, height);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			});
		}

		public void setRowView(final int row, final boolean collapsed) throws RowsExceededException {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					try {
						currentWorkbook.getSheet(name).setRowView(row, collapsed);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			});
		}

		// root file only
		public void setRowView(final int row, final CellView view) throws RowsExceededException {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					try {
						currentWorkbook.getSheet(name).setRowView(row, view);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			});
		}

		// root file only
		public void setRowView(final int row, final int height, final boolean collapsed) throws RowsExceededException {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					try {
						currentWorkbook.getSheet(name).setRowView(row, height, collapsed);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			});
		}

		// root file only
		public void unmergeCells(final Range r) {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					currentWorkbook.getSheet(name).unmergeCells(r);

				}
			});
		}

		// root file only
		public void unsetColumnGroup(final int col1, final int col2) throws WriteException, RowsExceededException {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					try {
						currentWorkbook.getSheet(name).unsetColumnGroup(col1, col2);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			});
		}

		// root file only
		public void unsetRowGroup(final int row1, final int row2) throws WriteException, RowsExceededException {
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					try {
						currentWorkbook.getSheet(name).unsetRowGroup(row1, row2);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			});
		}

		public Cell findCell(final String contents) {
			final Cell[] cell = new Cell[] { null };
			workInFiles(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					if (cell[0] == null)
						cell[0] = currentWorkbook.getSheet(name).findCell(contents);

				}

			});
			return cell[0];
		}

		public Cell findCell(final String contents, final int firstCol, final int firstRow, final int lastCol,
				final int lastRow, final boolean reverse) {
			final Cell[] cell = new Cell[] { null };
			if (maxRows > 0) {
				workInFiles(new FileWorker() {

					public void work(WritableWorkbook currentWorkbook, int fileIndex) {
						if (cell[0] == null)
							cell[0] = currentWorkbook.getSheet(name).findCell(contents, firstCol, firstRow, lastCol,
									lastRow, reverse);

					}

				}, firstRow / maxRows, lastRow / maxRows + 1);
			} else {
				cell[0] = getWrappedSheet().findCell(contents, firstCol, firstRow, lastCol, lastRow, reverse);
			}
			return cell[0];
		}

		public Cell findCell(final Pattern pattern, final int firstCol, final int firstRow, final int lastCol,
				final int lastRow, final boolean reverse) {
			final Cell[] cell = new Cell[] { null };
			if (maxRows > 0) {
				workInFiles(new FileWorker() {

					public void work(WritableWorkbook currentWorkbook, int fileIndex) {
						if (cell[0] == null)
							cell[0] = currentWorkbook.getSheet(name).findCell(pattern, firstCol, firstRow, lastCol,
									lastRow, reverse);

					}

				}, firstRow / maxRows, lastRow / maxRows + 1);
			} else {
				cell[0] = getWrappedSheet().findCell(pattern, firstCol, firstRow, lastCol, lastRow, reverse);
			}
			return cell[0];
		}

		public LabelCell findLabelCell(final String contents) {
			final LabelCell[] cell = new LabelCell[] { null };
			workInFiles(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					if (cell[0] == null)
						cell[0] = currentWorkbook.getSheet(name).findLabelCell(contents);

				}

			});
			return cell[0];
		}

		public Cell getCell(String loc) {
			Cell found = getWrappedSheet().getCell(loc);
			if (maxRows > 0 && found != null) {
				// int fileIdx = found.getRow() / maxRows;
				// int current = currentFileIndex;
				// switchFile(fileIdx);
				this.switchSheetFile(found.getRow());
				found = getWrappedSheet().getCell(loc);
				// switchFile(current);
			}
			return found;
		}

		public Cell getCell(int column, int row) {
			this.switchSheetFile(row);

			return getWrappedSheet().getCell(column, row);
		}

		public Cell[] getColumn(final int col) {
			final List<Cell> column = new ArrayList<Cell>();
			workInFiles(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					Cell[] cells = currentWorkbook.getSheet(name).getColumn(col);
					if (!ArrayUtils.isEmpty(cells)) {
						for (Cell cell : cells) {
							if (!CellType.EMPTY.equals(cell.getType())) {
								column.add(cell);
							}
						}
					}

				}

			});
			return column.toArray(new Cell[] {});
		}

		/**
		 * @deprecated
		 */
		// root file only
		public CellFormat getColumnFormat(final int col) {
			final CellFormat[] format = new CellFormat[] { null };
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					format[0] = currentWorkbook.getSheet(name).getColumnFormat(col);

				}
			});
			return format[0];
		}

		// root file only
		public int[] getColumnPageBreaks() {
			final List<Integer> breaks = new ArrayList<Integer>();
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					int[] bs = currentWorkbook.getSheet(name).getColumnPageBreaks();
					if (!ArrayUtils.isEmpty(bs)) {
						breaks.addAll(Arrays.asList(ArrayUtils.toObject(bs)));
					}

				}
			});
			return ArrayUtils.toPrimitive(breaks.toArray(new Integer[] {}));
		}

		// root file only
		public CellView getColumnView(final int col) {
			final CellView[] cellView = new CellView[] { null };
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					cellView[0] = currentWorkbook.getSheet(name).getColumnView(col);

				}
			});
			return cellView[0];
		}

		/**
		 * @deprecated
		 */
		// root file only
		public int getColumnWidth(final int col) {
			final int[] width = new int[] { -1 };
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					width[0] = currentWorkbook.getSheet(name).getColumnWidth(col);

				}
			});
			return width[0];
		}

		public int getColumns() {
			final int[] columns = new int[] { 0 };
			workInFiles(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					int cs = currentWorkbook.getSheet(name).getColumns();
					if (cs > columns[0])
						columns[0] = cs;

				}
			});
			return columns[0];
		}

		// root file only
		public Image getDrawing(final int i) {
			final Image[] image = new Image[] { null };
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					image[0] = currentWorkbook.getSheet(name).getDrawing(i);

				}
			});
			return image[0];
		}

		public Hyperlink[] getHyperlinks() {
			final List<Hyperlink> links = new ArrayList<Hyperlink>();
			workInFiles(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					Hyperlink[] lnk = currentWorkbook.getSheet(name).getHyperlinks();
					if (!ArrayUtils.isEmpty(lnk)) {
						links.addAll(Arrays.asList(lnk));
					}

				}
			});
			return links.toArray(new Hyperlink[] {});
		}

		// root file only
		public Range[] getMergedCells() {
			final List<Range> ranges = new ArrayList<Range>();
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					Range[] r = currentWorkbook.getSheet(name).getMergedCells();
					if (!ArrayUtils.isEmpty(r)) {
						ranges.addAll(Arrays.asList(r));
					}

				}
			});
			return ranges.toArray(new Range[] {});
		}

		public String getName() {
			return name;
		}

		public Cell[] getRow(int row) {
			this.switchSheetFile(row);

			return getWrappedSheet().getRow(row);
		}

		/**
		 * @deprecated
		 */
		// root file only
		public int getRowHeight(final int row) {
			final int[] height = new int[] { 0 };
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					height[0] = currentWorkbook.getSheet(name).getRowHeight(row);

				}
			});
			return height[0];
		}

		// root file only
		public int[] getRowPageBreaks() {
			final List<Integer> breaks = new ArrayList<Integer>();
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					int[] bs = currentWorkbook.getSheet(name).getRowPageBreaks();
					if (!ArrayUtils.isEmpty(bs)) {
						breaks.addAll(Arrays.asList(ArrayUtils.toObject(bs)));
					}

				}

			});
			return ArrayUtils.toPrimitive(breaks.toArray(new Integer[] {}));
		}

		// root file only
		public CellView getRowView(final int row) {
			final CellView[] cellView = new CellView[] { null };
			workInRootFile(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					cellView[0] = currentWorkbook.getSheet(name).getRowView(row);

				}
			});
			return cellView[0];
		}

		public int getRows() {
			final int[] rows = new int[] { 0 };
			workInFiles(new FileWorker() {

				public void work(WritableWorkbook currentWorkbook, int fileIndex) {
					rows[0] = currentWorkbook.getSheet(name).getRows();

				}
			}, files.size() - 1);
			return rows[0];
		}

		public SheetSettings getSettings() {
			return getWrappedSheet().getSettings();
		}

		/**
		 * @deprecated
		 */
		public boolean isHidden() {
			return getWrappedSheet().isHidden();
		}

		/**
		 * @deprecated
		 */
		public boolean isProtected() {
			return getWrappedSheet().isProtected();
		}

		public WritableSheet getWrappedSheet() {
			return currentWorkbook.getSheet(name);
		}

		@Override
		public void applySharedDataValidation(WritableCell arg0, int arg1,
				int arg2) throws WriteException {
			this.applySharedDataValidation(arg0, arg1, arg2);
			
		}

		@Override
		public void removeSharedDataValidation(WritableCell arg0)
				throws WriteException {
			this.removeSharedDataValidation(arg0);
			
		}
	}

	@Override
	public void close() throws IOException, WriteException {
		currentWorkbook.close();
		
	}

}
