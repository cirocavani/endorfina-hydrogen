package cavani.endorfina.hydrogen.engine.tests;

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
	public void testTotalItem1() throws Exception
	{
		startTest(getMethodName());
	}

	@Test
	public void testTotalItem2() throws Exception
	{
		startTest(getMethodName());
	}

}
