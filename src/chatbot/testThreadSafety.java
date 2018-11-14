package chatbot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import db.DataBase;
import db.IDataBase;

class testThreadSafety {

	@Test
	void test() throws InterruptedException {
		
		IDataBase db = new DataBase();
		db.initDatabase();
		db.connect();
		Chatbot bot = new Chatbot(new GameFactory(), db);
		int id1 = 2; 
		char letter = 'a';
		SentMessageTest[] array = new SentMessageTest[1000];
		BattleSea[] gameAr = new BattleSea[1000];
		for(var i = 0; i< 1000; i++) {
			bot.ProcessRequest("/start", id1 + i);
			gameAr[i] = (BattleSea)db.getData(id1 + i);
			BattleSeaParser parser = new BattleSeaParser(gameAr[i]);
			int count = (i/10) % 10 + 1;
			String message = letter + " " + count;
			parser.ProcessPlayerAnswer(message, id1 + i);
			array[i] = new SentMessageTest(bot, id1 + i, message);
			if((i/10) % 10 == 0 && i >=100) {
				letter = (char)(letter + 1);
			}
			
			array[i].start();
		}

			for (var t : array) {
				
				t.join();
				
			}
		System.out.println(1);
		for(var i = 0; i < 1000; i++)
			assertEquals(gameAr[i].EqualBattleSeaNotFull((BattleSea)db.getData(id1 + i)), true);
	}

}
