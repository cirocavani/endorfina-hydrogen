package cavani.endorfina.hydrogen.engine.data;

import static cavani.endorfina.hydrogen.engine.util.Constants.PARAM_ID;

import java.util.Map;

import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.engine.data.DataTotalHourEngine.Data;
import cavani.endorfina.hydrogen.engine.handler.HandlerEngine;

public class DataTotalHourEngine implements HandlerEngine<Data, Map<String, Object>>
{

	public static class Data
	{

		public final String userId;

		public Data(final String userId)
		{
			this.userId = userId;
		}

	}

	@Override
	public Data parseMessage(final JsonObject message)
	{
		final String userId = message.getString(PARAM_ID);
		return new Data(userId);
	}

	@Override
	public JsonObject processResult(final Map<String, Object> values)
	{
		return new JsonObject(values);
	}

}
