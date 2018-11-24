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

import db.DataBase;
import db.IDataBase;
import dialog.HourTimer;
import dialog.ITimer;

public class Program {

	private static IProxyConfig pc;

	public static void main(String[] args) {
		
		pc = new ProxyConfig(); 
		
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
		Authenticator.setDefault(new Authenticator() {
			
			
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(pc.getProxyUser(), pc.getProxyPassword().toCharArray());
			}
		});

		DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
		botOptions.setProxyHost(pc.getProxyHost());
		botOptions.setProxyPort(pc.getProxyPort());
		botOptions.setProxyType(args.length > 0 && args[0].equals("--dev") ?
                DefaultBotOptions.ProxyType.SOCKS5 : DefaultBotOptions.ProxyType.NO_PROXY);

		try {
			IDataBase db = new DataBase();

			db.initDatabase();
			db.connect();

			ITimer timer = new HourTimer();
			timer.start();
			
			Chatbot bot = new Chatbot(new GameFactory(), db);
			
			BotConfig cf = new BotConfig();
			TelegramCommunicator tg = new TelegramCommunicator(botOptions, bot, cf);
			botsApi.registerBot(tg);
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}
	
	}
}
