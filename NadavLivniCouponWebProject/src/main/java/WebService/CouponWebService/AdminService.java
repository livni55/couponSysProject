package WebService.CouponWebService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ObjectBeans.WebCompany;
import ObjectBeans.WebCustomer;
import b.JavaBeans.Company;
import b.JavaBeans.Customer;
import c.DAO.DAOException;
import e.Facades.AdminFacade;

/**
 * Root resource (exposed at "admin" path)
 */
@Path("adminservice")
public class AdminService {

	@Context
	HttpServletRequest request;

	@Context
	private HttpServletResponse response;

	private AdminFacade getAdminFacade() throws LoginException {

		AdminFacade adminFacade = (AdminFacade) request.getSession().getAttribute("couponClientFacade");
		if (adminFacade == null) {
			MyLogger.logger.log(Level.INFO, "Checking for the AdminFacade on the session in AdminService");
			throw new LoginException("You are attempting to connect without authorization!");
		}
		MyLogger.logger.log(Level.INFO, "Exiting the getAdminFacade method in AdminService");
		return adminFacade;
	}

	// ****************************************************************************************
	/**
	 * creates a new company in the database.
	 * 
	 * @throws DAOException
	 * @throws LoginException
	 * @param WebCompany
	 */
	@POST
	@Path("createcompany")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCompany(WebCompany webCompany) throws DAOException, LoginException {
		MyLogger.logger.info("Entering To *** createCompany() *** Method");
		try {
			getAdminFacade().creatCompany(webCompany.convertToCompany());
			MyLogger.logger.info("createCompany() Execute Seccesfully");
			return Response.ok().build();
		} catch (DAOException e) {
			MyLogger.logger.severe("There Is A Problem With createCompany() Execute" + e.getMessage());
			e.getMessage();
			return Response.status(400).entity(e.getMessage()).build();
		} finally {
			MyLogger.logger.info("Exiting to createCompany() Method");
		}
	}

	// ***************************************
	/**
	 * remove company from DB
	 * 
	 * @throws LoginException
	 * @throws DAOException
	 * @param companyId
	 */
	@DELETE
	@Path("deletecompany/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteCompany(@PathParam("id") long companyId) throws DAOException, LoginException {
		MyLogger.logger.info("Entering To *** deleteCompany() *** Method");
		WebCompany webCompany = new WebCompany();
		webCompany.setId(companyId);
		try {
			getAdminFacade().removeCompany(webCompany.convertToCompany());
			MyLogger.logger.info("deleteCompany() Execute Seccesfully");
		} catch (DAOException e) {
			MyLogger.logger.severe("There Is A Problem With deleteCompany() Execute: " + e.getMessage());
			e.getMessage();
		}
		MyLogger.logger.info("Exiting deleteCompany() Method");
	}

	// ***************************************
	/**
	 * update company in DB
	 *
	 * @throws LoginException
	 * @throws DAOException
	 * @param companyId
	 * @param WebCompany
	 */
	@PUT
	@Path("updatecompany/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCompany(@PathParam("id") long companyId, WebCompany webCompany)
			throws DAOException, LoginException {
		MyLogger.logger.info("Entering To *** updateCompany() *** Method");
		webCompany.setId(companyId);
		try {
			getAdminFacade().updateCompany(webCompany.convertToCompany());
			MyLogger.logger.info("updateCompany() Execute Seccesfully");
		} catch (DAOException e) {
			MyLogger.logger.severe("There Is A Problem With updateCompany() Execute: " + e.getMessage());
			e.getMessage();
		}
		MyLogger.logger.info("Exiting updateCompany() Method");

	}

	// ***************************************
	/**
	 * get all companies from DB
	 *
	 * @return Collection of WebCompanies
	 * @throws LoginException
	 * @throws DAOException
	 * 
	 */
	@GET
	@Path("getallcompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericEntity<Collection<WebCompany>> getAllCompanies() throws DAOException, LoginException {
		MyLogger.logger.info("Entering To *** getAllCompanies() *** Method");
		Collection<Company> dbCompanies;
		try {
			dbCompanies = getAdminFacade().getAllCompanies();
			MyLogger.logger.info("Convert To WebCompanies Execute Seccesfully");
			Collection<WebCompany> webCompanies = WebCompany.convertToWebCompanies(dbCompanies);
			MyLogger.logger.info("getAllCompanies() Execute Seccesfully ");
			return new GenericEntity<Collection<WebCompany>>(webCompanies) {
			};
		} catch (LoginException | DAOException e) {
			MyLogger.logger.severe("There Is A Problem With getAllCompanies() Execute" + e.getMessage());
			e.getMessage();
		}
		MyLogger.logger.info("Exiting to getAllCompanies() Method");
		return null;
	}

	// ***************************************
	/**
	 * get company from DB by ID
	 *
	 * @return WebCompany
	 * @throws LoginException
	 * @throws DAOException
	 * @param ID
	 */
	@GET
	@Path("getcompany/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WebCompany getCompany(@PathParam("id") long id) throws LoginException {
		MyLogger.logger.info("Entering To *** getCompany() *** Method");
		Company company = null;
		try {
			company = getAdminFacade().getCompany(id);
			MyLogger.logger.info("getCompany() Execute Seccesfully ");
			return new WebCompany(company);
		} catch (DAOException e) {
			MyLogger.logger.severe("There Is A Problem With getCompany() Execute" + e.getMessage());
			e.getMessage();
		}
		MyLogger.logger.info("Exiting to getCompany() Method");
		return null;
	}

	// ***************************************
	/**
	 * create customer in DB
	 *
	 * @throws LoginException
	 * @throws DAOException
	 * @param WebCustomer
	 */
	@POST
	@Path("createcustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public void createCustomer(WebCustomer webCustomer) throws LoginException {
		MyLogger.logger.info("Entering To *** createCustomer() *** Method");

		try {
			getAdminFacade().createCustomer(webCustomer.convertToCustomer());
			MyLogger.logger.info("createCustomer() Execute Seccesfully ");
		} catch (DAOException e) {
			MyLogger.logger.severe("There Is A Problem With createCustomer() Execute" + e.getMessage());
			e.getMessage();
		}
		MyLogger.logger.info("Exiting to createCustomer() Method");

		;
	}

	// ***************************************
	/**
	 * delete customer from DB
	 * 
	 * @throws LoginException
	 * @throws DAOException
	 * @param customerID
	 */
	@DELETE
	@Path("deletecustomer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteCustomer(@PathParam("id") long id) throws LoginException {
		MyLogger.logger.info("Entering To *** createCustomer() *** Method");

		WebCustomer webCustomer = new WebCustomer();
		webCustomer.setId(id);
		try {
			getAdminFacade().removeCustomer(webCustomer.convertToCustomer());
			MyLogger.logger.info("deleteCustomer() Execute Seccesfully ");
		} catch (DAOException e) {
			MyLogger.logger.severe("There Is A Problem With deleteCustomer() Execute" + e.getMessage());
			e.getMessage();
		}
		MyLogger.logger.info("Exiting deleteCustomer() Method");

	}

	// ***************************************
	/**
	 * update customer in DB
	 *
	 * @throws LoginException
	 * @throws DAOException
	 * @param customerID
	 * @param WebCustomer
	 */
	@PUT
	@Path("updatecustomer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updetCustomer(@PathParam("id") long id, WebCustomer webCustomer) throws LoginException {
		MyLogger.logger.info("Entering To *** updetCustomer() *** Method");
		webCustomer.setId(id);
		try {
			getAdminFacade().updateCustomer(webCustomer.convertToCustomer());
			MyLogger.logger.info("updetCustomer() Execute Seccesfully ");
		} catch (DAOException e) {
			MyLogger.logger.severe("There Is A Problem With updetCustomer() Execute" + e.getMessage());
			e.getMessage();
		}
		MyLogger.logger.info("Exiting to updetCustomer() Method");

	}

	// ***************************************
	/**
	 * get customer from DB by Id
	 *
	 * @return Customer
	 * @throws LoginException
	 * @throws DAOException
	 * @param customerId
	 */
	@GET
	@Path("getcustomer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@PathParam("id") long id) throws LoginException {
		MyLogger.logger.info("Entering To *** getCustomer() *** Method");
		try {
			MyLogger.logger.info("getCustomer() Execute Seccesfully ");
			return getAdminFacade().getCustomer(id);
		} catch (DAOException e) {
			MyLogger.logger.severe("There Is A Problem With getCustomer() Execute" + e.getMessage());
			e.getMessage();
		}
		MyLogger.logger.info("Exiting getCustomer() Method");
		return null;
	}

	// ***************************************
	/**
	 * get all customers from DB
	 *
	 * @return collection of webcustomer
	 * @throws LoginException
	 * @throws DAOException
	 */
	@GET
	@Path("getallcustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<WebCustomer> getAllCustomers() throws LoginException {
		Collection<WebCustomer> webCustomers = new ArrayList<>();
		MyLogger.logger.info("Entering to getAllCustomers Mehod");
		try {
			Collection<Customer> customers = getAdminFacade().getAllCustomer();
			MyLogger.logger.info("called To get AllCustomers Method In AdminServis");
			for (Customer customer : customers) {
				webCustomers.add(new WebCustomer(customer));
			}
			MyLogger.logger.info("Convert To webCustomer Seccesfully");
			return webCustomers;
		} catch (DAOException e) {
			MyLogger.logger.severe("Threr Is A Problem To get All Customres" + e.getMessage());
			// TODO Auto-generated catch block
			e.getMessage();
		}
		MyLogger.logger.info("Exiting getAllCustomers Method");
		return webCustomers;
	}

}
