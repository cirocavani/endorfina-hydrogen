package cavani.endorfina.hydrogen.dashboard.tests;

import static cavani.endorfina.hydrogen.dashboard.util.Constants.CONFIG_ENGINE_CONF;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.CONFIG_ENGINE_MODULE;
import static cavani.endorfina.hydrogen.dashboard.util.Constants.NO_ENGINE;
import static cavani.endorfina.hydrogen.network.http.Constants.CONFIG_HOST;
import static cavani.endorfina.hydrogen.network.http.Constants.CONFIG_PORT;
import static cavani.endorfina.hydrogen.network.http.Constants.CONFIG_ROOT_FILE;
import static cavani.endorfina.hydrogen.network.http.Constants.CONFIG_WEB_ROOT;

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

	private static final String HOST = "127.0.0.6";

	private static final int PORT = 8128;

	public static final String USER_ID = "10";

	public static final String ITEM = "5";

	public static final Long TOTAL = 1000L;

	public static final String TOTAL_HOUR = "{\"1\":100,\"2\":200,\"3\":300}";

	public static final Long TOTAL_ITEM = 479L;

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
		config.putString(CONFIG_ENGINE_MODULE, NO_ENGINE);
		config.putObject(CONFIG_ENGINE_CONF, new JsonObject());
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

	public void testTotal() throws Exception
	{
		final String path = "/total/" + USER_ID;
		final String actual = get(path);
		test(path, String.valueOf(TOTAL), actual);
		tu.testComplete();
	}

	public void testTotalHour() throws Exception
	{
		final String path = "/total/hour/" + USER_ID;
		final String actual = get(path);
		test(path, String.valueOf(TOTAL_HOUR), actual);
		tu.testComplete();
	}

	public void testTotalItem() throws Exception
	{
		final String path = "/total/" + USER_ID + "/" + ITEM;
		final String actual = get(path);
		test(path, String.valueOf(TOTAL_ITEM), actual);
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
