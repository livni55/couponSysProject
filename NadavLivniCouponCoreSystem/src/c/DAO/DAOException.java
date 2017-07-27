package c.DAO;

/**
 * Excpetion thrown when application communication with database fails, or when
 * some other issue is raised when working with database.
 */
public class DAOException extends Exception {

	/**
	 * for serializetion which not used, but inherited from Exception.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new DAOException with the specified detail message and
	 * cause.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new DAOException with the specified detail message.
	 */
	public DAOException(String message) {
		super(message);
	}
}
