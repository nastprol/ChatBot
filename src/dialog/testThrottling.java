package dialog;

import org.junit.jupiter.api.Test;

import chatbot.BotConfig;
import chatbot.Chatbot;
import chatbot.GameFactory;
import chatbot.TelegramCommunicator;

import static org.junit.jupiter.api.Assertions.*;
import  java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class testThrottling {
	
	@Test
	void testThrottling() {
		
		List<Integer> ids = Arrays.asList(1,2,3,4,5);
		testThrottlingAction action = new testThrottlingAction();
		IThrottlingClass tc = new ThrottlingClass(action);
		tc.Throttling(ids);
		assertEquals(action.list.size(),5);
		assertEquals(action.list.get(0),new Integer(1));
		assertEquals(action.list.get(1),new Integer(2));
		assertEquals(action.list.get(2),new Integer(3));
		assertEquals(action.list.get(3),new Integer(4));
		assertEquals(action.list.get(4),new Integer(5));
		
	}
	

}
