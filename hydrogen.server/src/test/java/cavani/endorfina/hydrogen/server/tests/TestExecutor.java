package cavani.endorfina.hydrogen.server.tests;

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
	public void testTrack() throws Exception
	{
		startTest(getMethodName());
	}

	@Test
	public void testRootFile() throws Exception
	{
		startTest(getMethodName());
	}

}
