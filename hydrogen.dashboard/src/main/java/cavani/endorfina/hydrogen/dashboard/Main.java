package cavani.endorfina.hydrogen.dashboard;

import static cavani.endorfina.hydrogen.dashboard.util.Constants.CONFIG_ENGINE_CONF;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.CONFIG_ENGINE_MODULE;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.CONFIG_HTTP_CONF;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.CONFIG_HTTP_INSTANCES;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.NO_ENGINE;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.dashboard.network.HttpServer;

public class Main extends BusModBase
{

	private static final int DEFAULT_HTTP_INSTANCES = 10;

	private static final JsonObject DEFAULT_HTTP_CONF = new JsonObject();

	private static final String DEFAULT_ENGINE_MODULE = "cavani.endorfina.hydrogen.engine-v1.0";

	private static final JsonObject DEFAULT_ENGINE_CONF = new JsonObject();

	private int httpInstances;

	private JsonObject httpConf;

	private String engineModule;

	private JsonObject engineConf;

	protected void info(final String msg)
	{
		container.getLogger().info(msg);
	}

	@Override
	public void start()
	{
		info("Hydrogen Dashboard starting...");

		super.start();

		setup();

		deployEngine();
		deployHttpServer();

		info("Hydrogen Dashboard start done!");
	}

	protected void setup()
	{
		httpInstances = getOptionalIntConfig(CONFIG_HTTP_INSTANCES, DEFAULT_HTTP_INSTANCES);
		httpConf = getOptionalObjectConfig(CONFIG_HTTP_CONF, DEFAULT_HTTP_CONF);
		engineModule = getOptionalStringConfig(CONFIG_ENGINE_MODULE, DEFAULT_ENGINE_MODULE);
		engineConf = getOptionalObjectConfig(CONFIG_ENGINE_CONF, DEFAULT_ENGINE_CONF);
	}

	protected void deployEngine()
	{
		if (NO_ENGINE.equals(engineModule))
		{
			return;
		}
		container.deployModule(engineModule, engineConf);
	}

	protected void deployHttpServer()
	{
		container.deployVerticle(HttpServer.class.getName(), httpConf, httpInstances);
	}

}
