package dialog;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import chatbot.Chatbot;
import chatbot.GameFactory;
import db.DataBase;
import db.IDataBase;

class testSendMessage {

	@Test
	void test() {
		IDataBase db = new TestDataBase();
		
		Chatbot bot = new Chatbot(new GameFactory(), db);
		var test = new testThrottlingAction();
		bot.subscribe(test, 1000);
		
		var array = new ArrayList<Integer>();
		array.add(1);
		array.add(2);
		array.add(3);
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < 3; i++)
			assertEquals(array.get(i), test.list.get(i));
	}
}
