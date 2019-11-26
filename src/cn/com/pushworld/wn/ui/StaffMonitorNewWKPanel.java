package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import cn.com.infostrategy.to.common.HashVO;
import cn.com.infostrategy.to.mdata.BillVO;
import cn.com.infostrategy.to.mdata.Pub_Templet_1_ItemVO;
import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.MessageBox;
import cn.com.infostrategy.ui.common.SplashWindow;
import cn.com.infostrategy.ui.common.UIUtil;
import cn.com.infostrategy.ui.common.WLTButton;
import cn.com.infostrategy.ui.mdata.BillCardDialog;
import cn.com.infostrategy.ui.mdata.BillListDialog;
import cn.com.infostrategy.ui.mdata.BillListHtmlHrefEvent;
import cn.com.infostrategy.ui.mdata.BillListHtmlHrefListener;
import cn.com.infostrategy.ui.mdata.BillListPanel;
import cn.com.pushworld.wn.to.WnUtils;

public class StaffMonitorNewWKPanel extends AbstractWorkPanel implements ActionListener,BillListHtmlHrefListener {
	private BillListPanel listPanel,sonListPanel;//����ģ��
	private JComboBox comboBox = null;//��ѡ��
	private WLTButton importButton;//���ݵ��밴ť
	private WLTButton sonexportButton;//��ģ�������ݵ�����excel��ť
	private WLTButton exportButton;//
	private WLTButton checkButton;//���ݴ���
	private String message;//��ʾ��Ϣ
	private JFileChooser fileChooser;
	@Override
	public void initialize() {//��ʼ
		listPanel=new BillListPanel("WN_GATHER_MONITOR_RESULT_ZPY_Q01");
//		sonListPanel=new BillListPanel("WN_SHOW_MONITOR_ZPY_Q01");
		sonListPanel=new BillListPanel("WN_CURRENT_DEAL_DATE_ZPY_Q01");
		
		comboBox=new  JComboBox();
		importButton=new WLTButton("Ա���쳣��Ϣ��ѯ");
		importButton.addActionListener(this);
		exportButton=new WLTButton("���ܽ������");
		exportButton.addActionListener(this);
		sonexportButton=new WLTButton("��ϸ���ݵ���");
		sonexportButton.addActionListener(this);
		checkButton=new WLTButton("Ա���쳣���ݴ���");
		checkButton.addActionListener(this);
		listPanel.addBatchBillListButton(new WLTButton[]{importButton,exportButton,checkButton});
		listPanel.repaintBillListButton();
		listPanel.addBillListHtmlHrefListener(this);
		listPanel.getQuickQueryPanel().addBillQuickActionListener(this);//��д���ٲ�ѯ�ķ���
		listPanel.setRowNumberChecked(true);//��������
		sonListPanel.addBatchBillListButton(new WLTButton[]{sonexportButton});
		sonListPanel.repaintBillListButton();
		this.add(listPanel);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	     if(e.getSource()==importButton){//Ա���쳣��Ϊ���ݵ���
	    	 try {
	    		 final WnSalaryServiceIfc service = (WnSalaryServiceIfc) UIUtil
							.lookUpRemoteService(WnSalaryServiceIfc.class);
	    		 new SplashWindow(this, new AbstractAction() {
					@Override
					public void actionPerformed(ActionEvent e) {
						message=service.importMonitorData();
					}
				});
	    		 MessageBox.show(this,message);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	     }else if(e.getSource()==sonexportButton){//���ݵ�����excel����
	    	 try {
	    		 sonListPanel.exportToExcel();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	     }else if(e.getSource()==checkButton){
	    	 try {
			   final BillVO[] billVos = listPanel.getCheckedBillVOs();
				if(billVos==null || billVos.length<=0){
					MessageBox.show(this,"��ѡ��һ�����߶������ݽ��д���");
					return;
				}
				String dealMessage="";
				for (int i = 0; i < billVos.length; i++) {
					if(!billVos[i].getStringValue("deal_result").equals("δ����")){
						dealMessage=dealMessage+billVos[i].getStringValue("NAME")+",";
					}
				}
				if(!"".equals(dealMessage)){
					dealMessage=dealMessage.substring(0,dealMessage.lastIndexOf(","));
					MessageBox.show(this,"��ǰѡ�С�"+dealMessage+"��Ա���쳣�����Ѿ����������ظ�����");
					return;
				}
				final BillCardDialog cardDialog=new BillCardDialog(this,"Ա���쳣��Ϣ����","WN_CURRENT_CHECK_RESULT_ZPY_Q01",600,300);
				cardDialog.getBillcardPanel().setEditable("CHECK_RESULT", true);
			    cardDialog.getBillcardPanel().setEditable("CHECK_REASON",true);
			    cardDialog.getBtn_save().setVisible(false);
				final WnSalaryServiceIfc service = (WnSalaryServiceIfc) UIUtil
							.lookUpRemoteService(WnSalaryServiceIfc.class);
				cardDialog.getBtn_confirm().addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						   Map<String, String> paraMap= new HashMap<String, String>();
						   paraMap.put("CHECK_RESULT", cardDialog.getCardItemValue("CHECK_RESULT"));
		      		       paraMap.put("CHECK_REASON", cardDialog.getCardItemValue("CHECK_REASON"));
		      		       paraMap.put("CHECK_USERCODE",cardDialog.getCardItemValue("CHECK_USERCODE"));
		      		       paraMap.put("CHECK_USERNAME",cardDialog.getCardItemValue("CHECK_USERNAME"));
		      		       paraMap.put("CHECK_DATE", cardDialog.getCardItemValue("CHECK_DATE"));
		      		       message=service.dealExceptionData(billVos,paraMap);
						   cardDialog.closeMe();
					}
				});
				cardDialog.setVisible(true);
				MessageBox.show(this,message);
				listPanel.refreshData();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	     }else if(e.getSource()==exportButton){//���ܽ������
	    	 try {
				String querySQLCondition = listPanel.getQuickQueryPanel().getQuerySQLCondition();//��ȡ����ǰ��ѯ����
				String sql="select * from WN_GATHER_MONITOR_RESULT where 1=1 ";
				if(WnUtils.isEmpty(querySQLCondition)){//
					sql=sql+querySQLCondition.replaceAll(";", "").replaceAll("��", "-").replaceAll("��","");
				}
				fileChooser=new JFileChooser();
		        String templetName = listPanel.getTempletVO().getTempletname();//��ȡ��ģ������
		         fileChooser.setSelectedFile(new File(templetName+".xls"));
		         int showOpenDialog = fileChooser.showOpenDialog(null);
	             final String filePath;
	             if(showOpenDialog==JFileChooser.APPROVE_OPTION){//ѡ����ļ�
	            	 filePath=fileChooser.getSelectedFile().getAbsolutePath();
	             }else {
	            	 return;
	             }
	             final String selectSQL=sql;
	             new SplashWindow(this , new AbstractAction() {
						@Override
						public void actionPerformed(ActionEvent e) {
							message=createExcel(listPanel,filePath,selectSQL,"Ա���쳣��Ϊ�������");
						}
					});
		           MessageBox.show(this,message);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	     }else  if(e.getSource()==listPanel.getQuickQueryPanel()){
	    	 try {
			    String queryCondition=listPanel.getQuickQueryPanel().getQuerySQLCondition().replaceAll(";", "").replaceAll("��", "-").replaceAll("��", "");
	    	    String querySQL="select * from WN_GATHER_MONITOR_RESULT where 1=1 "+queryCondition;
	    	    listPanel.QueryData(querySQL);
	    	 } catch (Exception e2) {
				e2.printStackTrace();
			}
	     }
	}
    //excel ��������
	public String createExcel(BillListPanel listPanel,String filePath,String selectSQL,String sheetName){
		String result="";
		try {
			 String templetName = listPanel.getTempletVO().getTempletname();//��ȡ��ģ������
			 Workbook monitorResult=new SXSSFWorkbook(100);
             Sheet firstSheet = monitorResult.createSheet(sheetName);//����sheet
             Row firstRow = firstSheet.createRow(0);//��������
             //��ȡ����ͷ��Ϣ
             Pub_Templet_1_ItemVO[] templetItemVOs = listPanel.getTempletItemVOs();
             List<String> unShowList=new ArrayList<String>();
             for (int i = 0,n = 0; i < templetItemVOs.length; i++) {
            	 Pub_Templet_1_ItemVO pub_Templet_1_ItemVO = templetItemVOs[i];
            	 String cellKey=pub_Templet_1_ItemVO.getItemkey();
            	 String cellName=pub_Templet_1_ItemVO.getItemname();
            	 if(pub_Templet_1_ItemVO.isListisshowable()){
            		 firstRow.createCell(i-n).setCellValue(cellName);
				 }else{
					 unShowList.add(cellKey);
					 n++;
				 }
			}
            HashVO[] hashVos = UIUtil.getHashVoArrayByDS(null, selectSQL);
            Row nextRow = null;
            for (int i = 0; i < hashVos.length; i++) {
            	nextRow=firstSheet.createRow(i+1);//������һ������
            	String[] keys = hashVos[i].getKeys();
            	for (int j = 0,n=0; j < keys.length; j++) {//��ÿһ�����ݽ��д���
					if(unShowList.contains(keys[j].toUpperCase())){//�жϵ�ǰ���Ƿ��Ѿ�����
						n++;
						continue;
					}
					nextRow.createCell(j-n).setCellValue(hashVos[i].getStringValue(keys[j]));
				}
			}
            String fileName=filePath.substring(filePath.lastIndexOf("\\")+1,filePath.lastIndexOf("."));
            filePath=filePath.substring(0,filePath.lastIndexOf("\\"));
            File file=new File(filePath+"/"+fileName+".xls");
            int i=1;
            while(file.exists()){
            	fileName=templetName+i+".xls";
            	file=new File(filePath+"/"+fileName);
            	i++;
            }
            file.createNewFile();
            FileOutputStream fout = new FileOutputStream(file.getAbsolutePath());
            monitorResult.write(fout);
            fout.close();
            result="�����ɹ�";
		} catch (Exception e) {
			result="����ʧ��";
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public void onBillListHtmlHrefClicked(BillListHtmlHrefEvent event) {
		
		if(event.getSource()==listPanel){
			BillVO vo = listPanel.getSelectedBillVO();
			BillListDialog dialog = new BillListDialog(this, "�鿴",
					sonListPanel);
			
			dialog.getBilllistPanel().QueryDataByCondition(
					"EXTERNAL_CUSTOMER_IC='"+vo.getStringValue("EXTERNAL_CUSTOMER_IC")+"' and DAT_TXN LIKE '"+vo.getStringValue("DAT_TXN")+"%'");
			dialog.getBtn_confirm().setVisible(false);
			dialog.setVisible(true);
		}
	}
}
