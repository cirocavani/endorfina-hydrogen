package cavani.endorfina.hydrogen.persistence.api;

import cavani.endorfina.hydrogen.persistence.mongodb.MongoDbManager;

public class PersistorManagerFactory
{

	public static final String PERSISTENCE_TYPE_MONGODB = "MongoDB";

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
