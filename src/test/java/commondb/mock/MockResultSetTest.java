package commondb.mock;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MockResultSetTest {
	private ResultSet rs;
	
	@Before
	public void loadData() throws Exception {
		rs = new MockResultSet();
		InputStream data = ClassLoader.getSystemResourceAsStream("data.csv");
		((MockResultSet)rs).loadCSV(new InputStreamReader(data));
	}
	
	@After
	public void afterTest() throws Exception {
		rs.close();
	}

	@Test
	public void testMoveFirst() throws Exception {
		assertTrue(rs.first());
		assertFalse(rs.previous());
	}

	@Test(expected=SQLException.class)
	public void testNoRow() throws Exception {
		rs.getString(1);
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
		 assertTrue(rs.last());
		 assertFalse(rs.next());
	}

	@Test
	public void testLastRow() throws Exception {
		assertTrue(rs.last());
		assertEquals(0, rs.getDouble("balance"));
		assertFalse(rs.next());
	}
	
	@Test
	public void testFullIteration() throws Exception {
		int id = 0;
		while (rs.next()) {
			id++;
			assertEquals(id, rs.getInt("id"));
		}
		
		assertEquals(11, id);
	}

	@Test
	public void testEmptyField() throws Exception {
		rs.next();rs.next();
		
		assertEquals("", rs.getString("parent_id"));
	}
	
	@Test
	public void testEscapedChars() throws Exception {
		rs.last();
		assertEquals("Client3,\"", rs.getString("name"));
	}
	
	@Test
	public void testDateValues() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date expected = sdf.parse("21/12/2009");
		
		rs.first();
		Date readDate = rs.getDate("creation_date");
		assertEquals(expected, readDate);
	}
	
	@Test
	public void testInvalidColumnName() {
		try {
			rs.next();
			rs.getString("last_name");
			fail();
		} catch (SQLException e) {
			assertTrue(e.getMessage().endsWith(MockResultSet.INVALID_COLUMN_NAME));
		}
	}
	
	@Test
	public void testNegativeColumnIndex() throws Exception {
		try {
			rs.next();
			rs.getDouble(-1);
			fail();
		} catch (SQLException e) {
			assertTrue(e.getMessage().endsWith(MockResultSet.INVALID_COLUMN_INDEX));
		}
	}
	
	@Test
	public void testIndexOutOfBounds() throws Exception {
		try {
			rs.next();
			rs.getDate(250);
			fail();
		} catch (SQLException e) {
			assertTrue(e.getMessage().endsWith(MockResultSet.INVALID_COLUMN_INDEX));
		}
	}
	
}
