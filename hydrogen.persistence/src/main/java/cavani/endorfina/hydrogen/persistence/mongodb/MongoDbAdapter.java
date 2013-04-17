package cavani.endorfina.hydrogen.persistence.mongodb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.persistence.api.PersistorAdapter;
import cavani.endorfina.hydrogen.persistence.api.PersistorOperation;

public class MongoDbAdapter implements PersistorAdapter
{

	public static final String COLLECTION_NAME = "compounds";

	public static final String FIELD_USER_ID = "_id";

	public static final String FIELD_ITEM1 = "1";

	public static final String FIELD_ITEM2 = "2";

	private final DateFormat dateFormat = new SimpleDateFormat("YMMddHH");

	@Override
	public JsonObject createUser(final String userId, final Date start, final Date end)
	{
		final JsonObject data = new JsonObject();
		data.putString(FIELD_USER_ID, userId);
		data.putNumber(FIELD_ITEM1, 0);
		data.putNumber(FIELD_ITEM2, 0);

		final Calendar c = Calendar.getInstance();
		c.setTime(start);

		final String last = dateFormat.format(end);
		String hour = dateFormat.format(c.getTime());
		while (hour.compareTo(last) < 1)
		{
			data.putNumber(hour, 0);
			c.add(Calendar.HOUR_OF_DAY, 1);
			hour = dateFormat.format(c.getTime());
		}

		final JsonObject create = new JsonObject();
		create.putString("action", "save");
		create.putString("collection", COLLECTION_NAME);
		create.putObject("document", data);

		return create;
	}

	@Override
	public JsonObject updateCounter(final String userId, final String item, final Date timestamp)
	{
		final String hour = dateFormat.format(timestamp);

		final JsonObject update = new JsonObject();
		update.putString("action", "update");
		update.putString("collection", COLLECTION_NAME);
		update.putObject("criteria", new JsonObject().putString(FIELD_USER_ID, userId));
		final JsonObject data = new JsonObject();
		data.putNumber(item, 1);
		data.putNumber(hour, 1);
		update.putObject("objNew", new JsonObject().putObject("$inc", data));
		update.putBoolean("upsert", false);
		update.putBoolean("multi", false);

		return update;
	}

	@Override
	public JsonObject cleanup()
	{
		final JsonObject deleteAll = new JsonObject();
		deleteAll.putString("action", "delete");
		deleteAll.putString("collection", COLLECTION_NAME);
		deleteAll.putObject("matcher", new JsonObject());
		return deleteAll;
	}

	@Override
	public PersistorOperation<Long[]> fetchCounter(final String userId)
	{
		return new PersistorOperation<Long[]>()
		{

			@Override
			public JsonObject request()
			{
				final JsonObject fetch = new JsonObject();
				fetch.putString("action", "findone");
				fetch.putString("collection", COLLECTION_NAME);
				fetch.putObject("matcher", new JsonObject().putString(FIELD_USER_ID, userId));
				fetch.putObject("keys", new JsonObject().putNumber(FIELD_ITEM1, 1).putNumber(FIELD_ITEM2, 1).putNumber(FIELD_USER_ID, 0));
				return fetch;
			}

			@Override
			public Long[] result(final JsonObject result)
			{
				if (!"ok".equals(result.getString("status")))
				{
					return new Long[0];
				}

				final JsonObject _result = result.getObject("result");

				return new Long[]
				{
					_result.getLong(FIELD_ITEM1),
					_result.getLong(FIELD_ITEM2)
				};
			}

		};
	}

	@Override
	public PersistorOperation<Map<String, Object>> fetchHour(final String userId)
	{
		return new PersistorOperation<Map<String, Object>>()
		{

			@Override
			public JsonObject request()
			{
				final JsonObject fetch = new JsonObject();
				fetch.putString("action", "findone");
				fetch.putString("collection", COLLECTION_NAME);
				fetch.putObject("matcher", new JsonObject().putString(FIELD_USER_ID, userId));
				fetch.putObject("keys", new JsonObject().putNumber(FIELD_ITEM1, 0).putNumber(FIELD_ITEM2, 0).putNumber(FIELD_USER_ID, 0));
				return fetch;
			}

			@Override
			public Map<String, Object> result(final JsonObject result)
			{
				if (!"ok".equals(result.getString("status")))
				{
					return Collections.emptyMap();
				}

				return result.getObject("result").toMap();
			}

		};
	}

	@Override
	public PersistorOperation<Long> fetchCounterItem(final String userId, final String item)
	{
		return new PersistorOperation<Long>()
		{

			@Override
			public JsonObject request()
			{
				final JsonObject fetch = new JsonObject();
				fetch.putString("action", "findone");
				fetch.putString("collection", COLLECTION_NAME);
				fetch.putObject("matcher", new JsonObject().putString(FIELD_USER_ID, userId));
				fetch.putObject("keys", new JsonObject().putNumber(item, 1).putNumber(FIELD_USER_ID, 0));
				return fetch;
			}

			@Override
			public Long result(final JsonObject result)
			{
				if (!"ok".equals(result.getString("status")))
				{
					return 0L;
				}

				final JsonObject _result = result.getObject("result");
				return _result.getLong(item);
			}

		};
	}
}
