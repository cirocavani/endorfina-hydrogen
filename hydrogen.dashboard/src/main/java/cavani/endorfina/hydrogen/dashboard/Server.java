package cavani.endorfina.hydrogen.dashboard;

import static cavani.endorfina.hydrogen.dashboard.util.Constants.CONFIG_ENGINE_CONF;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.CONFIG_ENGINE_MODULE;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.NO_ENGINE;

import java.util.Arrays;
import java.util.List;

import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.dashboard.service.ServiceFactory;
import cavani.endorfina.hydrogen.network.http.HttpServer;
import cavani.endorfina.hydrogen.network.http.HttpService;

public class Server extends HttpServer
{

	private static final String DEFAULT_ENGINE_MODULE = "cavani.endorfina.hydrogen.engine-v1.0";

	private static final JsonObject DEFAULT_ENGINE_CONF = new JsonObject();

	private String engineModule;

	private JsonObject engineConf;

	@Override
	public void start()
	{
		super.start();

		logger.info("Dashboard starting...");

		deployModules();

		logger.info("Dashboard start done!");
	}

	@Override
	protected void setup()
	{
		super.setup();
		engineModule = getOptionalStringConfig(CONFIG_ENGINE_MODULE, DEFAULT_ENGINE_MODULE);
		engineConf = getOptionalObjectConfig(CONFIG_ENGINE_CONF, DEFAULT_ENGINE_CONF);
	}

	protected void deployModules()
	{
		if (!NO_ENGINE.equals(engineModule))
		{
			container.deployModule(engineModule, engineConf);
		}
	}

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
