package cavani.endorfina.hydrogen.web.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.web.service.TrackService.Data;

public class TrackServiceTest
{

	@Test
	public void testTrackParameters()
	{
		final List<String> values = new ArrayList<>();
		values.add("userId");
		values.add("item");

		final TrackService service = new TrackService();
		final List<String> result = service.parameters();

		Assert.assertNotNull(result);
		Assert.assertEquals(values.size(), result.size());
		Assert.assertEquals(values, result);
	}

	@Test
	public void testTrackParseData()
	{
		final String userId = "1234";
		final String item = "5";

		final Map<String, String> values = new LinkedHashMap<>();
		values.put("userId", userId);
		values.put("item", item);

		final TrackService service = new TrackService();
		final Data result = service.parseData(values);

		Assert.assertNotNull(result);
		Assert.assertEquals(userId, result.userId);
		Assert.assertEquals(item, result.item);
	}

	@Test
	public void testTrackCreateMessage()
	{
		final Data value = new Data("1234", "5");

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"userId\":\"").append(value.userId).append("\",");
		expected.append("\"item\":\"").append(value.item).append("\"}");

		final TrackService service = new TrackService();
		final JsonObject result = service.createMessage(value);

		Assert.assertNotNull(result);
		Assert.assertEquals(expected.toString(), result.encode());
	}

	@Test
	public void testTrackMessage()
	{
		final String userId = "1234";
		final String item = "5";

		final Map<String, String> values = new LinkedHashMap<>();
		values.put("userId", userId);
		values.put("item", item);

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"userId\":\"").append(userId).append("\",");
		expected.append("\"item\":\"").append(item).append("\"}");

		final TrackService service = new TrackService();
		final JsonObject result = service.message(values);

		Assert.assertNotNull(result);
		Assert.assertEquals(expected.toString(), result.encode());
	}

	@Test
	public void testTrackResult()
	{
		final String value1 = "321";
		final String value2 = "654";
		final String value3 = "987";

		final JsonObject value = new JsonObject();
		value.putString("value1", value1);
		value.putString("value2", value2);
		value.putString("value3", value3);

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"value1\":\"").append(value1).append("\",");
		expected.append("\"value2\":\"").append(value2).append("\",");
		expected.append("\"value3\":\"").append(value3).append("\"}");

		final TrackService service = new TrackService();
		final String result = service.result(value);

		Assert.assertNotNull(result);
		Assert.assertEquals(expected.toString(), result);
	}

}
