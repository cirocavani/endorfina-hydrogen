package cavani.endorfina.hydrogen.server.service;

import java.util.List;
import java.util.Map;

import org.vertx.java.core.json.JsonObject;

public interface ServiceAdapter
{

	List<String> parameters();

	JsonObject message(Map<String, String> values);

	String result(JsonObject result);

}
