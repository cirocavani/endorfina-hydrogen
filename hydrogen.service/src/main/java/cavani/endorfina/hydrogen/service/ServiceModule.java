package cavani.endorfina.hydrogen.service;

import static cavani.endorfina.hydrogen.persistence.api.Constants.PERSISTENCE_TYPE_MONGODB;
import static cavani.endorfina.hydrogen.service.util.Constants.CONFIG_PERSISTENCE_SOURCE_CONF;
import static cavani.endorfina.hydrogen.service.util.Constants.CONFIG_PERSISTENCE_STORAGE_CONF;
import static cavani.endorfina.hydrogen.service.util.Constants.CONFIG_PERSISTENCE_TYPE;
import static cavani.endorfina.hydrogen.service.util.Constants.CONFIG_SOURCE_ADDRESS;
import static cavani.endorfina.hydrogen.service.util.Constants.CONFIG_STORAGE_ADDRESS;
import static cavani.endorfina.hydrogen.service.util.Constants.CONFIG_TRACK_INSTANCES;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.persistence.api.PersistorManager;
import cavani.endorfina.hydrogen.persistence.api.PersistorManagerFactory;
import cavani.endorfina.hydrogen.service.handler.TrackService;

public class ServiceModule extends BusModBase
{

	private static final int DEFAULT_TRACK_INSTANCES = 10;

	private static final String DEFAULT_PERSISTENCE_TYPE = PERSISTENCE_TYPE_MONGODB;

	private int trackInstances;

	private String persistenceType;

	private JsonObject persistenceStorageConf;

	private JsonObject persistenceSourceConf;

	protected void info(final String msg)
	{
		container.getLogger().info(msg);
	}

	@Override
	public void start()
	{
		info("Hydrogen Service starting...");

		super.start();

		setup();

		final PersistorManager manager = createPersistorManager();

		final String storageAddress = manager.deploy(container, persistenceStorageConf);
		final String sourceAddress = manager.deploy(container, persistenceSourceConf);

		deployTrackService(storageAddress, sourceAddress);

		info("Hydrogen Service start done!");
	}

	protected void setup()
	{
		trackInstances = getOptionalIntConfig(CONFIG_TRACK_INSTANCES, DEFAULT_TRACK_INSTANCES);
		persistenceType = getOptionalStringConfig(CONFIG_PERSISTENCE_TYPE, DEFAULT_PERSISTENCE_TYPE);
		persistenceStorageConf = getOptionalObjectConfig(CONFIG_PERSISTENCE_STORAGE_CONF, new JsonObject());
		persistenceSourceConf = getOptionalObjectConfig(CONFIG_PERSISTENCE_SOURCE_CONF, new JsonObject());
	}

	protected PersistorManager createPersistorManager()
	{
		final PersistorManagerFactory factory = new PersistorManagerFactory();
		return factory.createPersistorManager(persistenceType);
	}

	protected void deployTrackService(final String storageAddress, final String sourceAddress)
	{
		final JsonObject conf = new JsonObject();
		conf.putString(CONFIG_PERSISTENCE_TYPE, persistenceType);
		conf.putString(CONFIG_STORAGE_ADDRESS, storageAddress);
		conf.putString(CONFIG_SOURCE_ADDRESS, sourceAddress);

		container.deployWorkerVerticle(TrackService.class.getName(), conf, trackInstances);
	}

}
