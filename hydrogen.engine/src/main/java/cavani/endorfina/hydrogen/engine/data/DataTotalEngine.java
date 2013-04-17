package cavani.endorfina.hydrogen.engine.data;

import static cavani.endorfina.hydrogen.engine.util.Constants.PARAM_ID;

import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.engine.data.DataTotalEngine.Data;
import cavani.endorfina.hydrogen.engine.handler.HandlerEngine;

public class DataTotalEngine implements HandlerEngine<Data, Long[]>
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
	public JsonObject processResult(final Long[] values)
	{
		long total = 0;
		for (final long v : values)
		{
			total += v;
		}
		return new JsonObject().putNumber("result", total);
	}

}
