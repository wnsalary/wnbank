package cn.com.pushworld.wn.bs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.com.infostrategy.bs.common.WLTJobIFC;

/**
 * 
 * @author zzl
 *
 * 2019-6-20-����03:37:26
 * ���Ķ�ʱ���������������󣬱ȽϺ�ʱ��
 * [�����³�����]+";"+[���˵���ĩ����]+";"+[�������]+";"+[����ָ�꿼��ʱ��]+";"+[��������str]+";"+[����³�����]
 */
public class DepositTimingJob implements WLTJobIFC{

	@Override
	public String run() throws Exception {
		String inputParam=getKHYCMonth()+";"+getKHYMMonth()+";"+getKHNCYear()+";"+getSYYMMonth()+";"+getMonthCount()+";"+getKHNCMYear();
		ManAndWifeHouseholdsCount mc=new ManAndWifeHouseholdsCount();
		mc.getComputeMap(inputParam);
		AverageDailyManAnd am=new AverageDailyManAnd();
		am.getComputeMap(inputParam);
//		System.out.println(">>>>>>>>>>>>>"+inputParam);
		return "����ɹ�";
	}
	/**
	 * �����³�����
	 * zzl
	 * @return
	 */
	public String getKHYCMonth(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DATE));
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(otherDate);
	}
	/**
	 * ���˵���ĩ����
	 * zzl
	 * @return
	 */
	public String getKHYMMonth(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(otherDate);
	}
	/**
	 * �����ĩ����
	 * zzl
	 * @return
	 */
	public String getKHNCYear(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, -1);
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		return dateFormat.format(otherDate)+"-12-31";
	}
	/**
	 * ����ָ�꿼��ʱ��
	 * zzl
	 * @return
	 */
	public String getSYYMMonth(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -2);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(otherDate);
	}
	/**
	 * ��������str
	 * zzl
	 * @return
	 */
	public String getMonthCount(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		return String.valueOf(Integer.parseInt(dateFormat.format(otherDate)));
	}
	/**
	 * ����³�����
	 * zzl
	 * @return
	 */
	public String getKHNCMYear(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, -1);
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		return dateFormat.format(otherDate)+"-12-01";
	}
	
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		System.out.println(">>>>>>>>>>>>>"+dateFormat.format(otherDate));
	}

}
