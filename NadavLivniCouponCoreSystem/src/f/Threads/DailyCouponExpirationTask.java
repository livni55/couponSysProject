package f.Threads;

import java.sql.Date;
import java.util.Collection;

import b.JavaBeans.Coupon;
import c.DAO.DAOException;
import d.DBDAO.CouponDBDAO;
import d.DBDAO.JoinedTablesDBDAO;

/**
 * A runnable class which contain the daily process of removing expired coupons
 * from the database. <br>
 * The runnable also contains the delay of the process which is 24 hour
 * interval.
 */
public class DailyCouponExpirationTask implements Runnable {

	/**
	 * A running method which contains the process of the runnable object. <br>
	 * This is done first at system startup, by executing
	 * <b>deleteExpiredCoupons</b> method, which removes all expired coupons
	 * from the database.<br>
	 * Then, a constant interval is set to 24 hours between every execution of
	 * deleteAllExpiredCoupons method. This method runs until the containing
	 * thread gets interrupted.
	 */
	@Override
	public void run() {
		while (true) {
			try {
				deleteExpiredCoupons();
				Thread.sleep(86400000);
			} catch (InterruptedException e) {
				System.out.println("Interrupted, shutting down");
				return;
			}
		}

	}

	private void deleteExpiredCoupons() {
		CouponDBDAO couponsDBDAO = new CouponDBDAO();
		JoinedTablesDBDAO joinedTablesDBDAO = new JoinedTablesDBDAO();
		Date date = new Date(System.currentTimeMillis());
		Collection<Coupon> coupons;
		try {
			coupons = couponsDBDAO.getAllCoupon();
			for (Coupon coupon : coupons) {
				if (coupon.getEndDate().compareTo(date) < 0) {
					couponsDBDAO.removeCoupon(coupon);
					joinedTablesDBDAO.RemoveCoupon(coupon);
				}
			}
		} catch (DAOException e) {
			System.err.println("Expired coupons delete faild" + e.getMessage());
		}
	}

}
