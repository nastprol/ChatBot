package chatbot; 

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/*import java.util.Scanner;

public class Program {
	@SuppressWarnings("resource")
	public static void main(String[] args) { 
		Scanner scanner = new Scanner(System.in); 
		Chatbot bot = new Chatbot(new GameFactory()); 
		
		 while (true) { 
			 String request = scanner.nextLine(); 
			 System.out.println(bot.ProcessRequest(request)); 
		 }
	}
} */

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Program {

	private static String PROXY_HOST = System.getenv("PROXY_HOST");
	private static Integer PROXY_PORT = Integer.parseInt(System.getenv("PROXY_PORT"));
	private static String PROXY_USER = System.getenv("PROXY_USER");
	private static String PROXY_PASSWORD = System.getenv("PROXY_PASSWORD");

	public static void main(String[] args) {
		
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
		Authenticator.setDefault(new Authenticator() {
			
			
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(PROXY_USER, PROXY_PASSWORD.toCharArray());
			}
		});

		DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
		botOptions.setProxyHost(PROXY_HOST);
		botOptions.setProxyPort(PROXY_PORT);
		botOptions.setProxyType(args.length > 0 && args[0].equals("--dev") ?
                DefaultBotOptions.ProxyType.SOCKS5 : DefaultBotOptions.ProxyType.NO_PROXY);

		try {
			TelegramCommunicator tg = new TelegramCommunicator(botOptions);
			botsApi.registerBot(tg);
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}
	
	}
}
