package h.tests;

import java.sql.Date;

import b.JavaBeans.Company;
import b.JavaBeans.Coupon;
import b.JavaBeans.CouponType;
import b.JavaBeans.Customer;
import c.DAO.DAOException;
import d.DBDAO.CompanyDBDAO;
import d.DBDAO.CouponDBDAO;
import d.DBDAO.CustomerDBDAO;
import d.DBDAO.JoinedTablesDBDAO;
import e.Facades.CompanyFacade;
import e.Facades.clientType;
import g.CouponSystem.CouponSystem;

public class SimpleTests {
	public static void main(String[] args) throws DAOException {

		// ************************************************************
		// Creating An Object Of Coupon Class (Worked)
		Coupon coupon1 = new Coupon(1L, "Test1", Date.valueOf("2017-3-19"), Date.valueOf("2018-3-23"), 1,
				CouponType.CAMPING, "test1", 150.4, "1.img");
		Coupon coupon2 = new Coupon(2L, "Test2", Date.valueOf("2017-2-2"), Date.valueOf("2018-4-20"), 2,
				CouponType.ELECTRICITY, "test2", 202.5, "2.img");
		Coupon coupon3 = new Coupon(3L, "Test3", Date.valueOf("2017-4-2"), Date.valueOf("2018-5-20"), 2,
				CouponType.FOOD, "test3", 43.5, "3.img");
		// ************************************************************
		// Creating An Object Of CouponDBDAO Class (Worked)
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		// ************************************************************
		// Applying The createCoupon Method (Worked)
		couponDBDAO.createCoupon(coupon1);
		couponDBDAO.createCoupon(coupon2);
		couponDBDAO.createCoupon(coupon3);
		// ************************************************************
		// Creating An Object Of Customer Class (Worked)
		Customer customer1 = new Customer(1L, "Test1", "PassTest1");
		Customer customer2 = new Customer(2L, "Test2", "PassTest2");
		Customer customer3 = new Customer(3L, "Test3", "PassTest3");
		// ************************************************************
		// Creating An Object Of CompanyDBDAO Class (Worked)
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		// ************************************************************
		// Applying The createCompany Method (Worked)
		customerDBDAO.createCustomer(customer1);
		customerDBDAO.createCustomer(customer2);
		customerDBDAO.createCustomer(customer3);
		// ************************************************************
		// Creating An Object Of Company Class (Worked)
		Company company1 = new Company(111L, "Test1", "password1", "test1@test.com");
		Company company2 = new Company(222L, "Test2", "password2", "test2@test.com");
		Company company3 = new Company(333L, "Test3", "password3", "test3@test.com");
		// ************************************************************
		// Creating An Object Of CompanyDBDAO Class (Worked)
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		// ************************************************************
		// Applying The createCompany Method
		companyDBDAO.createCompany(company1);
		companyDBDAO.createCompany(company2);
		companyDBDAO.createCompany(company3);
		// ************************************************************
		// Creating An Object Of JoinedTableDBDAO Class (Worked)
		JoinedTablesDBDAO joinedTableDBDAO = new JoinedTablesDBDAO();
		// ************************************************************
		// Adds New Coupon To A Specific Company (Worked)
		joinedTableDBDAO.createCompany_Coupon(company1, coupon1);
		joinedTableDBDAO.createCompany_Coupon(company2, coupon2);
		joinedTableDBDAO.createCompany_Coupon(company3, coupon3);
		joinedTableDBDAO.createCompany_Coupon(company1, coupon2);
		joinedTableDBDAO.createCompany_Coupon(company2, coupon3);

		// ************************************************************
		// Specific Customer Purchases A Specific Coupon (Worked)
		joinedTableDBDAO.createCustomer_Coupon(customer1, coupon1);
		joinedTableDBDAO.createCustomer_Coupon(customer1, coupon2);
		joinedTableDBDAO.createCustomer_Coupon(customer1, coupon3);
		joinedTableDBDAO.createCustomer_Coupon(customer2, coupon2);
		joinedTableDBDAO.createCustomer_Coupon(customer3, coupon3);
		// ************************************************************
		// ************************************************************
		// Create An Available CouponSystem Object
		CompanyFacade couponSystem = (CompanyFacade) CouponSystem.getInstance().Login("Test1", "password1",
				clientType.COMPANY);
		;
		// ************************************************************
		// ************************************************************
		// Uses The Getter For The Correct Login Client Type For Calling Methods
		// From The Facade
		couponSystem.removeCoupon(coupon1);
		// ************************************************************
		// Shuts Down The Entire CouponSystem Services (Thread And Connection
		// Pool)
		CouponSystem.getInstance().shutdown();
		// ************************************************************
	}
}
