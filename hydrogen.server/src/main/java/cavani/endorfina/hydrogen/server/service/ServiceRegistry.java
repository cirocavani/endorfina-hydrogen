package cavani.endorfina.hydrogen.server.service;

public final class ServiceRegistry
{

	public static final String TRACK_SERVICE_PATH = "/track";

	public static final String TRACK_SERVICE_ADDRESS = "hydrogen.service.track";

	public static final HttpService TRACK_SERVICE = new HttpService(TRACK_SERVICE_PATH, TRACK_SERVICE_ADDRESS, new TrackService());

	private ServiceRegistry()
	{
	}

}
