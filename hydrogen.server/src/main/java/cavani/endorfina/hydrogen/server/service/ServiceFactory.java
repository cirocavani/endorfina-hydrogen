package cavani.endorfina.hydrogen.server.service;

import cavani.endorfina.hydrogen.network.http.HttpService;

public final class ServiceFactory
{

	public static final String TRACK_SERVICE_PATH = "/track";

	public static final String TRACK_SERVICE_ADDRESS = "hydrogen.service.track";

	private ServiceFactory()
	{
	}

	public static final HttpService TrackService()
	{
		return new HttpService(TRACK_SERVICE_PATH, TRACK_SERVICE_ADDRESS, new TrackService());
	}

}
