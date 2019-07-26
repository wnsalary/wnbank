package cn.com.pushworld.wn.bs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.infostrategy.bs.common.CommDMO;
import cn.com.infostrategy.bs.common.ServerEnvironment;
import cn.com.infostrategy.to.common.HashVO;
import cn.com.infostrategy.to.mdata.InsertSQLBuilder;
import cn.com.infostrategy.to.mdata.UpdateSQLBuilder;
import cn.com.infostrategy.to.mdata.jepfunctions.GetDateDifference;
import cn.com.infostrategy.ui.common.UIUtil;
import cn.com.pushworld.wn.to.WnUtils;
import cn.com.pushworld.wn.ui.WnSalaryServiceIfc;

public class WnSalaryServiceImpl implements WnSalaryServiceIfc {
	private CommDMO dmo = new CommDMO();
	private ImportDataDMO importDmo = new ImportDataDMO();

	/**
	 * zzl[2019-3-28] 暂不使用 柜员服务质量考核评分
	 */
	public String getSqlInsert(String time, int num) {
		System.out.println(time + "当前时间");
		String str = null;
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_gypf_table");
		List list = new ArrayList<String>();
		String[][] date = getTowWeiDate();
		try {
			HashVO[] vos = dmo
					.getHashVoArrayByDS(null,
							"select * from V_PUB_USER_POST_1 where POSTNAME like '%柜员%'");
			String d = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 6);
			Date t = cal.getTime();
			String day7 = new SimpleDateFormat("yyyy.MM.dd").format(t);
			String timezone = d + "~" + day7;
			for (int i = 0; i < vos.length; i++) {
				for (int j = 0; j < date.length; j++) {
					String id = dmo.getSequenceNextValByDS(null,
							"WN_GYPF_TABLE");
					insert.putFieldValue("id", id);
					insert.putFieldValue("username",
							vos[i].getStringValue("USERNAME"));
					insert.putFieldValue("usercode",
							vos[i].getStringValue("usercode"));
					insert.putFieldValue("userdept",
							vos[i].getStringValue("deptid"));
					insert.putFieldValue("xiangmu", date[j][0]);
					insert.putFieldValue("zhibiao", date[j][1]);
					insert.putFieldValue("fenzhi", date[j][2]);
					insert.putFieldValue("khsm", date[j][4]);
					insert.putFieldValue("pftime", time);
					insert.putFieldValue("state", "评分中");
					insert.putFieldValue("seq", j + 1);
					insert.putFieldValue("timezone", timezone);
					list.add(insert.getSQL());
				}
			}
			dmo.executeBatchByDS(null, list);
			str = "柜员服务质量考核评分开始成功";
		} catch (Exception e) {
			str = "柜员服务质量考核评分开始失败";
			e.printStackTrace();
		}
		return str;
	}

	// zpy
	@Override
	public String getSqlInsert(String time) {
		System.out.println(time + "当前时间");
		String str = null;
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_gypf_table");
		List list = new ArrayList<String>();
		String[][] date = getTowWeiDate();
		try {
			HashVO[] vos = dmo
					.getHashVoArrayByDS(null,
							"select * from V_PUB_USER_POST_1 where POSTNAME like '%柜员%'");
			String d = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 6);
			Date t = cal.getTime();
			String day7 = new SimpleDateFormat("yyyy.MM.dd").format(t);
			String timezone = d + "~" + day7;
			for (int i = 0; i < vos.length; i++) {
				for (int j = 0; j < date.length; j++) {
					String id = dmo.getSequenceNextValByDS(null,
							"WN_GYPF_TABLE");
					insert.putFieldValue("id", id);
					insert.putFieldValue("username",
							vos[i].getStringValue("USERNAME"));
					insert.putFieldValue("usercode",
							vos[i].getStringValue("usercode"));
					insert.putFieldValue("userdept",
							vos[i].getStringValue("deptid"));
					insert.putFieldValue("xiangmu", date[j][0]);
					insert.putFieldValue("zhibiao", date[j][1]);
					insert.putFieldValue("fenzhi", date[j][2]);
					insert.putFieldValue("khsm", date[j][4]);
					insert.putFieldValue("pftime", time);
					insert.putFieldValue("state", "评分中");
					insert.putFieldValue("seq", j + 1);
					insert.putFieldValue("timezone", timezone);
					if ("总分".equals(date[j][0])) {
						insert.putFieldValue("KOUOFEN", "100");
					} else {
						insert.putFieldValue("KOUOFEN", "0");
					}
					list.add(insert.getSQL());
				}
			}
			dmo.executeBatchByDS(null, list);
			str = "柜员服务质量考核评分开始成功";
		} catch (Exception e) {
			str = "柜员服务质量考核评分开始失败";
			e.printStackTrace();
		}
		return str;
	}

	public String[][] getTowWeiDate() {
		String[][] date = new String[][] {
				{ "职业形象", "着装", "5", "", "按要求着装，形象统一、规范、整洁" },
				{ "职业形象", "三姿", "5", "", "站姿、坐姿、走姿端庄大方" },
				{ "营业准备", "及时", "3", "", "准备工作要求营业前完成" },
				{
						"物件摆放归位整齐",
						"抽屉",
						"3",
						"",
						"除各种机具外，物品摆放必须做到规范、有序，不得随意放置，各种凭证、印章、印泥、印鉴册、剪刀、胶水、报表、信贷档案、现金尾箱、登记薄、私人物品等不得放置于桌面上；办理业务需要使用的，使用完毕后需放置于柜面下或抽屉中；未定位摆放、杂乱等不得分" },
				{
						"物件摆放归位整齐",
						"桌面",
						"3",
						"",
						"除各种机具外，物品摆放必须做到规范、有序，不得随意放置，各种凭证、印章、印泥、印鉴册、剪刀、胶水、报表、信贷档案、现金尾箱、登记薄、私人物品等不得放置于桌面上；办理业务需要使用的，使用完毕后需放置于柜面下或抽屉中；未定位摆放、杂乱等不得分" },
				{
						"物件摆放归位整齐",
						"钱箱",
						"3",
						"",
						"除各种机具外，物品摆放必须做到规范、有序，不得随意放置，各种凭证、印章、印泥、印鉴册、剪刀、胶水、报表、信贷档案、现金尾箱、登记薄、私人物品等不得放置于桌面上；办理业务需要使用的，使用完毕后需放置于柜面下或抽屉中；未定位摆放、杂乱等不得分" },
				{
						"物件摆放归位整齐",
						"桌牌",
						"3",
						"",
						"除各种机具外，物品摆放必须做到规范、有序，不得随意放置，各种凭证、印章、印泥、印鉴册、剪刀、胶水、报表、信贷档案、现金尾箱、登记薄、私人物品等不得放置于桌面上；办理业务需要使用的，使用完毕后需放置于柜面下或抽屉中；未定位摆放、杂乱等不得分" },
				{ "卫生保洁", "点钞机", "3", "", "有积灰、污渍、纸屑等不得分" },
				{ "卫生保洁", "计算机", "3", "", "有积灰、污渍、纸屑等不得分" },
				{ "卫生保洁", "地面", "3", "", "有积灰、污渍、纸屑等不得分" },
				{ "卫生保洁", "窗面", "3", "", "有积灰、污渍、纸屑等不得分" },
				{ "卫生保洁", "柜面", "3", "", "有积灰、污渍、纸屑等不得分" },
				{ "声音", "音量", "2", "", "适中" },
				{ "声音", "语速", "2", "", "清晰适中" },
				{ "表情", "眼神", "2", "", "交流、对视" },
				{ "表情", "微笑", "2", "", "适时微笑" },
				{ "问候迎接", "呼叫", "3", "", "举手示意" },
				{ "问候迎接", "问候", "3", "", "首问“您好”/日常称谓，请客入座" },
				{ "接待办理", "义务", "5", "",
						"做到先外后内，办理业务执行一次性回复；对非受理范围内业务主动主动告知、引导其到相关窗口办理。" },
				{ "接待办理", "语言", "10", "",
						"做到热心、诚心、耐心，自觉使用“请、您好、谢谢、对不起、再见、请问您需要办理什么业务、请稍等、请您查看计数器、请您核对后在这里签字”等文明用语" },
				{ "接待办理", "动作", "5", "", "双手递接、掌心朝上" },
				{ "接待办理", "动作", "5", "",
						"适时主动与客户交流我社销售产品内容，介绍我社业务，创造销售机会，或请客户协助完成服务满意度评价。" },
				{ "送别结束", "语言", "3", "", "请问您还需要办理其他业务吗？/请慢走，再见" },
				{ "其他情况", "1.办业务期间不大声闲谈", "1", "", "这些情况在发生时不得分" },
				{ "其他情况", "2.指引动作手掌朝上", "1", "", "这些情况在发生时不得分" },
				{ "其他情况", "3.间断业务时未向顾客做请示或表歉意", "1", "", "这些情况在发生时不得分" },
				{ "其他情况", "4.手机未设震动", "1", "", "这些情况在发生时不得分" },
				{ "其他情况", "5.暂离岗位时椅子未归位", "1", "", "这些情况在发生时不得分" },
				{ "其他情况", "6.不必要的肢体动作过多", "1", "", "这些情况在发生时不得分" },
				{ "其他情况", "7.暂停业务时未放置示意牌", "1", "", "这些情况在发生时不得分" },
				{ "其他情况", "8.长时间业务未主动安抚客户情绪", "1", "", "这些情况在发生时不得分" },
				{ "其他情况", "9.有其他被投诉情况", "5", "", "这些情况在发生时不得分" },
				{ "营业结束", "卫生保洁", "5", "", "这些情况在发生时不得分" },
				{ "总分", "", "", "", "" } };
		return date;
	}

	/**
	 * 部门考核计划生成 planid:计划id
	 */
	@Override
	public String getBMsql(String planid) {
		String str = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_BMPF_table");
			HashVO[] vos = dmo
					.getHashVoArrayByDS(
							null,
							"select * from sal_target_list where type='部门定性指标' AND (name LIKE '文明客户服务部%' OR name LIKE '党建工作%' OR name LIKE '内控合规%' OR name LIKE '安全保卫%')");
			HashMap deptMap = getdeptName();
			InsertSQLBuilder insertSQLBuilder = new InsertSQLBuilder(
					"wn_BMPF_table");
			List<String> list = new ArrayList<String>();
			List<String> wmsumList = new ArrayList<String>();
			List<String> djsumList = new ArrayList<String>();
			List<String> nksumList = new ArrayList<String>();
			List<String> aqsumList = new ArrayList<String>();
			// 对批复时间进行修改
			String[] date = dmo.getStringArrayFirstColByDS(null,
					"select PFTIME from WN_BMPF_TABLE where state='评分结束'");
			String pftime = "";
			if (date == null || date.length == 0) {
				int month = Integer.parseInt(new SimpleDateFormat("MM")
						.format(new Date()));
				int currentQuarter = getQuarter2(month);// 根据当前时间获取到当前季度
				pftime = new SimpleDateFormat("yyyy").format(new Date()) + "-"
						+ getQuarterEnd(currentQuarter);// 获取到当前季度最后一天
			} else {
				String maxTime = dmo
						.getStringValueByDS(null,
								"SELECT max(PFTIME) PFTINE FROM WN_BMPF_TABLE WHERE STATE='评分结束'");
				int year = Integer.parseInt(new SimpleDateFormat("yyyy")
						.format(new Date()));// 获取到当前年
				int nextQuarter = getQuarter(maxTime) + 1;
				if (nextQuarter >= 5) {
					nextQuarter = 1;
					year = year + 1;
				}
				pftime = String.valueOf(year) + "-"
						+ getQuarterEnd(nextQuarter);
			}
			for (int i = 0; i < vos.length; i++) {// 每一项指标
				String deptid = vos[i].getStringValue("checkeddept");// 获取到被考核部门的机构号
				deptid = deptid.replaceAll(";", ",").substring(1,
						deptid.length() - 1);
				String[] deptcodes = deptid.split(",");
				String xiangmu = vos[i].getStringValue("name");// 项目名称
				String evalstandard = vos[i].getStringValue("evalstandard");// 项目扣分描述
				String weights = vos[i].getStringValue("weights");// 项目权重
				String koufen = "0.0";// 扣分情况(默认是0.0)
				String state = "评分中";// 当前项的评分状态:评分中
				// String pftime = new SimpleDateFormat("yyyy-MM-dd").format(new
				// Date());//评分日期
				// 为每个部门生成每一项考核
				for (int j = 0; j < deptcodes.length; j++) {
					if ("964".equals(deptcodes[j])
							|| "965".equals(deptcodes[j])) {
						continue;
					}
					// 每一项考核指标都会为每一个考核部门生一个考核计划
					String deptName = deptMap.get(deptcodes[j]).toString();// 获取到机构名称
					insert.putFieldValue("PLANID", planid);
					insert.putFieldValue("deptcode", deptcodes[j]);
					insert.putFieldValue("deptname", deptName);
					insert.putFieldValue("xiangmu", xiangmu);
					insert.putFieldValue("evalstandard", evalstandard);
					insert.putFieldValue("fenzhi", weights);
					insert.putFieldValue("koufen", koufen);
					insert.putFieldValue("state", state);
					insert.putFieldValue("pftime", pftime);
					insert.putFieldValue("id",
							dmo.getSequenceNextValByDS(null, "S_WN_BMPF_TABLE"));
					list.add(insert.getSQL());
					// 为每一个部门的每一个大项生成总分
					String name = xiangmu.substring(0, xiangmu.indexOf("-"));
					if ("文明客户服务部".equals(name)) {
						if (!wmsumList.contains(deptcodes[j])) {
							insert.putFieldValue("PLANID", planid);
							insert.putFieldValue("deptcode", deptcodes[j]);
							insert.putFieldValue("xiangmu", name);
							insert.putFieldValue("koufen", "100.0");
							insert.putFieldValue("state", state);
							insert.putFieldValue("pftime", pftime);
							insert.putFieldValue("fenzhi", "");
							insert.putFieldValue("evalstandard", "");
							insert.putFieldValue("id", dmo
									.getSequenceNextValByDS(null,
											"S_WN_BMPF_TABLE"));
							wmsumList.add(deptcodes[j]);
							list.add(insert.getSQL());
						}
					} else if ("党建工作".equals(name)) {
						if (!djsumList.contains(deptcodes[j])) {
							insert.putFieldValue("PLANID", planid);
							insert.putFieldValue("deptcode", deptcodes[j]);
							insert.putFieldValue("xiangmu", name);
							insert.putFieldValue("koufen", "100.0");
							insert.putFieldValue("fenzhi", "");
							insert.putFieldValue("state", state);
							insert.putFieldValue("pftime", pftime);
							insert.putFieldValue("evalstandard", "");
							insert.putFieldValue("id", dmo
									.getSequenceNextValByDS(null,
											"S_WN_BMPF_TABLE"));
							list.add(insert.getSQL());
							djsumList.add(deptcodes[j]);
						}
					} else if ("安全保卫".equals(name)) {
						if (!aqsumList.contains(deptcodes[j])) {
							insert.putFieldValue("PLANID", planid);
							insert.putFieldValue("deptcode", deptcodes[j]);
							insert.putFieldValue("xiangmu", name);
							insert.putFieldValue("koufen", "100.0");
							insert.putFieldValue("state", state);
							insert.putFieldValue("pftime", pftime);
							insert.putFieldValue("fenzhi", "");
							insert.putFieldValue("evalstandard", "");
							insert.putFieldValue("id", dmo
									.getSequenceNextValByDS(null,
											"S_WN_BMPF_TABLE"));
							list.add(insert.getSQL());
							aqsumList.add(deptcodes[j]);
						}
					} else if ("内控合规".equals(name)) {
						if (!nksumList.contains(deptcodes[j])) {
							insert.putFieldValue("PLANID", planid);
							insert.putFieldValue("deptcode", deptcodes[j]);
							insert.putFieldValue("xiangmu", name);
							insert.putFieldValue("koufen", "100.0");
							insert.putFieldValue("state", state);
							insert.putFieldValue("fenzhi", "");
							insert.putFieldValue("pftime", pftime);
							insert.putFieldValue("evalstandard", "");
							insert.putFieldValue("id", dmo
									.getSequenceNextValByDS(null,
											"S_WN_BMPF_TABLE"));
							list.add(insert.getSQL());
							nksumList.add(deptcodes[j]);
						}
					}
				}
			}
			dmo.executeBatchByDS(null, list);
			list.clear();
			str = "部门考核计划生成成功";
		} catch (Exception e) {
			e.printStackTrace();
			str = "部门考核计划生成成功";
		}
		return str;
	}

	// 获取到部门编号
	public HashMap getdeptName() {
		HashMap hash = null;
		try {
			hash = dmo.getHashMapBySQLByDS(null,
					"SELECT id,NAME FROM pub_corp_dept");
		} catch (Exception e) {
			hash = new HashMap();
			e.printStackTrace();
		}
		return hash;
	}

	@Override
	public String gradeBMScoreEnd() {// 结束打分计划 算总分 改状态
		try {
			String[] csName = dmo
					.getStringArrayFirstColByDS(null,
							"SELECT DISTINCT(xiangmu) FROM WN_BMPF_TABLE WHERE fenzhi IS NULL");
			HashMap codeVos = dmo
					.getHashMapBySQLByDS(
							null,
							"SELECT DISTINCT(SUBSTR(name,0,INSTR(NAME,'-')-1)) name,CHECKEDDEPT FROM sal_target_list  WHERE TYPE ='部门定性指标'");
			UpdateSQLBuilder update = new UpdateSQLBuilder("WN_BMPF_TABLE");
			UpdateSQLBuilder update2 = new UpdateSQLBuilder("WN_BMPF_TABLE");
			List<String> list = new ArrayList<String>();

			for (int i = 0; i < csName.length; i++) {// 遍历每一个评分大项（文明 党建 案防 安全）
				String deptcodes = codeVos.get(csName[i]).toString();
				deptcodes = deptcodes.substring(1, deptcodes.lastIndexOf(";"));
				String[] codes = deptcodes.split(";");// 机构号
				for (int j = 0; j < codes.length; j++) {// 每个大项每个机构每个小项
														// 修改状态，计算总分
					if ("964".equals(codes[j]) || "965".equals(codes[j])) {
						continue;
					}
					String sql = "select * from WN_BMPF_TABLE where xiangmu like '"
							+ csName[i]
							+ "%' and deptcode='"
							+ codes[j]
							+ "' order by fenzhi";
					HashVO[] vos = dmo.getHashVoArrayByDS(null, sql);
					double result = 0.0;
					String KOUFEN = "";
					String FENZHI = "";
					for (int k = 0; k < vos.length; k++) {
						if (csName[i].equals(vos[k].getStringValue("xiangmu"))) {
							continue;
						}
						FENZHI = vos[i].getStringValue("fenzhi");
						KOUFEN = vos[i].getStringValue("koufen");
						if (KOUFEN == null || KOUFEN.isEmpty()
								|| "".equals(KOUFEN)) {
							KOUFEN = "0.0";
						}
						if (Double.parseDouble(KOUFEN) > Double
								.parseDouble(FENZHI)) {
							KOUFEN = FENZHI;
						}
						result = result + Double.parseDouble(KOUFEN);
						update.setWhereCondition("1=1 and deptcode='"
								+ codes[j] + "' and xiangmu like '" + csName[i]
								+ "%' and state='评分中'");
						update.putFieldValue("state", "评分结束");
						update.putFieldValue("KOUFEN", KOUFEN);
						list.add(update.getSQL());
					}
					update2.setWhereCondition("1=1 and deptcode='" + codes[j]
							+ "' and xiangmu='" + csName[i]
							+ "' and fenzhi is null");
					update2.putFieldValue("koufen", 100.0 - result);
					list.add(update2.getSQL());
					dmo.executeBatchByDS(null, list);
					list.clear();
				}
			}
			UpdateSQLBuilder update3 = new UpdateSQLBuilder("WN_BMPFPLAN");
			update3.setWhereCondition("1=1 and state='评分中'");
			update3.putFieldValue("state", "评分结束");
			dmo.executeUpdateByDS(null, update3.getSQL());
			return "当前计划结束成功";
		} catch (Exception e) {
			e.printStackTrace();
			return "当前计划结束失败";
		}

	}

	public String getQuarterEnd(int num) {
		switch (num) {
		case 1:
			return "03-31";
		case 2:
			return "06-30";
		case 3:
			return "09-30";
		case 4:
			return "12-31";
		default:
			return "12-31";
		}
	}

	// date格式:2019-01-01
	public int getQuarter(String date) {// 根据date获取到当前季度
		date = date.substring(5);
		if ("03-31".equals(date)) {
			return 1;
		} else if ("06-30".equals(date)) {
			return 2;
		} else if ("09-30".equals(date)) {
			return 3;
		} else if ("12-31".equals(date)) {
			return 4;
		} else {// 处理一切输入不合理的情况
			return 4;
		}

	}

	public int getQuarter2(int month) {
		switch (month) {
		case 1:
		case 2:
		case 3:
			return 1;
		case 4:
		case 5:
		case 6:
			return 2;
		case 7:
		case 8:
		case 9:
			return 3;
		case 10:
		case 11:
		case 12:
			return 4;
		default:
			return 4;
		}
	}

	/**
	 * 全量导入数据
	 */
	@Override
	public String ImportAll() {// 全量数据导入
		return importDmo.ImportAll();
	}

	/**
	 * 导入一天的数据
	 */
	@Override
	public String ImportDay(String date) {

		return importDmo.ImportDay(date);
	}

	@Override
	public String ImportOne(String filePath) {
		return importDmo.importOne(filePath);
	}

	@Override
	/**
	 * zzl[2019-4-28]
	 * 每个月的客户经理对应的贷款需要调整，故需要修改上月的基数。
	 */
	public String getChange(String date1, String date2) {
		String xx = null;
		Date date = new Date();
		Calendar scal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历。
		scal.setTime(date);
		scal.add(Calendar.MONTH, -2);// 取当前日期的后一天.
		scal.set(Calendar.DAY_OF_MONTH, scal.getActualMaximum(Calendar.DATE));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String smonth = df.format(scal.getTime());// 上月的月数
		Calendar cal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历。
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);// 取当前日期的后一天.
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		String kmonth = df.format(cal.getTime());// 考核月的月数
		StringBuffer sb = new StringBuffer();
		StringBuffer sqlsb = new StringBuffer();
		try {
			UpdateSQLBuilder update = new UpdateSQLBuilder("wnbank.s_loan_dk");
			List list = new ArrayList<String>();
			// 客户经理的信息表map
			HashMap<String, String> map = dmo.getHashMapBySQLByDS(null,
					"select xd_col1,xd_col2 from wnbank.s_loan_ryb");
			// 考核月的客户证件和客户经理号map
			HashMap<String, String> kmap = dmo
					.getHashMapBySQLByDS(
							null,
							"select distinct(dk.xd_col1),dk.XD_COL81 from wnbank.s_loan_dk dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date1 + "'");
			// 上月的客户证件和客户经理号map
			HashMap<String, String> smap = dmo
					.getHashMapBySQLByDS(
							null,
							"select distinct(dk.xd_col1),dk.XD_COL81 from wnbank.s_loan_dk dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date2 + "'");
			int a = 0;
			for (String str : kmap.keySet()) {
				if (kmap.get(str).equals(smap.get(str))) {
					continue;
				} else {
					a = a + 1;
					update.setWhereCondition("to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
							+ date2 + "' and xd_col1='" + str + "'");
					update.putFieldValue("XD_COL81", kmap.get(str));
					sb.append(smonth + "贷款号为[" + str + "]客户经理为["
							+ map.get(smap.get(str))
							+ "]与考核月的客户经理信息不符，故修改客户经理为["
							+ map.get(kmap.get(str)) + "]"
							+ System.getProperty("line.separator"));
					list.add(update.getSQL());
					if (a > 5000) {
						txtWriteFile(sb, "DK");
						sb.delete(0, sb.length());
						a = 0;
					}
				}
				if (list.size() > 5000) {// zzl 1000 一提交
					dmo.executeBatchByDS(null, list);
					for (int i = 0; i < list.size(); i++) {
						sqlsb.append(list.get(i).toString()
								+ System.getProperty("line.separator"));
					}
					txtWriteFile(sqlsb, "DK_SQL");
					sqlsb.delete(0, sqlsb.length());
					list.clear();
				}
			}
			if (list.size() > 0) {
				dmo.executeBatchByDS(null, list);
				for (int i = 0; i < list.size(); i++) {
					sqlsb.append(list.get(i).toString()
							+ System.getProperty("line.separator"));
				}
				txtWriteFile(sqlsb, "DK_SQL");
				sqlsb.delete(0, sqlsb.length());
				list.clear();
			}
			if (sb.length() > 0) {
				txtWriteFile(sb, "DK");
			}
			xx = "客户经理信息变更成功";
		} catch (Exception e) {
			xx = "客户经理信息变更失败";
			e.printStackTrace();
		}
		return xx;
	}

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		try {
			cal.setTime(format.parse("2019-07-11"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.add(Calendar.YEAR, -1);
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		System.out
				.println(">>>>>>.>" + dateFormat.format(otherDate) + "-12-31");
		// /* 写入Txt文件 */
		// try{
		// Date date = new Date();
		// SimpleDateFormat dfMonth = new SimpleDateFormat("yyyyMM");
		// SimpleDateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
		// String filePath = System.getProperty("WLTUPLOADFILEDIR");
		// filePath=filePath+"\\"+dfMonth+"\\"+dfDay;
		// File writepath = new
		// File("C:\\Users\\longlonggo521\\Desktop\\"+dfMonth.format(date).toString()+"\\"+dfDay.format(date).toString());
		// // 相对路径，如果没有则要建立一个新的output。txt文件
		// if(!writepath.exists()){
		// writepath.mkdirs();
		// }
		// File writename = new
		// File("C:\\Users\\longlonggo521\\Desktop\\"+dfMonth.format(date).toString()+"\\"+dfDay.format(date).toString()+"\\niubi.txt");
		// // 相对路径，如果没有则要建立一个新的output。txt文件
		// if(!writename.exists()){
		// writename.createNewFile(); // 创建新文件
		// }
		// BufferedWriter out = new BufferedWriter(new
		// FileWriter(writename,true));
		// out.write("好好学习"); // \r\n即为换行
		// out.flush(); // 把缓存区内容压入文件
		// out.close(); // 最后记得关闭文件
		// }catch (Exception e){
		// e.printStackTrace();
		// }
	}

	/**
	 * zzl 把导出的日志写入txt
	 */
	public void txtWriteFile(StringBuffer sb, String name) {
		try {
			Date date = new Date();
			SimpleDateFormat dfMonth = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			String filePath = ServerEnvironment.getProperty("WLTUPLOADFILEDIR");
			filePath = filePath + "\\ManagerLog\\"
					+ dfMonth.format(date).toString() + "\\"
					+ dfDay.format(date).toString();
			File writepath = new File(filePath); // 相对路径，如果没有则要建立一个新的output。txt文件
			if (!writepath.exists()) {
				writepath.mkdirs();
			}
			File writename = new File(filePath + "\\" + name + "_"
					+ dfDay.format(date).toString() + ".txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
			if (!writename.exists()) {
				writename.createNewFile(); // 创建新文件
			}
			BufferedWriter out = new BufferedWriter(new FileWriter(writename,
					true));
			out.write(sb.toString()); // \r\n即为换行
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * zzl
	 * 
	 * @param date
	 * @return 返回上月时间
	 */
	public String getSMonthDate(String date) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		try {
			cal.setTime(format.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(otherDate);
	}

	/**
	 * zzl
	 * 
	 * @param date
	 * @return 返回年初年末时间
	 */
	public String getYearDate(String date) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		try {
			cal.setTime(format.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.add(Calendar.YEAR, -1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		return dateFormat.format(otherDate) + "-12-31";
	}

	/**
	 * zzl[存款客户经理信息变更]
	 * 
	 */
	@Override
	public String getCKChange(String date1, String date2) {
		String xx = null;
		Date date = new Date();
		Calendar scal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历。
		scal.setTime(date);
		scal.add(Calendar.MONTH, -2);// 取当前日期的后一天.
		scal.set(Calendar.DAY_OF_MONTH, scal.getActualMaximum(Calendar.DATE));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String smonth = df.format(scal.getTime());// 上月的月数
		Calendar cal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历。
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);// 取当前日期的后一天.
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		String kmonth = df.format(cal.getTime());// 考核月的月数
		StringBuffer sb = new StringBuffer();
		StringBuffer sqlsb = new StringBuffer();
		try {
			UpdateSQLBuilder update = new UpdateSQLBuilder("wnbank.s_loan_khxx");
			List list = new ArrayList<String>();
			// 客户经理的信息表map
			HashMap<String, String> map = dmo.getHashMapBySQLByDS(null,
					"select xd_col1,xd_col2 from wnbank.s_loan_ryb");
			// 考核月的客户证件和客户经理号map
			HashMap<String, String> kmap = dmo
					.getHashMapBySQLByDS(
							null,
							"select XD_COL1,XD_COL96 from wnbank.S_LOAN_KHXX where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date1
									+ "' and XD_COL7 is not null and XD_COL96 is not null");
			// 上月的客户证件和客户经理号map
			HashMap<String, String> smap = dmo
					.getHashMapBySQLByDS(
							null,
							"select XD_COL1,XD_COL96 from wnbank.S_LOAN_KHXX where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date2
									+ "' and XD_COL7 is not null and XD_COL96 is not null");
			int a = 0;
			for (String str : kmap.keySet()) {
				if (kmap.get(str).equals(smap.get(str))) {
					continue;
				} else {
					a = a + 1;
					update.setWhereCondition("to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
							+ date2 + "' and xd_col1='" + str + "'");
					update.putFieldValue("XD_COL81", kmap.get(str));
					list.add(update.getSQL());
					sb.append("2018-12-31客户号为[" + str + "]客户经理为["
							+ map.get(smap.get(str))
							+ "]与考核月的客户经理信息不符，故修改客户经理为["
							+ map.get(kmap.get(str)) + "]  \n");
					if (a > 5000) {
						txtWriteFile(sb, "CK");
						sb.delete(0, sb.length());
						a = 0;
					}
				}
				if (list.size() > 5000) {// zzl 1000 一提交
					dmo.executeBatchByDS(null, list);
					for (int i = 0; i < list.size(); i++) {
						sqlsb.append(list.get(i).toString()
								+ System.getProperty("line.separator"));
					}
					txtWriteFile(sqlsb, "CK_SQL");
					sqlsb.delete(0, sqlsb.length());
					list.clear();
				}
			}
			if (list.size() > 0) {
				dmo.executeBatchByDS(null, list);
				for (int i = 0; i < list.size(); i++) {
					sqlsb.append(list.get(i).toString()
							+ System.getProperty("line.separator"));
				}
				txtWriteFile(sqlsb, "CK_SQL");
				sqlsb.delete(0, sqlsb.length());
				list.clear();
			}
			if (sb.length() > 0) {
				txtWriteFile(sb, "CK");
			}
			xx = "客户经理信息变更成功";
		} catch (Exception e) {
			xx = "客户经理信息变更失败";
			e.printStackTrace();
		}
		return xx;
	}

	/**
	 * zpy[2019-05-22] 为每个柜员生成定性考核计划
	 */
	@Override
	public String gradeDXscore(String id) {
		String result = "";
		String planName = "";
		try {
			// 获取到柜员相关信息
			HashVO[] vos = dmo
					.getHashVoArrayByDS(null,
							"select * from V_PUB_USER_POST_1 where POSTNAME like '%柜员%'");
			// 获取到柜员定性考核指标
			HashVO[] dxzbVos = dmo
					.getHashVoArrayByDS(
							null,
							"select * from sal_person_check_list where 1=1  and (type='43')  and (1=1)  order by seq");
			List<String> _sqlList = new ArrayList<String>();
			String sql = "";
			String maxTime = dmo.getStringValueByDS(null,
					"select max(plantime) from WN_GYDXPLAN");
			planName = dmo.getStringValueByDS(null,
					"select planname from WN_GYDXPLAN where plantime='"
							+ maxTime + "'");
			String pftime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
					.format(new Date());
			for (int i = 0; i < vos.length; i++) {
				String gyName = vos[i].getStringValue("username");// 获取到柜员名称
				String gyDeptCode = vos[i].getStringValue("deptcode");// 获取到当前用户的机构号
				String gyUserCode = vos[i].getStringValue("usercode");// 获取到当前柜员的柜员号
				for (int j = 0; j < dxzbVos.length; j++) {
					String xiangmu = dxzbVos[j].getStringValue("CATALOG");// 考核项目
					String khsm = dxzbVos[j].getStringValue("name");// 考核说明
					String fenzhi = dxzbVos[j].getStringValue("weights");

					sql = "insert into wn_gydx_table(id,username,userdept,usercode,xiangmu,khsm,fenzhi,koufen,state,pftime)"
							+ "  values('"
							+ id
							+ "','"
							+ gyName
							+ "','"
							+ gyDeptCode
							+ "','"
							+ gyUserCode
							+ "','"
							+ xiangmu
							+ "','"
							+ khsm
							+ "','"
							+ fenzhi
							+ "','0.0','评分中','"
							+ pftime + "')";
					_sqlList.add(sql);
				}
				sql = "insert into wn_gydx_table(id,username,userdept,usercode,xiangmu,khsm,fenzhi,state,pftime) values('"
						+ id
						+ "','"
						+ gyName
						+ "','"
						+ gyDeptCode
						+ "','"
						+ gyUserCode
						+ "','总分','总分','0.0','评分中','"
						+ pftime
						+ "')";
				_sqlList.add(sql);
				if (_sqlList.size() == 5000) {
					dmo.executeBatchByDS(null, _sqlList);
					_sqlList.clear();
				}
			}
			dmo.executeBatchByDS(null, _sqlList);
			sql = "update WN_GYDXPLAN set state='评分中' where id='" + id + "'";
			dmo.executeUpdateByDS(null, sql);
			result = "当前考核计划【" + planName + "】创建成功";
		} catch (Exception e) {
			result = "当前考核计划【" + planName + "】创建失败,请检查";
			e.printStackTrace();
		} finally {
			return result;
		}
	}

	/**
	 * 整个定性考核的分数发生变化
	 */
	@Override
	public String gradeDXEnd(String planid) {// 结束当前考核计划:1.计算分值;2.修改状态;
		String result = "";
		try {
			// 获取到当前所有柜员
			HashVO[] vos = dmo
					.getHashVoArrayByDS(null,
							"select * from V_PUB_USER_POST_1 where POSTNAME like '%柜员%'");
			// HashMap<String,String> gyMap = dmo.getHashMapBySQLByDS(null,
			// "select usercode,deptcode from V_PUB_USER_POST_1 where POSTNAME like '%柜员%'");
			// 需要提示客户，是否需要进行结束
			HashVO[] hvos = dmo
					.getHashVoArrayByDS(
							null,
							"SELECT * FROM wn_gydx_table WHERE STATE='评分中' OR FHRESULT IS NULL OR FHRESULT='复核未通过'");
			if (hvos.length > 0) {// 在评分未完成的情况下结束
				double koufen = 0.0;
				double fenzhi = 0.0;
				String xiangmu = "";
				String khsm = "";
				UpdateSQLBuilder update = new UpdateSQLBuilder("wn_gydx_table");
				UpdateSQLBuilder update2 = new UpdateSQLBuilder("wn_gydx_table");
				List<String> sqlList = new ArrayList<String>();
				for (int i = 0; i < vos.length; i++) {// 按照柜员进行遍历
					double sum = 100.0;
					String maxTime = dmo.getStringValueByDS(null,
							"select max(pftime) from wn_gydx_table where usercode='"
									+ vos[i].getStringValue("usercode") + "'");
					String sql = "select * from  wn_gydx_table where usercode='"
							+ vos[i].getStringValue("usercode")
							+ "' and (state='评分中' or fhresult is null  or fhresult='复核未通过') and pftime='"
							+ maxTime + "'";
					HashVO[] gyVos = dmo.getHashVoArrayByDS(null, sql);
					if (gyVos.length <= 0) {
						continue;
					}

					for (int j = 0; j < gyVos.length; j++) {// 柜员定性得分结束
						xiangmu = gyVos[j].getStringValue("xiangmu");
						khsm = gyVos[j].getStringValue("khsm");
						if ("总分".equals(xiangmu)) {
							continue;
						}
						fenzhi = Double.parseDouble(gyVos[j]
								.getStringValue("fenzhi"));
						// if(gyVos[j].getStringValue("koufen")==null ||
						// gyVos[j].getStringValue("koufen").isEmpty()){
						// koufen=fenzhi;
						// }
						koufen = fenzhi;
						sum -= koufen;
						update.setWhereCondition("usercode='"
								+ vos[i].getStringValue("usercode")
								+ "' and khsm='"
								+ khsm
								+ "' and (state='评分中' or fhresult is null  or fhresult='复核未通过') ");
						update.putFieldValue("koufen", koufen);
						update.putFieldValue("state", "评分结束");
						sqlList.add(update.getSQL());
					}
					update2.setWhereCondition("usercode='"
							+ vos[i].getStringValue("usercode")
							+ "' and khsm='总分' and (state='评分中' or fhresult is null  or fhresult='复核未通过')");
					update2.putFieldValue("fenzhi", sum);
					update2.putFieldValue("koufen", "");
					update2.putFieldValue("state", "评分结束");
					sqlList.add(update2.getSQL());
					if (sqlList.size() >= 5000) {
						dmo.executeBatchByDS(null, sqlList);
						sqlList.clear();
					}
				}
				dmo.executeBatchByDS(null, sqlList);
				result = "该项打分计划结束成功";
			} else {// 直接结束
				result = "该项打分计划结束成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}

	// 修改当前考核人当前考核项的评分(未评分的状态下)
	public void updatePF(String usercode, String khsm, double fenzhi,
			String pfusercode) {
		try {
			String sql = "update  wn_gydx_table set pfusercode='" + pfusercode
					+ "',koufen='" + fenzhi + ",state='评分结束'' where usercode='"
					+ usercode + "' and state='评分中' and khsm='" + khsm + "'";
			dmo.executeUpdateByDS(null, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 未复核的状态下修改评分
	public void updateFH(String usercode, String khsm, double fenzhi,
			String fhusercode) {
		try {
			String sql = "update  wn_gydx_table set fhusercode='" + fhusercode
					+ "',koufen='" + fenzhi + ",state='评分结束'' where usercode='"
					+ usercode + "' and state='评分中' and khsm='" + khsm + "'";
			dmo.executeUpdateByDS(null, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String gradeManagerDXscore(String id) {
		String result = "";
		try {
			// 获取到考核月时间
			SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
			SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
			int month = Integer.parseInt(monthFormat.format(new Date()));
			int year = Integer.parseInt(yearFormat.format(new Date()));
			String lastMonth = "";
			if (month == 1) {
				month = 12;
				year = year - 1;
			} else {
				month = month - 1;
			}
			String monthStr = String.valueOf(month);
			lastMonth = year + "-"
					+ (monthStr.length() == 1 ? "0" + monthStr : monthStr);
			// 获取到客户经理的信息
			HashVO[] managerVos = dmo
					.getHashVoArrayByDS(null,
							"SELECT * FROM V_PUB_USER_POST_1 WHERE POSTNAME LIKE '%客户经理%'");
			// 获取到客户经理定性考核计划
			HashVO[] vos = dmo
					.getHashVoArrayByDS(
							null,
							"select * from sal_person_check_list where 1=1  and (type='61')  and (1=1)  order by seq");
			List<String> _sqlList = new ArrayList<String>();
			for (int i = 0; i < managerVos.length; i++) {
				String username = managerVos[i].getStringValue("username");
				String usercode = managerVos[i].getStringValue("usercode");
				String deptcode = managerVos[i].getStringValue("deptcode");
				InsertSQLBuilder insert = new InsertSQLBuilder(
						"wn_managerdx_table");
				for (int j = 0; j < vos.length; j++) {// 获取到每一项考核计划指标
					String weight = vos[j].getStringValue("WEIGHTS");// 获取到指标的考核权重
					String khsm = vos[j].getStringValue("name");
					String xiangmu = vos[j].getStringValue("CATALOG");
					double koufen = 0.0;
					insert.putFieldValue("planid", id);
					insert.putFieldValue("username", username);
					insert.putFieldValue("usercode", usercode);
					insert.putFieldValue("userdept", deptcode);
					insert.putFieldValue("xiangmu", xiangmu);
					insert.putFieldValue("khsm", khsm);
					insert.putFieldValue("fenzhi", weight);
					insert.putFieldValue("koufen", koufen);
					insert.putFieldValue("state", "评分中");
					insert.putFieldValue("pftime", new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss").format(new Date()));
					insert.putFieldValue("khMonth", lastMonth);
					_sqlList.add(insert.getSQL());
				}
				InsertSQLBuilder insert2 = new InsertSQLBuilder(
						"wn_managerdx_table");
				insert2.putFieldValue("planid", id);
				insert2.putFieldValue("username", username);
				insert2.putFieldValue("usercode", usercode);
				insert2.putFieldValue("userdept", deptcode);
				insert2.putFieldValue("fenzhi", 100.0);
				insert2.putFieldValue("state", "评分中");
				insert2.putFieldValue("xiangmu", "总分");
				insert2.putFieldValue("pftime", new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss").format(new Date()));
				insert2.putFieldValue("khsm", "总分");
				insert2.putFieldValue("khMonth", lastMonth);
				_sqlList.add(insert2.getSQL());
			}
			dmo.executeBatchByDS(null, _sqlList);
			result = "当前考核计划生成成功";
		} catch (Exception e) {
			result = "当前考核计划常见失败";
			e.printStackTrace();
		} finally {
			return result;
		}

	}

	/**
	 * 客户经理结束考核计划
	 */
	@Override
	public String endManagerDXscore(String id) {
		String result = "";
		try {
			// 查询所有未结束评分的客户经理
			HashVO[] vos = dmo.getHashVoArrayByDS(null,
					"select * from wn_managerdx_table where  state='评分中'");
			if (vos.length <= 0) {
				result = "当前考核计划结束成功";
			} else {// 客户要求，当主任未完成对客户经理的考核时，保留对客户经理的评分
				UpdateSQLBuilder update = new UpdateSQLBuilder(
						"wn_managerdx_table");
				List<String> _sqllist = new ArrayList<String>();
				Map<String, Double> map = new HashMap<String, Double>();
				for (int i = 0; i < vos.length; i++) {
					String xiangmu = vos[i].getStringValue("xiangmu");
					String fenzhi = vos[i].getStringValue("fenzhi");
					String khsm = vos[i].getStringValue("khsm");
					String usercode = vos[i].getStringValue("usercode");
					String koufen = vos[i].getStringValue("koufen");
					// if(koufen==null || "".isEmpty() ||
					// Double.parseDouble(fenzhi)<Double.parseDouble(koufen)){
					// koufen=fenzhi;
					// }
					if (koufen == null || koufen.isEmpty()) {
						koufen = "0.0";
					} else if (Double.parseDouble(fenzhi) < Double
							.parseDouble(koufen)) {
						koufen = fenzhi;
					}
					// 计算每一位客户经理的总分
					if (!map.containsKey(usercode)) {
						map.put(usercode, Double.parseDouble(koufen));
					} else {
						map.put(usercode,
								map.get(usercode) + Double.parseDouble(koufen));
					}
					update.setWhereCondition("usercode='" + usercode
							+ "' and khsm='" + khsm + "' and state='评分中'");
					update.putFieldValue("koufen", koufen);
					update.putFieldValue("state", "评分结束");
					_sqllist.add(update.getSQL());
				}

				Set<String> userCodeSet = map.keySet();
				for (String usercode : userCodeSet) {
					String sql = "update wn_managerdx_table set fenzhi='"
							+ map.get(usercode)
							+ "' where state='评分中'  and usercode='" + usercode
							+ "' and xiangmu='总分'";
					_sqllist.add(sql);
				}
				dmo.executeBatchByDS(null, _sqllist);
				result = "当前考核计划结束成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
		}

	}

	/**
	 * zzl 贷款管户数比
	 */
	@Override
	public String getDKFinishB(String date) {
		String jv = null;
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_loans_wcb");
		List list = new ArrayList<String>();
		try {
			// 得到客户经理的Map
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			// 得到客户经理的任务数
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(
					null,
					"select A,sum(D) from EXCEL_TAB_53 where year='"
							+ date.substring(0, 4) + "' group by A");
			if (rwMap.size() == 0) {
				return "当前时间【" + date + "】没有上传任务数";
			}
			HashMap<String, String> nhMap = getDKNH(date);
			HashMap<String, String> nbzgMap = getDKNBZG(date);
			HashMap<String, String> ajMap = getDKAJ(date);
			HashMap<String, String> ybzrrMap = getDKYBZRR(date);
			HashMap<String, String> qyMap = getDKQY(date);
			for (String str : userMap.keySet()) {
				int count, nh, nbzg, aj, yb, qy = 0;
				if (nhMap.get(str) == null) {
					nh = 0;
				} else {
					nh = Integer.parseInt(nhMap.get(str));
				}
				if (nbzgMap.get(str) == null) {
					nbzg = 0;
				} else {
					nbzg = Integer.parseInt(nbzgMap.get(str));
				}
				if (ajMap.get(str) == null) {
					aj = 0;
				} else {
					aj = Integer.parseInt(ajMap.get(str));
				}
				if (ybzrrMap.get(str) == null) {
					yb = 0;
				} else {
					yb = Integer.parseInt(ybzrrMap.get(str));
				}
				if (qyMap.get(str) == null) {
					qy = 0;
				} else {
					qy = Integer.parseInt(qyMap.get(str));
				}
				count = nh + nbzg + aj + yb + qy;
				insert.putFieldValue("name", str);
				insert.putFieldValue("passed", count);
				insert.putFieldValue("task", rwMap.get(str));
				insert.putFieldValue("date_time", date);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			jv = "查询完成";
		} catch (Exception e) {
			jv = "查询失败";
			e.printStackTrace();
		}

		return jv;
	}

	/**
	 * zzl 贷款管户数-农户Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getDKNH(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 贷款管户数-内部职工Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getDKNBZG(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('26','19') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 贷款管户数-按揭客户Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getDKAJ(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('20','29','28','37','h01','h02') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 贷款管户数-一般自然人Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getDKYBZRR(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 not in ('20','29','28','37','h01','h02','26','19','16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 贷款管户数-企业Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getDKQY(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select dg.cus_manager cus_manager,count(dg.cus_manager) countxj from (select distinct(xx.cert_code),dg.cus_manager  from wnbank.s_cmis_acc_loan dg left join wnbank.s_cmis_cus_base xx on dg.cus_id=xx.cus_id  where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "'and dg.cla<>'05' and account_status='1' and loan_balance<>'0')dg group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * ZPY 黔农E贷签约完成比计算
	 */
	@Override
	public String getQnedRate(String date_time) {
		try {
			date_time = date_time.replace("年", "-").replace("月", "");
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(P) from EXCEL_TAB_53 where year='"
							+ date_time.substring(0, 4) + "' group by A");
			if (rwMap.size() <= 0) {
				return "当月【" + date_time + "】中没有数据,请更换查询时间";
			}
			// 黔农E贷线上替代
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_qned_result");
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,deptname from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			HashMap<String, String> jlMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select aa.e name,count(aa.e) num from (select distinct(a) a,e  from wnsalarydb.excel_tab_1 where year='"
									+ date_time.substring(0, 4)
									+ "') aa group by aa.e");
			Set<String> keys = userMap.keySet();
			List<String> sqllist = new ArrayList<String>();
			DecimalFormat format = new DecimalFormat("#.000");
			for (String key : keys) {
				insert.putFieldValue("username", key);
				insert.putFieldValue("date_time", getLastMonth(date_time));
				insert.putFieldValue("deptname", userMap.get(key));
				double task = Double.parseDouble(rwMap.get(key) == null ? "0"
						: rwMap.get(key));
				double passed = Double.parseDouble(jlMap.get(key) == null ? "0"
						: jlMap.get(key));
				insert.putFieldValue("passed", passed);
				insert.putFieldValue("task", task);
				if (task == 0 || passed == 0) {
					insert.putFieldValue("rate", "0");
				} else {
					insert.putFieldValue("rate", format.format(passed / task));
				}
				sqllist.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, sqllist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "结果执行成功";
	}

	/**
	 * ZPY 黔农E贷线上替代完成比 date_time:2019-01-01
	 */
	@Override
	public String getQnedtdRate(String date_time) {
		String result = "";
		try {
			// 对查询日期进行处理
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(Q) from EXCEL_TAB_53 where year='"
							+ date_time.substring(0, 4) + "'  group by A");
			if (rwMap.size() <= 0) {
				return "当前考核月【" + date_time + "】缺少业务数据,请更换日期进行查询";
			}
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,deptname from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			Set<String> keys = userMap.keySet();
			String lastMonth = getSMonthDate(date_time);
			String yearC = getYearC(date_time);
			HashMap<String, String> currMonthData = dmo
					.getHashMapBySQLByDS(
							null,
							"SELECT a.mname mname, count(a.CARDNO) CARDNO   FROM (SELECT DISTINCT(CARDNO) CARDNO,MNAME FROM wn_qnedtd WHERE  date_time  LIKE '"
									+ date_time + "%') a GROUP BY  a.mname");// 当前考核月所做的绩效
			HashMap<String, String> lastMonthData = dmo
					.getHashMapBySQLByDS(
							null,
							"SELECT a.mname mname, count(a.CARDNO) CARDNO   FROM (SELECT DISTINCT(CARDNO) CARDNO,MNAME FROM wn_qnedtd WHERE  date_time  = '"
									+ yearC + "') a GROUP BY  a.mname");// 年初
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_qnedtd_result");
			DecimalFormat format = new DecimalFormat("0.000");
			List<String> sqllist = new ArrayList<String>();
			for (String key : keys) {
				insert.putFieldValue("username", key);
				insert.putFieldValue("deptname", userMap.get(key));
				double task = Double.parseDouble(rwMap.get(key) == null ? "0"
						: rwMap.get(key));
				insert.putFieldValue("task", task);
				double curretData = Double
						.parseDouble(currMonthData.get(key) == null ? "0"
								: currMonthData.get(key));
				double lastData = Double
						.parseDouble(lastMonthData.get(key) == null ? "0"
								: lastMonthData.get(key));
				insert.putFieldValue("passed", (curretData - lastData));
				if (task == 0 || (curretData - lastData) <= 0) {
					insert.putFieldValue("rate", "0");
				} else {
					insert.putFieldValue("rate",
							format.format((curretData - lastData) / task));
				}
				insert.putFieldValue("date_time", getLastMonth(date_time));
				sqllist.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, sqllist);
			result = "查询成功";
		} catch (Exception e) {
			result = "查询失败";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 手机银行开户完成比计算
	 */
	@Override
	public String getsjRate(String date_time) {
		String result = "";
		try {
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(O) from EXCEL_TAB_53 where year||'-'||month='"
							+ date_time + "' group by A");
			if (rwMap.size() <= 0) {
				return "在当前月【" + date_time + "】缺少业务数据,请更换日期进行查询";
			}
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,deptname from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			HashMap<String, String> jlMap = dmo.getHashMapBySQLByDS(null,
					"SELECT XD_COL2,count(A) FROM V_sjyh WHERE DATE_TIME='"
							+ date_time + "' GROUP BY XD_COL2");
			Set<String> keys = userMap.keySet();
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_sjyh_result");
			List<String> list = new ArrayList<String>();
			for (String key : keys) {
				insert.putFieldValue("username", key);
				insert.putFieldValue("date_time", getLastMonth(date_time));
				insert.putFieldValue("deptname", userMap.get(key));
				double task = Double.parseDouble(rwMap.get(key) == null
						|| rwMap.get(key).length() == 0 ? "0" : rwMap.get(key));
				insert.putFieldValue("task", task);
				double passed = Double.parseDouble(jlMap.get(key) == null ? "0"
						: jlMap.get(key));
				insert.putFieldValue("passed", passed);
				DecimalFormat format = new DecimalFormat("0.00");
				if (task == 0 || passed == 0) {
					insert.putFieldValue("rate", "0");
				} else {
					insert.putFieldValue("rate", format.format(passed / task));
				}
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			result = "查询成功";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 助农商户维护完成比
	 */
	@Override
	public String getZNRate(String date_time) {
		String result = "";
		try {
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(L) from EXCEL_TAB_53 where year='"
							+ date_time.substring(0, 4) + "' group by A");
			if (rwMap.size() <= 0) {
				return "在当前月【" + date_time + "】缺少业务数据,请更换日期进行查询";
			}
			HashMap<String, String> jlMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select aaaa.b name,count(aaaa.c) num from (select aaa.b,aaa.c from (select a.b b,a.c c,b.num num from (select b,c from excel_tab_10 where year||'-'||month='"
									+ date_time
									+ "' ) a left join (select c,count(b) num from excel_tab_11 where year||'-'||month='"
									+ date_time
									+ "' group by c) b on b.c=a.c) aaa left join (select aa.b b,aa.c c,count(bb.a) num from (select a,b,c from excel_tab_10 where year||'-'||month='"
									+ date_time
									+ "' ) aa left join (select a.a,a.num,b.c,b.e from (select a,(b+c) num  from excel_tab_14 where year||'-'||month='"
									+ date_time
									+ "') a left join (select a,b ,c,d,e from excel_tab_11 where year ||'-'|| month='"
									+ date_time
									+ "' and d !='否') b on a.a=b.b where a.num>=b.e) bb on aa.c=bb.c  group by aa.b,aa.c)  bbb  on   aaa.b=bbb.b and aaa.c=bbb.c where aaa.num=bbb.num) aaaa group by aaaa.b");
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_znsh_result");
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,deptname from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			HashVO[] userVos = dmo
					.getHashVoArrayByDS(
							null,
							"select name,deptname,stationkind from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			Set<String> keys = userMap.keySet();
			List<String> sqllist = new ArrayList<String>();

			DecimalFormat format = new DecimalFormat("#.00");
			for (int i = 0; i < userVos.length; i++) {
				String manager_name = userVos[i].getStringValue("name");
				String manager_type = userVos[i].getStringValue("stationkind");
				insert.putFieldValue("username", manager_name);
				insert.putFieldValue("deptname",
						userVos[i].getStringValue("deptname"));
				insert.putFieldValue("date_time", getLastMonth(date_time));
				if ("城区客户经理".equals(manager_type)) {
					insert.putFieldValue("task", 0.0);
					insert.putFieldValue("passed", 0.0);
					insert.putFieldValue("rate", 0.0);
				} else {
					double passed = Double
							.parseDouble(jlMap.get(manager_name) == null ? "0"
									: jlMap.get(manager_name));
					double task = Double
							.parseDouble(rwMap.get(manager_name) == null
									|| rwMap.get(manager_name).length() == 0 ? "0"
									: rwMap.get(manager_name));
					insert.putFieldValue("passed", passed);
					insert.putFieldValue("task", task);
					if (passed == 0 || task == 0) {
						insert.putFieldValue("rate", 0.0);
					} else {
						insert.putFieldValue("rate", Double.parseDouble(format
								.format(passed / task)));
					}
				}
				sqllist.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, sqllist);
			result = "查询成功";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ZPY 特约、小微商户完成比计算
	 */
	@Override
	public String getTyxwRate(String date_time) {
		String result = "";
		try {
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(N) from EXCEL_TAB_53 where year||'-'||month='"
							+ date_time + "' group by A");
			if (rwMap.size() <= 0) {
				return "在当前月【" + date_time + "】缺少业务数据,请更换日期进行查询";
			}
			List<String> sqlList = new ArrayList<String>();
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(null,
							"select name,deptname from v_sal_personinfo where stationkind  like '%客户经理%'");
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_tyxw_result");
			Set<String> keys = userMap.keySet();
			Map<String, Integer> tyxw = getTyxw(date_time);
			DecimalFormat format = new DecimalFormat("0.000");
			String task = "";
			for (String manager_name : keys) {// 特约小微商户完成比查询
				int num = tyxw.get(manager_name);
				insert.putFieldValue("passed", num);
				task = rwMap.get(manager_name) == null ? "0" : rwMap
						.get(manager_name);
				insert.putFieldValue("task", task);
				insert.putFieldValue("username", manager_name);
				if ("0".equals(task)) {
					insert.putFieldValue("rate", "0.0");
				} else {
					insert.putFieldValue("rate",
							format.format(num / Double.parseDouble(task)));
				}
				insert.putFieldValue("date_time", getLastMonth(date_time));
				insert.putFieldValue("deptname", userMap.get(manager_name));
				sqlList.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, sqlList);
			result = "查询成功";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private Map<String, Integer> getTyxw(String date_time) {
		HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
		try {
			HashMap<String, String> xwMap = dmo
					.getHashMapBySQLByDS(
							null,
							" select aa.b name,count(bb.a) num from (select a.a a ,b.b b from (select * from excel_tab_13 where  c='小微商户' and year||'-'||month='"
									+ date_time
									+ "') a left join (select  * from excel_tab_16 where year||'-'||month='"
									+ date_time
									+ "') b on a.d=b.a)  aa left join (select a,c from excel_tab_59 where year||'-'||month='"
									+ date_time
									+ "' and b='小微' AND c>=5) bb on bb.a=aa.a where bb.a is  not null group by aa.b");
			HashMap<String, String> tyMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select aa.b name,count(bb.a) num from (select a.a a ,b.b b from (select * from excel_tab_13 where  c='特约商户' and year||'-'||month='"
									+ date_time
									+ "') a left join (select  * from excel_tab_16 where year||'-'||month='"
									+ date_time
									+ "') b on a.d=b.a)  aa left join  (select a,b,c from excel_tab_59 where year||'-'||month='"
									+ date_time
									+ "' and b='特约'  and c>=10) bb on bb.a=aa.a where bb.a is  not null group by aa.b");
			String[] manager_names = dmo
					.getStringArrayFirstColByDS(null,
							"SELECT name FROM v_sal_personinfo WHERE STATIONKIND LIKE '%客户经理%'");
			for (int i = 0; i < manager_names.length; i++) {
				Integer xwNum = Integer
						.parseInt(xwMap.get(manager_names[i]) == null ? "0"
								: xwMap.get(manager_names[i]));
				Integer tyNum = Integer
						.parseInt(tyMap.get(manager_names[i]) == null ? "0"
								: tyMap.get(manager_names[i]));
				resultMap.put(manager_names[i], xwNum + tyNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	public String getLastMonth(String date) {
		String result = "";
		try {
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM");
			int year = Integer.parseInt(new SimpleDateFormat("yyyy")
					.format(simple.parse(date)));
			int month = Integer.parseInt(new SimpleDateFormat("MM")
					.format(simple.parse(date)));
			String day = "";
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				day = "31";
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				day = "30";
				break;
			case 2:
				if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
					day = "29";
				} else {
					day = "28";
				}
				break;
			default:
				break;
			}
			String month2 = String.valueOf(month);
			month2 = month2.length() == 1 ? "0" + month2 : month2;
			result = year + "-" + month2 + "-" + day;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
		}

	}

	/**
	 * zzl 贷款余额新增完成比
	 */
	@Override
	public String getDKBalanceXZ(String date) {
		String jl = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_loan_balance");
			List list = new ArrayList<String>();
			// 得到客户经理的Map
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			// 得到客户经理的任务数
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(
					null,
					"select A,sum(E) from EXCEL_TAB_53 where year='"
							+ date.substring(0, 4) + "' group by A");
			if (rwMap.size() == 0) {
				return "当前时间【" + date + "】没有上传任务数";
			}
			// 得到机构编码的map
			HashMap<String, String> deptCodeMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select sal.name,dept.code from v_sal_personinfo sal left join pub_corp_dept dept on sal.deptid=dept.id");
			// 得到机构类型的map
			HashMap<String, String> deptTypeMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select sal.name,dept.corptype from v_sal_personinfo sal left join pub_corp_dept dept on sal.deptid=dept.id");
			// 营业部考核月贷款余额-农户Map
			HashMap<String, String> YEKDKNHMap = getKDKNHSalesOffice(date);
			HashMap<String, String> YESDKNHMap = getSDKNHSalesOffice(date);
			HashMap<String, String> YEKDKQTMap = getKDKQTSalesOffice(date);
			HashMap<String, String> YESDKQTMap = getSDKQTSalesOffice(date);
			HashMap<String, String> YEKDKDGMap = getKDKDGSalesOffice(date);
			HashMap<String, String> YESDKDGMap = getSDKDGSalesOffice(date);
			HashMap<String, String> KCQNHMap = getKDKCQNHSalesOffice(date);
			HashMap<String, String> SCQNHMap = getSDKCQNHSalesOffice(date);
			HashMap<String, String> KCQQTMap = getKDKCQQTSalesOffice(date);
			HashMap<String, String> SCQQTMap = getSDKCQQTSalesOffice(date);
			HashMap<String, String> KCQDGMap = getKDKDG100SalesOffice(date);
			HashMap<String, String> SCQDGMap = getSDKDG100SalesOffice(date);
			HashMap<String, String> KDGNHMap = getKDKNHSales(date);
			HashMap<String, String> SDGNHMap = getSDKNHSales(date);
			HashMap<String, String> KDGQTMap = getKDKQTSales(date);
			HashMap<String, String> SDGQTMap = getSDKQTSales(date);
			for (String str : userMap.keySet()) {
				Double yyb1, yyb2, yyb3, yyb4, yyb5, yyb6 = 0.0;
				Double count = 0.0;
				if (deptCodeMap.get(str).equals("2820100")) {
					if (YEKDKNHMap.get(str) == null) {
						yyb1 = 0.0;
					} else {
						yyb1 = Double.parseDouble(YEKDKNHMap.get(str));
					}
					if (YESDKNHMap.get(str) == null) {
						yyb2 = 0.0;
					} else {
						yyb2 = Double.parseDouble(YESDKNHMap.get(str));
					}
					if (YEKDKQTMap.get(str) == null) {
						yyb3 = 0.0;
					} else {
						yyb3 = Double.parseDouble(YEKDKQTMap.get(str));
					}
					if (YESDKQTMap.get(str) == null) {
						yyb4 = 0.0;
					} else {
						yyb4 = Double.parseDouble(YESDKQTMap.get(str));
					}
					if (YEKDKDGMap.get(str) == null) {
						yyb5 = 0.0;
					} else {
						yyb5 = Double.parseDouble(YEKDKDGMap.get(str));
					}
					if (YESDKDGMap.get(str) == null) {
						yyb6 = 0.0;
					} else {
						yyb6 = Double.parseDouble(YESDKDGMap.get(str));
					}
					count = (yyb1 - yyb2) + (yyb3 - yyb4) + (yyb5 - yyb6);
					insert.putFieldValue("name", str);
					insert.putFieldValue("passed", count);
					insert.putFieldValue("task", rwMap.get(str));
					insert.putFieldValue("date_time", date);
					list.add(insert.getSQL());
				} else if (deptTypeMap.get(str).equals("城区银行")) {
					if (KCQNHMap.get(str) == null) {
						yyb1 = 0.0;
					} else {
						yyb1 = Double.parseDouble(KCQNHMap.get(str));
					}
					if (SCQNHMap.get(str) == null) {
						yyb2 = 0.0;
					} else {
						yyb2 = Double.parseDouble(SCQNHMap.get(str));
					}
					if (KCQQTMap.get(str) == null) {
						yyb3 = 0.0;
					} else {
						yyb3 = Double.parseDouble(KCQQTMap.get(str));
					}
					if (SCQQTMap.get(str) == null) {
						yyb4 = 0.0;
					} else {
						yyb4 = Double.parseDouble(SCQQTMap.get(str));
					}
					if (KCQDGMap.get(str) == null) {
						yyb5 = 0.0;
					} else {
						yyb5 = Double.parseDouble(KCQDGMap.get(str));
					}
					if (SCQDGMap.get(str) == null) {
						yyb6 = 0.0;
					} else {
						yyb6 = Double.parseDouble(SCQDGMap.get(str));
					}
					count = (yyb1 - yyb2) + (yyb3 - yyb4) + (yyb5 - yyb6);
					insert.putFieldValue("name", str);
					insert.putFieldValue("passed", count);
					insert.putFieldValue("task", rwMap.get(str));
					insert.putFieldValue("date_time", date);
					list.add(insert.getSQL());
				} else {
					if (KDGNHMap.get(str) == null) {
						yyb1 = 0.0;
					} else {
						yyb1 = Double.parseDouble(KDGNHMap.get(str));
					}
					if (SDGNHMap.get(str) == null) {
						yyb2 = 0.0;
					} else {
						yyb2 = Double.parseDouble(SDGNHMap.get(str));
					}
					if (KDGQTMap.get(str) == null) {
						yyb3 = 0.0;
					} else {
						yyb3 = Double.parseDouble(KDGQTMap.get(str));
					}
					if (SDGQTMap.get(str) == null) {
						yyb4 = 0.0;
					} else {
						yyb4 = Double.parseDouble(SDGQTMap.get(str));
					}
					count = (yyb1 - yyb2) + (yyb3 - yyb4);
					insert.putFieldValue("name", str);
					insert.putFieldValue("passed", count);
					insert.putFieldValue("task", rwMap.get(str));
					insert.putFieldValue("date_time", date);
					list.add(insert.getSQL());
				}
			}
			dmo.executeBatchByDS(null, list);
			jl = "查询成功";
		} catch (Exception e) {
			jl = "查询失败";
			e.printStackTrace();
		}
		return jl;
	}

	/**
	 * zzl 营业部考核月贷款余额-农户Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getKDKNHSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "'  and xd_col2 not like '公司' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0  and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 营业部年初贷款余额-农户Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getSDKNHSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getYearDate(date)
									+ "'  and xd_col2 not like '公司' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0  and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 营业部考核月贷款余额-其他Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getKDKQTSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "'  and xd_col2 not like '公司' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 营业部年初贷款余额-其他Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getSDKQTSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getYearDate(date)
									+ "'  and xd_col2 not like '公司' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 营业部对公贷款余额Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getKDKDGSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select dg.cus_manager cus_manager,sum(LOAN_BALANCE)/10000 tj from (select dg.cus_manager cus_manager,sum(LOAN_AMOUNT) tj,sum(LOAN_BALANCE) LOAN_BALANCE  from wnbank.s_cmis_acc_loan dg where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "' and dg.cla<>'05' and account_status='1' and loan_balance<>'0' group by CUS_ID,dg.cus_manager) dg where dg.tj<10000000 group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 营业部年初对公贷款余额Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getSDKDGSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select dg.cus_manager cus_manager,sum(LOAN_BALANCE)/10000 tj from (select dg.cus_manager cus_manager,sum(LOAN_AMOUNT) tj,sum(LOAN_BALANCE) LOAN_BALANCE  from wnbank.s_cmis_acc_loan dg where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getYearDate(date)
									+ "' and dg.cla<>'05' and account_status='1' and loan_balance<>'0' group by CUS_ID,dg.cus_manager) dg where dg.tj<10000000 group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 城区网点农户贷款余额新增Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getKDKCQNHSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%公司%' group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 城区网点年初农户贷款余额新增Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getSDKCQNHSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getYearDate(date)
									+ "' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%公司%' group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 城区网点其他贷款余额新增Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getKDKCQQTSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col6) as xd_col6,sum(xd_col7) xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%公司%' group by XD_COL14,xd_col81 ) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 城区网点年初其他贷款余额新增Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getSDKCQQTSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col6) as xd_col6,sum(xd_col7) xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getYearDate(date)
									+ "' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%公司%' group by XD_COL14,xd_col81 ) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 对公贷款余额100万以下Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getKDKDG100SalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select dg.cus_manager cus_manager,sum(LOAN_BALANCE)/10000 tj from (select dg.cus_manager cus_manager,sum(LOAN_AMOUNT) tj,sum(LOAN_BALANCE) LOAN_BALANCE  from wnbank.s_cmis_acc_loan dg where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "' and dg.cla<>'05' and account_status='1' and loan_balance<>'0' group by CUS_ID,dg.cus_manager) dg where dg.tj<1000000 group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 对公贷款年初余额100万以下Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getSDKDG100SalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select dg.cus_manager cus_manager,sum(LOAN_BALANCE)/10000 tj from (select dg.cus_manager cus_manager,sum(LOAN_AMOUNT) tj,sum(LOAN_BALANCE) LOAN_BALANCE  from wnbank.s_cmis_acc_loan dg where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getYearDate(date)
									+ "' and dg.cla<>'05' and account_status='1' and loan_balance<>'0' group by CUS_ID,dg.cus_manager) dg where dg.tj<1000000 group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 考核月贷款余额-农户Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getKDKNHSales(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7,sum(xd_col6)/10000 xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%公司%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 年初贷款余额-农户Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getSDKNHSales(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7,sum(xd_col6)/10000 xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getYearDate(date)
									+ "' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%公司%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 考核月贷款余额-其他Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getKDKQTSales(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%公司%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 年初贷款余额-其他Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getSDKQTSales(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getYearDate(date)
									+ "' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%公司%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 贷款户数新增
	 */
	@Override
	public String getDKHouseholdsXZ(String date) {
		String jl = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("WN_LOANS_newly");
			List list = new ArrayList<String>();
			// 得到客户经理的Map
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			// 得到客户经理的任务数
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(
					null,
					"select A,sum(F) from EXCEL_TAB_53 where year='"
							+ date.substring(0, 4) + "' group by A");
			if (rwMap.size() == 0) {
				return "当前时间【" + date + "】没有上传任务数";
			}
			HashMap<String, String> knhMap = getKDKNHNewly(date);
			HashMap<String, String> snhMap = getSDKNHNewly(date);
			HashMap<String, String> kqtMap = getKDKQTNewly(date);
			HashMap<String, String> sqtMap = getSDKQTNewly(date);
			HashMap<String, String> kdgMap = getKDKDGNewly(date);
			HashMap<String, String> sdgMap = getSDKDGNewly(date);
			for (String str : userMap.keySet()) {
				int count = 0;
				int hs1, hs2, hs3, hs4, hs5, hs6 = 0;
				if (knhMap.get(str) == null) {
					hs1 = 0;
				} else {
					hs1 = Integer.parseInt(knhMap.get(str));
				}
				if (snhMap.get(str) == null) {
					hs2 = 0;
				} else {
					hs2 = Integer.parseInt(snhMap.get(str));
				}
				if (kqtMap.get(str) == null) {
					hs3 = 0;
				} else {
					hs3 = Integer.parseInt(kqtMap.get(str));
				}
				if (sqtMap.get(str) == null) {
					hs4 = 0;
				} else {
					hs4 = Integer.parseInt(sqtMap.get(str));
				}
				if (kdgMap.get(str) == null) {
					hs5 = 0;
				} else {
					hs5 = Integer.parseInt(kdgMap.get(str));
				}
				if (sdgMap.get(str) == null) {
					hs6 = 0;
				} else {
					hs6 = Integer.parseInt(sdgMap.get(str));
				}
				count = (hs1 - hs2) + (hs3 - hs4) + (hs5 - hs6);
				insert.putFieldValue("name", str);
				insert.putFieldValue("passed", count);
				insert.putFieldValue("task", rwMap.get(str));
				insert.putFieldValue("date_time", date);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			jl = "查询成功";
		} catch (Exception e) {
			jl = "查询失败";
			e.printStackTrace();
		}
		return jl;
	}

	/**
	 * zzl 考核月贷款户数-农户Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getKDKNHNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 年初贷款户数-农户Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getSDKNHNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getYearDate(date)
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 考核月贷款户数-其他Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getKDKQTNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 年初贷款户数-其他Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getSDKQTNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getYearDate(date)
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 考核月贷款户数-企业Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getKDKDGNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select dg.cus_manager cus_manager,count(dg.cus_manager) countxj from (select distinct(xx.cert_code),dg.cus_manager  from wnbank.s_cmis_acc_loan dg left join wnbank.s_cmis_cus_base xx on dg.cus_id=xx.cus_id  where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "'and dg.cla<>'05' and account_status='1' and loan_balance<>'0')dg group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 年初贷款户数-企业Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getSDKDGNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select dg.cus_manager cus_manager,count(dg.cus_manager) countxj from (select distinct(xx.cert_code),dg.cus_manager  from wnbank.s_cmis_acc_loan dg left join wnbank.s_cmis_cus_base xx on dg.cus_id=xx.cus_id  where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getYearDate(date)
									+ "'and dg.cla<>'05' and account_status='1' and loan_balance<>'0')dg group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 表外不良贷款完成比
	 * 
	 * @param date
	 * @return
	 */
	@Override
	public String getBadLoans(String date) {
		String jl = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("WN_OFFDK_BAB");
			List list = new ArrayList<String>();
			// 得到客户经理的Map
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			// 得到客户经理的任务数
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(
					null,
					"select A,sum(K) from EXCEL_TAB_53 where year='"
							+ date.substring(0, 4) + "' group by A");
			if (rwMap.size() == 0) {
				return "当前时间【" + date + "】没有上传任务数";
			}
			HashMap<String, String> bab2017Map = get2017DBadLoans(date);
			HashMap<String, String> bab2018Map = get2018DBadLoans(date);
			for (String str : userMap.keySet()) {
				Double count = 0.0;
				Double hj1, hj2 = 0.0;
				if (bab2017Map.get(str) == null) {
					hj1 = 0.0;
				} else {
					hj1 = Double.parseDouble(bab2017Map.get(str));
				}
				if (bab2018Map.get(str) == null) {
					hj2 = 0.0;
				} else {
					hj2 = Double.parseDouble(bab2018Map.get(str));
				}
				count = hj1 + hj2;
				insert.putFieldValue("name", str);
				insert.putFieldValue("passed", count);
				insert.putFieldValue("task", rwMap.get(str));
				insert.putFieldValue("date_time", date);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			jl = "查询成功";
		} catch (Exception e) {
			jl = "查询失败";
			e.printStackTrace();
		}
		return jl;
	}

	/**
	 * zzl 现金收回表外不良贷款本息金额2017Map
	 * 
	 * @return
	 */
	public HashMap<String, String> get2017DBadLoans(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select yb.xd_col2 xd_col2,sum(bwbl.sumtj)/10000 tj from(select hk.xd_col1 xd_col1,hk.sumtj sumtj,dk.xd_col81 xd_col81 from(select XD_COL1 XD_COL1,sum(XD_COL5+XD_COL11) sumtj from  wnbank.S_LOAN_HK hk where to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')>='"
									+ getMonthC(date)
									+ "' and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')<='"
									+ date
									+ "' and  XD_COL16='05'  and XD_COL20<>'81320101' group by XD_COL1) hk  left join wnbank.s_loan_dk dk on hk.xd_col1=dk.xd_col1  where to_char(to_date(dk.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "' and  XD_COL5<='2017-12-31') bwbl left join wnbank.S_LOAN_RYB yb on bwbl.XD_COL81=yb.xd_col1 group by yb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 现金收回表外不良贷款本息金额(2018)Map
	 * 
	 * @return
	 */
	public HashMap<String, String> get2018DBadLoans(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select yb.xd_col2,sum(bwbl.sumtj)/10000 from(select hk.xd_col1 xd_col1,hk.sumtj sumtj,dk.xd_col81 xd_col81 from(select XD_COL1 XD_COL1,sum(XD_COL5+XD_COL11) sumtj from  wnbank.S_LOAN_HK hk where to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')>='"
									+ getMonthC(date)
									+ "' and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')<='"
									+ date
									+ "' and  XD_COL16='05'  and XD_COL20<>'81320101' group by XD_COL1) hk left join wnbank.s_loan_dk dk on hk.xd_col1=dk.xd_col1  where to_char(to_date(dk.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "' and  XD_COL5<='2018-12-31' and XD_COL5>='2018-01-01') bwbl left join wnbank.S_LOAN_RYB yb on bwbl.XD_COL81=yb.xd_col1 group by yb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 返回月初的日期
	 * 
	 * @param date
	 * @return
	 */
	public String getMonthC(String date) {
		date = date.substring(0, 7);
		return date + "-01";
	}

	/**
	 * zzl 返回年初的日期
	 * 
	 * @param date
	 * @return
	 */
	public String getYearC(String date) {
		date = date.substring(0, 4);
		date = String.valueOf(Integer.valueOf(date) - 1);
		return date + "-12-31";
	}

	/**
	 * zzl[收回存量不良贷款完成比&不良贷款压降]
	 */
	@Override
	public String getTheStockOfLoan(String date) {
		String jl = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("WN_Stock_Loan");
			List list = new ArrayList<String>();
			// 得到客户经理的Map
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			// 得到客户经理的任务数
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(
					null,
					"select A,sum(G) from EXCEL_TAB_53 where year='"
							+ date.substring(0, 4) + "' group by A");
			if (rwMap.size() == 0) {
				return "当前时间【" + date + "】没有上传任务数";
			}
			HashMap<String, String> slMap = getStockLoans(date);
			for (String str : userMap.keySet()) {
				Double count = 0.0;
				if (slMap.get(str) == null) {
					count = 0.0;
				} else {
					count = Double.parseDouble(slMap.get(str));
				}
				insert.putFieldValue("name", str);
				insert.putFieldValue("passed", count);
				insert.putFieldValue("task", rwMap.get(str));
				insert.putFieldValue("date_time", date);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			jl = "查询成功";
		} catch (Exception e) {
			jl = "查询失败";
			e.printStackTrace();
		}

		return jl;
	}

	/**
	 * zzl 收回存量不良贷款考核
	 * 
	 * @return
	 */
	public HashMap<String, String> getStockLoans(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select yb.xd_col2,sum(zjj.XD_COL5)/10000 from(select zj.XD_COL81 XD_COL81,sum(zj.XD_COL5) XD_COL5 from(select hk.xd_col14,hk.XD_COL5,hk.XD_COL81,dk.xd_col1 from (select xdk.xd_col1,xhk.XD_COL4,xhk.XD_COL5,xdk.XD_COL2,xdk.XD_COL81,xdk.XD_COL14 from(select * from wnbank.S_LOAN_HK where Xd_Col16 in('03','04') and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')<='"
									+ date
									+ "' and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')>='"
									+ getMonthC(date)
									+ "') xhk left join (select * from wnbank.S_LOAN_DK where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "' and xd_col22 in('03','04')) xdk on xdk.xd_col1=xhk.xd_col1) hk left join (select * from wnbank.S_LOAN_DK where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getYearC(date)
									+ "' and xd_col22 in('03','04')) dk on dk.xd_col1=hk.xd_col1) zj where zj.xd_col1 is not null group by zj.xd_col14,zj.XD_COL81) zjj left join wnbank.S_LOAN_RYB yb on zjj.XD_COL81=yb.xd_col1 group by yb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 为每个委派会计生成打分
	 */
	@Override
	public String getKJDXScore(String id) {
		String result = "";
		try {
			// // 查询所有未结束评分的客户经理
			// HashVO[] vos = dmo.getHashVoArrayByDS(null,
			// "select * from wn_managerdx_table where  state='评分中'");
			// if (vos.length <= 0) {
			// result = "当前考核计划结束成功";
			// } else {// 尚未结束的考核人员,直接将考核人员的分数设置成0分
			// UpdateSQLBuilder update = new UpdateSQLBuilder(
			// "wn_managerdx_table");
			// List<String> _sqllist = new ArrayList<String>();
			// Map<String, Double> map=new HashMap<String, Double>();
			// for (int i = 0; i < vos.length; i++) {
			//
			// String xiangmu = vos[i].getStringValue("xiangmu");
			// // if("总分".equals(xiangmu)){continue;}
			// String fenzhi = vos[i].getStringValue("fenzhi");
			// String koufen = vos[i].getStringValue("koufen");
			// String khsm = vos[i].getStringValue("khsm");
			// String usercode = vos[i].getStringValue("usercode");
			// if(koufen==null || "".isEmpty()||
			// Integer.parseInt(koufen)>=Integer.parseInt(fenzhi)){
			// koufen=fenzhi;
			// }
			// if(!map.containsKey(usercode)){
			// map.put(usercode, Double.valueOf(koufen));
			// }else {
			// map.put(usercode, map.get(usercode)+Double.valueOf(koufen));
			// }
			// update.setWhereCondition("usercode='" + usercode
			// + "' and khsm='" + khsm + "' and state='评分中'");
			// update.putFieldValue("koufen", koufen);
			// update.putFieldValue("state", "评分结束");
			// _sqllist.add(update.getSQL());
			// }
			// Set<String> usercodeKey = map.keySet();
			// String sql="";
			// for (String usercode : usercodeKey) {
			// sql="update wn_managerdx_table set fenzhi='"+map.get(usercode)+"' where usercode='"+usercode+"' and state='评分中'";
			// _sqllist.add(sql);
			// }
			// dmo.executeBatchByDS(null, _sqllist);
			// result = "当前考核计划结束成功";
			// 首先，获取到需要参与考核的委派会计信息
			HashVO[] kjVos = dmo.getHashVoArrayByDS(null,
					"SELECT * FROM V_PUB_USER_POST_1 WHERE POSTNAME='委派会计'");
			// 获取到委派会计的打分项
			HashVO[] pfVos = dmo
					.getHashVoArrayByDS(
							null,
							"   select * from sal_person_check_list where 1=1  and (type='101')  and (1=1)  order by seq");
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date());
			List<String> sqlList = new ArrayList<String>();
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_kjscore_table");
			for (int i = 0; i < kjVos.length; i++) {// 为每一个会计生成打分计划
				for (int j = 0; j < pfVos.length; j++) {
					insert.putFieldValue("PLANID", id);
					insert.putFieldValue("USERNAME",
							kjVos[i].getStringValue("username"));
					insert.putFieldValue("USERCODE",
							kjVos[i].getStringValue("usercode"));
					insert.putFieldValue("USERDEPT",
							kjVos[i].getStringValue("deptcode"));
					insert.putFieldValue("xiangmu",
							pfVos[j].getStringValue("catalog"));
					insert.putFieldValue("khsm",
							pfVos[j].getStringValue("name"));
					insert.putFieldValue("fenzhi",
							pfVos[j].getStringValue("weights"));
					insert.putFieldValue("koufen", 0.0);
					insert.putFieldValue("pftime", date);
					insert.putFieldValue("state", "评分中");
				}
				sqlList.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, sqlList);
			result = "执行成功";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getKJDXEnd(String id) {
		String result = "";
		try {
			// 首先获取当前尚未结束考核的委派会计
			HashVO[] kjVos = dmo.getHashVoArrayByDS(null,
					"SELECT * FROM V_PUB_USER_POST_1 WHERE POSTNAME='委派会计'");
			HashVO[] pfVos = dmo.getHashVoArrayByDS(null,
					"select * from wn_kjscore_table where state='评分中' and planid='"
							+ id + "'");
			double fenzhi = 0.0;
			UpdateSQLBuilder update = new UpdateSQLBuilder("wn_kjscore_table");
			List<String> sqlList = new ArrayList<String>();
			for (int i = 0; i < pfVos.length; i++) {
				fenzhi = Double.parseDouble(pfVos[i].getStringValue("fenzhi"));
				update.setWhereCondition("planid='" + id + "' and usercode='"
						+ pfVos[i].getStringValue("USERCODE") + "'");
				update.putFieldValue("koufen", fenzhi);
				update.putFieldValue("state", "评分结束");
				sqlList.add(update.getSQL());
			}
			dmo.executeBatchByDS(null, sqlList);
			result = "计划结束成功";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * fj 农户建档户数完成比
	 * 
	 * @return
	 */

	@Override
	public String getNhjdHs(String date) {
		Double month = Double.parseDouble(date.substring(5, 7));
		String result = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("WN_NHJD_WCB");
			List list = new ArrayList<String>();
			// 得到客户经理的map
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			// 得到客户经理的任务数
			String sql = "select A,sum(R)*'" + month
					+ "' from EXCEL_TAB_53 where year||'-'||month='" + date
					+ "' group by A";
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null, sql);
			if (rwMap.size() == 0) {
				return "当前指标没有上传任务数！";
			}
			HashMap<String, String> map = getNhjdMap(date);
			for (String str : userMap.keySet()) {
				Double count = 0.0;
				Double rwcount = 0.0;
				if (map.get(str) == null || map.get(str).equals("")) {
					continue;
				} else {
					count = Double.parseDouble(map.get(str));
				}
				if (rwMap.get(str).equals("0") || rwMap.get(str) == null
						|| rwMap.get(str).equals("")) {
					continue;
				} else {
					rwcount = Double.parseDouble(rwMap.get(str));
				}
				insert.putFieldValue("name", str);
				System.out.println(str);
				insert.putFieldValue("passed", count);
				System.out.println(count);
				insert.putFieldValue("task", rwcount);
				System.out.println(rwcount);
				insert.putFieldValue("date_time", date);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			result = "查询成功！";
		} catch (Exception e) {
			e.printStackTrace();
			result = "查询失败！请联系管理员！";
		}
		return result;
	}

	/**
	 * 新建农户建档户数
	 * 
	 * @param date
	 * @return
	 */

	private HashMap<String, String> getNhjdMap(String date) {
		String year = date.substring(0, 4);
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select yb.xd_col2 xd_col2,zc.tj tj  from(select xx.xd_col96 xd_col96,count(xx.xd_col96) tj from (select XD_COL1,XD_COL96 from wnbank.s_loan_khxx where to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm')>='"
									+ year
									+ "-01' and  to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm')<='"
									+ date
									+ "'  and xd_col10 not in('未评级','等外','!$') and XD_COL4='905') xx left join wnbank.S_LOAN_KHXXZCQK zc on xx.xd_col1=zc.xd_col1 where zc.XD_COL6='1' group by xx.xd_col96)zc left join wnbank.s_loan_ryb yb on zc.xd_col96=yb.xd_col1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public String getQnedXstd(String date) {
		String result = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("WN_QNED_XSTD");
			List list = new ArrayList<String>();
			// 得到客户经理的map
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			// 得到客户经理的任务数
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(Q) from EXCEL_TAB_53 where year||'-'||month='"
							+ date.substring(0, 7) + "' group by A");
			if (rwMap.size() == 0) {
				return "当前时间【" + date + "】没有上传任务数";
			}
			HashMap<String, String> khMap = getKhxsCount(date);
			HashMap<String, String> lastMap = getLastxsCount(date);
			for (String str : userMap.keySet()) {
				Double count = 0.0;
				Double khcount = 0.0;
				Double lastcount = 0.0;
				if (khMap.get(str) == null) {
					khcount = 0.0;
				} else {
					khcount = Double.parseDouble(khMap.get(str));
				}
				if (lastMap.get(str) == null) {
					lastcount = 0.0;
				} else {
					lastcount = Double.parseDouble(lastMap.get(str));
				}
				count = khcount - lastcount;
				insert.putFieldValue("name", str);
				insert.putFieldValue("last_passed", lastcount);
				insert.putFieldValue("passed", khcount);
				insert.putFieldValue("task", rwMap.get(str));
				insert.putFieldValue("time", date);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			result = "查询成功";
		} catch (Exception e) {
			e.printStackTrace();
			result = "查询失败";
		}
		return result;
	}

	/**
	 * 黔农e贷上月线上户数
	 * 
	 * @param date
	 * @return
	 */
	private HashMap<String, String> getLastxsCount(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select b.xd_col2 name,count(a.xd_col16) num from (select distinct(xd_col16) xd_col16,xd_col81 from wnbank1.s_loan_dk where  xd_col7>0 and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')>='"
									+ getMonthC(getSMonthDate(date))
									+ "' and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')<='"
									+ getSMonthDate(date)
									+ "' and xd_col86='x_wd') a left join wnbank1.s_loan_ryb b on b.xd_col1=a.xd_col81 group by b.xd_col2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 黔农e贷考核月线上户数
	 * 
	 * @param date
	 * @return
	 */
	private HashMap<String, String> getKhxsCount(String date) {

		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select b.xd_col2 name,count(a.xd_col16) num from (select distinct(xd_col16) xd_col16,xd_col81 from wnbank1.s_loan_dk where  xd_col7>0 and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')>='"
									+ getMonthC(date)
									+ "' and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')<='"
									+ date
									+ "' and xd_col86='x_wd') a left join wnbank1.s_loan_ryb b on b.xd_col1=a.xd_col81 group by b.xd_col2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}

	/**
	 * fj [单位职工小微企业建档户数完成比]
	 */

	@Override
	public String getDwzgXwqyRatio(String date) {
		String result = null;
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_zgxw_wcb");
		List list = new ArrayList<String>();
		try {
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			// 得到客户经理的任务数
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(R) from EXCEL_TAB_53 where year||'-'||month='"
							+ date.substring(0, 7) + "' group by A");
			if (rwMap.size() == 0) {
				return "当前时间【" + date + "】没有上传任务数";
			}
			HashMap<String, String> dwzgMap = getDwzgCount(date);
			HashMap<String, String> xwqyMap = getXwqyCount(date);
			for (String str : userMap.keySet()) {
				Double dwzgcount = 0.0;
				Double xwqycount = 0.0;
				Double count = 0.0;
				if (dwzgMap.get(str) == null) {
					dwzgcount = 0.0;
				} else {
					dwzgcount = Double.parseDouble(dwzgMap.get(str));
				}
				if (xwqyMap.get(str) == null) {
					xwqycount = 0.0;
				} else {
					xwqycount = Double.parseDouble(xwqyMap.get(str));
				}
				count = dwzgcount + xwqycount;
				insert.putFieldValue("username", str);
				insert.putFieldValue("dwzgcount", dwzgcount);
				insert.putFieldValue("xwqycount", xwqycount);
				insert.putFieldValue("passed", count);
				insert.putFieldValue("task", rwMap.get(str));
				insert.putFieldValue("date_time", date);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			result = "查询成功";
		} catch (Exception e) {
			e.printStackTrace();
			result = "查询失败";
		}
		return result;
	}

	/**
	 * 客户经理小微企业建档户数
	 * 
	 * @param date
	 * @return
	 */
	private HashMap<String, String> getXwqyCount(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select yb.xd_col2 xd_col2,xx.tj tj from (select XD_COL96 XD_COL96,count(XD_COL96) tj from wnbank.s_loan_khxx where to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')>='"
									+ getMonthC(date)
									+ "' and to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')<='"
									+ date
									+ "' and xd_col10 not in('未评级','等外','!$') and XD_COL4='206' and XD_COL163 not in('05','03','06','01') group by XD_COL96)xx left join wnbank.s_loan_ryb yb on xx.xd_col96=yb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 客户经理单位职工建档户数
	 * 
	 * @param date
	 * @return
	 */

	private HashMap<String, String> getDwzgCount(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select yb.xd_col2 xd_col2,xx.tj tj from (select XD_COL96 XD_COL96,count(XD_COL96) tj from wnbank.s_loan_khxx where to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')>='"
									+ getMonthC(date)
									+ "'and to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')<='"
									+ date
									+ "'and xd_col10 not in('未评级','等外','!$') and XD_COL4='907' group by XD_COL96)xx left join wnbank.s_loan_ryb yb on xx.xd_col96=yb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public String getGyClass(String date) throws Exception {
		List list = new ArrayList<String>();
		InsertSQLBuilder insert = new InsertSQLBuilder("WN_GYPJ");
		String result = null;
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String str = getAnnual(year, month);
		HashVO[] vos = dmo.getHashVoArrayByDS(null,
				"select * from V_WN_GYPJ where pjtime='" + str + "'");
		if (vos.length > 0) {
			result = "该半年度已考核，请点击查询查看！";
		} else if (str == null) {
			result = "当前时间不在考核时间范围内！";
		} else {
			try {
				HashMap<String, String> userMap = dmo
						.getHashMapBySQLByDS(
								null,
								"select B,B from (select B,count(B) as c from excel_tab_39 group by B) where c=6");
				String userCount = dmo
						.getStringValueByDS(
								null,
								"select count(*) from (select B,B from (select B,count(B) as c from excel_tab_39 group by B) where c=6)");
				HashMap<String, String> userDeptMap = dmo
						.getHashMapBySQLByDS(null,
								"select username,deptname from v_pub_user_post_1");
				HashMap<String, String> wmgfMap = getWmgf(str, year);
				HashMap<String, String> fwzlMap = getFwzl(str, year);
				HashMap<String, String> gzzlMap = getGzzl(str, year);
				HashMap<String, String> wdpjzbMap = getWdpjzb(str, year);
				HashMap<String, String> qxpjzbMap = getQxpjzb(str, year);
				HashMap<String, String> ckzfMap = getCkzfzb(str, year);
				HashMap<String, String> dztdlMap = getDztdl(str, year);
				HashMap<String, String> wgjfMap = getWgjf(year, month);
				for (String user : userMap.keySet()) {
					Double wmgfcount = 0.0;
					Double fwzlcount = 0.0;
					Double gzzlcount = 0.0;
					Double wdpjcount = 0.0;
					Double qxpjcount = 0.0;
					Double ckzfcount = 0.0;
					Double dztdlcount = 0.0;
					Double wgjfcount = 0.0;
					Double count = 0.0;
					if (wmgfMap.get(user) == null) {
						wmgfcount = 0.0;
					} else {
						wmgfcount = Double.parseDouble(wmgfMap.get(user));
					}
					if (fwzlMap.get(user) == null) {
						fwzlcount = 0.0;
					} else {
						fwzlcount = Double.parseDouble(fwzlMap.get(user));
					}
					if (gzzlMap.get(user) == null) {
						gzzlcount = 0.0;
					} else {
						gzzlcount = Double.parseDouble(gzzlMap.get(user));
					}
					if (wdpjzbMap.get(user) == null) {
						wdpjcount = 0.0;
					} else {
						wdpjcount = Double.parseDouble(wdpjzbMap.get(user));
					}
					if (qxpjzbMap.get(user) == null) {
						qxpjcount = 0.0;
					} else {
						qxpjcount = Double.parseDouble(qxpjzbMap.get(user));
					}
					if (ckzfMap.get(user) == null
							|| ckzfMap.get(user).equals("")) {
						ckzfcount = 0.0;
					} else {
						ckzfcount = Double.parseDouble(ckzfMap.get(user));
					}
					if (dztdlMap.get(user) == null) {
						dztdlcount = 0.0;
					} else {
						dztdlcount = Double.parseDouble(dztdlMap.get(user));
					}
					if (wgjfMap.get(user) == null) {
						wgjfcount = 0.0;
					} else {
						wgjfcount = Double.parseDouble(wgjfMap.get(user));
					}
					count = wmgfcount + fwzlcount + gzzlcount + wdpjcount
							+ qxpjcount + ckzfcount + dztdlcount + wgjfcount;
					insert.putFieldValue("name", user);
					insert.putFieldValue("wdmc", userDeptMap.get(user));
					insert.putFieldValue("pjtime", str);
					insert.putFieldValue("wmgf", wmgfcount);
					insert.putFieldValue("fwzl", fwzlcount);
					insert.putFieldValue("gzzl", gzzlcount);
					insert.putFieldValue("szwd", wdpjcount);
					insert.putFieldValue("qxgy", qxpjcount);
					insert.putFieldValue("ckzf", ckzfcount);
					insert.putFieldValue("dztdl", dztdlcount);
					insert.putFieldValue("wgjf", wgjfcount);
					insert.putFieldValue("hjdf", count);
					insert.putFieldValue("khrs", userCount);
					list.add(insert.getSQL());
				}
				dmo.executeBatchByDS(null, list);
				result = "柜员评级成功,请点击查询查看！";
			} catch (Exception e) {
				e.printStackTrace();
				result = "柜员评级失败，请联系系统管理员！";
			}
		}

		return result;
	}

	@Override
	public String getWpkjClass(String date) throws Exception {
		List list = new ArrayList<String>();
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_wpkj_pj");
		String result = null;
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String str = getAnnual(year, month);
		HashVO[] vos = dmo.getHashVoArrayByDS(null,
				"select * from V_WN_WPKJ_PJ where pjtime='" + str + "'");
		if (vos.length > 0) {
			result = "该半年度已考核，请点击查询查看！";
		} else if (str == null) {
			result = "当前时间不在考核时间范围内！";
		}
		try {
			HashMap<String, String> userMap = dmo.getHashMapBySQLByDS(null, "");
			String userCount = dmo.getStringValueByDS(null, "");
			HashMap<String, String> userDeptMap = dmo.getHashMapBySQLByDS(null,
					"select username,deptname from v_pub_user_post_1");
			HashMap<String, String> xcdfMap = dmo.getHashMapBySQLByDS(null, "");
			HashMap<String, String> ypjfMap = dmo.getHashMapBySQLByDS(null, "");
			HashMap<String, String> jzksMap = dmo.getHashMapBySQLByDS(null, "");
			HashMap<String, String> wgjfMap = dmo.getHashMapBySQLByDS(null, "");
			for (String user : userMap.keySet()) {
				Double xcdf = 0.0;
				Double ypjf = 0.0;
				Double jzks = 0.0;
				Double wgjf = 0.0;
				Double count = 0.0;
				if (xcdfMap.get(user) == null) {
					xcdf = 0.0;
				} else {
					xcdf = Double.parseDouble(xcdfMap.get(user));
				}
				if (ypjfMap.get(user) == null) {
					ypjf = 0.0;
				} else {
					ypjf = Double.parseDouble(ypjfMap.get(user));
				}
				if (jzksMap.get(user) == null) {
					jzks = 0.0;
				} else {
					jzks = Double.parseDouble(jzksMap.get(user));
				}
				if (wgjfMap.get(user) == null) {
					wgjf = 0.0;
				} else {
					wgjf = Double.parseDouble(wgjfMap.get(user));
				}
				count = xcdf + ypjf + wgjf + xcdf;
				insert.putFieldValue("name", user);
				insert.putFieldValue("wdmc", userDeptMap.get(user));
				insert.putFieldValue("pjtime", str);
				insert.putFieldValue("xcdf", xcdf);
				insert.putFieldValue("ypjf", ypjf);
				insert.putFieldValue("jzks", jzks);
				insert.putFieldValue("wgjf", wgjf);
				insert.putFieldValue("khrs", userCount);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			result = "评级成功!请点击查询查看";
		} catch (Exception e) {
			e.printStackTrace();
			result = "评级失败，请联系管理员！";
		}
		return result;
	}

	/**
	 * 柜员累计违规积分
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private HashMap<String, String> getWgjf(String year, String month) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select A,B from wn_data_040");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 柜员所在网点电子替代率
	 * 
	 * @param str
	 * @param year
	 * @return
	 */
	private HashMap<String, String> getDztdl(String str, String year) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select a.username,b.M/100*20 from v_pub_user_post_1 a left join wn_data_041 b on a.deptname=substr(b.B,12)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 所在网点存款半年内增幅
	 * 
	 * @param str
	 * @param year
	 * @return
	 */
	private HashMap<String, String> getCkzfzb(String str, String year) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (str.contains("上")) {
				map = dmo
						.getHashMapBySQLByDS(
								null,
								"select username,c from (select username,c*20 as c from (select username,case when c>1 then 1 when c<0 then 0 else c end as c from (select a.username,b.rate as c from v_pub_user_post_1 a left join (select A,F/B as rate from excel_tab_9 where year||'-'||month='"
										+ year
										+ "-06') b on a.deptname=substr(b.A,12))))");
			} else if (str.contains("下")) {
				map = dmo
						.getHashMapBySQLByDS(
								null,
								"select username,c from (select username,c*20 as c from (select username,case when c>1 then 1 when c<0 then 0 else c end as c from (select a.username,b.rate as c from v_pub_user_post_1 a left join (select a.A,(a.C-b.C)/b.C as rate from (select A,C from excel_tab_9 where year||'-'||month='"
										+ String.valueOf((Integer.valueOf(year) - 1))
										+ "-12') a left join (select A,C from excel_tab_9 where year||'-'||month='"
										+ String.valueOf((Integer.valueOf(year) - 1))
										+ "-06') b on a.A=b.A) b on a.deptname=substr(b.A,12))))");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}

	/**
	 * 月平均工资占全辖柜员平均定量考核占比
	 * 
	 * @param date
	 * @param year
	 * @return
	 */
	private HashMap<String, String> getQxpjzb(String date, String year) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select B ,case when rate>15 then 15 else rate end as rate from (select a.b,to_char(ROUND(a.c/b.c,2),'fm9999990.9999')*15 as rate from (select B,sum(D)/5 as c from excel_tab_57 where B in (select B from (select B,count(B) as c from excel_tab_39 group by B) where c=6) group by B) a ,(select (sum(d)/5)/102 as c  from excel_tab_57 where B in (select B from (select B,count(B) as c from excel_tab_39 group by B) where c=6) ) b)");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}

	/**
	 * 月平均工资占所在网点柜员平均定量考核工资占比
	 * 
	 * @param date
	 * @param year
	 * @return
	 */
	private HashMap<String, String> getWdpjzb(String date, String year) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select B,case when rate>15 then 15 else rate end as rate from (select  a.b,to_char(ROUND(a.c/b.c,2),'fm9999990.9999')*15 as rate from (select B,sum(D)/5 as c from excel_tab_57 where B in (select B from (select B,count(B) as c from excel_tab_39 group by B) where c=6) group by B) a left join (select a.B,b.count as c from (select B,C from excel_tab_57 where B in (select B from (select B,count(B) as c from excel_tab_39 group by B) where c=6) and year||'-'||month='2019-05') a left join (select a.c,(b.count/a.count)/5 as count from (select c,count(B) as count from excel_tab_57 where B in (select B from (select B,count(B) as c from excel_tab_39 group by B) where c=6) and year||'-'||month='2019-05' group by c) a left join (select a.c,sum(b.c) as count from (select B,C from excel_tab_57 where B in (select B from (select B,count(B) as c from excel_tab_39 group by B) where c=6) and year||'-'||month='2019-05') a left join (select B,sum(D) as c from excel_tab_57 where B in (select B from (select B,count(B) as c from excel_tab_39 group by B) where c=6) group by B) b on a.b=b.b group by a.c) b on a.c=b.c) b on a.c=b.c) b on a.b=b.b)");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}

	/**
	 * 柜员工作质量得分平均分
	 * 
	 * @param str
	 * @param year
	 * @return
	 */
	private HashMap<String, String> getGzzl(String str, String year) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (str.contains("上")) {
				map = dmo.getHashMapBySQLByDS(null,
						"select B,sum(e)/6/100*10 as c from excel_tab_39 where year||'-'||month='"
								+ year + "-01' or year||'-'||month='" + year
								+ "-02' or year||'-'||month='" + year
								+ "-03' or year||'-'||month='" + year
								+ "-04' or year||'-'||month='" + year
								+ "-05' or year||'-'||month='" + year
								+ "-06' group by B");
				// map =
				// dmo.getHashMapBySQLByDS(null,"select a.B,(a.C+b.C)/6/100*10 as c from (select B,sum(E) as c from (select b,e from excel_tab_39 where year||'-'||month='"+year+"-01' or year||'-'||month='"+year+"-02' or year||'-'||month='"+year+"-03' or year||'-'||month='"+year+"-04' or year||'-'||month='"+year+"-05' or year||'-'||month='"+year+"-06') group by B) a,(select username username,sum(kouofen) as c from WN_GYPF_TABLE  where xiangmu ='总分' and pftime>='"+year+"-01-01' and pftime<='"+year+"-06-30' group by username) b where a.B=b.username");
			} else if (str.contains("下")) {
				map = dmo.getHashMapBySQLByDS(
						null,
						"select B,sum(f)/6/100*10 as c from excel_tab_39 where year||'-'||month='"
								+ String.valueOf((Integer.valueOf(year) - 1))
								+ "-07' or year||'-'||month='"
								+ String.valueOf((Integer.valueOf(year) - 1))
								+ "-08' or year||'-'||month='"
								+ String.valueOf((Integer.valueOf(year) - 1))
								+ "-09' or year||'-'||month='"
								+ String.valueOf((Integer.valueOf(year) - 1))
								+ "-10' or year||'-'||month='"
								+ String.valueOf((Integer.valueOf(year) - 1))
								+ "-11' or year||'-'||month='"
								+ String.valueOf((Integer.valueOf(year) - 1))
								+ "-12' group by B");
				// map =
				// dmo.getHashMapBySQLByDS(null,"select a.B,(a.C+b.C)/6/100*10 as c from (select B,sum(E) as c from (select b,e from excel_tab_39 where year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-07' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-08' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-09' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-10' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-11' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-12') group by B) a,(select username username,sum(kouofen) as c from WN_GYPF_TABLE  where xiangmu ='总分' and pftime>='"+String.valueOf((Integer.valueOf(year)-1))+"-07-01' and pftime<='"+String.valueOf((Integer.valueOf(year)-1))+"-12-31' group by username) b where a.B=b.username");
			}
		} catch (Exception e) {

		}
		return map;
	}

	/**
	 * 柜员服务质量得分平均分
	 * 
	 * @param str
	 * @param year
	 * @return
	 */

	private HashMap<String, String> getFwzl(String str, String year) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (str.contains("上")) {
				// map =
				// dmo.getHashMapBySQLByDS(null,"select a.B,(a.C+b.C)/24/100*10 as c from (select B,sum(F) as c from (select b,f from excel_tab_39 where year||'-'||month='"+year+"-01' or year||'-'||month='"+year+"-02' or year||'-'||month='"+year+"-03' or year||'-'||month='"+year+"-04' or year||'-'||month='"+year+"-05' or year||'-'||month='"+year+"-06') group by B) a,(select username username,sum(kouofen) as c from WN_GYPF_TABLE  where xiangmu ='总分' and pftime>='"+year+"-01-01' and pftime<='"+year+"-06-30' group by username) b where a.B=b.username");
				map = dmo.getHashMapBySQLByDS(null,
						"select B,sum(f)/6/100*10 as c from excel_tab_39 where year||'-'||month='"
								+ year + "-01' or year||'-'||month='" + year
								+ "-02' or year||'-'||month='" + year
								+ "-03' or year||'-'||month='" + year
								+ "-04' or year||'-'||month='" + year
								+ "-05' or year||'-'||month='" + year
								+ "-06' group by B");
			} else if (str.contains("下")) {
				// map =
				// dmo.getHashMapBySQLByDS(null,"select a.B,(a.C+b.C)/24/100*10 as c from (select B,sum(F) as c from (select b,f from excel_tab_39 where year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-07' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-08' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-09' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-10' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-11' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-12') group by B) a,(select username username,sum(kouofen) as c from WN_GYPF_TABLE  where xiangmu ='总分' and pftime>='"+String.valueOf((Integer.valueOf(year)-1))+"-07-01' and pftime<='"+String.valueOf((Integer.valueOf(year)-1))+"-12-31' group by username) b where a.B=b.username");
				map = dmo.getHashMapBySQLByDS(
						null,
						"select B,sum(f)/6/100*10 as c from excel_tab_39 where year||'-'||month='"
								+ String.valueOf((Integer.valueOf(year) - 1))
								+ "-07' or year||'-'||month='"
								+ String.valueOf((Integer.valueOf(year) - 1))
								+ "-08' or year||'-'||month='"
								+ String.valueOf((Integer.valueOf(year) - 1))
								+ "-09' or year||'-'||month='"
								+ String.valueOf((Integer.valueOf(year) - 1))
								+ "-10' or year||'-'||month='"
								+ String.valueOf((Integer.valueOf(year) - 1))
								+ "-11' or year||'-'||month='"
								+ String.valueOf((Integer.valueOf(year) - 1))
								+ "-12' group by B");
			}
		} catch (Exception e) {

		}
		return map;
	}

	/**
	 * 所在网点文明规范服务管理考核平均分
	 * 
	 * @param str
	 * @param year
	 * @return
	 */

	private HashMap<String, String> getWmgf(String str, String year) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (str.contains("上")) {
				map = dmo
						.getHashMapBySQLByDS(
								null,
								"select a.username,b.c/100*10 from v_pub_user_post_1 a,(select deptname,sum(koufen)/2 as c from (select * from WN_BMPF_TABLE where (to_char(to_date(pftime,'yyyy-mm-dd'),'yyyy-mm')='"
										+ year
										+ "-03' or to_char(to_date(pftime,'yyyy-mm-dd'),'yyyy-mm')='"
										+ year
										+ "-06') and xiangmu='文明客户服务部' and state='评分结束') group by deptname) b where a.deptname=b.deptname");
			} else if (str.contains("下")) {
				map = dmo
						.getHashMapBySQLByDS(
								null,
								"select a.username,b.c/100*10 from v_pub_user_post_1 a,(select deptname,sum(koufen)/2 as c from (select * from WN_BMPF_TABLE where (to_char(to_date(pftime,'yyyy-mm-dd'),'yyyy-mm')='"
										+ String.valueOf((Integer.valueOf(year) - 1))
										+ "-09' or to_char(to_date(pftime,'yyyy-mm-dd'),'yyyy-mm')='"
										+ String.valueOf((Integer.valueOf(year) - 1))
										+ "-09') and xiangmu='文明客户服务部' and state='评分结束') group by deptname) b where a.deptname=b.deptname ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取年度
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private String getAnnual(String year, String month) {
		String result = null;
		Integer years;
		String year1;
		if (month.equals("07")) {
			result = year + "年上半年";
		} else if (month == "01") {
			years = Integer.valueOf(year) - 1;
			year1 = String.valueOf(years);
			result = year1 + "年下半年";
		} else {
			result = null;
		}
		return result;
	}

	/**
	 * ZPY 客户经理等级评定计算(客户经理半年一评级)
	 * 
	 * @return
	 */
	@Override
	public String managerLevelCompute(int dateNum) {
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("WN_MANAGERDXSCORE");
			HashVO[] managerInfos = UIUtil
					.getHashVoArrayByDS(
							null,
							"SELECT CODE,NAME,STATIONKIND,DEPTNAME FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			// 获取客户经理每一项考核得分情况
			HashMap<String, Double> dqdkshlMap = getDqdkshl(dateNum);// 到期贷款收回率考核
			HashMap<String, Double> dnxzbldkMap = getDnxzbldk(dateNum);// 当年新增不良贷款
			HashMap<String, Double> shclMap = getshcl(dateNum);// 收回存量不良贷款
			HashMap<String, Double> dKsjyhMap = getDKsjyh(dateNum);// 贷款手机银行客户覆盖率
																	// 计算ok
			HashMap<String, Double> znshMap = getZnsh(dateNum);// 助农商户维护 计算ok
			HashMap<String, Double> cKyxhsMap = getCKyxhs(dateNum);// 存款有效户数提升
																	// 计算ok
			HashMap<String, Double> cKyxhsyeMap = getCKyxhsye(dateNum);// 存款有效户数余额提升
																		// 计算ok
			HashMap<String, Double> dKyexzMap = getDKyexz(dateNum);// 贷款余额新增
																	// 计算ok
			HashMap<String, Double> dKhsxzMap = getDKhsxz(dateNum);// 贷款户数新增
																	// 计算ok
			HashMap<String, Double> jdRateMap = jdRate(dateNum);// 新建档案 计算ok
			HashMap<String, Double> qnedRateMap = qnedRate(dateNum);// 黔农E贷签约
																	// 计算ok
			HashMap<String, Double> qnedtdScoreMap = qnedtdScore(dateNum);// 黔农E贷线上替代
																			// 计算ok
			HashMap<String, Double> nmgCountScoreMap = nmgCountScore(dateNum);// 农民工信息采集
																				// 计算ok
			double sum = 0;
			DecimalFormat decimal = new DecimalFormat("#.00");
			List<String> _sqlList = new ArrayList<String>();
			double dqdkshl, dnxzbldk, shcl, dKsjyh, znsh, cKyxhs, cKyxhsye, dKyexz, dKhsxz, jdRate, qnedRate, qnedtdScore, nmgCountScore;
			for (int i = 0; i < managerInfos.length; i++) {
				String manager_name = managerInfos[i].getStringValue("name");// 获取到客户经理姓名
				dqdkshl = dqdkshlMap.get(manager_name) == null ? 0 : dqdkshlMap
						.get(manager_name);// 到期贷款收回率
				dnxzbldk = dnxzbldkMap.get(manager_name) == null ? 0
						: dnxzbldkMap.get(manager_name);// 当年新增不良贷款考核
				shcl = shclMap.get(manager_name) == null ? 0.0 : shclMap
						.get(manager_name);// 收回存量
				dKsjyh = dKsjyhMap.get(manager_name) == null ? 0 : dKsjyhMap
						.get(manager_name);// 贷款手机银行客户覆盖率
				znsh = znshMap.get(manager_name) == null ? 0 : znshMap
						.get(manager_name);// 助农商户维护
				cKyxhs = cKyxhsMap.get(manager_name) == null ? 0 : cKyxhsMap
						.get(manager_name);// 存款有效户数提升
				cKyxhsye = cKyxhsyeMap.get(manager_name) == null ? 0
						: cKyxhsyeMap.get(manager_name);// 存款有效户数余额提升
				dKyexz = dKyexzMap.get(manager_name) == null ? 0 : dKyexzMap
						.get(manager_name);// 贷款余额新增
				dKhsxz = dKhsxzMap.get(manager_name) == null ? 0 : dKhsxzMap
						.get(manager_name);// 贷款户数新增
				jdRate = jdRateMap.get(manager_name) == null ? 0 : jdRateMap
						.get(manager_name);// 建档评级
				qnedRate = qnedRateMap.get(manager_name) == null ? 0
						: qnedRateMap.get(manager_name);// 黔农E贷签约
				qnedtdScore = qnedtdScoreMap.get(manager_name) == null ? 0
						: qnedtdScoreMap.get(manager_name);
				nmgCountScore = nmgCountScoreMap.get(manager_name) == null ? 0
						: nmgCountScoreMap.get(manager_name);
				sum = dqdkshl + dnxzbldk + shcl + dKsjyh + znsh + cKyxhs
						+ cKyxhsye + dKyexz + dKhsxz + jdRate + qnedRate
						+ qnedtdScore + nmgCountScore;
				insert.putFieldValue("MANAEGER_NAME", manager_name);
				insert.putFieldValue("DQDKSHL", dqdkshl);// 到期贷款收回率
				insert.putFieldValue("DNXZBLDK", dnxzbldk);// 当年新增不良贷款
				insert.putFieldValue("SHCLBLDK", shcl);// 收回存量不良贷款
				insert.putFieldValue("DKKHSJFGL", dKsjyh);// 贷款手机银行客户覆盖率
				insert.putFieldValue("ZNSHWH", znsh);// 助农商户维护
				insert.putFieldValue("CKYXHS", cKyxhs);// 存款有效户数提升
				insert.putFieldValue("CKYXHSTS", cKyxhsye);
				insert.putFieldValue("DKYEXZ", dKyexz);// 贷款余额新增
				insert.putFieldValue("DKHSXZ", dKhsxz);// 贷款户数新增
				insert.putFieldValue("XJDA", jdRate);// 建档户数新增
				insert.putFieldValue("QNEDQY", qnedRate);// 黔农E贷签约
				insert.putFieldValue("QNEDXSTD", qnedtdScore);// 黔农E贷线上替代
				insert.putFieldValue("MANAGER_LEVEL",
						nmgCountScoreMap.get(manager_name));// 黔农E贷线上替代
				insert.putFieldValue("SCORESUM",
						Double.parseDouble(decimal.format(sum)));// 总分
				insert.putFieldValue("DATE_TIME", new SimpleDateFormat(
						"yyyy-MM-dd").format(new Date()));
				insert.putFieldValue("WN_CORP",
						managerInfos[i].getStringValue("deptname"));
				_sqlList.add(insert.getSQL());
			}
			UIUtil.executeBatchByDS(null, _sqlList);
		} catch (Exception e) {
			e.printStackTrace();
			return "客户经理等级评定事失败";
		}
		return "客户经理等级计算成功";
	}

	private HashMap<String, Double> nmgCountScore(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			HashMap<String, String> manager_names = UIUtil
					.getHashMapBySQLByDS(
							null,
							"SELECT NAME,STATIONKIND FROM V_SAL_PERSONINFO  WHERE STATIONKIND LIKE '%客户经理%'");
			String date_time = getYearStartAndEnd(dateNum).get(1);
			HashMap<String, String> taskNums = UIUtil.getHashMapBySQLByDS(null,
					"select a,sum(s) from excel_tab_53 where year='"
							+ date_time.substring(0, 4) + "' group by a");
			HashMap<String, String> numMap = UIUtil
					.getHashMapBySQLByDS(
							null,
							"select j,count(a) from excel_tab_66 where  year='"
									+ date_time.substring(0, 4)
									+ "' and  b is not null and  d is not null and e is not null and  g is not null and h is not null  group by j ");
			Set<String> managerSet = manager_names.keySet();
			DecimalFormat format = new DecimalFormat("#.00");
			double score = 0.0;
			for (String manager_name : managerSet) {
				String manager_type = manager_names.get(manager_name);
				if ("城区客户经理".equals(manager_type)) {
					resultMap.put(manager_name, 0.0);
					continue;
				}
				double task = Double
						.parseDouble(taskNums.get(manager_name) == null ? "0"
								: taskNums.get(manager_name));
				double num = Double
						.parseDouble(numMap.get(manager_name) == null ? "0"
								: numMap.get(manager_name));
				System.out.println("客户经理:" + manager_name + ",农民工信息采集:实际完成"
						+ num + ",总任务数:" + task);
				if (task <= 0 || num <= 0) {
					resultMap.put(manager_name, 0.0);
				} else {
					score = Double.parseDouble(format.format(num / task * 1.0));
					resultMap.put(manager_name, score >= 1.0 ? 1.0 : score);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	// 到期贷款收回率考核
	/**
	 * 客户经理之到期贷款收回率考核 为了方便测试，dmo执行容易出现空指针异常，暂时使用dmo。 城区和乡镇:5
	 * 
	 * @param dateNum
	 * @return
	 */
	public HashMap<String, Double> getDqdkshl(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			// 到期贷款收回率考核首先获取到考核时间
			String yearStartDate = getYearStartAndEnd(dateNum).get(0);
			String checkDate = getYearStartAndEnd(dateNum).get(1);
			// 1-3月份累计已收回
			HashMap<String, String> dsMonth1_3Map = UIUtil.getHashMapBySQLByDS(
					null, "select B,D from excel_tab_44");
			// 获取到对私贷款收回到期贷款本金
			HashMap<String, String> dsBackUpMoneyMap = UIUtil
					.getHashMapBySQLByDS(
							null,
							"select bb.xd_col2 xd_col2,zj.hksum zh from(select ye.xd_col81,sum(ye.hksum) hksum from(select dk.xd_col1 xd_col1,dk.xd_col81 xd_col81,sum(dk.XD_COL6) XD_COL6,sum(hk.hksum) hksum from(select XD_COL1 hkcode,sum(XD_COL5) hksum from wnbank.S_LOAN_hk where to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')>='2019-04-01' and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')<='"
									+ checkDate
									+ "' group by XD_COL1) hk  left join (select XD_COL1,XD_COL81,XD_COL5,XD_COL6 from wnbank.S_LOAN_DK where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ checkDate
									+ "')dk on hk.hkcode=dk.XD_COL1  where dk.XD_COL5>='"
									+ yearStartDate
									+ "' and dk.XD_COL5<='"
									+ checkDate
									+ "' group by dk.xd_col1,dk.xd_col81) ye  group by ye.xd_col81) zj left join wnbank.s_loan_ryb bb on zj.xd_col81=bb.xd_col1");

			// 累计未收回金额
			HashMap<String, String> dsMoneyMap = UIUtil
					.getHashMapBySQLByDS(
							null,
							"select bb.xd_col2 xd_col2,dk.hj XD_COL7 from (select dkxj.xd_col81 xd_col81,sum(dkxj.xd_col7) hj from(select XD_COL1 XD_COL1,XD_COL81 XD_COL81,sum(XD_COL6) XD_COL6,sum(XD_COL7) XD_COL7 from wnbank.S_LOAN_DK where  XD_COL5>='"
									+ yearStartDate
									+ "' and XD_COL5<='"
									+ checkDate
									+ "' and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ checkDate
									+ "' group by XD_COL1,XD_COL81) dkxj group by dkxj.XD_COL81 )dk left join wnbank.s_loan_ryb bb on dk.xd_col81=bb.xd_col1");

			// 获取到对公累计收回到期贷款本金
			HashMap<String, String> dgBackUpMoneyMap = UIUtil
					.getHashMapBySQLByDS(
							null,
							"SELECT b.name,a.AMOUNTREPAID FROM (select dg.cus_manager cus_manager,sum(hk.AMOUNTREPAID) AMOUNTREPAID from(select cus_manager cus_manager,CUS_ID CUS_ID from wnbank.s_cmis_acc_loan where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ checkDate
									+ "' and cla<>'05' and account_status='1' and loan_balance<>'0'  group by cus_manager,CUS_ID) dg left join  wnbank.S_CMIS_ACC_BILL_REPAY hk on hk.CUS_ID=dg.CUS_ID where to_char(to_date(DATEOFREPAYMENT,'yyyy-mm-dd'),'yyyy-mm-dd')>='"
									+ yearStartDate
									+ "' and to_char(to_date(DATEOFREPAYMENT,'yyyy-mm-dd'),'yyyy-mm-dd')<='"
									+ checkDate
									+ "' group by dg.cus_manager) a LEFT JOIN (V_SAL_PERSONINFO) b ON a.cus_manager=b.code");
			HashMap<String, String> dgMoneyMap = UIUtil
					.getHashMapBySQLByDS(
							null,
							"SELECT b.NAME,a.LOAN_BALANCE FROM (select dg.cus_manager cus_manager,sum(dg.LOAN_BALANCE) LOAN_BALANCE from (select cus_manager cus_manager,sum(LOAN_BALANCE) LOAN_BALANCE from wnbank.s_cmis_acc_loan where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ checkDate
									+ "' and cla<>'05' and loan_balance<>'0' and account_status='1' and to_char(to_date(ORIG_EXPI_DATE,'yyyy-mm-dd'),'yyyy-mm-dd')>='"
									+ yearStartDate
									+ "' and to_char(to_date(ORIG_EXPI_DATE,'yyyy-mm-dd'),'yyyy-mm-dd')<='"
									+ checkDate
									+ "' group by CUS_ID,cus_manager) dg  group by dg.cus_manager) a LEFT JOIN V_SAL_PERSONINFO b ON a.cus_manager=b.code");
			String[] managers_names = UIUtil
					.getStringArrayFirstColByDS(null,
							"SELECT NAME FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			DecimalFormat format = new DecimalFormat("0.0000");
			for (int i = 0; i < managers_names.length; i++) {
				double dsBackUpMoney = Double
						.parseDouble(dsBackUpMoneyMap.get(managers_names[i]) == null
								|| dsBackUpMoneyMap.get(managers_names[i])
										.equals("") ? "0.0" : dsBackUpMoneyMap
								.get(managers_names[i]));
				double dsMoney = Double.parseDouble(dsMoneyMap
						.get(managers_names[i]) == null
						|| dsMoneyMap.get(managers_names[i]).equals("") ? "0.0"
						: dsMoneyMap.get(managers_names[i]));
				double dgBackUpMoney = Double
						.parseDouble(dgBackUpMoneyMap.get(managers_names[i]) == null
								|| dgBackUpMoneyMap.get(managers_names[i])
										.equals("") ? "0.0" : dgBackUpMoneyMap
								.get(managers_names[i]));
				double dgMoney = Double.parseDouble(dgMoneyMap
						.get(managers_names[i]) == null
						|| dgMoneyMap.get(managers_names[i]).equals("") ? "0.0"
						: dgMoneyMap.get(managers_names[i]));
				double dsMonth1_3 = Double
						.parseDouble(dsMonth1_3Map.get(managers_names[i]) == null
								|| dsMonth1_3Map.get(managers_names[i]).equals(
										"") ? "0.0" : dsMonth1_3Map
								.get(managers_names[i]));
				double dgdsMoney = dsMoney + dsBackUpMoney + dsMonth1_3
						+ dgMoney;// 应收回本金
				double backUpMoney = dsBackUpMoney + dgBackUpMoney + dsMonth1_3;// 实际收回
				System.out.println("客户经理:" + managers_names[i] + ",对私累计未收回="
						+ dsBackUpMoney + ",对私应收=" + dsMoney + ",对公收回="
						+ dgBackUpMoney + ",对公应收=" + dgMoney + "1-3月份累计收回:"
						+ dsMonth1_3);
				if (backUpMoney == 0) {
					resultMap.put(managers_names[i], 0.0);
					continue;
				}
				if (dgdsMoney == 0) {
					resultMap.put(managers_names[i], 0.0);
					continue;
				}
				double rate = Double.parseDouble(format.format(backUpMoney
						/ dgdsMoney));
				if (rate < 0.97) {
					resultMap.put(managers_names[i], 0.0);
				} else if (rate == 0.97) {
					resultMap.put(managers_names[i], 2.0);
				} else {
					double score = Double.parseDouble(format
							.format(2 + (rate - 0.97) / 0.001 * 0.1));
					resultMap
							.put(managers_names[i], score >= 5.0 ? 5.0 : score);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 客户经理绩效考核之当年新增不良贷款 为了方便测试，使用dmo查询出现空指针异常，暂时使用dmo查询，以后出现异常，在进行更正
	 * 
	 * @param dateNum
	 *            乡镇和城区:3分
	 * @return
	 */
	public HashMap<String, Double> getDnxzbldk(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			// 获取到年初和考核日期
			String checkDate = getYearStartAndEnd(dateNum).get(1);
			// 计算新增不良贷款容忍额度
			HashMap<String, String> tolerateMoneyMap = UIUtil
					.getHashMapBySQLByDS(
							null,
							"SELECT b.xd_col2,a.money FROM (SELECT xd_col81,sum(xd_col7) money FROM WNBANK.S_LOAN_DK WHERE TO_CHAR(TO_DATE(LOAD_DATES,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ checkDate
									+ "' AND xd_col7>0 GROUP BY xd_col81) a LEFT JOIN (WNBANK.S_LOAN_RYB) b ON a.xd_col81=b.xd_col1");
			// 计算新增不良贷款余额
			HashMap<String, String> addTolerateMoneyMap = UIUtil
					.getHashMapBySQLByDS(
							null,
							"SELECT bb.xd_col2,aa.money FROM (SELECT a.xd_col81,sum(a.xd_col7) money FROM (SELECT xd_col1,xd_col7,xd_col81 FROM WNBANK.s_loan_dk WHERE TO_CHAR(TO_DATE(LOAD_DATES,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ checkDate
									+ "' AND XD_COL22 IN ('03','04') AND xd_col7>0) a LEFT JOIN (SELECT xd_col1,xd_col7,XD_COL81 FROM WNBANK.S_LOAN_DK WHERE TO_CHAR(TO_DATE(LOAD_DATES,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ WnUtils.getYearEnd(checkDate)
									+ "' AND xd_col22 IN ('01','02') AND xd_col7>0) b ON a.xd_col1=b.xd_col1 WHERE b.xd_col1 IS NOT NULL GROUP BY a.xd_col81) aa LEFT JOIN (WNBANK.S_LOAN_RYB) bb ON aa.xd_col81=bb.xd_col1");
			String[] manager_names = UIUtil
					.getStringArrayFirstColByDS(null,
							"SELECT NAME FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			double rate = 0.0;
			if (checkDate.indexOf("06-30") != -1) {
				rate = 0.004;
			} else if (checkDate.indexOf("12-31") != -1) {
				rate = 0.008;
			}
			DecimalFormat format = new DecimalFormat("0.0000");
			for (int i = 0; i < manager_names.length; i++) {
				double tolerateMoney = Double.parseDouble(tolerateMoneyMap
						.get(manager_names[i]) == null ? "0.0"
						: tolerateMoneyMap.get(manager_names[i]))
						* rate;// 新增不良贷款容忍额度
				if (tolerateMoney <= 0) {
					resultMap.put(manager_names[i], 0.0);
					continue;
				}
				double addTolerateMoney = Double
						.parseDouble(addTolerateMoneyMap.get(manager_names[i]) == null ? "0.0"
								: addTolerateMoneyMap.get(manager_names[i]));
				System.out.println("客户经理:" + manager_names[i] + ",容忍度:"
						+ tolerateMoney + ",新增不良贷款:" + addTolerateMoney);
				if (addTolerateMoney <= 0) {
					resultMap.put(manager_names[i], 3.0);
					continue;
				}
				double score = Double.parseDouble(format
						.format((tolerateMoney - addTolerateMoney)
								/ tolerateMoney * 3.0));
				if (score <= 0.0) {
					resultMap.put(manager_names[i], 0.0);
				} else if (score >= 3.0) {
					resultMap.put(manager_names[i], 3.0);
				} else {
					resultMap.put(manager_names[i],
							Double.parseDouble(format.format(score)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	// 收回存量不良贷款
	/**
	 * 客户经理等级评定之收回存量不良贷款 为了方便测试，dmo查询结果为空，暂时使用dmo对sql进行查询，如果以后遇到问题，在进行更改
	 * 城区和乡镇:5分
	 * 
	 * @param dateNum
	 * @return
	 */
	public HashMap<String, Double> getshcl(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			String yearStartDate = getYearStartAndEnd(dateNum).get(0);
			String check_date = getYearStartAndEnd(dateNum).get(1);
			String yearLastEnd = getYearStartAndEnd(dateNum).get(2);
			// 实际收回不良贷款
			HashMap<String, String> backUpMoneyMap = UIUtil
					.getHashMapBySQLByDS(
							null,
							"SELECT bb.xd_col2,aa.money FROM (SELECT b.xd_col81 xd_col81,sum(a.xd_col5) money FROM (SELECT xd_col1,XD_COL5 FROM WNBANK.S_LOAN_HK WHERE TO_CHAR(TO_DATE(LOAD_DATES,'yyyy-mm-dd'),'yyyy-mm-dd')<='"
									+ check_date
									+ "'  AND TO_CHAR(TO_DATE(LOAD_DATES,'yyyy-mm-dd'),'yyyy-mm-dd')>='"
									+ yearStartDate
									+ "'  AND XD_COL16 IN ('03','04')  AND xd_col5>0) a LEFT JOIN (SELECT xd_col1,XD_COL81 FROM WNBANK.S_LOAN_DK WHERE TO_CHAR(TO_DATE(LOAD_DATES,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ check_date
									+ "') b ON a.xd_col1=b.xd_col1 GROUP BY b.xd_col81) aa LEFT JOIN (WNBANK.S_LOAN_RYB) bb ON aa.xd_col81=bb.xd_col1");
			// 存量不良贷款金额
			HashMap<String, String> clMoneyMap = UIUtil
					.getHashMapBySQLByDS(
							null,
							"SELECT B.XD_COL2, A.XD_COL7 FROM (SELECT XD_COL81,SUM(XD_COL7) XD_COL7 FROM WNBANK.S_LOAN_DK WHERE TO_CHAR(TO_DATE(LOAD_DATES,'YYYY-MM-DD'),'YYYY-MM-DD')='"
									+ yearLastEnd
									+ "' AND XD_COL7>0 AND XD_COL22 IN ('03','04') GROUP BY XD_COL81) A LEFT JOIN (WNBANK.S_LOAN_RYB ) B ON A.XD_COL81=B.XD_COL1");
			String[] manager_names = UIUtil
					.getStringArrayFirstColByDS(null,
							"SELECT NAME FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			double rate = 0.0;
			if (check_date.indexOf("06-30") != -1) {
				rate = 0.1;
			} else {
				rate = 0.2;
			}
			DecimalFormat format = new DecimalFormat("0.0000");
			for (int i = 0; i < manager_names.length; i++) {
				double backUpMoney = Double.parseDouble(backUpMoneyMap
						.get(manager_names[i]) == null ? "0.0" : backUpMoneyMap
						.get(manager_names[i]));
				if (backUpMoney <= 0) {
					resultMap.put(manager_names[i], 0.0);
					continue;
				}
				double clMoney = Double.parseDouble(clMoneyMap
						.get(manager_names[i]) == null ? "0.0" : clMoneyMap
						.get(manager_names[i]));
				System.out.println("客户经理:" + manager_names[i] + ",收回存量："
						+ backUpMoney + ",存量不良:" + clMoney);
				if (clMoney <= 0.0) {
					resultMap.put(manager_names[i], 0.0);
					continue;
				}
				double score = Double.parseDouble(format.format(backUpMoney
						/ (clMoney * rate)));
				if (score <= 0) {
					resultMap.put(manager_names[i], 0.0);
				} else if (score >= 5.0) {
					resultMap.put(manager_names[i], 5.0);
				} else {
					resultMap.put(manager_names[i], score);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 客户经理等级评定之贷款客户手机银行开户覆盖率 城区和乡镇:3分
	 * 
	 * @param dateNum
	 * @return
	 */
	public HashMap<String, Double> getDKsjyh(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {

			String yearStartDate = getYearStartAndEnd(dateNum).get(0);
			String checkDate = getYearStartAndEnd(dateNum).get(1);
			// 首先计算建档明细
			String gtjmSQL = "select yb.xd_col2 xd_col2,xx.tj tj from (select XD_COL96 XD_COL96,count(XD_COL96) tj from wnbank.s_loan_khxx where to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')>='"
					+ yearStartDate
					+ "' and to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')<='"
					+ checkDate
					+ "' and xd_col10 not in('未评级','等外','!$') and XD_COL4 in('206','908') group by XD_COL96)xx left join wnbank.s_loan_ryb yb on xx.xd_col96=yb.xd_col1";// 个体工商户、居民建档户数
			String dwzgSQL = "select yb.xd_col2 xd_col2,xx.tj tj from (select XD_COL96 XD_COL96,count(XD_COL96) tj from wnbank.s_loan_khxx where to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')>='"
					+ yearStartDate
					+ "'and to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')<='"
					+ checkDate
					+ "'and xd_col10 not in('未评级','等外','!$') and XD_COL4='907' group by XD_COL96)xx left join wnbank.s_loan_ryb yb on xx.xd_col96=yb.xd_col1";// 单位职工
			String nhSQL = "select yb.xd_col2 xd_col2,zc.tj tj  from(select xx.xd_col96 xd_col96,count(xx.xd_col96) tj from (select XD_COL1,XD_COL96 from wnbank.s_loan_khxx where to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')>='"
					+ yearStartDate
					+ "' and to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')<='"
					+ checkDate
					+ "' and xd_col10 not in('未评级','等外','!$') and XD_COL4='905') xx left join wnbank.S_LOAN_KHXXZCQK zc on xx.xd_col1=zc.xd_col1 where zc.XD_COL6!='0' group by xx.xd_col96)zc left join wnbank.s_loan_ryb yb on zc.xd_col96=yb.xd_col1";// 农户建档
			String xwSQL = "select yb.xd_col2 xd_col2,xx.tj tj from (select XD_COL96 XD_COL96,count(XD_COL96) tj from wnbank.s_loan_khxx where to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')>='"
					+ yearStartDate
					+ "' and to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')<='"
					+ checkDate
					+ "' and xd_col10 not in('未评级','等外','!$') and XD_COL4='206' and XD_COL163 not in('05','03','06','01') group by XD_COL96)xx left join wnbank.s_loan_ryb yb on xx.xd_col96=yb.xd_col1";// 小微企业建档

			String sjyh = "select b.xd_col2 name,a.card_no num from (select bbbbb.xd_col96,count(aaaaa.card_no) card_no from (select aaa.card_no card_no,aaa.manager_no manager_no from (select distinct(aa.card_no) card_no,aa.manager_no manager_no from (select a.a card_no,a.b manager_no,a.c c,b.b d from  (select * from excel_tab_12 where c!='否' and year||'-'||month='"
					+ checkDate.substring(0, checkDate.lastIndexOf("-"))
					+ "') a  left join (select * from excel_tab_49  where  year||'-'||month='"
					+ checkDate.substring(0, checkDate.lastIndexOf("-"))
					+ "') b on a.a=b.b where b.a is not null order by a.a) aa) aaa  where (to_char(sysdate,'yyyy')-substr(aaa.card_no,7,4))<=45) aaaaa left join (select xd_col7,xd_col96 from wnbank.s_loan_khxx where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
					+ checkDate
					+ "')  bbbbb on bbbbb.xd_col7=aaaaa.card_no  group by bbbbb.xd_col96) a left join wnbank.s_loan_ryb b on b.xd_col1=a.xd_col96";
			HashMap<String, String> gtjmMap = UIUtil.getHashMapBySQLByDS(null,
					gtjmSQL);
			HashMap<String, String> dwzgMap = UIUtil.getHashMapBySQLByDS(null,
					dwzgSQL);
			HashMap<String, String> nhMap = UIUtil.getHashMapBySQLByDS(null,
					nhSQL);
			HashMap<String, String> xwMap = UIUtil.getHashMapBySQLByDS(null,
					xwSQL);
			HashMap<String, String> shyhMap = UIUtil.getHashMapBySQLByDS(null,
					sjyh);
			HashVO[] managerVos = UIUtil
					.getHashVoArrayByDS(
							null,
							"SELECT CODE,NAME,STATIONKIND FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			// 获取到手机银行开户数据
			DecimalFormat format = new DecimalFormat("#.000");
			DecimalFormat scoreformat = new DecimalFormat("#.00");
			double score = 0.0;
			for (int i = 0; i < managerVos.length; i++) {
				String manager_name = managerVos[i].getStringValue("name");// 获取导客户经理姓名
				double jdSum = 0;
				jdSum = Double
						.parseDouble(gtjmMap.get(manager_name) == null ? "0"
								: gtjmMap.get(manager_name))
						+ Double.parseDouble(dwzgMap.get(manager_name) == null ? "0"
								: dwzgMap.get(manager_name))
						+ Double.parseDouble(nhMap.get(manager_name) == null ? "0"
								: nhMap.get(manager_name))
						+ Double.parseDouble(xwMap.get(manager_name) == null ? "0"
								: xwMap.get(manager_name));
				if (jdSum == 0) {
					resultMap.put(manager_name, 0.0);
					continue;
				}

				if (shyhMap.get(manager_name) == null) {
					resultMap.put(manager_name, 0.0);
				}

				// 手机银行开户数
				double sjyhNum = Double
						.parseDouble(shyhMap.get(manager_name) == null ? "0"
								: shyhMap.get(manager_name));
				double route = Double.parseDouble(format
						.format(sjyhNum / jdSum));
				System.out.println("客户经理姓名:" + manager_name + ",建档户数:" + jdSum
						+ ",手机银行开户:" + sjyhNum + ",完成比率:" + route);
				if (route == 0.2) {
					resultMap.put(manager_name, 2.0);
				} else if (route > 0.2) {
					score = 2 + (route - 0.20) / 0.001 * 0.1 > 3 ? 3.0
							: 2 + (route - 0.20) / 0.001 * 0.1;
					resultMap.put(manager_name,
							Double.parseDouble(scoreformat.format(score)));
				} else {
					score = 2 - (0.20 - route) / 0.001 * 0.2 < 0 ? 0.00
							: 2 - (0.20 - route) / 0.001 * 0.2;
					resultMap.put(manager_name,
							Double.parseDouble(scoreformat.format(score)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * WN_ZNSH_RESULT 客户经理等级评定之助农商户维护考核
	 * 声明:因为测试过程中，dmo中的方法无法执行成功，暂时使用dmo中的方法，日后出现问题，记得改正。 乡镇:3分，城区不考核此项
	 * 
	 * @param dateNum
	 * @return
	 */
	public HashMap<String, Double> getZnsh(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			HashMap<String, String> managerNamesMap = UIUtil
					.getHashMapBySQLByDS(
							null,
							"SELECT NAME,STATIONKIND FROM V_SAL_PERSONINFO  WHERE STATIONKIND LIKE '%客户经理%'");
			Set<String> managerNames = managerNamesMap.keySet();
			String yearStartDate = getYearStartAndEnd(dateNum).get(0);
			String date_time = getYearStartAndEnd(dateNum).get(1);// 考核月日期

			HashMap<String, String> managerRateMap = UIUtil
					.getHashMapBySQLByDS(
							null,
							"select aaaa.b name,count(aaaa.c) num from (select aaa.b,aaa.c from (select a.b b,a.c c,b.num num from (select b,c from excel_tab_10 where year||'-'||month='"
									+ date_time.substring(0, 7)
									+ "' ) a left join (select c,count(b) num from excel_tab_11 where year||'-'||month='"
									+ date_time.substring(0, 7)
									+ "' group by c) b on b.c=a.c) aaa left join (select aa.b b,aa.c c,count(bb.a) num from (select a,b,c from excel_tab_10 where year||'-'||month='"
									+ date_time.substring(0, 7)
									+ "' ) aa left join (select a.a,a.num,b.c,b.e from (select a,(b+c) num  from excel_tab_14 where year||'-'||month='"
									+ date_time.substring(0, 7)
									+ "') a left join (select a,b ,c,d,e from excel_tab_11 where year ||'-'|| month='"
									+ date_time.substring(0, 7)
									+ "' and d !='否') b on a.a=b.b where a.num>=b.e) bb on aa.c=bb.c  group by aa.b,aa.c)  bbb  on   aaa.b=bbb.b and aaa.c=bbb.c where aaa.num=bbb.num) aaaa group by aaaa.b");
			HashMap<String, String> gridNumMap = UIUtil.getHashMapBySQLByDS(
					null,
					"SELECT b,COUNT(C) FROM EXCEL_TAB_10 WHERE year||'-'||month='"
							+ date_time.substring(0, 7) + "' GROUP BY b");
			DecimalFormat format = new DecimalFormat("#.00");
			for (String manager_name : managerNames) {
				String manager_type = managerNamesMap.get(manager_name);
				if ("城区客户经理".equals(manager_type)) {
					resultMap.put(manager_name, 0.0);
					continue;
				}
				double managerRate = Double.parseDouble(managerRateMap
						.get(manager_name) == null ? "0" : managerRateMap
						.get(manager_name));// 客户经理达标网格数
				double gridNum = Double.parseDouble(gridNumMap
						.get(manager_name) == null ? "0" : gridNumMap
						.get(manager_name));// 客户经理累计网格数
				if (gridNum == 0) {
					resultMap.put(manager_name, gridNum);
					continue;
				}
				System.out.println("客户经理:" + manager_name + ",完成网格数:"
						+ managerRate + ",网格总数:" + gridNum + ",完成比:"
						+ (managerRate / gridNum));
				double score = (managerRate / gridNum) * 3.0;
				resultMap.put(manager_name,
						Double.parseDouble(format.format(score)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 存款有效户数余额提升 V_WN_DEPOSIT_BALANCE
	 * 为了方便测试，dmo查询出的sql为null，暂时使用dmo查询sql，以后遇见bug在更正 城区4分，乡镇3分
	 * 
	 * @param dateNum
	 * @return
	 */
	public HashMap<String, Double> getCKyxhsye(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			String yearStartDate = getYearStartAndEnd(dateNum).get(0);
			String check_date = getYearStartAndEnd(dateNum).get(1);
			HashMap<String, String> manager_names = UIUtil
					.getHashMapBySQLByDS(null,
							"SELECT name,STATIONKIND FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			HashMap<String, String> ckyeMap = UIUtil.getHashMapBySQLByDS(null,
					"SELECT  name,sum(bfb) FROM V_WN_DEPOSIT_BALANCE WHERE  date_time='"
							+ check_date + "' GROUP BY name");
			Set<String> managerSet = manager_names.keySet();
			DecimalFormat format = new DecimalFormat("#.00");
			double fullscore = 0.0;
			for (String manager_name : managerSet) {
				String manager_type = manager_names.get(manager_name);
				double rate = Double
						.parseDouble(ckyeMap.get(manager_name) == null
								|| ckyeMap.get(manager_name).equals("") ? "0.0"
								: ckyeMap.get(manager_name));
				System.out.println("客户经理:" + manager_name + ",存款余额新增完成比:"
						+ rate);
				if ("城区客户经理".equals(manager_type)) {
					fullscore = 4.0;
				} else {
					fullscore = 3.0;
				}
				if (rate <= 0) {
					resultMap.put(manager_name, 0.0);
				} else if (rate >= 1) {
					resultMap.put(manager_name, fullscore);
				} else {
					resultMap
							.put(manager_name,
									Double.parseDouble(format.format(fullscore
											* rate)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 客户经理评级之存款有效户数提升 为了方便测试，dmo查询出的结果为null值，暂时使用dmo对sql进行查询，以后出现异常在更正
	 * 乡镇5分，城区4分
	 * 
	 * @param dateNum
	 * @return
	 */
	public HashMap<String, Double> getCKyxhs(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			// 获取到当前日期
			String yearStart = getYearStartAndEnd(dateNum).get(0);// 获取到年初时间
			String check_date = getYearStartAndEnd(dateNum).get(1);// 获取到考核时间
			Map<String, String> ckhsMap = UIUtil.getHashMapBySQLByDS(null,
					"SELECT name,bfb FROM V_WN_DEPOSIT_NUMBER  WHERE  date_time='"
							+ check_date + "'");
			HashMap<String, String> manager_names = UIUtil
					.getHashMapBySQLByDS(null,
							"SELECT name,STATIONKIND FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			Set<String> manager_sets = manager_names.keySet();
			DecimalFormat format = new DecimalFormat("#.00");
			double score = 0;
			double fullscore = 0.0;
			for (String manager_name : manager_sets) {
				String manager_type = manager_names.get(manager_name);

				if (manager_type.equals("城区客户经理")) {
					fullscore = 4.0;
				} else {
					fullscore = 5.0;
				}
				double rate = Double
						.parseDouble(ckhsMap.get(manager_name) == null
								|| ckhsMap.get(manager_name).equals("") ? "0.0"
								: ckhsMap.get(manager_name));
				System.out.println("客户经理姓名:" + manager_name + ",存款户数新增完成比:"
						+ rate);
				if (rate >= 1.0) {
					resultMap.put(manager_name, score);
				} else if (rate <= 0) {
					resultMap.put(manager_name, 0.0);
				} else {
					score = Double.parseDouble(format.format(fullscore * rate));
					resultMap.put(manager_name, score >= fullscore ? fullscore
							: score);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 贷款余额新增 [V_WN_LOAN_BALANCE] 为了方便测试,dmo查询出的结果为空值，暂时使用dmo进行测试，以后遇见异常在进行更正。
	 * 城区和乡镇:3分
	 * 
	 * @return
	 */
	public HashMap<String, Double> getDKyexz(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			String yearStartDate = getYearStartAndEnd(dateNum).get(0);
			String check_date = getYearStartAndEnd(dateNum).get(1);
			String[] managers = UIUtil
					.getStringArrayFirstColByDS(null,
							"SELECT name FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			HashMap<String, String> ckyeMap = UIUtil.getHashMapBySQLByDS(null,
					"SELECT  name,bfb FROM V_WN_LOAN_BALANCE WHERE date_time='"
							+ check_date + "'");
			DecimalFormat format = new DecimalFormat("#.00");
			for (int i = 0; i < managers.length; i++) {
				double rate = Double
						.parseDouble(ckyeMap.get(managers[i]) == null
								|| ckyeMap.get(managers[i]).equals("") ? "0.0"
								: ckyeMap.get(managers[i]));
				System.out
						.println("客户经理:" + managers[i] + ",贷款余额新增完成比:" + rate);
				if (rate <= 0) {
					resultMap.put(managers[i], 0.0);
				} else if (rate >= 1) {
					resultMap.put(managers[i], 3.0);
				} else {
					resultMap.put(managers[i],
							Double.parseDouble(format.format(3.0 * rate)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 贷款户数新增 [V_WN_DEPOSIT_NUMBER]
	 * 为了方便测试，dmo在进行查询时会出现空指针异常，暂时使用dmo来进行查询，以后出现bug在改正 城区5分:乡镇:3分
	 * 
	 * @return
	 */
	public HashMap<String, Double> getDKhsxz(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			String yearStartDate = getYearStartAndEnd(dateNum).get(0);
			String check_date = getYearStartAndEnd(dateNum).get(1);
			HashMap<String, String> manager_names = UIUtil
					.getHashMapBySQLByDS(null,
							"SELECT name,STATIONKIND FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			HashMap<String, String> dkhsMap = UIUtil.getHashMapBySQLByDS(null,
					"SELECT name,BFB FROM V_WN_LOANS_NEWLY WHERE  DATE_TIME='"
							+ check_date + "'");
			Set<String> managerSet = manager_names.keySet();
			DecimalFormat format = new DecimalFormat("#.00");
			double score = 0.0;
			double fullScore = 0.0;
			for (String manager_name : managerSet) {
				String manager_type = manager_names.get(manager_name);
				if ("城区客户经理".equals(manager_type)) {
					fullScore = 5.0;
				} else {
					fullScore = 3.0;
				}
				double rate = Double
						.parseDouble(dkhsMap.get(manager_name) == null
								|| dkhsMap.get(manager_name).equals("") ? "0.0"
								: dkhsMap.get(manager_name));
				System.out.println("客户经理:" + manager_name + ",贷款户数完成比:" + rate);
				if (rate <= 0) {
					resultMap.put(manager_name, 0.0);
				} else if (rate >= 1) {
					resultMap.put(manager_name, fullScore);
				} else {
					score = fullScore * rate >= fullScore ? fullScore
							: fullScore * rate;
					resultMap.put(manager_name,
							Double.parseDouble(format.format(score)));
				}
			}
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 客户经理评级之新建档案 城区:4分，乡镇2分
	 * 为了方便测试，dmo执行sql查询时，会出现空指针异常的情况，暂时使用dmo。暂时无法测试，原因为:目标任务表中缺少建档任务
	 * 
	 * @return
	 */
	public HashMap<String, Double> jdRate(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			// 获取到年初时间
			String yearStartDate = getYearStartAndEnd(dateNum).get(0);
			String checkDate = getYearStartAndEnd(dateNum).get(1);
			// ////获取到建档评级考核数据
			String nhSQL = "select yb.xd_col2 xd_col2,zc.tj tj  from(select xx.xd_col96 xd_col96,count(xx.xd_col96) tj from (select XD_COL1,XD_COL96 from wnbank.s_loan_khxx where   to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')<='"
					+ checkDate
					+ "'  and to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')>='"
					+ yearStartDate
					+ "' and xd_col10 not in('未评级','等外','!$') and XD_COL4='905') xx left join wnbank.S_LOAN_KHXXZCQK zc on xx.xd_col1=zc.xd_col1 where zc.XD_COL6!='0' group by xx.xd_col96)zc left join wnbank.s_loan_ryb yb on zc.xd_col96=yb.xd_col1";// 农户建档
			// //个体工商户、居民建档
			String gjSQL = "select yb.xd_col2 xd_col2,xx.tj tj from (select XD_COL96 XD_COL96,count(XD_COL96) tj from wnbank.s_loan_khxx where  to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')<='"
					+ checkDate
					+ "' and to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')>='"
					+ yearStartDate
					+ "' and    xd_col10 not in('未评级','等外','!$') and XD_COL4 in('206','908') group by XD_COL96)xx left join wnbank.s_loan_ryb yb on xx.xd_col96=yb.xd_col1";
			// //单位职工建档
			String dwSQL = "select yb.xd_col2 xd_col2,xx.tj tj from (select XD_COL96 XD_COL96,count(XD_COL96) tj from wnbank.s_loan_khxx where  to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')<='"
					+ checkDate
					+ "'and to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')>='"
					+ yearStartDate
					+ "' and  xd_col10 not in('未评级','等外','!$') and XD_COL4='907' group by XD_COL96)xx left join wnbank.s_loan_ryb yb on xx.xd_col96=yb.xd_col1";
			// //小微企业建档
			String xwSQL = "select yb.xd_col2 xd_col2,xx.tj tj from (select XD_COL96 XD_COL96,count(XD_COL96) tj from wnbank.s_loan_khxx where to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')>='[考核月初日期]' and to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')<='"
					+ checkDate
					+ "' and to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')>='"
					+ yearStartDate
					+ "' and xd_col10 not in('未评级','等外','!$') and XD_COL4='206' and XD_COL163 not in('05','03','06','01') group by XD_COL96)xx left join wnbank.s_loan_ryb yb on xx.xd_col96=yb.xd_col1";
			HashMap<String, String> nhMap = UIUtil.getHashMapBySQLByDS(null,
					nhSQL);
			HashMap<String, String> gjMap = UIUtil.getHashMapBySQLByDS(null,
					gjSQL);
			HashMap<String, String> dwMap = UIUtil.getHashMapBySQLByDS(null,
					dwSQL);
			HashMap<String, String> xwMap = UIUtil.getHashMapBySQLByDS(null,
					xwSQL);
			// 获取到相应的人员信息
			HashVO[] managerVos = UIUtil
					.getHashVoArrayByDS(null,
							"SELECT NAME,STATIONKIND FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			HashMap<String, String> jobNums = UIUtil.getHashMapBySQLByDS(null,
					"SELECT A,sum(R) FROM EXCEL_TAB_53 WHERE year='"
							+ checkDate.substring(0, 4) + "' GROUP BY A");// 按照考核的工作量来做
			DecimalFormat format = new DecimalFormat("#.00");
			double score = 0.0;
			double fullScore = 0.0;
			for (int i = 0; i < managerVos.length; i++) {
				String manager_type = managerVos[i]
						.getStringValue("STATIONKIND");
				String manager_name = managerVos[i].getStringValue("NAME");
				double jobNum = Double
						.parseDouble(jobNums.get(manager_name) == null ? "0.0"
								: jobNums.get(manager_name));
				if (jobNum == 0) {
					resultMap.put(manager_name, 0.0);
					continue;
				}
				if (manager_type.equals("城区客户经理")) {// 所有建档都需要考虑
					double sum = Double
							.parseDouble(nhMap.get(manager_name) == null ? "0"
									: nhMap.get(manager_name))
							+ Double.parseDouble(gjMap.get(manager_name) == null ? "0"
									: gjMap.get(manager_name))
							+ Double.parseDouble(dwMap.get(manager_name) == null ? "0"
									: dwMap.get(manager_name))
							+ Double.parseDouble(xwMap.get(manager_name) == null ? "0"
									: xwMap.get(manager_name));
					fullScore = 4.0;
					score = sum / jobNum * fullScore;
					System.out.println("客户经理:" + manager_name + ",建档户数:" + sum);
				} else {
					double nhNum = Double
							.parseDouble(nhMap.get(manager_name) == null ? "0"
									: nhMap.get(manager_name));
					fullScore = 2.0;
					score = nhNum / jobNum * fullScore;
					System.out.println("客户经理:" + manager_name + ",农户建档户数:"
							+ nhNum);
				}
				System.out.println("客户经理:" + manager_name + ",考核得分:" + score);
				if (score <= 0) {
					resultMap.put(manager_name, 0.0);
				} else if (score >= fullScore) {
					resultMap.put(manager_name, fullScore);
				} else {
					resultMap.put(manager_name,
							Double.parseDouble(format.format(score)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 客户经理评级之黔农E贷签约 WN_QNED_RESULT 城区和乡镇分值:2分
	 * 
	 * @return
	 */
	public HashMap<String, Double> qnedRate(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			String yearStartDate = getYearStartAndEnd(dateNum).get(0);
			String checkDate = getYearStartAndEnd(dateNum).get(1);
			String[] manager_names = UIUtil
					.getStringArrayFirstColByDS(null,
							"SELECT NAME FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			HashMap<String, String> qnedMap = UIUtil.getHashMapBySQLByDS(null,
					"select c,count(b)  from excel_tab_65 where year='"
							+ checkDate.substring(0, 4) + "' group by c");
			HashMap<String, String> taskNum = UIUtil.getHashMapBySQLByDS(null,
					"select a,sum(p) from excel_tab_53 where year ='"
							+ checkDate.substring(0, 4) + "' group by  a");
			DecimalFormat decimal = new DecimalFormat("#.00");
			double score = 0.0;
			double num, tasknum, rate = 0;
			for (int i = 0; i < manager_names.length; i++) {
				num = Double
						.parseDouble(qnedMap.get(manager_names[i]) == null ? "0.0"
								: qnedMap.get(manager_names[i]));
				tasknum = Double
						.parseDouble(taskNum.get(manager_names[i]) == null ? "0.0"
								: taskNum.get(manager_names[i]));
				System.out.println("客户经理:" + manager_names[i] + ",完成任务数:"
						+ qnedMap.get(manager_names[i]) + ",任务总数:" + tasknum);
				if (num == 0 || tasknum == 0) {
					resultMap.put(manager_names[i], 0.0);
				} else {
					score = num / tasknum * 2.0 >= 2.0 ? 2.0 : num / tasknum
							* 2.0;
					resultMap.put(manager_names[i],
							Double.parseDouble(decimal.format(score)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * ZPY 客户经理之黔农E贷线上替代 为了方便测试，dmo执行sql出现空指针异常，暂时使用dmo执行sql查询，以后出现异常在更正。
	 * 
	 * @return
	 */
	public HashMap<String, Double> qnedtdScore(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			String yearStartDate = getYearStartAndEnd(dateNum).get(0);
			String checkDate = getYearStartAndEnd(dateNum).get(1);
			// 获取到期客户经理的考核
			String[] manager_names = UIUtil
					.getStringArrayFirstColByDS(null,
							"SELECT NAME FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			HashMap<String, String> qnedtd = UIUtil.getHashMapBySQLByDS(null,
					"SELECT USERNAME,RATE FROM WN_QNEDTD_RESULT WHERE DATE_TIME='"
							+ checkDate + "'");
			DecimalFormat format = new DecimalFormat("#.00");
			for (int i = 0; i < manager_names.length; i++) {
				double rate = Double
						.parseDouble(qnedtd.get(manager_names[i]) == null ? "0.0"
								: qnedtd.get(manager_names[i]));
				if (rate <= 0.0) {
					resultMap.put(manager_names[i], 0.0);
				} else {
					resultMap.put(manager_names[i], Double.parseDouble(format
							.format(rate * 2.0 > 2 ? 2.0 : rate * 2.0)));
				}
				System.out.println("客户经理:" + manager_names[i] + ",黔农E贷线上替代完成比:"
						+ rate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	// 获取年初时间
	public String getYearStart() {
		SimpleDateFormat simple = new SimpleDateFormat("yyyy");
		String yearStartDate = "";
		try {
			yearStartDate = simple.format(new Date()) + "-01-01";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return yearStartDate;
	}

	// 获取到年中时间
	public String getYearCenterDate() {
		String result = "";
		try {
			SimpleDateFormat simple = new SimpleDateFormat("yyyy");
			String year = simple.format(new Date());
			result = year + "-" + "06-30";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 获取到年末时间
	public String getYearEndDate() {
		String result = "";
		try {
			SimpleDateFormat simple = new SimpleDateFormat("yyyy");
			result = simple.format(new Date()) + "-12-31";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ZPY 获取到当前日期中的年月日
	 * 
	 * @return
	 */
	public String[] getCurrentDate() {
		String[] results = new String[3];
		try {
			Date currentDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(currentDate);
			results = date.split("-");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public List<String> getYearStartAndEnd(int dateNum) {
		List<String> list = new ArrayList<String>();
		String[] currentDate = getCurrentDate();
		String year = "";
		String month = "";
		String day = "";
		if (dateNum == 0) {// 上半年
			year = currentDate[0];
			month = "06";
			day = "30";
		} else {
			year = String.valueOf(Integer.parseInt(currentDate[0]) - 1);
			month = "12";
			day = "31";
		}
		String yearStartDate = year + "-" + "01-01";// 获取到考核年的年初日期
		String checkDate = year + "-" + month + "-" + day;// 获取到年末日期
		list.add(yearStartDate);
		list.add(checkDate);
		list.add(String.valueOf(Integer.parseInt(year) - 1) + "-12-31");
		return list;
	}

	/**
	 * 将监督查询结果插入到结果表中 ZPY 【2019-07-25】
	 * 
	 * @param sql
	 */
	@Override
	public void insertMonitorResult(String sql, Map<String, String> conditionMap) {
		try {
			sql = sql
					+ " and DAT_TXN like '"
					+ conditionMap.get("DAT_TXN").substring(0,
							conditionMap.get("DAT_TXN").lastIndexOf(";"))
					+ "%'";
			if (conditionMap.containsKey("AMT_TXN2")) {
//				String[] dateNum = conditionMap.get("AMT_TXN2").split(";");
//
//				if (dateNum.length==2) {
//					sql = sql + " and AMT_TXN<="
//							+ Double.parseDouble(dateNum[1]) * 10000
//							+ " and AMT_TXN>="
//							+ Double.parseDouble(dateNum[0]) * 10000;
//				} else {
//					sql = sql + " and AMT_TXN>='"
//							+ Double.parseDouble(conditionMap.get("AMT_TXN2").replace(";", ""))
//							* 10000 + "'";
//				}
				sql=sql +" and AMT_TXN>="+ Double.parseDouble(conditionMap.get("AMT_TXN2"))*10000;
			}
			if (conditionMap.containsKey("COD_ACCT_TITLE")) {
				sql = sql + " and COD_ACCT_TITLE like '%"
						+ conditionMap.get("COD_ACCT_TITLE") + "%'";
			}
			HashVO[] monitorResult = monitorResult = dmo.getHashVoArrayByDS(
					null, sql);
			String[] results = dmo.getStringArrayFirstColByDS(
					null,
					"select COD_ACCT_NO from WN_CURRENT_DEAL_result where  dat_txn  like '"
							+ conditionMap.get("DAT_TXN").substring(
									0,
									conditionMap.get("DAT_TXN")
											.lastIndexOf(";")) + "%'");// 员工交易账号
			List<String> resList = Arrays.asList(results);
			InsertSQLBuilder insert = new InsertSQLBuilder(
					"WN_CURRENT_DEAL_result");
			DecimalFormat format = new DecimalFormat("#.0000");
			List<String> list = new ArrayList<String>();
			for (HashVO vo : monitorResult) {//
				String codAcctNo = vo.getStringValue("COD_ACCT_NO");// 获取到当前的交易账号
				if (resList.contains(codAcctNo)) {
					continue;
				}
				insert.putFieldValue("dat_txn", vo.getStringValue("dat_txn"));// 交易日期
				insert.putFieldValue("COD_ACCT_NO", codAcctNo);// 交易账号
				insert.putFieldValue("AMT_TXN", vo.getStringValue("AMT_TXN"));// 获取到当前的交易金额
				insert.putFieldValue("AMT_TXN2", format.format(Double
						.parseDouble(vo.getStringValue("AMT_TXN")) / 10000));// 获取到当前交易金额2
				insert.putFieldValue("COD_ACCT_TITLE",
						vo.getStringValue("COD_ACCT_TITLE"));
				insert.putFieldValue("COD_CUST", vo.getStringValue("COD_CUST"));
				insert.putFieldValue("FLG_IC_TYP",
						vo.getStringValue("FLG_IC_TYP"));
				insert.putFieldValue("EXTERNAL_CUSTOMER_IC",
						vo.getStringValue("EXTERNAL_CUSTOMER_IC"));
				insert.putFieldValue("deal_result", "未处理");
				list.add(insert.getSQL());
				if (list.size() >= 5000) {
					dmo.executeBatchByDS(null, list);
					list.clear();
				}
			}
			System.out.println("当前执行的SQL:"+sql);
			if (list.size() > 0) {
				dmo.executeBatchByDS(null, list);
			}
		} catch (Exception e) {
           e.printStackTrace();
		}

	}

}
