package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.ClientEnvironment;
import cn.com.infostrategy.ui.mdata.BillListPanel;
import freemarker.core.Environment;

public class GyServiceMX extends AbstractWorkPanel implements ActionListener{

	/**
	 * @auther FJ[柜员业务量查询]
	 * 2019年5月29日10:05:43
	 */
	private static final long serialVersionUID = 1L;
	private BillListPanel listPanel;
	
	@Override
	public void initialize() {
		listPanel = new BillListPanel("V_GYYW_MX_CODE1");
		listPanel.getQuickQueryPanel().addBillQuickActionListener(this);
		listPanel.repaintBillListButton();
		this.add(listPanel);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==listPanel.getQuickQueryPanel()){
			QuickQuery();
		}
		
	}
	private void QuickQuery() {
		String name = ClientEnvironment.getInstance().getLoginUserName();//获取当前登录人姓名
		String sqlCondition = listPanel.getQuickQueryPanel().getQuerySQLCondition();//获取快速查询面板上的查询条件
		String sql="select * from V_GYYW_MX  where 1=1 and B='"+name+"'"+sqlCondition;;
		listPanel.QueryData(sql);
	}
	
}
