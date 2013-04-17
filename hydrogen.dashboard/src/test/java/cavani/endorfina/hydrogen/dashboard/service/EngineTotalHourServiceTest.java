package cavani.endorfina.hydrogen.dashboard.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.dashboard.service.EngineTotalHourService.Data;

public class EngineTotalHourServiceTest
{

	@Test
	public void testEngineTotalHourParameters()
	{
		final List<String> values = new ArrayList<>();
		values.add("userId");

		final EngineTotalHourService service = new EngineTotalHourService();
		final List<String> result = service.parameters();

		Assert.assertNotNull(result);
		Assert.assertEquals(values.size(), result.size());
		Assert.assertEquals(values, result);
	}

	@Test
	public void testEngineTotalHourParseData()
	{
		final String userId = "1234";

		final Map<String, String> values = new LinkedHashMap<>();
		values.put("userId", userId);

		final EngineTotalHourService service = new EngineTotalHourService();
		final Data result = service.parseData(values);

		Assert.assertNotNull(result);
		Assert.assertEquals(userId, result.userId);
	}

	@Test
	public void testEngineTotalHourCreateMessage()
	{
		final Data value = new Data("1234");

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"userId\":\"").append(value.userId).append("\"}");

		final EngineTotalHourService service = new EngineTotalHourService();
		final JsonObject result = service.createMessage(value);

		Assert.assertNotNull(result);
		Assert.assertEquals(expected.toString(), result.encode());
	}

	@Test
	public void testEngineTotalHourMessage()
	{
		final String userId = "1234";

		final Map<String, String> values = new LinkedHashMap<>();
		values.put("userId", userId);

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"userId\":\"").append(userId).append("\"}");

		final EngineTotalHourService service = new EngineTotalHourService();
		final JsonObject result = service.message(values);

		Assert.assertNotNull(result);
		Assert.assertEquals(expected.toString(), result.encode());
	}

	@Test
	public void testEngineTotalHourResult()
	{
		final Long value1 = 321L;
		final Long value2 = Long.MAX_VALUE;
		final Long value3 = Long.MIN_VALUE;

		final JsonObject value = new JsonObject();
		value.putNumber("value1", value1);
		value.putNumber("value2", value2);
		value.putNumber("value3", value3);

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"value1\":").append(value1).append(",");
		expected.append("\"value2\":").append(value2).append(",");
		expected.append("\"value3\":").append(value3).append("}");

		final EngineTotalHourService service = new EngineTotalHourService();
		final String result = service.result(value);

		Assert.assertNotNull(result);
		Assert.assertEquals(expected.toString(), result);
	}

}
