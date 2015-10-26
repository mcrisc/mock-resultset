package commondb.sample;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.ResultSet;

import commondb.mock.MockResultSet;

public class Example {
	public static void main(String[] args) throws Exception {
		InputStream data = ClassLoader.getSystemResourceAsStream("data.csv");
		
		// making a ResultSet from a CSV file
		ResultSet rs = new MockResultSet(new InputStreamReader(data));
		data.close();

		
		// from now on, it's simply a java.util.ResultSet
		while (rs.next()) {
			
			double balance = rs.getDouble("balance");
			Date date = rs.getDate("creation_date");
			
			if (balance > 0) {
				System.out.println(date + ": " + balance);
			}
		}
		
		rs.close();
	}
}
