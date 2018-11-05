package chatbot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import db.DataBase;

class testChatBot {

	@Test
	void testExitGame(){
		var db = new DataBase();
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
		var db = new DataBase();
		db.initDatabase();
		db.connect();
		
		GameFactory gameFactory = new GameFactory();
		Chatbot bot = new Chatbot(gameFactory, db);

		IGame game = bot.getGame();
		assertEquals(game.isActive(), false);
		Reply answer = bot.ProcessRequest("/start",4);
		assertEquals(game.isActive(), true);
	}
	
	
	@Test
	void testRestartGame(){
		var db = new DataBase();
		db.initDatabase();
		db.connect();
		
		GameFactory gameFactory = new GameFactory();
		Chatbot bot = new Chatbot(gameFactory, db);

		IGame game = bot.getGame();
		Reply answer = bot.ProcessRequest("/start",3);
		assertEquals(game.isActive(), true);
		answer = bot.ProcessRequest("/exit",3);
		assertEquals(game.isActive(), false);
		answer = bot.ProcessRequest("/restart",3);
		assertEquals(bot.getGame().isActive(), true);
	}
}
