package chatbot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testGameSituations {

	private String CoordinatesToString(int x, int y) {
		String sY = String.valueOf(y + 1);
		String sX = String.valueOf((char) (x + 96));
		return sX + " " + sY;
	}

	@Test
	void testDamageOneShip() {
		BotMap map = new BotMap();
		BattleSea game = new BattleSea(map, true);
		Reply reply = null;
		Parser parser = new  Parser(game);
		int id = 1;
		game.SetActive();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (map.GetStateCell(i, j) != Report.miss) {
					reply = parser.ProcessPlayerAnswer(CoordinatesToString(i,j), id);
					break;
				}
			}
		}
		assertEquals(reply.botAnswer == "damage" || reply.botAnswer == "kill", true);
	}

	@Test
	void testMissShip() {

		BotMap map = new BotMap();
		BattleSea game = new BattleSea(map, true);
		Reply reply = null;
		Parser parser = new  Parser(game);
		int id = 1;
		game.SetActive();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (map.GetStateCell(i, j) == Report.miss) {
					game.setPlayerTurn();
					reply = parser.ProcessPlayerAnswer(CoordinatesToString(i,j), id);
					break;
				}
			}
		}
		assertEquals(reply.botAnswer.substring(0, 4), "miss");
	}

	@Test
	void testKillAllShips() {

		BotMap map = new BotMap();
		BattleSea game = new BattleSea(map, true);
		Reply reply = null;
		Parser parser = new  Parser(game);
		int id = 1;
		game.SetActive();
		int count = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (map.GetStateCell(i, j) != Report.miss) {
					reply = parser.ProcessPlayerAnswer(CoordinatesToString(i,j), id);
					parser.ProcessPlayerAnswer("miss", id);
					if (reply.botAnswer == "kill")
						count += 1;
					
				}
			}
		}
		assertEquals(game.isActive(), false);
		assertEquals(count, 10);
	}

	@Test
	void testKillPlayerShip() {

		BotMap map = new BotMap();
		BattleSea game = new BattleSea(map, false);
		Parser parser = new  Parser(game);
		int id = 1;
		game.SetActive();

		for (int i = 0; i < 10; i++) {
			parser.ProcessPlayerAnswer("kill", id);
		}
		assertEquals(game.isActive(), false);
	}
	

}
