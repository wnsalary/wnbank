package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.infostrategy.bs.common.CommDMO;
import cn.com.infostrategy.to.mdata.InsertSQLBuilder;
import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.ClientEnvironment;
import cn.com.infostrategy.ui.common.WLTButton;
import cn.com.infostrategy.ui.mdata.BillListPanel;

public class TwiceDistribute extends AbstractWorkPanel implements ActionListener{
	
	 private CommDMO dmo = new CommDMO();
	 private BillListPanel billListPanel;
	 private WLTButton btn_submit, btn_edit,btn_select;
	 private String time;
	@Override
	public void actionPerformed(ActionEvent arg0) {
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_twicedistribute");
		time = billListPanel.getQuickQueryPanel().getCompentRealValue("time");
		List list = new ArrayList<String>();
		try {
			String[][] infor = dmo.getStringArrayByDS(null, "select deptname,username,usercode from v_pub_user_post_1");
			for(int i = 0; i < infor.length;i++){
				String id = dmo.getSequenceNextValByDS(null,"wn_twicedistribute");
				insert.putFieldValue("id", id);
				insert.putFieldValue("WDMC", infor[i][0]);
				insert.putFieldValue("name", infor[i][1]);
				insert.putFieldValue("code", infor[i][2]);
				insert.putFieldValue("time", time);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void initialize() {
		BillListPanel billListPanel = new BillListPanel("WN_TWICEDISTRIBUTE_CODE1");
		btn_submit = new WLTButton("提交");
		btn_submit.addActionListener(this);
		billListPanel.addBatchBillListButton(new WLTButton[] { btn_submit, btn_select, btn_edit });
		billListPanel.repaintBillListButton();
		btn_edit = billListPanel.getBillListBtn("$列表弹出编辑");
		btn_edit.addActionListener(this);
		btn_select = billListPanel.getBillListBtn("$列表查看");
		btn_select.addActionListener(this);
		billListPanel.setVisible(true);
		billListPanel.getQuickQueryPanel().addBillQuickActionListener(this);//获取到快速查询事件
		this.add(billListPanel);
	}
	
		

}
