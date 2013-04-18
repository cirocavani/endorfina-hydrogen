package cavani.endorfina.hydrogen.network.http;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.network.http.HttpService;
import cavani.endorfina.hydrogen.network.http.ServiceAdapter;

public class HttpServiceTest
{

	@Test
	public void testUrl()
	{
		final String path = "/this/is/a/valid/path";
		final String address = "none";
		final ServiceAdapter adapter = new ServiceAdapter()
		{

			@Override
			public List<String> parameters()
			{
				return Arrays.asList("1", "2", "3");
			}

			@Override
			public JsonObject message(final Map<String, String> values)
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public String result(final JsonObject result)
			{
				throw new UnsupportedOperationException();
			}

		};

		final String expected = path + "/:1/:2/:3";

		final HttpService service = new HttpService(path, address, adapter);
		final String result = service.url();

		Assert.assertNotNull(result);
		Assert.assertEquals(expected, result);
	}

}
