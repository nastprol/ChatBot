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
		char letter = 'A';
		SentMessageTest[] array = new SentMessageTest[100];
		BattleSea[] gameAr = new BattleSea[100];
		for(var i = 0; i< 100; i++) {
			bot.ProcessRequest("/start", id1 + i);
			array[i] = new SentMessageTest(bot, letter, id1 + i, i % 10 + 1);
			gameAr[i] = (BattleSea)db.getData(id1 + i);
			BattleSeaParser parser = new BattleSeaParser(gameAr[i]);
			int count = i % 10 + 1;
			StringBuilder message = new StringBuilder();
			if (count < 10)
				message.append(letter).append(' ').append((char)count);
			else
				message.append(letter).append(' ').append("10");
			parser.ProcessPlayerAnswer(message.toString(), id1 + i);
			if(i % 10 == 0 && i >=10) {
				letter = (char)(letter + 1);
			}
			
			array[i].start();
		}

			for (var t : array) {
				
				t.join();
				
			}
		System.out.println(1);
		for(var i = 0; i < 100; i++)
			System.out.println(gameAr[i].EqualBattleSeaNotFull((BattleSea)db.getData(id1 + i)));
	}

}
