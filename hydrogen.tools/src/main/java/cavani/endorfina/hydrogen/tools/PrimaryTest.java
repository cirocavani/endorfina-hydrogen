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

public class PrimaryTest extends Verticle
{

	public static final String MONGODB_0 = "mongodb.0";

	private final String mongoDbModule = "vertx.mongo-persistor-v1.2.1";

	private final String mongoDb0Host = "127.0.0.1";

	private final int mongoDb0Port = 27017;

	private final String mongoDb0Database = "default_db";

//	private final String mongoDb0Username = "system";
//
//	private final String mongoDboPassword = "secret";

	@Override
	public void start() throws Exception
	{
		System.out.println("Mongo 0 Test!");

		final JsonObject mongoDb0Conf = new JsonObject();

		mongoDb0Conf.putString("address", MONGODB_0);
		mongoDb0Conf.putString("host", mongoDb0Host);
		mongoDb0Conf.putNumber("port", mongoDb0Port);
		mongoDb0Conf.putString("db_name", mongoDb0Database);
//		mongoDb0Conf.putString("username", mongoDb0Username);
//		mongoDb0Conf.putString("password", mongoDb0Password);

		System.out.println("Deploying " + mongoDbModule);

		final PersistorAdapter persistorAdapter = new MongoDbAdapter();

		container.deployModule(mongoDbModule, mongoDb0Conf, 1, new Handler<String>()
		{

			@Override
			public void handle(final String event)
			{
				final EventBus eventBus = vertx.eventBus();

				final String userId = "1";

				final PersistorOperation<Long[]> op = persistorAdapter.fetchCounter(userId);
				eventBus.send(MONGODB_0, op.request(), new Handler<Message<JsonObject>>()
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
