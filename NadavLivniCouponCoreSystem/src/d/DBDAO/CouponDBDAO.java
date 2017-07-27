package d.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import a.ConnectionPool.ConnectionPool;
import b.JavaBeans.Coupon;
import b.JavaBeans.CouponType;
import c.DAO.CouponDAO;
import c.DAO.DAOException;

/**
 * <p>
 * implementation of CouponDAO which enable to contact with the <b>CouponDB</b>
 * database easily and safely.
 * </p>
 * All connections to the database are given by the ConnectionPool, and then
 * returned to it. No opening or closing of connections are made here.
 */
public class CouponDBDAO implements CouponDAO {
	private ConnectionPool pool = ConnectionPool.getInstance();

	public CouponDBDAO() {
	}

	@Override
	public void createCoupon(Coupon coupon) throws DAOException {
		Connection connection = pool.getConnection();
		try {
			String sql = "INSERT INTO Coupon VALUES(?,?,?,?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, coupon.getId());
			statement.setString(2, coupon.getTitle());
			statement.setDate(3, coupon.getStartDate());
			statement.setDate(4, coupon.getEndDate());
			statement.setInt(5, coupon.getAmount());
			statement.setString(6, coupon.getCouponType().toString());
			statement.setString(7, coupon.getMessage());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("Create Coupon Failed! A coupon with ID:" + coupon.getId()
					+ " or with the same NAME: '" + coupon.getTitle() + "' already exist!", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public void removeCoupon(Coupon coupon) throws DAOException {
		Connection connection = pool.getConnection();
		String sql = "DELETE FROM Coupon WHERE ID= ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, coupon.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("Delete Coupon failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws DAOException {
		Connection connection = pool.getConnection();
		String sql = "UPDATE Coupon SET START_DATE= ?, END_DATE= ?,AMOUNT= ?,TYPE= ?,MESSAGE= ?,PRICE= ?,IMAGE= ? WHERE ID= ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDate(1, coupon.getStartDate());
			statement.setDate(2, coupon.getEndDate());
			statement.setInt(3, coupon.getAmount());
			statement.setString(4, coupon.getCouponType().toString());
			statement.setString(5, coupon.getMessage());
			statement.setDouble(6, coupon.getPrice());
			statement.setString(7, coupon.getImage());
			statement.setLong(8, coupon.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("Update Coupon failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public Coupon getCoupon(Long id) throws DAOException {
		Connection connection = pool.getConnection();
		String sql = "SELECT * FROM Coupon  WHERE ID= ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(id);
				coupon.setTitle(resultSet.getString("TITLE"));
				coupon.setStartDate(resultSet.getDate("START_DATE"));
				coupon.setEndDate(resultSet.getDate("END_DATE"));
				coupon.setAmount(resultSet.getInt("AMOUNT"));
				coupon.setCouponType(CouponType.valueOf(resultSet.getString("TYPE")));
				coupon.setMessage(resultSet.getString("MESSAGE"));
				coupon.setPrice(resultSet.getDouble("PRICE"));
				coupon.setImage(resultSet.getString("IMAGE"));
				resultSet.close();
				return coupon;
			} else {
				resultSet.close();
				throw new DAOException("Coupon with ID " + id + " does not exist");
			}
		} catch (SQLException e) {
			throw new DAOException("Get Coupon failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public Collection<Coupon> getAllCoupon() throws DAOException {
		Connection connection = pool.getConnection();
		String sql = "SELECT * FROM Coupon";
		try {
			Collection<Coupon> coupons = new ArrayList<>();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				Coupon coupon = new Coupon(set.getLong(1), set.getString("TITLE"), set.getDate("START_DATE"),
						set.getDate("END_DATE"), set.getInt("AMOUNT"), CouponType.valueOf(set.getString("TYPE")),
						set.getString("MESSAGE"), set.getDouble("PRICE"), set.getString("IMAGE"));
				coupons.add(coupon);
			}
			set.close();
			return coupons;

		} catch (SQLException e) {
			throw new DAOException("Get Coupons failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public Collection<Coupon> getCouponByType(CouponType type) throws DAOException {
		Connection connection = pool.getConnection();
		String sql = "SELECT * FROM Coupon WHERE TYPE= ?";
		Collection<Coupon> coupons = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, type.toString());
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				Coupon coupon = new Coupon(set.getLong(1), set.getString("TITLE"), set.getDate("START_DATE"),
						set.getDate("END_DATE"), set.getInt("AMOUNT"), type, set.getString("MESSAGE"),
						set.getDouble("PRICE"), set.getString("IMAGE"));
				coupons.add(coupon);
			}
			set.close();
			return coupons;

		} catch (SQLException e) {
			throw new DAOException("Read Coupons failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

}
