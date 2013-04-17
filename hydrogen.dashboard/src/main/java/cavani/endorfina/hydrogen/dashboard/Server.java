package cavani.endorfina.hydrogen.dashboard;

import static cavani.endorfina.hydrogen.dashboard.util.Constants.CONFIG_ENGINE_CONF;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.CONFIG_ENGINE_MODULE;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.CONFIG_HOST;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.CONFIG_PORT;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.CONFIG_ROOT_FILE;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.CONFIG_WEB_ROOT;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.NO_ENGINE;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.dashboard.service.HttpService;
import cavani.endorfina.hydrogen.dashboard.service.HttpServiceHandler;
import cavani.endorfina.hydrogen.dashboard.service.ServiceRegistry;

public class Server extends BusModBase
{

	private static final String DEFAULT_ENGINE_MODULE = "cavani.endorfina-hydrogen.engine-v1.0";

	private static final JsonObject DEFAULT_ENGINE_CONF = new JsonObject();

	private static final String DEFAULT_HOST = "127.0.0.1";

	private static final int DEFAULT_PORT = 8180;

	private static final String DEFAULT_WEB_ROOT = "hydrogen.dashboard/src/main/webapp/";

	private static final String DEFAULT_ROOT_FILE = "index.html";

	private String engineModule;

	private JsonObject engineConf;

	private String host;

	private int port;

	private String webRoot;

	private String rootFile;

	@Override
	public void start()
	{
		super.start();

		logger.info("Dashboard starting...");

		setup();

		deployModules();

		createServer();

		logger.info("Dashboard start done!");
	}

	protected void setup()
	{
		engineModule = getOptionalStringConfig(CONFIG_ENGINE_MODULE, DEFAULT_ENGINE_MODULE);
		engineConf = getOptionalObjectConfig(CONFIG_ENGINE_CONF, DEFAULT_ENGINE_CONF);
		host = getOptionalStringConfig(CONFIG_HOST, DEFAULT_HOST);
		port = getOptionalIntConfig(CONFIG_PORT, DEFAULT_PORT);
		webRoot = getOptionalStringConfig(CONFIG_WEB_ROOT, DEFAULT_WEB_ROOT);
		rootFile = getOptionalStringConfig(CONFIG_ROOT_FILE, DEFAULT_ROOT_FILE);
	}

	protected void deployModules()
	{
		if (!NO_ENGINE.equals(engineModule))
		{
			container.deployModule(engineModule, engineConf);
		}
	}

	protected void registerServices(final RouteMatcher rm)
	{
		bindService(rm, ServiceRegistry.ENGINE_DATA_TOTAL);
		bindService(rm, ServiceRegistry.ENGINE_DATA_TOTALHOUR);
		bindService(rm, ServiceRegistry.ENGINE_DATA_TOTALITEM);
	}

	protected void bindService(final RouteMatcher rm, final HttpService service)
	{
		final String pattern = service.url();
		final HttpServiceHandler handler = service.createHandler(eb);
		rm.get(pattern, handler);
	}

	protected void registerWebContent(final RouteMatcher rm)
	{
		rm.getWithRegEx(".*", new Handler<HttpServerRequest>()
		{

			@Override
			public void handle(final HttpServerRequest req)
			{
				final String file = req.path.equals("/") ? rootFile : req.path;
				req.response.sendFile(webRoot + file);
			}

		});
	}

	protected void createServer()
	{
		final RouteMatcher rm = new RouteMatcher();

		registerServices(rm);
		registerWebContent(rm);

		vertx.createHttpServer().requestHandler(rm).listen(port, host);
	}

}
