package cavani.endorfina.hydrogen.dashboard.tests;

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
	public void testTotal() throws Exception
	{
		startTest(getMethodName());
	}

	@Test
	public void testTotalHour() throws Exception
	{
		startTest(getMethodName());
	}

	@Test
	public void testTotalItem() throws Exception
	{
		startTest(getMethodName());
	}

	@Test
	public void testRootFile() throws Exception
	{
		startTest(getMethodName());
	}

}
