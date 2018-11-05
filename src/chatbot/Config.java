package chatbot;

public class Config {
	private static String BOT_TOKEN;
	private static String BOT_USERNAME;
	
	public Config() {
	BOT_USERNAME = System.getenv("BOT_USERNAME");
    BOT_TOKEN = System.getenv("BOT_TOKEN");
	}
	
	public String getBotUsername() {
		return BOT_USERNAME;
	}
	
	public String getBotToken() {
		return BOT_TOKEN;
	}
}
