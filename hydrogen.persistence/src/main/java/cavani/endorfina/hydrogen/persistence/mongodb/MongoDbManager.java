package cavani.endorfina.hydrogen.persistence.mongodb;

import org.vertx.java.core.json.JsonObject;
import org.vertx.java.deploy.Container;

import cavani.endorfina.hydrogen.persistence.api.PersistorManager;

public class MongoDbManager implements PersistorManager
{

	public static final String CONFIG_MODULE = "mongodb_module";

	public static final String CONFIG_INSTANCES = "mongodb_instances";

	public static final String CONFIG_ADDRESS = "mongodb_address";

	public static final String CONFIG_HOST = "mongodb_host";

	public static final String CONFIG_PORT = "mongodb_port";

	public static final String CONFIG_DATABASE = "mongodb_database";

	public static final String CONFIG_SECONDARY = "mongodb_secondary";

	public static final String CONFIG_AUTH = "mongodb_auth";

	public static final String CONFIG_USERNAME = "mongodb_username";

	public static final String CONFIG_PASSWORD = "mongodb_password";

	private static final String DEFAULT_MODULE = "vertx.mongo-persistor-v1.2.1";

	private static final int DEFAULT_INSTANCES = 10;

	private static final String DEFAULT_ADDRESS = "vertx.mongopersistor";

	private static final String DEFAULT_HOST = "127.0.0.1";

	private static final int DEFAULT_PORT = 27017;

	private static final String DEFAULT_DATABASE = "default_db";

	private static final boolean DEFAULT_SECONDARY = false;

	private static final boolean DEFAULT_AUTH = false;

	@Override
	public String deploy(final Container container, final JsonObject config)
	{
		final String module = config.getString(CONFIG_MODULE, DEFAULT_MODULE);
		final int instances = config.getNumber(CONFIG_INSTANCES, DEFAULT_INSTANCES).intValue();
		final String address = config.getString(CONFIG_ADDRESS, DEFAULT_ADDRESS);

		final String host = config.getString(CONFIG_HOST, DEFAULT_HOST);
		final int port = config.getNumber(CONFIG_PORT, DEFAULT_PORT).intValue();
		final String database = config.getString(CONFIG_DATABASE, DEFAULT_DATABASE);
		final boolean secondary = config.getBoolean(CONFIG_SECONDARY, DEFAULT_SECONDARY);

		final boolean auth = config.getBoolean(CONFIG_AUTH, DEFAULT_AUTH);
		final String username = config.getString(CONFIG_USERNAME);
		final String password = config.getString(CONFIG_PASSWORD);

		final JsonObject mongoDbConf = new JsonObject();

		mongoDbConf.putString("address", address);
		mongoDbConf.putString("host", host);
		mongoDbConf.putNumber("port", port);
		mongoDbConf.putString("db_name", database);
		mongoDbConf.putBoolean("secondary", secondary);
		if (auth)
		{
			mongoDbConf.putString("username", username);
			mongoDbConf.putString("password", password);
		}

		container.deployModule(module, mongoDbConf, instances);

		return address;
	}

}
