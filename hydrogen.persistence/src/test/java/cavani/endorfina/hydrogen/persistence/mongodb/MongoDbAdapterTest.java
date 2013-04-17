package cavani.endorfina.hydrogen.persistence.mongodb;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.persistence.api.PersistorAdapter;
import cavani.endorfina.hydrogen.persistence.api.PersistorOperation;

public class MongoDbAdapterTest
{

	@Test
	public void testCreate()
	{
		final String id = "456";
		final Calendar c = new GregorianCalendar(2013, Calendar.APRIL, 11);
		c.set(Calendar.HOUR_OF_DAY, 22);
		final Date start = c.getTime();
		c.add(Calendar.HOUR_OF_DAY, 3);
		final Date end = c.getTime();

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"_id\":\"").append(id).append("\",");
		expected.append("\"1\":").append(0).append(",");
		expected.append("\"2\":").append(0).append(",");
		expected.append("\"2013041122\":").append(0).append(",");
		expected.append("\"2013041123\":").append(0).append(",");
		expected.append("\"2013041200\":").append(0).append(",");
		expected.append("\"2013041201\":").append(0).append("}");

		final PersistorAdapter adapter = new MongoDbAdapter();
		final JsonObject result = adapter.createUser(id, start, end);

		Assert.assertEquals("save", result.getString("action"));
		Assert.assertEquals("compounds", result.getString("collection"));
		Assert.assertEquals(expected.toString(), result.getObject("document").encode());
	}

	@Test
	public void testUpdate()
	{
		final String id = "456";
		final String item = "1";

		final Calendar c = new GregorianCalendar(2013, Calendar.APRIL, 11);
		c.set(Calendar.HOUR_OF_DAY, 22);
		final Date timestamp = c.getTime();

		final PersistorAdapter adapter = new MongoDbAdapter();
		final JsonObject result = adapter.updateCounter(id, item, timestamp);

		final StringBuilder criteriaExpected = new StringBuilder();
		criteriaExpected.append("{\"_id\":\"").append(id).append("\"}");

		final StringBuilder updateExpected = new StringBuilder();
		updateExpected.append("{\"$inc\":{");
		updateExpected.append("\"").append(item).append("\":1,");
		updateExpected.append("\"2013041122\":1");
		updateExpected.append("}}");

		Assert.assertEquals("update", result.getString("action"));
		Assert.assertEquals("compounds", result.getString("collection"));
		Assert.assertEquals(criteriaExpected.toString(), result.getObject("criteria").encode());
		Assert.assertEquals(updateExpected.toString(), result.getObject("objNew").encode());
	}

	@Test
	public void testFecthCounterRequest()
	{
		final String id = "465";

		final PersistorAdapter adapter = new MongoDbAdapter();
		final PersistorOperation<Long[]> op = adapter.fetchCounter(id);

		final JsonObject request = op.request();

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"_id\":\"").append(id).append("\"}");

		Assert.assertNotNull(request);
		Assert.assertEquals("findone", request.getString("action"));
		Assert.assertEquals("compounds", request.getString("collection"));
		Assert.assertEquals(expected.toString(), request.getObject("matcher").encode());
	}

	@Test
	public void testFecthCounterResult()
	{
		final String id = "465";
		final Long _1 = 10L;
		final Long _2 = 17L;

		final PersistorAdapter adapter = new MongoDbAdapter();
		final PersistorOperation<Long[]> op = adapter.fetchCounter(id);

		final JsonObject data = new JsonObject();
		data.putString("status", "ok");
		data.putObject("result", new JsonObject().putNumber("1", _1).putNumber("2", _2));

		final Long[] result = op.result(data);

		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.length);
		Assert.assertEquals(_1, result[0]);
		Assert.assertEquals(_2, result[1]);
	}

	@Test
	public void testFecthHourRequest()
	{
		final String id = "465";

		final PersistorAdapter adapter = new MongoDbAdapter();
		final PersistorOperation<Map<String, Object>> op = adapter.fetchHour(id);

		final JsonObject request = op.request();

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"_id\":\"").append(id).append("\"}");

		Assert.assertNotNull(request);
		Assert.assertEquals("findone", request.getString("action"));
		Assert.assertEquals("compounds", request.getString("collection"));
		Assert.assertEquals(expected.toString(), request.getObject("matcher").encode());
	}

	@Test
	public void testFecthHourResult()
	{
		final String id = "465";
		final String h1 = "2013041122";
		final Long v1 = 321L;
		final String h2 = "2013041123";
		final Long v2 = 654L;
		final String h3 = "2013041120";
		final Long v3 = 987L;

		final PersistorAdapter adapter = new MongoDbAdapter();
		final PersistorOperation<Map<String, Object>> op = adapter.fetchHour(id);

		final JsonObject data = new JsonObject();
		data.putString("status", "ok");
		final JsonObject hour = new JsonObject();
		hour.putNumber(h1, v1);
		hour.putNumber(h2, v2);
		hour.putNumber(h3, v3);
		data.putObject("result", hour);

		final Map<String, Object> result = op.result(data);

		Assert.assertNotNull(result);
		Assert.assertEquals(3, result.size());
		Assert.assertEquals(v1, result.get(h1));
		Assert.assertEquals(v2, result.get(h2));
		Assert.assertEquals(v3, result.get(h3));
	}

	@Test
	public void testFecthCounterItemRequest()
	{
		final String id = "465";

		final PersistorAdapter adapter = new MongoDbAdapter();
		final PersistorOperation<Long> op = adapter.fetchCounterItem(id, "1");

		final JsonObject request = op.request();

		final StringBuilder expected = new StringBuilder();
		expected.append("{\"_id\":\"").append(id).append("\"}");

		Assert.assertNotNull(request);
		Assert.assertEquals("findone", request.getString("action"));
		Assert.assertEquals("compounds", request.getString("collection"));
		Assert.assertEquals(expected.toString(), request.getObject("matcher").encode());
	}

	@Test
	public void testFecthCounterItemResult()
	{
		final String id = "465";
		final String item = "1";
		final Long v = 248L;

		final PersistorAdapter adapter = new MongoDbAdapter();
		final PersistorOperation<Long> op = adapter.fetchCounterItem(id, item);

		final JsonObject data = new JsonObject();
		data.putString("status", "ok");
		data.putObject("result", new JsonObject().putNumber(item, v));

		final Long result = op.result(data);

		Assert.assertNotNull(result);
		Assert.assertEquals(v, result);
	}

}
