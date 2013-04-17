package cavani.endorfina.hydrogen.persistence.api;

import org.vertx.java.core.json.JsonObject;

public interface PersistorOperation<T>
{

	JsonObject request();

	T result(JsonObject result);

}
