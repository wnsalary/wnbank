package cn.com.pushworld.wn.ui;

import cn.com.infostrategy.ui.common.WLTRemoteCallServiceIfc;

public interface WnSalaryServiceIfc extends WLTRemoteCallServiceIfc{
	/**
	 * zzl[柜员服务质量评价]
	 * @return
	 */
	public String getSqlInsert(String time);
	/**
	 * zpy[部门指标打分]
	 */
	public String getBMsql(String planid);
	public String gradeBMScoreEnd();
	/**
	 * zpy[导入全量数据]
	 * @return
	 */
	public String ImportAll();
	/**
	 * zpy[导入一天的数据]
	 * @param date:日期，具体格式为:[20190301]
	 * @return
	 */
	public String ImportDay(String date);
	/**
	 * zpy[导入某张表某天的数据]
	 * @param filePath
	 * @return
	 */
	public String ImportOne(String filePath);
	/**
	 * zzl[贷款客户经理信息更新]
	 * @return
	 */
	public String getChange();
	/**
	 * zzl[存款客户经理信息更新]
	 * @return
	 */
	public String getCKChange();

	/**
	 * zpy[2019-05-22]
	 * 为每个柜员生成定性考核计划
	 * @return:返回最后执行结果
	 */
	public String gradeDXscore(String id);
	/**
	 * zpy[2019-05-22]
	 * 结束当前考核计划
	 * @param planid:当前计划id
	 * @return
	 */
	public String gradeDXEnd(String planid);
	/**
	 * ZPY[2019-05-23]
	 * 客户经理定性打分生成
	 * @param id
	 * @return
	 */
	public String gradeManagerDXscore(String id);
	/**
	 * ZPY[2019-05-23]
	 * 结束所有客户经理定性考核
	 * @param id:计划ID
	 * @return
	 */
	public String endManagerDXscore(String id);

	/**
	 * zzl[贷款户数完成比]
	 */
	public String getDKFinishB(String date);
	/**
	 * zpy[黔农E贷的完成比计算]
	 * @param date_time:查询日期
	 * @return
	 */
	public String getQnedRate(String date_time);
	/**
	 * zpy[黔农E贷线上替代完成比计算]
	 * @param date_time:查询日期
	 * @param username:客户经理名
	 * @return
	 */
	public String getQnedtdRate(String date_time);
	/**
	 * zpy[手机银行完成比计算]
	 * @param date_time:查询日期
	 * @return
	 */
	public String getsjRate(String date_time);
	/**
	 * zpy[助农商户维护完成比计算]
	 * @param date_time:查询日期
	 * @param username:客户经理
	 * @return
	 */
	public String getZNRate(String date_time);
	/**
	 * zpy[特约小微商户完成比计算]
	 * @param date_time:日期
	 * @return
	 */
	public String getTyxwRate(String date_time);
	/**
	 * zzl[贷款余额新增完成比]
	 */
	public String getDKBalanceXZ(String date);
	/**
	 * zzl[贷款户数新增完成比]
	 */
	public String getDKHouseholdsXZ(String date);
	/**
	 * zzl [收回表外不良贷款完成比]
	 */
	public String getBadLoans(String date);
	/**
	 * zzl[收回存量不良贷款完成比&不良贷款压降]
	 */
	public String getTheStockOfLoan(String date);
	/**
	 * 为委派会计生成打分计划
	 * @param id
	 * @return
	 */
	public String getKJDXScore(String id);
	/**
	 * 结束当前委派会计打分
	 * @param id
	 * @return
	 */
	public String getKJDXEnd(String id);

	/**
	 * fj[农户建档指标完成比]
	 */
	public String getNhjdHs(String date);
	
	/**
	 * fj[黔农e贷线上替代完成率]
	 */
	public String getQnedXstd(String data);
	
	/**
	 * fj[单位职工，小微企业建档完成比]
	 * @param data
	 * @return
	 */
	public String getDwzgXwqyRatio(String data);
	/**
	 * 对客户经理进行绩效考核
	 * @return
	 */
	public String managerLevelCompute();
}
