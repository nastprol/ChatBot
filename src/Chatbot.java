
public class Chatbot {
	
	private Game game; 

	Chatbot(Game game) { 
		this.game = game;
    } 
	
	public String ProcessRequest(String userRequest) {
		String request = userRequest.toLowerCase();
		switch (request) {
			case "start": {
				if(!game.isActive()) {
					game.setActive();
					return game.IntroductionMessage;
				}
				return "Send me coordinates. For example, \"A 6\"";	
				
			}
			case "who are you": {
				return game.IntroductionMessage; 
			}
			default: {
				String[] coord = request.split(" ");
				if(IsInputIncorrect(coord)) {
					return "Send me coordinates in right format. For example, \"A 6\"";
				}
				else {
					return game.Play();
				}
			}
		}
	}
	
	private Boolean IsInputIncorrect(String[] coord) {
		String letter = coord[0];
		Integer letterCode = (int)letter.charAt(0);
		Integer number = Integer.parseInt(coord[1]);
		return coord.length != 2
				|| letter.length() != 1
				|| 96 >= letterCode
				|| letterCode >= 107
				|| number > 10
				|| number < 1;
	}
}
