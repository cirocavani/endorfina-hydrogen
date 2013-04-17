package cavani.endorfina.hydrogen.service.tests;

import static cavani.endorfina.hydrogen.service.util.Constants.CONFIG_PERSISTENCE_TYPE;
import static cavani.endorfina.hydrogen.service.util.Constants.TRACK_SERVICE_ADDRESS;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.testframework.TestClientBase;

import cavani.endorfina.hydrogen.service.util.Constants;

public class IntegrationTest extends TestClientBase
{

	public static final String ITEM1_ID = "1";

	public static final String ITEM2_ID = "2";

	private static final String USER_ID = "456";

	public static final Long ITEM1 = 321L;

	public static final Long ITEM2 = 654L;

	public static final Long[] VALUES = new Long[]
	{
		ITEM1,
		ITEM2
	};

	public static final Long P1 = 100 * ITEM1 / (ITEM1 + ITEM2);

	public static final Long P2 = 100 - P1;

	public static final String SHARE = "{\"1\":" + P1 + ",\"2\":" + P2 + "}";

	@Override
	public void start()
	{
		super.start();

		final JsonObject config = new JsonObject();
		config.putString(CONFIG_PERSISTENCE_TYPE, "none");

		container.deployWorkerVerticle(FakeService.class.getName(), config, 1, new Handler<String>()
		{

			@Override
			public void handle(final String event)
			{
				tu.appReady();
			}

		});
	}

	boolean test(final String field, final Object expected, final Object actual)
	{
		final boolean test = expected.equals(actual);
		tu.azzert(test, field + " expected [" + expected + "] actual [" + actual + "]");
		return test;
	}

	public void testTrackItem1()
	{
		final EventBus eb = vertx.eventBus();

		final JsonObject request = new JsonObject();
		request.putString(Constants.PARAM_ID, USER_ID);
		request.putString(Constants.PARAM_ITEM, ITEM1_ID);

		eb.send(TRACK_SERVICE_ADDRESS, request, new Handler<Message<JsonObject>>()
		{

			@Override
			public void handle(final Message<JsonObject> event)
			{
				test("TrackItem1", SHARE, event.body.encode());
				tu.testComplete();
			};

		});
	}

	public void testTrackItem2()
	{
		final EventBus eb = vertx.eventBus();

		final JsonObject request = new JsonObject();
		request.putString(Constants.PARAM_ID, USER_ID);
		request.putString(Constants.PARAM_ITEM, ITEM1_ID);

		eb.send(TRACK_SERVICE_ADDRESS, request, new Handler<Message<JsonObject>>()
		{

			@Override
			public void handle(final Message<JsonObject> event)
			{
				test("TrackItem2", SHARE, event.body.encode());
				tu.testComplete();
			};

		});
	}

}
