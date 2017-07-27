package h.tests;

import java.sql.Date;

import b.JavaBeans.Company;
import b.JavaBeans.Coupon;
import b.JavaBeans.CouponType;
import b.JavaBeans.Customer;
import c.DAO.DAOException;
import e.Facades.AdminFacade;
import e.Facades.CompanyFacade;
import e.Facades.CustomerFacade;
import e.Facades.clientType;
import g.CouponSystem.CouponSystem;

public class CouponSystemTest {
	public static void main(String[] args) {
		// Creating An Object Of Company Class (Worked)
		Company company1 = new Company(111L, "Test1", "password1", "test1@test.com");
		Company company2 = new Company(222L, "Test2", "password2", "test2@test.com");
		Company company3 = new Company(333L, "Test3", "password3", "test3@test.com");
		// Creating An Object Of Customer Class (Worked)
		Customer customer1 = new Customer(1L, "Test1", "PassTest1");
		Customer customer2 = new Customer(2L, "Test2", "PassTest2");
		Customer customer3 = new Customer(3L, "Test3", "PassTest3");
		// Creating An Object Of Coupon Class (Worked)
		Coupon coupon1 = new Coupon(1L, "Test1", Date.valueOf("2017-3-19"), Date.valueOf("2018-3-23"), 1,
				CouponType.CAMPING, "test1", 150.4, "1.img");
		Coupon coupon2 = new Coupon(2L, "Test2", Date.valueOf("2017-2-2"), Date.valueOf("2018-4-20"), 2,
				CouponType.ELECTRICITY, "test2", 202.5, "2.img");
		Coupon coupon3 = new Coupon(3L, "Test3", Date.valueOf("2017-4-2"), Date.valueOf("2018-5-20"), 2,
				CouponType.FOOD, "test3", 43.5, "3.img");
		Coupon coupon4 = new Coupon(444444L, "tobe removed", Date.valueOf("2012-4-2"), Date.valueOf("2015-5-20"), 2,
				CouponType.FOOD, "test4", 433333.5, "3.img");

		// ********************************************************************************
		// ******************Testing Admin Facade***********************
		System.out.println("***Start of Admin Facade Test***");
		AdminFacade aFacade = null;
		try {
			aFacade = (AdminFacade) CouponSystem.getInstance().Login("admin", "1234", clientType.ADMIN);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		try {
			aFacade.creatCompany(company1);
			aFacade.creatCompany(company2);
			aFacade.creatCompany(company3);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		try {
			aFacade.createCustomer(customer1);
			aFacade.createCustomer(customer2);
			aFacade.createCustomer(customer3);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("All companies:");
		try {
			System.out.println(aFacade.getAllCompanies());
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("=========================");
		System.out.println("All customers:");
		try {
			System.out.println(aFacade.getAllCustomer());
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("=========================");
		System.out.println("get company1 by ID:");
		try {
			System.out.println(aFacade.getCompany(company1.getId()));
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("get customer1 by ID:");
		try {
			System.out.println(aFacade.getCustomer(customer1.getId()));
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		company1.setEmail("newone@gmail.com");
		try {
			aFacade.updateCompany(company1);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("get company1 with new mail:");
		try {
			System.out.println(aFacade.getCompany(company1.getId()));
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		customer2.setPassword("newPASS");
		try {
			aFacade.updateCustomer(customer2);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("get customer2 with new PASS:");
		try {
			System.out.println(aFacade.getCustomer(customer2.getId()));
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		try {
			aFacade.removeCompany(company3);
			aFacade.removeCompany(company1);
			aFacade.removeCompany(company2);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		try {
			aFacade.removeCustomer(customer3);
			aFacade.removeCustomer(customer2);
			aFacade.removeCustomer(customer1);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("All companies after remove:");
		try {
			System.out.println(aFacade.getAllCompanies());
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("=========================");
		System.out.println("All customers after remove:");
		try {
			System.out.println(aFacade.getAllCustomer());
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("=========================");
		// Adding the companies and the customers for the other tests
		try {
			aFacade.creatCompany(company1);
			aFacade.creatCompany(company2);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		try {
			aFacade.createCustomer(customer1);
			aFacade.createCustomer(customer2);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("companies and customers where added so other tests will work");
		System.out.println("***=End of Admin Facade Test***");

		// ************************************************************
		// ******************Testing Company Facade************
		System.out.println("***start of Company Facade Test***");
		CompanyFacade cFacade = null;
		try {
			cFacade = (CompanyFacade) CouponSystem.getInstance().Login(company1.getCompName(), company1.getPassword(),
					clientType.COMPANY);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("current company:");
		System.out.println(cFacade.getCompany());
		try {
			cFacade.creatCoupon(coupon1);
			cFacade.creatCoupon(coupon2);
			cFacade.creatCoupon(coupon3);
			cFacade.creatCoupon(coupon4); // the daily thread should remove that
											// one (and it did!)
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("all coupons:");
		try {
			System.out.println(cFacade.getAllCoupons());
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("=================");
		System.out.println("coupon 1 by id:");
		try {
			System.out.println(cFacade.getCoupon(coupon1.getId()));
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("coupons by type Camping:");
		try {
			System.out.println(cFacade.getCouponsByType(CouponType.CAMPING));
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("coupons by date- enterd 2017-3-23 should return only one: ");
		try {
			System.out.println(cFacade.getCouponsWithMaxEndDate(Date.valueOf("2018-3-23")));
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("coupons by maxprice- enterd 160:");
		try {
			System.out.println(cFacade.getCouponsWithMaxPrice(160));
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		try {
			cFacade.removeCoupon(coupon2);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		try {
			cFacade.updateCoupon(1L, Date.valueOf("2020-8-23"), 50055.5);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("All coupons after remove and update:");
		try {
			System.out.println(cFacade.getAllCoupons());
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("***END of Company Facade Test***");

		// ************************************************************
		// ******************Testing Customer Facade*******************

		System.out.println("***start of Customer Facade Test***");
		CustomerFacade cFacade2 = null;
		try {
			cFacade2 = (CustomerFacade) CouponSystem.getInstance().Login("Test1", "PassTest1", clientType.CUSTOMER);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		try {
			cFacade2.purchaseCoupon(coupon1);
			cFacade2.purchaseCoupon(coupon3);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("all Purchases: ");
		try {
			System.out.println(cFacade2.getAllPurchases());
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("all Purchases by maxprice 100: ");
		try {
			System.out.println(cFacade2.getAllPurchasesByMaxPrice(100));
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("all Purchases by type food: ");
		try {
			System.out.println(cFacade2.getAllPurchasesByType(CouponType.FOOD));
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("***END of Customer Facade Test***");

		// ************************************************************
		// Shuts Down The Entire CouponSystem Services (Thread And
		// Connection Pool)
		CouponSystem.getInstance().shutdown();
		System.out.println("waiting");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(CouponSystem.getInstance().getThreadStatus());

	}

}
