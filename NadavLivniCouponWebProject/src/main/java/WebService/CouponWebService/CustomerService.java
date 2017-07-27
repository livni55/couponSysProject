package WebService.CouponWebService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import ObjectBeans.WebCoupon;
import b.JavaBeans.Coupon;
import b.JavaBeans.CouponType;
import c.DAO.DAOException;
import e.Facades.CustomerFacade;

@Path("customerservice")
public class CustomerService {

	@Context
	HttpServletRequest request;

	@Context
	private HttpServletResponse response;

	/**
	 * ctrate a logger
	 */
	public static final Logger logger = Logger
			.getLogger("MyLogger" + CustomerService.class.getName() + "\n*********************");
	boolean append = true;
	{
		ConsoleHandler cHandler = new ConsoleHandler();
		cHandler.setLevel(Level.INFO);
		logger.addHandler(cHandler);
		logger.setLevel(Level.INFO);
		FileHandler fHandler;
		try {
			fHandler = new FileHandler("myLogFile", append);
			fHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fHandler);
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}
	}
	// ***********************************************************

	/**
	 * private CustomerFacade getCustomerFacade(), method serving all other methods
	 * in this class for obtaining the CustomerFacade from the session.
	 * 
	 * @throws LoginException.
	 * @return AdminFacade, unless there is no such facade on the session.
	 * @throws LoginException
	 */
	public CustomerFacade getCustomerFacade() throws LoginException {
		CustomerFacade customerFacade = (CustomerFacade) request.getSession().getAttribute("couponClientFacade");
		if (customerFacade == null) {
			logger.log(Level.INFO, "Checking for the CustomerFacade on the session in CustomerService");
			throw new LoginException("You are attempting to connect without authorization!");
		}
		logger.log(Level.INFO, "exiting the getCustomerFacade method");
		return customerFacade;
	}

	// ***************************************
	/**
	 * Method handling HTTP POST requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 * @throws LoginException
	 * @throws DAOException
	 */
	@POST
	@Path("purchasecoupon/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void purchaseCoupon(@PathParam("id") long id) throws LoginException {
		logger.info("Entering To *** purchaseCoupon() *** Method");
		Coupon coupon = new Coupon();
		coupon.setId(id);
		try {
			getCustomerFacade().purchaseCoupon(coupon);
			logger.info("purchaseCoupon() Execute Seccesfully");
			System.out.println("CustomerService purchaseCoupon ");
		} catch (DAOException e) {
			logger.severe("There Is A Problem With purchaseCoupon() Execute" + e.getMessage());
			e.printStackTrace();
		}
		logger.info("Exiting to purchaseCoupon() Method");
	}

	// ***************************************
	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 * @throws LoginException
	 * @throws DAOException
	 */
	@GET
	@Path("getallpurchasedcoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<WebCoupon> getAllPurchasedCoupons() throws LoginException {
		logger.info("Entering To *** getAllPurchasedCoupons() *** Method");

		Collection<WebCoupon> webCoupons = new ArrayList<>();
		try {
			Collection<Coupon> coupons = getCustomerFacade().getAllPurchases();
			logger.info("getAllPurchasedCoupons() Execute Seccesfully");
			for (Coupon coupon : coupons) {
				webCoupons.add(new WebCoupon(coupon));
			}
			return webCoupons;
		} catch (DAOException e) {
			logger.severe("There Is A Problem With getAllPurchasedCoupons() Execute" + e.getMessage());
			e.printStackTrace();
		}
		logger.info("Exiting to getAllPurchasedCoupons() Method");
		return null;
	}

	// ***************************************
	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 * @throws LoginException
	 * @throws DAOException
	 */
	@GET
	@Path("getallpurchasedcouponsbytype/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<WebCoupon> getAllPurchasedCouponsByType(@PathParam("type") CouponType type)
			throws LoginException {
		logger.info("Entering To *** getAllPurchasedCouponsByType() *** Method");
		Collection<WebCoupon> webCoupons = new ArrayList<>();
		try {
			Collection<Coupon> coupons = getCustomerFacade().getAllPurchasesByType(type);
			for (Coupon coupon : coupons) {
				webCoupons.add(new WebCoupon(coupon));
			}
			logger.info("getAllPurchasedCouponsByType() Execute Seccesfully");
			return webCoupons;
		} catch (DAOException e) {
			logger.severe("There Is A Problem With getAllPurchasedCouponsByType() Execute" + e.getMessage());
			e.printStackTrace();
		}
		logger.info("Exiting to getAllPurchasedCouponsByType() Method");
		return webCoupons;
	}

	// ***************************************
	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 * @throws LoginException
	 * @throws DAOException
	 */
	@GET
	@Path("getallpurchasedcouponbyprice/{maxprice}")
	@Produces(MediaType.APPLICATION_JSON)
	// @Consumes(MediaType.APPLICATION_JSON)
	public Collection<WebCoupon> getAllPurchasedCouponByPrice(@PathParam("maxprice") double maxPrice)
			throws LoginException {
		logger.info("Entering To *** getAllPurchasedCouponByPrice() *** Method");
		Collection<WebCoupon> webCoupons = new ArrayList<>();
		try {
			Collection<Coupon> coupons = getCustomerFacade().getAllPurchasesByMaxPrice(maxPrice);
			for (Coupon coupon : coupons) {
				webCoupons.add(new WebCoupon(coupon));
			}
			logger.info("getAllPurchasedCouponByPrice() Execute Seccesfully");
			return webCoupons;
		} catch (DAOException e) {
			logger.severe("There Is A Problem With getAllPurchasedCouponByPrice() Execute" + e.getMessage());
			e.printStackTrace();
		}
		logger.info("Exiting to getAllPurchasedCouponByPrice() Method");
		return webCoupons;
	}

}
