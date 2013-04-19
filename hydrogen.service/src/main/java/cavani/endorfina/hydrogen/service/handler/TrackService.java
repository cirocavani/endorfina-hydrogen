package cavani.endorfina.hydrogen.service.handler;

import static cavani.endorfina.hydrogen.service.util.Constants.CONFIG_PERSISTENCE_TYPE;
import static cavani.endorfina.hydrogen.service.util.Constants.CONFIG_SOURCE_ADDRESS;
import static cavani.endorfina.hydrogen.service.util.Constants.CONFIG_STORAGE_ADDRESS;
import static cavani.endorfina.hydrogen.service.util.Constants.TRACK_SERVICE_ADDRESS;

import org.vertx.java.busmods.BusModBase;

import cavani.endorfina.hydrogen.persistence.api.PersistorAdapter;
import cavani.endorfina.hydrogen.persistence.api.PersistorAdapterFactory;

public class TrackService extends BusModBase
{

	private String persistenceType;

	private String storageAddress;

	private String sourceAddress;

	@Override
	public void start()
	{
		super.start();

		setup();

		registerServices();
	}

	protected void setup()
	{
		persistenceType = getMandatoryStringConfig(CONFIG_PERSISTENCE_TYPE);
		storageAddress = getMandatoryStringConfig(CONFIG_STORAGE_ADDRESS);
		sourceAddress = getMandatoryStringConfig(CONFIG_SOURCE_ADDRESS);
	}

	protected void registerServices()
	{
		final PersistorAdapter persistorAdapter = createPersistorAdapter();
		eb.registerHandler(TRACK_SERVICE_ADDRESS, new TrackHandler(eb, storageAddress, sourceAddress, persistorAdapter));
	}

	protected PersistorAdapter createPersistorAdapter()
	{
		final PersistorAdapterFactory factory = new PersistorAdapterFactory();
		return factory.createPersistorAdapter(persistenceType);
	}

}
