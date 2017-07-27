package Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import e.Facades.AdminFacade;
import e.Facades.CompanyFacade;
import e.Facades.CouponClientFacade;
import e.Facades.CustomerFacade;

/**
 * Servlet implementation class servletLogin
 */
public class servletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	CouponClientFacade couponClientFacade = null;

	private void setCookies(HttpServletRequest request, HttpServletResponse response, String user, String pwd) {
		Cookie cookieName = new Cookie("userName", user);
		cookieName.setMaxAge(30 * 60);
		Cookie cookiePass = new Cookie("password", pwd);
		cookiePass.setMaxAge(30 * 60);
		Cookie cookieType = new Cookie("clientType", request.getParameter("clientType"));
		cookieType.setMaxAge(30 * 60);

		response.addCookie(cookieName);
		response.addCookie(cookiePass);
		response.addCookie(cookieType);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		CouponClientFacade facade = (CouponClientFacade) request.getSession().getAttribute("couponClientFacade");
		String username = (String) request.getSession().getAttribute("username");
		String password = (String) request.getSession().getAttribute("password");
		setCookies(request, response, username, password);
		if (facade instanceof AdminFacade) {
			response.sendRedirect("http://localhost:8080/CouponWebService/admin/AdminIndex.html");
		} else if (facade instanceof CompanyFacade) {
			response.sendRedirect("http://localhost:8080/CouponWebService/company/company.html");
		} else if (facade instanceof CustomerFacade) {
			response.sendRedirect("http://localhost:8080/CouponWebService/customer/customer.html");
		}

	}

}
