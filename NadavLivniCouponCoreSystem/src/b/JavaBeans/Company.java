package b.JavaBeans;

import java.util.Collection;

/**
 * A class representing company attributes
 */
public class Company {
	private Long id;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;

	public Company() {
		super();
	}

	public Company(Long id, String compName, String password, String email) {
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		return "Company [id=" + id + ", Name=" + compName + ", password=" + password + ", email=" + email + "]";
	}

}
