package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.com.infostrategy.to.mdata.BillVO;
import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.mdata.BillListDialog;
import cn.com.infostrategy.ui.mdata.BillListHtmlHrefEvent;
import cn.com.infostrategy.ui.mdata.BillListHtmlHrefListener;
import cn.com.infostrategy.ui.mdata.BillListPanel;

public class LoanblWkPanel extends AbstractWorkPanel implements ActionListener,BillListHtmlHrefListener {

	private BillListPanel listPanel;
	@Override
	public void actionPerformed(ActionEvent actionevent) {
	}

	@Override
	public void initialize() {
		listPanel=new BillListPanel("V_BLLOAN_ZPY_Q01");
		listPanel.addBillListHtmlHrefListener(this);
		this.add(listPanel);
	}

	@Override
	public void onBillListHtmlHrefClicked(BillListHtmlHrefEvent event) {
		if(event.getSource()==listPanel){
			   BillVO vo= listPanel.getSelectedBillVO();
			    BillListDialog dialog=new BillListDialog(this,"还款信息","V_S_LOAN_HK_CODE1");
			    dialog.getBilllistPanel().QueryDataByCondition("xd_col1='"+vo.getStringValue("xd_col1")+"'");
			    dialog.getBtn_confirm().setVisible(false);
			    dialog.setVisible(true);
		}
		
	}

}
