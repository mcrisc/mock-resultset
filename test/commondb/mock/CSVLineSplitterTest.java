package commondb.mock;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Ignore;
import org.junit.Test;

public class CSVLineSplitterTest {

	private CSVLineSplitter sp = new CSVLineSplitter();
	
	@Test(expected=NullPointerException.class)
	public void testNPE() {
		assertArrayEquals(new String[] {}, sp.split(null));
	}
	
	@Test
	public void testEmptyArray() {
		assertArrayEquals(new String[] {}, sp.split(""));
	}

	@Test
	public void testSingleQuotedElement() throws Exception {
		assertArrayEquals(new String[] {" "}, sp.split("\" \""));
	}
	
	@Test
	public void testSingleNonQuotedElement() throws Exception {
		assertArrayEquals(new String[] {"1"}, sp.split("\"1\""));
	}

	@Test
	public void testTrivialCase1() {
		assertArrayEquals(new String[] {"City1", "20"}, sp.split("\"City1\",20"));
	}

	@Test
	public void testTrivialCase2() throws Exception {
		assertArrayEquals(new String[] {"City1", "20"}, sp.split("\"City1\",\"20\""));
	}
	
	@Test
	public void testFinalEmptyField() {
		assertArrayEquals(new String[] {"City1", "20", ""}, sp.split("\"City1\",20,"));
		assertArrayEquals(new String[] {"1", ""}, sp.split("1,"));
	}

	@Test
	public void testEmptyFieldsOnly() throws Exception {
		assertArrayEquals(new String[] {"", "", ""}, sp.split(",,"));
	}

	@Test
	public void testMiddleEmptyFieldFollowedByQuoted() {
		assertArrayEquals(new String[] {"City1", "", "Neighborhood"}, sp.split("\"City1\",,\"Neighborhood\""));
	}

	@Test
	public void testMiddleEmptyFieldFollowedByNonQuoted() {
		assertArrayEquals(new String[] {"City1", "", "10"}, sp.split("\"City1\",,10"));
	}
	
	@Test
	public void testQuotesInsideField() throws Exception {
		assertArrayEquals(new String[] {"City2 \"", "10"}, sp.split("\"City2 \"\"\",\"10\""));
	}

	@Test
	public void testQuotesAndCommaInsideField() throws Exception {
		assertArrayEquals(new String[] {"City2, \"", "10"}, sp.split("\"City2, \"\"\",\"10\""));
	}
	
}
