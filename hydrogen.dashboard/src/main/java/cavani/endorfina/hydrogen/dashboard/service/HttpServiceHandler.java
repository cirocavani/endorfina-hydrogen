package cavani.endorfina.hydrogen.dashboard.service;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.HttpServerResponse;
import org.vertx.java.core.json.JsonObject;

public class HttpServiceHandler implements Handler<HttpServerRequest>
{

	private final EventBus eventBus;

	private final String serviceAddress;

	private final ServiceAdapter serviceAdapter;

	public HttpServiceHandler(final EventBus eventBus, final String serviceAddress, final ServiceAdapter serviceAdapter)
	{
		this.eventBus = eventBus;
		this.serviceAddress = serviceAddress;
		this.serviceAdapter = serviceAdapter;
	}

	@Override
	public void handle(final HttpServerRequest req)
	{
		final JsonObject message = createServiceMessage(req);
		invokeService(req.response, message);
	}

	protected void invokeService(final HttpServerResponse out, final JsonObject message)
	{
		eventBus.send(serviceAddress, message, new Handler<Message<JsonObject>>()
		{

			@Override
			public void handle(final Message<JsonObject> event)
			{
				final JsonObject result = event.body;
				final String response = processServiceResult(result);
				out.end(response);
			}

		});
	}

	protected JsonObject createServiceMessage(final HttpServerRequest req)
	{
		return serviceAdapter.message(req.params());
	}

	protected String processServiceResult(final JsonObject result)
	{
		return serviceAdapter.result(result);
	}

}
