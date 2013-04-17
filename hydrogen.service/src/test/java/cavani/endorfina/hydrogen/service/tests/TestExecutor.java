package cavani.endorfina.hydrogen.service.tests;

import org.junit.Test;
import org.vertx.java.testframework.TestBase;

public class TestExecutor extends TestBase
{

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		startApp(IntegrationTest.class.getName());
	}

	@Test
	public void testTrackItem1() throws Exception
	{
		startTest(getMethodName());
	}

	@Test
	public void testTrackItem2() throws Exception
	{
		startTest(getMethodName());
	}

}
