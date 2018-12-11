package chatbot;

public class SentMessageTest extends Thread {
	IBot Bot;
	int Id;
	private String message;

	
	SentMessageTest(IBot Bot, int Id, String message){
		this.Bot = Bot;
		this.Id = Id;
		this.message = message;
	}
	
	@Override
	public void run()
	{
		Bot.ProcessRequest(message, this.Id);
		return;
	}
}
