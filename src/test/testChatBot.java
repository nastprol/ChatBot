package chatbot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import db.DataBase;

class testChatBot {

	@Test
	void testExitGame(){
		DataBase db = new DataBase();
		db.initDatabase();
		db.connect();
		
		GameFactory gameFactory = new GameFactory();
		Chatbot bot = new Chatbot(gameFactory, db);
		IGame game = gameFactory.create(db, 1);
		Reply answer = bot.ProcessRequest("/exit",3);
		assertEquals(game.isActive(), false);
	}
	
	@Test
	void testStartGame(){
		DataBase db = new DataBase();
		db.initDatabase(); 
		db.connect();
		
		GameFactory gameFactory = new GameFactory();
		Chatbot bot = new Chatbot(gameFactory, db);

		Reply answer = bot.ProcessRequest("/start",4);
		IGame game = bot.getGame();  
		assertEquals(game.isActive(), true);
	}
}
