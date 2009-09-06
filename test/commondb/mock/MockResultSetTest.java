package commondb.mock;

import static org.junit.Assert.assertEquals;

import java.io.InputStreamReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MockResultSetTest {
	private MockResultSet rs;
	
	@Before
	public void loadData() throws Exception {
		rs = new MockResultSet();
		rs.loadCSV(new InputStreamReader(MockResultSetTest.class.getResourceAsStream("resources/data.csv")));
	}
	
	@After
	public void afterTest() throws Exception {
		rs.close();
	}

	@Test
	public void testFirstRow() throws Exception {
		rs.next();
		assertEquals(1, rs.getInt("id"));
		assertEquals(-30, rs.getInt("balance"));
	}
	
	@Test
	public void testDoubleValue() throws Exception {
		rs.next(); rs.next(); rs.next();
		assertEquals(-1.4, rs.getDouble("balance"));
	}
}
