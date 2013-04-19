package cavani.endorfina.hydrogen.server;

import static cavani.endorfina.hydrogen.server.util.Constants.CONFIG_HTTP_CONF;
import static cavani.endorfina.hydrogen.server.util.Constants.CONFIG_HTTP_INSTANCES;
import static cavani.endorfina.hydrogen.server.util.Constants.CONFIG_SERVICE_CONF;
import static cavani.endorfina.hydrogen.server.util.Constants.CONFIG_SERVICE_MODULE;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.server.network.HttpServer;
import cavani.endorfina.hydrogen.server.util.Constants;

public class Main extends BusModBase
{

	private static final int DEFAULT_HTTP_INSTANCES = 10;

	private static final JsonObject DEFAULT_HTTP_CONF = new JsonObject();

	private static final String DEFAULT_SERVICE_MODULE = "cavani.endorfina.hydrogen.service-v1.0";

	private static final JsonObject DEFAULT_SERVICE_CONF = new JsonObject();

	private int httpInstances;

	private JsonObject httpConf;

	private String serviceModule;

	private JsonObject serviceConf;

	protected void info(final String msg)
	{
		container.getLogger().info(msg);
	}

	@Override
	public void start()
	{
		info("Hydrogen Server starting...");

		super.start();

		setup();

		deployService();
		deployHttpServer();

		info("Hydrogen Server start done!");
	}

	protected void setup()
	{
		httpInstances = getOptionalIntConfig(CONFIG_HTTP_INSTANCES, DEFAULT_HTTP_INSTANCES);
		httpConf = getOptionalObjectConfig(CONFIG_HTTP_CONF, DEFAULT_HTTP_CONF);
		serviceModule = getOptionalStringConfig(CONFIG_SERVICE_MODULE, DEFAULT_SERVICE_MODULE);
		serviceConf = getOptionalObjectConfig(CONFIG_SERVICE_CONF, DEFAULT_SERVICE_CONF);
	}

	protected void deployService()
	{
		if (Constants.NO_SERVICE.equals(serviceModule))
		{
			return;
		}
		container.deployModule(serviceModule, serviceConf);
	}

	protected void deployHttpServer()
	{
		container.deployVerticle(HttpServer.class.getName(), httpConf, httpInstances);
	}

}
