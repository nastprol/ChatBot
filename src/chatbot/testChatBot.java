package chatbot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testChatBot {

	@Test
	void testExitGame(){
		var gameFactory = new GameFactory();
		var bot = new Chatbot(gameFactory);
		var game = gameFactory.create();
		var answer = bot.ProcessRequest("exit");
		assertEquals(game.isActive(), false);
	}
	
	@Test
	void testStartGame(){
		var gameFactory = new GameFactory();
		var bot = new Chatbot(gameFactory);
		var game = bot.getGame();
		assertEquals(game.isActive(), false);
		var answer = bot.ProcessRequest("start");
		assertEquals(game.isActive(), true);
	}
	
	@Test
	void testRestartGame(){
		var gameFactory = new GameFactory();
		var bot = new Chatbot(gameFactory);
		var game = bot.getGame();
		var answer = bot.ProcessRequest("start");
		assertEquals(game.isActive(), true);
		answer = bot.ProcessRequest("exit");
		assertEquals(game.isActive(), false);
		answer = bot.ProcessRequest("restart");
		assertEquals(bot.getGame().isActive(), true);
	}
}
