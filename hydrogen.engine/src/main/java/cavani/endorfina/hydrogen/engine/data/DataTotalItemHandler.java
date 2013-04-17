package cavani.endorfina.hydrogen.engine.data;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.engine.data.DataTotalItemEngine.Data;
import cavani.endorfina.hydrogen.persistence.api.PersistorAdapter;
import cavani.endorfina.hydrogen.persistence.api.PersistorOperation;

public class DataTotalItemHandler implements Handler<Message<JsonObject>>
{

	private final EventBus eventBus;

	private final String persistorAddress;

	private final PersistorAdapter persistorAdapter;

	private final DataTotalItemEngine engine = new DataTotalItemEngine();

	public DataTotalItemHandler(final EventBus eventBus, final String persistorAddress, final PersistorAdapter persistorAdapter)
	{
		this.eventBus = eventBus;
		this.persistorAddress = persistorAddress;
		this.persistorAdapter = persistorAdapter;
	}

	@Override
	public void handle(final Message<JsonObject> message)
	{
		final Data data = parseMessage(message.body);

		final PersistorOperation<Long> op = persistorAdapter.fetchCounterItem(data.userId, data.item);
		eventBus.send(persistorAddress, op.request(), new Handler<Message<JsonObject>>()
		{

			@Override
			public void handle(final Message<JsonObject> event)
			{
				final long value = op.result(event.body);
				final JsonObject result = processResult(value);
				message.reply(result);
			}

		});
	}

	protected Data parseMessage(final JsonObject message)
	{
		return engine.parseMessage(message);
	}

	protected JsonObject processResult(final Long value)
	{
		return engine.processResult(value);
	}

}
