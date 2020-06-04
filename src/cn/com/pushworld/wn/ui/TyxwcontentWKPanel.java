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
		btn_count=new WLTButton("户数统计");
		btn_count.addActionListener(this);
		btn_export=new WLTButton("导出");
		btn_export.addActionListener(this);
		panel.addBatchBillListButton(new WLTButton[]{btn_count,btn_export});
		panel.getQuickQueryPanel().addBillQuickActionListener(this);
		panel.repaintBillListButton();
		this.add(panel);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== btn_count){//户数统计 

			try{
				RefItemVO refItemVO = new RefItemVO();
//				refItemVO.setId(report_date_new);
				RefDialog_Date date = new RefDialog_Date(this, "", refItemVO, null);
				/**
				 * 获取到用户选中的日期，
				 * 每次执行SQL时，日期区间从选中日期的当前月月初到当前选中日期
				 * 如:用户选中2020-04-08，则日期区间定义到2020-04-01~2020-04-08
				 */
				date.initialize();
				date.setVisible(true);// 设置日期选择框可见
				if(date.getCloseType()==1){
				final RefItemVO ivo = date.getReturnRefItemVO();
				final	String curSelectDate =ivo.getId();// 当前选中日期
				final	String curSelectMonth=curSelectDate.substring(0,7);
					String exisSQL="select 1 from wn_tyxwcount_result where curmonth ='"+curSelectMonth+"'";
					String[]  exists=  UIUtil.getStringArrayFirstColByDS(null, exisSQL);
					final  TyxwcontentCount tyxw=new TyxwcontentCount();
				
					if(exists !=null && exists.length>0){// 已经存在是否重新计算
						if(MessageBox.confirm(this, "日期【" + curSelectDate + "】小微特约商户统计信息已经存在，确定重复计算吗？")){
							new SplashWindow(this, new AbstractAction() {
								public void actionPerformed(ActionEvent e) {
									//特约小微商户计算
									try {
										message = tyxw.count(curSelectMonth+"-01", curSelectDate, curSelectMonth, true);
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
							});
						}
					}else {//当前日期区间没有数据，不需要删除
						new SplashWindow(this, new AbstractAction() {
							public void actionPerformed(ActionEvent e) {
								//特约小微商户计算
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
		}else if(e.getSource()==panel.getQuickQueryPanel()) {// 获取到对应的数据
			String curMonth = panel.getQuickQueryPanel().getRealValueAt("CURMONTH");
			String sql="select * from wn_tyxwcount_result where  curMonth ='"+curMonth.replace("年", "-").replace("月;", "")+"'";
			panel.queryDataByDS(null, sql);
		}else  if(e.getSource() == btn_export){
			try {
				// 设置弹出文件选择对话框
				chooser = new JFileChooser();
				final String templeName = panel.getTempletVO()
						.getTempletname();// 获取到模板的名称
				chooser.setSelectedFile(new File(templeName + ".xls"));
				int showOpenDialog = chooser.showOpenDialog(null);
				final String filePath;
				if (showOpenDialog == JFileChooser.APPROVE_OPTION) {// 选择打开的文件
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
			Sheet firstSheet = monitorBook.createSheet("特约小微商户");
			Row firstRow = firstSheet.createRow(0);
			 Cell firstCell = null;
			 CellStyle firstCellStyle = monitorBook.createCellStyle();
			 firstCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			
			// 获取到表头信息
			Pub_Templet_1_ItemVO[] templetItemVOs = panel
					.getTempletItemVOs();
			List<String> unShowList = new ArrayList<String>();
			Pub_Templet_1_ItemVO pub_Templet_1_ItemVO = null;
			List<String> colList = new ArrayList<String>();// 存放可以显示的字段
			for (int i = 0, n = 0; i < templetItemVOs.length; i++) {
				pub_Templet_1_ItemVO = templetItemVOs[i];
				String cellKey = pub_Templet_1_ItemVO.getItemkey();
				String cellName = pub_Templet_1_ItemVO.getItemname();
				firstSheet.setColumnWidth(i,25*256);
				if (pub_Templet_1_ItemVO.isListisshowable()) {
					 firstCell=firstRow.createCell(i-n);
					firstCell.setCellValue(cellName);
					 firstCell.setCellStyle(firstCellStyle);//设置背景色和居中显示
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
				return "当前无数据需要导出";
			}
			Row nextRow = null;
		
			String cellValue = "";
			// 对除首行以外的数据行进行处理
			for (int i = 0; i < hashVos.length; i++) {
				nextRow = firstSheet.createRow(i + 1);
				String[] keys = hashVos[i].getKeys();// 这个是按照数据库中的顺序来进行排列的
				System.out.println(Arrays.toString(keys));
				for (int j = 0, n = 0; j < colList.size(); j++) {// 对当前行中没有一行数据进行处理
					if (unShowList.contains(colList.get(j).toUpperCase())) {
						n++;
						continue;
					}
					// 加工处理每一行数据(有一部分数据在数据表中存储的是id，需要转化成name来输出)
				
					// 其他(名称 描述等)
						cellValue = hashVos[i].getStringValue(colList.get(j));
					    nextRow.createCell(j - n).setCellValue(cellValue);
				}
			}
			// 对数据进行处理
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
			result = "数据导出成功";
		} catch (Exception e) {
			result = "数据导出失败";
			e.printStackTrace();
		}
		return result;
	}
}