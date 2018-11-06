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

	Chatbot(IGameFactory gameFactory, IDataBase db) {
		this.gameFactory = gameFactory;
		this.db = db;
	}
	
	protected IGame getGame() {
		return game;
	}

	public Reply ProcessRequest(String userRequest, int id) {

		String request = userRequest.toLowerCase();
		
		switch (request) {
		case "/help":{
			return new Reply("/exit if you wanna leave this game\n"
					+ "/start if you wanna start or restart game\n"
					+ "/whoareyou if you wanna get know game's rules", null);
		}
		case "/exit":{
			game = this.gameFactory.create(db, id);
			parser = this.gameFactory.createParser();
			
			this.game.SetInactive();
			db.removeUserData(id);

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

			db.removeUserData(id);
			
			game = this.gameFactory.create(db, id);
			parser = this.gameFactory.createParser();
			game.SetActive();
			
			var answer = new Reply(game.GetIntroductionMessage(), null);
			db.setDataItem(id, game);
			return answer;
		}
		case "/whoareyou": {
			return new Reply(game.GetIntroductionMessage(), null);
		}
		default: {
			game = this.gameFactory.create(db, id);
			parser = this.gameFactory.createParser();
			
			var answer = parser.ProcessPlayerAnswer(request, id);
			db.setDataItem(id, game);
			return answer;
		}
		}
	}
}