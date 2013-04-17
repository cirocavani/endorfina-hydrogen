package cavani.endorfina.hydrogen.engine.data;

import org.junit.Assert;
import org.junit.Test;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.engine.data.DataTotalEngine.Data;

public class DataTotalEngineTest
{

	@Test
	public void testDataTotalParse()
	{
		final String userId = "456";

		final JsonObject message = new JsonObject();
		message.putString("userId", userId);

		final DataTotalEngine engine = new DataTotalEngine();
		final Data result = engine.parseMessage(message);

		Assert.assertNotNull(result);
		Assert.assertEquals(userId, result.userId);
	}

	private void testProcess(final Long value)
	{
		final Long[] values = new Long[]
		{
			value,
			value,
			value,
			value,
			value,
		};

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"result\":").append(value * values.length).append("}");

		final DataTotalEngine engine = new DataTotalEngine();
		final JsonObject result = engine.processResult(values);

		Assert.assertNotNull(result);
		Assert.assertEquals(expected.toString(), result.encode());
	}

	@Test
	public void testDataTotalProcess()
	{
		testProcess(10L);
	}

	@Test
	public void testDataTotalProcessZero()
	{
		testProcess(0L);
	}

	@Test
	public void testDataTotalProcessMax()
	{
		testProcess(Long.MAX_VALUE / 5L);
	}

}
