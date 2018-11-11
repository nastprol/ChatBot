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
	
	SentMessageTest(IBot Bot, char Letter, int Id){
		this.Bot = Bot;
		this.Letter = Letter;
		this.Id = Id;
		this.count = 0;
	}
	
	@Override
	public void run()
	{	do {
			if(!mFinish) {
				this.count++;
				String message = new StringBuilder().append(Letter).append(' ').append((char)this.count).toString();
				Bot.ProcessRequest(message, this.Id);
				Bot.ProcessRequest("miss", this.Id);
				if (this.count == 10) {
					this.mFinish = true;
				}
			}
			else
				return;
			
			try{
				sleep(1000);
			}
			catch(InterruptedException e){};
		}
		while(true);
	}
}
