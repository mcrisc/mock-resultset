package commondb.mock;

import static org.junit.Assert.assertEquals;

import java.io.InputStreamReader;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MockResultSetTest {
	private ResultSet rs;
	
	@Before
	public void loadData() throws Exception {
		rs = new MockResultSet();
		((MockResultSet)rs).loadCSV(new InputStreamReader(MockResultSetTest.class.getResourceAsStream("resources/data.csv")));
	}
	
	@After
	public void afterTest() throws Exception {
		rs.close();
	}

	@Test
	public void testMoveFirst() throws Exception {
		rs.first();
	}

	@Test
	public void testFirstRow() throws Exception {
		rs.first();
		assertEquals(1, rs.getInt("id"));
		assertEquals(-30, rs.getInt("balance"));
	}
	
	@Test
	public void testDoubleValue() throws Exception {
		rs.next(); rs.next(); rs.next();
		assertEquals(-1.4, rs.getDouble("balance"));
	}
	
	@Test
	public void testMoveLast() throws Exception {
		rs.last();
	}

	public void testLastRow() throws Exception {
		rs.last();
		assertEquals(0, rs.getDouble("balance"));
	}
}
