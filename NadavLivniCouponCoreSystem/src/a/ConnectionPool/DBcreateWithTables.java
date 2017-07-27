package a.ConnectionPool;

import java.sql.Connection;

import c.DAO.DAOException;

/**
 * This class creates all the tables needed. it should only happen once.
 */

public class DBcreateWithTables {

	/**
	 * First it tries to drop the tables, if they are already up SQLECEPTION
	 * will pop and it will try to create all tables.
	 * 
	 * @throws DAOException
	 *             in errors
	 */
	public static void DBInit() throws DAOException {
		// public static void main(String[] args) throws DAOException {

		Connection con = ConnectionPool.getInstance().getConnection();

		String Comp = "CREATE TABLE Company(ID BIGINT PRIMARY KEY, COMP_NAME VARCHAR(50) NOT NULL UNIQUE, PASSWORD VARCHAR(10), EMAIL VARCHAR(1000))";
		String Cust = "CREATE TABLE Customer(ID BIGINT PRIMARY KEY,CUST_NAME VARCHAR(50) NOT NULL UNIQUE, PASSWORD VARCHAR(100))";
		String Coup = "CREATE TABLE Coupon(ID BIGINT PRIMARY KEY,TITLE VARCHAR(50) NOT NULL UNIQUE, START_DATE DATE,END_DATE DATE,AMOUNT INTEGER,TYPE VARCHAR(1000),MESSAGE VARCHAR(100),PRICE DOUBLE, IMAGE VARCHAR(1000))";
		String Cust_Coup = "CREATE TABLE Customer_Coupon(CUST_ID BIGINT ,COUP_ID BIGINT ,PRIMARY KEY(CUST_ID ,COUP_ID))";
		String Comp_Coup = "CREATE TABLE Company_Coupon(COMP_ID BIGINT ,COUP_ID BIGINT ,PRIMARY KEY(COMP_ID ,COUP_ID))";

		try {
			con.prepareStatement("drop table Company").execute();
		} catch (Exception e) {
		}
		try {
			con.prepareStatement("drop table Customer").execute();
		} catch (Exception e) {
		}
		try {
			con.prepareStatement("drop table Coupon").execute();
		} catch (Exception e) {
		}
		try {
			con.prepareStatement("drop table Customer_Coupon").execute();
		} catch (Exception e) {
		}
		try {
			con.prepareStatement("drop table Company_Coupon").execute();
		} catch (Exception e) {
		}
		try {
			con.prepareStatement(Comp).execute();
			System.out.println(Comp);
			con.prepareStatement(Cust).execute();
			System.out.println(Cust);
			con.prepareStatement(Coup).execute();
			System.out.println(Coup);
			con.prepareStatement(Cust_Coup).execute();
			System.out.println(Cust_Coup);
			con.prepareStatement(Comp_Coup).execute();
			System.out.println(Comp_Coup);
		} catch (Exception e) {
			throw new DAOException("Error while trying to init the DB" + e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		System.out.println("All Tables Created!");
		System.out.println("disconnected");
	}
}
