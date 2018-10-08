package chatbot;

public class Parser implements IParser {
	
	private BattleSea game;
	
	public Parser(BattleSea game) {
		this.game = game;
	}
	
	private boolean coordinatesInFormat(int x, int y) {
		return x < 10 && x > -1 && y < 10 && y > -1;
	} 
	
	public String ProcessPlayerAnswer(String command) {
		if (!game.isActive())
			return "Game wasn't started";
		switch (command) {
		case "damage": {
			if (game.isPlayerTurn())
				return "It's your turn";
			game.UpdatePlayerMap(Report.damage);
			var point = game.Shoot();
			game.setNotPlayerTurn();
			return point.toString();
		}
		case "kill": {
			if (game.isPlayerTurn())
				return "It's your turn";
			game.UpdatePlayerMap(Report.kill);
			var point = game.Shoot();
			game.setNotPlayerTurn();
			return point.toString();
		}
		case "miss": {
			if (game.isPlayerTurn())
				return "It's your turn";
			game.UpdatePlayerMap(Report.miss);
			game.setPlayerTurn();
			return "Your turn";
		}
		default: {
			if (!game.isPlayerTurn())
				return "It's not your turn";
			try {
				var coord = command.split(" ");
				var y = Integer.parseInt(coord[1]) - 1;
				var x = (int) coord[0].charAt(0) - 96;
				game.setPlayerTurn();
				if (coordinatesInFormat(x, y))
				{
					String check = game.Check(x, y);
					var a = check.split("\n")[0];
					if (a.equals("miss"))
					{
						game.setNotPlayerTurn();
					}
					return check;
				}
			} catch (Exception e) {
				return "Send me coordinates in format <A-I> <1-10>";
			}
			return "Send me coordinates in format <A-I> <1-10>";
		}
		}
	}
}
