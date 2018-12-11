package chatbot;

public class ProxyConfig implements IProxyConfig{

	private static String PROXY_HOST;
	private static Integer PROXY_PORT;
	private static String PROXY_USER;
	private static String PROXY_PASSWORD;
	
	public ProxyConfig() {
		PROXY_HOST = System.getenv("PROXY_HOST");
		PROXY_PORT = Integer.parseInt(System.getenv("PROXY_PORT"));
		PROXY_USER = System.getenv("PROXY_USER");
		PROXY_PASSWORD = System.getenv("PROXY_PASSWORD");
	}
	
	@Override
	public String getProxyHost() {
		return PROXY_HOST;
	}
	
	@Override
	public Integer getProxyPort() {
		return PROXY_PORT;
	}
	@Override
	public String getProxyUser() {
		return PROXY_USER;
	}
	
	@Override
	public String getProxyPassword() {
		return PROXY_PASSWORD;
	}
}