package cavani.endorfina.hydrogen.dashboard.network;

import java.util.Arrays;
import java.util.List;

import cavani.endorfina.hydrogen.dashboard.service.ServiceFactory;
import cavani.endorfina.hydrogen.network.http.HttpService;

public class HttpServer extends cavani.endorfina.hydrogen.network.http.HttpServer
{

	@Override
	protected List<HttpService> services()
	{
		return Arrays.asList(new HttpService[]
		{
			ServiceFactory.EngineDataTotal(),
			ServiceFactory.EngineDataTotalHour(),
			ServiceFactory.EngineDataTotalItem(),
		});
	}

}
