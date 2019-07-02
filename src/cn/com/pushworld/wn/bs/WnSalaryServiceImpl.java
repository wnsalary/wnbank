package cn.com.pushworld.wn.bs;

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

import jxl.write.DateTime;

import org.apache.axis.encoding.ser.ArraySerializer;
import org.apache.catalina.startup.PasswdUserDatabase;
import org.apache.commons.httpclient.UsernamePasswordCredentials;

import com.ibm.db2.jcc.a.jl;

import cn.com.infostrategy.bs.common.CommDMO;
import cn.com.infostrategy.to.common.HashVO;
import cn.com.infostrategy.to.mdata.InsertSQLBuilder;
import cn.com.infostrategy.to.mdata.UpdateSQLBuilder;
import cn.com.infostrategy.to.mdata.formulaEngine.jepFunctions.IF;
import cn.com.infostrategy.ui.common.MessageBox;
import cn.com.pushworld.salary.bs.indexpage.MoneyTotleReport;
import cn.com.pushworld.wn.ui.WnSalaryServiceIfc;

public class WnSalaryServiceImpl implements WnSalaryServiceIfc {
	private CommDMO dmo = new CommDMO();
	private ImportDataDMO importDmo = new ImportDataDMO();

	/**
	 * zzl[2019-3-28]  暂不使用
	 * 柜员服务质量考核评分
	 */
	public String getSqlInsert(String time, int num) {
		System.out.println(time + "当前时间");
		String str = null;
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_gypf_table");
		List list = new ArrayList<String>();
		String[][] date = getTowWeiDate();
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(null, "select * from V_PUB_USER_POST_1 where POSTNAME like '%柜员%'");
			String d = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 6);
			Date t = cal.getTime();
			String day7 = new SimpleDateFormat("yyyy.MM.dd").format(t);
			String timezone = d + "~" + day7;
			for (int i = 0; i < vos.length; i++) {
				for (int j = 0; j < date.length; j++) {
					String id = dmo.getSequenceNextValByDS(null, "WN_GYPF_TABLE");
					insert.putFieldValue("id", id);
					insert.putFieldValue("username", vos[i].getStringValue("USERNAME"));
					insert.putFieldValue("usercode", vos[i].getStringValue("usercode"));
					insert.putFieldValue("userdept", vos[i].getStringValue("deptid"));
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

	//zpy
	@Override
	public String getSqlInsert(String time) {
		System.out.println(time + "当前时间");
		String str = null;
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_gypf_table");
		List list = new ArrayList<String>();
		String[][] date = getTowWeiDate();
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(null, "select * from V_PUB_USER_POST_1 where POSTNAME like '%柜员%'");
			String d = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 6);
			Date t = cal.getTime();
			String day7 = new SimpleDateFormat("yyyy.MM.dd").format(t);
			String timezone = d + "~" + day7;
			for (int i = 0; i < vos.length; i++) {
				for (int j = 0; j < date.length; j++) {
					String id = dmo.getSequenceNextValByDS(null, "WN_GYPF_TABLE");
					insert.putFieldValue("id", id);
					insert.putFieldValue("username", vos[i].getStringValue("USERNAME"));
					insert.putFieldValue("usercode", vos[i].getStringValue("usercode"));
					insert.putFieldValue("userdept", vos[i].getStringValue("deptid"));
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
		String[][] date = new String[][] { { "职业形象", "着装", "5", "", "按要求着装，形象统一、规范、整洁" }, { "职业形象", "三姿", "5", "", "站姿、坐姿、走姿端庄大方" }, { "营业准备", "及时", "3", "", "准备工作要求营业前完成" }, { "物件摆放归位整齐", "抽屉", "3", "", "除各种机具外，物品摆放必须做到规范、有序，不得随意放置，各种凭证、印章、印泥、印鉴册、剪刀、胶水、报表、信贷档案、现金尾箱、登记薄、私人物品等不得放置于桌面上；办理业务需要使用的，使用完毕后需放置于柜面下或抽屉中；未定位摆放、杂乱等不得分" },
				{ "物件摆放归位整齐", "桌面", "3", "", "除各种机具外，物品摆放必须做到规范、有序，不得随意放置，各种凭证、印章、印泥、印鉴册、剪刀、胶水、报表、信贷档案、现金尾箱、登记薄、私人物品等不得放置于桌面上；办理业务需要使用的，使用完毕后需放置于柜面下或抽屉中；未定位摆放、杂乱等不得分" }, { "物件摆放归位整齐", "钱箱", "3", "", "除各种机具外，物品摆放必须做到规范、有序，不得随意放置，各种凭证、印章、印泥、印鉴册、剪刀、胶水、报表、信贷档案、现金尾箱、登记薄、私人物品等不得放置于桌面上；办理业务需要使用的，使用完毕后需放置于柜面下或抽屉中；未定位摆放、杂乱等不得分" },
				{ "物件摆放归位整齐", "桌牌", "3", "", "除各种机具外，物品摆放必须做到规范、有序，不得随意放置，各种凭证、印章、印泥、印鉴册、剪刀、胶水、报表、信贷档案、现金尾箱、登记薄、私人物品等不得放置于桌面上；办理业务需要使用的，使用完毕后需放置于柜面下或抽屉中；未定位摆放、杂乱等不得分" }, { "卫生保洁", "点钞机", "3", "", "有积灰、污渍、纸屑等不得分" }, { "卫生保洁", "计算机", "3", "", "有积灰、污渍、纸屑等不得分" }, { "卫生保洁", "地面", "3", "", "有积灰、污渍、纸屑等不得分" }, { "卫生保洁", "窗面", "3", "", "有积灰、污渍、纸屑等不得分" },
				{ "卫生保洁", "柜面", "3", "", "有积灰、污渍、纸屑等不得分" }, { "声音", "音量", "2", "", "适中" }, { "声音", "语速", "2", "", "清晰适中" }, { "表情", "眼神", "2", "", "交流、对视" }, { "表情", "微笑", "2", "", "适时微笑" }, { "问候迎接", "呼叫", "3", "", "举手示意" }, { "问候迎接", "问候", "3", "", "首问“您好”/日常称谓，请客入座" }, { "接待办理", "义务", "5", "", "做到先外后内，办理业务执行一次性回复；对非受理范围内业务主动主动告知、引导其到相关窗口办理。" },
				{ "接待办理", "语言", "10", "", "做到热心、诚心、耐心，自觉使用“请、您好、谢谢、对不起、再见、请问您需要办理什么业务、请稍等、请您查看计数器、请您核对后在这里签字”等文明用语" }, { "接待办理", "动作", "5", "", "双手递接、掌心朝上" }, { "接待办理", "动作", "5", "", "适时主动与客户交流我社销售产品内容，介绍我社业务，创造销售机会，或请客户协助完成服务满意度评价。" }, { "送别结束", "语言", "3", "", "请问您还需要办理其他业务吗？/请慢走，再见" }, { "其他情况", "1.办业务期间不大声闲谈", "1", "", "这些情况在发生时不得分" },
				{ "其他情况", "2.指引动作手掌朝上", "1", "", "这些情况在发生时不得分" }, { "其他情况", "3.间断业务时未向顾客做请示或表歉意", "1", "", "这些情况在发生时不得分" }, { "其他情况", "4.手机未设震动", "1", "", "这些情况在发生时不得分" }, { "其他情况", "5.暂离岗位时椅子未归位", "1", "", "这些情况在发生时不得分" }, { "其他情况", "6.不必要的肢体动作过多", "1", "", "这些情况在发生时不得分" }, { "其他情况", "7.暂停业务时未放置示意牌", "1", "", "这些情况在发生时不得分" }, { "其他情况", "8.长时间业务未主动安抚客户情绪", "1", "", "这些情况在发生时不得分" },
				{ "其他情况", "9.有其他被投诉情况", "5", "", "这些情况在发生时不得分" }, { "营业结束", "卫生保洁", "5", "", "这些情况在发生时不得分" }, { "总分", "", "", "", "" } };
		return date;
	}

	/**
	 * 部门考核计划生成
	 * planid:计划id
	 */
	@Override
	public String getBMsql(String planid) {
		String str = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_BMPF_table");
			HashVO[] vos = dmo.getHashVoArrayByDS(null, "select * from sal_target_list where type='部门定性指标'");
			HashMap deptMap = getdeptName();
			InsertSQLBuilder insertSQLBuilder = new InsertSQLBuilder("wn_BMPF_table");
			List<String> list = new ArrayList<String>();
			List<String> wmsumList = new ArrayList<String>();
			List<String> djsumList = new ArrayList<String>();
			List<String> nksumList = new ArrayList<String>();
			List<String> aqsumList = new ArrayList<String>();
			//对批复时间进行修改
			String[] date = dmo.getStringArrayFirstColByDS(null, "select PFTIME from WN_BMPF_TABLE where state='评分结束'");
			String pftime = "";
			if (date == null || date.length == 0) {
				int month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
				int currentQuarter = getQuarter2(month);//根据当前时间获取到当前季度
				pftime = new SimpleDateFormat("yyyy").format(new Date()) + "-" + getQuarterEnd(currentQuarter);//获取到当前季度最后一天
			} else {
				String maxTime = dmo.getStringValueByDS(null, "SELECT max(PFTIME) PFTINE FROM WN_BMPF_TABLE WHERE STATE='评分结束'");
				int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));//获取到当前年
				int nextQuarter = getQuarter(maxTime) + 1;
				if (nextQuarter >= 5) {
					nextQuarter = 1;
					year = year + 1;
				}
				pftime = String.valueOf(year) + "-" + getQuarterEnd(nextQuarter);
			}
			for (int i = 0; i < vos.length; i++) {//每一项指标
				String deptid = vos[i].getStringValue("checkeddept");//获取到被考核部门的机构号
				deptid = deptid.replaceAll(";", ",").substring(1, deptid.length() - 1);
				String[] deptcodes = deptid.split(",");
				String xiangmu = vos[i].getStringValue("name");//项目名称
				String evalstandard = vos[i].getStringValue("evalstandard");//项目扣分描述
				String weights = vos[i].getStringValue("weights");//项目权重
				String koufen = "0.0";//扣分情况(默认是0.0)
				String state = "评分中";//当前项的评分状态:评分中
				//				String pftime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());//评分日期
				//为每个部门生成每一项考核
				for (int j = 0; j < deptcodes.length; j++) {
					if ("964".equals(deptcodes[j]) || "965".equals(deptcodes[j])) {
						continue;
					}
					//每一项考核指标都会为每一个考核部门生一个考核计划
					String deptName = deptMap.get(deptcodes[j]).toString();//获取到机构名称
					insert.putFieldValue("PLANID", planid);
					insert.putFieldValue("deptcode", deptcodes[j]);
					insert.putFieldValue("deptname", deptName);
					insert.putFieldValue("xiangmu", xiangmu);
					insert.putFieldValue("evalstandard", evalstandard);
					insert.putFieldValue("fenzhi", weights);
					insert.putFieldValue("koufen", koufen);
					insert.putFieldValue("state", state);
					insert.putFieldValue("pftime", pftime);
					insert.putFieldValue("id", dmo.getSequenceNextValByDS(null, "S_WN_BMPF_TABLE"));
					list.add(insert.getSQL());
					//为每一个部门的每一个大项生成总分
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
							insert.putFieldValue("id", dmo.getSequenceNextValByDS(null, "S_WN_BMPF_TABLE"));
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
							insert.putFieldValue("id", dmo.getSequenceNextValByDS(null, "S_WN_BMPF_TABLE"));
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
							insert.putFieldValue("id", dmo.getSequenceNextValByDS(null, "S_WN_BMPF_TABLE"));
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
							insert.putFieldValue("id", dmo.getSequenceNextValByDS(null, "S_WN_BMPF_TABLE"));
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

	//获取到部门编号
	public HashMap getdeptName() {
		HashMap hash = null;
		try {
			hash = dmo.getHashMapBySQLByDS(null, "SELECT id,NAME FROM pub_corp_dept");
		} catch (Exception e) {
			hash = new HashMap();
			e.printStackTrace();
		}
		return hash;
	}

	@Override
	public String gradeBMScoreEnd() {//结束打分计划 算总分 改状态
		try {
			String[] csName = dmo.getStringArrayFirstColByDS(null, "SELECT DISTINCT(xiangmu) FROM WN_BMPF_TABLE WHERE fenzhi IS NULL");
			HashMap codeVos = dmo.getHashMapBySQLByDS(null, "SELECT DISTINCT(SUBSTR(name,0,INSTR(NAME,'-')-1)) name,CHECKEDDEPT FROM sal_target_list  WHERE TYPE ='部门定性指标'");
			UpdateSQLBuilder update = new UpdateSQLBuilder("WN_BMPF_TABLE");
			UpdateSQLBuilder update2 = new UpdateSQLBuilder("WN_BMPF_TABLE");
			List<String> list = new ArrayList<String>();

			for (int i = 0; i < csName.length; i++) {//遍历每一个评分大项（文明 党建  案防 安全）
				String deptcodes = codeVos.get(csName[i]).toString();
				deptcodes = deptcodes.substring(1, deptcodes.lastIndexOf(";"));
				String[] codes = deptcodes.split(";");//机构号
				for (int j = 0; j < codes.length; j++) {//每个大项每个机构每个小项  修改状态，计算总分
					if ("964".equals(codes[j]) || "965".equals(codes[j])) {
						continue;
					}
					String sql = "select * from WN_BMPF_TABLE where xiangmu like '" + csName[i] + "%' and deptcode='" + codes[j] + "' order by fenzhi";
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
						if (KOUFEN == null || KOUFEN.isEmpty() || "".equals(KOUFEN)) {
							KOUFEN = "0.0";
						}
						if (Double.parseDouble(KOUFEN) > Double.parseDouble(FENZHI)) {
							KOUFEN = FENZHI;
						}
						result = result + Double.parseDouble(KOUFEN);
						update.setWhereCondition("1=1 and deptcode='" + codes[j] + "' and xiangmu like '" + csName[i] + "%' and state='评分中'");
						update.putFieldValue("state", "评分结束");
						update.putFieldValue("KOUFEN", KOUFEN);
						list.add(update.getSQL());
					}
					update2.setWhereCondition("1=1 and deptcode='" + codes[j] + "' and xiangmu='" + csName[i] + "' and fenzhi is null");
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

	//date格式:2019-01-01
	public int getQuarter(String date) {//根据date获取到当前季度
		date = date.substring(5);
		if ("03-31".equals(date)) {
			return 1;
		} else if ("06-30".equals(date)) {
			return 2;
		} else if ("09-30".equals(date)) {
			return 3;
		} else if ("12-31".equals(date)) {
			return 4;
		} else {//处理一切输入不合理的情况
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
	public String ImportAll() {//全量数据导入
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
	public String getChange() {
		Date date = new Date();
		Calendar scal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
		scal.setTime(date);
		scal.set(Calendar.DAY_OF_MONTH, 31);
		scal.add(Calendar.MONTH, -2);//取当前日期的后一天. 
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String smonth = df.format(scal.getTime());//上月的月数
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		//		cal.add(Calendar.MONTH, -1);//取当前日期的后一天. 
		String kmonth = df.format(cal.getTime());//考核月的月数
		StringBuffer sb = new StringBuffer();
		try {
			UpdateSQLBuilder update = new UpdateSQLBuilder("wnbank.s_loan_dk");
			List list = new ArrayList<String>();
			//客户经理的信息表map
			HashMap<String, String> map = dmo.getHashMapBySQLByDS(null, "select xd_col1,xd_col2 from wnbank.s_loan_ryb");
			//考核月的客户证件和客户经理号map
			HashMap<String, String> kmap = dmo.getHashMapBySQLByDS(null, "select distinct(dk.xd_col16),dk.XD_COL81 from wnbank.s_loan_dk dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + kmonth + "' and XD_COL7<>0");
			//上月的客户证件和客户经理号map
			HashMap<String, String> smap = dmo.getHashMapBySQLByDS(null, "select distinct(dk.xd_col16),dk.XD_COL81 from wnbank.s_loan_dk dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + smonth + "' and XD_COL7<>0");
			for (String str : kmap.keySet()) {
				if (kmap.get(str).equals(smap.get(str))) {
					continue;
				} else {
					update.setWhereCondition("to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + smonth + "' and xd_col16='" + str + "'");
					update.putFieldValue("XD_COL81", kmap.get(str));
					list.add(update.getSQL());
					sb.append(smonth + "客户证件号为[" + str + "]客户经理为[" + map.get(smap.get(str)) + "]与考核月的客户经理信息不符，故修改客户经理为[" + map.get(kmap.get(str)) + "]  \n");
				}
				if (list.size() > 5000) {//zzl 1000 一提交
					dmo.executeBatchByDS(null, list);
					list.clear();
				}
			}
			dmo.executeBatchByDS(null, list);
		} catch (Exception e) {
			sb.append("客户经理信息变更失败");
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		//		Date date = new Date();
		//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
		//		cal.setTime(date);
		//		cal.set(Calendar.DAY_OF_MONTH, 0);
		//		//		cal.add(Calendar.MONTH, -1);//取当前日期的后一天. 
		//		String kmonth = df.format(cal.getTime());//考核月的月数
		//		System.out.println(">>>>>>>>>>>" + kmonth);
		//
		//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		//		try {
		//			cal.setTime(format.parse("2019-5-24"));
		//		} catch (ParseException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		cal.add(Calendar.MONTH, -1);
		//		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		//		Date otherDate = cal.getTime();
		//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//		System.out.println(">>>>>>>>>>>>>>>>"+dateFormat.format(otherDate));
	}

	/**
	 * zzl
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
	 * zzl[存款客户经理信息变更]
	 * 
	 */
	@Override
	public String getCKChange() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		//		cal.add(Calendar.MONTH, -1);//取当前日期的后一天. 
		String kmonth = df.format(cal.getTime());//考核月的月数
		StringBuffer sb = new StringBuffer();
		try {
			UpdateSQLBuilder update = new UpdateSQLBuilder("wnbank.s_loan_hkxx");
			List list = new ArrayList<String>();
			//客户经理的信息表map
			HashMap<String, String> map = dmo.getHashMapBySQLByDS(null, "select xd_col1,xd_col2 from wnbank.s_loan_ryb");
			//考核月的客户证件和客户经理号map
			HashMap<String, String> kmap = dmo.getHashMapBySQLByDS(null, "select XD_COL7,XD_COL96 from wnbank.S_LOAN_KHXX where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + kmonth + "' and XD_COL7 is not null and XD_COL96 is not null");
			//上月的客户证件和客户经理号map
			HashMap<String, String> smap = dmo.getHashMapBySQLByDS(null, "select distinct(dk.xd_col16),dk.XD_COL81 from wnbank.s_loan_dk dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='2018-12-31' and XD_COL7<>0");
			for (String str : kmap.keySet()) {
				if (kmap.get(str).equals(smap.get(str))) {
					continue;
				} else {
					update.setWhereCondition("to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='2018-12-31' and xd_col16='" + str + "'");
					update.putFieldValue("XD_COL81", kmap.get(str));
					list.add(update.getSQL());
					sb.append("2018-12-31客户证件号为[" + str + "]客户经理为[" + map.get(smap.get(str)) + "]与考核月的客户经理信息不符，故修改客户经理为[" + map.get(kmap.get(str)) + "]  \n");
				}
				if (list.size() > 5000) {//zzl 1000 一提交
					dmo.executeBatchByDS(null, list);
					list.clear();
				}
			}
			dmo.executeBatchByDS(null, list);
		} catch (Exception e) {
			sb.append("客户经理信息变更失败");
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * zpy[2019-05-22]
	 * 为每个柜员生成定性考核计划
	 */
	@Override
	public String gradeDXscore(String id) {
		String result = "";
		String planName = "";
		try {
			//获取到柜员相关信息
			HashVO[] vos = dmo.getHashVoArrayByDS(null, "select * from V_PUB_USER_POST_1 where POSTNAME like '%柜员%'");
			//获取到柜员定性考核指标
			HashVO[] dxzbVos = dmo.getHashVoArrayByDS(null, "select * from sal_person_check_list where 1=1  and (type='43')  and (1=1)  order by seq");
			List<String> _sqlList = new ArrayList<String>();
			String sql = "";
			String maxTime = dmo.getStringValueByDS(null, "select max(plantime) from WN_GYDXPLAN");
			planName = dmo.getStringValueByDS(null, "select planname from WN_GYDXPLAN where plantime='" + maxTime + "'");
			String pftime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
			for (int i = 0; i < vos.length; i++) {
				String gyName = vos[i].getStringValue("username");//获取到柜员名称
				String gyDeptCode = vos[i].getStringValue("deptcode");//获取到当前用户的机构号
				String gyUserCode = vos[i].getStringValue("usercode");//获取到当前柜员的柜员号
				for (int j = 0; j < dxzbVos.length; j++) {
					String xiangmu = dxzbVos[j].getStringValue("CATALOG");//考核项目
					String khsm = dxzbVos[j].getStringValue("name");//考核说明
					String fenzhi = dxzbVos[j].getStringValue("weights");

					sql = "insert into wn_gydx_table(id,username,userdept,usercode,xiangmu,khsm,fenzhi,koufen,state,pftime)" + "  values('" + id + "','" + gyName + "','" + gyDeptCode + "','" + gyUserCode + "','" + xiangmu + "','" + khsm + "','" + fenzhi + "','0.0','评分中','" + pftime + "')";
					_sqlList.add(sql);
				}
				sql = "insert into wn_gydx_table(id,username,userdept,usercode,xiangmu,khsm,fenzhi,state,pftime) values('" + id + "','" + gyName + "','" + gyDeptCode + "','" + gyUserCode + "','总分','总分','0.0','评分中','" + pftime + "')";
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
	public String gradeDXEnd(String planid) {//结束当前考核计划:1.计算分值;2.修改状态;
		String result = "";
		try {
			//获取到当前所有柜员
			HashVO[] vos = dmo.getHashVoArrayByDS(null, "select * from V_PUB_USER_POST_1 where POSTNAME like '%柜员%'");
			//HashMap<String,String> gyMap = dmo.getHashMapBySQLByDS(null, "select usercode,deptcode from V_PUB_USER_POST_1 where POSTNAME like '%柜员%'");
			//需要提示客户，是否需要进行结束
			HashVO[] hvos = dmo.getHashVoArrayByDS(null, "SELECT * FROM wn_gydx_table WHERE STATE='评分中' OR FHRESULT IS NULL OR FHRESULT='复核未通过'");
			if (hvos.length > 0) {//在评分未完成的情况下结束
				double koufen = 0.0;
				double fenzhi = 0.0;
				String xiangmu = "";
				String khsm = "";
				UpdateSQLBuilder update = new UpdateSQLBuilder("wn_gydx_table");
				UpdateSQLBuilder update2 = new UpdateSQLBuilder("wn_gydx_table");
				List<String> sqlList = new ArrayList<String>();
				for (int i = 0; i < vos.length; i++) {//按照柜员进行遍历
					double sum = 100.0;
					String maxTime = dmo.getStringValueByDS(null, "select max(pftime) from wn_gydx_table where usercode='" + vos[i].getStringValue("usercode") + "'");
					String sql = "select * from  wn_gydx_table where usercode='" + vos[i].getStringValue("usercode") + "' and (state='评分中' or fhresult is null  or fhresult='复核未通过') and pftime='" + maxTime + "'";
					HashVO[] gyVos = dmo.getHashVoArrayByDS(null, sql);
					if (gyVos.length <= 0) {
						continue;
					}

					for (int j = 0; j < gyVos.length; j++) {//柜员定性得分结束
						xiangmu = gyVos[j].getStringValue("xiangmu");
						khsm = gyVos[j].getStringValue("khsm");
						if ("总分".equals(xiangmu)) {
							continue;
						}
						fenzhi = Double.parseDouble(gyVos[j].getStringValue("fenzhi"));
						//						if(gyVos[j].getStringValue("koufen")==null || gyVos[j].getStringValue("koufen").isEmpty()){
						//							koufen=fenzhi;
						//						}
						koufen = fenzhi;
						sum -= koufen;
						update.setWhereCondition("usercode='" + vos[i].getStringValue("usercode") + "' and khsm='" + khsm + "' and (state='评分中' or fhresult is null  or fhresult='复核未通过') ");
						update.putFieldValue("koufen", koufen);
						update.putFieldValue("state", "评分结束");
						sqlList.add(update.getSQL());
					}
					update2.setWhereCondition("usercode='" + vos[i].getStringValue("usercode") + "' and khsm='总分' and (state='评分中' or fhresult is null  or fhresult='复核未通过')");
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
			} else {//直接结束
				result = "该项打分计划结束成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}

	//修改当前考核人当前考核项的评分(未评分的状态下)
	public void updatePF(String usercode, String khsm, double fenzhi, String pfusercode) {
		try {
			String sql = "update  wn_gydx_table set pfusercode='" + pfusercode + "',koufen='" + fenzhi + ",state='评分结束'' where usercode='" + usercode + "' and state='评分中' and khsm='" + khsm + "'";
			dmo.executeUpdateByDS(null, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//未复核的状态下修改评分
	public void updateFH(String usercode, String khsm, double fenzhi, String fhusercode) {
		try {
			String sql = "update  wn_gydx_table set fhusercode='" + fhusercode + "',koufen='" + fenzhi + ",state='评分结束'' where usercode='" + usercode + "' and state='评分中' and khsm='" + khsm + "'";
			dmo.executeUpdateByDS(null, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String gradeManagerDXscore(String id) {
		String result = "";
		try {
			//获取到考核月时间
			SimpleDateFormat monthFormat=new SimpleDateFormat("MM");
			SimpleDateFormat yearFormat=new SimpleDateFormat("yyyy");
			int month=Integer.parseInt(monthFormat.format(new Date()));
			int year=Integer.parseInt(yearFormat.format(new Date()));
			String lastMonth="";
			if(month==1){
				month=12;
				year=year-1;
			}else{
				month=month-1;
			}
			String monthStr=String.valueOf(month);
			lastMonth=year+"-"+(monthStr.length()==1?"0"+monthStr:monthStr);
			//获取到客户经理的信息
			HashVO[] managerVos = dmo.getHashVoArrayByDS(null, "SELECT * FROM V_PUB_USER_POST_1 WHERE POSTNAME LIKE '%客户经理%'");
			//获取到客户经理定性考核计划
			HashVO[] vos = dmo.getHashVoArrayByDS(null, "select * from sal_person_check_list where 1=1  and (type='61')  and (1=1)  order by seq");
			List<String> _sqlList = new ArrayList<String>();
			for (int i = 0; i < managerVos.length; i++) {
				String username = managerVos[i].getStringValue("username");
				String usercode = managerVos[i].getStringValue("usercode");
				String deptcode = managerVos[i].getStringValue("deptcode");
				InsertSQLBuilder insert = new InsertSQLBuilder("wn_managerdx_table");
				for (int j = 0; j < vos.length; j++) {//获取到每一项考核计划指标
					String weight = vos[j].getStringValue("WEIGHTS");//获取到指标的考核权重
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
					insert.putFieldValue("pftime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
					insert.putFieldValue("khMonth", lastMonth);
					_sqlList.add(insert.getSQL());
				}
				InsertSQLBuilder insert2 = new InsertSQLBuilder("wn_managerdx_table");
				insert2.putFieldValue("planid", id);
				insert2.putFieldValue("username", username);
				insert2.putFieldValue("usercode", usercode);
				insert2.putFieldValue("userdept", deptcode);
				insert2.putFieldValue("fenzhi", 100.0);
				insert2.putFieldValue("state", "评分中");
				insert2.putFieldValue("xiangmu", "总分");
				insert2.putFieldValue("pftime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
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
			//查询所有未结束评分的客户经理
			HashVO[] vos = dmo.getHashVoArrayByDS(null, "select * from wn_managerdx_table where  state='评分中'");
			if (vos.length <= 0) {
				result = "当前考核计划结束成功";
			} else {//尚未结束的考核人员,直接将考核人员的分数设置成0分
				UpdateSQLBuilder update = new UpdateSQLBuilder("wn_managerdx_table");
				List<String> _sqllist = new ArrayList<String>();
				Map<String, Double> map=new HashMap<String, Double>();
				for (int i = 0; i < vos.length; i++) {
					String xiangmu = vos[i].getStringValue("xiangmu");
					//					if("总分".equals(xiangmu)){continue;}
					String fenzhi = vos[i].getStringValue("fenzhi");
					String khsm = vos[i].getStringValue("khsm");
					String usercode = vos[i].getStringValue("usercode");
					String koufen=vos[i].getStringValue("koufen");
					if(koufen==null || "".isEmpty() || Double.parseDouble(fenzhi)<Double.parseDouble(koufen)){
						koufen=fenzhi;
					}
					if(!map.containsKey(usercode)){
						map.put(usercode, Double.parseDouble(koufen));
					}else{
						map.put(usercode, map.get(usercode)+Double.parseDouble(koufen));
					}
					update.setWhereCondition("usercode='" + usercode + "' and khsm='" + khsm + "' and state='评分中'");
					update.putFieldValue("koufen", koufen);
					update.putFieldValue("state", "评分结束");
					_sqllist.add(update.getSQL());
				}
				
				Set<String> userCodeSet = map.keySet();
				for (String usercode : userCodeSet) {
					String sql="update wn_managerdx_table set fenzhi='"+map.get(usercode)+"' where state='评分中'  and usercode='"+usercode+"' and xiangmu='总分'";
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
			//得到客户经理的Map
			HashMap<String, String> userMap = dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			//得到客户经理的任务数
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null, "select A,sum(D) from EXCEL_TAB_53 where year||'-'||month='" + date.substring(0, 7) + "' group by A");
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
	 * zzl
	 * 贷款管户数-农户Map
	 * @return
	 */
	public HashMap<String, String> getDKNH(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date + "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 贷款管户数-内部职工Map
	 * @return
	 */
	public HashMap<String, String> getDKNBZG(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null,
					"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('26','19') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + date
							+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 贷款管户数-按揭客户Map
	 * @return
	 */
	public HashMap<String, String> getDKAJ(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null,
					"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('20','29','28','37','h01','h02') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
							+ date + "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 贷款管户数-一般自然人Map
	 * @return
	 */
	public HashMap<String, String> getDKYBZRR(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 not in ('20','29','28','37','h01','h02','26','19','16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date + "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 贷款管户数-企业Map
	 * @return
	 */
	public HashMap<String, String> getDKQY(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select dg.cus_manager cus_manager,count(dg.cus_manager) countxj from (select distinct(xx.cert_code),dg.cus_manager  from wnbank.s_cmis_acc_loan dg left join wnbank.s_cmis_cus_base xx on dg.cus_id=xx.cus_id  where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + date
					+ "'and dg.cla<>'05' and account_status='1' and loan_balance<>'0')dg group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public String getQnedRate(String date_time) {
		try {
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null, "select A,sum(P) from EXCEL_TAB_53 where year||'年'||month||'月'='" + date_time + "' group by A");
			if (rwMap.size() <= 0) {
				return "当月【" + date_time + "】中没有数据,请更换查询时间";
			}
			//黔农E贷线上替代
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_qned_result");
			date_time = date_time.replace("年", "-").replace("月", "");
			HashMap<String, String> userMap = dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			HashMap<String, String> jlMap = dmo.getHashMapBySQLByDS(null, "SELECT E,COUNT(E) FROM V_qnedqy_zpy where date_time='" + date_time + "' GROUP BY E");
			Set<String> keys = userMap.keySet();
			List<String> sqllist = new ArrayList<String>();
			DecimalFormat format = new DecimalFormat("#00.00");
			for (String key : keys) {
				insert.putFieldValue("username", key);
				insert.putFieldValue("date_time", getLastMonth(date_time) );
				double task = Double.parseDouble(rwMap.get(key) == null ? "0" : rwMap.get(key));
				double passed = Double.parseDouble(jlMap.get(key) == null ? "0" : jlMap.get(key));
				insert.putFieldValue("passed", passed);
				insert.putFieldValue("task", task);
				if (task == 0 || passed == 0) {
					insert.putFieldValue("rate", "00.00");
				} else {
					insert.putFieldValue("rate", format.format(passed / task * 100));
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
	 * date_time:2019-01-01
	 */
	@Override
	public String getQnedtdRate(String date_time) {
		String result = "";
		try {
			System.out.println("黔农E贷线上替代查询时间:" + date_time);
			//对查询日期进行处理
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null, "select A,sum(Q) from EXCEL_TAB_53 where year||'-'||month='" + date_time + "'  group by A");
			if (rwMap.size() <= 0) {
				return "当前考核月【" + date_time + "】缺少业务数据,请更换日期进行查询";
			}
			HashMap<String, String> userMap = dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			Set<String> keys = userMap.keySet();
			String lastMonth = getSMonthDate(date_time);
			HashMap<String, String> currMonthData = dmo.getHashMapBySQLByDS(null, "SELECT a.mname mname, count(a.CARDNO) CARDNO   FROM (SELECT DISTINCT(CARDNO) CARDNO,MNAME FROM wn_qnedtd WHERE  date_time  LIKE '" + date_time + "%') a GROUP BY  a.mname");//当前考核月所做的绩效
			HashMap<String, String> lastMonthData = dmo.getHashMapBySQLByDS(null, "SELECT a.mname mname, count(a.CARDNO) CARDNO   FROM (SELECT DISTINCT(CARDNO) CARDNO,MNAME FROM wn_qnedtd WHERE  date_time  LIKE '" + lastMonth + "%') a GROUP BY  a.mname");//上一个月所做的绩效
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_qnedtd_result");
			DecimalFormat format = new DecimalFormat("00.00");
			List<String> sqllist = new ArrayList<String>();
			for (String key : keys) {
				insert.putFieldValue("username", key);
				double task = Double.parseDouble(rwMap.get(key) == null ? "0" : rwMap.get(key));
				insert.putFieldValue("task", task);
				double curretData = Double.parseDouble(currMonthData.get(key) == null ? "0" : currMonthData.get(key));
				double lastData = Double.parseDouble(lastMonthData.get(key) == null ? "0" : lastMonthData.get(key));
				insert.putFieldValue("passed", (curretData - lastData));
				if (task == 0 || (curretData - lastData) == 0) {
					insert.putFieldValue("rate", "00.00");
				} else {
					insert.putFieldValue("rate", format.format((curretData - lastData) / task * 100.0));
				}
				insert.putFieldValue("date_time", getLastMonth(date_time) );
				sqllist.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, sqllist);
			//			InsertSQLBuilder insert = new InsertSQLBuilder("wn_qnedtd_result");
			//			insert.putFieldValue("date_time", date_time);

			//			String date = date_time.substring(0, 7);//获取到当前时间
			//			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null, "select A,sum(Q) from EXCEL_TAB_53 where year||'-'||month='" + date + "' and A='" + username + "' group by A");
			//			if (rwMap.size() <= 0) {
			//				return "当前客户经理【" + username + "】在当前月【" + date + "】缺少业务数据,请更换日期进行查询";
			//			}
			//			String deleteSQL = "delete from wn_qnedtd_result where username='" + username + "' and date_time='" + date + "'";
			//			InsertSQLBuilder insert = new InsertSQLBuilder("wn_qnedtd_result");
			//			insert.putFieldValue("username", username);
			//			insert.putFieldValue("date_time", date);
			//			//获取到当前客户经理所做的任务数
			//			String countString = dmo.getStringValueByDS(null, "select count(cardno) num from ( SELECT DISTINCT(cardno) cardno FROM wn_qnedtd WHERE mname='" + username + "' AND date_time='" + date_time + "')");
			//			double count = Double.parseDouble(countString == null ? "0" : countString);
			//			insert.putFieldValue("passed", count);
			//			double task = Double.parseDouble(rwMap.get(username));
			//			insert.putFieldValue("task", task);
			//			insert.putFieldValue("rate", count / task);
			//			List<String> sqlList = new ArrayList<String>();
			//			sqlList.add(deleteSQL);
			//			sqlList.add(insert.getSQL());
			//			dmo.executeBatchByDS(null, sqlList);//
						result = "查询成功";
		} catch (Exception e) {
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
//			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null, "select A,sum(O) from EXCEL_TAB_53 where year||'-'||month='" + date_time + "' and A='" + username + "' group by A");
//			if (rwMap.size() <= 0) {
//				return "当前客户经理【" + username + "】在当前月【" + date_time + "】缺少业务数据,请更换日期进行查询";
//			}
//			String deleteSQL = "delete from wn_sjyh_result where username='" + username + "' and date_time='" + date_time + "'";//
//			InsertSQLBuilder insert = new InsertSQLBuilder("wn_sjyh_result");
//			insert.putFieldValue("date_time", date_time);
//			insert.putFieldValue("username", username);
//			//获取到当前客户经理当月任务数
//			double task = Double.parseDouble(rwMap.get(username));
//			insert.putFieldValue("task", task);
//			String countString = dmo.getStringValueByDS(null, "SELECT COUNT(*) FROM   V_sjyh  WHERE date_time='" + date_time + "' AND xd_col2='" + username + "'");
//			double count = Double.parseDouble(countString == null ? "0" : countString);
//			insert.putFieldValue("passed", count);
//			insert.putFieldValue("rate", count / task);
//			List<String> list = new ArrayList<String>();
//			list.add(deleteSQL);
//			list.add(insert.getSQL());
//			dmo.executeBatchByDS(null, list);
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null, "select A,sum(O) from EXCEL_TAB_53 where year||'-'||month='" + date_time + "' group by A");
			if (rwMap.size() <= 0) {
				return "在当前月【" + date_time + "】缺少业务数据,请更换日期进行查询";
			}
			HashMap<String, String> userMap =dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			HashMap<String,String>jlMap = dmo.getHashMapBySQLByDS(null, "SELECT XD_COL2,count(A) FROM V_sjyh WHERE DATE_TIME='"+date_time+"' GROUP BY XD_COL2");
			Set<String> keys = userMap.keySet();
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_sjyh_result");
			List<String> list=new ArrayList<String>();
			for (String key : keys) {
				insert.putFieldValue("username", key);
				insert.putFieldValue("date_time", getLastMonth(date_time));
				double task=Double.parseDouble(rwMap.get(key)==null || rwMap.get(key).length()==0?"0":rwMap.get(key));
				insert.putFieldValue("task", task);
				double passed=Double.parseDouble(jlMap.get(key)==null?"0":jlMap.get(key));
				insert.putFieldValue("passed", passed);
				DecimalFormat format=new DecimalFormat("00.00");
				if(task==0 || passed==0){
					insert.putFieldValue("rate", "00.00");
				}else{
					insert.putFieldValue("rate",format.format(passed/task*100) );
				}
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null,list);
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
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null, "select A,sum(L) from EXCEL_TAB_53 where year||'-'||month='" + date_time + "' group by A");
			if (rwMap.size() <= 0) {
				return "在当前月【" + date_time + "】缺少业务数据,请更换日期进行查询";
			}
			HashMap<String,String> jlMap = dmo.getHashMapBySQLByDS(null, "SELECT a.name,a.num num FROM (select bbbb.b name,count(bbbb.b)  num  from (select aaa.c as c,aaa.n as n from (select aa.c,count(aa.c) as n from (select b.b as b ,b.c as c,b.e as e from (select * from excel_tab_10 where year||'-'||month='" + date_time + "' ) a left join (select * from excel_tab_11 where year||'-'||month='" + date_time
										+ "' and d='是' or d is null) b on b.c=a.c  where b.b is not null) aa left join (select a,(b+c) as num  from excel_tab_14 where year||'-'||month='" + date_time + "') bb on aa.b=bb.a where bb.num>=aa.e group by aa.c ) aaa left join (select c,count(c) as n from excel_tab_11 where year||'-'||month='" + date_time
										+ "'  and d='是' or d is null group by c ) bbb on bbb.c=aaa.c where aaa.n>=bbb.n) aaaa left join (select b,c from excel_tab_10 where  year||'-'||month='" + date_time + "')  bbbb on aaaa.c=bbbb.c  WHERE bbbb.b NOT  IN ('何超','施宗华','陈举','余崇玺','李顺苍','朱锡刚','罗尧')   group by bbbb.b) a ");
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_znsh_result");
			HashMap<String, String> userMap =dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			Set<String> keys = userMap.keySet();
			List<String> sqllist=new ArrayList<String>();
			for (String key : keys) {
				insert.putFieldValue("username", key);
				double task=Double.parseDouble(rwMap.get(key)==null || rwMap.get(key).length()==0  ?"0":rwMap.get(key));
				insert.putFieldValue("task", task);
				double passed=Double.parseDouble(jlMap.get(key)==null?"0":jlMap.get(key));
				insert.putFieldValue("passed", passed);
			    DecimalFormat format=new DecimalFormat("00.00");
				if(task==0 || passed==0){
			    	insert.putFieldValue("rate", "00.00");
			    }else{
			    	insert.putFieldValue("rate", format.format(passed/task*100));
			    }
				insert.putFieldValue("date_time", getLastMonth(date_time));
				sqllist.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null,sqllist);
			result = "查询成功";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getTyxwRate(String date_time) {
		String result = "";
		try {
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null, "select A,sum(N) from EXCEL_TAB_53 where year||'-'||month='" + date_time + "' group by A");
			if (rwMap.size() <= 0) {
				return "在当前月【" + date_time + "】缺少业务数据,请更换日期进行查询";
			}
			List<String> wdList=Arrays.asList(new String[]{"西海信用社","人民南路分社","人民中路分社","营业部","草海信用社","龙凤分社","鸭子塘信用社","新区信用社"}) ;
			List<String> sqlList=new ArrayList<String>();
			HashMap<String, String> userMap =dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_tyxw_result");
			Set<String> keys = userMap.keySet();
			HashMap<String,String> deptMap = dmo.getHashMapBySQLByDS(null, "SELECT USERNAME,DEPTNAME FROM V_PUB_USER_POST_1");
			DecimalFormat format=new  DecimalFormat("00.00");
			for (String key : keys) {
				insert.putFieldValue("username", key);
				insert.putFieldValue("date_time", getLastMonth(date_time));
				double task=Double.parseDouble(rwMap.get(key)==null|| rwMap.get(key).isEmpty()?"0":rwMap.get(key));
				insert.putFieldValue("task",task);
                //判断客户属于城区还是乡镇
				String wdName=deptMap.get(key);
				String local="";
				if(wdList.indexOf(wdName)!=-1){
					local="城区";
				}else{
					local="乡镇";
				}
				double passed=getTyNum(local, key, date_time)+getXwNum(local, key, date_time);
				System.out.println("key="+key+",小微="+getXwNum(local,key,date_time)+",特约="+getTyNum(local, key, date_time));
				insert.putFieldValue("passed", passed);
				if(passed==0 || task==0){
					insert.putFieldValue("rate", "00.00");
				}else{
					insert.putFieldValue("rate", format.format(passed/task*100));
				}
				sqlList.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, sqlList);
			result = "查询成功";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private int getTyNum(String local, String username, String date_time) {//特约商户
		int result = 0;
		try {
			String monthEnd = getLastMonth(date_time);
			String count = dmo.getStringValueByDS(null, "select count(bb.mcht_id) num from (select a.a a ,b.b b from (select * from excel_tab_13 where b='" + local + "' and c='特约商户' and year||'-'||month='" + date_time + "') a left join (select  * from excel_tab_16 where year||'-'||month='" + date_time
					+ "') b on a.d=b.a)  aa left join  (select a.mcht_id mcht_id,a.num num from (select mcht_id, sum(kbsxj_m+sbsxj_m+zqbsxj_m+zzbsxj_m) num from wnbank.t_dis_ifsp_intgr_busi_dtl_cxb  where mcht_prop='特约商户' and  to_char(to_date(day_id,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + date_time + "-01" + "' and  to_char(to_date(day_id,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + monthEnd
					+ "'  group by mcht_id) a  where a.num>=10) bb on bb.mcht_id=aa.a where bb.mcht_id is  not NULL AND aa.b='" + username + "' group by aa.b");
			result = Integer.parseInt(count == null ? "0" : count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private int getXwNum(String local, String username, String date_time) {//小微商户
		int result = 0;
		try {
			String monthEnd = getLastMonth(date_time);
			String count = (dmo.getStringValueByDS(null, "select count(bb.mcht_id) num from (select a.a a ,b.b b from (select * from excel_tab_13 where b='" + local + "' and c='小微商户' and year||'-'||month='" + date_time + "') a left join (select  * from excel_tab_16 where year||'-'||month='" + date_time
					+ "') b on a.d=b.a)  aa left join  (select a.mcht_id mcht_id,a.num num from (select mcht_id, sum(kbsxj_m+sbsxj_m+zqbsxj_m+zzbsxj_m) num from wnbank.t_dis_ifsp_intgr_busi_dtl_cxb  where mcht_prop='小微商户' and  to_char(to_date(day_id,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + date_time + "-01" + "' and  to_char(to_date(day_id,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + monthEnd
					+ "'  group by mcht_id) a  where a.num>=5) bb on bb.mcht_id=aa.a where bb.mcht_id is  not null and aa.b='" + username + "' group by aa.b  "));
			result = Integer.parseInt(count == null ? "0" : count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getLastMonth(String date) {
		String result = "";
		try {
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM");
			int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(simple.parse(date)));
			int month = Integer.parseInt(new SimpleDateFormat("MM").format(simple.parse(date)));
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
	 * zzl
	 * 贷款余额新增完成比
	 */
	@Override
	public String getDKBalanceXZ(String date) {
		String jl = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_loan_balance");
			List list = new ArrayList<String>();
			//得到客户经理的Map
			HashMap<String, String> userMap = dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			//得到客户经理的任务数
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null, "select A,sum(E) from EXCEL_TAB_53 where year||'-'||month='" + date.substring(0, 7) + "' group by A");
			if (rwMap.size() == 0) {
				return "当前时间【" + date + "】没有上传任务数";
			}
			//得到机构编码的map
			HashMap<String, String> deptCodeMap = dmo.getHashMapBySQLByDS(null, "select sal.name,dept.code from v_sal_personinfo sal left join pub_corp_dept dept on sal.deptid=dept.id");
			//得到机构类型的map
			HashMap<String, String> deptTypeMap = dmo.getHashMapBySQLByDS(null, "select sal.name,dept.corptype from v_sal_personinfo sal left join pub_corp_dept dept on sal.deptid=dept.id");
			//营业部考核月贷款余额-农户Map
			HashMap<String, String> YEKDKNHMap = getKDKNHSalesOffice(date);
			HashMap<String, String> YESDKNHMap = getSDKNHSalesOffice(date);
			HashMap<String, String> YEKDKQTMap = getKDKQTSalesOffice(date);
			HashMap<String, String> YESDKQTMap = getKDKQTSalesOffice(date);
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
	 * zzl
	 * 营业部考核月贷款余额-农户Map
	 * @return
	 */
	public HashMap<String, String> getKDKNHSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + date
					+ "'  and xd_col2 not like '公司' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0  and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 营业部上月贷款余额-农户Map
	 * @return
	 */
	public HashMap<String, String> getSDKNHSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + getSMonthDate(date)
					+ "'  and xd_col2 not like '公司' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0  and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 营业部考核月贷款余额-其他Map
	 * @return
	 */
	public HashMap<String, String> getKDKQTSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + date
					+ "'  and xd_col2 not like '公司' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 营业部上月贷款余额-其他Map
	 * @return
	 */
	public HashMap<String, String> getSDKQTSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + getSMonthDate(date)
					+ "'  and xd_col2 not like '公司' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 营业部对公贷款余额Map
	 * @return
	 */
	public HashMap<String, String> getKDKDGSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select dg.cus_manager cus_manager,sum(LOAN_BALANCE)/10000 tj from (select dg.cus_manager cus_manager,sum(LOAN_AMOUNT) tj,sum(LOAN_BALANCE) LOAN_BALANCE  from wnbank.s_cmis_acc_loan dg where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + date
					+ "' and dg.cla<>'05' and account_status='1' and loan_balance<>'0' group by CUS_ID,dg.cus_manager) dg where dg.tj<10000000 group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 营业部上月对公贷款余额Map
	 * @return
	 */
	public HashMap<String, String> getSDKDGSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select dg.cus_manager cus_manager,sum(LOAN_BALANCE)/10000 tj from (select dg.cus_manager cus_manager,sum(LOAN_AMOUNT) tj,sum(LOAN_BALANCE) LOAN_BALANCE  from wnbank.s_cmis_acc_loan dg where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + getSMonthDate(date)
					+ "' and dg.cla<>'05' and account_status='1' and loan_balance<>'0' group by CUS_ID,dg.cus_manager) dg where dg.tj<10000000 group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 城区网点农户贷款余额新增Map
	 * @return
	 */
	public HashMap<String, String> getKDKCQNHSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + date
					+ "' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%公司%' group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 城区网点上月农户贷款余额新增Map
	 * @return
	 */
	public HashMap<String, String> getSDKCQNHSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + getSMonthDate(date)
					+ "' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%公司%' group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 城区网点其他贷款余额新增Map
	 * @return
	 */
	public HashMap<String, String> getKDKCQQTSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col6) as xd_col6,sum(xd_col7) xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + date
					+ "' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%公司%' group by XD_COL14,xd_col81 ) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 城区网点上月其他贷款余额新增Map
	 * @return
	 */
	public HashMap<String, String> getSDKCQQTSalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col6) as xd_col6,sum(xd_col7) xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + getSMonthDate(date)
					+ "' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%公司%' group by XD_COL14,xd_col81 ) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 对公贷款余额100万以下Map
	 * @return
	 */
	public HashMap<String, String> getKDKDG100SalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select dg.cus_manager cus_manager,sum(LOAN_BALANCE)/10000 tj from (select dg.cus_manager cus_manager,sum(LOAN_AMOUNT) tj,sum(LOAN_BALANCE) LOAN_BALANCE  from wnbank.s_cmis_acc_loan dg where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + date
					+ "' and dg.cla<>'05' and account_status='1' and loan_balance<>'0' group by CUS_ID,dg.cus_manager) dg where dg.tj<1000000 group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 对公贷款余额100万以下Map
	 * @return
	 */
	public HashMap<String, String> getSDKDG100SalesOffice(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select dg.cus_manager cus_manager,sum(LOAN_BALANCE)/10000 tj from (select dg.cus_manager cus_manager,sum(LOAN_AMOUNT) tj,sum(LOAN_BALANCE) LOAN_BALANCE  from wnbank.s_cmis_acc_loan dg where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + getSMonthDate(date)
					+ "' and dg.cla<>'05' and account_status='1' and loan_balance<>'0' group by CUS_ID,dg.cus_manager) dg where dg.tj<1000000 group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 考核月贷款余额-农户Map
	 * @return
	 */
	public HashMap<String, String> getKDKNHSales(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7,sum(xd_col6)/10000 xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + date
					+ "' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%公司%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 上月贷款余额-农户Map
	 * @return
	 */
	public HashMap<String, String> getSDKNHSales(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7,sum(xd_col6)/10000 xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + getSMonthDate(date)
					+ "' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%公司%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 考核月贷款余额-其他Map
	 * @return
	 */
	public HashMap<String, String> getKDKQTSales(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + date
					+ "' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%公司%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 上月贷款余额-其他Map
	 * @return
	 */
	public HashMap<String, String> getSDKQTSales(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + getSMonthDate(date)
					+ "' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%公司%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 贷款户数新增
	 */
	@Override
	public String getDKHouseholdsXZ(String date) {
		String jl = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("WN_LOANS_newly");
			List list = new ArrayList<String>();
			//得到客户经理的Map
			HashMap<String, String> userMap = dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			//得到客户经理的任务数
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null, "select A,sum(F) from EXCEL_TAB_53 where year||'-'||month='" + date.substring(0, 7) + "' group by A");
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
	 * zzl
	 * 考核月贷款户数-农户Map
	 * @return
	 */
	public HashMap<String, String> getKDKNHNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date + "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 上月贷款户数-农户Map
	 * @return
	 */
	public HashMap<String, String> getSDKNHNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getSMonthDate(date) + "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 考核月贷款户数-其他Map
	 * @return
	 */
	public HashMap<String, String> getKDKQTNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date + "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 上月月贷款户数-其他Map
	 * @return
	 */
	public HashMap<String, String> getSDKQTNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getSMonthDate(date) + "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 考核月贷款户数-企业Map
	 * @return
	 */
	public HashMap<String, String> getKDKDGNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select dg.cus_manager cus_manager,count(dg.cus_manager) countxj from (select distinct(xx.cert_code),dg.cus_manager  from wnbank.s_cmis_acc_loan dg left join wnbank.s_cmis_cus_base xx on dg.cus_id=xx.cus_id  where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + date
					+ "'and dg.cla<>'05' and account_status='1' and loan_balance<>'0')dg group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 上月贷款户数-企业Map
	 * @return
	 */
	public HashMap<String, String> getSDKDGNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select dg.cus_manager cus_manager,count(dg.cus_manager) countxj from (select distinct(xx.cert_code),dg.cus_manager  from wnbank.s_cmis_acc_loan dg left join wnbank.s_cmis_cus_base xx on dg.cus_id=xx.cus_id  where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + getSMonthDate(date)
					+ "'and dg.cla<>'05' and account_status='1' and loan_balance<>'0')dg group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 表外不良贷款完成比
	 * @param date
	 * @return
	 */
	@Override
	public String getBadLoans(String date) {
		String jl = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("WN_OFFDK_BAB");
			List list = new ArrayList<String>();
			//得到客户经理的Map
			HashMap<String, String> userMap = dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			//得到客户经理的任务数
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null, "select A,sum(K) from EXCEL_TAB_53 where year||'-'||month='" + date.substring(0, 7) + "' group by A");
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
	 * zzl
	 * 现金收回表外不良贷款本息金额2017Map
	 * @return
	 */
	public HashMap<String, String> get2017DBadLoans(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select yb.xd_col2 xd_col2,sum(bwbl.sumtj)/10000 tj from(select hk.xd_col1 xd_col1,hk.sumtj sumtj,dk.xd_col81 xd_col81 from(select XD_COL1 XD_COL1,sum(XD_COL5+XD_COL11) sumtj from  wnbank.S_LOAN_HK hk where to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')>='" + getMonthC(date)
					+ "' and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')<='" + date + "' and  XD_COL16='05'  and XD_COL20<>'81320101' group by XD_COL1) hk  left join wnbank.s_loan_dk dk on hk.xd_col1=dk.xd_col1  where to_char(to_date(dk.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + date
					+ "' and  XD_COL5<='2017-12-31') bwbl left join wnbank.S_LOAN_RYB yb on bwbl.XD_COL81=yb.xd_col1 group by yb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl
	 * 现金收回表外不良贷款本息金额(2018)Map
	 * @return
	 */
	public HashMap<String, String> get2018DBadLoans(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select yb.xd_col2,sum(bwbl.sumtj)/10000 from(select hk.xd_col1 xd_col1,hk.sumtj sumtj,dk.xd_col81 xd_col81 from(select XD_COL1 XD_COL1,sum(XD_COL5+XD_COL11) sumtj from  wnbank.S_LOAN_HK hk where to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')>='" + getMonthC(date)
					+ "' and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')<='" + date + "' and  XD_COL16='05'  and XD_COL20<>'81320101' group by XD_COL1) hk left join wnbank.s_loan_dk dk on hk.xd_col1=dk.xd_col1  where to_char(to_date(dk.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + date
					+ "' and  XD_COL5<='2018-12-31' and XD_COL5>='2018-01-01') bwbl left join wnbank.S_LOAN_RYB yb on bwbl.XD_COL81=yb.xd_col1 group by yb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl 返回月初的日期
	 * @param date
	 * @return
	 */
	public String getMonthC(String date) {
		date = date.substring(0, 7);
		return date + "-01";
	}

	/**
	 * zzl 返回年初的日期
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
			//得到客户经理的Map
			HashMap<String, String> userMap = dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			//得到客户经理的任务数
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null, "select A,sum(G) from EXCEL_TAB_53 where year||'-'||month='" + date.substring(0, 7) + "' group by A");
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
	 * zzl
	 * 收回存量不良贷款考核
	 * @return
	 */
	public HashMap<String, String> getStockLoans(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null,
					"select yb.xd_col2,sum(zjj.XD_COL5)/10000 from(select zj.XD_COL81 XD_COL81,sum(zj.XD_COL5) XD_COL5 from(select hk.xd_col14,hk.XD_COL5,hk.XD_COL81,dk.xd_col1 from (select xdk.xd_col1,xhk.XD_COL4,xhk.XD_COL5,xdk.XD_COL2,xdk.XD_COL81,xdk.XD_COL14 from(select * from wnbank.S_LOAN_HK where Xd_Col16 in('03','04') and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')<='"
							+ date + "' and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')>='" + getMonthC(date) + "') xhk left join (select * from wnbank.S_LOAN_DK where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + date
							+ "' and xd_col22 in('03','04')) xdk on xdk.xd_col1=xhk.xd_col1) hk left join (select * from wnbank.S_LOAN_DK where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='" + getYearC(date)
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
//<<<<<<< HEAD
//			// 查询所有未结束评分的客户经理
//			HashVO[] vos = dmo.getHashVoArrayByDS(null,
//					"select * from wn_managerdx_table where  state='评分中'");
//			if (vos.length <= 0) {
//				result = "当前考核计划结束成功";
//			} else {// 尚未结束的考核人员,直接将考核人员的分数设置成0分
//				UpdateSQLBuilder update = new UpdateSQLBuilder(
//						"wn_managerdx_table");
//				List<String> _sqllist = new ArrayList<String>();
//				Map<String, Double> map=new HashMap<String, Double>();
//				for (int i = 0; i < vos.length; i++) {
//					
//					String xiangmu = vos[i].getStringValue("xiangmu");
//					// if("总分".equals(xiangmu)){continue;}
//					String fenzhi = vos[i].getStringValue("fenzhi");
//					String koufen = vos[i].getStringValue("koufen");
//					String khsm = vos[i].getStringValue("khsm");
//					String  usercode = vos[i].getStringValue("usercode");
//					if(koufen==null || "".isEmpty()|| Integer.parseInt(koufen)>=Integer.parseInt(fenzhi)){
//						koufen=fenzhi;
//					}
//                    if(!map.containsKey(usercode)){
//                    	map.put(usercode, Double.valueOf(koufen));
//                    }else {
//                    	map.put(usercode, map.get(usercode)+Double.valueOf(koufen));
//                    }
//					update.setWhereCondition("usercode='" + usercode
//							+ "' and khsm='" + khsm + "' and state='评分中'");
//					update.putFieldValue("koufen", koufen);
//					update.putFieldValue("state", "评分结束");
//					_sqllist.add(update.getSQL());
//				}
//				Set<String> usercodeKey = map.keySet();
//				String sql="";
//				for (String usercode : usercodeKey) {
//			         sql="update wn_managerdx_table set fenzhi='"+map.get(usercode)+"' where usercode='"+usercode+"' and state='评分中'";
//				     _sqllist.add(sql);
//				}
//				dmo.executeBatchByDS(null, _sqllist);
//				result = "当前考核计划结束成功";
//=======
			//首先，获取到需要参与考核的委派会计信息
			HashVO[] kjVos = dmo.getHashVoArrayByDS(null, "SELECT * FROM V_PUB_USER_POST_1 WHERE POSTNAME='委派会计'");
			//获取到委派会计的打分项
			HashVO[] pfVos = dmo.getHashVoArrayByDS(null, "   select * from sal_person_check_list where 1=1  and (type='101')  and (1=1)  order by seq");
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			List<String> sqlList = new ArrayList<String>();
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_kjscore_table");
			for (int i = 0; i < kjVos.length; i++) {//为每一个会计生成打分计划
				for (int j = 0; j < pfVos.length; j++) {
					insert.putFieldValue("PLANID", id);
					insert.putFieldValue("USERNAME", kjVos[i].getStringValue("username"));
					insert.putFieldValue("USERCODE", kjVos[i].getStringValue("usercode"));
					insert.putFieldValue("USERDEPT", kjVos[i].getStringValue("deptcode"));
					insert.putFieldValue("xiangmu", pfVos[j].getStringValue("catalog"));
					insert.putFieldValue("khsm", pfVos[j].getStringValue("name"));
					insert.putFieldValue("fenzhi", pfVos[j].getStringValue("weights"));
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
			//首先获取当前尚未结束考核的委派会计
			HashVO[] kjVos = dmo.getHashVoArrayByDS(null, "SELECT * FROM V_PUB_USER_POST_1 WHERE POSTNAME='委派会计'");
			HashVO[] pfVos = dmo.getHashVoArrayByDS(null, "select * from wn_kjscore_table where state='评分中' and planid='" + id + "'");
			double fenzhi = 0.0;
			UpdateSQLBuilder update = new UpdateSQLBuilder("wn_kjscore_table");
			List<String> sqlList = new ArrayList<String>();
			for (int i = 0; i < pfVos.length; i++) {
				fenzhi = Double.parseDouble(pfVos[i].getStringValue("fenzhi"));
				update.setWhereCondition("planid='" + id + "' and usercode='" + pfVos[i].getStringValue("USERCODE") + "'");
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
	 * fj
	 * 农户建档户数完成比
	 * @return
	 */

	@Override
	public String getNhjdHs(String date) {
			String result = null;
			try {
				InsertSQLBuilder insert=new InsertSQLBuilder("WN_NHJD_WCB");
				List list=new ArrayList<String>();
				//得到客户经理的map
				HashMap<String, String> userMap=dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
				//得到客户经理的任务数
				String sql="select A,sum(R) from EXCEL_TAB_53 where year||'-'||month='"+date+"' group by A";
				HashMap<String,String> rwMap=dmo.getHashMapBySQLByDS(null, sql);
				if(rwMap.size()==0){
					return "当前时间【"+date+"】没有上传任务数";
				}
				HashMap<String,String> map = getNhjdMap(date);
				for(String str:userMap.keySet()){
					Double count=0.0;
					if(map.get(str)==null){
						count=0.0;
					}else{
						count=Double.parseDouble(map.get(str));
					}
					insert.putFieldValue("name",str);
					insert.putFieldValue("passed",count);
					insert.putFieldValue("task",rwMap.get(str));
					insert.putFieldValue("date_time",date);
					list.add(insert.getSQL());
				}
				dmo.executeBatchByDS(null,list);
				result = "查询成功！";
			} catch (Exception e) {
				e.printStackTrace();
				result = "查询失败！";
			}
			return result;
	}
	/**
	 * 新建农户建档户数
	 * @param date
	 * @return
	 */

	private HashMap<String, String> getNhjdMap(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select yb.xd_col2 xd_col2,zc.tj tj  from(select xx.xd_col96 xd_col96,count(xx.xd_col96) tj from (select XD_COL1,XD_COL96 from wnbank.s_loan_khxx where to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm')='"+date+"'  and xd_col10 not in('未评级','等外','!$') and XD_COL4='905') xx left join wnbank.S_LOAN_KHXXZCQK zc on xx.xd_col1=zc.xd_col1 where zc.XD_COL6='1' group by xx.xd_col96)zc left join wnbank.s_loan_ryb yb on zc.xd_col96=yb.xd_col1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public String getQnedXstd(String date) {
		String result = null;
		try {
			InsertSQLBuilder insert=new InsertSQLBuilder("WN_QNED_XSTD");
			List list=new ArrayList<String>();
			//得到客户经理的map
			HashMap<String, String> userMap=dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
			//得到客户经理的任务数
			HashMap<String,String> rwMap=dmo.getHashMapBySQLByDS(null, "select A,sum(Q) from EXCEL_TAB_53 where year||'-'||month='"+date.substring(0,7)+"' group by A");
			if(rwMap.size()==0){
				return "当前时间【"+date+"】没有上传任务数";
			}
			HashMap<String,String> khMap=getKhxsCount(date);
			HashMap<String,String> lastMap=getLastxsCount(date);
			for(String str:userMap.keySet()){
				Double count = 0.0;
				Double khcount = 0.0;
				Double lastcount = 0.0;
				if(khMap.get(str)==null){
					khcount=0.0;
				}else{
					khcount=Double.parseDouble(khMap.get(str));
				}
				if(lastMap.get(str)==null){
					lastcount=0.0;
				}else{
					lastcount=Double.parseDouble(lastMap.get(str));
				}
				count=khcount-lastcount;
				insert.putFieldValue("name",str);
				insert.putFieldValue("last_passed",lastcount);
				insert.putFieldValue("passed", khcount);
				insert.putFieldValue("task", rwMap.get(str));
				insert.putFieldValue("time", date);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null,list);
			result="查询成功";
		} catch (Exception e) {
			e.printStackTrace();
			result="查询失败";
		}
		return result;
	}
	/**
	 * 黔农e贷上月线上户数
	 * @param date
	 * @return
	 */
	private HashMap<String, String> getLastxsCount(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select b.xd_col2 name,count(a.xd_col16) num from (select distinct(xd_col16) xd_col16,xd_col81 from wnbank1.s_loan_dk where  xd_col7>0 and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')>='"+getMonthC(getSMonthDate(date))+"' and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')<='"+getSMonthDate(date)+"' and xd_col86='x_wd') a left join wnbank1.s_loan_ryb b on b.xd_col1=a.xd_col81 group by b.xd_col2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 黔农e贷考核月线上户数
	 * @param date
	 * @return
	 */
	private HashMap<String, String> getKhxsCount(String date) {

		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select b.xd_col2 name,count(a.xd_col16) num from (select distinct(xd_col16) xd_col16,xd_col81 from wnbank1.s_loan_dk where  xd_col7>0 and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')>='"+getMonthC(date)+"' and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')<='"+date+"' and xd_col86='x_wd') a left join wnbank1.s_loan_ryb b on b.xd_col1=a.xd_col81 group by b.xd_col2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	
	}
	/**
	 * fj
	 * [单位职工小微企业建档户数完成比]
	 */

	@Override
	public String getDwzgXwqyRatio(String date) {
		String result = null;
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_zgxw_wcb");
		List list = new ArrayList<String>();
		try{
		HashMap<String, String> userMap = dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
		//得到客户经理的任务数
		HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null, "select A,sum(R) from EXCEL_TAB_53 where year||'-'||month='" + date.substring(0, 7) + "' group by A");
		if (rwMap.size() == 0) {
			return "当前时间【" + date + "】没有上传任务数";
		}
		HashMap<String, String> dwzgMap = getDwzgCount(date);
		HashMap<String, String> xwqyMap = getXwqyCount(date);
		for(String str:userMap.keySet()){
			Double dwzgcount=0.0;
			Double xwqycount=0.0;
			Double count = 0.0;
			if(dwzgMap.get(str)==null){
				dwzgcount=0.0;
			}else{
				dwzgcount=Double.parseDouble(dwzgMap.get(str));
			}
			if(xwqyMap.get(str)==null){
				xwqycount=0.0;
			}else{
				xwqycount=Double.parseDouble(xwqyMap.get(str));
			}
			count=dwzgcount+xwqycount;
			insert.putFieldValue("username", str);
			insert.putFieldValue("dwzgcount", dwzgcount);
			insert.putFieldValue("xwqycount", xwqycount);
			insert.putFieldValue("passed", count);
			insert.putFieldValue("task", rwMap.get(str));
			insert.putFieldValue("date_time", date);
			list.add(insert.getSQL());
		}
		dmo.executeBatchByDS(null,list);
		result="查询成功";
		}catch (Exception e) {
			e.printStackTrace();
			result="查询失败";
		}
		return result;
	}
	
   /**
    * 客户经理小微企业建档户数
    * @param date
    * @return
    */
	private HashMap<String, String> getXwqyCount(String date) {
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select yb.xd_col2 xd_col2,xx.tj tj from (select XD_COL96 XD_COL96,count(XD_COL96) tj from wnbank.s_loan_khxx where to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')>='"+getMonthC(date)+"' and to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')<='"+date+"' and xd_col10 not in('未评级','等外','!$') and XD_COL4='206' and XD_COL163 not in('05','03','06','01') group by XD_COL96)xx left join wnbank.s_loan_ryb yb on xx.xd_col96=yb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 客户经理单位职工建档户数
	 * @param date
	 * @return
	 */

	private HashMap<String, String> getDwzgCount(String date) {
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null, "select yb.xd_col2 xd_col2,xx.tj tj from (select XD_COL96 XD_COL96,count(XD_COL96) tj from wnbank.s_loan_khxx where to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')>='"+getMonthC(date)+"'and to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')<='"+date+"'and xd_col10 not in('未评级','等外','!$') and XD_COL4='907' group by XD_COL96)xx left join wnbank.s_loan_ryb yb on xx.xd_col96=yb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public String getGyClass(String date) {
		List list = new ArrayList<String>();
		InsertSQLBuilder insert = new InsertSQLBuilder("WN_GYPJ");
		String result = null;
		String year = date.substring(0,4);
		String month = date.substring(5,7);
		String str = getAnnual(year,month);
		if(str==null){
			result = "当前时间不在考核时间范围内！";
		}
		try {
			HashMap<String,String> userMap = dmo.getHashMapBySQLByDS(null,"select B,B from (select B,count(B) as c from excel_tab_39 group by B) where c>=5");
			HashMap<String,String> userDeptMap = dmo.getHashMapBySQLByDS(null,"select username,deptname from v_pub_user_post_1");
			HashMap<String,String> wmgfMap = getWmgf(str,year);
			HashMap<String,String> fwzlMap = getFwzl(str,year);
			HashMap<String,String> gzzlMap = getGzzl(str,year);
			HashMap<String,String> wdpjzbMap = getWdpjzb(str,year);
			HashMap<String,String> qxpjzbMap = getQxpjzb(str,year);
			HashMap<String,String> ckzfMap = getCkzfzb(str,year);
			HashMap<String,String> dztdlMap = getDztdl(str,year);
			HashMap<String,String> wgjfMap = getWgjf(year,month);
			for(String user:userMap.keySet()){
				Double wmgfcount=0.0;
				Double fwzlcount=0.0;
				Double gzzlcount=0.0;
				Double wdpjcount=0.0;
				Double qxpjcount=0.0;
				Double ckzfcount=0.0;
				Double dztdlcount=0.0;
				Double wgjfcount=0.0;
				Double count=0.0;
				if(wmgfMap.get(user)==null){
					wmgfcount=0.0;
				}else{
					wmgfcount=Double.parseDouble(wmgfMap.get(user));
				}if(fwzlMap.get(user)==null){
					fwzlcount=0.0;
				}else{
					fwzlcount=Double.parseDouble(fwzlMap.get(user));
				}if(gzzlMap.get(user)==null){
					gzzlcount=0.0;
				}else{
					gzzlcount=Double.parseDouble(gzzlMap.get(user));
				}if(wdpjzbMap.get(user)==null){
					wdpjcount=0.0;
				}else{
					wdpjcount=Double.parseDouble(wdpjzbMap.get(user));
				}if(qxpjzbMap.get(user)==null){
					qxpjcount=0.0;
				}else{
					qxpjcount=Double.parseDouble(qxpjzbMap.get(user));
				}if(ckzfMap.get(user)==null||Integer.valueOf(ckzfMap.get(user))<=0){
					ckzfcount=0.0;
				}else{
					ckzfcount=Double.parseDouble(ckzfMap.get(user));
				}if(dztdlMap.get(user)==null){
					dztdlcount=0.0;
				}else{
					dztdlcount=Double.parseDouble(dztdlMap.get(user));
				}if(wgjfMap.get(user)==null){
					wgjfcount=0.0;
				}else{
					wgjfcount=Double.parseDouble(wgjfMap.get(user));
				}
				count=wmgfcount+fwzlcount+gzzlcount+wdpjcount+qxpjcount+ckzfcount+dztdlcount+wgjfcount;
				insert.putFieldValue("name", user);
				insert.putFieldValue("wdmc", userDeptMap.get(user));
				insert.putFieldValue("pjtime", str);
				insert.putFieldValue("wmgf", wmgfMap.get(user));
				insert.putFieldValue("fwzl", fwzlMap.get(user));
				insert.putFieldValue("gzzl", gzzlMap.get(user));
				insert.putFieldValue("szwd", wdpjzbMap.get(user));
				insert.putFieldValue("qxgy", qxpjzbMap.get(user));
				insert.putFieldValue("ckzf", ckzfMap.get(user));
				insert.putFieldValue("dztdl", dztdlMap.get(user));
				insert.putFieldValue("wgjf", wgjfMap.get(user));
				insert.putFieldValue("hjdf", count);
			}
			dmo.executeBatchByDS(null,list);
			result="柜员评级成功";
		} catch (Exception e) {
			result = "柜员评级失败，请联系系统管理员！v";
		}
		
		return result;
	}
	
	/**
	 * 柜员累计违规积分
	 * @param year
	 * @param month
	 * @return
	 */
	private HashMap<String, String> getWgjf(String year, String month) {
		HashMap<String,String> map = new HashMap<String, String>();
		try {
			map = dmo.getHashMapBySQLByDS(null,"select A,B from excel_tab_ where year||'-'||month="+year+"-"+month);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 柜员所在网点电子替代率
	 * @param str
	 * @param year
	 * @return
	 */
	private HashMap<String, String> getDztdl(String str, String year) {
		HashMap<String,String> map = new HashMap<String, String>();
		return map;
	}
	
	/**
	 * 所在网点存款半年内增幅
	 * @param str
	 * @param year
	 * @return
	 */
	private HashMap<String, String> getCkzfzb(String str, String year) {
		HashMap<String,String> map = new HashMap<String, String>();
		try {
			if(str.contains("上")){
			map = dmo.getHashMapBySQLByDS(null,"select username,c*20 as c from (select username,case when c>1 then 1 when c<0 then 0 else c end as c from (select a.username,b.rate as c from v_pub_user_post_1 a left join (select A,F/B as rate from excel_tab_9 where year||'-'||month='"+year+"-06') b on a.deptname=substr(b.A,12)))");
			}else if(str.contains("下")){	
				map=dmo.getHashMapBySQLByDS(null,"select username,c*20 as c from (select username,case when c>1 then 1 when c<0 then 0 else c end as c from (select a.username,b.rate as c from v_pub_user_post_1 a left join (select a.A,(a.C-b.C)/b.C as rate from (select A,C from excel_tab_9 where year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-12') a left join (select A,C from excel_tab_9 where year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-06') b on a.A=b.A) b on a.deptname=substr(b.A,12)))");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}
	
	/**
	 * 月平均工资占全辖柜员平均定量考核占比
	 * @param date
	 * @param year
	 * @return
	 */
	private HashMap<String, String> getQxpjzb(String date, String year) {
		HashMap<String,String> map = new HashMap<String, String>();
		return map;
	}
	
	/**
	 * 月平均工资占所在网点柜员平均定量考核工资占比
	 * @param date
	 * @param year
	 * @return
	 */
	private HashMap<String, String> getWdpjzb(String date, String year) {
		HashMap<String,String> map = new HashMap<String, String>();
		return map;
	}

	/**
	 * 柜员工作质量得分平均分
	 * @param str
	 * @param year
	 * @return
	 */
	private HashMap<String, String> getGzzl(String str,String year) {
		HashMap<String,String> map = new HashMap<String, String>();
		try {
			if(str.contains("上")){
			map = dmo.getHashMapBySQLByDS(null,"select a.B,(a.C+b.C)/6/100*10 as c from (select B,sum(E) as c from (select b,e from excel_tab_39 where year||'-'||month='"+year+"-01' or year||'-'||month='"+year+"-02' or year||'-'||month='"+year+"-03' or year||'-'||month='"+year+"-04' or year||'-'||month='"+year+"-05' or year||'-'||month='"+year+"-06') group by B) a,(select username username,sum(kouofen) as c from WN_GYPF_TABLE  where xiangmu ='总分' and pftime>='"+year+"-01-01' and pftime<='"+year+"-06-30' group by username) b where a.B=b.username");
		}else if(str.contains("下")){
			map = dmo.getHashMapBySQLByDS(null,"select a.B,(a.C+b.C)/6/100*10 as c from (select B,sum(E) as c from (select b,e from excel_tab_39 where year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-07' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-08' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-09' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-10' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-11' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-12') group by B) a,(select username username,sum(kouofen) as c from WN_GYPF_TABLE  where xiangmu ='总分' and pftime>='"+String.valueOf((Integer.valueOf(year)-1))+"-07-01' and pftime<='"+String.valueOf((Integer.valueOf(year)-1))+"-12-31' group by username) b where a.B=b.username");
		}
		} catch (Exception e) {
			
		}
		return map;
	}
	/**
	 * 柜员服务质量得分平均分
	 * @param str
	 * @param year
	 * @return
	 */

	private HashMap<String, String> getFwzl(String str,String year) {
		HashMap<String,String> map = new HashMap<String, String>();
		try {
			if(str.contains("上")){
			map = dmo.getHashMapBySQLByDS(null,"select a.B,(a.C+b.C)/24/100*10 as c from (select B,sum(F) as c from (select b,f from excel_tab_39 where year||'-'||month='"+year+"-01' or year||'-'||month='"+year+"-02' or year||'-'||month='"+year+"-03' or year||'-'||month='"+year+"-04' or year||'-'||month='"+year+"-05' or year||'-'||month='"+year+"-06') group by B) a,(select username username,sum(kouofen) as c from WN_GYPF_TABLE  where xiangmu ='总分' and pftime>='"+year+"-01-01' and pftime<='"+year+"-06-30' group by username) b where a.B=b.username");
		}else if(str.contains("下")){
			map = dmo.getHashMapBySQLByDS(null,"select a.B,(a.C+b.C)/24/100*10 as c from (select B,sum(F) as c from (select b,f from excel_tab_39 where year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-07' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-08' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-09' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-10' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-11' or year||'-'||month='"+String.valueOf((Integer.valueOf(year)-1))+"-12') group by B) a,(select username username,sum(kouofen) as c from WN_GYPF_TABLE  where xiangmu ='总分' and pftime>='"+String.valueOf((Integer.valueOf(year)-1))+"-07-01' and pftime<='"+String.valueOf((Integer.valueOf(year)-1))+"-12-31' group by username) b where a.B=b.username");
		}
		} catch (Exception e) {
			
		}
		return map;
	}
	/**
	 * 所在网点文明规范服务管理考核平均分
	 * @param str
	 * @param year
	 * @return
	 */

	private HashMap<String, String> getWmgf(String str,String year) {
		HashMap<String,String> map = new HashMap<String, String>();
		try {
			if(str.contains("上")){
				map = dmo.getHashMapBySQLByDS(null,"select a.username,b.c/100*10 from v_pub_user_post_1 a,(select deptname,sum(koufen)/2 as c from (select * from WN_BMPF_TABLE where (to_char(to_date(pftime,'yyyy-mm-dd'),'yyyy-mm')='"+year+"-03' or to_char(to_date(pftime,'yyyy-mm-dd'),'yyyy-mm')='"+year+"-06') and xiangmu='文明客户服务部' and state='评分结束') group by deptname) b where a.deptname=b.deptname");
			}else if(str.contains("下")){
				map = dmo.getHashMapBySQLByDS(null, "select a.username,b.c/100*10 from v_pub_user_post_1 a,(select deptname,sum(koufen)/2 as c from (select * from WN_BMPF_TABLE where (to_char(to_date(pftime,'yyyy-mm-dd'),'yyyy-mm')='"+String.valueOf((Integer.valueOf(year)-1))+"-09' or to_char(to_date(pftime,'yyyy-mm-dd'),'yyyy-mm')='"+String.valueOf((Integer.valueOf(year)-1))+"-09') and xiangmu='文明客户服务部' and state='评分结束') group by deptname) b where a.deptname=b.deptname ");
			}
		} catch (Exception e) {
			
		}
		return map;
	}

	/**
	 * 获取年度
	 * @param year
	 * @param month
	 * @return
	 */
	private String getAnnual(String year, String month) {
		String result = null;
		Integer years;
		String year1;
		if(month=="7"){
			result = year+"年上半年";
		}else if(month=="1"){
			years = Integer.valueOf(year)-1;
			year1 = String.valueOf(years);
			result = year1+"年下半年";
		}else{
			result = null;
		}
		return result;
	}
	@Override
	public String getWpkjClass(String date) {
		List list = new ArrayList<String>();
		InsertSQLBuilder insert = new InsertSQLBuilder("WN_GYPJ");
		String result = null;
		String year = date.substring(0,4);
		String month = date.substring(5,7);
		String str = getAnnual(year,month);
		if(str==null){
			result = "当前时间不在考核时间范围内！";
		}
		try {
			HashMap<String,String> userMap = dmo.getHashMapBySQLByDS(null, "");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
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
			HashVO[] managerInfos = dmo
					.getHashVoArrayByDS(
							null,
							"SELECT CODE,NAME,STATIONKIND FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			// 获取客户经理每一项考核得分情况
			HashMap<String, Double> dqdkshl = getDqdkshl(dateNum);// 到期贷款收回率考核
			HashMap<String, Double> dnxzbldk = getDnxzbldk(dateNum);// 当年新增不良贷款
			HashMap<String, Double> shcl = getshcl(dateNum);// 收回存量不良贷款
			HashMap<String, Double> dKsjyh = getDKsjyh(dateNum);// 贷款手机银行客户覆盖率
			HashMap<String, Double> znsh = getZnsh(dateNum);// 助农商户维护
			HashMap<String, Double> cKyxhs = getCKyxhs(dateNum);// 存款有效户数提升
			HashMap<String, Double> cKyxhsye = getCKyxhsye(dateNum);// 存款有效户数余额提升
			HashMap<String, Double> dKyexz = getDKyexz(dateNum);// 贷款余额新增
			HashMap<String, Double> dKhsxz = getDKhsxz(dateNum);// 贷款户数新增
			HashMap<String, Double> jdRate = jdRate();// 新建档案
			HashMap<String, Double> qnedRate = qnedRate(dateNum);// 黔农E贷签约
			HashMap<String, Double> qnedtdScore = qnedtdScore(dateNum);// 黔农E贷线上替代
			double sum = 0;
			List<String> _sqlList = new ArrayList<String>();
			for (int i = 0; i < managerInfos.length; i++) {
				String manager_name = managerInfos[i].getStringValue("name");// 获取到客户经理姓名
				sum = dqdkshl.get(manager_name) + dnxzbldk.get(manager_name)
						+ shcl.get(manager_name) + dKsjyh.get(manager_name)
						+ znsh.get(manager_name) + cKyxhs.get(manager_name)
						+ cKyxhsye.get(manager_name) + dKyexz.get(manager_name)
						+ dKhsxz.get(manager_name) + jdRate.get(manager_name)
						+ qnedRate.get(manager_name)
						+ qnedtdScore.get(manager_name);
				insert.putFieldValue("MANAEGER_NAME", manager_name);
				insert.putFieldValue("DQDKSHL", dqdkshl.get(manager_name));// 到期贷款收回率
				insert.putFieldValue("DNXZBLDK", dnxzbldk.get(manager_name));// 当年新增不良贷款
				insert.putFieldValue("SHCLBLDK", shcl.get(manager_name));// 收回存量不良贷款
				insert.putFieldValue("DKKHSJFGL", dKsjyh.get(manager_name));// 贷款手机银行客户覆盖率
				insert.putFieldValue("ZNSHWH", znsh.get(manager_name));// 助农商户维护
				insert.putFieldValue("CKYXHS", cKyxhs.get(manager_name));// 存款有效户数提升
				insert.putFieldValue("CKYXHSTS", cKyxhsye.get(manager_name));
				insert.putFieldValue("DKYEXZ", dKyexz.get(manager_name));// 贷款余额新增
				insert.putFieldValue("DKHSXZ", dKhsxz.get(manager_name));// 贷款户数新增
				insert.putFieldValue("XJDA", jdRate.get(manager_name));// 建档户数新增
				insert.putFieldValue("QNEDQY", qnedRate.get(manager_name));// 黔农E贷签约
				insert.putFieldValue("QNEDXSTD", qnedtdScore.get(manager_name));// 黔农E贷线上替代
				insert.putFieldValue("SCORESUM", sum);// 总分
				insert.putFieldValue("DATE_TIME", new SimpleDateFormat(
						"yyyy-MM-dd").format(new Date()));
				_sqlList.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, _sqlList);
		} catch (Exception e) {
			e.printStackTrace();
			return "客户经理等级评定事失败";
		}
		return "客户经理等级计算成功";
	}

	/**
	 * ZPY 客户经理之黔农E贷线上替代
	 * 
	 * @return
	 */
	public HashMap<String, Double> qnedtdScore(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			String yearStartDate = getYearStartAndEnd(dateNum).get(0);
			String checkDate = getYearStartAndEnd(dateNum).get(0);
			// 获取到期客户经理的考核
			String[] manager_names = dmo
					.getStringArrayFirstColByDS(null,
							"SELECT NAME FROM V_SAL_PERSONINFO WHERE POSITION LIKE '%客户经理%'");
			HashMap<String, String> qnedtd = dmo
					.getHashMapBySQLByDS(
							null,
							"SELECT USERNAME,sum(RATE) FROM WN_QNEDTD_RESULT WHERE DATE_TIME>='2019-01-01' AND DATE_TIME<='2019-06-30' GROUP BY USERNAME");
			for (int i = 0; i < manager_names.length; i++) {
				double rate = Double
						.parseDouble(qnedtd.get(manager_names[i]) == null ? "0.0"
								: qnedtd.get(manager_names[i]));
				if (rate <= 0.0) {
					resultMap.put(manager_names[i], 0.0);
				} else {
					resultMap.put(manager_names[i], rate/100.0 * 2.0 > 2 ? 2.0
							: rate/100.0 * 2.0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 客户经理评级:建档考核 新建档案占4分，计分方式为： 得分=年初至评分时点新建档案数/（年初新建档案任务数*评分时点月数/12）*4分。
	 * 
	 * @return
	 */
	public HashMap<String, Double> jdRate() {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			// 获取到年初时间
			String yearStartDate = getYearStart();
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-mm-dd");
			String currentDate = simple.format(new Date());
			String checkDate = "";
			if (currentDate.compareTo(getYearCenterDate()) >= 0
					&& currentDate.compareTo(getYearEndDate()) < 0) {
				checkDate = getYearCenterDate();
			} else {
				checkDate = getYearEndDate();
			}
			// //获取到建档评级考核数据
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
			HashMap<String, String> gtjmMap = dmo.getHashMapBySQLByDS(null,
					gtjmSQL);
			HashMap<String, String> dwzgMap = dmo.getHashMapBySQLByDS(null,
					dwzgSQL);
			HashMap<String, String> nhMap = dmo
					.getHashMapBySQLByDS(null, nhSQL);
			HashMap<String, String> xwMap = dmo
					.getHashMapBySQLByDS(null, xwSQL);
			// 获取到相应的人员信息
			HashVO[] managerVos = dmo
					.getHashVoArrayByDS(
							null,
							"SELECT CODE,NAME,STATIONKIND FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			for (int i = 0; i < managerVos.length; i++) {
				String manager_name = managerVos[i].getStringValue("name");
				double sum = 0;
				sum = Double
						.parseDouble(gtjmMap.get(manager_name) == null ? "0"
								: gtjmMap.get(manager_name))
						+ Double.parseDouble(dwzgMap.get(manager_name) == null ? "0"
								: dwzgMap.get(manager_name))
						+ Double.parseDouble(nhMap.get(manager_name) == null ? "0"
								: nhMap.get(manager_name))
						+ Double.parseDouble(xwMap.get(manager_name) == null ? "0"
								: xwMap.get(manager_name));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 客户经理评级之黔农E贷签约 WN_QNED_RESULT
	 * 
	 * @return
	 */
	public HashMap<String, Double> qnedRate(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {

			String yearStartDate = getYearStartAndEnd(dateNum).get(0);
			String checkDate = getYearStartAndEnd(dateNum).get(1);
			String[] manager_names = dmo
					.getStringArrayFirstColByDS(null,
							"SELECT NAME FROM V_SAL_PERSONINFO WHERE POSITION  LIKE '%客户经理%'");
			HashMap<String, String> qnedMap = dmo.getHashMapBySQLByDS(null,
					"SELECT username , sum(RATE) FROM WN_QNED_RESULT   WHERE date_time>='"
							+ yearStartDate + "' AND DATE_TIME<='" + checkDate
							+ "' GROUP BY USERNAME");
			for (int i = 0; i < manager_names.length; i++) {
				String rate = qnedMap.get(manager_names[i]) == null ? "0.0"
						: qnedMap.get(manager_names[i]);
				if ("0.0".equals(rate)) {
					resultMap.put(manager_names[i], 0.0);
				} else {
					resultMap.put(manager_names[i],
							Double.parseDouble(rate)/100.0 * 2.0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	// 到期贷款收回率考核

	public HashMap<String, Double> getDqdkshl(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			// 到期贷款收回率考核首先获取到考核时间
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-mm-dd");
			String currentDate = simple.format(new Date());
			String checkDate = "";
			if (currentDate.compareTo(getYearCenterDate()) >= 0
					&& currentDate.compareTo(getYearEndDate()) < 0) {
				checkDate = getYearCenterDate();
			} else {
				checkDate = getYearEndDate();
			}
			// 累计已收回金额
			String addUpsql = "select bb.xd_col2 xd_col2,zj.hksum XD_COL7 from(select ye.xd_col81,sum(ye.hksum) hksum from(select dk.xd_col1 xd_col1,dk.xd_col81 xd_col81,sum(dk.XD_COL6) XD_COL6,sum(hk.hksum) hksum from(select XD_COL1 hkcode,sum(XD_COL5) hksum from wnbank.S_LOAN_hk where to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')>='2019-04-01' and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')<='"
					+ checkDate
					+ "' group by XD_COL1) hk left join ( select XD_COL1,XD_COL81,XD_COL5,XD_COL6 from wnbank.S_LOAN_DK where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
					+ checkDate
					+ "') dk on hk.hkcode=dk.XD_COL1  where dk.XD_COL5>='2019-01-01' and dk.XD_COL5<='"
					+ checkDate
					+ "' group by dk.xd_col1,dk.xd_col81) ye where ye.XD_COL6<=1000000 group by ye.xd_col81) zj left join wnbank.s_loan_ryb bb on zj.xd_col81=bb.xd_col1";
			// 1-3月累计收回
			String month1_3 = "select B,D from excel_tab_44";
			// 未收回金额Map
			String callBackMoney = "select bb.xd_col2 xd_col2,dk.hj hj from (select dkxj.xd_col81 xd_col81,sum(dkxj.xd_col7) hj from(select XD_COL1 XD_COL1,XD_COL81 XD_COL81,sum(XD_COL6) XD_COL6,sum(XD_COL7) XD_COL7 from wnbank.S_LOAN_DK where XD_COL5>='2019-01-01' and XD_COL5<='"
					+ checkDate
					+ "' and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
					+ checkDate
					+ "' group by XD_COL1,XD_COL81) dkxj group by dkxj.XD_COL81 )dk left join wnbank.s_loan_ryb bb on dk.xd_col81=bb.xd_col1";
			// 个人贷款贷款本金100万以下Map
			String loanCapital = "select bb.xd_col2 xd_col2,zj.hksum XD_COL6 from(select ye.xd_col81,sum(ye.hksum) hksum from(select dk.xd_col1 xd_col1,dk.xd_col81 xd_col81,sum(dk.XD_COL6) XD_COL6,sum(hk.hksum) hksum from(select XD_COL1 hkcode,sum(XD_COL5) hksum from wnbank.S_LOAN_hk where to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')>='2019-04-01' and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')<='"
					+ checkDate
					+ "' group by XD_COL1) hk left join (select XD_COL1,XD_COL81,XD_COL5,XD_COL6 from wnbank.S_LOAN_DK where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
					+ checkDate
					+ "') dk on hk.hkcode=dk.XD_COL1  where dk.XD_COL5>='2019-01-01' and dk.XD_COL5<='"
					+ checkDate
					+ "' group by dk.xd_col1,dk.xd_col81) ye where ye.XD_COL6<=1000000 group by ye.xd_col81) zj left join wnbank.s_loan_ryb bb on zj.xd_col81=bb.xd_col1";
			// 1-3月客户经理已收回金额Map
			String month1_3h = "select B,D from excel_tab_44";
			// 对公贷款还款余额100万以下Map
			String publicLoanhk = "select dg.cus_manager cus_manager,sum(hk.AMOUNTREPAID) AMOUNTREPAID from(select cus_manager cus_manager,CUS_ID CUS_ID from wnbank.s_cmis_acc_loan where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
					+ checkDate
					+ "' and cla<>'05' and account_status='1' and loan_balance<>'0' and LOAN_AMOUNT<=1000000 group by cus_manager,CUS_ID) dg left join  wnbank.S_CMIS_ACC_BILL_REPAY hk on hk.CUS_ID=dg.CUS_ID where to_char(to_date(DATEOFREPAYMENT,'yyyy-mm-dd'),'yyyy-mm-dd')>='2019-04-01' and to_char(to_date(DATEOFREPAYMENT,'yyyy-mm-dd'),'yyyy-mm-dd')<='"
					+ checkDate + "' group by dg.cus_manager";
			// 对公贷款贷款本金100万以下Map
			String publicLoan = "select dg.cus_manager cus_manager,sum(dg.LOAN_BALANCE) LOAN_BALANCE from (select cus_manager cus_manager,sum(LOAN_BALANCE) LOAN_BALANCE from wnbank.s_cmis_acc_loan where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
					+ checkDate
					+ "' and cla<>'05' and loan_balance<>'0' and account_status='1' and to_char(to_date(ORIG_EXPI_DATE,'yyyy-mm-dd'),'yyyy-mm-dd')>='2019-04-01' and to_char(to_date(ORIG_EXPI_DATE,'yyyy-mm-dd'),'yyyy-mm-dd')<='"
					+ checkDate
					+ "' group by CUS_ID,cus_manager) dg where dg.LOAN_BALANCE<=1000000 group by dg.cus_manager";
			// 执行对应的sql
			HashMap<String, String> addUpsqlMap = dmo.getHashMapBySQLByDS(null,
					addUpsql);// 累计已收回金额
			HashMap<String, String> month1_3Map = dmo.getHashMapBySQLByDS(null,
					month1_3);// 1-3月累计收回
			HashMap<String, String> callBackMoneyMap = dmo.getHashMapBySQLByDS(
					null, callBackMoney);// //未收回金额Map
			HashMap<String, String> loanCapitalMap = dmo.getHashMapBySQLByDS(
					null, loanCapital); // 个人贷款贷款本金100万以下Map
			HashMap<String, String> month1_3hMap = dmo.getHashMapBySQLByDS(
					null, month1_3h);// 1-3月客户经理已收回金额Map
			HashMap<String, String> publicLoanhkMap = dmo.getHashMapBySQLByDS(
					null, publicLoanhk);// 对公贷款还款余额100万以下Map
			HashMap<String, String> publicLoanMap = dmo.getHashMapBySQLByDS(
					null, publicLoan);// 对公贷款贷款本金100万以下Map

			HashVO[] managerVos = dmo
					.getHashVoArrayByDS(
							null,
							"SELECT CODE,NAME,STATIONKIND FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			for (int i = 0; i < managerVos.length; i++) {
				String manager_name = managerVos[i].getStringValue("name");// 客户经理
				/**
				 * ([累计已收回金额]+[1-3月客户经理已收回金额])-
				 * ([未收回金额]+[个人贷款贷款本金100万以下]+[1-3月客户经理已收回金额])*0.97 +
				 * ([对公贷款还款余额100万以下]-[对公贷款贷款本金100万以下]*0.97)
				 */
				double addUpsqlMoney = Double.parseDouble(addUpsqlMap
						.get(manager_name) == null ? "0" : addUpsqlMap
						.get(manager_name));// 累计已收回金额
				double month1_3Money = Double.parseDouble(month1_3Map
						.get(manager_name) == null ? "0" : month1_3Map
						.get(manager_name));// 1-3月累计收回
				double callBackMoneyMoney = Double.parseDouble(callBackMoneyMap
						.get(manager_name) == null ? "0" : callBackMoneyMap
						.get(manager_name));// 未收回金额Map
				double loanCapitalMoney = Double.parseDouble(loanCapitalMap
						.get(manager_name) == null ? "0" : loanCapitalMap
						.get(manager_name));// 个人贷款贷款本金100万以下Map
				double month1_3hMoney = Double.parseDouble(month1_3hMap
						.get(manager_name) == null ? "0" : month1_3hMap
						.get(manager_name));
				double publicLoanhkMoney = Double.parseDouble(publicLoanhkMap
						.get(manager_name) == null ? "0" : publicLoanhkMap
						.get(manager_name));
				double publicLoanMoney = Double.parseDouble(publicLoanMap
						.get(manager_name) == null ? "0" : publicLoanMap
						.get(manager_name));
				// 计算累计到期贷款本金
				double publicLoanM = publicLoanMoney + callBackMoneyMoney
						+ loanCapitalMoney + month1_3hMoney;
				// 计算到期贷款收回本金
				double publicLoanSHMoney = addUpsqlMoney + month1_3Money
						+ publicLoanhkMoney;

				if (publicLoanM == 0.0) {// 累计收回本金为0
					resultMap.put(manager_name, 0.0);
					continue;
				}
				double rate = publicLoanSHMoney / publicLoanM;
				if (rate < 0.97) {
					resultMap.put(manager_name, 0.0);

				} else {
					double score = 2.0 + (rate - 0.97) / 0.001;
					resultMap.put(manager_name, score > 5 ? 5 : score);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	// 当年新增不良贷款
	public HashMap<String, Double> getDnxzbldk(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			// 获取到年初和考核日期
			String yearStartDate = getYearStartAndEnd(dateNum).get(0);
			String checkDate = getYearStartAndEnd(dateNum).get(0);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	// 收回存量不良贷款
	public HashMap<String, Double> getshcl(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
           String yearStartDate=getYearStartAndEnd(dateNum).get(0);
           String check_date=getYearStartAndEnd(dateNum).get(1);
           String[] manager_names = dmo.getStringArrayFirstColByDS(null, "SELECT name FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
		   HashMap<String,String> blMap = dmo.getHashMapBySQLByDS(null, "SELECT name,sum(BFB) FROM V_WN_STOCK_LOAN WHERE date_time>='"+yearStartDate+"' AND date_time<='"+check_date+"' GROUP BY name");
		   for (int i = 0; i < manager_names.length; i++) {
			double rate=Double.parseDouble(blMap.get(manager_names[i])==null?"0.0":blMap.get(manager_names[i]));
			if(rate<=0){
				resultMap.put(manager_names[i], 0.0);
			}else if(rate>=1){
				resultMap.put(manager_names[i], 5.0);
			}else {
				resultMap.put(manager_names[i], 5.0*rate);
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 客户经理等级评定之贷款客户手机银行开户覆盖率
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
					+ checkDate.substring(checkDate.lastIndexOf("-"))
					+ "') a  left join (select * from excel_tab_49  where  year||'-'||month='[指标考核时间]') b on a.a=b.b where b.a is not null order by a.a) aa) aaa  where (to_char(sysdate,'yyyy')-substr(aaa.card_no,7,4))<=45) aaaaa left join (select xd_col7,xd_col96 from wnbank.s_loan_khxx where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='[考核的月末日期]')  bbbbb on bbbbb.xd_col7=aaaaa.card_no  group by bbbbb.xd_col96) a left join wnbank.s_loan_ryb b on b.xd_col1=a.xd_col96";

			HashMap<String, String> gtjmMap = dmo.getHashMapBySQLByDS(null,
					gtjmSQL);
			HashMap<String, String> dwzgMap = dmo.getHashMapBySQLByDS(null,
					dwzgSQL);
			HashMap<String, String> nhMap = dmo
					.getHashMapBySQLByDS(null, nhSQL);
			HashMap<String, String> xwMap = dmo
					.getHashMapBySQLByDS(null, xwSQL);
			HashMap<String, String> shyhMap = dmo.getHashMapBySQLByDS(null,
					sjyh);
			HashVO[] managerVos = dmo
					.getHashVoArrayByDS(
							null,
							"SELECT CODE,NAME,STATIONKIND FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
			// 获取到手机银行开户数据
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
				double route = Double.parseDouble(shyhMap.get(manager_name))
						/ jdSum;
				if (route == 0.2) {
					resultMap.put(manager_name, 2.0);
				} else if (route > 0.2) {
					resultMap.put(manager_name,
							2 + (route - 0.20) / 0.001 * 0.1 > 3 ? 3.0
									: 0.20 + (route - 0.20) / 0.001 * 0.1);
				} else {
					resultMap.put(manager_name,
							2 - (0.20 - route) / 0.001 * 0.2 < 0 ? 0.00
									: 2 - (0.20 - route) / 0.001 * 0.2);
				}
				resultMap.put(manager_name, route);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	
	/**
	 * WN_ZNSH_RESULT 客户经理等级评定之助农商户维护考核 
	 * @param dateNum
	 * @return
	 */
	public HashMap<String, Double> getZnsh(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			String[] managerNames = dmo
					.getStringArrayFirstColByDS(null,
							"SELECT NAME FROM V_SAL_PERSONINFO WHERE STATIONKIND IN ('乡镇客户经理','副主任兼职客户经理')");
			String yearStartDate = getYearStartAndEnd(dateNum).get(0);
			String date_time = getYearStartAndEnd(dateNum).get(1);// 考核月日期
			HashMap<String, String> managerRate = dmo.getHashMapBySQLByDS(null,
					"SELECT USERNAME,sum(RATE) FROM WN_ZNSH_RESULT WHERE DATE_TIME >='"
							+ yearStartDate + "' AND DATE_TIME<='" + date_time
							+ "' GROUP BY USERNAME");
			for (int i = 0; i < managerNames.length; i++) {
				double rate = Double.parseDouble(managerRate
						.get(managerNames[i]) == null ? "0.00" : managerRate
						.get(managerNames[i]));
				if (rate >= 100.0) {
					resultMap.put(managerNames[i], 3.0);
				} else {
					resultMap.put(managerNames[i], rate/100.0 * 3.0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	// 存款有效户数提升
	public HashMap<String, Double> getCKyxhs(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			// 获取到当前日期
			String yearStart = getYearStartAndEnd(dateNum).get(0);// 获取到年初时间
			String check_date = getYearStartAndEnd(dateNum).get(1);// 获取到考核时间
			Map<String, String> ckhsMap = dmo.getHashMapBySQLByDS(null,
					"SELECT name,sum(BFB) FROM V_WN_DEPOSIT_NUMBER  WHERE date_time >='"
							+ yearStart + "' AND date_time<='" + check_date
							+ "' GROUP BY name");
			HashMap<String, String> manager_names = dmo
					.getHashMapBySQLByDS(
							null,
							"SELECT name,STATIONKIND FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%';");
			Set<String> manager_sets = manager_names.keySet();
			for (String manager_name : manager_sets) {
				String manager_type=manager_names.get(manager_name);
                double score=0;
                if(manager_type.equals("城区客户经理")){
                	score=4.0;
                }else{
                	score=3.0;
                }
                double rate=Double.parseDouble(ckhsMap.get(manager_name)==null?"0.0":ckhsMap.get(manager_name));
                if(rate>=1){
                	resultMap.put(manager_name, score);
                }else if(rate<=0) {
                	resultMap.put(manager_name, 0.0);
                }else {
                	resultMap.put(manager_name, score*rate);
                }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	// 存款有效户数余额提升
	/**
	 * 存款有效户数余额提升
	 * V_WN_DEPOSIT_BALANCE
	 * @param dateNum
	 * @return
	 */
	public HashMap<String, Double> getCKyxhsye(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
             String yearStartDate = getYearStartAndEnd(dateNum).get(0);
             String check_date = getYearStartAndEnd(dateNum).get(1);
             HashMap<String, String> manager_names = dmo.getHashMapBySQLByDS(null, "SELECT name,STATIONKIND FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
             HashMap<String,String> ckyeMap = dmo.getHashMapBySQLByDS(null, "SELECT  name,sum(bfb) FROM V_WN_DEPOSIT_BALANCE WHERE date_time>='"+yearStartDate+"' AND date_time<='"+check_date+"' GROUP BY name");
		     Set<String> managerSet = manager_names.keySet();
		     for (String manager_name : managerSet) {
				String manager_type=manager_names.get(manager_name);
				double score=0.0;
				if(manager_type.equals("城区客户经理")){
					score=4.0;
				}else{
					score=3.0;
				}
				double rate=Double.parseDouble(ckyeMap.get(manager_name)==null?"0.0":ckyeMap.get(manager_name));
				if(rate==0){
					resultMap.put(manager_name, 0.0);
				}else if(rate>=1){
					resultMap.put(manager_name, score);
				}else{
					resultMap.put(manager_name, score*rate);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 *  贷款余额新增
	 *  [V_WN_LOAN_BALANCE]
	 * @return
	 */
	public HashMap<String, Double> getDKyexz(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
			String yearStartDate=getYearStartAndEnd(dateNum).get(0);
			String check_date=getYearStartAndEnd(dateNum).get(1);
			String[] managers = dmo.getStringArrayFirstColByDS(null, "SELECT name FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
		    HashMap<String,String> ckyeMap = dmo.getHashMapBySQLByDS(null, "SELECT  name,sum(bfb) FROM V_WN_LOAN_BALANCE WHERE date_time>='"+yearStartDate+"' AND date_time<='"+check_date+"' GROUP BY name");
		    for (int i = 0; i < managers.length; i++) {
				double rate=Double.parseDouble(ckyeMap.get(managers[i])==null?"0.0":ckyeMap.get(managers[i]));
				if(rate<=0){
					resultMap.put(managers[i],0.0 );
				}else if(rate>=1){
					resultMap.put(managers[i], 3.0);
				}else{
					resultMap.put(managers[i], 3.0*rate);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 贷款户数新增
	 * [V_WN_DEPOSIT_NUMBER]
	 * @return
	 */
	public HashMap<String, Double> getDKhsxz(int dateNum) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		try {
              String yearStartDate=getYearStartAndEnd(dateNum).get(0);
              String check_date=getYearStartAndEnd(dateNum).get(1);
              HashMap<String,String> manager_names = dmo.getHashMapBySQLByDS(null, "SELECT name,STATIONKIND FROM V_SAL_PERSONINFO WHERE STATIONKIND LIKE '%客户经理%'");
		      HashMap<String,String> dkhsMap = dmo.getHashMapBySQLByDS(null,"SELECT name,sum(BFB) FROM V_WN_DEPOSIT_NUMBER WHERE DATE_TIME>='"+yearStartDate+"' AND DATE_TIME<='"+check_date+"' GROUP BY NAME" );
		      Set<String> managerSet = manager_names.keySet();
		      for (String manager_name : managerSet) {
				String manager_type=manager_names.get(manager_name);
				double score=0.0;
				if("城区客户经理".equals(manager_type)){
					score=5.0;
				}else{
					score=4.0;
				}
				double rate=Double.parseDouble(dkhsMap.get(manager_name)==null?"0.0":dkhsMap.get(manager_name));
				if(rate<=0){
					resultMap.put(manager_name, 0.0);
				}else if(rate>=1){
					resultMap.put(manager_name, score);
				}else{
					resultMap.put(manager_name, score*rate);
				}
			};
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

	/**
	 * 
	 * @return
	 */
	public List<String> getYearStartAndEnd(int dateNum) {
		List<String> list = new ArrayList<String>();
		String[] currentDate = getCurrentDate();
		String year = "";
		String month = "";
		String day = "";
		if (dateNum != 0) {// 上半年
			year = currentDate[0];
			month = "06";
			day = "30";
		} else {
			year = String.valueOf(Integer.parseInt(year) - 1);
			month = "12";
			day = "31";
		}
		String yearStartDate = year + "-" + "01-01";
		String checkDate = year + "-" + month + "-" + day;
		list.add(yearStartDate);
		list.add(checkDate);
		return list;
	}
}
