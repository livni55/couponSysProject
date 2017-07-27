package ObjectBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import b.JavaBeans.Coupon;
import b.JavaBeans.Customer;

@XmlRootElement
public class WebCustomer implements Serializable {

	private static final long serialVersionUID = 1L;

	// Attribute***************
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	// CTOR********************
	public WebCustomer() {
	}

	public WebCustomer(long id, String custName, String password) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
	}

	// CTOR for Web Coupon that rum on the net without Password
	public WebCustomer(Customer customer) {
		super();
		this.id = customer.getId();
		this.custName = customer.getCustName();
		this.password = customer.getPassword();
		this.coupons = customer.getCoupons();
	}

	// *************convertToCustomer***************************

	public Customer convertToCustomer() {
		return new Customer(id, custName, password);
	}
	// **************convertToWebCustomer**********************

	public WebCustomer convertToWebCustomer(Customer customer) {
		return new WebCustomer(customer);
	}
	// ********************convertToCustomers***********************

	public static Collection<Customer> convertToCustomers(Collection<WebCustomer> webCustomers) {
		Collection<Customer> customers = new ArrayList<>();
		for (WebCustomer webCustomer : webCustomers) {
			customers.add(webCustomer.convertToCustomer());
		}
		return customers;
	}
	// ***********************convertToWebCustomers******************************

	public static Collection<WebCustomer> convertToWebCustomers(Collection<Customer> customers) {
		Collection<WebCustomer> webCustomers = new ArrayList<>();
		for (Customer customer : customers) {
			webCustomers.add(new WebCustomer(customer));
		}
		return webCustomers;
	}

	// ***********************************************************************
	// Getters and Setters
	public void setId(long id) {
		if (this.getId() == id || this.getId() == 0) {
			this.id = id;
		} else {
			System.out.println("Cannot Update ID - Primary Key");
			return;
		}
	}

	public long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

}
