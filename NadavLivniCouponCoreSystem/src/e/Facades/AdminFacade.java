package e.Facades;

import java.util.Collection;

import b.JavaBeans.Company;
import b.JavaBeans.Coupon;
import b.JavaBeans.Customer;
import c.DAO.DAOException;
import d.DBDAO.CompanyDBDAO;
import d.DBDAO.CouponDBDAO;
import d.DBDAO.CustomerDBDAO;
import d.DBDAO.JoinedTablesDBDAO;

/**
 * A Facade which contains all methods that can be executed by the <b>ADMIN</b>.
 * Any instance represents a single admin, that can execute the methods by this
 * object, whose given by the login static method when correct parameters are
 * given to it.<br>
 * This class implements CouponClientFacade. The Admin Facade is part of the
 * Facades layer which enables the users to handle their information.
 */
public class AdminFacade implements CouponClientFacade {
	private CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	private CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	private CouponDBDAO couponDBDAO = new CouponDBDAO();
	private JoinedTablesDBDAO joinedTablesDBDAO = new JoinedTablesDBDAO();

	private AdminFacade() {
	}

	/**
	 * 
	 * @param name
	 *            The username "admin"
	 * @param password
	 *            The password "1234"
	 * @return AdminFacade if login parameters are correct
	 * @throws DAOException
	 *             if one of the parameters are wrong
	 */
	public static AdminFacade login(String name, String password) throws DAOException {
		if (name.equals("admin") && password.equals("1234")) {
			return new AdminFacade();
		} else {
			throw new DAOException("Admin login failed! Incorrect username or password!");
		}
	}

	/**
	 * creates a new company in the DB
	 * 
	 * @param company
	 *            The Java object that contains the values for the SQL
	 * @throws DAOException
	 *             If the company already exits
	 */
	public void creatCompany(Company company) throws DAOException {
		try {
			companyDBDAO.createCompany(company);
		} catch (DAOException e) {
			throw new DAOException("A company with the name '" + company.getCompName() + "' or with id: "
					+ company.getId() + " already exists!", e);
		}
	}

	/**
	 * Removes a company and all of it's coupons from the <b>Company,
	 * Company_Coupon</b> and <b>Coupon</b> Tables
	 * 
	 * @param company
	 *            The company the should be deleted
	 * @throws DAOException
	 *             If the action failed
	 */
	public void removeCompany(Company company) throws DAOException {
		Collection<Coupon> couponsToDelete = companyDBDAO.getCoupons(company);
		for (Coupon coupon : couponsToDelete) {
			joinedTablesDBDAO.RemoveCoupon(coupon);
			couponDBDAO.removeCoupon(coupon);
		}
		companyDBDAO.removeCompany(company);

	}

	/**
	 * Update the company's attributes in the database.
	 * 
	 * @param company
	 *            The company attributes that need to be changed.
	 * @throws DAOException
	 *             When company doesn't exist, or update company fails.
	 */
	public void updateCompany(Company company) throws DAOException {
		try {
			companyDBDAO.updateCompany(company);
		} catch (Exception e) {
			throw new DAOException("A company with the id: " + company.getId() + " doesn't exists");
		}
	}

	/**
	 * @return A collection of the companies stored in database.
	 * @throws DAOException
	 *             When getting all companies fails.
	 */
	public Collection<Company> getAllCompanies() throws DAOException {
		return companyDBDAO.getAllCompanies();
	}

	/**
	 * Get a company as a Java object from the DB.
	 * 
	 * @param id
	 *            The ID of the company that the user want to get
	 * @return The company from database with the given ID.
	 * @throws DAOException
	 *             When get company fails.
	 */
	public Company getCompany(Long id) throws DAOException {
		return companyDBDAO.getCompany(id);
	}

	/**
	 * Creates a customer in the DB
	 * 
	 * @param customer
	 *            The customer data.
	 * @throws DAOException
	 *             If the given customer name already taken, or Creation failed.
	 */
	public void createCustomer(Customer customer) throws DAOException {
		for (Customer tempCust : customerDBDAO.getAllCustomer()) {
			if (tempCust.getCustName().equals(customer.getCustName())) {
				throw new DAOException("A customer with the name '" + customer.getCustName() + "' already exists!");
			}
		}
		customerDBDAO.createCustomer(customer);
	}

	/**
	 * Remove the customer from DB.
	 * 
	 * @param customer
	 *            The customer to delete from database.
	 * @throws DAOException
	 *             When remove customer fails.
	 */
	public void removeCustomer(Customer customer) throws DAOException {
		customerDBDAO.removeCustomer(customer);
		joinedTablesDBDAO.RemoveCustomer(customer);

	}

	/**
	 * Update the customer's attributes in the database.
	 * 
	 * @param customer
	 *            The customer object that need to be changed.
	 * @throws DAOException
	 *             When customer doesn't exist, or update customer fails.
	 */
	public void updateCustomer(Customer customer) throws DAOException {
		try {
			getCustomer(customer.getId());
		} catch (Exception e) {
			throw new DAOException("details Upadte for the customer with id: " + customer.getId()
					+ " failed. Customer doesn't exist!");
		}
		customerDBDAO.updateCustomer(customer);

	}

	/**
	 * @return A collection of the customers stored in database.
	 * @throws DAOException
	 *             When getting all customers fails.
	 */
	public Collection<Customer> getAllCustomer() throws DAOException {
		return customerDBDAO.getAllCustomer();
	}

	/**
	 * Get a customer information from the database.
	 * 
	 * @param id
	 *            The ID of the customer to be given.
	 * @return The customer from database with the given ID.
	 * @throws DAOException
	 *             When read customer fails.
	 */
	public Customer getCustomer(Long id) throws DAOException {
		return customerDBDAO.getCustomer(id);
	}
}
