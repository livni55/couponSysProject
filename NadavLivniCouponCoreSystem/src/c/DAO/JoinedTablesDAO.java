package c.DAO;

import b.JavaBeans.Company;
import b.JavaBeans.Coupon;
import b.JavaBeans.Customer;

/**
 * <p>
 * Interface that allows the application to perform all essential methods that
 * involve communication with the database join tables.
 * </p>
 * The join tables are <b>Company_Coupon</b> and <b>Customer_Couponb</b>. The
 * communication channels for this interface are all the JavaBeans.
 */
public interface JoinedTablesDAO {

	/**
	 * Add new row for Company_Coupon table.
	 * 
	 * @param company
	 *            The company for the row.
	 * @param coupon
	 *            The coupon for the row.
	 * @throws DAOException
	 *             If company or coupon doesn't exist, or connection lost.
	 */
	public void createCompany_Coupon(Company company, Coupon coupon) throws DAOException;

	/**
	 * Add new row for Customer_Coupon table.
	 * 
	 * @param customer
	 *            The customer for the row.
	 * @param coupon
	 *            The coupon for the row.
	 * @throws DAOException
	 *             If customer or coupon doesn't exist, or connection lost.
	 */
	public void createCustomer_Coupon(Customer customer, Coupon coupon) throws DAOException;

	/**
	 * Remove coupon from both Company_Coupon and Customer_Coupon tables.
	 * 
	 * @param coupon
	 *            The coupon to remove.
	 * @throws DAOException
	 *             If coupon doesn't exist, or connection lost.
	 */
	public void RemoveCoupon(Coupon coupon) throws DAOException;

	/**
	 * Remove specific row in Customer_Coupon table of CouponDB database
	 * 
	 * @param customer
	 *            The customer with the ID to search for.
	 * @param coupon
	 *            The coupon with the ID to search for.
	 * @throws DAOException
	 *             If customer or coupon doesn't exist, or connection lost.
	 */
	public void RemoveCustomer_Coupon(Customer customer, Coupon coupon) throws DAOException;

	/**
	 * Remove customer and all of its coupon purchase history from
	 * Customer_Coupon table. This is done according to the database information
	 * and regardless to the coupon collection of the given customer object.
	 * 
	 * @param customer
	 *            The customer to remove.
	 * @throws DAOException
	 *             If customer doesn't exist, or connection lost.
	 */
	public void RemoveCustomer(Customer customer) throws DAOException;

	/**
	 * Check if a specific company has a specific coupon.
	 * 
	 * @param compID
	 *            The company ID.
	 * @param couponID
	 *            The coupon ID.
	 * @return True - if correct. <br>
	 *         False - if not.
	 * @throws DAOException
	 *             When connection with database failed.
	 */
	public boolean isThereCompanyCoupon(long compID, long couponID) throws DAOException;

	/**
	 * Check if a specific customer has a specific coupon.
	 * 
	 * @param custID
	 *            The customer ID.
	 * @param couponID
	 *            The coupon ID.
	 * @return True - if correct. <br>
	 *         False - if not.
	 * @throws DAOException
	 *             When connection with database failed.
	 */
	public boolean isThereCustomerCoupon(long custID, long couponID) throws DAOException;

}
