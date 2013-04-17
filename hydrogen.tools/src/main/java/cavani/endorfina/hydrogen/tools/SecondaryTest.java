package cavani.endorfina.hydrogen.tools;

import java.util.Arrays;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.deploy.Verticle;

import cavani.endorfina.hydrogen.persistence.api.PersistorAdapter;
import cavani.endorfina.hydrogen.persistence.api.PersistorOperation;
import cavani.endorfina.hydrogen.persistence.mongodb.MongoDbAdapter;

public class SecondaryTest extends Verticle
{

	public static final String MONGODB_2 = "mongodb.2";

	private final String mongoDbModule = "vertx.mongo-persistor-v1.2.1";

	private final String mongoDb2Host = "127.0.0.1";

	private final int mongoDb2Port = 27019;

	private final String mongoDb2Database = "default_db";

//	private final String mongoDb2Username = "system";
//
//	private final String mongoDb2Password = "secret";

	@Override
	public void start() throws Exception
	{
		System.out.println("Mongo 2 Test!");

		final JsonObject mongoDb2Conf = new JsonObject();

		mongoDb2Conf.putString("address", MONGODB_2);
		mongoDb2Conf.putString("host", mongoDb2Host);
		mongoDb2Conf.putNumber("port", mongoDb2Port);
		mongoDb2Conf.putString("db_name", mongoDb2Database);
		mongoDb2Conf.putBoolean("secondary", true);
//		mongoDb2Conf.putString("username", mongoDb0Username);
//		mongoDb2Conf.putString("password", mongoDb0Password);

		System.out.println("Deploying " + mongoDbModule);

		final PersistorAdapter persistorAdapter = new MongoDbAdapter();

		container.deployModule(mongoDbModule, mongoDb2Conf, 1, new Handler<String>()
		{

			@Override
			public void handle(final String event)
			{
				final EventBus eventBus = vertx.eventBus();

				final String userId = "1";

				final PersistorOperation<Long[]> op = persistorAdapter.fetchCounter(userId);
				eventBus.send(MONGODB_2, op.request(), new Handler<Message<JsonObject>>()
				{

					@Override
					public void handle(final Message<JsonObject> event)
					{
						final Long[] values = op.result(event.body);
						System.out.println(Arrays.toString(values));
						System.out.println("exiting...");
						container.exit();
					}

				});

			}

		});
	}

}
