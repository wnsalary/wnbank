package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import cn.com.infostrategy.to.mdata.BillVO;
import cn.com.infostrategy.to.mdata.UpdateSQLBuilder;
import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.MessageBox;
import cn.com.infostrategy.ui.common.UIUtil;
import cn.com.infostrategy.ui.common.WLTButton;
import cn.com.infostrategy.ui.mdata.BillListDialog;
import cn.com.infostrategy.ui.mdata.BillListHtmlHrefEvent;
import cn.com.infostrategy.ui.mdata.BillListHtmlHrefListener;
import cn.com.infostrategy.ui.mdata.BillListPanel;

public class WokerRadioWKPanel extends AbstractWorkPanel implements
		ActionListener {
	private BillListPanel listPanel;
	private BillListPanel list;
	private WLTButton bmButton, updateBatch;
	private JComboBox comboBox = null;

	@Override
	public void initialize() {
		listPanel = new BillListPanel("V_WN_MISSIONARY_ZPY_Q01");
		list = new BillListPanel("V_WN_FUNCTIONARY_ZPY_Q01");
		bmButton = new WLTButton("����ϵ���鿴");
		bmButton.addActionListener(this);
		updateBatch = new WLTButton("�����޸�");
		updateBatch.addActionListener(this);
		listPanel.addBatchBillListButton(new WLTButton[] { bmButton,
				updateBatch });
		listPanel.repaintBillListButton();
		listPanel.setRowNumberChecked(true);//��������
		this.add(listPanel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bmButton) {// ����ϵ���鿴
			// ������ʽ�鿴
			BillListDialog dialog = new BillListDialog(listPanel, "����ϵ���鿴",
					list);
			dialog.getBtn_confirm().setVisible(false);
			dialog.getBilllistPanel().QueryDataByCondition("1=1");
			dialog.setVisible(true);
		} else if (e.getSource() == updateBatch) {// �����޸�
			try{
				//��ȡ��ѡ����Ա��Ϣ
//	              BillVO[] billVos = listPanel.getSelectedBillVOs();
				BillVO[] billVos = listPanel.getCheckedBillVOs();
	              if(billVos==null || billVos.length<=0){
	            	  MessageBox.show(this,"��ѡ��һ�����ݽ��в���");
	            	  return;
	              }
	              UpdateSQLBuilder update=new UpdateSQLBuilder("sal_personinfo");
	              List<String> list=new ArrayList<String>();
	              String stationratio = JOptionPane.showInputDialog("�����������ĸ�λϵ��(ֻ������������):");
	              //��ֹ�������(�ͻ����������)
	             if(stationratio!=null &&!stationratio.matches("[0-9]*\\.?[0-9]*")){
	            	 MessageBox.show(this,"����ĸ�λϵ������,����");
	            	 return;
	             }
	             for (int i = 0; i < billVos.length; i++) {
					String code=billVos[i].getStringValue("gycode");
					update.setWhereCondition("code="+code);
					update.putFieldValue("STATIONRATIO",stationratio);
					list.add(update.getSQL());
				}
	            UIUtil.executeBatchByDS(null, list);
	            listPanel.refreshData();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}