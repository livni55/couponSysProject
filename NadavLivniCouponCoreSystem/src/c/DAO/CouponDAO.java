package c.DAO;

import java.util.Collection;

import b.JavaBeans.Coupon;
import b.JavaBeans.CouponType;

/**
 * Interface that allows the application to perform all essential methods that
 * involve communication with the database table <b>Coupon</b>. The
 * communication channel for this interface is the JavaBean Coupon
 */
public interface CouponDAO {
	/**
	 * Add Coupon to database.
	 * 
	 * @param coupon
	 *            Coupon object to add to database
	 * @throws DAOException
	 *             When database contains a coupon with the same title, or
	 *             connection with the database failed.
	 */
	public void createCoupon(Coupon coupon) throws DAOException;

	/**
	 * Remove coupon from coupon table of the database, if coupon with the given
	 * ID Exists.
	 * 
	 * @param coupon
	 *            The coupon to remove.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void removeCoupon(Coupon coupon) throws DAOException;

	/**
	 * Change information of specific Coupon.
	 * 
	 * @param coupon
	 *            The coupon.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void updateCoupon(Coupon coupon) throws DAOException;

	/**
	 * Get the coupon with the given ID from database.
	 * 
	 * @param id
	 *            The requested coupon's ID.
	 * @return The coupon from database with the given ID.
	 * @throws DAOException
	 *             When coupon with the given ID haven't been found, or
	 *             connection with the database failed.
	 */
	public Coupon getCoupon(Long id) throws DAOException;

	/**
	 * Get all coupons from database.
	 * 
	 * @return All coupons from database.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Collection<Coupon> getAllCoupon() throws DAOException;

	/**
	 * Get all coupons from database matching a specific Coupon Type.
	 * 
	 * @param type
	 *            The coupon type.
	 * @return All coupons from database matching a specific Coupon Type.
	 * @throws DAOException
	 *             When connection with the database failed.
	 * @see CouponType
	 */
	public Collection<Coupon> getCouponByType(CouponType type) throws DAOException;

}
