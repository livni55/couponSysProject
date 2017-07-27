package d.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import a.ConnectionPool.ConnectionPool;
import b.JavaBeans.Company;
import b.JavaBeans.Coupon;
import b.JavaBeans.Customer;
import c.DAO.DAOException;
import c.DAO.JoinedTablesDAO;

/**
 * <p>
 * implementation of JoinedTablesDAO which enable to contact with the database
 * easily and safely.
 * </p>
 * All connections to the database are given by the link ConnectionPool, and
 * then returned to it. No opening or closing of connections are made here.<br>
 * This class is used by the Facades Layer of the application.
 */
public class JoinedTablesDBDAO implements JoinedTablesDAO {
	private ConnectionPool pool = ConnectionPool.getInstance();

	public JoinedTablesDBDAO() {
	}

	@Override
	public void createCompany_Coupon(Company company, Coupon coupon) throws DAOException {
		Connection connection = pool.getConnection();
		try {
			String sql = "INSERT INTO Company_Coupon VALUES(?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, company.getId());
			statement.setLong(2, coupon.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("Create Company_Coupon Failed!", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public void createCustomer_Coupon(Customer customer, Coupon coupon) throws DAOException {
		Connection connection = pool.getConnection();
		try {
			String sql = "INSERT INTO Customer_Coupon VALUES(?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, customer.getId());
			statement.setLong(2, coupon.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("Create Customer_Coupon Failed!", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public void RemoveCoupon(Coupon coupon) throws DAOException {
		Connection connection = pool.getConnection();
		try {
			String sql = "DELETE FROM Company_Coupon WHERE COUP_ID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, coupon.getId());
			statement.execute();
			sql = "DELETE FROM Customer_Coupon WHERE COUP_ID = ?";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, coupon.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("Delete Coupon Failed!", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public void RemoveCustomer_Coupon(Customer customer, Coupon coupon) throws DAOException {
		Connection connection = pool.getConnection();
		try {
			String sql = "DELETE FROM Customer_Coupon WHERE CUST_ID = ? AND COUP_ID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, customer.getId());
			statement.setLong(2, coupon.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("Delete Customer_Coupon Failed!", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public void RemoveCustomer(Customer customer) throws DAOException {
		Connection connection = pool.getConnection();
		try {
			String sql = "DELETE FROM Customer_Coupon WHERE CUST_ID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, customer.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("Delete Customer_Coupon Failed!", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public boolean isThereCompanyCoupon(long compID, long couponID) throws DAOException {
		Connection connection = pool.getConnection();
		try {
			String sql = "SELECT * FROM Company_Coupon WHERE COMP_ID = ? AND COUP_ID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, compID);
			statement.setLong(2, couponID);
			ResultSet set = statement.executeQuery();

			boolean isThere = set.next();
			set.close();
			return isThere;
		} catch (SQLException e) {
			throw new DAOException("Connection Failed!", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public boolean isThereCustomerCoupon(long custID, long couponID) throws DAOException {
		Connection connection = pool.getConnection();
		try {
			String sql = "SELECT * FROM Customer_Coupon WHERE CUST_ID = ? AND COUP_ID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, custID);
			statement.setLong(2, couponID);
			ResultSet set = statement.executeQuery();

			boolean isThere = set.next();
			set.close();
			return isThere;
		} catch (SQLException e) {
			throw new DAOException("Connection Failed!", e);
		} finally {
			pool.returnConnection(connection);
		}
	}
}
