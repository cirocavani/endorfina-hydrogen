package cavani.endorfina.hydrogen.engine.data;

import static cavani.endorfina.hydrogen.engine.util.Constants.CONFIG_PERSISTENCE_TYPE;
import static cavani.endorfina.hydrogen.engine.util.Constants.CONFIG_PERSISTOR_ADDRESS;
import static cavani.endorfina.hydrogen.engine.util.Constants.TOTALHOUR_HANDLER_ADDRESS;
import static cavani.endorfina.hydrogen.engine.util.Constants.TOTALITEM_HANDLER_ADDRESS;
import static cavani.endorfina.hydrogen.engine.util.Constants.TOTAL_HANDLER_ADDRESS;

import org.vertx.java.busmods.BusModBase;

import cavani.endorfina.hydrogen.persistence.api.PersistorAdapter;
import cavani.endorfina.hydrogen.persistence.api.PersistorAdapterFactory;

public class DataService extends BusModBase
{

	private String persistenceType;

	private String persistorAddress;

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
		persistorAddress = getMandatoryStringConfig(CONFIG_PERSISTOR_ADDRESS);

	}

	protected void registerServices()
	{
		final PersistorAdapter persistorAdapter = createPersistorAdapter();
		eb.registerHandler(TOTAL_HANDLER_ADDRESS, new DataTotalHandler(eb, persistorAddress, persistorAdapter));
		eb.registerHandler(TOTALHOUR_HANDLER_ADDRESS, new DataTotalHourHandler(eb, persistorAddress, persistorAdapter));
		eb.registerHandler(TOTALITEM_HANDLER_ADDRESS, new DataTotalItemHandler(eb, persistorAddress, persistorAdapter));
	}

	protected PersistorAdapter createPersistorAdapter()
	{
		final PersistorAdapterFactory factory = new PersistorAdapterFactory();
		return factory.createPersistorAdapter(persistenceType);
	}

}
