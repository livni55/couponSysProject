package d.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import a.ConnectionPool.ConnectionPool;
import b.JavaBeans.Company;
import b.JavaBeans.Coupon;
import c.DAO.CompanyDAO;
import c.DAO.DAOException;

/**
 * <p>
 * implementation of CompanyDAO which enable to contact with the <b>CouponDB</b>
 * database easily and safely.
 * </p>
 * All connections to the database are given by the ConnectionPool, and then
 * returned to it. No opening or closing of connections are made here.
 */
public class CompanyDBDAO implements CompanyDAO {
	private ConnectionPool pool = ConnectionPool.getInstance();

	public CompanyDBDAO() {
	}

	@Override
	public void createCompany(Company company) throws DAOException {
		String sql = "insert into Company values(?,?,?,?)";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, company.getId());
			statement.setString(2, company.getCompName());
			statement.setString(3, company.getPassword());
			statement.setString(4, company.getEmail());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("Create Company Failed! A company with ID '" + company.getId() + "' already exist!",
					e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public void removeCompany(Company company) throws DAOException {
		String sql = "DELETE FROM Company WHERE ID= ?";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, company.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("Delete Company failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public void updateCompany(Company company) throws DAOException {
		String sql = "UPDATE Company set PASSWORD=? ,EMAIL=? WHERE ID= ?";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getPassword());
			statement.setString(2, company.getEmail());
			statement.setLong(3, company.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("Update Company failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public Company getCompany(String name) throws DAOException {
		String sql = "SELECT * FROM Company  WHERE COMP_NAME= ?";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet set = statement.executeQuery();
			if (set.next()) {
				Company comp = new Company();
				comp.setId(set.getLong(1));
				comp.setCompName(name);
				comp.setPassword(set.getString(3));
				comp.setEmail(set.getString(4));
				set.close();
				return comp;
			} else {
				set.close();
				throw new DAOException("Company with NAME " + name + " does not exist");
			}
		} catch (SQLException e) {
			throw new DAOException("get Company failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public Company getCompany(Long id) throws DAOException {
		String sql = "SELECT * FROM Company  WHERE ID= ?";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet set = statement.executeQuery();
			if (set.next()) {
				Company comp = new Company();
				comp.setId(id);
				comp.setCompName(set.getString(2));
				comp.setPassword(set.getString(3));
				comp.setEmail(set.getString(4));
				set.close();
				return comp;
			} else {
				set.close();
				throw new DAOException("Company with ID " + id + " does not exist");
			}
		} catch (SQLException e) {
			throw new DAOException("get Company failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public Collection<Company> getAllCompanies() throws DAOException {
		String sql = "SELECT * FROM Company";
		Connection connection = pool.getConnection();
		Collection<Company> companies = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				Company comp = new Company(set.getLong(1), set.getString(2), set.getString(3), set.getString(4));
				companies.add(comp);
			}
			set.close();
			return companies;

		} catch (SQLException e) {
			throw new DAOException("Get All Companies failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public Collection<Coupon> getCoupons(Company company) throws DAOException {
		String sql = "SELECT COUP_ID FROM Company_Coupon WHERE COMP_ID= ? ";
		Connection connection = pool.getConnection();
		Collection<Coupon> coupons = new ArrayList<>();
		CouponDBDAO couponsDBDAO = new CouponDBDAO();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, company.getId());
			// get a ResultSet of all the coupons id related to the given
			// company
			ResultSet couponsIdSet = statement.executeQuery();
			while (couponsIdSet.next()) {
				long tempCouponId = couponsIdSet.getLong(1);
				try {
					// get the coupon object by id and add it to the collection
					coupons.add(couponsDBDAO.getCoupon(tempCouponId));
				} catch (DAOException e) {
					couponsIdSet.close();
					throw new DAOException("Company " + company.getId() + " has Coupon " + tempCouponId
							+ " that doesn't exist any more!", e);
				}
			}
			couponsIdSet.close();
			return coupons;

		} catch (SQLException e) {
			throw new DAOException("failed to Read Coupons for Company with ID " + company.getId(), e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public boolean login(String name, String password) throws DAOException {
		String sql = "SELECT * from Company Where COMP_NAME= ? AND PASSWORD= ? ";
		Connection connection = pool.getConnection();
		try {
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
