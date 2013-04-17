package cavani.endorfina.hydrogen.persistence.api;

import java.util.Date;
import java.util.Map;

import org.vertx.java.core.json.JsonObject;

public interface PersistorAdapter
{

	JsonObject createUser(String userId, Date start, Date end);

	JsonObject updateCounter(String userId, String item, Date timestamp);

	JsonObject cleanup();

	PersistorOperation<Long[]> fetchCounter(String userId);

	PersistorOperation<Map<String, Object>> fetchHour(String userId);

	PersistorOperation<Long> fetchCounterItem(String userId, String item);

}
