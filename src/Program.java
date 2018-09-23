
import java.util.Scanner; 

public class Program {
	public static void main(String[] args) { 
		Scanner scanner = new Scanner(System.in); 
		Chatbot bot = new Chatbot(new Game()); 
		
		 while (true) { 
			 String request = scanner.nextLine(); 
			 System.out.println(bot.ProcessRequest(request)); 
		 }
	}
}
