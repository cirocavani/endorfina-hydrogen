package cavani.endorfina.hydrogen.server.service;

import org.vertx.java.core.eventbus.EventBus;

public class HttpService
{

	private final String path;

	private final String address;

	private final ServiceAdapter adapter;

	public HttpService(final String path, final String address, final ServiceAdapter adapter)
	{
		this.path = path;
		this.address = address;
		this.adapter = adapter;
	}

	public String url()
	{
		String url = path;
		for (final String parameter : adapter.parameters())
		{
			url += "/:" + parameter;
		}
		return url;
	}

	public HttpServiceHandler createHandler(final EventBus eventBus)
	{
		return new HttpServiceHandler(eventBus, address, adapter);
	}

}
