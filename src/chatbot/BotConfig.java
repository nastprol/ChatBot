package chatbot;

public class BotConfig implements IBotConfig{
	private static String BOT_TOKEN;
	private static String BOT_USERNAME;
	
	public BotConfig() {
	BOT_USERNAME = System.getenv("BOT_USERNAME");
    BOT_TOKEN = System.getenv("BOT_TOKEN");
	}
	
	@Override
	public String getBotUsername() {
		return BOT_USERNAME;
	}
	
	@Override
	public String getBotToken() {
		return BOT_TOKEN;
	}
}
