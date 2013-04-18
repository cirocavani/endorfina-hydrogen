package cavani.endorfina.hydrogen.network.http;

import static cavani.endorfina.hydrogen.network.http.Constants.CONFIG_HOST;
import static cavani.endorfina.hydrogen.network.http.Constants.CONFIG_PORT;
import static cavani.endorfina.hydrogen.network.http.Constants.CONFIG_ROOT_FILE;
import static cavani.endorfina.hydrogen.network.http.Constants.CONFIG_WEB_ROOT;

import java.util.List;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;

public abstract class HttpServer extends BusModBase
{

	private static final String DEFAULT_HOST = "127.0.0.1";

	private static final int DEFAULT_PORT = 8080;

	private static final String DEFAULT_WEB_ROOT = "webroot/";

	private static final String DEFAULT_ROOT_FILE = "index.html";

	private String host;

	private int port;

	private String webRoot;

	private String rootFile;

	@Override
	public void start()
	{
		super.start();

		logger.info("HttpServer starting...");

		setup();

		createHttpServer();

		logger.info("HttpServer start done!");
	}

	protected abstract List<HttpService> services();

	protected void setup()
	{
		host = getOptionalStringConfig(CONFIG_HOST, DEFAULT_HOST);
		port = getOptionalIntConfig(CONFIG_PORT, DEFAULT_PORT);
		webRoot = getOptionalStringConfig(CONFIG_WEB_ROOT, DEFAULT_WEB_ROOT);
		rootFile = getOptionalStringConfig(CONFIG_ROOT_FILE, DEFAULT_ROOT_FILE);
	}

	protected void registerServices(final RouteMatcher rm)
	{
		for (final HttpService service : services())
		{
			bindService(rm, service);
		}
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

	protected void createHttpServer()
	{
		final RouteMatcher rm = new RouteMatcher();

		registerServices(rm);
		registerWebContent(rm);

		vertx.createHttpServer().requestHandler(rm).listen(port, host);
	}

}
