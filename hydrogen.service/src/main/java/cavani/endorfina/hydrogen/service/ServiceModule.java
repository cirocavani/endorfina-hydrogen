package cavani.endorfina.hydrogen.service;

import static cavani.endorfina.hydrogen.service.util.Constants.CONFIG_PERSISTENCE_SOURCE_CONF;
import static cavani.endorfina.hydrogen.service.util.Constants.CONFIG_PERSISTENCE_STORAGE_CONF;
import static cavani.endorfina.hydrogen.service.util.Constants.CONFIG_PERSISTENCE_TYPE;
import static cavani.endorfina.hydrogen.service.util.Constants.TRACK_SERVICE_ADDRESS;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.persistence.api.PersistorAdapter;
import cavani.endorfina.hydrogen.persistence.api.PersistorManager;
import cavani.endorfina.hydrogen.persistence.api.PersistorManagerFactory;
import cavani.endorfina.hydrogen.service.handler.TrackHandler;


public class ServiceModule extends BusModBase
{

	private String persistenceType;

	private JsonObject persistenceStorageConf;

	private JsonObject persistenceSourceConf;

	@Override
	public void start()
	{
		super.start();

		logger.info("Service starting...");

		setup();

		final PersistorManager manager = createPersistorManager();

		final PersistorAdapter persistorAdapter = manager.createAdapter();
		final String storageAddress = manager.deploy(container, persistenceStorageConf);
		final String sourceAddress = manager.deploy(container, persistenceSourceConf);

		registerServices(storageAddress, sourceAddress, persistorAdapter);

		logger.info("Service start done!");
	}

	protected void setup()
	{
		persistenceType = getMandatoryStringConfig(CONFIG_PERSISTENCE_TYPE);
		persistenceStorageConf = getOptionalObjectConfig(CONFIG_PERSISTENCE_STORAGE_CONF, new JsonObject());
		persistenceSourceConf = getOptionalObjectConfig(CONFIG_PERSISTENCE_SOURCE_CONF, new JsonObject());
	}

	protected PersistorManager createPersistorManager()
	{
		final PersistorManagerFactory factory = new PersistorManagerFactory();
		return factory.createPersistorManager(persistenceType);
	}

	protected void registerServices(final String storageAddress, final String sourceAddress, final PersistorAdapter persistorAdapter)
	{
		eb.registerHandler(TRACK_SERVICE_ADDRESS, new TrackHandler(eb, storageAddress, sourceAddress, persistorAdapter));
	}

}
