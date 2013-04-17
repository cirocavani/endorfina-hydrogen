package cavani.endorfina.hydrogen.service.handler;

import java.util.Date;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.persistence.api.PersistorAdapter;
import cavani.endorfina.hydrogen.persistence.api.PersistorOperation;
import cavani.endorfina.hydrogen.service.handler.TrackEngine.Data;


public class TrackHandler implements Handler<Message<JsonObject>>
{

	private final EventBus eventBus;

	private final String storageAddress;

	private final String sourceAddress;

	private final PersistorAdapter persistorAdapter;

	private final TrackEngine engine = new TrackEngine();

	public TrackHandler(final EventBus eventBus, final String storageAddress, final String sourceAddress, final PersistorAdapter persistorAdapter)
	{
		this.eventBus = eventBus;
		this.storageAddress = storageAddress;
		this.sourceAddress = sourceAddress;
		this.persistorAdapter = persistorAdapter;
	}

	@Override
	public void handle(final Message<JsonObject> message)
	{
		final Data data = parseMessage(message.body);
		reply(data, message);
		persist(data);
	}

	protected void reply(final Data data, final Message<JsonObject> message)
	{
		final PersistorOperation<Long[]> op = persistorAdapter.fetchCounter(data.userId);
		eventBus.send(sourceAddress, op.request(), new Handler<Message<JsonObject>>()
		{

			@Override
			public void handle(final Message<JsonObject> response)
			{
				final Long[] values = op.result(response.body);
				final JsonObject result = processResult(values);
				message.reply(result);
			}

		});
	}

	protected void persist(final Data data)
	{
		eventBus.send(storageAddress, persistorAdapter.updateCounter(data.userId, data.item, new Date()));
	}

	protected Data parseMessage(final JsonObject message)
	{
		return engine.parseMessage(message);
	}

	protected JsonObject processResult(final Long[] values)
	{
		return engine.processResult(values);
	}

}
