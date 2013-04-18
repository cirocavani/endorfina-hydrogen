package cavani.endorfina.hydrogen.server.tests;

import static cavani.endorfina.hydrogen.network.http.Constants.CONFIG_HOST;
import static cavani.endorfina.hydrogen.network.http.Constants.CONFIG_PORT;
import static cavani.endorfina.hydrogen.network.http.Constants.CONFIG_ROOT_FILE;
import static cavani.endorfina.hydrogen.network.http.Constants.CONFIG_WEB_ROOT;
import static cavani.endorfina.hydrogen.server.util.Constants.CONFIG_SERVICE_CONF;
import static cavani.endorfina.hydrogen.server.util.Constants.CONFIG_SERVICE_MODULE;
import static cavani.endorfina.hydrogen.server.util.Constants.NO_SERVICE;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.testframework.TestClientBase;

public class IntegrationTest extends TestClientBase
{

	private static final String ROOT_FILE = "index.html";

	private static final String WEB_ROOT = "src/main/webapp/";

	private static final String HOST = "127.0.0.9";

	private static final int PORT = 9876;

	public static final String USER_ID = "10";

	public static final String ITEM = "5";

	public static final String SHARE = "{\"1\":37,\"2\":63}";

	boolean test(final String field, final String expected, final String actual)
	{
		final boolean test = expected.equals(actual);
		tu.azzert(test, field + " expected [" + expected + "] actual [" + actual + "]");
		return test;
	}

	@Override
	public void start()
	{
		super.start();

		final JsonObject config = new JsonObject();
		config.putString(CONFIG_SERVICE_MODULE, NO_SERVICE);
		config.putObject(CONFIG_SERVICE_CONF, new JsonObject());
		config.putString(CONFIG_HOST, HOST);
		config.putNumber(CONFIG_PORT, PORT);
		config.putString(CONFIG_WEB_ROOT, WEB_ROOT);
		config.putString(CONFIG_ROOT_FILE, ROOT_FILE);

		container.deployVerticle(FakeServer.class.getName(), config, 1, new Handler<String>()
		{

			@Override
			public void handle(final String event)
			{
				tu.appReady();
			}

		});
	}

	String get(final String path) throws Exception
	{
		final URL u = new URL("http", HOST, PORT, path);

		try (
			InputStream in = u.openStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();)
		{
			int read = -1;
			final byte[] buffer = new byte[1024];
			while ((read = in.read(buffer)) != -1)
			{
				out.write(buffer, 0, read);
			}
			return out.toString("UTF-8");
		}
	}

	public void testTrack() throws Exception
	{
		final String path = "/track/" + USER_ID + "/" + ITEM;
		final String actual = get(path);
		test(path, SHARE, actual);
		tu.testComplete();
	}

	public void testRootFile() throws Exception
	{
		final Path path = Paths.get(WEB_ROOT, ROOT_FILE);
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		Files.copy(path, out);
		final String expected = out.toString("UTF-8");
		final String actual = get("/");
		test(path.toString(), expected, actual);
		tu.testComplete();
	}

}
