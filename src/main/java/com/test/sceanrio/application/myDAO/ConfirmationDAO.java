package com.test.sceanrio.application.myDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.test.sceanrio.application.model.ConfirmationBean;
import com.test.sceanrio.application.queryutil.ConfirmationQueryUtil;

import Utilities.AutomationLog;
import Utilities.DatabaseUtil;

public class ConfirmationDAO {
    
	private ConfirmationQueryUtil objConfirmationQueryUtil = new ConfirmationQueryUtil();
	List<Integer> historyid ;
	public int prerequisitesDropQuery(ConfirmationBean bean) {
		Connection conn = null;
		Statement ps = null;
		int result = 0;
		try {
			conn = DatabaseUtil.getDatabaseConnection();
			ps=conn.createStatement();
			result=ps.executeUpdate(objConfirmationQueryUtil.getQuery("DROP_TEMP_CONF_AUTO_DEAL_TABLE"));
		} catch (SQLException e) {
			AutomationLog.error(e.getMessage());
		} finally {
			DatabaseUtil.close(conn, ps);
		}
		return result;
	}

	public void getDeal(ConfirmationBean bean) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int counter = 1;
		try {
			conn = DatabaseUtil.getDatabaseConnection();
			ps = conn.prepareStatement(objConfirmationQueryUtil.getQuery("GET_DEAL_NEW"));
			ps.setString(counter++, bean.getDeal());
			rs = ps.executeQuery();
			while (rs.next()) {
				bean.setDeal(rs.getString("Deal"));
			}
		} catch (SQLException e) {
			AutomationLog.error(e.getMessage(), e);
		} finally {
			DatabaseUtil.close(conn, ps, rs);
		}
}
	}
