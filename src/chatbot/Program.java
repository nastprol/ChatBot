package chatbot; 

import java.util.Scanner; 

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
}
