package cavani.endorfina.hydrogen.server;

import static cavani.endorfina.hydrogen.server.util.Constants.CONFIG_SERVICE_CONF;
import static cavani.endorfina.hydrogen.server.util.Constants.CONFIG_SERVICE_MODULE;

import java.util.Arrays;
import java.util.List;

import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.network.http.HttpServer;
import cavani.endorfina.hydrogen.network.http.HttpService;
import cavani.endorfina.hydrogen.server.service.ServiceFactory;
import cavani.endorfina.hydrogen.server.util.Constants;

public class Server extends HttpServer
{

	private static final String DEFAULT_SERVICE_MODULE = "cavani.endorfina.hydrogen.service-v1.0";

	private static final JsonObject DEFAULT_SERVICE_CONF = new JsonObject();

	private String serviceModule;

	private JsonObject serviceConf;

	@Override
	public void start()
	{
		super.start();

		logger.info("Server starting...");

		deployModules();

		logger.info("Server start done!");
	}

	@Override
	protected void setup()
	{
		super.setup();
		serviceModule = getOptionalStringConfig(CONFIG_SERVICE_MODULE, DEFAULT_SERVICE_MODULE);
		serviceConf = getOptionalObjectConfig(CONFIG_SERVICE_CONF, DEFAULT_SERVICE_CONF);
	}

	protected void deployModules()
	{
		if (!Constants.NO_SERVICE.equals(serviceModule))
		{
			container.deployModule(serviceModule, serviceConf);
		}
	}

	@Override
	protected List<HttpService> services()
	{
		return Arrays.asList(new HttpService[]
		{
			ServiceFactory.TrackService(),
		});
	}

}
