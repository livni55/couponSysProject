package e.Facades;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import b.JavaBeans.Company;
import b.JavaBeans.Coupon;
import b.JavaBeans.CouponType;
import c.DAO.DAOException;
import d.DBDAO.CompanyDBDAO;
import d.DBDAO.CouponDBDAO;
import d.DBDAO.JoinedTablesDBDAO;

/**
 * A Facade which contains all methods that can be executed by the
 * <b>COMPANY</b> user. Any instance represents a single company, that can
 * execute the methods by this object, whose given by the login method when
 * correct parameters are given to it.<br>
 * This class implements CouponClientFacade. The Company Facade is part of the
 * Facades layer which enables the users to handle their information.
 */
public class CompanyFacade implements CouponClientFacade {
	private CouponDBDAO couponDBDAO = new CouponDBDAO();
	private static CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	private JoinedTablesDBDAO joinedTablesDBDAO = new JoinedTablesDBDAO();
	private Company company;

	/**
	 * Private CTOR in order to prevent creation of instance with no object related.
	 * 
	 * @param newcompany
	 *            the object with the company details
	 */
	private CompanyFacade(Company newcompany) {
		this.company = newcompany;
	}

	/**
	 * Only by login with correct parameters you can get a CompanyFacade object.
	 * 
	 * @param name
	 *            The name of the company.
	 * @param Password
	 *            The password of the company.
	 * @return Company with these parameters if exists.
	 * @throws DAOException
	 *             When company with the given parameters does not exists.
	 */
	public static CompanyFacade login(String name, String Password) throws DAOException {
		if (companyDBDAO.login(name, Password)) {
			Company Newcompany = companyDBDAO.getCompany(name);
			Newcompany.setCoupons(companyDBDAO.getCoupons(Newcompany));
			CompanyFacade NewCompFacade = new CompanyFacade(Newcompany);
			return NewCompFacade;
		} else {
			throw new DAOException("Company login failed! Incorrect username or password!");
		}

	}

	/**
	 * Create coupon (id and title must be NOT NULL and UNIQUE).
	 * 
	 * @param coupon
	 *            The coupon object.
	 * @throws DAOException
	 *             When a coupon with the same id or name already exist, or creation
	 *             failed.
	 */
	public void creatCoupon(Coupon coupon) throws DAOException {
		if (coupon.getTitle() == null) {
			throw new DAOException("Coupon creation failed. Coupon must have title!");
		}
		this.couponDBDAO.createCoupon(coupon);
		this.joinedTablesDBDAO.createCompany_Coupon(this.company, coupon);
	}

	/**
	 * Delete coupon from all tables of DB.
	 * 
	 * @param coupon
	 *            The coupon to delete .
	 * @throws DAOException
	 *             When coupon with the given ID doesn't exist on database, or
	 *             connection failed.
	 */
	public void removeCoupon(Coupon coupon) throws DAOException {
		couponDBDAO.removeCoupon(coupon);
		joinedTablesDBDAO.RemoveCoupon(coupon);
	}

	/**
	 * Update coupon attributes on database, according to given ID.
	 * 
	 * @param id
	 *            The ID of the coupon to update.
	 * @param endDate
	 *            The expiration date to update the coupon with.
	 * @param price
	 *            The price to update the coupon with.
	 * @throws DAOException
	 *             When coupon with the given ID doesn't exist on database, or
	 *             connection failed.
	 */
	public void updateCoupon(Coupon coupon) throws DAOException {
		couponDBDAO.updateCoupon(coupon);
	}

	/**
	 * Get the current Facade's company object for details
	 * 
	 * @return the current company
	 */
	public Company getCompany() {
		return this.company;
	}

	/**
	 * Get all coupons of the current company.
	 * 
	 * @return A collection of coupons owned by current company.
	 * @throws DAOException
	 *             When connection failed.
	 */
	public Collection<Coupon> getAllCoupons() throws DAOException {
		return companyDBDAO.getCoupons(this.company);
	}

	/**
	 * Get coupon instance with attributes from database according to coupon's ID.
	 * 
	 * @param id
	 *            The ID of the requested coupon.
	 * @return The coupon instance from the database.
	 * @throws DAOException
	 *             When get coupon process failed.
	 */
	public Coupon getCoupon(Long id) throws DAOException {
		return couponDBDAO.getCoupon(id);
	}

	/**
	 * Get all coupons of the current company, matching a specific coupon type.
	 * 
	 * @param type
	 *            The coupon type to filter coupons by, must be from the Enum
	 *            CouponType.
	 * @return A collection of coupons owned by current company, matching the given
	 *         coupon type.
	 * @throws DAOException
	 *             When connection failed.
	 */
	public Collection<Coupon> getCouponsByType(CouponType type) throws DAOException {
		Collection<Coupon> tempcollection = companyDBDAO.getCoupons(this.company);
		Collection<Coupon> couponsByType = new ArrayList<>();
		for (Coupon coupon : tempcollection) {
			if (coupon.getCouponType() == type) {
				couponsByType.add(coupon);
			}
		}
		return couponsByType;
	}

	/**
	 * Get all coupons of the current company, which their price is <b> under or
	 * equals </b> a specific price.
	 * 
	 * @param maxPrice
	 *            The maximum price of which to filter coupon by.
	 * @return A collection of coupons owned by current company, with price which
	 *         doesn't exceeds the given maximum price.
	 * @throws DAOException
	 *             When connection failed.
	 */
	public Collection<Coupon> getCouponsWithMaxPrice(double maxPrice) throws DAOException {
		Collection<Coupon> tempcollection = companyDBDAO.getCoupons(this.company);
		Collection<Coupon> couponsByMaxPrice = new ArrayList<>();
		for (Coupon coupon : tempcollection) {
			if (coupon.getPrice() <= maxPrice) {
				couponsByMaxPrice.add(coupon);
			}
		}
		return couponsByMaxPrice;
	}

	/**
	 * Get all coupons of the current company, which their expiration date is
	 * <b>before or exactly</b> a given date.
	 * 
	 * @param maxEndDate
	 *            The latest date of which to filter coupon by.
	 * @return A collection of coupons owned by current company, with expiration
	 *         date which is before or exactly the given date.
	 * @throws DAOException
	 *             When connection failed.
	 */
	public Collection<Coupon> getCouponsWithMaxEndDate(Date maxEndDate) throws DAOException {
		Collection<Coupon> tempcollection = companyDBDAO.getCoupons(this.company);
		Collection<Coupon> couponsByMaxEndDate = new ArrayList<>();
		for (Coupon coupon : tempcollection) {
			if (coupon.getEndDate().compareTo(maxEndDate) <= 0) {
				couponsByMaxEndDate.add(coupon);
			}
		}
		return couponsByMaxEndDate;
	}
}
