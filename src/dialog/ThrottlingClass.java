package dialog;
import java.util.*;

import chatbot.TelegramCommunicator;
import junit.framework.Protectable;

public class ThrottlingClass implements IThrottlingClass {
	
	private ArrayDeque<Integer> PriorityIdQueue = new ArrayDeque<Integer>();
	private TelegramCommunicator tg;

	public ThrottlingClass(TelegramCommunicator communicator)
	{
		tg =communicator;
	}
	
	private void traversalDeque()
	{
		Send2000Messege();
		while(PriorityIdQueue.peek()!=null){
			try {
				Thread.sleep(1000*60*60);
				Send2000Messege();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
		
		  
	}
	
	private void Send2000Messege()
	{
		SendMessege();
			for(int i = 0; i < 1999;i++) {
				if(PriorityIdQueue.peek()==null)
					break;
				try {
					Thread.sleep(33);
					SendMessege();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
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
		if(PriorityIdQueue.isEmpty())
		{
			PriorityIdQueue.addAll(ids);
			traversalDeque();
		}
		PriorityIdQueue.addAll(ids);
		
	}
	

}
