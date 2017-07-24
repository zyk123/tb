package com.flash.toolbar.omp.common.export;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.CellType;
import jxl.CellView;
import jxl.HeaderFooter;
import jxl.Hyperlink;
import jxl.Image;
import jxl.LabelCell;
import jxl.Range;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.CellFormat;
import jxl.format.PageOrientation;
import jxl.read.biff.BiffException;
import jxl.read.biff.PasswordException;
import jxl.read.biff.WorkbookParser;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.biff.WritableWorkbookImpl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;

import com.flash.toolbar.omp.common.io.TempFileProvider;

public class RemoteWorkbook extends Workbook {

	public static final int DEFAULT_MAX_ROWS = 1024;

	private Workbook currentWorkbook;
	private int maxRows = -1;
	private List<File> files = new ArrayList<File>();// element [0] is root xls
	private int currentFileIndex = -1;
	private WorkbookSettings settings;

	protected RemoteWorkbook(List<File> files, WorkbookSettings settings, int maxRows) {
		this.files.addAll(files);
		this.settings = settings;
		this.maxRows = maxRows;
		currentFileIndex = -2;
	}

	protected RemoteWorkbook(File file, WorkbookSettings ws) {
		this(file, ws, DEFAULT_MAX_ROWS);
	}

	protected RemoteWorkbook(File file, WorkbookSettings ws, int maxRows) {
		if (file == null || !file.exists())
			throw new IllegalArgumentException("Invalid excel file : " + file);

		this.maxRows = maxRows;
		this.files.add(file);
		this.settings = ws;
	}

	@Override
	public void close() {
		currentWorkbook.close();
	}

	@Override
	public Range[] findByName(final String name) {
		final List<Range> ranges = new ArrayList<Range>();
		this.workInFiles(new FileWorker() {

			public void work(Workbook currentWorkbook, int fileIndex) {
				Range[] rs = currentWorkbook.findByName(name);
				if (!ArrayUtils.isEmpty(rs))
					ranges.addAll(Arrays.asList(rs));
			}

		});

		return ranges.toArray(new Range[] {});
	}

	@Override
	public Cell findCellByName(String name) {
		Cell found = currentWorkbook.findCellByName(name);
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
	public Cell getCell(String loc) {
		Cell found = currentWorkbook.getCell(loc);
		if (maxRows > 0 && found != null) {
			int fileIdx = found.getRow() / maxRows;
			// int current = currentFileIndex;
			switchFile(fileIdx);
			found = currentWorkbook.getCell(loc);
			// switchFile(current);
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
	public Sheet getSheet(int index) throws IndexOutOfBoundsException {
		Sheet sheet = currentWorkbook.getSheet(index);
		return sheet == null ? null : new RemoteSheet(sheet);
	}

	@Override
	public Sheet getSheet(String name) {
		Sheet sheet = currentWorkbook.getSheet(name);
		return sheet == null ? null : new RemoteSheet(sheet);
	}

	@Override
	public String[] getSheetNames() {
		return currentWorkbook.getSheetNames();
	}

	@Override
	public Sheet[] getSheets() {
		Sheet[] sheets = currentWorkbook.getSheets();
		if (!ArrayUtils.isEmpty(sheets)) {
			for (int i = 0; i < sheets.length; i++) {
				sheets[i] = sheets[i] == null ? null : new RemoteSheet(sheets[i]);
			}
		}
		return sheets;
	}

	@Override
	public boolean isProtected() {
		return currentWorkbook.isProtected();
	}


	public void switchFile(int idx) {
		try {
			if (idx == this.currentFileIndex || idx < 0)
				return;

			File file = files.get(idx);

			if (this.currentWorkbook != null) {
				this.currentWorkbook.close();
			}

			this.currentWorkbook = this.createWrappedWorkbook(file);
			this.currentFileIndex = idx;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Workbook createWrappedWorkbook(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);

			jxl.read.biff.File dataFile = null;

			try {
				dataFile = new jxl.read.biff.File(fis, this.settings);
			} finally {
				fis.close();
			}

			fis.close();

			Workbook workbook = new WorkbookParser(dataFile, this.settings);
			this.invokeWorkbookParse(workbook);
			return workbook;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private void invokeWorkbookParse(Workbook workbook) {
		try {
			Method parseMethod = workbook.getClass().getDeclaredMethod("parse");
			if (parseMethod != null) {
				if (!parseMethod.isAccessible()) {
					parseMethod.setAccessible(true);
				}
				parseMethod.invoke(workbook);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Workbook getCurrentWorkbook() {
		return currentWorkbook;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public List<File> getFiles() {
		return files;
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
		public void work(Workbook workbook, int fileIndex);
	}

	class RemoteSheet implements Sheet {
		// private Sheet wrappedSheet;
		private String name;

		public RemoteSheet(Sheet sheet) {
			if (sheet == null)
				throw new NullPointerException("sheet is null");

			name = sheet.getName();
		}

		public Cell findCell(final String contents) {
			final Cell[] cell = new Cell[] { null };
			workInFiles(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
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

					public void work(Workbook currentWorkbook, int fileIndex) {
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

					public void work(Workbook currentWorkbook, int fileIndex) {
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

				public void work(Workbook currentWorkbook, int fileIndex) {
					if (cell[0] == null)
						cell[0] = currentWorkbook.getSheet(name).findLabelCell(contents);

				}

			});
			return cell[0];
		}

		public void switchSheetFile(int row) {
			if (getMaxRows() > 0 && this.isRowsExceeded(row)) {
				try {
					int fileIdx = row / getMaxRows();
					// String sheetName = this.wrappedSheet.getName();

					switchFile(fileIdx);

					// this.wrappedSheet = currentWorkbook.getSheet(sheetName);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		public boolean isRowsExceeded(int row) {
			return row < currentFileIndex * maxRows || row >= (currentFileIndex + 1) * maxRows;
		}

		public Cell getCell(String loc) {
			Cell found = getWrappedSheet().getCell(loc);
			if (maxRows > 0 && found != null) {
				// int fileIdx = found.getRow() / maxRows;
				// int current = currentFileIndex;
				// switchFile(fileIdx);
				// found =
				// currentWorkbook.getSheet(wrappedSheet.getName()).getCell(loc);
				// switchFile(current);
				this.switchSheetFile(found.getRow());
				getWrappedSheet().getCell(loc);
			}
			return found;
		}

		public Cell getCell(int column, int row) {
			// Cell found = wrappedSheet.getCell(column, row);
			// if (maxRows > 0 && found != null) {
			// int fileIdx = found.getRow() / maxRows;
			// int current = currentFileIndex;
			// switchFile(fileIdx);
			// found =
			// currentWorkbook.getSheet(wrappedSheet.getName()).getCell(column,
			// row);
			// switchFile(current);
			// }
			this.switchSheetFile(row);

			return getWrappedSheet().getCell(column, row);
		}

		public Cell[] getColumn(final int col) {
			final List<Cell> column = new ArrayList<Cell>();
			workInFiles(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
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
		public CellFormat getColumnFormat(final int col) {
			final CellFormat[] format = new CellFormat[] { null };
			workInRootFile(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
					format[0] = currentWorkbook.getSheet(name).getColumnFormat(col);

				}
			});
			return format[0];
		}

		public int[] getColumnPageBreaks() {
			final List<Integer> breaks = new ArrayList<Integer>();
			workInRootFile(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
					int[] bs = currentWorkbook.getSheet(name).getColumnPageBreaks();
					if (!ArrayUtils.isEmpty(bs)) {
						breaks.addAll(Arrays.asList(ArrayUtils.toObject(bs)));
					}

				}
			});
			return ArrayUtils.toPrimitive(breaks.toArray(new Integer[] {}));
		}

		public CellView getColumnView(final int col) {
			final CellView[] cellView = new CellView[] { null };
			workInRootFile(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
					cellView[0] = currentWorkbook.getSheet(name).getColumnView(col);

				}
			});
			return cellView[0];
		}

		/**
		 * @deprecated
		 */
		public int getColumnWidth(final int col) {
			final int[] width = new int[] { -1 };
			workInRootFile(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
					width[0] = currentWorkbook.getSheet(name).getColumnWidth(col);

				}
			});
			return width[0];
		}

		public int getColumns() {
			final int[] columns = new int[] { 0 };
			workInFiles(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
					int cs = currentWorkbook.getSheet(name).getColumns();
					if (cs > columns[0])
						columns[0] = cs;

				}
			});
			return columns[0];
		}

		public Image getDrawing(final int i) {
			final Image[] image = new Image[] { null };
			workInRootFile(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
					image[0] = currentWorkbook.getSheet(name).getDrawing(i);

				}
			});
			return image[0];
		}

		public Hyperlink[] getHyperlinks() {
			final List<Hyperlink> links = new ArrayList<Hyperlink>();
			workInFiles(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
					Hyperlink[] lnk = currentWorkbook.getSheet(name).getHyperlinks();
					if (!ArrayUtils.isEmpty(lnk)) {
						links.addAll(Arrays.asList(lnk));
					}

				}
			});
			return links.toArray(new Hyperlink[] {});
		}

		public Range[] getMergedCells() {
			final List<Range> ranges = new ArrayList<Range>();
			workInRootFile(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
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
			// if (maxRows > 0) {
			// int fileIdx = row / maxRows;
			// int current = currentFileIndex;
			// switchFile(fileIdx);
			// Cell[] cells =
			// currentWorkbook.getSheet(wrappedSheet.getName()).getRow(row);
			// switchFile(current);
			// return cells;
			// } else {
			//
			// }
			this.switchSheetFile(row);
			return getWrappedSheet().getRow(row);
		}

		/**
		 * @deprecated
		 */
		public int getRowHeight(final int row) {
			final int[] height = new int[] { 0 };
			workInRootFile(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
					height[0] = currentWorkbook.getSheet(name).getRowHeight(row);

				}
			});
			return height[0];
		}

		public int[] getRowPageBreaks() {
			final List<Integer> breaks = new ArrayList<Integer>();
			workInRootFile(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
					int[] bs = currentWorkbook.getSheet(name).getRowPageBreaks();
					if (!ArrayUtils.isEmpty(bs)) {
						breaks.addAll(Arrays.asList(ArrayUtils.toObject(bs)));
					}

				}

			});
			return ArrayUtils.toPrimitive(breaks.toArray(new Integer[] {}));
		}

		public CellView getRowView(final int row) {
			final CellView[] cellView = new CellView[] { null };
			workInRootFile(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
					cellView[0] = currentWorkbook.getSheet(name).getRowView(row);

				}
			});
			return cellView[0];
		}

		public int getRows() {
			final int[] rows = new int[] { 0 };
			workInFiles(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
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

		public Sheet getWrappedSheet() {
			return currentWorkbook.getSheet(name);
		}

		public int getNumberOfImages() {
			final int[] cnt = new int[] { 0 };
			workInRootFile(new FileWorker() {

				public void work(Workbook currentWorkbook, int fileIndex) {
					cnt[0] = currentWorkbook.getSheet(name).getNumberOfImages();
				}

			});
			return cnt[0];
		}
	}

	// ----------Workbook factory static methods------------------------------
	public static Workbook getWorkbook(File file) throws IOException, BiffException {
		return getWorkbook(file, createWorkbookSettings());
	}

	public static Workbook getWorkbook(File file, WorkbookSettings ws) throws IOException, BiffException {
		return getWorkbook(file, ws, DEFAULT_MAX_ROWS);
	}

	public static Workbook getWorkbook(File file, WorkbookSettings ws, int maxRows) throws IOException, BiffException {
		// Workbook workbook = Workbook.getWorkbook(file, ws);
		// return workbook;

		if (maxRows > 0) {
			RemoteWorkbook workbook = new RemoteWorkbook(file, ws, maxRows);
			workbook.parse();
			return workbook;
		} else {
			Workbook workbook = Workbook.getWorkbook(file, ws);
			return workbook;
		}
	}

	public static WritableWorkbook createWorkbook(File file) throws IOException {
		return createWorkbook(file, createWorkbookSettings());
	}

	public static WritableWorkbook createWorkbook(File file, WorkbookSettings ws) throws IOException {
		return createWorkbook(file, ws, DEFAULT_MAX_ROWS);
	}

	public static WritableWorkbook createWorkbook(File file, WorkbookSettings ws, int maxRows) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		WritableWorkbook wb = new WritableWorkbookImpl(fos, true, ws);
		WritableWorkbook w = maxRows > 0 ? new RemoteWritableWorkbook(wb, file, maxRows) : wb;
		return w;
	}

	public static WritableWorkbook createWorkbook(File file, Workbook in) throws IOException {
		return createWorkbook(file, in, createWorkbookSettings());
	}

	public static WritableWorkbook createWorkbook(File file, Workbook in, WorkbookSettings ws) throws IOException {
		return createWorkbook(file, in, ws, DEFAULT_MAX_ROWS);
	}

	public static WritableWorkbook createWorkbook(File file, Workbook in, WorkbookSettings ws, int maxRows)
			throws IOException {
		if (in instanceof RemoteWorkbook) {
			RemoteWorkbook remoteIn = (RemoteWorkbook) in;
			String prefix = FilenameUtils.getBaseName(file.getPath());
			String suffix = "." + FilenameUtils.getExtension(file.getPath());
			List<File> newFiles = new ArrayList<File>();
			for (int i = 0; i < remoteIn.getFiles().size(); i++) {
				File newF = null;
				if (i == 0) {
					newF = file;
				} else {
					newF = TempFileProvider.createTempFile(prefix, suffix);
				}
				newFiles.add(newF);
				FileUtils.copyFile(remoteIn.getFiles().get(i), newF);
			}

			RemoteWritableWorkbook writeBook = new RemoteWritableWorkbook(newFiles, ws, maxRows);
			return writeBook;
		} else {
			return Workbook.createWorkbook(file, in, ws);
		}
	}

	public static WritableWorkbook createWorkbook(File newFile, File srcFile) throws Exception {
		return createWorkbook(newFile, srcFile, createWorkbookSettings());
	}

	public static WritableWorkbook createWorkbook(File newFile, File srcFile, WorkbookSettings ws) throws Exception {
		return createWorkbook(newFile, srcFile, ws, DEFAULT_MAX_ROWS);
	}

	public static WritableWorkbook createWorkbook(File newFile, File srcFile, WorkbookSettings ws, int maxRows)
			throws Exception {
		Workbook readBook = getWorkbook(srcFile, ws, maxRows);

		readBook.close();
		WritableWorkbook writeBook = createWorkbook(newFile, readBook, ws, maxRows);
		// readBook.close();

		return writeBook;
	}

	public static WritableSheet createWritableSheet(WritableWorkbook workbook, String sheetName, String headerStr)
			throws Exception {
		WritableSheet sheet = workbook.createSheet(sheetName, workbook.getNumberOfSheets());
		SheetSettings setting = sheet.getSettings();
		setting.setTopMargin(1);
		setting.setLeftMargin(0.5);
		setting.setBottomMargin(0.5);
		setting.setRightMargin(1);
		setting.setHorizontalCentre(true);
		setting.setOrientation(PageOrientation.LANDSCAPE);
		setting.setPrintTitlesRow(0, 0);
		// 表头固定
		setting.setHorizontalFreeze(0);
		if (headerStr != null) {
			HeaderFooter hf = new HeaderFooter();
			hf.getCentre().setFontSize(10);
			hf.getCentre().toggleItalics();
			hf.getCentre().append(headerStr);
			setting.setHeader(hf);
		}
		return sheet;
	}

	public static WritableSheet createWritableProtectedSheet(WritableWorkbook workbook, String sheetName,
			String headerStr) throws Exception {
		WritableSheet sheet = createWritableSheet(workbook, sheetName, headerStr);
		sheet.getSettings().setProtected(true);
		return sheet;
	}


	public static WorkbookSettings createWorkbookSettings() {
		WorkbookSettings setting = new WorkbookSettings();
		setting.setUseTemporaryFileDuringWrite(true);
		return setting;
	}

	public static void main(String[] args) {
		try {
			File xlsDir = new File("D:" + File.separator + "temp" + File.separator + "export" + File.separator + "xls");
			if (!xlsDir.exists())
				xlsDir.mkdirs();

			String xlsBase = "test";

			File xlsFile = new File(xlsDir.getPath() + File.separator + xlsBase + ".xls");
			if (!xlsFile.exists())
				xlsFile.createNewFile();

			WorkbookSettings settings = createWorkbookSettings();
			int maxRows = DEFAULT_MAX_ROWS;

			// create remote workbook
			WritableWorkbook writableWorkbook = RemoteWorkbook.createWorkbook(xlsFile, settings, maxRows);
			WritableSheet writableSheet = writableWorkbook.createSheet("test", writableWorkbook.getNumberOfSheets());

			CellFormats formats = CellFormats.createDefaultCellFormats();

			for (int r = 0; r < 655; r++) {
				for (int c = 0; c < 255; c++) {
					Label label = new Label(c, r, "R" + r + "C" + c);

					label.setCellFormat(formats.getDefaultFormat());

					writableSheet.addCell(label);
				}

			}

			writableWorkbook.write();
			writableWorkbook.close();

			// read remote workbook
			File newXlsFile = new File(xlsDir.getPath() + File.separator + xlsBase + "_new" + ".xls");
			if (!newXlsFile.exists()) {
				newXlsFile.createNewFile();
			}

			WritableWorkbook newWorkbook = RemoteWorkbook.createWorkbook(newXlsFile, xlsFile, settings, maxRows);

			newWorkbook.write();
			newWorkbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void parse() throws BiffException, PasswordException {
		try {
			File resultFile = TempFileProvider.createTempFile("result", ".txt");
			files.clear();
			BufferedReader br = new BufferedReader(new FileReader(resultFile));
			String line;
			while ((line = br.readLine()) != null) {
				files.add(new File(line));
			}
			br.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} 		
		this.switchFile(0);
		
	}
}
