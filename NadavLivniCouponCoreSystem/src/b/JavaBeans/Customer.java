package b.JavaBeans;

import java.util.Collection;

/**
 * A class representing customer attributes.
 */
public class Customer {
	private Long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	public Customer() {
		super();
	}

	public Customer(Long id, String custName, String password) {
		this.id = id;
		this.custName = custName;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String compName) {
		this.custName = compName;
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

	public void AddCoupons(Coupon coupon) {
		this.coupons.add(coupon);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + "]";
	}

}
