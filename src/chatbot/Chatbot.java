package chatbot;

public class Chatbot {
	
	private IGame game; 
	private IGameFactory gameFactory;
	
	Chatbot(IGameFactory gameFactory) { 
		this.gameFactory = gameFactory;
        game = this.gameFactory.create();
	} 
	
	public String ProcessRequest(String userRequest) {
		String request = userRequest.toLowerCase();
		switch (request) {
			case "start": {
				if(!game.isActive()) {
					game.SetActive();				
				}
				return game.GetIntroductionMessage();
				
			}
			case "who are you": {
			return game.GetIntroductionMessage(); 
			}
			default: {
				return game.Play(request);
			}
		}
	}
}