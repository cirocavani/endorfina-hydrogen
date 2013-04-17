package cavani.endorfina.hydrogen.engine.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.engine.data.DataTotalHourEngine.Data;

public class DataTotalHourEngineTest
{

	@Test
	public void testDataTotalHourParse()
	{
		final String userId = "456";

		final JsonObject message = new JsonObject();
		message.putString("userId", userId);

		final DataTotalHourEngine engine = new DataTotalHourEngine();
		final Data result = engine.parseMessage(message);

		Assert.assertNotNull(result);
		Assert.assertEquals(userId, result.userId);
	}

	private void testProcess(final Long value)
	{
		final Map<String, Object> values = new LinkedHashMap<>();
		values.put("h1", value);
		values.put("h2", value);
		values.put("h3", value);
		values.put("h4", value);
		values.put("h5", value);

		final StringBuilder expected = new StringBuilder();
		expected.append("{");
		boolean first = true;
		for (final Entry<String, Object> entry : values.entrySet())
		{
			if (first)
			{
				first = false;
			}
			else
			{
				expected.append(",");
			}
			expected.append("\"").append(entry.getKey()).append("\":").append(value);
		}
		expected.append("}");

		final DataTotalHourEngine engine = new DataTotalHourEngine();
		final JsonObject result = engine.processResult(values);

		Assert.assertNotNull(result);
		Assert.assertEquals(expected.toString(), result.encode());
	}

	@Test
	public void testDataTotalHourProcess()
	{
		testProcess(10L);
	}

	@Test
	public void testDataTotalHourProcessZero()
	{
		testProcess(0L);
	}

	@Test
	public void testDataTotalHourProcessMax()
	{
		testProcess(Long.MAX_VALUE);
	}

}
