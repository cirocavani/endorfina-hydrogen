package cavani.endorfina.hydrogen.persistence.api;

import static cavani.endorfina.hydrogen.persistence.api.Constants.PERSISTENCE_TYPE_MONGODB;
import cavani.endorfina.hydrogen.persistence.mongodb.MongoDbAdapter;

public class PersistorAdapterFactory
{

	public PersistorAdapter createPersistorAdapter(final String type)
	{
		switch (type)
		{
			case PERSISTENCE_TYPE_MONGODB:
				return new MongoDbAdapter();
			default:
				return null;
		}
	}

}
