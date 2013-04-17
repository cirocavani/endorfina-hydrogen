package cavani.endorfina.hydrogen.dashboard.service;

import static cavani.endorfina.hydrogen.dashboard.util.Constants.PARAM_ID;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.PARAM_ITEM;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.vertx.java.core.json.JsonObject;

public class EngineTotalItemService implements ServiceAdapter
{

	public static class Data
	{

		public final String userId;

		public final String item;

		public Data(final String userId, final String item)
		{
			this.userId = userId;
			this.item = item;
		}

	}

	public Data parseData(final Map<String, String> values)
	{
		final String userId = values.get(PARAM_ID);
		final String item = values.get(PARAM_ITEM);
		return new Data(userId, item);
	}

	@Override
	public List<String> parameters()
	{
		return Arrays.asList(PARAM_ID, PARAM_ITEM);
	}

	public JsonObject createMessage(final Data data)
	{
		final JsonObject message = new JsonObject();
		message.putString(PARAM_ID, data.userId);
		message.putString(PARAM_ITEM, data.item);
		return message;
	}

	@Override
	public JsonObject message(final Map<String, String> values)
	{
		final Data data = parseData(values);
		return createMessage(data);
	}

	@Override
	public String result(final JsonObject result)
	{
		final long total = result.getLong("result");
		return String.valueOf(total);
	}

}
