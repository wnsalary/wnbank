package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import cn.com.infostrategy.to.mdata.BillVO;
import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.MessageBox;
import cn.com.infostrategy.ui.common.WLTTabbedPane;
import cn.com.infostrategy.ui.mdata.BillListDialog;
import cn.com.infostrategy.ui.mdata.BillListHtmlHrefEvent;
import cn.com.infostrategy.ui.mdata.BillListHtmlHrefListener;
import cn.com.infostrategy.ui.mdata.BillListPanel;

public class LoanblWkPanel extends AbstractWorkPanel implements ActionListener,
		BillListHtmlHrefListener, ChangeListener {

	private BillListPanel listPanel, listPanel1, listPanel2, listPanel3;
	private WLTTabbedPane tab = null;

	@Override
	public void initialize() {
		listPanel = new BillListPanel("V_WN_SHCL_ZPY_Q01");
		listPanel.getQuickQueryPanel().addBillQuickActionListener(this);// 获取到查询条件
		listPanel.addBillListHtmlHrefListener(this);
		listPanel.repaintBillListButton();
		listPanel1 = new BillListPanel("V_WN_DQDKSHL_ZPY_Q01");// 到期贷款收回率考核
		listPanel1.addBillListHtmlHrefListener(this);
		listPanel1.repaintBillListButton();
		listPanel2 = new BillListPanel("V_WN_DNXZBLDK_ZPY_Q01");
		listPanel2.getQuickQueryPanel().addBillQuickActionListener(this);
		listPanel2.addBillListHtmlHrefListener(this);
		listPanel3=new BillListPanel("V_WN_SHBWBL_ZPY_Q01");
		listPanel3.repaintBillListButton();
		tab=new WLTTabbedPane();
		tab.addTab("收回存量不良贷款", listPanel);
		tab.addTab("到期贷款收回率考核", listPanel1);
		tab.addTab("当年新增不良贷款",listPanel2);
		tab.addTab("收回表外不良考核", listPanel3);
		tab.addChangeListener(this);
		this.add(tab);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == listPanel.getQuickQueryPanel()) {
			try {
				// 涉及到写查询条件
				String querySQL = listPanel.getQuickQueryPanel().getQuerySQL();
				String allSQL = "select a.cus_manager,a.xd_col1,a.xd_col2,a.xd_col3,a.xd_col4,a.xd_col5,a.xd_col6,a.xd_col7,a.xd_col16,a.xd_col22,a.LOAD_DATES,a.ck from ";
				SimpleDateFormat simple = new SimpleDateFormat("yyyy");
				Integer year = Integer.parseInt(simple.format(new Date())) - 1;
				String lastYearEnd = year + "-" + "12-31";
				allSQL = allSQL
						+ "("
						+ querySQL
						+ ") a left join (select * from wnbank.s_loan_dk where xd_col22 in ('03','04') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"
						+ lastYearEnd
						+ "') b on b.xd_col1=a.xd_col1 where b.xd_col1 is not null";
				listPanel.QueryData(allSQL);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (event.getSource() == listPanel2.getQuickQueryPanel()) {
			try {
              String querySQL=listPanel2.getQuickQueryPanel().getQuerySQL();
              System.out.println("当年新增不良贷款:"+querySQL);
              String allSQL="select a.xd_col1,a.xd_col2,a.xd_col4,a.xd_col5,a.xd_col6,a.xd_col7,a.xd_col22,a.xd_col16,a.cus_manager,a.load_dates, a.ck from ";
              SimpleDateFormat simple=new SimpleDateFormat("yyyy");
              Integer year=Integer.parseInt(simple.format(new Date()))-1;
              String lastYearEnd=year+"-12-31";
              allSQL=allSQL+" ("+querySQL+") a left join (select * from wnbank.s_loan_dk where xd_col22 in ('01','02') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+lastYearEnd+"')  b  on b.xd_col1=a.xd_col1 where b.xd_col1 is not null";
			  listPanel2.QueryData(allSQL);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onBillListHtmlHrefClicked(BillListHtmlHrefEvent event) {
		if(event.getSource()==listPanel){
			BillVO vo = listPanel.getSelectedBillVO();
			    BillListDialog dialog=new BillListDialog(listPanel,"查看","V_S_LOAN_HK_CODE1");
			    dialog.getBilllistPanel().QueryDataByCondition("xd_col1='"+vo.getStringValue("xd_col1")+"'");
			    dialog.getBtn_confirm().setVisible(false);
			    dialog.setVisible(true);
		}else if(event.getSource()==listPanel1){
			BillVO vo= listPanel1.getSelectedBillVO();
		    BillListDialog dialog=new BillListDialog(listPanel1,"查看","V_S_LOAN_HK_CODE1");
		    dialog.getBilllistPanel().QueryDataByCondition("xd_col1='"+vo.getStringValue("xd_col1")+"'");
		    dialog.getBtn_confirm().setVisible(false);
		    dialog.setVisible(true);
		}else if(event.getSource()==listPanel2){
			MessageBox.show("this message");
			BillVO vo= listPanel2.getSelectedBillVO();
		    BillListDialog dialog=new BillListDialog(this,"查看","V_S_LOAN_HK_CODE1");
		    dialog.getBilllistPanel().QueryDataByCondition("xd_col1='"+vo.getStringValue("xd_col1")+"'");
		    dialog.getBtn_confirm().setVisible(false);
		    dialog.setVisible(true);
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {

	}

}
