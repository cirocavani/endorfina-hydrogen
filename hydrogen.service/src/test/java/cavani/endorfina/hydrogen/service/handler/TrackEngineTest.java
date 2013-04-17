package cavani.endorfina.hydrogen.service.handler;

import org.junit.Assert;
import org.junit.Test;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.service.handler.TrackEngine.Data;

public class TrackEngineTest
{

	@Test
	public void testTrackEngineParse()
	{
		final String userId = "456";
		final String item = "5";

		final JsonObject message = new JsonObject();
		message.putString("userId", userId);
		message.putString("item", item);

		final TrackEngine engine = new TrackEngine();
		final Data result = engine.parseMessage(message);

		Assert.assertNotNull(result);
		Assert.assertEquals(userId, result.userId);
		Assert.assertEquals(item, result.item);
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

		final Long total = value * values.length;
		final Long p = total == 0 ? 0 : 100L * value / total;

		final StringBuilder expected = new StringBuilder();
		expected.append("{");
		for (int i = 0; i < values.length; ++i)
		{
			if (i != 0)
			{
				expected.append(",");
			}
			expected.append("\"").append(i + 1).append("\":").append(p);
		}
		expected.append("}");

		final TrackEngine engine = new TrackEngine();
		final JsonObject result = engine.processResult(values);

		Assert.assertNotNull(result);
		Assert.assertEquals(expected.toString(), result.encode());
	}

	@Test
	public void testTrackEngineProcess()
	{
		testProcess(10L);
	}

	@Test
	public void testTrackEngineProcessZero()
	{
		testProcess(0L);
	}

	@Test
	public void testTrackEngineProcessMax()
	{
		testProcess(Long.MAX_VALUE / 100L);
	}

}
