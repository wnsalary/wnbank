package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.infostrategy.bs.common.CommDMO;
import cn.com.infostrategy.to.common.WLTConstants;
import cn.com.infostrategy.to.common.WLTRemoteException;
import cn.com.infostrategy.to.mdata.BillVO;
import cn.com.infostrategy.to.mdata.InsertSQLBuilder;
import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.ClientEnvironment;
import cn.com.infostrategy.ui.common.MessageBox;
import cn.com.infostrategy.ui.common.UIUtil;
import cn.com.infostrategy.ui.common.WLTButton;
import cn.com.infostrategy.ui.mdata.BillCardDialog;
import cn.com.infostrategy.ui.mdata.BillCardPanel;
import cn.com.infostrategy.ui.mdata.BillListPanel;

public class TwiceDistribute extends AbstractWorkPanel implements ActionListener{
	
	 private UIUtil uiutil = new UIUtil();
	 private BillListPanel billListPanel =null;
	 private WLTButton btn_submit, btn_edit,btn_select;
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn_submit){
			try {
				onSubmit();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}else if(e.getSource()==btn_edit){
			onEdit();
		}else if(e.getSource()==btn_select){
			onSelect();
		}else if(e.getSource()==billListPanel.getQuickQueryPanel()){
		    String status="�ݸ�"; 
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_twicedistribute");
			String time = billListPanel.getQuickQueryPanel().getCompentRealValue("time");
			List list = new ArrayList<String>();
			try {
				String[][] chaxun= uiutil.getStringArrayByDS(null,"select * from wn_twicedistribute where time='"+time+"'");
				if(chaxun.length<=0){
			try {
				String[][] infor = uiutil.getStringArrayByDS(null, "select deptname,username,usercode from v_pub_user_post_1");
				for(int i = 0; i < infor.length;i++){
					String id = UIUtil.getSequenceNextValByDS(null,"wn_twicedistribute");
					insert.putFieldValue("id", id);
					insert.putFieldValue("WDMC", infor[i][0]);
					insert.putFieldValue("name", infor[i][1]);
					insert.putFieldValue("code", infor[i][2]);
					insert.putFieldValue("time", time);
					insert.putFieldValue("money", 0);
					insert.putFieldValue("status",status);
					list.add(insert.getSQL());
				}
				uiutil.executeBatchByDS(null, list);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			Query();
			}
	}

	public void onSelect() {
		BillVO billvo=billListPanel.getSelectedBillVO();
		if(billvo==null||billvo.equals("")){
			MessageBox.show("��ѡ��һ����¼��ִ�д˲�����");
			return;
		}else{
			BillCardPanel cardPanel = new BillCardPanel("WN_TWICEDISTRIBUTE_CODE1");
			BillCardDialog cardDialog = new BillCardDialog(billListPanel, "�鿴", cardPanel, WLTConstants.BILLDATAEDITSTATE_INIT);
			cardPanel.setBillVO(billvo);
			cardDialog.setVisible(true); 
		}
		
	}

	public void onEdit() {
		BillVO billvo=billListPanel.getSelectedBillVO();
		if(billvo==null||billvo.equals("")){
			MessageBox.show("��ѡ��һ����¼��ִ�д˲�����");
			return;
		}
		String status = billvo.getStringValue("status");
		if(status.equals("���ύ")){
			MessageBox.show("�ü�¼���ύ�޷��޸�");
			return;
		}else{
			BillCardPanel cardpanel = new BillCardPanel("WN_TWICEDISTRIBUTE_CODE1");
			cardpanel.setBillVO(billvo);
			BillCardDialog dialog = new BillCardDialog(billListPanel, "�༭", cardpanel, WLTConstants.BILLDATAEDITSTATE_UPDATE);
			dialog.setVisible(true);
			cardpanel.setEditable("status", false);//�����޸�ʱ�������޸�״̬
			billListPanel.refreshCurrSelectedRow();
		}
		
	}

	public void onSubmit() throws Exception {
		BillVO billvo=billListPanel.getSelectedBillVO();
		if(billvo==null){
			MessageBox.show("��ѡ��һ����¼��ִ�д˲�����");
			return;
		}else{
			String id = billvo.getStringValue("id");
			uiutil.executeUpdateByDS(null,"update wn_twicedistribute set status='���ύ' where id='"+id+"'");
			billListPanel.refreshData();
		}
		
	}

	public void Query() {
		String sqlCondition = billListPanel.getQuickQueryPanel().getQuerySQLCondition();
		String sql = "select * from wn_twicedistribute where 1=1 "+sqlCondition+"";
		billListPanel.QueryData(sql);
		
	}

	@Override
	public void initialize() {
		billListPanel = new BillListPanel("WN_TWICEDISTRIBUTE_CODE1");
		btn_submit = new WLTButton("�ύ");
		btn_submit.addActionListener(this);
		btn_edit = new WLTButton("�޸�");
		btn_edit.addActionListener(this);
		btn_select = new WLTButton("���");
		btn_select.addActionListener(this);
		billListPanel.addBatchBillListButton(new WLTButton[] { btn_submit, btn_select, btn_edit });
		billListPanel.repaintBillListButton();
		billListPanel.setVisible(true);
		billListPanel.getQuickQueryPanel().addBillQuickActionListener(this);//��ȡ�����ٲ�ѯ�¼�
		this.add(billListPanel);
	}
	
		

}
