package cavani.endorfina.hydrogen.persistence.api;

import org.vertx.java.core.json.JsonObject;
import org.vertx.java.deploy.Container;

public interface PersistorManager
{

	String deploy(Container conteiner, JsonObject config);

}
