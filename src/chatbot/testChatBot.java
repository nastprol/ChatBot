package chatbot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testChatBot {

	@Test
	void testExitGame(){
		GameFactory gameFactory = new GameFactory();
		Chatbot bot = new Chatbot(gameFactory);
		IGame game = gameFactory.create();
		Reply answer = bot.ProcessRequest("exit",3);
		assertEquals(game.isActive(), false);
	}
	
	@Test
	void testStartGame(){
		GameFactory gameFactory = new GameFactory();
		Chatbot bot = new Chatbot(gameFactory);
		IGame game = bot.getGame();
		assertEquals(game.isActive(), false);
		Reply answer = bot.ProcessRequest("start",4);
		assertEquals(game.isActive(), true);
	}
	
	
	@Test
	void testRestartGame(){
		GameFactory gameFactory = new GameFactory();
		Chatbot bot = new Chatbot(gameFactory);
		IGame game = bot.getGame();
		Reply answer = bot.ProcessRequest("start",3);
		assertEquals(game.isActive(), true);
		answer = bot.ProcessRequest("exit",3);
		assertEquals(game.isActive(), false);
		answer = bot.ProcessRequest("restart",3);
		assertEquals(bot.getGame().isActive(), true);
	}
}
