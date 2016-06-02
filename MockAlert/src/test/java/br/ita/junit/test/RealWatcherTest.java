package br.ita.junit.test;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class RealWatcherTest {

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

}
