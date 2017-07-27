package d.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import a.ConnectionPool.ConnectionPool;
import b.JavaBeans.Coupon;
import b.JavaBeans.Customer;
import c.DAO.CustomerDAO;
import c.DAO.DAOException;

/**
 * <p>
 * implementation of CustomerDAO which enable to contact with the
 * <b>CouponDB</b> database easily and safely.
 * </p>
 * All connections to the database are given by the ConnectionPool, and then
 * returned to it. No opening or closing of connections are made here. <br>
 */
public class CustomerDBDAO implements CustomerDAO {
	private ConnectionPool pool = ConnectionPool.getInstance();

	public CustomerDBDAO() {
	}

	@Override
	public void createCustomer(Customer customer) throws DAOException {
		String sql = "INSERT INTO Customer VALUES(?,?,?)";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, customer.getId());
			statement.setString(2, customer.getCustName());
			statement.setString(3, customer.getPassword());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException(
					"Create Customer Failed! A customer with ID '" + customer.getId() + "' already exist!", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public void removeCustomer(Customer customer) throws DAOException {
		String sql = "DELETE FROM Customer WHERE ID= ?";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, customer.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("Delete Customer failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws DAOException {
		String sql = "UPDATE Customer SET  PASSWORD= ? WHERE ID=?";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getPassword());
			statement.setLong(2, customer.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("Update Customer failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public Customer getCustomer(Long id) throws DAOException {
		String sql = "SELECT * FROM Customer  WHERE ID= ?";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet set = statement.executeQuery();
			if (set.next()) {
				Customer customer = new Customer();
				customer.setId(id);
				customer.setCustName(set.getString("CUST_NAME"));
				customer.setPassword(set.getString("PASSWORD"));
				set.close();
				return customer;
			} else {
				set.close();
				throw new DAOException("Customer with ID " + id + " does not exist");
			}
		} catch (SQLException e) {
			throw new DAOException("Get Customer failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public Customer getCustomer(String name) throws DAOException {
		String sql = "SELECT * FROM Customer  WHERE CUST_NAME= ?";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet set = statement.executeQuery();
			if (set.next()) {
				Customer customer = new Customer();
				customer.setCustName(name);
				customer.setId(set.getLong("ID"));
				customer.setPassword(set.getString("PASSWORD"));
				set.close();
				return customer;
			} else {
				set.close();
				throw new DAOException("Customer with NAME " + name + " does not exist");
			}
		} catch (SQLException e) {
			throw new DAOException("Get Customer failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public Collection<Customer> getAllCustomer() throws DAOException {
		String sql = "SELECT * FROM Customer";
		Connection connection = pool.getConnection();
		Collection<Customer> customers = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				Customer customer = new Customer(set.getLong(1), set.getString(2), set.getString(3));
				customers.add(customer);
			}
			set.close();
			return customers;

		} catch (SQLException e) {
			throw new DAOException("Get Customers failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public Collection<Coupon> getCoupons(Customer customer) throws DAOException {
		String sql = "SELECT COUP_ID FROM Customer_Coupon WHERE CUST_ID= ?";
		Connection connection = pool.getConnection();
		Collection<Coupon> coupons = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, customer.getId());
			ResultSet CouponIDset = statement.executeQuery();
			CouponDBDAO couponDBDAO = new CouponDBDAO();
			while (CouponIDset.next()) {
				long tempCouponId = CouponIDset.getLong(1);
				try {
					coupons.add(couponDBDAO.getCoupon(tempCouponId));
				} catch (DAOException e) {
					CouponIDset.close();
					throw new DAOException("Customer " + customer.getId() + " has Coupon " + tempCouponId
							+ " that doesn't exist any more!", e);
				}

			}
			CouponIDset.close();
			return coupons;
		} catch (SQLException e) {
			throw new DAOException("failed to Read Coupons for Customer with ID " + customer.getId(), e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public boolean login(String name, String password) throws DAOException {
		Connection connection = pool.getConnection();
		try {
			String sql = "SELECT * FROM Customer WHERE CUST_NAME= ? AND PASSWORD= ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.setString(2, password);
			ResultSet logInSet = statement.executeQuery();
			boolean loggedIn = logInSet.next();
			logInSet.close();
			return loggedIn;
		} catch (SQLException e) {
			throw new DAOException("Login Failed!", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

}
