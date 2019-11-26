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
	private BillListPanel listPanel,sonListPanel;//创建模板
	private JComboBox comboBox = null;//复选框
	private WLTButton importButton;//数据导入按钮
	private WLTButton sonexportButton;//子模板上数据导出成excel按钮
	private WLTButton exportButton;//
	private WLTButton checkButton;//数据处理
	private String message;//提示信息
	private JFileChooser fileChooser;
	@Override
	public void initialize() {//初始
		listPanel=new BillListPanel("WN_GATHER_MONITOR_RESULT_ZPY_Q01");
//		sonListPanel=new BillListPanel("WN_SHOW_MONITOR_ZPY_Q01");
		sonListPanel=new BillListPanel("WN_CURRENT_DEAL_DATE_ZPY_Q01");
		
		comboBox=new  JComboBox();
		importButton=new WLTButton("员工异常信息查询");
		importButton.addActionListener(this);
		exportButton=new WLTButton("汇总结果导出");
		exportButton.addActionListener(this);
		sonexportButton=new WLTButton("明细数据导出");
		sonexportButton.addActionListener(this);
		checkButton=new WLTButton("员工异常数据处理");
		checkButton.addActionListener(this);
		listPanel.addBatchBillListButton(new WLTButton[]{importButton,exportButton,checkButton});
		listPanel.repaintBillListButton();
		listPanel.addBillListHtmlHrefListener(this);
		listPanel.getQuickQueryPanel().addBillQuickActionListener(this);//重写快速查询的方法
		listPanel.setRowNumberChecked(true);//设置启动
		sonListPanel.addBatchBillListButton(new WLTButton[]{sonexportButton});
		sonListPanel.repaintBillListButton();
		this.add(listPanel);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	     if(e.getSource()==importButton){//员工异常行为数据导入
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
	     }else if(e.getSource()==sonexportButton){//数据导出成excel操作
	    	 try {
	    		 sonListPanel.exportToExcel();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	     }else if(e.getSource()==checkButton){
	    	 try {
			   final BillVO[] billVos = listPanel.getCheckedBillVOs();
				if(billVos==null || billVos.length<=0){
					MessageBox.show(this,"请选中一条或者多条数据进行处理");
					return;
				}
				String dealMessage="";
				for (int i = 0; i < billVos.length; i++) {
					if(!billVos[i].getStringValue("deal_result").equals("未处理")){
						dealMessage=dealMessage+billVos[i].getStringValue("NAME")+",";
					}
				}
				if(!"".equals(dealMessage)){
					dealMessage=dealMessage.substring(0,dealMessage.lastIndexOf(","));
					MessageBox.show(this,"当前选中【"+dealMessage+"】员工异常数据已经处理，请勿重复操作");
					return;
				}
				final BillCardDialog cardDialog=new BillCardDialog(this,"员工异常信息处理","WN_CURRENT_CHECK_RESULT_ZPY_Q01",600,300);
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
	     }else if(e.getSource()==exportButton){//汇总结果导出
	    	 try {
				String querySQLCondition = listPanel.getQuickQueryPanel().getQuerySQLCondition();//获取到当前查询条件
				String sql="select * from WN_GATHER_MONITOR_RESULT where 1=1 ";
				if(WnUtils.isEmpty(querySQLCondition)){//
					sql=sql+querySQLCondition.replaceAll(";", "").replaceAll("年", "-").replaceAll("月","");
				}
				fileChooser=new JFileChooser();
		        String templetName = listPanel.getTempletVO().getTempletname();//获取到模板名称
		         fileChooser.setSelectedFile(new File(templetName+".xls"));
		         int showOpenDialog = fileChooser.showOpenDialog(null);
	             final String filePath;
	             if(showOpenDialog==JFileChooser.APPROVE_OPTION){//选择打开文件
	            	 filePath=fileChooser.getSelectedFile().getAbsolutePath();
	             }else {
	            	 return;
	             }
	             final String selectSQL=sql;
	             new SplashWindow(this , new AbstractAction() {
						@Override
						public void actionPerformed(ActionEvent e) {
							message=createExcel(listPanel,filePath,selectSQL,"员工异常行为监测数据");
						}
					});
		           MessageBox.show(this,message);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	     }else  if(e.getSource()==listPanel.getQuickQueryPanel()){
	    	 try {
			    String queryCondition=listPanel.getQuickQueryPanel().getQuerySQLCondition().replaceAll(";", "").replaceAll("年", "-").replaceAll("月", "");
	    	    String querySQL="select * from WN_GATHER_MONITOR_RESULT where 1=1 "+queryCondition;
	    	    listPanel.QueryData(querySQL);
	    	 } catch (Exception e2) {
				e2.printStackTrace();
			}
	     }
	}
    //excel 导出操作
	public String createExcel(BillListPanel listPanel,String filePath,String selectSQL,String sheetName){
		String result="";
		try {
			 String templetName = listPanel.getTempletVO().getTempletname();//获取到模板名称
			 Workbook monitorResult=new SXSSFWorkbook(100);
             Sheet firstSheet = monitorResult.createSheet(sheetName);//创建sheet
             Row firstRow = firstSheet.createRow(0);//创建首行
             //获取到表头信息
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
            	nextRow=firstSheet.createRow(i+1);//创建下一行数据
            	String[] keys = hashVos[i].getKeys();
            	for (int j = 0,n=0; j < keys.length; j++) {//对每一行数据进行处理
					if(unShowList.contains(keys[j].toUpperCase())){//判断当前列是否已经隐藏
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
            result="导出成功";
		} catch (Exception e) {
			result="导出失败";
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public void onBillListHtmlHrefClicked(BillListHtmlHrefEvent event) {
		
		if(event.getSource()==listPanel){
			BillVO vo = listPanel.getSelectedBillVO();
			BillListDialog dialog = new BillListDialog(this, "查看",
					sonListPanel);
			
			dialog.getBilllistPanel().QueryDataByCondition(
					"EXTERNAL_CUSTOMER_IC='"+vo.getStringValue("EXTERNAL_CUSTOMER_IC")+"' and DAT_TXN LIKE '"+vo.getStringValue("DAT_TXN")+"%'");
			dialog.getBtn_confirm().setVisible(false);
			dialog.setVisible(true);
		}
	}
}
