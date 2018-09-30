package chatbot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class test1 {
	
	private String CoordinatesToString(int x, int y) {
		String sY = String.valueOf(y + 1);
		String sX = String.valueOf((char) (x + 96));
		return sX + " " + sY;
	}

	@Test
	void test() {
		
		var map = new BotMap();
		var game = new Game(map);
		var report = "";
		game.SetActive();
		for(var i = 0; i < 10; i++) {
			for (var j = 0; j < 10; j++)
			{
				if (map.GetStateCell(i, j) != Report.miss) {
					report = game.Play(CoordinatesToString(i, j));
				}
			}
		}
		assertEquals(report == "damage" || report == "kill", true);
	}

}
