package cavani.endorfina.hydrogen.engine.tests;

import static cavani.endorfina.hydrogen.engine.util.Constants.CONFIG_PERSISTENCE_TYPE;
import static cavani.endorfina.hydrogen.engine.util.Constants.CONFIG_PERSISTOR_ADDRESS;
import static cavani.endorfina.hydrogen.engine.util.Constants.TOTALHOUR_HANDLER_ADDRESS;
import static cavani.endorfina.hydrogen.engine.util.Constants.TOTALITEM_HANDLER_ADDRESS;
import static cavani.endorfina.hydrogen.engine.util.Constants.TOTAL_HANDLER_ADDRESS;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.testframework.TestClientBase;

import cavani.endorfina.hydrogen.engine.util.Constants;

public class IntegrationTest extends TestClientBase
{

	public static final String PERSISTOR_FAKE_ADDRESS = "persistor.fake";

	public static final String ITEM2_ID = "2";

	public static final String ITEM1_ID = "1";

	private static final String USER_ID = "456";

	public static final Long ITEM1 = 321L;

	public static final Long ITEM2 = 654L;

	public static final Long[] VALUES = new Long[]
	{
		ITEM1,
		ITEM2
	};

	public static final String HOUR = "{\"1\":975,\"2\":0,\"3\":0}";

	private static final Long TOTAL = ITEM1 + ITEM2;

	@Override
	public void start()
	{
		super.start();

		container.deployWorkerVerticle(FakeEngine.class.getName(), 1);

		final JsonObject config = new JsonObject();
		config.putString(CONFIG_PERSISTENCE_TYPE, "none");
		config.putString(CONFIG_PERSISTOR_ADDRESS, PERSISTOR_FAKE_ADDRESS);

		container.deployWorkerVerticle(FakeData.class.getName(), config, 1, new Handler<String>()
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

	public void testTotal()
	{
		final EventBus eb = vertx.eventBus();

		final JsonObject request = new JsonObject();
		request.putString(Constants.PARAM_ID, USER_ID);

		eb.send(TOTAL_HANDLER_ADDRESS, request, new Handler<Message<JsonObject>>()
		{

			@Override
			public void handle(final Message<JsonObject> event)
			{
				test("Total", TOTAL, event.body.getLong("result"));
				tu.testComplete();
			};

		});
	}

	public void testTotalHour()
	{
		final EventBus eb = vertx.eventBus();

		final JsonObject request = new JsonObject();
		request.putString(Constants.PARAM_ID, USER_ID);

		eb.send(TOTALHOUR_HANDLER_ADDRESS, request, new Handler<Message<JsonObject>>()
		{

			@Override
			public void handle(final Message<JsonObject> event)
			{
				test("TotalHour", HOUR, event.body.encode());
				tu.testComplete();
			};

		});
	}

	public void testTotalItem1()
	{
		final EventBus eb = vertx.eventBus();

		final JsonObject request = new JsonObject();
		request.putString(Constants.PARAM_ID, USER_ID);
		request.putString(Constants.PARAM_ITEM, ITEM1_ID);

		eb.send(TOTALITEM_HANDLER_ADDRESS, request, new Handler<Message<JsonObject>>()
		{

			@Override
			public void handle(final Message<JsonObject> event)
			{
				test("TotalItem1", ITEM1, event.body.getLong("result"));
				tu.testComplete();
			};

		});
	}

	public void testTotalItem2()
	{
		final EventBus eb = vertx.eventBus();

		final JsonObject request = new JsonObject();
		request.putString(Constants.PARAM_ID, USER_ID);
		request.putString(Constants.PARAM_ITEM, ITEM2_ID);

		eb.send(TOTALITEM_HANDLER_ADDRESS, request, new Handler<Message<JsonObject>>()
		{

			@Override
			public void handle(final Message<JsonObject> event)
			{
				test("TotalItem2", ITEM2, event.body.getLong("result"));
				tu.testComplete();
			};

		});
	}

}
