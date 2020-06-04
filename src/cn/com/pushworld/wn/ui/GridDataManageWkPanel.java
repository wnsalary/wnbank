package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import cn.com.infostrategy.to.common.WLTConstants;
import cn.com.infostrategy.to.mdata.BillVO;
import cn.com.infostrategy.to.mdata.DeleteSQLBuilder;
import cn.com.infostrategy.to.mdata.InsertSQLBuilder;
import cn.com.infostrategy.to.mdata.UpdateSQLBuilder;
import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.BillDialog;
import cn.com.infostrategy.ui.common.ClientEnvironment;
import cn.com.infostrategy.ui.common.MessageBox;
import cn.com.infostrategy.ui.common.UIUtil;
import cn.com.infostrategy.ui.common.WLTButton;
import cn.com.infostrategy.ui.mdata.BillCardDialog;
import cn.com.infostrategy.ui.mdata.BillCardPanel;
import cn.com.infostrategy.ui.mdata.BillListDialog;
import cn.com.infostrategy.ui.mdata.BillListPanel;

/**
 * 网格信息修改
 * 
 * @author ZPY
 * 
 */
public class GridDataManageWkPanel extends AbstractWorkPanel implements
		ActionListener {

	private String code = "EXCEL_TAB_85_CODE";
	private BillListPanel listPanel = null;
	private WLTButton btn_add, btn_update, btn_delete, btn_log;// 新增 修改 删除
	private final String USERCODE = ClientEnvironment.getCurrLoginUserVO()
			.getCode();
	private final String USERNAME = ClientEnvironment.getCurrSessionVO()
			.getLoginUserName();
	private BillListPanel list;

	@Override
	public void initialize() {
		listPanel = new BillListPanel(code);
		btn_add = new WLTButton("新增");
		btn_add.addActionListener(this);
		btn_update = new WLTButton("修改");
		btn_update.addActionListener(this);
		btn_delete = new WLTButton("删除");
		btn_delete.addActionListener(this);
		btn_log = new WLTButton("日志查看");
		btn_log.addActionListener(this);
		listPanel
				.addBatchBillListButton(new WLTButton[] { btn_update, btn_log });
		list = new BillListPanel("WN_WGINFOUPDATE_LOG_CODE");
		listPanel.repaintBillListButton();// 刷新按钮
		this.add(listPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_add) {// 新增按钮
		// BillCardPanel cardpanel=new BillCardPanel("EXCEL_TAB_85_EDIT_CODE");
		// BillCardDialog dialog=new BillCardDialog(listPanel,
		// "新增",cardpanel,WLTConstants.BILLDATAEDITSTATE_INSERT);
		// dialog.setCardEditable(true);
		// WLTButton btn_confirm = dialog.getBtn_confirm();
		// dialog.setVisible(true);
		// dialog.setSaveBtnVisiable(false);
		// BillVO billVO = dialog.getBillVO();
		// if(btn_confirm==e.getSource()){
		// System.out.println("数据");
		// }
		// int closeType = dialog.getCloseType();
		// System.out.printf("closeType=%d",closeType);
		// MessageBox.show(this,"新增完成");
		} else if (e.getSource() == btn_update) {// 修改操作
			BillVO vo = listPanel.getSelectedBillVO();
			if (vo == null) {
				MessageBox.show(this, "请选中一条数据进行修改");
				return;
			}
			BillCardPanel cardPanel = new BillCardPanel(
					"EXCEL_TAB_85_EDIT_CODE");
			cardPanel.setBillVO(vo);
			BillCardDialog dialog = new BillCardDialog(listPanel, "修改",
					cardPanel, WLTConstants.BILLDATAEDITSTATE_UPDATE);// 修改设置
			dialog.setSaveBtnVisiable(false);
			dialog.setVisible(true);
			// UIUtil.getStringValueByDS(null,
			// "SELECT * FROM WNSALARYDB.PUB_USER WHERE CODE='"+newVo.getStringValue("G")+"'");
			try {
				int closeType = dialog.getCloseType();
				System.out.printf("closeType=%d", closeType);
				List<String> list = new ArrayList<String>();
				if (closeType == 1) {// 保存和确定都是1
					BillVO newVo = dialog.getBillVO();
					String gyName = UIUtil.getStringValueByDS(null,
							"SELECT NAME FROM WNSALARYDB.PUB_USER WHERE CODE='"
									+ newVo.getStringValue("G") + "'");
					String deptName = UIUtil.getStringValueByDS(null,
							"SELECT NAME FROM WNSALARYDB.pub_corp_dept WHERE CODE='"
									+ newVo.getStringValue("F") + "'");
					String wgNum = newVo.getStringValue("E");// 网格号
					UpdateSQLBuilder update = new UpdateSQLBuilder();
					update.setTableName("EXCEL_TAB_85");
					update.setWhereCondition("E='" + wgNum + "'");
					update.putFieldValue("D", newVo.getStringValue("D"));// 网格名称
					update.putFieldValue("F", newVo.getStringValue("F"));
					update.putFieldValue("A", deptName);
					update.putFieldValue("G", newVo.getStringValue("G"));
					update.putFieldValue("B", gyName);
					list.add(update.getSQL());
					InsertSQLBuilder insert = new InsertSQLBuilder();
					insert.setTableName("wn_wginfoupdate_log");
					insert.putFieldValue("code", USERCODE);
					insert.putFieldValue("name", USERNAME);
					insert.putFieldValue("dept_old", vo.getStringValue("F"));
					insert.putFieldValue("dept_new", newVo.getStringValue("F"));
					insert.putFieldValue("person_old", vo.getStringValue("G"));
					insert.putFieldValue("person_new",
							newVo.getStringValue("G"));
					insert.putFieldValue("wgname_old", vo.getStringValue("D"));
					insert.putFieldValue("wgname_new",
							newVo.getStringValue("D"));
					insert.putFieldValue("operate_name", "修改");
					insert.putFieldValue("update_time", new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").format(new Date()));
					list.add(insert.getSQL());
					UIUtil.executeBatchByDS(null, list);
				} else {
					return;
				}
				MessageBox.show(this, "修改完成");
				listPanel.refreshData();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			listPanel.refreshData();
		} else if (e.getSource() == btn_delete) {
			BillVO vo = listPanel.getSelectedBillVO();
			if (vo == null) {
				MessageBox.show(this, "请选中一条数据删除");
				return;
			}
			boolean confirm = MessageBox.confirm("确定删除当前选中网格信息吗?");
			if (confirm) {
				try {
					/**
					 * 操作日志记录
					 */
					InsertSQLBuilder insert = new InsertSQLBuilder();
					insert.setTableName("wn_wginfoupdate_log");// 记录操作日志
					insert.putFieldValue("operate_name", "删除");
					insert.putFieldValue("code", USERCODE);
					insert.putFieldValue("name", USERNAME);
					insert.putFieldValue("dept_old", vo.getStringValue("A"));
					insert.putFieldValue("person_old", vo.getStringValue("B"));
					insert.putFieldValue("wgname_old", vo.getStringValue("D"));
					insert.putFieldValue("update_time", new SimpleDateFormat(
							"yyyy-MM-dd").format(new Date()));
					UIUtil.executeUpdateByDS(null, insert.getSQL());
					DeleteSQLBuilder delete = new DeleteSQLBuilder(
							"EXCEL_TAB_85");
					delete.setWhereCondition("E='" + vo.getStringValue("E")
							+ "'");
					UIUtil.executeUpdateByDS(null, delete.getSQL());
					listPanel.refreshData();
					MessageBox.show(this, "删除成功");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} else if (e.getSource() == btn_log) {// 日志查看
			BillListDialog dialog = new BillListDialog(listPanel, "部门系数调整情况查看",
					list);
			dialog.getBtn_confirm().setVisible(false);
			dialog.getBilllistPanel().QueryDataByCondition("1=1");
			dialog.setVisible(true);
		}

	}
}
