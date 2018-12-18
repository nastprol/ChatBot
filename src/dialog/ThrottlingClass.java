package dialog;

import java.util.*;

import chatbot.TelegramCommunicator;
import junit.framework.Protectable;

public class ThrottlingClass implements IThrottlingClass {

	private final ArrayDeque<Integer> PriorityIdQueue = new ArrayDeque<Integer>();
	private static Integer sentMessages = 0;
	private IThrottlingAction action;
	
	public ThrottlingClass(IThrottlingAction action) {
		this.action = action;
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
				action.Send(PriorityIdQueue.pop());
				Thread.sleep(33);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.sentMessages = 2000;
	}

	

	public void Throttling(List<Integer> ids) {
		PriorityIdQueue.addAll(ids);
		traversalDeque();
	}

}
