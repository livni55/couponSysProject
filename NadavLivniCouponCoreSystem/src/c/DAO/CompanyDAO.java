package c.DAO;

import java.util.Collection;

import b.JavaBeans.Company;
import b.JavaBeans.Coupon;

/**
 * Interface that allows the application to perform all essential methods that
 * involve communication with the database table <b>Company</b>. The
 * communication channel for this interface is the JavaBean Company
 */
public interface CompanyDAO {
	/**
	 * Add Company to DB
	 * 
	 * @param company
	 *            Company object to add to database
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void createCompany(Company company) throws DAOException;

	/**
	 * Remove company from company table of database, if company with the given
	 * ID exists.
	 * 
	 * @param company
	 *            The company to remove.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void removeCompany(Company company) throws DAOException;

	/**
	 * Change information of specific Company.
	 * 
	 * @param company
	 *            The company.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void updateCompany(Company company) throws DAOException;

	/**
	 * Get the company with the given ID from database.
	 * 
	 * @param id
	 *            The requested company's ID.
	 * @return The company from database with the given ID.
	 * @throws DAOException
	 *             When company with the given ID haven't been found, or
	 *             connection with the database failed.
	 */
	public Company getCompany(Long id) throws DAOException;

	/**
	 * Overloading the get Method for the login in the FACAED Get the company
	 * with the given NAME from database.
	 * 
	 * @param name
	 *            The requested company's NAME.
	 * @return The company from database with the given ID.
	 * @throws DAOException
	 *             When company with the given NAME haven't been found, or
	 *             connection with the database failed.
	 */
	public Company getCompany(String name) throws DAOException;

	/**
	 * Get all companies from database.
	 * 
	 * @return All companies from database.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Collection<Company> getAllCompanies() throws DAOException;

	/**
	 * Get all coupons associated with the given company.
	 * 
	 * @param company
	 *            The company for which to search coupons for.
	 * @return All coupons associated with the given company.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Collection<Coupon> getCoupons(Company company) throws DAOException;

	/**
	 * Get all coupons of the given company, matching a specific coupon type.
	 * 
	 * @param type
	 *            The coupon type to filter coupons by, must be from the Enum
	 *            CouponType.
	 * @param company
	 *            The company for which to search coupons for.
	 * @return All coupons associated with the given company and matching the
	 *         type.
	 * @throws DAOException
	 *             When connection failed.
	 */
	// public Collection<Coupon> getCouponsByType(CouponType type, Company
	// company) throws DAOException;

	/**
	 * Verifies a company user credentials.
	 * 
	 * @param name
	 *            The company name.
	 * @param password
	 *            The company password.
	 * @return True - if verification resulted true. <br>
	 *         False - if verification resulted false.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public boolean login(String name, String password) throws DAOException;

}
