package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.com.infostrategy.to.common.WLTRemoteException;
import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.MessageBox;
import cn.com.infostrategy.ui.common.UIUtil;
import cn.com.infostrategy.ui.mdata.BillListPanel;

/**
 * 
 * @author FJ[农户建档指标完成比]
 * 2019年5月29日17:08:09
 */
public class NhjdCompletionRatio extends AbstractWorkPanel implements ActionListener {
	private BillListPanel billListPanel;

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==billListPanel.getQuickQueryPanel()){
			String[][] data;
			try {
				data = UIUtil.getStringArrayByDS(null, billListPanel.getQuickQueryPanel().getQuerySQL());
				if(data.length<=0){
					WnSalaryServiceIfc service = (WnSalaryServiceIfc) UIUtil.lookUpRemoteService(WnSalaryServiceIfc.class);
					String str=service.getNhjdHs(billListPanel.getQuickQueryPanel().getCompentRealValue("DATE_TIME").substring(0,billListPanel.getQuickQueryPanel().getCompentRealValue("DATE_TIME").length()-1));
					MessageBox.show(this,str);
					billListPanel.QueryData(billListPanel.getQuickQueryPanel().getQuerySQL());
				}else{
					billListPanel.QueryData(billListPanel.getQuickQueryPanel().getQuerySQL());
				}
			} catch (WLTRemoteException e1) {
				e1.printStackTrace();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
	}

	@Override
	public void initialize() {
		billListPanel = new BillListPanel("V_WN_NHJD_WCB_CODE1");
		billListPanel.getQuickQueryPanel().addBillQuickActionListener(this);
		this.add(billListPanel);
		
	}

}
