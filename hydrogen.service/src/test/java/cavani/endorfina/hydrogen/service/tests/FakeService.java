package cavani.endorfina.hydrogen.service.tests;

import static cavani.endorfina.hydrogen.service.tests.IntegrationTest.PERSISTOR_FAKE_ADDRESS;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.deploy.Container;

import cavani.endorfina.hydrogen.persistence.api.PersistorManager;
import cavani.endorfina.hydrogen.service.ServiceModule;

public class FakeService extends ServiceModule
{

	@Override
	protected PersistorManager createPersistorManager()
	{
		return new PersistorManager()
		{

			@Override
			public String deploy(final Container conteiner, final JsonObject config)
			{
				eb.registerHandler(PERSISTOR_FAKE_ADDRESS, new Handler<Message<JsonObject>>()
				{

					@Override
					public void handle(final Message<JsonObject> event)
					{
						event.reply(new JsonObject());
					};

				});

				return PERSISTOR_FAKE_ADDRESS;
			}

		};
	}

	@Override
	protected void deployTrackService(final String storageAddress, final String sourceAddress)
	{
		// no op - see IntegrationTest class
	}

}
