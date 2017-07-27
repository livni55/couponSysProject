package d.DBDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import a.ConnectionPool.ConnectionPool;

public class test {
	public static void main(String[] args) throws SQLException {
		ConnectionPool pool = ConnectionPool.getInstance();
		PreparedStatement stmt = (pool.getConnection())
				.prepareStatement("select* from Customer_Coupon where(CUST_ID=123 and COUP_ID=123)");
		ResultSet set = stmt.executeQuery();
		/*
		 * while (set.next()) { Company comp = new Company(set.getLong(1),
		 * set.getString(2), set.getString(3), set.getString(4));
		 * System.out.println(comp); }
		 */
		if (set.next()) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
		System.out.println(set);
	}
}
