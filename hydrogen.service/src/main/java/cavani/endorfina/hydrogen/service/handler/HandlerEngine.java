package cavani.endorfina.hydrogen.service.handler;

import org.vertx.java.core.json.JsonObject;

public interface HandlerEngine<MessageType, ResultType>
{

	MessageType parseMessage(JsonObject message);

	JsonObject processResult(ResultType result);

}
