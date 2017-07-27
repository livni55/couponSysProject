package e.Facades;

import java.util.ArrayList;
import java.util.Collection;

import b.JavaBeans.Coupon;
import b.JavaBeans.CouponType;
import b.JavaBeans.Customer;
import c.DAO.DAOException;
import d.DBDAO.CouponDBDAO;
import d.DBDAO.CustomerDBDAO;
import d.DBDAO.JoinedTablesDBDAO;

/**
 * A Facade which contains all methods that can be executed by the
 * <b>CUSTOMER</b> user. Any instance represents a single CUSTOMER, that can
 * execute the methods by this object, whose given by the login method when
 * correct parameters are given to it.<br>
 * This class implements CouponClientFacade. The CUSTOMER Facade is part of the
 * Facades layer which enables the users to handle their information.
 */
public class CustomerFacade implements CouponClientFacade {
	private static CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	private CouponDBDAO couponDBDAO = new CouponDBDAO();
	private JoinedTablesDBDAO joinedTablesDBDAO = new JoinedTablesDBDAO();
	private Customer customer;

	/**
	 * Private CTOR in order to prevent creation of instance with no object related
	 * 
	 * @param customer
	 *            the object with the customer details
	 */
	private CustomerFacade(Customer customer) {
		this.customer = customer;
	}

	/**
	 * Only by login with correct parameters you can get a CustomerFacade object.
	 * 
	 * @param name
	 *            The name of the customer.
	 * @param Password
	 *            The password of the customer.
	 * @return customer with these parameters if exists.
	 * @throws DAOException
	 *             When customer with the given parameters does not exists.
	 */
	public static CustomerFacade login(String name, String Password) throws DAOException {
		if (customerDBDAO.login(name, Password)) {
			Customer NewCustomer = customerDBDAO.getCustomer(name);
			NewCustomer.setCoupons(customerDBDAO.getCoupons(NewCustomer));
			CustomerFacade NewCustomerFacade = new CustomerFacade(NewCustomer);
			return NewCustomerFacade;
		} else {
			throw new DAOException("Customer login failed! Incorrect username or password!");
		}
	}

	/**
	 * Perform a customer client purchase of coupon by changing the relevant data on
	 * database.
	 * 
	 * @param coupon
	 *            The coupon to purchase.
	 * @throws DAOException
	 *             When coupon doesn't exist, current customer already purchased
	 *             this coupon, coupon out of stock or connection failed.
	 */
	public void purchaseCoupon(Coupon coupon) throws DAOException {
		Coupon couponToBuy = couponDBDAO.getCoupon(coupon.getId());
		if (joinedTablesDBDAO.isThereCustomerCoupon(this.customer.getId(), coupon.getId())) {
			throw new DAOException("You already bought it");
		}
		if (couponToBuy.getAmount() == 0) {
			throw new DAOException("There are no coupons left");
		}

		coupon.setAmount(couponToBuy.getAmount() - 1);
		this.customer.AddCoupons(couponToBuy);
		joinedTablesDBDAO.createCustomer_Coupon(this.customer, couponToBuy);
		couponDBDAO.updateCoupon(couponToBuy);
	}

	/**
	 * Get all coupons bought by the current customer.
	 * 
	 * @return A collection of coupons which the current customer bought.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Collection<Coupon> getAllPurchases() throws DAOException {
		return customerDBDAO.getCoupons(this.customer);
	}

	/**
	 * Get all coupons bought by the current customer, matching a specific coupon
	 * type.
	 * 
	 * @param type
	 *            The coupon type to filter coupons by.
	 * @return A collection of coupons bought by the current customer, matching the
	 *         given coupon type.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Collection<Coupon> getAllPurchasesByType(CouponType type) throws DAOException {
		Collection<Coupon> tempcollection = customerDBDAO.getCoupons(this.customer);
		Collection<Coupon> couponsByType = new ArrayList<>();
		for (Coupon coupon : tempcollection) {
			if (coupon.getCouponType() == type) {
				couponsByType.add(coupon);
			}
		}
		return couponsByType;
	}

	/**
	 * Get all coupons bought by the current customer, which their price is <b>not
	 * more than</b> the given price.
	 * 
	 * @param maxPrice
	 *            The maximum price of which to filter coupon by.
	 * @return A collection of coupons bought by current customer, with price which
	 *         doesn't exceeds the given maximum price.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Collection<Coupon> getAllPurchasesByMaxPrice(double maxPrice) throws DAOException {
		Collection<Coupon> tempcollection = customerDBDAO.getCoupons(this.customer);
		Collection<Coupon> couponsByPrice = new ArrayList<>();
		for (Coupon coupon : tempcollection) {
			if (coupon.getPrice() <= maxPrice) {
				couponsByPrice.add(coupon);
			}
		}
		return couponsByPrice;
	}

	public Customer getCustomer() {
		return customer;
	}

}
