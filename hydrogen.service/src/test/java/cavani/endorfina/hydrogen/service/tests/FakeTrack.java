package cavani.endorfina.hydrogen.service.tests;

import static cavani.endorfina.hydrogen.service.tests.IntegrationTest.VALUES;

import java.util.Date;
import java.util.Map;

import org.vertx.java.core.json.JsonObject;

import cavani.endorfina.hydrogen.persistence.api.PersistorAdapter;
import cavani.endorfina.hydrogen.persistence.api.PersistorOperation;
import cavani.endorfina.hydrogen.service.handler.TrackService;

public class FakeTrack extends TrackService
{

	@Override
	protected PersistorAdapter createPersistorAdapter()
	{
		return new PersistorAdapter()
		{

			@Override
			public JsonObject createUser(final String userId, final Date start, final Date end)
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public JsonObject updateCounter(final String userId, final String item, final Date timestamp)
			{
				return new JsonObject();
			}

			@Override
			public JsonObject cleanup()
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public PersistorOperation<Long[]> fetchCounter(final String userId)
			{
				return new PersistorOperation<Long[]>()
				{

					@Override
					public JsonObject request()
					{
						return new JsonObject();
					}

					@Override
					public Long[] result(final JsonObject result)
					{
						return VALUES;
					}

				};
			}

			@Override
			public PersistorOperation<Map<String, Object>> fetchHour(final String userId)
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public PersistorOperation<Long> fetchCounterItem(final String userId, final String item)
			{
				throw new UnsupportedOperationException();
			}

		};
	}

}
