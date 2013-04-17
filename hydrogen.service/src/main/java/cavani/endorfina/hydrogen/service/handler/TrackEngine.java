package cavani.endorfina.hydrogen.service.handler;

import static cavani.endorfina.hydrogen.service.util.Constants.PARAM_ID;
import static cavani.endorfina.hydrogen.service.util.Constants.PARAM_ITEM;

import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.service.handler.TrackEngine.Data;

public class TrackEngine implements HandlerEngine<Data, Long[]>
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
	public JsonObject processResult(final Long[] values)
	{
		Long total = 0L;
		for (final Long v : values)
		{
			total += v;
		}

		final JsonObject result = new JsonObject();
		int pt = 0;
		for (int i = 0; i < values.length - 1; i++)
		{
			final Long p = total == 0 ? 0L : 100 * values[i] / total;
			result.putNumber(String.valueOf(i + 1), p);
			pt += p;
		}
		result.putNumber(String.valueOf(values.length), total == 0 || pt >= 100 ? 0L : 100L - pt);

		return result;
	}

}
