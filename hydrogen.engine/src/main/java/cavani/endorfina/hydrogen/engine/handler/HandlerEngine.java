package cavani.endorfina.hydrogen.engine.handler;

import org.vertx.java.core.json.JsonObject;

public interface HandlerEngine<MessageType, ResultType>
{

	MessageType parseMessage(JsonObject message);

	JsonObject processResult(ResultType result);

}
