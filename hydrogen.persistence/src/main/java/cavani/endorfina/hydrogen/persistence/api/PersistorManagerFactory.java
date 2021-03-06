package cavani.endorfina.hydrogen.persistence.api;

import static cavani.endorfina.hydrogen.persistence.api.Constants.PERSISTENCE_TYPE_MONGODB;

import cavani.endorfina.hydrogen.persistence.mongodb.MongoDbManager;

public class PersistorManagerFactory
{

	public PersistorManager createPersistorManager(final String type)
	{
		switch (type)
		{
			case PERSISTENCE_TYPE_MONGODB:
				return new MongoDbManager();
			default:
				return null;
		}
	}

}
