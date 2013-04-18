package cavani.endorfina.hydrogen.dashboard.service;

import cavani.endorfina.hydrogen.network.http.HttpService;

public final class ServiceFactory
{

	public static final String ENGINE_DATA_TOTAL_PATH = "/total";

	public static final String ENGINE_DATA_TOTALHOUR_PATH = "/total/hour";

	public static final String ENGINE_DATA_TOTALITEM_PATH = "/total";

	public static final String ENGINE_DATA_TOTALITEM_ADDRESS = "data.totalitem";

	public static final String ENGINE_DATA_TOTALHOUR_ADDRESS = "data.totalhour";

	public static final String ENGINE_DATA_TOTAL_ADDRESS = "data.total";

	private ServiceFactory()
	{
	}

	public static final HttpService EngineDataTotal()
	{
		return new HttpService(ENGINE_DATA_TOTAL_PATH, ENGINE_DATA_TOTAL_ADDRESS, new EngineTotalService());
	}

	public static final HttpService EngineDataTotalHour()
	{
		return new HttpService(ENGINE_DATA_TOTALHOUR_PATH, ENGINE_DATA_TOTALHOUR_ADDRESS, new EngineTotalHourService());
	}

	public static final HttpService EngineDataTotalItem()
	{
		return new HttpService(ENGINE_DATA_TOTALITEM_PATH, ENGINE_DATA_TOTALITEM_ADDRESS, new EngineTotalItemService());
	}

}
