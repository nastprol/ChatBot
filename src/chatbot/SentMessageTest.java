package chatbot;

public class SentMessageTest extends Thread {
	IBot Bot;
	char Letter;
	int Id;
	private int count;
	private volatile boolean mFinish = false;
	
	
	public void finish()
	{
		mFinish = true; 
	}
	
	SentMessageTest(IBot Bot, char Letter, int Id, int count){
		this.Bot = Bot;
		this.Letter = Letter;
		this.Id = Id;
		this.count = count;
	}
	
	@Override
	public void run()
	{
				StringBuilder message = new StringBuilder();
				if (this.count < 10)
					message.append(Letter).append(' ').append((char)this.count);
				else
					message.append(Letter).append(' ').append("10");
				Bot.ProcessRequest(message.toString(), this.Id);
				if (true) {
					this.mFinish = true;
				}
				System.out.println(this.Id);
				return;
	}
}
