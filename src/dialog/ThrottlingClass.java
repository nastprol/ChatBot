package dialog;

import java.util.*;

import chatbot.TelegramCommunicator;
import junit.framework.Protectable;

public class ThrottlingClass implements IThrottlingClass {

	private final ArrayDeque<Integer> PriorityIdQueue = new ArrayDeque<Integer>();
	private TelegramCommunicator tg;
	private static Integer sentMessages = 0;

	public ThrottlingClass(TelegramCommunicator communicator) {
		tg = communicator;
	}

	private void traversalDeque() {
		while (!PriorityIdQueue.isEmpty()) {
			try {
				SendMesseges();
				synchronized (sentMessages) {
					if (sentMessages == 2000) {
						Thread.sleep(1000 * 60 * 60);
						sentMessages = 0;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void SendMesseges() {
		for (int i = 0; i < 1999; i++) {
			if (PriorityIdQueue.peek() == null) {
				synchronized (sentMessages) {
					this.sentMessages += i + 1;
				}
				return;
			}
			try {
				SendMessege();
				Thread.sleep(33);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.sentMessages = 2000;
	}

	private void SendMessege() {
		int id = PriorityIdQueue.pop();
		try {
			tg.SendMessage(id, "Would you like to play?");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Throttling(List<Integer> ids) {
		PriorityIdQueue.addAll(ids);
		traversalDeque();
	}

}
