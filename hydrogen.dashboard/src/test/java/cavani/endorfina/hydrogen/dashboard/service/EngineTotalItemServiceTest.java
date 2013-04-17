package cavani.endorfina.hydrogen.dashboard.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.dashboard.service.EngineTotalItemService.Data;

public class EngineTotalItemServiceTest
{

	@Test
	public void testEngineTotalItemParameters()
	{
		final List<String> values = new ArrayList<>();
		values.add("userId");
		values.add("item");

		final EngineTotalItemService service = new EngineTotalItemService();
		final List<String> result = service.parameters();

		Assert.assertNotNull(result);
		Assert.assertEquals(values.size(), result.size());
		Assert.assertEquals(values, result);
	}

	@Test
	public void testEngineTotalItemParseData()
	{
		final String userId = "1234";
		final String item = "5";

		final Map<String, String> values = new LinkedHashMap<>();
		values.put("userId", userId);
		values.put("item", item);

		final EngineTotalItemService service = new EngineTotalItemService();
		final Data result = service.parseData(values);

		Assert.assertNotNull(result);
		Assert.assertEquals(userId, result.userId);
		Assert.assertEquals(item, result.item);
	}

	@Test
	public void testEngineTotalItemCreateMessage()
	{
		final Data value = new Data("1234", "5");

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"userId\":\"").append(value.userId).append("\",");
		expected.append("\"item\":\"").append(value.item).append("\"}");

		final EngineTotalItemService service = new EngineTotalItemService();
		final JsonObject result = service.createMessage(value);

		Assert.assertNotNull(result);
		Assert.assertEquals(expected.toString(), result.encode());
	}

	@Test
	public void testEngineTotalItemMessage()
	{
		final String userId = "1234";
		final String item = "5";

		final Map<String, String> values = new LinkedHashMap<>();
		values.put("userId", userId);
		values.put("item", item);

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"userId\":\"").append(userId).append("\",");
		expected.append("\"item\":\"").append(item).append("\"}");

		final EngineTotalItemService service = new EngineTotalItemService();
		final JsonObject result = service.message(values);

		Assert.assertNotNull(result);
		Assert.assertEquals(expected.toString(), result.encode());
	}

	@Test
	public void testEngineTotalItemResult()
	{
		final Long _value = 321L;

		final JsonObject value = new JsonObject();
		value.putNumber("result", _value);

		final EngineTotalItemService service = new EngineTotalItemService();
		final String result = service.result(value);

		Assert.assertNotNull(result);
		Assert.assertEquals(_value.toString(), result);
	}

	@Test
	public void testEngineTotalItemResultMax()
	{
		final Long _value = Long.MAX_VALUE;

		final JsonObject value = new JsonObject();
		value.putNumber("result", _value);

		final EngineTotalItemService service = new EngineTotalItemService();
		final String result = service.result(value);

		Assert.assertNotNull(result);
		Assert.assertEquals(_value.toString(), result);
	}

}
