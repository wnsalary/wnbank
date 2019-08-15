package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import cn.com.infostrategy.to.common.HashVO;
import cn.com.infostrategy.to.mdata.Pub_Templet_1_ItemVO;
import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.MessageBox;
import cn.com.infostrategy.ui.common.SplashWindow;
import cn.com.infostrategy.ui.common.UIUtil;
import cn.com.infostrategy.ui.common.WLTButton;
import cn.com.infostrategy.ui.mdata.BillListPanel;
import cn.com.pushworld.wn.to.WnUtils;

public class StaffMonitorNewShowWKPanel extends AbstractWorkPanel implements ActionListener {
	private BillListPanel listPanel;
	private WLTButton importButton;
	private JFileChooser fileChooser;
	private String message;
	@Override
	public void initialize() {
		listPanel=new BillListPanel("WN_DEAL_MONITOR_NEW_ZPY_Q01");
		importButton=new WLTButton("�쳣����������");
		importButton.addActionListener(this);
		listPanel.addBatchBillListButton(new WLTButton[]{importButton});
		listPanel.repaintBillListButton();
		this.add(listPanel);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==importButton){
			 try {
					String querySQLCondition = listPanel.getQuickQueryPanel().getQuerySQLCondition();//��ȡ����ǰ��ѯ����
					String sql="select * from wn_deal_monitor where 1=1 ";
					if(WnUtils.isEmpty(querySQLCondition)){//
						sql=sql+querySQLCondition;
					}
					 MessageBox.show(this,sql);
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
								message=createExcel(listPanel,filePath,selectSQL,"Ա���쳣��Ϊ������");
							}
						});
			           MessageBox.show(this,message);
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
}