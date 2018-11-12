package chatbot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import db.DataBase;
import db.IDataBase;

class testThreadSafety {

	@Test
	void test() {
		
		IDataBase db = new DataBase();
		db.initDatabase();
		db.connect();
		Chatbot bot = new Chatbot(new GameFactory(), db);
		int id1 = 1234567899;
		int id2 = 1234567890;
		char letter1 = 'A';
		char letter2 = 'B';
		bot.ProcessRequest("/start", id1);
		bot.ProcessRequest("/start", id2);
		BattleSea game = (BattleSea)db.getData(id1);
		BattleSeaParser parser = new BattleSeaParser(game);
		for(int i = 1; i < 11; i++) {
			String message = new StringBuilder().append(letter1).append(' ').append((char)i).toString();
			parser.ProcessPlayerAnswer(message, id1);
			parser.ProcessPlayerAnswer("miss", id1);
		}
		SentMessageTest thread1 = new SentMessageTest(bot, letter1, id1);
		SentMessageTest thread2 = new SentMessageTest(bot, letter2, id2);
		thread1.start();
		thread2.start();
		while(thread1.isAlive() || thread2.isAlive());
		BattleSea game1 = (BattleSea)db.getData(id1);
		assertEquals(game1.EqualBattleSeaNotFull(game), true);
	}

}
