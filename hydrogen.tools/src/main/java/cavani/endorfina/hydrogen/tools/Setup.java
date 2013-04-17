package cavani.endorfina.hydrogen.tools;

import java.util.Date;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.deploy.Verticle;

import cavani.endorfina.hydrogen.persistence.api.PersistorAdapter;
import cavani.endorfina.hydrogen.persistence.mongodb.MongoDbAdapter;

public class Setup extends Verticle
{

	public static final String MONGODB_0 = "mongodb.0";

	private final String mongoDbModule = "vertx.mongo-persistor-v1.2.1";

	private final String mongoDb0Host = "127.0.0.1";

	private final int mongoDb0Port = 27017;

	private final String mongoDb0Database = "default_db";

//	private final String mongoDb0Username = "system";
//
//	private final String mongoDb0Password = "secret";

	@Override
	public void start() throws Exception
	{
		System.out.println("Setup!");

		final String id = "1";
		final Date start = new Date(System.currentTimeMillis() - 60 * 60 * 1000);
		final Date end = new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000);

		final JsonObject mongoDb0Conf = new JsonObject();

		mongoDb0Conf.putString("address", MONGODB_0);
		mongoDb0Conf.putString("host", mongoDb0Host);
		mongoDb0Conf.putNumber("port", mongoDb0Port);
		mongoDb0Conf.putString("db_name", mongoDb0Database);
//		mongoDb0Conf.putString("username", mongoDb0Username);
//		mongoDb0Conf.putString("password", mongoDb0Password);

		System.out.println("Deploying " + mongoDbModule);

		final PersistorAdapter persistor = new MongoDbAdapter();

		container.deployModule(mongoDbModule, mongoDb0Conf, 1, new Handler<String>()
		{

			@Override
			public void handle(final String event)
			{
				final EventBus eventBus = vertx.eventBus();

				eventBus.send(MONGODB_0, persistor.cleanup(), new Handler<Message<JsonObject>>()
				{

					@Override
					public void handle(final Message<JsonObject> event)
					{
						System.out.println("Cleanup done!");

						eventBus.send(MONGODB_0, persistor.createUser(id, start, end), new Handler<Message<JsonObject>>()
						{

							@Override
							public void handle(final Message<JsonObject> event)
							{
								System.out.println("Create done!");
								container.exit();
							};

						});
					}

				});
			}

		});
	}

}
