package cavani.endorfina.hydrogen.engine;

import static cavani.endorfina.hydrogen.engine.util.Constants.CONFIG_PERSISTENCE_SOURCE_CONF;
import static cavani.endorfina.hydrogen.engine.util.Constants.CONFIG_PERSISTENCE_TYPE;
import static cavani.endorfina.hydrogen.engine.util.Constants.TOTALHOUR_HANDLER_ADDRESS;
import static cavani.endorfina.hydrogen.engine.util.Constants.TOTALITEM_HANDLER_ADDRESS;
import static cavani.endorfina.hydrogen.engine.util.Constants.TOTAL_HANDLER_ADDRESS;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.engine.data.DataTotalHandler;
import cavani.endorfina.hydrogen.engine.data.DataTotalHourHandler;
import cavani.endorfina.hydrogen.engine.data.DataTotalItemHandler;
import cavani.endorfina.hydrogen.persistence.api.PersistorAdapter;
import cavani.endorfina.hydrogen.persistence.api.PersistorManager;
import cavani.endorfina.hydrogen.persistence.api.PersistorManagerFactory;

public class EngineModule extends BusModBase
{

	private String persistenceType;

	private JsonObject persistenceSourceConf;

	@Override
	public void start()
	{
		super.start();

		logger.info("Engine starting...");

		setup();

		final PersistorManager manager = createPersistorManager();

		final PersistorAdapter persistorAdapter = manager.createAdapter();
		final String persistorAddress = manager.deploy(container, persistenceSourceConf);

		registerServices(persistorAddress, persistorAdapter);

		logger.info("Engine start done!");
	}

	protected void setup()
	{
		persistenceType = getMandatoryStringConfig(CONFIG_PERSISTENCE_TYPE);
		persistenceSourceConf = getOptionalObjectConfig(CONFIG_PERSISTENCE_SOURCE_CONF, new JsonObject());
	}

	protected PersistorManager createPersistorManager()
	{
		final PersistorManagerFactory factory = new PersistorManagerFactory();
		return factory.createPersistorManager(persistenceType);
	}

	protected void registerServices(final String persistorAddress, final PersistorAdapter persistorAdapter)
	{
		eb.registerHandler(TOTAL_HANDLER_ADDRESS, new DataTotalHandler(eb, persistorAddress, persistorAdapter));
		eb.registerHandler(TOTALHOUR_HANDLER_ADDRESS, new DataTotalHourHandler(eb, persistorAddress, persistorAdapter));
		eb.registerHandler(TOTALITEM_HANDLER_ADDRESS, new DataTotalItemHandler(eb, persistorAddress, persistorAdapter));
	}

}
