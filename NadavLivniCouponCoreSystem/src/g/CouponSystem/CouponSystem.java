package g.CouponSystem;

import a.ConnectionPool.ConnectionPool;
import c.DAO.DAOException;
import e.Facades.AdminFacade;
import e.Facades.CompanyFacade;
import e.Facades.CouponClientFacade;
import e.Facades.CustomerFacade;
import e.Facades.clientType;
import f.Threads.DailyCouponExpirationTask;

/**
 * A <b>singleton</b> That function as the top layer of the application.
 */
public class CouponSystem {
	private static CouponSystem instance = new CouponSystem();
	private Thread DailyThread;

	/**
	 * private CTOR that initialize the system.<br>
	 * "inits" the connectionPool,starts the DailyThread <br>
	 * if needed, for the first lunch you can build the DB.
	 */
	private CouponSystem() {
		// if needed delete the comment (ctrl+shift+\)

		/*
		 * try { DBcreateWithTables.DBInit(); } catch (DAOException e) {
		 * e.printStackTrace(); }
		 */

		ConnectionPool.getInstance();
		this.DailyThread = new Thread(new DailyCouponExpirationTask());
		DailyThread.start();
	}

	public static CouponSystem getInstance() {
		return instance;
	}

	/**
	 * Login action performed by client with specific parameters.
	 * 
	 * @param name
	 *            The name of the client.
	 * @param password
	 *            The password of the client.
	 * @param type
	 *            The client type of which to try and return facade instance
	 *            from.
	 * @return CouponClientFacade that points at a specific client type as
	 *         given.
	 * @throws DAOException
	 *             When login failed, or no client type was given.
	 */
	public CouponClientFacade Login(String name, String password, clientType type) throws DAOException {
		switch (type) {
		case ADMIN:
			return AdminFacade.login(name, password);
		case COMPANY:
			return CompanyFacade.login(name, password);
		case CUSTOMER:
			return CustomerFacade.login(name, password);
		default:
			throw new DAOException("There is a problem with the client type");
		}

	}

	/**
	 * Order application content to stop and end, in order to close the
	 * application.
	 */
	public void shutdown() {
		DailyThread.interrupt();
		ConnectionPool.getInstance().closeAllConnections();
	}

	/**
	 * used in the test to check the DailyThread status
	 * 
	 * @return boolean DailyThread.isAlive()
	 */
	public boolean getThreadStatus() {
		return DailyThread.isAlive();
	}

}
