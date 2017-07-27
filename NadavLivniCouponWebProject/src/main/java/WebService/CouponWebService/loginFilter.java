package WebService.CouponWebService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import c.DAO.DAOException;
import e.Facades.CouponClientFacade;
import e.Facades.clientType;
import g.CouponSystem.CouponSystem;

@Path("loginFilter")
public class loginFilter {
	@Context
	private HttpServletRequest request;

	@Path("login/{userName}/{password}/{clientType}")
	@POST
	public Response doPost(@PathParam("userName") String user, @PathParam("password") String pwd,
			@PathParam("clientType") clientType type) {

		try {
			CouponClientFacade couponClientFacade = null;
			couponClientFacade = CouponSystem.getInstance().Login(user, pwd, type);
			request.getSession().setAttribute("couponClientFacade", couponClientFacade);
			request.getSession().setAttribute("username", user);
			request.getSession().setAttribute("password", pwd);
			return Response.ok().build();

		} catch (DAOException e) {
			MyLogger.logger.info(e.getMessage());
			return Response.status(400).entity(e.getMessage()).build();
		}
	}

}
