package cavani.endorfina.hydrogen.server.tests;

import static cavani.endorfina.hydrogen.server.service.ServiceFactory.TRACK_SERVICE_ADDRESS;
import static cavani.endorfina.hydrogen.server.tests.IntegrationTest.ITEM;
import static cavani.endorfina.hydrogen.server.tests.IntegrationTest.SHARE;
import static cavani.endorfina.hydrogen.server.tests.IntegrationTest.USER_ID;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.server.Server;
import cavani.endorfina.hydrogen.server.util.Constants;

public class FakeServer extends Server
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
	protected void deployModules()
	{
		eb.registerHandler(TRACK_SERVICE_ADDRESS, new Handler<Message<JsonObject>>()
		{

			@Override
			public void handle(final Message<JsonObject> message)
			{
				if (test("Track.userId", USER_ID, message.body.getString(Constants.PARAM_ID))
					&& test("Track.item", ITEM, message.body.getString(Constants.PARAM_ITEM)))
				{
					message.reply(new JsonObject(SHARE));
				}
				else
				{
					message.reply(new JsonObject());
				}
			};

		});
	}

}
