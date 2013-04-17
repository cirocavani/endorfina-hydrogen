package cavani.endorfina.hydrogen.dashboard.service;

public final class ServiceRegistry
{

	public static final String ENGINE_DATA_TOTAL_PATH = "/total";

	public static final String ENGINE_DATA_TOTALHOUR_PATH = "/total/hour";

	public static final String ENGINE_DATA_TOTALITEM_PATH = "/total";

	public static final String ENGINE_DATA_TOTALITEM_ADDRESS = "data.totalitem";

	public static final String ENGINE_DATA_TOTALHOUR_ADDRESS = "data.totalhour";

	public static final String ENGINE_DATA_TOTAL_ADDRESS = "data.total";

	public static final HttpService ENGINE_DATA_TOTAL = new HttpService(ENGINE_DATA_TOTAL_PATH, ENGINE_DATA_TOTAL_ADDRESS, new EngineTotalService());

	public static final HttpService ENGINE_DATA_TOTALHOUR = new HttpService(ENGINE_DATA_TOTALHOUR_PATH, ENGINE_DATA_TOTALHOUR_ADDRESS, new EngineTotalHourService());

	public static final HttpService ENGINE_DATA_TOTALITEM = new HttpService(ENGINE_DATA_TOTALITEM_PATH, ENGINE_DATA_TOTALITEM_ADDRESS, new EngineTotalItemService());

	private ServiceRegistry()
	{
	}

}
