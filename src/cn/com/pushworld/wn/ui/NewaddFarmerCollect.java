package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.mdata.BillListPanel;

public class NewaddFarmerCollect extends AbstractWorkPanel implements ActionListener {

	/**
	 * @author FJ[农民工管理指标完成比]
	 * 2019年6月4日14:36:09
	 */
	private static final long serialVersionUID = 1L;
	private BillListPanel billListPanel;
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==billListPanel.getQuickQueryPanel()){
			QuickQuery();
		}

	}

	private void QuickQuery() {
		String sql = "select * from V_NMGGLZB_WCL where 1=1 ";
		String date_time = billListPanel.getQuickQueryPanel().getCompentRealValue("KHDATE");
		if (date_time != null && !date_time.isEmpty()) {
			date_time = date_time.substring(0, date_time.length() - 1);
			date_time = date_time.replace("-", "年");
			sql = sql + " and KHDATE='" + date_time + "' ";
		}
		String code = billListPanel.getQuickQueryPanel().getCompentRealValue("code");
		if (code != null && !code.isEmpty()) {
			sql = sql + " and CODE like '%" + code + "%' ";
		}
		String name = billListPanel.getQuickQueryPanel().getCompentRealValue("j");
		if (name != null && !name.isEmpty()) {
			sql = sql + " and J like'%" + name + "%'";
		}
		billListPanel.QueryData(sql);
		
	}

	@Override
	public void initialize() {
		billListPanel = new BillListPanel("V_NMGGLZB_WCL_CODE1");
		billListPanel.getQuickQueryPanel().addBillQuickActionListener(this);
		this.add(billListPanel);

	}

}
