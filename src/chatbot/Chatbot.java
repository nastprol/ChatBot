package chatbot;

import java.util.ArrayList;
import java.util.Arrays;

import db.DataBase;
import db.IDataBase;

public class Chatbot implements IBot {

	private IGame game;
	private IGameFactory gameFactory;
	private IParser parser;
	private IDataBase db;

	Chatbot(IGameFactory gameFactory, DataBase db) {
		this.gameFactory = gameFactory;
		this.db = db;
	}
	
	protected IGame getGame() {
		return game;
	}

	public Reply ProcessRequest(String userRequest, int id) {

		String request = userRequest.toLowerCase();
		game = this.gameFactory.create(db, id);
		parser = this.gameFactory.createParser();
		game.SetActive();
		
		
		switch (request) {
		case "/help":{
			return new Reply("/exit if you wanna leave this game\n"
					+ "/start if you wanna start or restart game\n"
					+ "/whoareyou if you wanna get know game's rules", null);
		}
		case "/exit":{
			this.game.SetInactive();
			return new Reply("Game is over", null);
		}
		case "":
		{
			return new Reply("", null);
		}
		case "\n":
		{
			return new Reply("", null);
		}
		case "/start": {

			game.SetActive();
			
			return new Reply(game.GetIntroductionMessage(), null);
		}
		case "/whoareyou": {
			return new Reply(game.GetIntroductionMessage(), null);
		}
		default: {
			return parser.ProcessPlayerAnswer(request, id);
		}
		}
	}
}