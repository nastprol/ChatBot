package chatbot;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser implements IParser {
	
	private BattleSea game;
	private final ArrayList answers = new ArrayList(Arrays.asList("miss", "kill", "damage"));
	
	public Parser(BattleSea game) {
		this.game = game;
	}
	
	private boolean coordinatesInFormat(int x, int y) {
		return x < 10 && x > -1 && y < 10 && y > -1;
	} 
	

	public Reply ProcessPlayerAnswer(String command, int id) {
	//	game.initPlayerGame(id);
		if (!game.isActive())
			return new Reply("Game wasn't started", null);
		switch (command) {
		case "damage": {
			if (game.isPlayerTurn())
				return new Reply("It's your turn to shoot", null);
			game.UpdatePlayerMap(Report.damage);
			Tuple point = game.Shoot();
			game.setNotPlayerTurn();
			return new Reply(point.toString(), answers);
		}
		case "kill": {
			if (game.isPlayerTurn())
				return new Reply("It's your turn to shoot", null);
			game.UpdatePlayerMap(Report.kill);
			Tuple point = game.Shoot();
			game.setNotPlayerTurn();
			return new Reply(point.toString(), answers);
		}
		case "miss": {
			if (game.isPlayerTurn())
				return new Reply("It's your turn to shoot", null);
			game.UpdatePlayerMap(Report.miss);
			game.setPlayerTurn();
			return new Reply("Your turn to shoot", null);
		}
		default: {
			if (!game.isPlayerTurn())
				return new Reply("It's not your turn to shoot", null);
			try {
				String[] coord = command.split(" ");
				int y = Integer.parseInt(coord[1]) - 1;
				int x = (int) coord[0].charAt(0) - 96;
				game.setPlayerTurn();
				if (coordinatesInFormat(x, y))
				{
					String check = game.Check(x, y);
					String a = check.split("\n")[0];
					if (a.equals("miss"))
					{
						game.setNotPlayerTurn();
						return new Reply(check, answers);
					}
					return new Reply(check, null);
				}
			} catch (Exception e) {
				return new Reply("Send me coordinates in format <A-I> <1-10>", null);
			}
			return new Reply("Send me coordinates in format <A-I> <1-10>", null);
		}
		}
	}
}
