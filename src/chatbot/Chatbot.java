package chatbot;

public class Chatbot {

	private IGame game;
	private IGameFactory gameFactory;
	private IParser parser;

	Chatbot(IGameFactory gameFactory) {
		this.gameFactory = gameFactory;
		game = this.gameFactory.create();
		parser = this.gameFactory.createParser();
	}
	
	public IGame getGame()
	{
		return game;
	}

	public String ProcessRequest(String userRequest) {

		String request = userRequest.toLowerCase();
		switch (request) {
		case "exit":{
			this.game.SetInactive();
			return "Game is over";
		}
		case "":
		{
			return "";
		}
		case "\n":
		{
			return "";
		}
		case "restart": {
			game = this.gameFactory.create();
			game.SetActive();
			return "New game is started"; 

		}
		case "start": {
			if (!game.isActive()) {
				game.SetActive();
			}
			return game.GetIntroductionMessage();

		}
		case "who are you": {
			return game.GetIntroductionMessage();
		}
		default: {
			return parser.ProcessPlayerAnswer(request);
		}
		}
	}
}