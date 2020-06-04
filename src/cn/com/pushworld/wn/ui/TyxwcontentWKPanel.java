package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


import cn.com.infostrategy.to.common.HashVO;
import cn.com.infostrategy.to.mdata.Pub_Templet_1_ItemVO;
import cn.com.infostrategy.to.mdata.RefItemVO;
import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.MessageBox;
import cn.com.infostrategy.ui.common.SplashWindow;
import cn.com.infostrategy.ui.common.UIUtil;
import cn.com.infostrategy.ui.common.WLTButton;
import cn.com.infostrategy.ui.mdata.BillListPanel;
import cn.com.infostrategy.ui.mdata.cardcomp.RefDialog_Date;
import cn.com.pushworld.wn.bs.TyxwcontentCount;

public class TyxwcontentWKPanel extends AbstractWorkPanel implements ActionListener {

	private BillListPanel panel;
	private WLTButton btn_count,btn_export;
	private String message=null;
	private JFileChooser chooser;
	private String result;
	private String queryConditionSQL;
	@Override
	public void initialize() {
		panel=new BillListPanel("WN_TYXWCOUNT_RESULT_CODE");
		btn_count=new WLTButton("����ͳ��");
		btn_count.addActionListener(this);
		btn_export=new WLTButton("����");
		btn_export.addActionListener(this);
		panel.addBatchBillListButton(new WLTButton[]{btn_count,btn_export});
		panel.getQuickQueryPanel().addBillQuickActionListener(this);
		panel.repaintBillListButton();
		this.add(panel);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== btn_count){//����ͳ�� 

			try{
				RefItemVO refItemVO = new RefItemVO();
//				refItemVO.setId(report_date_new);
				RefDialog_Date date = new RefDialog_Date(this, "", refItemVO, null);
				/**
				 * ��ȡ���û�ѡ�е����ڣ�
				 * ÿ��ִ��SQLʱ�����������ѡ�����ڵĵ�ǰ���³�����ǰѡ������
				 * ��:�û�ѡ��2020-04-08�����������䶨�嵽2020-04-01~2020-04-08
				 */
				date.initialize();
				date.setVisible(true);// ��������ѡ���ɼ�
				if(date.getCloseType()==1){
				final RefItemVO ivo = date.getReturnRefItemVO();
				final	String curSelectDate =ivo.getId();// ��ǰѡ������
				final	String curSelectMonth=curSelectDate.substring(0,7);
					String exisSQL="select 1 from wn_tyxwcount_result where curmonth ='"+curSelectMonth+"'";
					String[]  exists=  UIUtil.getStringArrayFirstColByDS(null, exisSQL);
					final  TyxwcontentCount tyxw=new TyxwcontentCount();
				
					if(exists !=null && exists.length>0){// �Ѿ������Ƿ����¼���
						if(MessageBox.confirm(this, "���ڡ�" + curSelectDate + "��С΢��Լ�̻�ͳ����Ϣ�Ѿ����ڣ�ȷ���ظ�������")){
							new SplashWindow(this, new AbstractAction() {
								public void actionPerformed(ActionEvent e) {
									//��ԼС΢�̻�����
									try {
										message = tyxw.count(curSelectMonth+"-01", curSelectDate, curSelectMonth, true);
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
							});
						}
					}else {//��ǰ��������û�����ݣ�����Ҫɾ��
						new SplashWindow(this, new AbstractAction() {
							public void actionPerformed(ActionEvent e) {
								//��ԼС΢�̻�����
								try {
									message = tyxw.count(curSelectMonth+"-01", curSelectDate, curSelectMonth, false);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						});
					}
					MessageBox.show(this,message);
					panel.refreshData();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}else if(e.getSource()==panel.getQuickQueryPanel()) {// ��ȡ����Ӧ������
			String curMonth = panel.getQuickQueryPanel().getRealValueAt("CURMONTH");
			String sql="select * from wn_tyxwcount_result where  curMonth ='"+curMonth.replace("��", "-").replace("��;", "")+"'";
			panel.queryDataByDS(null, sql);
		}else  if(e.getSource() == btn_export){
			try {
				// ���õ����ļ�ѡ��Ի���
				chooser = new JFileChooser();
				final String templeName = panel.getTempletVO()
						.getTempletname();// ��ȡ��ģ�������
				chooser.setSelectedFile(new File(templeName + ".xls"));
				int showOpenDialog = chooser.showOpenDialog(null);
				final String filePath;
				if (showOpenDialog == JFileChooser.APPROVE_OPTION) {// ѡ��򿪵��ļ�
					filePath = chooser.getSelectedFile().getAbsolutePath();
				} else {
					return;
				}
				new SplashWindow(this, new AbstractAction() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						result = ImportExcel(filePath, templeName);
					}
				});
				MessageBox.show(this, result);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}

	public String ImportExcel(String filePath, String templeName) {
		String result = "";
		try {
			Workbook monitorBook = new SXSSFWorkbook(100);
			Sheet firstSheet = monitorBook.createSheet("��ԼС΢�̻�");
			Row firstRow = firstSheet.createRow(0);
			 Cell firstCell = null;
			 CellStyle firstCellStyle = monitorBook.createCellStyle();
			 firstCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			
			// ��ȡ����ͷ��Ϣ
			Pub_Templet_1_ItemVO[] templetItemVOs = panel
					.getTempletItemVOs();
			List<String> unShowList = new ArrayList<String>();
			Pub_Templet_1_ItemVO pub_Templet_1_ItemVO = null;
			List<String> colList = new ArrayList<String>();// ��ſ�����ʾ���ֶ�
			for (int i = 0, n = 0; i < templetItemVOs.length; i++) {
				pub_Templet_1_ItemVO = templetItemVOs[i];
				String cellKey = pub_Templet_1_ItemVO.getItemkey();
				String cellName = pub_Templet_1_ItemVO.getItemname();
				firstSheet.setColumnWidth(i,25*256);
				if (pub_Templet_1_ItemVO.isListisshowable()) {
					 firstCell=firstRow.createCell(i-n);
					firstCell.setCellValue(cellName);
					 firstCell.setCellStyle(firstCellStyle);//���ñ���ɫ�;�����ʾ
					colList.add(cellKey);
				} else {
					unShowList.add(cellKey.toUpperCase());
					n++;
				}
			}
			if (queryConditionSQL == null || "".equals(queryConditionSQL)) {
				queryConditionSQL = "select * from wn_tyxwcount_result where 1=1";
			}
			HashVO[] hashVos = UIUtil.getHashVoArrayByDS(null,
					queryConditionSQL);
			if (hashVos == null || hashVos.length == 0) {
				return "��ǰ��������Ҫ����";
			}
			Row nextRow = null;
		
			String cellValue = "";
			// �Գ���������������н��д���
			for (int i = 0; i < hashVos.length; i++) {
				nextRow = firstSheet.createRow(i + 1);
				String[] keys = hashVos[i].getKeys();// ����ǰ������ݿ��е�˳�����������е�
				System.out.println(Arrays.toString(keys));
				for (int j = 0, n = 0; j < colList.size(); j++) {// �Ե�ǰ����û��һ�����ݽ��д���
					if (unShowList.contains(colList.get(j).toUpperCase())) {
						n++;
						continue;
					}
					// �ӹ�����ÿһ������(��һ�������������ݱ��д洢����id����Ҫת����name�����)
				
					// ����(���� ������)
						cellValue = hashVos[i].getStringValue(colList.get(j));
					    nextRow.createCell(j - n).setCellValue(cellValue);
				}
			}
			// �����ݽ��д���
			String filename = filePath.substring(
					filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf("."));
			filePath = filePath.substring(0, filePath.lastIndexOf("\\"));
			File file = new File(filePath + "/" + filename + ".xls");
			int i = 1;
			while (file.exists()) {
				filename = templeName + i + ".xls";
				file = new File(filePath + "/" + filename);
				i++;
			}
			file.createNewFile();
			FileOutputStream fout = new FileOutputStream(file.getAbsolutePath());
			monitorBook.write(fout);
			fout.close();
			result = "���ݵ����ɹ�";
		} catch (Exception e) {
			result = "���ݵ���ʧ��";
			e.printStackTrace();
		}
		return result;
	}
}