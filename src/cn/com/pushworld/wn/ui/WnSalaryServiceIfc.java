package cn.com.pushworld.wn.ui;

import cn.com.infostrategy.ui.common.WLTRemoteCallServiceIfc;

public interface WnSalaryServiceIfc extends WLTRemoteCallServiceIfc{
	/**
	 * zzl[��Ա������������]
	 * @return
	 */
	public String getSqlInsert(String time);
	/**
	 * zpy[����ָ����]
	 */
	public String getBMsql(String planid);
	public String gradeBMScoreEnd();
	/**
	 * zpy[����ȫ������]
	 * @return
	 */
	public String ImportAll();
	/**
	 * zpy[����һ�������]
	 * @param date:���ڣ������ʽΪ:[20190301]
	 * @return
	 */
	public String ImportDay(String date);
	/**
	 * zpy[����ĳ�ű�ĳ�������]
	 * @param filePath
	 * @return
	 */
	public String ImportOne(String filePath);
	/**
	 * zzl[����ͻ�������Ϣ����]
	 * @return
	 */
	public String getChange();
	/**
	 * zzl[���ͻ�������Ϣ����]
	 * @return
	 */
	public String getCKChange();

	/**
	 * zpy[2019-05-22]
	 * Ϊÿ����Ա���ɶ��Կ��˼ƻ�
	 * @return:�������ִ�н��
	 */
	public String gradeDXscore(String id);
	/**
	 * zpy[2019-05-22]
	 * ������ǰ���˼ƻ�
	 * @param planid:��ǰ�ƻ�id
	 * @return
	 */
	public String gradeDXEnd(String planid);
	/**
	 * ZPY[2019-05-23]
	 * �ͻ������Դ������
	 * @param id
	 * @return
	 */
	public String gradeManagerDXscore(String id);
	/**
	 * ZPY[2019-05-23]
	 * �������пͻ������Կ���
	 * @param id:�ƻ�ID
	 * @return
	 */
	public String endManagerDXscore(String id);

	/**
	 * zzl[�������ɱ�]
	 */
	public String getDKFinishB(String date);
	/**
	 * zpy[ǭũE������ɱȼ���]
	 * @param date_time:��ѯ����
	 * @return
	 */
	public String getQnedRate(String date_time);
	/**
	 * zpy[ǭũE�����������ɱȼ���]
	 * @param date_time:��ѯ����
	 * @param username:�ͻ�������
	 * @return
	 */
	public String getQnedtdRate(String date_time);
	/**
	 * zpy[�ֻ�������ɱȼ���]
	 * @param date_time:��ѯ����
	 * @return
	 */
	public String getsjRate(String date_time);
	/**
	 * zpy[��ũ�̻�ά����ɱȼ���]
	 * @param date_time:��ѯ����
	 * @param username:�ͻ�����
	 * @return
	 */
	public String getZNRate(String date_time);
	/**
	 * zpy[��ԼС΢�̻���ɱȼ���]
	 * @param date_time:����
	 * @return
	 */
	public String getTyxwRate(String date_time);
	/**
	 * zzl[�������������ɱ�]
	 */
	public String getDKBalanceXZ(String date);
	/**
	 * zzl[�����������ɱ�]
	 */
	public String getDKHouseholdsXZ(String date);
	/**
	 * zzl [�ջر��ⲻ��������ɱ�]
	 */
	public String getBadLoans(String date);
	/**
	 * zzl[�ջش�������������ɱ�&��������ѹ��]
	 */
	public String getTheStockOfLoan(String date);
	/**
	 * Ϊί�ɻ�����ɴ�ּƻ�
	 * @param id
	 * @return
	 */
	public String getKJDXScore(String id);
	/**
	 * ������ǰί�ɻ�ƴ��
	 * @param id
	 * @return
	 */
	public String getKJDXEnd(String id);

	/**
	 * fj[ũ������ָ����ɱ�]
	 */
	public String getNhjdHs(String date);
	
	/**
	 * fj[ǭũe��������������]
	 */
	public String getQnedXstd(String data);
	
	/**
	 * fj[��λְ����С΢��ҵ������ɱ�]
	 * @param data
	 * @return
	 */
	public String getDwzgXwqyRatio(String data);
	/**
	 * �Կͻ�������м�Ч����
	 * @return
	 */
	public String managerLevelCompute();
}
