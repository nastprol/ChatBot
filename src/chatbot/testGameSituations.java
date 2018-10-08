package chatbot;
/*
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

		var map = new BotMap();
		var game = new BattleSea(map);
		var report = "";
		game.SetActive();
		for (var i = 0; i < 10; i++) {
			for (var j = 0; j < 10; j++) {
				if (map.GetStateCell(i, j) != Report.miss) {
					report = game.Play(CoordinatesToString(i, j));
					break;
				}
			}
		}
		assertEquals(report == "damage" || report == "kill", true);
	}

	@Test
	void testMissShip() {

		var map = new BotMap();
		var game = new BattleSea(map);
		var report = "";
		game.SetActive();
		for (var i = 0; i < 10; i++) {
			for (var j = 0; j < 10; j++) {
				if (map.GetStateCell(i, j) == Report.miss) {
					report = game.Play(CoordinatesToString(i, j));
					break;
				}
			}
		}
		System.out.println(report.substring(0, 4));
		assertEquals(report.substring(0, 4), "miss");
	}

	@Test
	void testKillAllShips() {

		var map = new BotMap();
		var game = new BattleSea(map);
		var report = "";
		game.SetActive();
		var count = 0;
		for (var i = 0; i < 10; i++) {
			for (var j = 0; j < 10; j++) {
				if (map.GetStateCell(i, j) != Report.miss) {
					report = game.Play(CoordinatesToString(i, j));
					game.Play("miss");
					if (report == "kill")
						count += 1;
				}
			}
		}
		assertEquals(game.isActive(), false);
		assertEquals(count, 10);
	}

	@Test
	void testKillPlayerShip() {

		var map = new BotMap();
		var game = new BattleSea(map);
		game.SetActive();

		for (var i = 0; i < 10; i++) {
			game.Play("kill");
		}
		assertEquals(game.isActive(), false);
	}
	

}*/
