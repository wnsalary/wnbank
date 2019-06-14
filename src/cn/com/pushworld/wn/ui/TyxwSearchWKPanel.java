package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.mdata.BillListPanel;

public class TyxwSearchWKPanel extends AbstractWorkPanel implements ActionListener{
    private BillListPanel listPanel;
	@Override
	public void initialize() {
		listPanel=new BillListPanel("V_TYXW_ZPY_Q01");
		listPanel.getQuickQueryPanel().addBillQuickActionListener(this);
        this.add(listPanel);		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==listPanel.getQuickQueryPanel()){
			QuerySearch();
		}
	}
	private void QuerySearch() {
		try {
		  String sql="select * from v_tyxw where 1=1 ";
		  String date_time=listPanel.getQuickQueryPanel().getCompentRealValue("date_time");
		  if(date_time!=null&&!date_time.isEmpty()){
			  date_time=date_time.substring(0,date_time.length()-1);
			  date_time=date_time.replace("年", "-").replace("月", "");
			  sql=sql+" and date_time='"+date_time+"'";
		  }
		  String cus_no=listPanel.getQuickQueryPanel().getCompentRealValue("A");
		  if(cus_no!=null&&!cus_no.isEmpty()){
			  sql=sql+" and A like '%"+cus_no+"%' ";
		  }
		  
		  String cus_type=listPanel.getQuickQueryPanel().getCompentRealValue("C");
		  if(cus_type!=null && !cus_type.isEmpty()){
			  sql=sql+" and C like '%"+cus_type+"%' ";
		  }
		  
		  String manager_name=listPanel.getQuickQueryPanel().getCompentRealValue("E");
		  if(manager_name!=null && !manager_name.isEmpty()){
			  sql=sql+" and E like '%"+manager_name+"%' ";
		  }
		  
		  String cus_name=listPanel.getQuickQueryPanel().getCompentRealValue("MCHT_NAME");
		  if(cus_name!=null && !cus_name.isEmpty()){
			  sql=sql+" and MCHT_NAME like '%"+cus_name+"%'";
		  }
		  System.out.println("当前执行的sql:"+sql);
		  listPanel.QueryData(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	

}
