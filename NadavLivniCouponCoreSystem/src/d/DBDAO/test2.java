package d.DBDAO;

import java.sql.SQLException;

public class test2 {
	public static void main(String[] args) throws SQLException {
		int x = 2;
		if (x == 0 ? true : false) {
			System.out.println("true");
		}
		String y = "Nadav";
		System.out.println(y.getClass());
		System.out.println(y);
	}
}
