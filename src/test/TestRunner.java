package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class TestRunner {
	 public static void main(String[] args) {
	      Result result = JUnitCore.runClasses(BarrenLandAnalysisTest.class);
			
	      for (Failure failure : result.getFailures()) {
	         System.out.println("Failed: " + failure.toString());
	      }
			
	      System.out.println("All tests passed: "+ result.wasSuccessful());
	   }
}
