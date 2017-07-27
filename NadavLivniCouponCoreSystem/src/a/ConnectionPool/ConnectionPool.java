package a.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * A <b>singleton</b> that handles all Connections of the application with the
 * database. <br>
 * That is performed by having a Collection of connections that stay open
 * through the entire application's lifetime, Letting every client borrow a
 * single connection from the pool and return it to the pool when he is done
 * with it. that process, which avoids persistent opening and closing of
 * connections, makes the application a lot more efficient since every
 * connection takes much longer time than all other tasks in this application.
 * The address of the database is given from the file located in the project
 * (folder files/url.txt)
 */
public class ConnectionPool {

	public static final int MAX_CONNECTIONS = 10;
	private Collection<Connection> connectionpool = new HashSet<>();

	/**
	 * Set the class singleton and get Instance.
	 */
	private static ConnectionPool instance = new ConnectionPool();

	public static ConnectionPool getInstance() {
		return instance;
	}

	/**
	 * Private constructor to build the connection pool. The constructor
	 * for-loop is adding a connection to the connection poll set by the
	 * MAX_CONNECTIONS number.
	 */
	private ConnectionPool() {
		try {
			for (int i = 0; i < MAX_CONNECTIONS; i++) {
				connectionpool.add(createConnection());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		System.out.println("connection pool was initialized ");
	}

	/**
	 * This method reads the DataBase url from the url file located in the
	 * project in folder called "files". It builds a connection and return it to
	 * the caller.
	 * 
	 * @return a connection or null if there been an error
	 */
	private Connection createConnection() {
		try {
			String url = "jdbc:derby://localhost:1527/projectDB;";
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			return (DriverManager.getConnection(url));
		} catch (ClassNotFoundException e) {
			System.err.println("There is an error with the driver loading");
		} catch (SQLException e) {
			System.err.println("There is an error with getting the connection from the DB");
		}
		return null;
	}

	/**
	 * Synchronized method, while there is no unused connection in the pool, it
	 * waits for the notify (will get it from the return connection method)
	 * after a connection was returned/there is an unused one the method will
	 * 
	 * @return a connection from the connection pool.
	 **/
	public synchronized Connection getConnection() {
		Iterator<Connection> it = connectionpool.iterator();
		while (!it.hasNext()) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println("getConnection was Interupted");
			}
		}
		Connection connection = it.next();
		it.remove();
		notifyAll();
		return connection;
	}

	/**
	 * return a connection that was sent from the user to the connection pool
	 * 
	 * @param connection
	 *            the connection to return
	 */
	public synchronized void returnConnection(Connection connection) {
		connectionpool.add(connection);
		notifyAll();
	}

	/**
	 * This method uses the getConnection method to get back all the connections
	 * and close them. if it will have to wait it will happen in the
	 * getConnection method.
	 */
	public synchronized void closeAllConnections() {
		for (int i = 0; i < MAX_CONNECTIONS; i++) {
			try {
				getConnection().close();
			} catch (SQLException e) {
				System.err.println("Error while trying to close all connections");
			}
		}
		System.out.println("All Connections Are Closed");
	}
}