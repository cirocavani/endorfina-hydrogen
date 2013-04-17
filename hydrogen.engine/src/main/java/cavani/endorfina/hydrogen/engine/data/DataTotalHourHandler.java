package cavani.endorfina.hydrogen.engine.data;

import java.util.Map;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.engine.data.DataTotalHourEngine.Data;
import cavani.endorfina.hydrogen.persistence.api.PersistorAdapter;
import cavani.endorfina.hydrogen.persistence.api.PersistorOperation;

public class DataTotalHourHandler implements Handler<Message<JsonObject>>
{

	private final EventBus eventBus;

	private final String persistorAddress;

	private final PersistorAdapter persistorAdapter;

	private final DataTotalHourEngine engine = new DataTotalHourEngine();

	public DataTotalHourHandler(final EventBus eventBus, final String persistorAddress, final PersistorAdapter persistorAdapter)
	{
		this.eventBus = eventBus;
		this.persistorAddress = persistorAddress;
		this.persistorAdapter = persistorAdapter;
	}

	@Override
	public void handle(final Message<JsonObject> message)
	{
		final Data data = parseMessage(message.body);

		final PersistorOperation<Map<String, Object>> op = persistorAdapter.fetchHour(data.userId);
		eventBus.send(persistorAddress, op.request(), new Handler<Message<JsonObject>>()
		{

			@Override
			public void handle(final Message<JsonObject> event)
			{
				final Map<String, Object> values = op.result(event.body);
				final JsonObject result = processResult(values);
				message.reply(result);
			}

		});
	}

	protected Data parseMessage(final JsonObject message)
	{
		return engine.parseMessage(message);
	}

	protected JsonObject processResult(final Map<String, Object> values)
	{
		return engine.processResult(values);
	}

}
