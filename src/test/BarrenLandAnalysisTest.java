package test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import main.BarrenLandAnalysis;

public class BarrenLandAnalysisTest {
	BarrenLandAnalysis test;
	
	@Test
	public void testNoBarrenLandTest() throws IOException {
		test = new BarrenLandAnalysis();
		
		//inputString: { }
		String inputString = new String("{ }");
		ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(in);
		
		String expectedResult = new String("240000");
		String actualResult = test.getFertileLandAreas();
		
		assertEquals(expectedResult, actualResult);
		
	}
	
	@Test
	public void testNoFertileLandTest() throws IOException {
		test = new BarrenLandAnalysis();
		
		//inputString: {"0 0 399 599"}
		String inputString = new String("{0 0 399 599 }");
		ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(in);
		
		String expectedResult = new String("0");
		String actualResult = test.getFertileLandAreas();
		
		assertEquals(expectedResult, actualResult);
		
	}
	
	@Test
	public void testTest1() throws IOException {
		test = new BarrenLandAnalysis();
		
		//inputString: {"0 292 399 307"}
		String inputString = new String("{\"0 292 399 307\"}");
		ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(in);
		
		String expectedResult = new String("116800 116800");
		String actualResult = test.getFertileLandAreas();
		
		assertEquals(expectedResult, actualResult);
		
	}
	
	@Test
	public void testTest2() throws IOException {
		test = new BarrenLandAnalysis();
		
		//inputString: {"48 192 351 207", "48 392 351 407", "120 52 135 547", "260 52 275 547"}
		String inputString = new String("{\"48 192 351 207\", \"48 392 351 407\", \"120 52 135 547\", \"260 52 275 547 \"}");
		ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(in);
		
		String expectedResult = new String("22816 192608");
		String actualResult = test.getFertileLandAreas();
		
		assertEquals(expectedResult, actualResult);
		
	}
	
	@Test
	public void testXInputTooLargeTest() throws IOException {
		test = new BarrenLandAnalysis();
		
		//inputString: {"0 0 400 400"}
		String inputString = new String("{\"0 0 400 400\"}");
		ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(in);
		
		String expectedResult = new String("Invalid input");
		String actualResult = test.getFertileLandAreas();
		
		assertEquals(expectedResult, actualResult);
		
	}
	@Test
	public void testYInputTooLargeTest() throws IOException {
		test = new BarrenLandAnalysis();
		
		//inputString: {"0 0 300 600"}
		String inputString = new String("{\"0 0 300 600\"}");
		ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(in);
		
		String expectedResult = new String("Invalid input");
		String actualResult = test.getFertileLandAreas();
		
		assertEquals(expectedResult, actualResult);
		
	}
	
	@Test
	public void testXInputTooSmallTest() throws IOException {
		test = new BarrenLandAnalysis();
		
		//inputString: {"-1 0 400 400"}
		String inputString = new String("{\"-1 0 400 400\"}");
		ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(in);
		
		String expectedResult = new String("Invalid input");
		String actualResult = test.getFertileLandAreas();
		
		assertEquals(expectedResult, actualResult);
		
	}
	
	@Test
	public void testYInputTooSmallTest() throws IOException {
		test = new BarrenLandAnalysis();
		
		//inputString: {"-1 0 400 400"}
		String inputString = new String("{\"0 0 200 -1\"}");
		ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(in);
		
		String expectedResult = new String("Invalid input");
		String actualResult = test.getFertileLandAreas();
		
		assertEquals(expectedResult, actualResult);
		
	}
	
	@Test
	public void testEmptyStringTest() throws IOException {
		test = new BarrenLandAnalysis();
		
		//inputString: ""
		String inputString = new String("\0");
		ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(in);
		
		String expectedResult = "Invalid input";
		String actualResult = test.getFertileLandAreas();
		
		assertEquals(expectedResult, actualResult);
		
	}
	
	@Test
	public void testBlankStringTest() throws IOException {
		test = new BarrenLandAnalysis();
		
		//inputString: " "
		String inputString = new String(" ");
		ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(in);
		
		String expectedResult = "Invalid input";
		String actualResult = test.getFertileLandAreas();
		
		assertEquals(expectedResult, actualResult);
		
	}
	
	@Test
	public void testEmptyRectangleTest() throws IOException {
		test = new BarrenLandAnalysis();
		
		//inputString: {""}
		String inputString = new String("{\"\"}");
		ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(in);
		
		String expectedResult = "Invalid input";
		String actualResult = test.getFertileLandAreas();
		
		assertEquals(expectedResult, actualResult);
		
	}
	
	@Test
	public void testBlankRectangleTest() throws IOException {
		test = new BarrenLandAnalysis();
		
		//inputString: {"  "}
		String inputString = new String("{\"  \"}");
		ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(in);
		
		String expectedResult = "Invalid input";
		String actualResult = test.getFertileLandAreas();
		
		assertEquals(expectedResult, actualResult);
		
	}
	
	@Test
	public void testLessThanFourCoordinatesTest() throws IOException {
		test = new BarrenLandAnalysis();
		
		//inputString: {"1 2 3"}
		String inputString = new String("{\"1 2 3\"}");
		ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(in);
		
		String expectedResult = "Invalid input";
		String actualResult = test.getFertileLandAreas();
		
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void testNonNumericCoordinate() throws IOException {
		test = new BarrenLandAnalysis();
		
		//inputString: {"1 2 3 a"}
		String inputString = new String("{\"1 2 3 a\"}");
		ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(in);
		
		String expectedResult = "Invalid input";
		String actualResult = test.getFertileLandAreas();
		
		assertEquals(expectedResult, actualResult);
	}

}
