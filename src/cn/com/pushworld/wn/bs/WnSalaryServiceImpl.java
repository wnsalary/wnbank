package cn.com.pushworld.wn.bs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import cn.com.pushworld.wn.ui.WnSalaryServiceIfc;

public class WnSalaryServiceImpl implements WnSalaryServiceIfc {
	private CommDMO dmo = new CommDMO();
	private ImportDataDMO importDmo = new ImportDataDMO();

	/**
	 * zzl[2019-3-28] �ݲ�ʹ�� ��Ա����������������
	 */
	public String getSqlInsert(String time, int num) {
		System.out.println(time + "��ǰʱ��");
		String str = null;
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_gypf_table");
		List list = new ArrayList<String>();
		String[][] date = getTowWeiDate();
		try {
			HashVO[] vos = dmo
					.getHashVoArrayByDS(null,
							"select * from V_PUB_USER_POST_1 where POSTNAME like '%��Ա%'");
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
					insert.putFieldValue("username", vos[i]
							.getStringValue("USERNAME"));
					insert.putFieldValue("usercode", vos[i]
							.getStringValue("usercode"));
					insert.putFieldValue("userdept", vos[i]
							.getStringValue("deptid"));
					insert.putFieldValue("xiangmu", date[j][0]);
					insert.putFieldValue("zhibiao", date[j][1]);
					insert.putFieldValue("fenzhi", date[j][2]);
					insert.putFieldValue("khsm", date[j][4]);
					insert.putFieldValue("pftime", time);
					insert.putFieldValue("state", "������");
					insert.putFieldValue("seq", j + 1);
					insert.putFieldValue("timezone", timezone);
					list.add(insert.getSQL());
				}
			}
			dmo.executeBatchByDS(null, list);
			str = "��Ա���������������ֿ�ʼ�ɹ�";
		} catch (Exception e) {
			str = "��Ա���������������ֿ�ʼʧ��";
			e.printStackTrace();
		}
		return str;
	}

	// zpy
	@Override
	public String getSqlInsert(String time) {
		System.out.println(time + "��ǰʱ��");
		String str = null;
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_gypf_table");
		List list = new ArrayList<String>();
		String[][] date = getTowWeiDate();
		try {
			HashVO[] vos = dmo
					.getHashVoArrayByDS(null,
							"select * from V_PUB_USER_POST_1 where POSTNAME like '%��Ա%'");
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
					insert.putFieldValue("username", vos[i]
							.getStringValue("USERNAME"));
					insert.putFieldValue("usercode", vos[i]
							.getStringValue("usercode"));
					insert.putFieldValue("userdept", vos[i]
							.getStringValue("deptid"));
					insert.putFieldValue("xiangmu", date[j][0]);
					insert.putFieldValue("zhibiao", date[j][1]);
					insert.putFieldValue("fenzhi", date[j][2]);
					insert.putFieldValue("khsm", date[j][4]);
					insert.putFieldValue("pftime", time);
					insert.putFieldValue("state", "������");
					insert.putFieldValue("seq", j + 1);
					insert.putFieldValue("timezone", timezone);
					if ("�ܷ�".equals(date[j][0])) {
						insert.putFieldValue("KOUOFEN", "100.0");
					} else {
						insert.putFieldValue("KOUOFEN", "0.0");
					}

					list.add(insert.getSQL());
				}
			}
			dmo.executeBatchByDS(null, list);
			str = "��Ա���������������ֿ�ʼ�ɹ�";
		} catch (Exception e) {
			str = "��Ա���������������ֿ�ʼʧ��";
			e.printStackTrace();
		}
		return str;
	}

	public String[][] getTowWeiDate() {
		String[][] date = new String[][] {
				{ "ְҵ����", "��װ", "5.0", "", "��Ҫ����װ������ͳһ���淶������" },
				{ "ְҵ����", "����", "5.0", "", "վ�ˡ����ˡ����˶�ׯ��" },
				{ "Ӫҵ׼��", "��ʱ", "3.0", "", "׼������Ҫ��Ӫҵǰ���" },
				{
						"����ڷŹ�λ����",
						"����",
						"3.0",
						"",
						"�����ֻ����⣬��Ʒ�ڷű��������淶�����򣬲���������ã�����ƾ֤��ӡ�¡�ӡ�ࡢӡ���ᡢ��������ˮ���������Ŵ��������ֽ�β�䡢�ǼǱ���˽����Ʒ�Ȳ��÷����������ϣ�����ҵ����Ҫʹ�õģ�ʹ����Ϻ�������ڹ����»�����У�δ��λ�ڷš����ҵȲ��÷�" },
				{
						"����ڷŹ�λ����",
						"����",
						"3.0",
						"",
						"�����ֻ����⣬��Ʒ�ڷű��������淶�����򣬲���������ã�����ƾ֤��ӡ�¡�ӡ�ࡢӡ���ᡢ��������ˮ���������Ŵ��������ֽ�β�䡢�ǼǱ���˽����Ʒ�Ȳ��÷����������ϣ�����ҵ����Ҫʹ�õģ�ʹ����Ϻ�������ڹ����»�����У�δ��λ�ڷš����ҵȲ��÷�" },
				{
						"����ڷŹ�λ����",
						"Ǯ��",
						"3.0",
						"",
						"�����ֻ����⣬��Ʒ�ڷű��������淶�����򣬲���������ã�����ƾ֤��ӡ�¡�ӡ�ࡢӡ���ᡢ��������ˮ���������Ŵ��������ֽ�β�䡢�ǼǱ���˽����Ʒ�Ȳ��÷����������ϣ�����ҵ����Ҫʹ�õģ�ʹ����Ϻ�������ڹ����»�����У�δ��λ�ڷš����ҵȲ��÷�" },
				{
						"����ڷŹ�λ����",
						"����",
						"3.0",
						"",
						"�����ֻ����⣬��Ʒ�ڷű��������淶�����򣬲���������ã�����ƾ֤��ӡ�¡�ӡ�ࡢӡ���ᡢ��������ˮ���������Ŵ��������ֽ�β�䡢�ǼǱ���˽����Ʒ�Ȳ��÷����������ϣ�����ҵ����Ҫʹ�õģ�ʹ����Ϻ�������ڹ����»�����У�δ��λ�ڷš����ҵȲ��÷�" },
				{ "��������", "�㳮��", "3.0", "", "�л��ҡ����ա�ֽм�Ȳ��÷�" },
				{ "��������", "�����", "3.0", "", "�л��ҡ����ա�ֽм�Ȳ��÷�" },
				{ "��������", "����", "3.0", "", "�л��ҡ����ա�ֽм�Ȳ��÷�" },
				{ "��������", "����", "3.0", "", "�л��ҡ����ա�ֽм�Ȳ��÷�" },
				{ "��������", "����", "3.0", "", "�л��ҡ����ա�ֽм�Ȳ��÷�" },
				{ "����", "����", "2.0", "", "����" },
				{ "����", "����", "2.0", "", "��������" },
				{ "����", "����", "2.0", "", "����������" },
				{ "����", "΢Ц", "2.0", "", "��ʱ΢Ц" },
				{ "�ʺ�ӭ��", "����", "3.0", "", "����ʾ��" },
				{ "�ʺ�ӭ��", "�ʺ�", "3.0", "", "���ʡ����á�/�ճ���ν���������" },
				{ "�Ӵ�����", "����", "5.0", "",
						"����������ڣ�����ҵ��ִ��һ���Իظ����Է�������Χ��ҵ������������֪�������䵽��ش��ڰ�����" },
				{ "�Ӵ�����", "����", "10.0", "",
						"�������ġ����ġ����ģ��Ծ�ʹ�á��롢���á�лл���Բ����ټ�����������Ҫ����ʲôҵ�����Եȡ������鿴�������������˶Ժ�������ǩ�֡�����������" },
				{ "�Ӵ�����", "����", "5.0", "", "˫�ֵݽӡ����ĳ���" },
				{ "�Ӵ�����", "����", "5.0", "",
						"��ʱ������ͻ������������۲�Ʒ���ݣ���������ҵ�񣬴������ۻ��ᣬ����ͻ�Э����ɷ�����������ۡ�" },
				{ "�ͱ����", "����", "3.0", "", "����������Ҫ��������ҵ����/�����ߣ��ټ�" },
				{ "�������", "1.��ҵ���ڼ䲻������̸", "1.0", "", "��Щ����ڷ���ʱ���÷�" },
				{ "�������", "2.ָ���������Ƴ���", "1.0", "", "��Щ����ڷ���ʱ���÷�" },
				{ "�������", "3.���ҵ��ʱδ��˿�����ʾ���Ǹ��", "1.0", "", "��Щ����ڷ���ʱ���÷�" },
				{ "�������", "4.�ֻ�δ����", "1.0", "", "��Щ����ڷ���ʱ���÷�" },
				{ "�������", "5.�����λʱ����δ��λ", "1.0", "", "��Щ����ڷ���ʱ���÷�" },
				{ "�������", "6.����Ҫ��֫�嶯������", "1.0", "", "��Щ����ڷ���ʱ���÷�" },
				{ "�������", "7.��ͣҵ��ʱδ����ʾ����", "1.0", "", "��Щ����ڷ���ʱ���÷�" },
				{ "�������", "8.��ʱ��ҵ��δ���������ͻ�����", "1.0", "", "��Щ����ڷ���ʱ���÷�" },
				{ "�������", "9.��������Ͷ�����", "5.0", "", "��Щ����ڷ���ʱ���÷�" },
				{ "Ӫҵ����", "��������", "5.0", "", "��Щ����ڷ���ʱ���÷�" },
				{ "�ܷ�", "", "", "", "" } };
		return date;
	}

	/**
	 * ���ſ��˼ƻ����� planid:�ƻ�id
	 */
	@Override
	public String getBMsql(String planid) {
		String str = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_BMPF_table");
			HashVO[] vos = dmo.getHashVoArrayByDS(null,
					"select * from sal_target_list where type='���Ŷ���ָ��'");
			HashMap deptMap = getdeptName();
			InsertSQLBuilder insertSQLBuilder = new InsertSQLBuilder(
					"wn_BMPF_table");
			List<String> list = new ArrayList<String>();
			List<String> wmsumList = new ArrayList<String>();
			List<String> djsumList = new ArrayList<String>();
			List<String> nksumList = new ArrayList<String>();
			List<String> aqsumList = new ArrayList<String>();
			// ������ʱ������޸�
			String[] date = dmo.getStringArrayFirstColByDS(null,
					"select PFTIME from WN_BMPF_TABLE where state='���ֽ���'");
			String pftime = "";
			if (date == null || date.length == 0) {
				int month = Integer.parseInt(new SimpleDateFormat("MM")
						.format(new Date()));
				int currentQuarter = getQuarter2(month);// ���ݵ�ǰʱ���ȡ����ǰ����
				pftime = new SimpleDateFormat("yyyy").format(new Date()) + "-"
						+ getQuarterEnd(currentQuarter);// ��ȡ����ǰ�������һ��
			} else {
				String maxTime = dmo
						.getStringValueByDS(null,
								"SELECT max(PFTIME) PFTINE FROM WN_BMPF_TABLE WHERE STATE='���ֽ���'");
				int year = Integer.parseInt(new SimpleDateFormat("yyyy")
						.format(new Date()));// ��ȡ����ǰ��
				int nextQuarter = getQuarter(maxTime) + 1;
				if (nextQuarter >= 5) {
					nextQuarter = 1;
					year = year + 1;
				}
				pftime = String.valueOf(year) + "-"
						+ getQuarterEnd(nextQuarter);
			}
			for (int i = 0; i < vos.length; i++) {// ÿһ��ָ��
				String deptid = vos[i].getStringValue("checkeddept");// ��ȡ�������˲��ŵĻ�����
				deptid = deptid.replaceAll(";", ",").substring(1,
						deptid.length() - 1);
				String[] deptcodes = deptid.split(",");
				String xiangmu = vos[i].getStringValue("name");// ��Ŀ����
				String evalstandard = vos[i].getStringValue("evalstandard");// ��Ŀ�۷�����
				String weights = vos[i].getStringValue("weights");// ��ĿȨ��
				String koufen = "0.0";// �۷����(Ĭ����0.0)
				String state = "������";// ��ǰ�������״̬:������
				// String pftime = new SimpleDateFormat("yyyy-MM-dd").format(new
				// Date());//��������
				// Ϊÿ����������ÿһ���
				for (int j = 0; j < deptcodes.length; j++) {
					if ("964".equals(deptcodes[j])
							|| "965".equals(deptcodes[j])) {
						continue;
					}
					// ÿһ���ָ�궼��Ϊÿһ�����˲�����һ�����˼ƻ�
					String deptName = deptMap.get(deptcodes[j]).toString();// ��ȡ����������
					insert.putFieldValue("PLANID", planid);
					insert.putFieldValue("deptcode", deptcodes[j]);
					insert.putFieldValue("deptname", deptName);
					insert.putFieldValue("xiangmu", xiangmu);
					insert.putFieldValue("evalstandard", evalstandard);
					insert.putFieldValue("fenzhi", weights);
					insert.putFieldValue("koufen", koufen);
					insert.putFieldValue("state", state);
					insert.putFieldValue("pftime", pftime);
					insert.putFieldValue("id", dmo.getSequenceNextValByDS(null,
							"S_WN_BMPF_TABLE"));
					list.add(insert.getSQL());
					// Ϊÿһ�����ŵ�ÿһ�����������ܷ�
					String name = xiangmu.substring(0, xiangmu.indexOf("-"));
					if ("�����ͻ�����".equals(name)) {
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
					} else if ("��������".equals(name)) {
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
					} else if ("��ȫ����".equals(name)) {
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
					} else if ("�ڿغϹ�".equals(name)) {
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
			str = "���ſ��˼ƻ����ɳɹ�";
		} catch (Exception e) {
			e.printStackTrace();
			str = "���ſ��˼ƻ����ɳɹ�";
		}
		return str;
	}

	// ��ȡ�����ű��
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
	public String gradeBMScoreEnd() {// ������ּƻ� ���ܷ� ��״̬
		try {
			String[] csName = dmo
					.getStringArrayFirstColByDS(null,
							"SELECT DISTINCT(xiangmu) FROM WN_BMPF_TABLE WHERE fenzhi IS NULL");
			HashMap codeVos = dmo
					.getHashMapBySQLByDS(
							null,
							"SELECT DISTINCT(SUBSTR(name,0,INSTR(NAME,'-')-1)) name,CHECKEDDEPT FROM sal_target_list  WHERE TYPE ='���Ŷ���ָ��'");
			UpdateSQLBuilder update = new UpdateSQLBuilder("WN_BMPF_TABLE");
			UpdateSQLBuilder update2 = new UpdateSQLBuilder("WN_BMPF_TABLE");
			List<String> list = new ArrayList<String>();

			for (int i = 0; i < csName.length; i++) {// ����ÿһ�����ִ������ ���� ���� ��ȫ��
				String deptcodes = codeVos.get(csName[i]).toString();
				deptcodes = deptcodes.substring(1, deptcodes.lastIndexOf(";"));
				String[] codes = deptcodes.split(";");// ������
				for (int j = 0; j < codes.length; j++) {// ÿ������ÿ������ÿ��С��
														// �޸�״̬�������ܷ�
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
								+ "%' and state='������'");
						update.putFieldValue("state", "���ֽ���");
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
			update3.setWhereCondition("1=1 and state='������'");
			update3.putFieldValue("state", "���ֽ���");
			dmo.executeUpdateByDS(null, update3.getSQL());
			return "��ǰ�ƻ������ɹ�";
		} catch (Exception e) {
			e.printStackTrace();
			return "��ǰ�ƻ�����ʧ��";
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

	// date��ʽ:2019-01-01
	public int getQuarter(String date) {// ����date��ȡ����ǰ����
		date = date.substring(5);
		if ("03-31".equals(date)) {
			return 1;
		} else if ("06-30".equals(date)) {
			return 2;
		} else if ("09-30".equals(date)) {
			return 3;
		} else if ("12-31".equals(date)) {
			return 4;
		} else {// ����һ�����벻���������
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
	 * ȫ����������
	 */
	@Override
	public String ImportAll() {// ȫ�����ݵ���
		return importDmo.ImportAll();
	}

	/**
	 * ����һ�������
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
	 * ÿ���µĿͻ�������Ӧ�Ĵ�����Ҫ����������Ҫ�޸����µĻ�����
	 */
	public String getChange() {
		String strjv = null;
		Date date = new Date();
		Calendar scal = Calendar.getInstance();// ʹ��Ĭ��ʱ�������Ի������һ��������
		scal.setTime(date);
		scal.add(Calendar.MONTH, -2);
		scal.set(Calendar.DAY_OF_MONTH, scal.getActualMaximum(Calendar.DATE));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String smonth = df.format(scal.getTime());// ���µ�����
		Calendar cal = Calendar.getInstance();// ʹ��Ĭ��ʱ�������Ի������һ��������
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		String kmonth = df.format(cal.getTime());// �����µ�����
		StringBuffer sb = new StringBuffer();
		StringBuffer sqlsb = new StringBuffer();
		try {
			UpdateSQLBuilder update = new UpdateSQLBuilder("wnbank.s_loan_dk");
			List list = new ArrayList<String>();
			// �ͻ���������Ϣ��map
			HashMap<String, String> map = dmo.getHashMapBySQLByDS(null,
					"select xd_col1,xd_col2 from wnbank.s_loan_ryb");
			// �����µĿͻ�֤���Ϳͻ�������map
			HashMap<String, String> kmap = dmo
					.getHashMapBySQLByDS(
							null,
							"select distinct(dk.xd_col1),dk.XD_COL81 from wnbank.s_loan_dk dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ kmonth + "' and XD_COL7<>0");
			// ���µĿͻ�֤���Ϳͻ�������map
			HashMap<String, String> smap = dmo
					.getHashMapBySQLByDS(
							null,
							"select distinct(dk.xd_col1),dk.XD_COL81 from wnbank.s_loan_dk dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ smonth + "' and XD_COL7<>0");
			for (String str : kmap.keySet()) {
				if (kmap.get(str).equals(smap.get(str))) {
					continue;
				} else {
					update
							.setWhereCondition("to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ smonth + "' and xd_col1='" + str + "'");
					update.putFieldValue("XD_COL81", kmap.get(str));
					list.add(update.getSQL());
					sb.append(smonth + "�����˺�Ϊ[" + str + "]�ͻ�����Ϊ["
							+ map.get(smap.get(str))
							+ "]�뿼���µĿͻ�������Ϣ���������޸Ŀͻ�����Ϊ["
							+  map.get(kmap.get(str)) + "] "
							+ System.getProperty("line.separator"));
				}
				if (list.size() > 5000) {// zzl 1000 һ�ύ
					dmo.executeBatchByDS(null, list);
					for (int i = 0; i < list.size(); i++) {
						sqlsb.append(list.get(i).toString() + " "
								+ System.getProperty("line.separator"));
					}
					contentToSQL("DK", sqlsb.toString());
					list.clear();
				}
			}
			if (list.size() > 0) {
				dmo.executeBatchByDS(null, list);
				sqlsb.delete(0, sqlsb.length());
				for (int i = 0; i < list.size(); i++) {
					sqlsb.append(list.get(i).toString() + " "
							+ System.getProperty("line.separator"));
				}
				contentToSQL("DK", sqlsb.toString());
			}
			contentToTxt("DK", sb.toString());
		} catch (Exception e) {
			strjv = "�ͻ�������Ϣ���ʧ��";
			e.printStackTrace();
		}
		return "�ͻ�������Ϣ����ɹ�";
	}

	public static void main(String[] args) {
		Date date = new Date();
		Calendar scal = Calendar.getInstance();// ʹ��Ĭ��ʱ�������Ի������һ��������
		scal.setTime(date);
		scal.add(Calendar.MONTH, -2);
		scal.set(Calendar.DAY_OF_MONTH, scal.getActualMaximum(Calendar.DATE));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String smonth = df.format(scal.getTime());// ���µ�����
		Calendar cal = Calendar.getInstance();// ʹ��Ĭ��ʱ�������Ի������һ��������
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		String kmonth = df.format(cal.getTime());// �����µ�����
		System.out.println(">>>>smonth>>>>>>>" + smonth);
		System.out.println(">>>>kmonth>>>>>>>" + kmonth);
	}

	/**
	 * zzl
	 * 
	 * @param date
	 * @return ��������ʱ��
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
	 * zzl[���ͻ�������Ϣ���]
	 * 
	 */
	@Override
	public String getCKChange() {
		String strjv = null;
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();// ʹ��Ĭ��ʱ�������Ի������һ��������
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		// cal.add(Calendar.MONTH, -1);//ȡ��ǰ���ڵĺ�һ��.
		String kmonth = df.format(cal.getTime());// �����µ�����
		StringBuffer sb = new StringBuffer();
		StringBuffer sqlsb = new StringBuffer();
		try {
			UpdateSQLBuilder update = new UpdateSQLBuilder("wnbank.s_loan_KHXX");
			List list = new ArrayList<String>();
			// �ͻ���������Ϣ��map
			HashMap<String, String> map = dmo.getHashMapBySQLByDS(null,
					"select xd_col1,xd_col2 from wnbank.s_loan_ryb");
			// �����µĿͻ�֤���Ϳͻ�������map
			HashMap<String, String> kmap = dmo
					.getHashMapBySQLByDS(
							null,
							"select XD_COL1,XD_COL96 from wnbank.S_LOAN_KHXX where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ kmonth
									+ "' and XD_COL1 is not null and XD_COL96 is not null");
			// ����ͻ�֤���Ϳͻ�������map
			String year = String.valueOf(Integer.parseInt(kmonth
					.substring(0, 4)) - 1);
			HashMap<String, String> smap = dmo
					.getHashMapBySQLByDS(
							null,
							"select XD_COL1,XD_COL96 from wnbank.S_LOAN_KHXX where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ year
									+ "-12-31' and XD_COL1 is not null and XD_COL96 is not null");
			for (String str : kmap.keySet()) {
				if (kmap.get(str).equals(smap.get(str))) {
					continue;
				} else {
					update
							.setWhereCondition("to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ year
									+ "-12-31' and XD_COL1='"
									+ str
									+ "'");
					update.putFieldValue("XD_COL96", kmap.get(str));
					list.add(update.getSQL());
					sb.append("2018-12-31�ͻ���Ϊ[" + str + "]�ͻ�����Ϊ["
							+ map.get(smap.get(str))
							+ "]�뿼���µĿͻ�������Ϣ���������޸Ŀͻ�����Ϊ["
							+ map.get(kmap.get(str)) + "] "
							+ System.getProperty("line.separator"));
				}
				if (list.size() > 5000) {// zzl 1000 һ�ύ
					dmo.executeBatchByDS(null, list);
					for (int i = 0; i < list.size(); i++) {
						sqlsb.append(list.get(i).toString() + " "
								+ System.getProperty("line.separator"));
					}
					contentToSQL("CK", sqlsb.toString());
					list.clear();
				}
			}
			if (list.size() > 0) {
				dmo.executeBatchByDS(null, list);
				sqlsb.delete(0, sqlsb.length());
				for (int i = 0; i < list.size(); i++) {
					sqlsb.append(list.get(i).toString() + " \n");
				}
				contentToSQL("CK", sqlsb.toString());
			}
			contentToTxt("CK", sb.toString());
		} catch (Exception e) {
			strjv = "�ͻ�������Ϣ���ʧ��";
			e.printStackTrace();
		}
		return "�ͻ�������Ϣ����ɹ�";
	}

	/**
	 * zzl ����ܻ�����
	 */
	@Override
	public String getDKFinishB(String date) {
		String jv = null;
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_loans_wcb");
		List list = new ArrayList<String>();
		try {
			// �õ��ͻ�������Map
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
			// �õ��ͻ�������������
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(D) from EXCEL_TAB_53 where year||'-'||month='"
							+ date.substring(0, 7) + "' group by A");
			if (rwMap.size() == 0) {
				return "��ǰʱ�䡾" + date + "��û���ϴ�������";
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
			jv = "��ѯ���";
		} catch (Exception e) {
			jv = "��ѯʧ��";
			e.printStackTrace();
		}

		return jv;
	}

	/**
	 * zzl ����ܻ���-ũ��Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getDKNH(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl ����ܻ���-�ڲ�ְ��Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getDKNBZG(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 in('26','19') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl ����ܻ���-���ҿͻ�Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getDKAJ(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 in('20','29','28','37','h01','h02') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl ����ܻ���-һ����Ȼ��Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getDKYBZRR(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 not in ('20','29','28','37','h01','h02','26','19','16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl ����ܻ���-��ҵMap
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
	 * zzl �������������ɱ�
	 */
	@Override
	public String getDKBalanceXZ(String date) {
		String jl = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_loan_balance");
			List list = new ArrayList<String>();
			// �õ��ͻ�������Map
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
			// �õ��ͻ�������������
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(E) from EXCEL_TAB_53 where year||'-'||month='"
							+ date.substring(0, 7) + "' group by A");
			if (rwMap.size() == 0) {
				return "��ǰʱ�䡾" + date + "��û���ϴ�������";
			}
			// �õ����������map
			HashMap<String, String> deptCodeMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select sal.name,dept.code from v_sal_personinfo sal left join pub_corp_dept dept on sal.deptid=dept.id");
			// �õ��������͵�map
			HashMap<String, String> deptTypeMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select sal.name,dept.corptype from v_sal_personinfo sal left join pub_corp_dept dept on sal.deptid=dept.id");
			// Ӫҵ�������´������-ũ��Map
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
				} else if (deptTypeMap.get(str).equals("��������")) {
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
			jl = "��ѯ�ɹ�";
		} catch (Exception e) {
			jl = "��ѯʧ��";
			e.printStackTrace();
		}
		return jl;
	}

	/**
	 * zzl Ӫҵ�������´������-ũ��Map
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
									+ "'  and xd_col2 not like '��˾' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0  and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl Ӫҵ�����´������-ũ��Map
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
									+ getSMonthDate(date)
									+ "'  and xd_col2 not like '��˾' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0  and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl Ӫҵ�������´������-����Map
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
									+ "'  and xd_col2 not like '��˾' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl Ӫҵ�����´������-����Map
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
									+ getSMonthDate(date)
									+ "'  and xd_col2 not like '��˾' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl Ӫҵ���Թ��������Map
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
	 * zzl Ӫҵ�����¶Թ��������Map
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
									+ getSMonthDate(date)
									+ "' and dg.cla<>'05' and account_status='1' and loan_balance<>'0' group by CUS_ID,dg.cus_manager) dg where dg.tj<10000000 group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl ��������ũ�������������Map
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
									+ "' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%��˾%' group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl ������������ũ�������������Map
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
									+ getSMonthDate(date)
									+ "' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%��˾%' group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl �����������������������Map
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
									+ "' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%��˾%' group by XD_COL14,xd_col81 ) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl ���������������������������Map
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
									+ getSMonthDate(date)
									+ "' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%��˾%' group by XD_COL14,xd_col81 ) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl �Թ��������100������Map
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
	 * zzl �Թ��������100������Map
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
									+ getSMonthDate(date)
									+ "' and dg.cla<>'05' and account_status='1' and loan_balance<>'0' group by CUS_ID,dg.cus_manager) dg where dg.tj<1000000 group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl �����´������-ũ��Map
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
									+ "' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%��˾%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl ���´������-ũ��Map
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
									+ getSMonthDate(date)
									+ "' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%��˾%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl �����´������-����Map
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
									+ "' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%��˾%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl ���´������-����Map
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
									+ getSMonthDate(date)
									+ "' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%��˾%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl ���������
	 */
	@Override
	public String getDKHouseholdsXZ(String date) {
		String jl = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("WN_LOANS_newly");
			List list = new ArrayList<String>();
			// �õ��ͻ�������Map
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
			// �õ��ͻ�������������
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(F) from EXCEL_TAB_53 where year||'-'||month='"
							+ date.substring(0, 7) + "' group by A");
			if (rwMap.size() == 0) {
				return "��ǰʱ�䡾" + date + "��û���ϴ�������";
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
			jl = "��ѯ�ɹ�";
		} catch (Exception e) {
			jl = "��ѯʧ��";
			e.printStackTrace();
		}
		return jl;
	}

	/**
	 * zzl �����´����-ũ��Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getKDKNHNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl ���´����-ũ��Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getSDKNHNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getSMonthDate(date)
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl �����´����-����Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getKDKQTNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ date
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl �����´����-����Map
	 * 
	 * @return
	 */
	public HashMap<String, String> getSDKQTNewly(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
									+ getSMonthDate(date)
									+ "') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl �����´����-��ҵMap
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
	 * zzl ���´����-��ҵMap
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
									+ getSMonthDate(date)
									+ "'and dg.cla<>'05' and account_status='1' and loan_balance<>'0')dg group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * zzl ���ⲻ��������ɱ�
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
			// �õ��ͻ�������Map
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
			// �õ��ͻ�������������
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(K) from EXCEL_TAB_53 where year||'-'||month='"
							+ date.substring(0, 7) + "' group by A");
			if (rwMap.size() == 0) {
				return "��ǰʱ�䡾" + date + "��û���ϴ�������";
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
			jl = "��ѯ�ɹ�";
		} catch (Exception e) {
			jl = "��ѯʧ��";
			e.printStackTrace();
		}
		return jl;
	}

	/**
	 * zzl �ֽ��ջر��ⲻ�����Ϣ���2017Map
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
	 * zzl �ֽ��ջر��ⲻ�����Ϣ���(2018)Map
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
	 * zzl �����³�������
	 * 
	 * @param date
	 * @return
	 */
	public String getMonthC(String date) {
		date = date.substring(0, 7);
		return date + "-01";
	}

	/**
	 * zzl �������������
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
	 * zzl[�ջش�������������ɱ�&��������ѹ��]
	 */
	@Override
	public String getTheStockOfLoan(String date) {
		String jl = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("WN_Stock_Loan");
			List list = new ArrayList<String>();
			// �õ��ͻ�������Map
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
			// �õ��ͻ�������������
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(G) from EXCEL_TAB_53 where year||'-'||month='"
							+ date.substring(0, 7) + "' group by A");
			if (rwMap.size() == 0) {
				return "��ǰʱ�䡾" + date + "��û���ϴ�������";
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
			jl = "��ѯ�ɹ�";
		} catch (Exception e) {
			jl = "��ѯʧ��";
			e.printStackTrace();
		}

		return jl;
	}

	/**
	 * zzl �ջش������������
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

	// public static void main(String[] args) {
	// //
	// contentToTxt("C:\\Users\\longlonggo521\\Desktop\\niubi","niubi.txt","ţ��+ɵ��");
	// // StringBuffer sb=new StringBuffer();
	// // sb.append("������");
	// // sb.append("ţ��");
	// // sb.append("����");
	// // System.out.println(">>>>>>>>>>>>");
	// }
	/**
	 * ��¼�ͻ������������־ zzl [2019-6-12]
	 * 
	 * @param filename
	 * @param content
	 */
	public static void contentToTxt(String filename, String content) {
		String str_path = ServerEnvironment.getProperty("WLTUPLOADFILEDIR");
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		str_path = str_path + "\\ManagerLog\\TXT_" + sf.format(date).toString();
		FileWriter fw = null;
		try {
			// ����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
			File f = new File(str_path);
			if (!f.exists()) {
				f.mkdirs();
			}
			filename = filename + sf.format(date).toString() + ".txt";
			File f2 = new File(str_path + "\\" + filename);
			fw = new FileWriter(f2, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(content);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��¼�ͻ��������SQL����־ zzl [2019-6-12]
	 * 
	 * @param filename
	 * @param content
	 */
	public static void contentToSQL(String filename, String content) {
		String str_path = ServerEnvironment.getProperty("WLTUPLOADFILEDIR");
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		str_path = str_path + "\\ManagerLog\\SQL_" + sf.format(date).toString();
		FileWriter fw = null;
		try {
			// ����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
			File f = new File(str_path);
			if (!f.exists()) {
				f.mkdirs();
			}
			filename = filename + sf.format(date).toString() + ".sql";
			File f2 = new File(str_path + "\\" + filename);
			fw = new FileWriter(f2, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(content);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ͻ������������˼ƻ�
	 */
	@Override
	public String endManagerDXscore(String id) {
		String result = "";
		try {
			// ��ѯ����δ�������ֵĿͻ�����
			HashVO[] vos = dmo.getHashVoArrayByDS(null,
					"select * from wn_managerdx_table where  state='������'");
			if (vos.length <= 0) {
				result = "��ǰ���˼ƻ������ɹ�";
			} else {// ��δ�����Ŀ�����Ա,ֱ�ӽ�������Ա�ķ������ó�0��
				UpdateSQLBuilder update = new UpdateSQLBuilder(
						"wn_managerdx_table");
				List<String> _sqllist = new ArrayList<String>();
				Map<String, Double> map=new HashMap<String, Double>();
				for (int i = 0; i < vos.length; i++) {
					
					String xiangmu = vos[i].getStringValue("xiangmu");
					// if("�ܷ�".equals(xiangmu)){continue;}
					String fenzhi = vos[i].getStringValue("fenzhi");
					String koufen = vos[i].getStringValue("koufen");
					String khsm = vos[i].getStringValue("khsm");
					String  usercode = vos[i].getStringValue("usercode");
					if(koufen==null || "".isEmpty()|| Integer.parseInt(koufen)>=Integer.parseInt(fenzhi)){
						koufen=fenzhi;
					}
                    if(!map.containsKey(usercode)){
                    	map.put(usercode, Double.valueOf(koufen));
                    }else {
                    	map.put(usercode, map.get(usercode)+Double.valueOf(koufen));
                    }
					update.setWhereCondition("usercode='" + usercode
							+ "' and khsm='" + khsm + "' and state='������'");
					update.putFieldValue("koufen", koufen);
					update.putFieldValue("state", "���ֽ���");
					_sqllist.add(update.getSQL());
				}
				Set<String> usercodeKey = map.keySet();
				String sql="";
				for (String usercode : usercodeKey) {
			         sql="update wn_managerdx_table set fenzhi='"+map.get(usercode)+"' where usercode='"+usercode+"' and state='������'";
				     _sqllist.add(sql);
				}
				dmo.executeBatchByDS(null, _sqllist);
				result = "��ǰ���˼ƻ������ɹ�";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
		}

	}

	@Override
	public String getKJDXEnd(String id) {
		String result = "";
		try {
			// ���Ȼ�ȡ��ǰ��δ�������˵�ί�ɻ��
			HashVO[] kjVos = dmo.getHashVoArrayByDS(null,
					"SELECT * FROM V_PUB_USER_POST_1 WHERE POSTNAME='ί�ɻ��'");
			HashVO[] pfVos = dmo.getHashVoArrayByDS(null,
					"select * from wn_kjscore_table where state='������' and planid='"
							+ id + "'");
			double fenzhi = 0.0;
			UpdateSQLBuilder update = new UpdateSQLBuilder("wn_kjscore_table");
			List<String> sqlList = new ArrayList<String>();
			for (int i = 0; i < pfVos.length; i++) {
				fenzhi = Double.parseDouble(pfVos[i].getStringValue("fenzhi"));
				update.setWhereCondition("planid='" + id + "' and usercode='"
						+ pfVos[i].getStringValue("USERCODE") + "'");
				update.putFieldValue("koufen", fenzhi);
				update.putFieldValue("state", "���ֽ���");
				sqlList.add(update.getSQL());
			}
			dmo.executeBatchByDS(null, sqlList);
			result = "�ƻ������ɹ�";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Ϊÿ��ί�ɻ�����ɴ��
	 */
	@Override
	public String getKJDXScore(String id) {
		String result = "";
		try {
			// ���ȣ���ȡ����Ҫ���뿼�˵�ί�ɻ����Ϣ
			HashVO[] kjVos = dmo.getHashVoArrayByDS(null,
					"SELECT * FROM V_PUB_USER_POST_1 WHERE POSTNAME='ί�ɻ��'");
			// ��ȡ��ί�ɻ�ƵĴ����
			HashVO[] pfVos = dmo
					.getHashVoArrayByDS(
							null,
							"   select * from sal_person_check_list where 1=1  and (type='101')  and (1=1)  order by seq");
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date());
			List<String> sqlList = new ArrayList<String>();
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_kjscore_table");
			for (int i = 0; i < kjVos.length; i++) {// Ϊÿһ��������ɴ�ּƻ�
				for (int j = 0; j < pfVos.length; j++) {
					insert.putFieldValue("PLANID", id);
					insert.putFieldValue("USERNAME", kjVos[i]
							.getStringValue("username"));
					insert.putFieldValue("USERCODE", kjVos[i]
							.getStringValue("usercode"));
					insert.putFieldValue("USERDEPT", kjVos[i]
							.getStringValue("deptcode"));
					insert.putFieldValue("xiangmu", pfVos[j]
							.getStringValue("catalog"));
					insert.putFieldValue("khsm", pfVos[j]
							.getStringValue("name"));
					insert.putFieldValue("fenzhi", pfVos[j]
							.getStringValue("weights"));
					insert.putFieldValue("koufen", 0.0);
					insert.putFieldValue("pftime", date);
					insert.putFieldValue("state", "������");
				}
				sqlList.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, sqlList);
			result = "ִ�гɹ�";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * fj ũ������������ɱ�
	 * 
	 * @return
	 */

	@Override
	public String getNhjdHs(String date) {
		String result = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("WN_NHJD_WCB");
			List list = new ArrayList<String>();
			// �õ��ͻ�������map
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
			// �õ��ͻ�������������
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(R) from EXCEL_TAB_53 where year||'-'||month='"
							+ date.substring(0, 7) + "' group by A");
			if (rwMap.size() == 0) {
				return "��ǰʱ�䡾" + date + "��û���ϴ�������";
			}
			HashMap<String, String> map = getNhjdMap(date);
			for (String str : userMap.keySet()) {
				Double count = 0.0;
				if (map.get(str) == null) {
					count = 0.0;
				} else {
					count = Double.parseDouble(map.get(str));
				}
				insert.putFieldValue("name", str);
				insert.putFieldValue("passed", count);
				insert.putFieldValue("task", rwMap.get(str));
				insert.putFieldValue("date_time", date);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			result = "��ѯ�ɹ���";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ѯʧ�ܣ�";
		}
		return result;
	}

	@Override
	public String getQnedRate(String date_time) {
		try {
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(P) from EXCEL_TAB_53 where year||'��'||month||'��'='"
							+ date_time + "' group by A");
			if (rwMap.size() <= 0) {
				return "���¡�" + date_time + "����û������,�������ѯʱ��";
			}
			// ǭũE���������
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_qned_result");
			date_time = date_time.replace("��", "-").replace("��", "");
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
			HashMap<String, String> jlMap = dmo.getHashMapBySQLByDS(null,
					"SELECT E,COUNT(E) FROM V_qnedqy_zpy where date_time='"
							+ date_time + "' GROUP BY E");
			Set<String> keys = userMap.keySet();
			List<String> sqllist = new ArrayList<String>();
			DecimalFormat format = new DecimalFormat("#00.00");
			for (String key : keys) {
				insert.putFieldValue("username", key);
				insert.putFieldValue("date_time", getLastMonth(date_time));
				double task = Double.parseDouble(rwMap.get(key) == null ? "0"
						: rwMap.get(key));
				double passed = Double.parseDouble(jlMap.get(key) == null ? "0"
						: jlMap.get(key));
				insert.putFieldValue("passed", passed);
				insert.putFieldValue("task", task);
				if (task == 0 || passed == 0) {
					insert.putFieldValue("rate", "00.00");
				} else {
					insert.putFieldValue("rate", format.format(passed / task
							* 100));
				}
				sqllist.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, sqllist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "���ִ�гɹ�";
	}

	/**
	 * date_time:2019-01-01
	 */
	@Override
	public String getQnedtdRate(String date_time) {
		String result = "";
		try {
			System.out.println("ǭũE�����������ѯʱ��:" + date_time);
			// �Բ�ѯ���ڽ��д���
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(Q) from EXCEL_TAB_53 where year||'-'||month='"
							+ date_time + "'  group by A");
			if (rwMap.size() <= 0) {
				return "��ǰ�����¡�" + date_time + "��ȱ��ҵ������,��������ڽ��в�ѯ";
			}
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
			Set<String> keys = userMap.keySet();
			String lastMonth = getSMonthDate(date_time);
			HashMap<String, String> currMonthData = dmo
					.getHashMapBySQLByDS(
							null,
							"SELECT a.mname mname, count(a.CARDNO) CARDNO   FROM (SELECT DISTINCT(CARDNO) CARDNO,MNAME FROM wn_qnedtd WHERE  date_time  LIKE '"
									+ date_time + "%') a GROUP BY  a.mname");// ��ǰ�����������ļ�Ч
			HashMap<String, String> lastMonthData = dmo
					.getHashMapBySQLByDS(
							null,
							"SELECT a.mname mname, count(a.CARDNO) CARDNO   FROM (SELECT DISTINCT(CARDNO) CARDNO,MNAME FROM wn_qnedtd WHERE  date_time  LIKE '"
									+ lastMonth + "%') a GROUP BY  a.mname");// ��һ���������ļ�Ч
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_qnedtd_result");
			DecimalFormat format = new DecimalFormat("00.00");
			List<String> sqllist = new ArrayList<String>();
			for (String key : keys) {
				insert.putFieldValue("username", key);
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
				if (task == 0 || (curretData - lastData) == 0) {
					insert.putFieldValue("rate", "00.00");
				} else {
					insert.putFieldValue("rate", format
							.format((curretData - lastData) / task * 100.0));
				}
				insert.putFieldValue("date_time", getLastMonth(date_time));
				sqllist.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, sqllist);
			// InsertSQLBuilder insert = new
			// InsertSQLBuilder("wn_qnedtd_result");
			// insert.putFieldValue("date_time", date_time);

			// String date = date_time.substring(0, 7);//��ȡ����ǰʱ��
			// HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
			// "select A,sum(Q) from EXCEL_TAB_53 where year||'-'||month='" +
			// date + "' and A='" + username + "' group by A");
			// if (rwMap.size() <= 0) {
			// return "��ǰ�ͻ�������" + username + "���ڵ�ǰ�¡�" + date +
			// "��ȱ��ҵ������,��������ڽ��в�ѯ";
			// }
			// String deleteSQL =
			// "delete from wn_qnedtd_result where username='" + username +
			// "' and date_time='" + date + "'";
			// InsertSQLBuilder insert = new
			// InsertSQLBuilder("wn_qnedtd_result");
			// insert.putFieldValue("username", username);
			// insert.putFieldValue("date_time", date);
			// //��ȡ����ǰ�ͻ�����������������
			// String countString = dmo.getStringValueByDS(null,
			// "select count(cardno) num from ( SELECT DISTINCT(cardno) cardno FROM wn_qnedtd WHERE mname='"
			// + username + "' AND date_time='" + date_time + "')");
			// double count = Double.parseDouble(countString == null ? "0" :
			// countString);
			// insert.putFieldValue("passed", count);
			// double task = Double.parseDouble(rwMap.get(username));
			// insert.putFieldValue("task", task);
			// insert.putFieldValue("rate", count / task);
			// List<String> sqlList = new ArrayList<String>();
			// sqlList.add(deleteSQL);
			// sqlList.add(insert.getSQL());
			// dmo.executeBatchByDS(null, sqlList);//
			result = "��ѯ�ɹ�";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getTyxwRate(String date_time) {
		String result = "";
		try {
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(N) from EXCEL_TAB_53 where year||'-'||month='"
							+ date_time + "' group by A");
			if (rwMap.size() <= 0) {
				return "�ڵ�ǰ�¡�" + date_time + "��ȱ��ҵ������,��������ڽ��в�ѯ";
			}
			List<String> wdList = Arrays.asList(new String[] { "����������",
					"������·����", "������·����", "Ӫҵ��", "�ݺ�������", "�������", "Ѽ����������",
					"����������" });
			List<String> sqlList = new ArrayList<String>();
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_tyxw_result");
			Set<String> keys = userMap.keySet();
			HashMap<String, String> deptMap = dmo.getHashMapBySQLByDS(null,
					"SELECT USERNAME,DEPTNAME FROM V_PUB_USER_POST_1");
			DecimalFormat format = new DecimalFormat("00.00");
			for (String key : keys) {
				insert.putFieldValue("username", key);
				insert.putFieldValue("date_time", getLastMonth(date_time));
				double task = Double.parseDouble(rwMap.get(key) == null
						|| rwMap.get(key).isEmpty() ? "0" : rwMap.get(key));
				insert.putFieldValue("task", task);
				// �жϿͻ����ڳ�����������
				String wdName = deptMap.get(key);
				String local = "";
				if (wdList.indexOf(wdName) != -1) {
					local = "����";
				} else {
					local = "����";
				}
				double passed = getTyNum(local, key, date_time)
						+ getXwNum(local, key, date_time);
				System.out.println("key=" + key + ",С΢="
						+ getXwNum(local, key, date_time) + ",��Լ="
						+ getTyNum(local, key, date_time));
				insert.putFieldValue("passed", passed);
				if (passed == 0 || task == 0) {
					insert.putFieldValue("rate", "00.00");
				} else {
					insert.putFieldValue("rate", format.format(passed / task
							* 100));
				}
				sqlList.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, sqlList);
			result = "��ѯ�ɹ�";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ��ũ�̻�ά����ɱ�
	 */
	@Override
	public String getZNRate(String date_time) {
		String result = "";
		try {
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(L) from EXCEL_TAB_53 where year||'-'||month='"
							+ date_time + "' group by A");
			if (rwMap.size() <= 0) {
				return "�ڵ�ǰ�¡�" + date_time + "��ȱ��ҵ������,��������ڽ��в�ѯ";
			}
			HashMap<String, String> jlMap = dmo
					.getHashMapBySQLByDS(
							null,
							"SELECT a.name,a.num num FROM (select bbbb.b name,count(bbbb.b)  num  from (select aaa.c as c,aaa.n as n from (select aa.c,count(aa.c) as n from (select b.b as b ,b.c as c,b.e as e from (select * from excel_tab_10 where year||'-'||month='"
									+ date_time
									+ "' ) a left join (select * from excel_tab_11 where year||'-'||month='"
									+ date_time
									+ "' and d='��' or d is null) b on b.c=a.c  where b.b is not null) aa left join (select a,(b+c) as num  from excel_tab_14 where year||'-'||month='"
									+ date_time
									+ "') bb on aa.b=bb.a where bb.num>=aa.e group by aa.c ) aaa left join (select c,count(c) as n from excel_tab_11 where year||'-'||month='"
									+ date_time
									+ "'  and d='��' or d is null group by c ) bbb on bbb.c=aaa.c where aaa.n>=bbb.n) aaaa left join (select b,c from excel_tab_10 where  year||'-'||month='"
									+ date_time
									+ "')  bbbb on aaaa.c=bbbb.c  WHERE bbbb.b NOT  IN ('�γ�','ʩ�ڻ�','�¾�','�����','��˳��','������','��Ң')   group by bbbb.b) a ");
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_znsh_result");
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
			Set<String> keys = userMap.keySet();
			List<String> sqllist = new ArrayList<String>();
			for (String key : keys) {
				insert.putFieldValue("username", key);
				double task = Double.parseDouble(rwMap.get(key) == null
						|| rwMap.get(key).length() == 0 ? "0" : rwMap.get(key));
				insert.putFieldValue("task", task);
				double passed = Double.parseDouble(jlMap.get(key) == null ? "0"
						: jlMap.get(key));
				insert.putFieldValue("passed", passed);
				DecimalFormat format = new DecimalFormat("00.00");
				if (task == 0 || passed == 0) {
					insert.putFieldValue("rate", "00.00");
				} else {
					insert.putFieldValue("rate", format.format(passed / task
							* 100));
				}
				insert.putFieldValue("date_time", getLastMonth(date_time));
				sqllist.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, sqllist);
			result = "��ѯ�ɹ�";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * �ֻ����п�����ɱȼ���
	 */
	@Override
	public String getsjRate(String date_time) {
		String result = "";
		try {
			// HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
			// "select A,sum(O) from EXCEL_TAB_53 where year||'-'||month='" +
			// date_time + "' and A='" + username + "' group by A");
			// if (rwMap.size() <= 0) {
			// return "��ǰ�ͻ�������" + username + "���ڵ�ǰ�¡�" + date_time +
			// "��ȱ��ҵ������,��������ڽ��в�ѯ";
			// }
			// String deleteSQL = "delete from wn_sjyh_result where username='"
			// + username + "' and date_time='" + date_time + "'";//
			// InsertSQLBuilder insert = new InsertSQLBuilder("wn_sjyh_result");
			// insert.putFieldValue("date_time", date_time);
			// insert.putFieldValue("username", username);
			// //��ȡ����ǰ�ͻ���������������
			// double task = Double.parseDouble(rwMap.get(username));
			// insert.putFieldValue("task", task);
			// String countString = dmo.getStringValueByDS(null,
			// "SELECT COUNT(*) FROM   V_sjyh  WHERE date_time='" + date_time +
			// "' AND xd_col2='" + username + "'");
			// double count = Double.parseDouble(countString == null ? "0" :
			// countString);
			// insert.putFieldValue("passed", count);
			// insert.putFieldValue("rate", count / task);
			// List<String> list = new ArrayList<String>();
			// list.add(deleteSQL);
			// list.add(insert.getSQL());
			// dmo.executeBatchByDS(null, list);
			HashMap<String, String> rwMap = dmo.getHashMapBySQLByDS(null,
					"select A,sum(O) from EXCEL_TAB_53 where year||'-'||month='"
							+ date_time + "' group by A");
			if (rwMap.size() <= 0) {
				return "�ڵ�ǰ�¡�" + date_time + "��ȱ��ҵ������,��������ڽ��в�ѯ";
			}
			HashMap<String, String> userMap = dmo
					.getHashMapBySQLByDS(
							null,
							"select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
			HashMap<String, String> jlMap = dmo.getHashMapBySQLByDS(null,
					"SELECT XD_COL2,count(A) FROM V_sjyh WHERE DATE_TIME='"
							+ date_time + "' GROUP BY XD_COL2");
			Set<String> keys = userMap.keySet();
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_sjyh_result");
			List<String> list = new ArrayList<String>();
			for (String key : keys) {
				insert.putFieldValue("username", key);
				insert.putFieldValue("date_time", getLastMonth(date_time));
				double task = Double.parseDouble(rwMap.get(key) == null
						|| rwMap.get(key).length() == 0 ? "0" : rwMap.get(key));
				insert.putFieldValue("task", task);
				double passed = Double.parseDouble(jlMap.get(key) == null ? "0"
						: jlMap.get(key));
				insert.putFieldValue("passed", passed);
				DecimalFormat format = new DecimalFormat("00.00");
				if (task == 0 || passed == 0) {
					insert.putFieldValue("rate", "00.00");
				} else {
					insert.putFieldValue("rate", format.format(passed / task
							* 100));
				}
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			result = "��ѯ�ɹ�";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * �������Կ��˵ķ��������仯
	 */
	@Override
	public String gradeDXEnd(String planid) {// ������ǰ���˼ƻ�:1.�����ֵ;2.�޸�״̬;
		String result = "";
		try {
			// ��ȡ����ǰ���й�Ա
			HashVO[] vos = dmo
					.getHashVoArrayByDS(null,
							"select * from V_PUB_USER_POST_1 where POSTNAME like '%��Ա%'");
			// HashMap<String,String> gyMap = dmo.getHashMapBySQLByDS(null,
			// "select usercode,deptcode from V_PUB_USER_POST_1 where POSTNAME like '%��Ա%'");
			// ��Ҫ��ʾ�ͻ����Ƿ���Ҫ���н���
			HashVO[] hvos = dmo
					.getHashVoArrayByDS(
							null,
							"SELECT * FROM wn_gydx_table WHERE STATE='������' OR FHRESULT IS NULL OR FHRESULT='����δͨ��'");
			if (hvos.length > 0) {// ������δ��ɵ�����½���
				double koufen = 0.0;
				double fenzhi = 0.0;
				String xiangmu = "";
				String khsm = "";
				UpdateSQLBuilder update = new UpdateSQLBuilder("wn_gydx_table");
				UpdateSQLBuilder update2 = new UpdateSQLBuilder("wn_gydx_table");
				List<String> sqlList = new ArrayList<String>();
				for (int i = 0; i < vos.length; i++) {// ���չ�Ա���б���
					double sum = 100.0;
					String maxTime = dmo.getStringValueByDS(null,
							"select max(pftime) from wn_gydx_table where usercode='"
									+ vos[i].getStringValue("usercode") + "'");
					String sql = "select * from  wn_gydx_table where usercode='"
							+ vos[i].getStringValue("usercode")
							+ "' and (state='������' or fhresult is null  or fhresult='����δͨ��') and pftime='"
							+ maxTime + "'";
					HashVO[] gyVos = dmo.getHashVoArrayByDS(null, sql);
					if (gyVos.length <= 0) {
						continue;
					}

					for (int j = 0; j < gyVos.length; j++) {// ��Ա���Ե÷ֽ���
						xiangmu = gyVos[j].getStringValue("xiangmu");
						khsm = gyVos[j].getStringValue("khsm");
						if ("�ܷ�".equals(xiangmu)) {
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
						update
								.setWhereCondition("usercode='"
										+ vos[i].getStringValue("usercode")
										+ "' and khsm='"
										+ khsm
										+ "' and (state='������' or fhresult is null  or fhresult='����δͨ��') ");
						update.putFieldValue("koufen", koufen);
						update.putFieldValue("state", "���ֽ���");
						sqlList.add(update.getSQL());
					}
					update2
							.setWhereCondition("usercode='"
									+ vos[i].getStringValue("usercode")
									+ "' and khsm='�ܷ�' and (state='������' or fhresult is null  or fhresult='����δͨ��')");
					update2.putFieldValue("fenzhi", sum);
					update2.putFieldValue("koufen", "");
					update2.putFieldValue("state", "���ֽ���");
					sqlList.add(update2.getSQL());
					if (sqlList.size() >= 5000) {
						dmo.executeBatchByDS(null, sqlList);
						sqlList.clear();
					}
				}
				dmo.executeBatchByDS(null, sqlList);
				result = "�����ּƻ������ɹ�";
			} else {// ֱ�ӽ���
				result = "�����ּƻ������ɹ�";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}

	/**
	 * zpy[2019-05-22] Ϊÿ����Ա���ɶ��Կ��˼ƻ�
	 */
	@Override
	public String gradeDXscore(String id) {
		String result = "";
		String planName = "";
		try {
			// ��ȡ����Ա�����Ϣ
			HashVO[] vos = dmo
					.getHashVoArrayByDS(null,
							"select * from V_PUB_USER_POST_1 where POSTNAME like '%��Ա%'");
			// ��ȡ����Ա���Կ���ָ��
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
				String gyName = vos[i].getStringValue("username");// ��ȡ����Ա����
				String gyDeptCode = vos[i].getStringValue("deptcode");// ��ȡ����ǰ�û��Ļ�����
				String gyUserCode = vos[i].getStringValue("usercode");// ��ȡ����ǰ��Ա�Ĺ�Ա��
				for (int j = 0; j < dxzbVos.length; j++) {
					String xiangmu = dxzbVos[j].getStringValue("CATALOG");// ������Ŀ
					String khsm = dxzbVos[j].getStringValue("name");// ����˵��
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
							+ "','0.0','������','"
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
						+ "','�ܷ�','�ܷ�','0.0','������','"
						+ pftime
						+ "')";
				_sqlList.add(sql);
				if (_sqlList.size() == 5000) {
					dmo.executeBatchByDS(null, _sqlList);
					_sqlList.clear();
				}
			}
			dmo.executeBatchByDS(null, _sqlList);
			sql = "update WN_GYDXPLAN set state='������' where id='" + id + "'";
			dmo.executeUpdateByDS(null, sql);
			result = "��ǰ���˼ƻ���" + planName + "�������ɹ�";
		} catch (Exception e) {
			result = "��ǰ���˼ƻ���" + planName + "������ʧ��,����";
			e.printStackTrace();
		} finally {
			return result;
		}
	}

	@Override
	public String gradeManagerDXscore(String id) {
		String result = "";
		try {
			// ��ȡ��������ʱ��
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
			// ��ȡ���ͻ���������Ϣ
			HashVO[] managerVos = dmo
					.getHashVoArrayByDS(null,
							"SELECT * FROM V_PUB_USER_POST_1 WHERE POSTNAME LIKE '%�ͻ�����%'");
			// ��ȡ���ͻ��������Կ��˼ƻ�
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
				for (int j = 0; j < vos.length; j++) {// ��ȡ��ÿһ��˼ƻ�ָ��
					String weight = vos[j].getStringValue("WEIGHTS");// ��ȡ��ָ��Ŀ���Ȩ��
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
					insert.putFieldValue("state", "������");
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
				insert2.putFieldValue("fenzhi", 0.0);
				insert2.putFieldValue("state", "������");
				insert2.putFieldValue("xiangmu", "�ܷ�");
				insert2.putFieldValue("pftime", new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss").format(new Date()));
				insert2.putFieldValue("khsm", "�ܷ�");
				insert2.putFieldValue("khMonth", lastMonth);
				_sqlList.add(insert2.getSQL());
			}
			dmo.executeBatchByDS(null, _sqlList);
			result = "��ǰ���˼ƻ����ɳɹ�";
		} catch (Exception e) {
			result = "��ǰ���˼ƻ�����ʧ��";
			e.printStackTrace();
		} finally {
			return result;
		}

	}

	private HashMap<String, String> getNhjdMap(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = dmo
					.getHashMapBySQLByDS(
							null,
							"select yb.xd_col2 xd_col2,zc.tj tj  from(select xx.xd_col96 xd_col96,count(xx.xd_col96) tj from (select XD_COL1,XD_COL96 from wnbank.s_loan_khxx where to_char(cast (cast (xd_col3 as timestamp) as date),'yyyy-mm-dd')='"
									+ date
									+ "'  and xd_col10 not in('δ����','����','!$') and XD_COL4='905') xx left join wnbank.S_LOAN_KHXXZCQK zc on xx.xd_col1=zc.xd_col1 where zc.XD_COL6='1' group by xx.xd_col96)zc left join wnbank.s_loan_ryb yb on zc.xd_col96=yb.xd_col1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
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

	private int getXwNum(String local, String username, String date_time) {// С΢�̻�
		int result = 0;
		try {
			String monthEnd = getLastMonth(date_time);
			String count = (dmo
					.getStringValueByDS(
							null,
							"select count(bb.mcht_id) num from (select a.a a ,b.b b from (select * from excel_tab_13 where b='"
									+ local
									+ "' and c='С΢�̻�' and year||'-'||month='"
									+ date_time
									+ "') a left join (select  * from excel_tab_16 where year||'-'||month='"
									+ date_time
									+ "') b on a.d=b.a)  aa left join  (select a.mcht_id mcht_id,a.num num from (select mcht_id, sum(kbsxj_m+sbsxj_m+zqbsxj_m+zzbsxj_m) num from wnbank.t_dis_ifsp_intgr_busi_dtl_cxb  where mcht_prop='С΢�̻�' and  to_char(to_date(day_id,'yyyy-mm-dd'),'yyyy-mm-dd')>='"
									+ date_time
									+ "-01"
									+ "' and  to_char(to_date(day_id,'yyyy-mm-dd'),'yyyy-mm-dd')<='"
									+ monthEnd
									+ "'  group by mcht_id) a  where a.num>=5) bb on bb.mcht_id=aa.a where bb.mcht_id is  not null and aa.b='"
									+ username + "' group by aa.b  "));
			result = Integer.parseInt(count == null ? "0" : count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private int getTyNum(String local, String username, String date_time) {// ��Լ�̻�
		int result = 0;
		try {
			String monthEnd = getLastMonth(date_time);
			String count = dmo
					.getStringValueByDS(
							null,
							"select count(bb.mcht_id) num from (select a.a a ,b.b b from (select * from excel_tab_13 where b='"
									+ local
									+ "' and c='��Լ�̻�' and year||'-'||month='"
									+ date_time
									+ "') a left join (select  * from excel_tab_16 where year||'-'||month='"
									+ date_time
									+ "') b on a.d=b.a)  aa left join  (select a.mcht_id mcht_id,a.num num from (select mcht_id, sum(kbsxj_m+sbsxj_m+zqbsxj_m+zzbsxj_m) num from wnbank.t_dis_ifsp_intgr_busi_dtl_cxb  where mcht_prop='��Լ�̻�' and  to_char(to_date(day_id,'yyyy-mm-dd'),'yyyy-mm-dd')>='"
									+ date_time
									+ "-01"
									+ "' and  to_char(to_date(day_id,'yyyy-mm-dd'),'yyyy-mm-dd')<='"
									+ monthEnd
									+ "'  group by mcht_id) a  where a.num>=10) bb on bb.mcht_id=aa.a where bb.mcht_id is  not NULL AND aa.b='"
									+ username + "' group by aa.b");
			result = Integer.parseInt(count == null ? "0" : count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}