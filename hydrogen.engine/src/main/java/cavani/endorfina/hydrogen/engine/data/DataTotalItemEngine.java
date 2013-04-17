package cavani.endorfina.hydrogen.engine.data;

import static cavani.endorfina.hydrogen.engine.util.Constants.PARAM_ID;
import static cavani.endorfina.hydrogen.engine.util.Constants.PARAM_ITEM;

import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.engine.data.DataTotalItemEngine.Data;
import cavani.endorfina.hydrogen.engine.handler.HandlerEngine;

public class DataTotalItemEngine implements HandlerEngine<Data, Long>
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

	@Override
	public Data parseMessage(final JsonObject message)
	{
		final String userId = message.getString(PARAM_ID);
		final String item = message.getString(PARAM_ITEM);
		return new Data(userId, item);
	}

	@Override
	public JsonObject processResult(final Long value)
	{
		return new JsonObject().putNumber("result", value);
	}

}
