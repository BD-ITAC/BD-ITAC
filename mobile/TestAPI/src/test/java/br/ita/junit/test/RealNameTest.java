package br.ita.junit.test;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

import org.junit.AfterClass;
import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class RealNameTest {

	private static String testLog = "";
	
	@Rule public TestName name = new TestName();
	
	@Rule public TestRule testLogger = new TestWatcher() {
		@Override
	    protected void succeeded(Description description) {
			testLog += description.getDisplayName() + " " + "succeeded\n";
		}
		@Override
	    protected void failed(Throwable e, Description description) {
			testLog += description.getDisplayName() + " " + e.getClass().getSimpleName() + " failed\n";
		}
		@Override
	    protected void skipped(AssumptionViolatedException e, Description description) {
			testLog += description.getDisplayName() + " " + e.getClass().getSimpleName() + " skipped\n";
		}
	};

	@AfterClass
	public static void printLog() {
		System.out.println(testLog);
	}
	
	@Test public void exemploSucesso() {
		assertEquals("exemploSucesso", name.getMethodName());
	}
	
	@Test public void exemploFracasso() {
		assertEquals("exemplo de fracasso", name.getMethodName());
	}

	@Test public void exemploIgnorado() {
		assumeThat(testLog, not(isA(String.class)));
		assertEquals("exemploIgnorado", name.getMethodName());
	}

}
