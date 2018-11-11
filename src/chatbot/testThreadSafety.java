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
		BotMap map = new BotMap();
		for(int i = 0; i < 10; i++)
			map.ChangeState(0, i);
		BattleSea game = new BattleSea(map);
		int id1 = 1234567899;
		int id2 = 1234567890;
		char letter1 = 'A';
		char letter2 = 'B';
		bot.ProcessRequest("/start", id1);
		bot.ProcessRequest("/start", id2);
		SentMessageTest thread1 = new SentMessageTest(bot, letter1, id1);
		SentMessageTest thread2 = new SentMessageTest(bot, letter2, id2);
		thread1.start();
		thread2.start();
		BattleSea game1 = (BattleSea)db.getData(id1);
		assertEquals(game1.EqualBattleSeaBotMap(game), true);
	}

}
