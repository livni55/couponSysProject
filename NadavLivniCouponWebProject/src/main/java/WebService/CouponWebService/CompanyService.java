package WebService.CouponWebService;

import java.io.IOException;
import java.sql.Date;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import ObjectBeans.WebCompany;
import ObjectBeans.WebCoupon;
import b.JavaBeans.Coupon;
import b.JavaBeans.CouponType;
import c.DAO.DAOException;
import e.Facades.CompanyFacade;

/**
 * Root resource (exposed at "myresource" path)
 * 
 */
@Path("companyservice")
public class CompanyService {

	@Context
	HttpServletRequest request;

	@Context
	private HttpServletResponse response;
	/**
	 * ctrate a logger
	 */
	public static final Logger logger = Logger
			.getLogger("MyLogger" + CompanyService.class.getName() + "\n*********************");
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

	// ***************************************************************************************

	public CompanyFacade getCompanyFacade() throws LoginException {
		CompanyFacade companyFacade = (CompanyFacade) request.getSession().getAttribute("couponClientFacade");
		if (companyFacade == null) {
			logger.log(Level.INFO, "Checking for the CompanyFacade on the session in AdminService");
			throw new LoginException("You are attempting to connect without authorization!");
		}
		logger.log(Level.INFO, "Exiting the getCompanyFacade method in CompanyService without Exceptions");
		return companyFacade;
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
	@POST
	@Path("createcoupon")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCoupon(WebCoupon webCoupon) throws LoginException {
		logger.info("Entering To *** createCoupon() *** Method");

		Coupon coupon = new Coupon(webCoupon.getId(), webCoupon.getTitle(), webCoupon.getStartDate(),
				webCoupon.getEndDate(), webCoupon.getAmount(), webCoupon.getType(), webCoupon.getMessage(),
				webCoupon.getPrice(), webCoupon.getImage());
		try {
			getCompanyFacade().creatCoupon(coupon);
			logger.info("createCoupon() Execute Seccesfully");
		} catch (DAOException e) {
			logger.severe("There Is A Problem With createCoupon() Execute" + e.getMessage());
			e.getMessage();
		}
		logger.info("Exiting to createCoupon() Method");

	}

	// ***************************************
	/**
	 * Method handling HTTP DELETE requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 * @throws LoginException
	 * @throws DAOException
	 */
	@DELETE
	@Path("deletecoupon/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Coupon> deleteCoupon(@PathParam("id") long id) throws LoginException {
		logger.info("Entering To *** deleteCoupon() *** Method");
		Collection<Coupon> coupons = new ArrayList<>();
		Coupon coupon = new Coupon();
		coupon.setId(id);
		try {
			getCompanyFacade().removeCoupon(coupon);
			logger.info("deleteCoupon() Execute Seccesfully");
			coupons = getCompanyFacade().getAllCoupons();
			logger.info("getAllCoupons() In deleteCoupon() Method Execute Seccesfully");

			System.out.println("CompanyService deleteCoupon ");
		} catch (DAOException e) {
			logger.severe("There Is A Problem With deleteCoupon() Execute" + e.getMessage());
			e.getMessage();
		}
		logger.info("Exiting to deleteCoupon() Method");
		return coupons;
	}

	// ***************************************
	/**
	 * Method handling HTTP UPDATE requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 * @throws LoginException
	 * @throws DAOException
	 */
	@PUT
	@Path("updatecoupon/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCoupon(@PathParam("id") long id, WebCoupon webCoupon) throws LoginException {
		logger.info("Entering To *** updateCoupon() *** Method");
		try {
			getCompanyFacade().updateCoupon(webCoupon.convertToCoupon());
			logger.info("updateCoupon() Execute Seccesfully");
		} catch (DAOException e) {
			logger.severe("There Is A Problem With updateCoupon() Execute" + e.getMessage());
			e.getMessage();
		}
		logger.info("Exiting to updateCoupon() Method");
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
	@Path("getcoupon/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public WebCoupon getCoupon(@PathParam("id") long id) throws LoginException {
		logger.info("Entering To *** getCoupon() *** Method");
		try {
			logger.info("getCoupon() Execute Seccesfully");
			return new WebCoupon(getCompanyFacade().getCoupon(id));
		} catch (DAOException e) {
			logger.severe("There Is A Problem With getCoupon() Execute" + e.getMessage());
			e.getMessage();
		}
		logger.info("Exiting to getCoupon() Method");
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
	@Path("getallcoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<WebCoupon> getAllCoupons() throws LoginException {
		logger.info("Entering To *** getAllCoupons() *** Method");
		Collection<WebCoupon> webCoupons = new ArrayList<>();
		try {
			Collection<Coupon> coupons = getCompanyFacade().getAllCoupons();
			for (Coupon coupon : coupons) {
				webCoupons.add(new WebCoupon(coupon));
			}
			logger.info("getAllCoupons() Execute Seccesfully");
			return webCoupons;
		} catch (DAOException e) {
			logger.severe("There Is A Problem With getAllCoupons() Execute" + e.getMessage());
			e.getMessage();
		}
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
	@Path("getcouponsbytype/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<WebCoupon> getCouponsByType(@PathParam("type") CouponType type) throws LoginException {
		logger.info("Entering To *** getCouponsByType() *** Method");
		Collection<WebCoupon> webCoupons = new ArrayList<>();
		try {
			Collection<Coupon> coupons = getCompanyFacade().getCouponsByType(type);
			for (Coupon coupon : coupons) {
				webCoupons.add(new WebCoupon(coupon));
			}
			logger.info("getCouponsByType() Execute Seccesfully");
			return webCoupons;
		} catch (DAOException e) {
			logger.severe("There Is A Problem With getCouponsByType() Execute" + e.getMessage());
			e.getMessage();
		}
		logger.info("Exiting to getCouponsByType() Method");
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
	@Path("currentcompany")
	@Produces(MediaType.APPLICATION_JSON)
	public WebCompany getCurrentCompany() throws LoginException {
		logger.info("Entering To *** getCurrentCompany() *** Method");
		System.out.println("CompanyService getCurrentCompany ");
		return new WebCompany(getCompanyFacade().getCompany());
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
	@Path("getcouponsbymaxprice/{maxprice}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<WebCoupon> getCouponsByMaxPrice(@PathParam("maxprice") double maxPrice) throws LoginException {
		logger.info("Entering To *** getCouponsByMaxPrice() *** Method");
		Collection<WebCoupon> webCoupons = new ArrayList<>();
		try {
			Collection<Coupon> coupons = getCompanyFacade().getCouponsWithMaxPrice(maxPrice);
			for (Coupon coupon : coupons) {
				webCoupons.add(new WebCoupon(coupon));
			}
			logger.info("getCouponsByMaxPrice() Execute Seccesfully");
			return webCoupons;
		} catch (DAOException e) {
			logger.severe("There Is A Problem With getCouponsByMaxPrice() Execute" + e.getMessage());
			e.getMessage();
		}
		logger.info("Exiting to getCouponsByMaxPrice() Method");
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
	@Path("getcouponsbyenddate/{date}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<WebCoupon> getCouponsByEndDate(@PathParam("date") String date) throws LoginException {
		logger.info("Entering To *** getCouponsByEndDate() *** Method");
		Date maxDate = new Date(Long.parseLong(date));
		Collection<WebCoupon> webCoupons = new ArrayList<>();
		try {
			Collection<Coupon> coupons = getCompanyFacade().getCouponsWithMaxEndDate((maxDate));
			for (Coupon coupon : coupons) {
				webCoupons.add(new WebCoupon(coupon));
			}
			logger.info("getCouponsByEndDate() Execute Seccesfully");
			return webCoupons;
		} catch (DAOException e) {
			logger.severe("There Is A Problem With getCouponsByEndDate() Execute" + e.getMessage());
			e.printStackTrace();
		}
		logger.info("Exiting to getCouponsByEndDate() Method");
		return webCoupons;
	}

}
