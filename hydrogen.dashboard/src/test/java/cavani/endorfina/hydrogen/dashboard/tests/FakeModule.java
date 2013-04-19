package cavani.endorfina.hydrogen.dashboard.tests;

import static cavani.endorfina.hydrogen.dashboard.service.ServiceFactory.ENGINE_DATA_TOTALHOUR_ADDRESS;
import static cavani.endorfina.hydrogen.dashboard.service.ServiceFactory.ENGINE_DATA_TOTALITEM_ADDRESS;
import static cavani.endorfina.hydrogen.dashboard.service.ServiceFactory.ENGINE_DATA_TOTAL_ADDRESS;
import static cavani.endorfina.hydrogen.dashboard.tests.IntegrationTest.ITEM;
import static cavani.endorfina.hydrogen.dashboard.tests.IntegrationTest.TOTAL;
import static cavani.endorfina.hydrogen.dashboard.tests.IntegrationTest.TOTAL_HOUR;
import static cavani.endorfina.hydrogen.dashboard.tests.IntegrationTest.TOTAL_ITEM;
import static cavani.endorfina.hydrogen.dashboard.tests.IntegrationTest.USER_ID;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.dashboard.Main;
import cavani.endorfina.hydrogen.dashboard.util.Constants;

public class FakeModule extends Main
{

	boolean test(final String field, final String expected, final String actual)
	{
		final boolean test = expected.equals(actual);
		if (!test)
		{
			System.out.println(field + " expected [" + expected + "] actual [" + actual + "]");
		}
		return test;
	}

	@Override
	protected void deployEngine()
	{
		eb.registerHandler(ENGINE_DATA_TOTAL_ADDRESS, new Handler<Message<JsonObject>>()
		{

			@Override
			public void handle(final Message<JsonObject> message)
			{
				if (test("Total.userId", USER_ID, message.body.getString(Constants.PARAM_ID)))
				{
					message.reply(new JsonObject().putNumber("result", TOTAL));
				}
				else
				{
					message.reply(new JsonObject().putNumber("result", -1));
				}
			};

		});

		eb.registerHandler(ENGINE_DATA_TOTALHOUR_ADDRESS, new Handler<Message<JsonObject>>()
		{

			@Override
			public void handle(final Message<JsonObject> message)
			{
				if (test("TotalHour.userId", USER_ID, message.body.getString(Constants.PARAM_ID)))
				{
					message.reply(new JsonObject(TOTAL_HOUR));
				}
				else
				{
					message.reply(new JsonObject());
				}
			};

		});

		eb.registerHandler(ENGINE_DATA_TOTALITEM_ADDRESS, new Handler<Message<JsonObject>>()
		{

			@Override
			public void handle(final Message<JsonObject> message)
			{
				if (test("TotalItem.userId", USER_ID, message.body.getString(Constants.PARAM_ID)) && test("TotalItem.item", ITEM, message.body.getString(Constants.PARAM_ITEM)))
				{
					message.reply(new JsonObject().putNumber("result", TOTAL_ITEM));
				}
				else
				{
					message.reply(new JsonObject().putNumber("result", -1));
				}
			};

		});
	}

	@Override
	protected void deployHttpServer()
	{
		// no op - see IntegrationTest class
	}

}
