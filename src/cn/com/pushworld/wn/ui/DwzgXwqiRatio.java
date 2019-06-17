package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.UIUtil;
import cn.com.infostrategy.ui.mdata.BillListPanel;

public class DwzgXwqiRatio extends AbstractWorkPanel implements ActionListener {
    
	/**
	 * @author FJ[单位职工，小微企业建档指标完成比]
	 */
	private static final long serialVersionUID = 1L;
	private BillListPanel listPanel = null;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == listPanel.getQuickQueryPanel()){
			String[][] data;
			try {
				data = UIUtil.getStringArrayByDS(null, listPanel.getQuickQueryPanel().getQuerySQL());
				if(data.length<=0){
					WnSalaryServiceIfc service = (WnSalaryServiceIfc) UIUtil.lookUpRemoteService(WnSalaryServiceIfc.class);
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
	}

	@Override
	public void initialize() {
		listPanel = new BillListPanel("");
		listPanel.getQuickQueryPanel().addBillQuickActionListener(this);
		this.add(listPanel);
		
	}

}
