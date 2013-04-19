package cavani.endorfina.hydrogen.server.network;

import java.util.Arrays;
import java.util.List;

import cavani.endorfina.hydrogen.network.http.HttpService;
import cavani.endorfina.hydrogen.server.service.ServiceFactory;

public class HttpServer extends cavani.endorfina.hydrogen.network.http.HttpServer
{

	@Override
	protected List<HttpService> services()
	{
		return Arrays.asList(new HttpService[]
		{
			ServiceFactory.TrackService(),
		});
	}

}
