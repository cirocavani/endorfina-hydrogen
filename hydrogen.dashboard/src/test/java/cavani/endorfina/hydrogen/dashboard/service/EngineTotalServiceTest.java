package cavani.endorfina.hydrogen.dashboard.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.dashboard.service.EngineTotalService.Data;

public class EngineTotalServiceTest
{

	@Test
	public void testEngineTotalParameters()
	{
		final List<String> values = new ArrayList<>();
		values.add("userId");

		final EngineTotalService service = new EngineTotalService();
		final List<String> result = service.parameters();

		Assert.assertNotNull(result);
		Assert.assertEquals(values.size(), result.size());
		Assert.assertEquals(values, result);
	}

	@Test
	public void testEngineTotalParseData()
	{
		final String userId = "1234";

		final Map<String, String> values = new LinkedHashMap<>();
		values.put("userId", userId);

		final EngineTotalService service = new EngineTotalService();
		final Data result = service.parseData(values);

		Assert.assertNotNull(result);
		Assert.assertEquals(userId, result.userId);
	}

	@Test
	public void testEngineTotalCreateMessage()
	{
		final Data value = new Data("1234");

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"userId\":\"").append(value.userId).append("\"}");

		final EngineTotalService service = new EngineTotalService();
		final JsonObject result = service.createMessage(value);

		Assert.assertNotNull(result);
		Assert.assertEquals(expected.toString(), result.encode());
	}

	@Test
	public void testEngineTotalMessage()
	{
		final String userId = "1234";

		final Map<String, String> values = new LinkedHashMap<>();
		values.put("userId", userId);

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"userId\":\"").append(userId).append("\"}");

		final EngineTotalService service = new EngineTotalService();
		final JsonObject result = service.message(values);

		Assert.assertNotNull(result);
		Assert.assertEquals(expected.toString(), result.encode());
	}

	@Test
	public void testEngineTotalResult()
	{
		final Long _value = 321L;

		final JsonObject value = new JsonObject();
		value.putNumber("result", _value);

		final EngineTotalService service = new EngineTotalService();
		final String result = service.result(value);

		Assert.assertNotNull(result);
		Assert.assertEquals(_value.toString(), result);
	}

	@Test
	public void testEngineTotalResultMax()
	{
		final Long _value = Long.MAX_VALUE;

		final JsonObject value = new JsonObject();
		value.putNumber("result", _value);

		final EngineTotalService service = new EngineTotalService();
		final String result = service.result(value);

		Assert.assertNotNull(result);
		Assert.assertEquals(_value.toString(), result);
	}

}
