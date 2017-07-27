package a.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Used only as test class
 */
public class DBdropAlltables {

	public static void main(String[] args) {

		String driverName = "org.apache.derby.jdbc.ClientDriver";
		try {
			Class.forName(driverName);
			System.out.println(driverName + " loaded successfuly");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String urlCreate = "jdbc:derby://localhost:1527/projectDB;";

		try (Connection con = DriverManager.getConnection(urlCreate);) {
			System.out.println("connected to: " + urlCreate);

			con.prepareStatement("drop table Company").execute();
			con.prepareStatement("drop table Customer").execute();
			con.prepareStatement("drop table Coupon").execute();
			con.prepareStatement("drop table Customer_Coupon").execute();
			con.prepareStatement("drop table Company_Coupon").execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("disconnected");
	}

}
