package dialog;
import java.util.*;

import chatbot.TelegramCommunicator;
import junit.framework.Protectable;

public class ThrottlingClass implements IThrottlingClass {
	
	private static ArrayDeque<Integer> PriorityIdQueue = new ArrayDeque<Integer>();
	private TelegramCommunicator tg;
	private static int sentMessages = 0;

	public ThrottlingClass(TelegramCommunicator communicator)
	{
		tg = communicator;
	}
	
	private synchronized void traversalDeque()
	{
		Send2000Messege();
		while(!PriorityIdQueue.isEmpty()){
			try {
				if (sentMessages == 2000) {
					Thread.sleep(1000*60*60);
					sentMessages = 0;
				}
				Send2000Messege();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
	}
	
	private synchronized void Send2000Messege()
	{
		SendMessege();
			for(int i = 0; i < 1999;i++) {
				if(PriorityIdQueue.peek()==null) {
					this.sentMessages += i + 1;
					return;
				}
				try {
					Thread.sleep(33);
					SendMessege();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
			this.sentMessages = 2000;
	}
	
	private void SendMessege() {
		int id = PriorityIdQueue.pop();
		try {
		tg.SendMessage(id, "не хочешь сыграть в морской бой?");
		} catch (Exception e) {;
		}
		System.out.println(id);
	}

	public void Throttling(List<Integer> ids) {
		PriorityIdQueue.addAll(ids);
		traversalDeque();
	}
	

}
