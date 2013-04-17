package cavani.endorfina.hydrogen.engine.data;

import org.junit.Assert;
import org.junit.Test;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.engine.data.DataTotalItemEngine.Data;

public class DataTotalItemEngineTest
{

	@Test
	public void testDataTotalItemParse()
	{
		final String userId = "456";
		final String item = "5";

		final JsonObject message = new JsonObject();
		message.putString("userId", userId);
		message.putString("item", item);

		final DataTotalItemEngine engine = new DataTotalItemEngine();
		final Data result = engine.parseMessage(message);

		Assert.assertNotNull(result);
		Assert.assertEquals(userId, result.userId);
		Assert.assertEquals(item, result.item);
	}

	private void testProcess(final Long value)
	{
		final StringBuilder expected = new StringBuilder();
		expected.append("{\"result\":").append(value).append("}");

		final DataTotalItemEngine engine = new DataTotalItemEngine();
		final JsonObject result = engine.processResult(value);

		Assert.assertNotNull(result);
		Assert.assertEquals(expected.toString(), result.encode());
	}

	@Test
	public void testDataTotalItemProcess()
	{
		testProcess(10L);
	}

	@Test
	public void testDataTotalItemProcessZero()
	{
		testProcess(0L);
	}

	@Test
	public void testDataTotalItemProcessMax()
	{
		testProcess(Long.MAX_VALUE);
	}

}
