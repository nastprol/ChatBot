package dialog;

import chatbot.TelegramCommunicator;

public class MessageSender implements IThrottlingAction {
 
	private TelegramCommunicator tg;
	
	public MessageSender(TelegramCommunicator communicator) {
		tg = communicator;
	}
	
	public void Send(int id) {
		try {
			tg.SendMessage(id, "Would you like to play?");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
