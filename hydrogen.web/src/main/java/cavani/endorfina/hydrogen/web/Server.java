package cavani.endorfina.hydrogen.web;

import static cavani.endorfina.hydrogen.web.util.Constants.CONFIG_HOST;
import static cavani.endorfina.hydrogen.web.util.Constants.CONFIG_PORT;
import static cavani.endorfina.hydrogen.web.util.Constants.CONFIG_ROOT_FILE;
import static cavani.endorfina.hydrogen.web.util.Constants.CONFIG_SERVICE_CONF;
import static cavani.endorfina.hydrogen.web.util.Constants.CONFIG_SERVICE_MODULE;
import static cavani.endorfina.hydrogen.web.util.Constants.CONFIG_WEB_ROOT;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.web.service.HttpService;
import cavani.endorfina.hydrogen.web.service.HttpServiceHandler;
import cavani.endorfina.hydrogen.web.service.ServiceRegistry;
import cavani.endorfina.hydrogen.web.util.Constants;

public class Server extends BusModBase
{

	private static final String DEFAULT_SERVICE_MODULE = "cavani.endorfina-hydrogen.service-v1.0";

	private static final JsonObject DEFAULT_SERVICE_CONF = new JsonObject();

	private static final String DEFAULT_HOST = "127.0.0.1";

	private static final int DEFAULT_PORT = 8080;

	private static final String DEFAULT_WEB_ROOT = "hydrogen.web/src/main/webapp/";

	private static final String DEFAULT_ROOT_FILE = "index.html";

	private String serviceModule;

	private JsonObject serviceConf;

	private String host;

	private int port;

	private String webRoot;

	private String rootFile;

	@Override
	public void start()
	{
		super.start();

		logger.info("Web starting...");

		setup();

		deployModules();

		createServer();

		logger.info("Web start done!");
	}

	protected void setup()
	{
		serviceModule = getOptionalStringConfig(CONFIG_SERVICE_MODULE, DEFAULT_SERVICE_MODULE);
		serviceConf = getOptionalObjectConfig(CONFIG_SERVICE_CONF, DEFAULT_SERVICE_CONF);
		host = getOptionalStringConfig(CONFIG_HOST, DEFAULT_HOST);
		port = getOptionalIntConfig(CONFIG_PORT, DEFAULT_PORT);
		webRoot = getOptionalStringConfig(CONFIG_WEB_ROOT, DEFAULT_WEB_ROOT);
		rootFile = getOptionalStringConfig(CONFIG_ROOT_FILE, DEFAULT_ROOT_FILE);
	}

	protected void deployModules()
	{
		if (!Constants.NO_SERVICE.equals(serviceModule))
		{
			container.deployModule(serviceModule, serviceConf);
		}
	}

	protected void registerServices(final RouteMatcher rm)
	{
		bindService(rm, ServiceRegistry.TRACK_SERVICE);
	}

	protected void bindService(final RouteMatcher rm, final HttpService service)
	{
		final String pattern = service.url();
		final HttpServiceHandler handler = service.createHandler(eb);
		rm.get(pattern, handler);
	}

	protected void registerContent(final RouteMatcher rm)
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

		registerContent(rm);

		vertx.createHttpServer().requestHandler(rm).listen(port, host);
	}

}
