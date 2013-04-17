package cavani.endorfina.hydrogen.engine.tests;

import static cavani.endorfina.hydrogen.engine.tests.IntegrationTest.HOUR;
import static cavani.endorfina.hydrogen.engine.tests.IntegrationTest.ITEM1_ID;
import static cavani.endorfina.hydrogen.engine.tests.IntegrationTest.ITEM1;
import static cavani.endorfina.hydrogen.engine.tests.IntegrationTest.ITEM2;
import static cavani.endorfina.hydrogen.engine.tests.IntegrationTest.ITEM2_ID;
import static cavani.endorfina.hydrogen.engine.tests.IntegrationTest.VALUES;

import java.util.Date;
import java.util.Map;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.deploy.Container;

import cavani.endorfina.hydrogen.engine.EngineModule;
import cavani.endorfina.hydrogen.persistence.api.PersistorAdapter;
import cavani.endorfina.hydrogen.persistence.api.PersistorManager;
import cavani.endorfina.hydrogen.persistence.api.PersistorOperation;

public class FakeEngine extends EngineModule
{

	private static final String PERSISTOR_FAKE_ADDRESS = "persistor.fake";

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

			@Override
			public PersistorAdapter createAdapter()
			{
				return new PersistorAdapter()
				{

					@Override
					public JsonObject createUser(final String userId, final Date start, final Date end)
					{
						throw new UnsupportedOperationException();
					}

					@Override
					public JsonObject updateCounter(final String userId, final String item, final Date timestamp)
					{
						throw new UnsupportedOperationException();
					}

					@Override
					public JsonObject cleanup()
					{
						throw new UnsupportedOperationException();
					}

					@Override
					public PersistorOperation<Long[]> fetchCounter(final String userId)
					{
						return new PersistorOperation<Long[]>()
						{

							@Override
							public JsonObject request()
							{
								return new JsonObject();
							}

							@Override
							public Long[] result(final JsonObject result)
							{
								return VALUES;
							}

						};
					}

					@Override
					public PersistorOperation<Map<String, Object>> fetchHour(final String userId)
					{
						return new PersistorOperation<Map<String, Object>>()
						{

							@Override
							public JsonObject request()
							{
								return new JsonObject();
							}

							@Override
							public Map<String, Object> result(final JsonObject result)
							{
								return new JsonObject(HOUR).toMap();
							}

						};
					}

					@Override
					public PersistorOperation<Long> fetchCounterItem(final String userId, final String item)
					{
						return new PersistorOperation<Long>()
						{

							@Override
							public JsonObject request()
							{
								return new JsonObject();
							}

							@Override
							public Long result(final JsonObject result)
							{
								switch (item)
								{
									case ITEM1_ID:
										return ITEM1;
									case ITEM2_ID:
										return ITEM2;
								}
								return -1L;
							}

						};
					}

				};
			}

		};
	}

}
