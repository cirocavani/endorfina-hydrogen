package cavani.endorfina.hydrogen.dashboard.service;

import static cavani.endorfina.hydrogen.dashboard.util.Constants.PARAM_ID;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.network.http.ServiceAdapter;

public class EngineTotalHourService implements ServiceAdapter
{

	public static class Data
	{

		public final String userId;

		public Data(final String userId)
		{
			this.userId = userId;
		}

	}

	public Data parseData(final Map<String, String> values)
	{
		final String userId = values.get(PARAM_ID);
		return new Data(userId);
	}

	public JsonObject createMessage(final Data data)
	{
		final JsonObject message = new JsonObject();
		message.putString(PARAM_ID, data.userId);
		return message;
	}

	@Override
	public List<String> parameters()
	{
		return Arrays.asList(PARAM_ID);
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
		return result.encode();
	}

}
