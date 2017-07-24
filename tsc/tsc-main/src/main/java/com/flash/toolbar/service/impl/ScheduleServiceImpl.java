package com.flash.toolbar.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.*;
import com.flash.toolbar.mapper.*;
import com.flash.toolbar.model.*;
import com.flash.toolbar.redis.RedisOperation;
import com.flash.toolbar.service.ScheduleService;
import com.flash.toolbar.utils.SHA1HashSignature;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private RedisOperation redisOperation;

	/**
	 * 操作运营商登记表
	 */
	@Autowired
	private SysTelecomOperatorMapper sysTelecomOperatorMapper;

	/**
	 * 操作号段运营商对应表
	 */
	@Autowired
	private SysOperatorSegMapper sysOperatorSegMapper;

	/**
	 * 操作白名单表
	 */
	@Autowired
	private HyWhiteListMapper hyWhiteListMapper;

	/**
	 * 操作白名单号段表
	 */
	@Autowired
	private HyWhiteSectionMapper hyWhiteSectionMapper;

	/**
	 * 操作黑名单表
	 */
	@Autowired
	private HyBlackListMapper hyBlackListMapper;

	/**
	 * 操作黑名单号段表
	 */
	@Autowired
	private HyBlackSectionMapper hyBlackSectionMapper;

	/**
	 * 黑名单设备表
	 */
	@Autowired
	private HyBlacklistDeviceMapper hyBlacklistDeviceMapper;


	@Autowired
	private CzReloadOrderMapper czReloadOrderMapper;

	@Autowired
	private CzPayResponseMapper czPayResponseMapper;

	// 过期时间 一天
	private int expireTimes = 86400;

	// 分页
	private int pageSize = 100000;

	@Override
	public void doStaticCache() {
		BusinessLogger.LoggerInfo("quartz-schedule", "tsc", "doStaticCache",
				true, null, String.valueOf(new Date().getTime()));
		try {
			// expireTimes =
			// Integer.parseInt(ParamPropertiesUtil.getCacheTimeOut());
			// 重新读取数据，为保证数据不出现空档：如果存在key，新建一个新key，然后删除原有的key，再把新key重命名为原来的key
			// 运营商表
			doSysTelecomOperator();

			// 运营商号段表
			List<SysOperatorSeg> sysOperatorSegList = sysOperatorSegMapper
					.selectAll();

			doSysOperatorSeg(sysOperatorSegList);
			Set<String> set = new HashSet<String>();
			for (SysOperatorSeg seg : sysOperatorSegList) {
				String countryNo = seg.getCountryno().trim();
				String toperatorId = seg.getToperatorid().trim();
				String ct = countryNo + "_" + toperatorId;
				if (!set.contains(ct)) {
					set.add(ct);
				} else {
					continue;
				}

				// 白名单表
				doHyWhiteList(countryNo, toperatorId);
				Thread.sleep(30000);
				// 黑名单表
				doHyBlackList(countryNo, toperatorId);
				Thread.sleep(30000);

				// 黑名单设备表
				doHyBlackListDevice(countryNo, toperatorId);
				Thread.sleep(30000);
				// 白名单号段表
				doHyWhiteSection(countryNo, toperatorId);
				Thread.sleep(30000);
				// 黑名单号段表
				doHyBlackSection(countryNo, toperatorId);

			}
		} catch (Exception e) {
			ExceptionLogger.error("quartz redis error !" , e);
		}
		BusinessLogger.LoggerInfo("quartz-schedule", "tsc", "doStaticCache",
				false, null, String.valueOf(new Date().getTime()));
	}

	private void doSysTelecomOperator() {
		try {
			List<SysTelecomOperator> sysTelecomOperatorList = sysTelecomOperatorMapper
					.selectAll();
			if (redisOperation.exists(Constant.CACHE_SYS_TELECOMOPERATOR)) {
				if (sysTelecomOperatorList.size() > 0) {
					Jedis jedis = null;
					try {
				    jedis = createJedis();
				    // 使用pipeline sadd 优化存储速度
					Pipeline p = jedis.pipelined();
					long start = System.currentTimeMillis();
					for (SysTelecomOperator sto : sysTelecomOperatorList) {
						JSONObject obj = (JSONObject) JSONObject.toJSON(sto);
						p.sadd("sys_TelecomOperator_new",
								obj.toJSONString());
					}
					p.sync();
					long end = System.currentTimeMillis();
					TraceLogger
							.info("tsc redis CACHE_SYSTELECOMOPERATOR with pipeline"
									+ " used ["
									+ (end - start)
									+ "] ms ..");
					closeJedis(jedis);
					} catch (Exception e) {
						ExceptionLogger.error("quartz redis error !" , e);
					} finally {
						closeJedis(jedis);
					}
				} else {
					redisOperation.SADD("sys_TelecomOperator_new", "");
				}
				rename(Constant.CACHE_SYS_TELECOMOPERATOR);
			} else {
				if (sysTelecomOperatorList.size() > 0) {
					Jedis jedis = null;
					try {
				    jedis = createJedis();
				    // 使用pipeline sadd 优化存储速度
					Pipeline p = jedis.pipelined();
					long start = System.currentTimeMillis();
					for (SysTelecomOperator sto : sysTelecomOperatorList) {
						JSONObject obj = (JSONObject) JSONObject.toJSON(sto);
						p.sadd(Constant.CACHE_SYS_TELECOMOPERATOR,
								obj.toJSONString());
					}
					p.sync();
					long end = System.currentTimeMillis();
					TraceLogger
							.info("tsc redis CACHE_SYSTELECOMOPERATOR with pipeline"
									+ " used ["
									+ (end - start)
									+ "] ms ..");
					closeJedis(jedis);
					} catch (Exception e) {
						ExceptionLogger.error("quartz redis error !" , e);
					} finally {
						closeJedis(jedis);
					}
				} else {
					redisOperation.SADD(Constant.CACHE_SYS_TELECOMOPERATOR, "");
				}
				redisOperation.expire(Constant.CACHE_SYS_TELECOMOPERATOR,
						expireTimes, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			ExceptionLogger.error("quartz redis error !" , e);
		}
	}

	private void doSysOperatorSeg(List<SysOperatorSeg> sysOperatorSegList) {
		try {
			if (redisOperation.exists(Constant.CACHE_SYS_OPERATORSEG)) {
				if (sysOperatorSegList.size() > 0) {
					Jedis jedis = null;
					try {
				    jedis = createJedis();
				    // 使用pipeline sadd 优化存储速度
					Pipeline p = jedis.pipelined();
					long start = System.currentTimeMillis();
					for (SysOperatorSeg seg : sysOperatorSegList) {
						JSONObject obj = (JSONObject) JSONObject.toJSON(seg);
						p.sadd("sys_OperatorSeg_new",
								obj.toJSONString());
					}
					p.sync();
					long end = System.currentTimeMillis();
					TraceLogger
							.info("tsc redis CACHE_SYSOPERATORSEG with pipeline"
									+ " used ["
									+ (end - start)
									+ "] ms ..");
					closeJedis(jedis);
					} catch (Exception e) {
						ExceptionLogger.error("quartz redis error !" , e);
					} finally {
						closeJedis(jedis);
					}
				} else {
					redisOperation.SADD("sys_OperatorSeg_new", "");
				}
				rename(Constant.CACHE_SYS_OPERATORSEG);
			} else {
				if (sysOperatorSegList.size() > 0) {
					Jedis jedis = null;
					try {
				    jedis = createJedis();
				    // 使用pipeline sadd 优化存储速度
					Pipeline p = jedis.pipelined();
					long start = System.currentTimeMillis();
					for (SysOperatorSeg seg : sysOperatorSegList) {
						JSONObject obj = (JSONObject) JSONObject.toJSON(seg);
						p.sadd(Constant.CACHE_SYS_OPERATORSEG,
								obj.toJSONString());
					}
					p.sync();
					long end = System.currentTimeMillis();
					TraceLogger
							.info("tsc redis CACHE_SYSOPERATORSEG with pipeline"
									+ " used ["
									+ (end - start)
									+ "] ms ..");
					closeJedis(jedis);
					} catch (Exception e) {
						ExceptionLogger.error("quartz redis error !" , e);
					} finally {
						closeJedis(jedis);
					}
				} else {
					redisOperation.SADD(Constant.CACHE_SYS_OPERATORSEG, "");
				}
				redisOperation.expire(Constant.CACHE_SYS_OPERATORSEG,
						expireTimes, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			ExceptionLogger.error("quartz redis error !" , e);
		}
	}

	private void doHyWhiteList(String countryNo, String toperatorId) {
		try {
			long whiteStartTime = System.currentTimeMillis();
			// 白名单表
			String whiteListName = Constant.CACHE_HY_WHITELIST + countryNo
					+ "_" + toperatorId;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("countryno", countryNo);
			map.put("toperatorid", toperatorId);
			int whiteListCount = hyWhiteListMapper.countByToperator(map);

			// 分页查询
			double size = Math.ceil((double) whiteListCount / pageSize);
			if (redisOperation.exists(whiteListName)) {
				if (whiteListCount > 0) {
					Jedis jedis = null;
					try {
						jedis = createJedis();
						for (int i = 0; i < size; i++) {
							map.put("startnum", (i * pageSize) + 1);
							map.put("endnum", (i + 1) * pageSize);
							List<String> hyWhiteList = hyWhiteListMapper
									.selectMobileNoPageByToperator(map);
							// 使用pipeline sadd 优化存储速度
							Pipeline p = jedis.pipelined();
							long start = System.currentTimeMillis();
							for (String wno : hyWhiteList) {
								p.sadd(whiteListName + "_new", wno.trim());
							}
							p.sync();
							long end = System.currentTimeMillis();
							TraceLogger
									.info("tsc redis CACHE_HY_WHITELIST with pipeline"
											+ i
											+ " used ["
											+ (end - start)
											+ "] ms ..");
						}
						closeJedis(jedis);
					} catch (Exception e) {
						ExceptionLogger.error("quartz redis error !" , e);
					} finally {
						closeJedis(jedis);
					}
				} else {
					redisOperation.SADD(whiteListName + "_new", "");
				}
				rename(whiteListName);
			} else {
				if (whiteListCount > 0) {
					Jedis jedis = null;
					try {
						jedis = createJedis();
						for (int i = 0; i < size; i++) {
							map.put("startnum", (i * pageSize) + 1);
							map.put("endnum", (i + 1) * pageSize);
							List<String> hyWhiteList = hyWhiteListMapper
									.selectMobileNoPageByToperator(map);
							// 使用pipeline sadd 优化存储速度
							Pipeline p = jedis.pipelined();
							long start = System.currentTimeMillis();
							for (String wno : hyWhiteList) {
								p.sadd(whiteListName, wno.trim());
							}
							p.sync();
							long end = System.currentTimeMillis();
							TraceLogger
									.info("tsc redis CACHE_HY_WHITELIST with pipeline"
											+ i
											+ " used ["
											+ (end - start)
											+ "] ms ..");
						}
						closeJedis(jedis);
					} catch (Exception e) {
						ExceptionLogger.error("quartz redis error !" , e);
					} finally {
						closeJedis(jedis);
					}
				} else {
					redisOperation.SADD(whiteListName, "");
				}
				redisOperation.expire(whiteListName, expireTimes,
						TimeUnit.SECONDS);
			}

			long whiteEndTime = System.currentTimeMillis();
			TraceLogger.info("WhiteList cost "
					+ (whiteEndTime - whiteStartTime));
		} catch (Exception e) {
			ExceptionLogger.error("quartz redis error !" , e);
		}
	}

	private void doHyBlackList(String countryNo, String toperatorId) {
		try {
			long blackStartTime = System.currentTimeMillis();
			// 黑名单表
			String blackListName = Constant.CACHE_HY_BLACKLIST + countryNo
					+ "_" + toperatorId;
			Map<String, Object> mapBlack = new HashMap<String, Object>();
			mapBlack.put("countryno", countryNo);
			mapBlack.put("toperatorid", toperatorId);
			int blackListCount = hyBlackListMapper.countByToperator(mapBlack);

			double sizeBlack = Math.ceil((double) blackListCount / pageSize);
			if (redisOperation.exists(blackListName)) {
				if (blackListCount > 0) {
					Jedis jedis = null;
					try {
				    jedis = createJedis();
					for (int i = 0; i < sizeBlack; i++) {
						mapBlack.put("startnum", (i * pageSize) + 1);
						mapBlack.put("endnum", (i + 1) * pageSize);
						List<String> hyBlackList = hyBlackListMapper
								.selectMobileNoPageByToperator(mapBlack);
						// 使用pipeline sadd 优化存储速度
						Pipeline p = jedis.pipelined();
						long start = System.currentTimeMillis();
						for (String blo : hyBlackList) {
							p.sadd(blackListName + "_new", blo.trim());
						}
						p.sync();
						long end = System.currentTimeMillis();
						TraceLogger
								.info("tsc redis CACHE_HY_BLACKLIST with pipeline"
										+ i
										+ " used ["
										+ (end - start)
										+ "] ms ..");
					}
					closeJedis(jedis);
					} catch (Exception e) {
						ExceptionLogger.error("quartz redis error !" , e);
					} finally {
						closeJedis(jedis);
					}
				} else {
					redisOperation.SADD(blackListName + "_new", "");
				}
				rename(blackListName);
			} else {
				if (blackListCount > 0) {
					Jedis jedis = null;
					try {
				    jedis = createJedis();
					for (int i = 0; i < sizeBlack; i++) {
						mapBlack.put("startnum", (i * pageSize) + 1);
						mapBlack.put("endnum", (i + 1) * pageSize);
						List<String> hyBlackList = hyBlackListMapper
								.selectMobileNoPageByToperator(mapBlack);
						// 使用pipeline sadd 优化存储速度
						Pipeline p = jedis.pipelined();
						long start = System.currentTimeMillis();
						for (String blo : hyBlackList) {
							p.sadd(blackListName, blo.trim());
						}
						p.sync();
						long end = System.currentTimeMillis();
						TraceLogger
								.info("tsc redis CACHE_HY_BLACKLIST with pipeline"
										+ i
										+ " used ["
										+ (end - start)
										+ "] ms ..");
					}
					closeJedis(jedis);
					} catch (Exception e) {
						ExceptionLogger.error("quartz redis error !" , e);
					} finally {
						closeJedis(jedis);
					}
				} else {
					redisOperation.SADD(blackListName, "");
				}
				redisOperation.expire(blackListName, expireTimes,
						TimeUnit.SECONDS);
			}
			long blackEndTime = System.currentTimeMillis();
			TraceLogger.info("BlackList cost "
					+ (blackEndTime - blackStartTime));
		} catch (Exception e) {
			ExceptionLogger.error("quartz redis error !" , e);
		}
	}

	private void doHyBlackListDevice(String countryNo, String toperatorId) {
		try {
			String blackListDeviceName = Constant.CACHE_HY_BLACKLISTDEVICE
					+ countryNo + "_" + toperatorId;
			Map<String, Object> mapbd = new HashMap<String, Object>();
			mapbd.put("countryno", countryNo);
			mapbd.put("toperatorid", toperatorId);
			List<String> hyBlackListDevice = hyBlacklistDeviceMapper
					.selectByToperator(mapbd);

			if (redisOperation.exists(blackListDeviceName)) {
				if (hyBlackListDevice.size() > 0) {
					Jedis jedis = null;
					try {
				    jedis = createJedis();
				    // 使用pipeline sadd 优化存储速度
					Pipeline p = jedis.pipelined();
					long start = System.currentTimeMillis();
					for (String device : hyBlackListDevice) {
						p.sadd(blackListDeviceName + "_new",
								device.trim());
					}
					p.sync();
					long end = System.currentTimeMillis();
					TraceLogger
							.info("tsc redis CACHE_HY_BLACKLISTDEVICE with pipeline"
									+ " used ["
									+ (end - start)
									+ "] ms ..");
					closeJedis(jedis);
					} catch (Exception e) {
						ExceptionLogger.error("quartz redis error !" , e);
					} finally {
						closeJedis(jedis);
					}
				} else {
					redisOperation.SADD(blackListDeviceName + "_new", "");
				}
				rename(blackListDeviceName);
			} else {
				if (hyBlackListDevice.size() > 0) {
					Jedis jedis = null;
					try {
				    jedis = createJedis();
				    // 使用pipeline sadd 优化存储速度
					Pipeline p = jedis.pipelined();
					long start = System.currentTimeMillis();
					for (String device : hyBlackListDevice) {
						p.sadd(blackListDeviceName, device.trim());
					}
					p.sync();
					long end = System.currentTimeMillis();
					TraceLogger
							.info("tsc redis CACHE_HY_BLACKLISTDEVICE with pipeline"
									+ " used ["
									+ (end - start)
									+ "] ms ..");
					closeJedis(jedis);
					} catch (Exception e) {
						ExceptionLogger.error("quartz redis error !" , e);
					} finally {
						closeJedis(jedis);
					}
				} else {
					redisOperation.SADD(blackListDeviceName, "");
				}
				redisOperation.expire(blackListDeviceName, expireTimes,
						TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			ExceptionLogger.error("quartz redis error !" , e);
		}
	}

	private void doHyWhiteSection(String countryNo, String toperatorId) {
		try {
			Map<String, Object> mapbd = new HashMap<String, Object>();
			mapbd.put("countryno", countryNo);
			mapbd.put("toperatorid", toperatorId);
			String whiteSectionName = Constant.CACHE_HY_WHITESECTION
					+ countryNo + "_" + toperatorId;
			List<HyWhiteSection> hyWhiteSectionList = hyWhiteSectionMapper
					.selectByToperator(mapbd);

			if (redisOperation.exists(whiteSectionName)) {
				if (hyWhiteSectionList.size() > 0) {
					Jedis jedis = null;
					try {
				    jedis = createJedis();
				    // 使用pipeline sadd 优化存储速度
					Pipeline p = jedis.pipelined();
					long start = System.currentTimeMillis();
					for (HyWhiteSection section : hyWhiteSectionList) {
						p.sadd(whiteSectionName + "_new", section
								.getMobilenostart().trim()
								+ ","
								+ section.getMobilenoend().trim());
					}
					p.sync();
					long end = System.currentTimeMillis();
					TraceLogger
							.info("tsc redis CACHE_HY_WHITESECTION with pipeline"
									+ " used ["
									+ (end - start)
									+ "] ms ..");
					closeJedis(jedis);
					} catch (Exception e) {
						ExceptionLogger.error("quartz redis error !" , e);
					} finally {
						closeJedis(jedis);
					}
				} else {
					redisOperation.SADD(whiteSectionName + "_new", "");
				}
				rename(whiteSectionName);
			} else {
				if (hyWhiteSectionList.size() > 0) {
					Jedis jedis = null;
					try {
				    jedis = createJedis();
				    // 使用pipeline sadd 优化存储速度
					Pipeline p = jedis.pipelined();
					long start = System.currentTimeMillis();
					for (HyWhiteSection section : hyWhiteSectionList) {
						p.sadd(whiteSectionName, section
								.getMobilenostart().trim()
								+ ","
								+ section.getMobilenoend().trim());
					}
					p.sync();
					long end = System.currentTimeMillis();
					TraceLogger
							.info("tsc redis CACHE_HY_WHITESECTION with pipeline"
									+ " used ["
									+ (end - start)
									+ "] ms ..");
					closeJedis(jedis);
					} catch (Exception e) {
						ExceptionLogger.error("quartz redis error !" , e);
					} finally {
						closeJedis(jedis);
					}
				} else {
					redisOperation.SADD(whiteSectionName, "");
				}
				redisOperation.expire(whiteSectionName, expireTimes,
						TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			ExceptionLogger.error("quartz redis error !" , e);
		}
	}

	private void doHyBlackSection(String countryNo, String toperatorId) {
		try {
			Map<String, Object> mapbd = new HashMap<String, Object>();
			mapbd.put("countryno", countryNo);
			mapbd.put("toperatorid", toperatorId);
			String blackSectionName = Constant.CACHE_HY_BLACKSECTION
					+ countryNo + "_" + toperatorId;
			List<HyBlackSection> hyBlackSectionList = hyBlackSectionMapper
					.selectByToperator(mapbd);

			if (redisOperation.exists(blackSectionName)) {
				if (hyBlackSectionList.size() > 0) {
					Jedis jedis = null;
					try {
				    jedis = createJedis();
				    // 使用pipeline sadd 优化存储速度
					Pipeline p = jedis.pipelined();
					long start = System.currentTimeMillis();
					for (HyBlackSection section : hyBlackSectionList) {
						p.sadd(blackSectionName + "_new", section
								.getMobilenostart().trim()
								+ ","
								+ section.getMobilenoend().trim());
					}
					p.sync();
					long end = System.currentTimeMillis();
					TraceLogger
							.info("tsc redis CACHE_HY_BLACKSECTION with pipeline"
									+ " used ["
									+ (end - start)
									+ "] ms ..");
					closeJedis(jedis);
					} catch (Exception e) {
						ExceptionLogger.error("quartz redis error !" , e);
					} finally {
						closeJedis(jedis);
					}
				} else {
					redisOperation.SADD(blackSectionName + "_new", "");
				}
				rename(blackSectionName);
			} else {
				if (hyBlackSectionList.size() > 0) {
					Jedis jedis = null;
					try {
				    jedis = createJedis();
				    // 使用pipeline sadd 优化存储速度
					Pipeline p = jedis.pipelined();
					long start = System.currentTimeMillis();
					for (HyBlackSection section : hyBlackSectionList) {
						p.sadd(blackSectionName, section
								.getMobilenostart().trim()
								+ ","
								+ section.getMobilenoend().trim());
					}
					p.sync();
					long end = System.currentTimeMillis();
					TraceLogger
							.info("tsc redis CACHE_HY_BLACKSECTION with pipeline"
									+ " used ["
									+ (end - start)
									+ "] ms ..");
					closeJedis(jedis);
					} catch (Exception e) {
						ExceptionLogger.error("quartz redis error !" , e);
					} finally {
						closeJedis(jedis);
					}
				} else {
					redisOperation.SADD(blackSectionName, "");
				}
				redisOperation.expire(blackSectionName, expireTimes,
						TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			ExceptionLogger.error("quartz redis error !" , e);
		}
	}

	private void rename(String name) {
		Jedis jedis = null;
		try {
			// 获取redis配置
			Configuration cfg = new Configuration(null,
					"tsc-main-redis.properties");
			String redisHost = cfg.getValue("tsc.main.redis.hostA");
			int redisPort = Integer.parseInt(cfg
					.getValue("tsc.main.redis.portA"));
			int redisMaxWait = Integer.parseInt(cfg
					.getValue("tsc.main.redis.maxWait"));
			long start1 = System.currentTimeMillis();
			jedis = new Jedis(redisHost, redisPort, redisMaxWait);
			Transaction tx = jedis.multi();
			tx.rename(name, name + "_bak");
			tx.rename(name + "_new", name);
			tx.del(name + "_bak");
			tx.expire(name, expireTimes);
			List<Object> results = tx.exec();
			long end1 = System.currentTimeMillis();
			TraceLogger.info("tsc redis " + name + " rename new key used ["
					+ (end1 - start1) + "] ms ..");
			jedis.disconnect();
		} catch (Exception e) {
			ExceptionLogger.error("quartz redis error !" , e);
		} finally {
			if (jedis != null && jedis.isConnected()) {
				jedis.disconnect();
			}
		}
	}

	/**
	 * 创建jedis
	 * @return
	 */
	private Jedis createJedis() {
		// 获取redis配置
		Configuration cfg = new Configuration(null, "tsc-main-redis.properties");
		String redisHost = cfg.getValue("tsc.main.redis.hostA");
		int redisPort = Integer.parseInt(cfg.getValue("tsc.main.redis.portA"));
		int redisMaxWait = Integer.parseInt(cfg
				.getValue("tsc.main.redis.maxWait"));
		Jedis jedis = new Jedis(redisHost, redisPort, redisMaxWait);
		return jedis;
	}
	
	/**
	 * 关闭jedis
	 * @param jedis
	 */
	private void closeJedis(Jedis jedis){
		if (jedis != null && jedis.isConnected()) {
			jedis.disconnect();
		}
	}




	//todo wangxiaoran 这个方法不完善   临时用
	@Override
	public void doQueryStateReload()
	{
		try
		{
			String dateStr = DateFormatUtils.format(DateUtils.addDays(new Date(),-2),"yyyyMMdd");
			List<CzReloadOrder> orderList = czReloadOrderMapper.select4QueryStateReload(dateStr+"%");
			for (CzReloadOrder czReloadOrder : orderList)
			{
				try
				{
					String orderId , transDate , totalAmount;
					orderId = czReloadOrder.getId();
					transDate = czReloadOrder.getTransdate();
					totalAmount = czReloadOrder.getTotalamount().toString();
					DefaultHttpClient httpclient = new DefaultHttpClient();
					HttpPost httpost = new HttpPost("https://onlinepayment.celcom.com.my/Payment/QueryPayment");
					String storeId = "toolbar";
					String pass = "*654DC3C253175B13003CF4858E2CF335608A4CBC";
					String signature = storeId + pass + orderId + totalAmount.replace(".", "");
					SHA1HashSignature sha1Hash = new SHA1HashSignature();
					signature = sha1Hash.generateSHA(signature);
					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					nvps.add(new BasicNameValuePair("orderId", orderId));
					nvps.add(new BasicNameValuePair("storeId", storeId));
					nvps.add(new BasicNameValuePair("signature", signature));
					nvps.add(new BasicNameValuePair("transDate", transDate));
					nvps.add(new BasicNameValuePair("totalAmount", totalAmount));
					httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
					TraceLogger.info("send queey states reload!");
					HttpResponse response = httpclient.execute(httpost);
					Map<String, String> responseParams = parseResponse(response);
					TraceLogger.info(orderId + "\t" +  transDate + "\t" + totalAmount  + "\t" +  responseParams.get("reasonCode")+ "\t" +  responseParams.get("reasonDesc") );
					CzPayResponse czPayResponse = new CzPayResponse();
					String czPayResponseId = RuleUtil.generateUUID();
					czPayResponse.setId(czPayResponseId);
					czPayResponse.setStoreid(storeId);
					czPayResponse.setOrderid(orderId);
					czPayResponse.setTransdate(transDate);
					if(!StringUtil.isNotNullOrEmpty(totalAmount)){
						totalAmount = "0";
					}
					czPayResponse.setTotalamount(new BigDecimal(totalAmount));
					czPayResponse.setMsisdn(czReloadOrder.getMsisdn());

//					czPayResponse.setSuspenddate(suspendDate);
					String returnCode = responseParams.get("returnCode");
					czPayResponse.setReturncode(returnCode);
					String reasonCode = responseParams.get("reasonCode");
					if(!StringUtil.isNotNullOrEmpty(reasonCode)){
						reasonCode = "0";
					}
					czPayResponse.setReasoncode(new BigDecimal(reasonCode));
					String reasonDesc = responseParams.get("reasonDesc");
					czPayResponse.setReasondesc(reasonDesc);
					String referId = responseParams.get("referId");
					czPayResponse.setReferid(referId);
					czPayResponse.setSignature(signature);

					czPayResponse.setModifyman("querystate|"+DateFormatUtils.format(new Date() , "yyyyMMddHHmmss"));
					czPayResponse.setModifydate(new Timestamp(new Date().getTime()));
					czPayResponseMapper.insertSelective(czPayResponse);
					String reloadStatus="2";
					if(StringUtil.isNotNullOrEmpty(returnCode)&&StringUtil.isNotNullOrEmpty(reasonCode)&&"1".equals(returnCode)&&("10000".equals(reasonCode)||"10001".equals(reasonCode)||"10002".equals(reasonCode)||"10003".equals(reasonCode)||"10004".equals(reasonCode)||"11000".equals(reasonCode)||"14000".equals(reasonCode))){
						reloadStatus="1";
					}
					CzReloadOrder czReloadOrder_new = new CzReloadOrder();
					czReloadOrder_new.setId(orderId);
					czReloadOrder_new.setStatus(reloadStatus);
					czReloadOrder_new.setModifydate(new Date());
					czReloadOrderMapper.updateByPrimaryKeySelective(czReloadOrder_new);
				} catch (IOException e)
				{
					ExceptionLogger.error(e);
				}
			}
		} catch (Exception e)
		{
			ExceptionLogger.error(e);
		}
	}


	private  Map<String, String> parseResponse(HttpResponse response) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		String responseStr = getResponseContent(response);
		//        System.out.println("repsonse for PG");
		//        System.out.println("responseStr:" + responseStr);
		List<NameValuePair> responseParams = new ArrayList<NameValuePair>();
		URLEncodedUtils.parse(responseParams, new Scanner(responseStr), "UTF-8");
		for (NameValuePair nvp : responseParams) {
			//            System.out.println("nvp:" + nvp);
			map.put(nvp.getName(), nvp.getValue());
		}
		return map;
	}
	private  String getResponseContent(HttpResponse response) throws IOException {
		String result = "";
		InputStream is = response.getEntity().getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while ((line = br.readLine()) != null) {
			result += line;
		}
		return result;
	}
}
