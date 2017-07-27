package c.DAO;

import java.util.Collection;

import b.JavaBeans.Coupon;
import b.JavaBeans.Customer;

/**
 * Interface that allows the application to perform all essential methods that
 * involve communication with the database table <b>Customer</b>. The
 * communication channel for this interface is the JavaBean Customer.
 */
public interface CustomerDAO {
	/**
	 * Add Customer to database.
	 * 
	 * @param customer
	 *            Customer object to add to database
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void createCustomer(Customer customer) throws DAOException;

	/**
	 * Remove customer from the customer table of the database, if customer with
	 * the given ID Exists.
	 * 
	 * @param customer
	 *            The customer to remove.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void removeCustomer(Customer customer) throws DAOException;

	/**
	 * Change information of specific Customer. The coupons are not
	 * changed/added/removed by this method.
	 * 
	 * @param customer
	 *            The customer.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void updateCustomer(Customer customer) throws DAOException;

	/**
	 * Get the customer with the given ID from database.
	 * 
	 * @param id
	 *            The requested customer's ID.
	 * @return The customer from database with the given ID.
	 * @throws DAOException
	 *             When customer with the given ID haven't been found, or
	 *             connection with the database failed.
	 */
	public Customer getCustomer(Long id) throws DAOException;

	/**
	 * overloading for usage in the Facade Get the customer with the given NAME
	 * from database.
	 * 
	 * @param name
	 *            The requested customer's NAME.
	 * @return The customer from database with the given NAME.
	 * @throws DAOException
	 *             When customer with the given NAME haven't been found, or
	 *             connection with the database failed.
	 */
	public Customer getCustomer(String name) throws DAOException;

	/**
	 * Get all customers from database.
	 * 
	 * @return All customers from database.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Collection<Customer> getAllCustomer() throws DAOException;

	/**
	 * Get all coupons bought by the given customer.
	 * 
	 * @param customer
	 *            The customer for which to search coupons for.
	 * @return All coupons bought by the given customer.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Collection<Coupon> getCoupons(Customer customer) throws DAOException;

	/**
	 * Verifies a customer user credentials.
	 * 
	 * @param name
	 *            The customer name.
	 * @param password
	 *            The customer password.
	 * @return True - if verification resulted true. <br>
	 *         False - if verification resulted false.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public boolean login(String name, String password) throws DAOException;

}
