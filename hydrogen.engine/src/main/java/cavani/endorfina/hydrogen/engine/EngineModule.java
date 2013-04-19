package cavani.endorfina.hydrogen.engine;

import static cavani.endorfina.hydrogen.engine.util.Constants.CONFIG_DATA_INSTANCES;
import static cavani.endorfina.hydrogen.engine.util.Constants.CONFIG_PERSISTENCE_SOURCE_CONF;
import static cavani.endorfina.hydrogen.engine.util.Constants.CONFIG_PERSISTENCE_TYPE;
import static cavani.endorfina.hydrogen.engine.util.Constants.CONFIG_PERSISTOR_ADDRESS;
import static cavani.endorfina.hydrogen.persistence.api.Constants.PERSISTENCE_TYPE_MONGODB;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.engine.data.DataService;
import cavani.endorfina.hydrogen.persistence.api.PersistorManager;
import cavani.endorfina.hydrogen.persistence.api.PersistorManagerFactory;

public class EngineModule extends BusModBase
{

	private static final int DEFAULT_DATA_INSTANCES = 10;

	private static final String DEFAULT_PERSISTENCE_TYPE = PERSISTENCE_TYPE_MONGODB;

	private int dataInstances;

	private String persistenceType;

	private JsonObject persistenceSourceConf;

	protected void info(final String msg)
	{
		container.getLogger().info(msg);
	}

	@Override
	public void start()
	{
		info("Hydrogen Engine starting...");

		super.start();

		setup();

		final PersistorManager manager = createPersistorManager();
		final String persistorAddress = manager.deploy(container, persistenceSourceConf);

		deployDataService(persistorAddress);

		info("Hydrogen Engine start done!");
	}

	protected void setup()
	{
		dataInstances = getOptionalIntConfig(CONFIG_DATA_INSTANCES, DEFAULT_DATA_INSTANCES);
		persistenceType = getOptionalStringConfig(CONFIG_PERSISTENCE_TYPE, DEFAULT_PERSISTENCE_TYPE);
		persistenceSourceConf = getOptionalObjectConfig(CONFIG_PERSISTENCE_SOURCE_CONF, new JsonObject());
	}

	protected PersistorManager createPersistorManager()
	{
		final PersistorManagerFactory factory = new PersistorManagerFactory();
		return factory.createPersistorManager(persistenceType);
	}

	protected void deployDataService(final String persistorAddress)
	{
		final JsonObject conf = new JsonObject();
		conf.putString(CONFIG_PERSISTENCE_TYPE, persistenceType);
		conf.putString(CONFIG_PERSISTOR_ADDRESS, persistorAddress);

		container.deployWorkerVerticle(DataService.class.getName(), conf, dataInstances);
	}

}
