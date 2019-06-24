package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.MessageBox;
import cn.com.infostrategy.ui.common.SplashWindow;
import cn.com.infostrategy.ui.common.UIUtil;
import cn.com.infostrategy.ui.common.WLTButton;
import cn.com.infostrategy.ui.mdata.BillListPanel;

public class ManagerScoreDXWKPanel extends AbstractWorkPanel implements ActionListener {
    
	private BillListPanel listPanel=null;
	private WLTButton rateButton;
	private String message;
	@Override
	public void initialize() {
		listPanel=new BillListPanel("WN_MANAGERDXSCORE_ZPY_Q01");
		rateButton=new WLTButton("等级评定");
		rateButton.addActionListener(this);
		listPanel.addBatchBillListButton(new WLTButton[]{rateButton});
		listPanel.repaintBillListButton();
		this.add(listPanel);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==rateButton){//客户经理等级评定
			try {
				
				 final WnSalaryServiceIfc service = (WnSalaryServiceIfc) UIUtil.lookUpRemoteService(WnSalaryServiceIfc.class);
				 new SplashWindow(this, new AbstractAction() {
					
					@Override
					public void actionPerformed(ActionEvent e) {//对客户经理进行评级考核功能开发
                      message=service.managerLevelCompute();
					}
				});
				 MessageBox.show(this,message);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}