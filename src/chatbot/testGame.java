package chatbot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

class testGame {
	
	private String CoordinatesToString(int x, int y) {
		String sY = String.valueOf(y + 1);
		String sX = String.valueOf((char) (x + 96));
		return sX + " " + sY;
	}

	@Test
	void test() {
		
		BotMap map = new BotMap();
		Game game = new Game(map,new PlayerMap(), true,new Ship(0,0),0);
		String report = "";
		game.SetActive();
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++)
			{
				if (map.GetStateCell(i, j) != Report.miss) {
					report = game.Play(CoordinatesToString(i, j));
				}
			}
		}
		assertEquals(report == "damage" || report == "kill", true);
	}
	
	@Test
	void testUpdatePlayerMapForOneShip() {
		Random random = new Random();
		for(int i = 0; i <10;i++)
		{
		int x = random.nextInt(8)+1;
		int y = random.nextInt(8)+1;
		int position = x*10+y;
		PlayerMap map = new PlayerMap();
		
		Game game = new Game(new BotMap(),map, true,new Ship(0,0),position);
		
		game.SetActive();
		game.UpdatePlayerMap(Report.kill);
		assertEquals(map.get(position),Report.kill);
		assertEquals(map.get(position-1),Report.round);
		assertEquals(map.get(position+1), Report.round);
		assertEquals(map.get(position-9), Report.round);
		assertEquals(map.get(position-11), Report.round);
		assertEquals(map.get(position-10),Report.round);
		assertEquals(map.get(position+11), Report.round);
		assertEquals(map.get(position+9), Report.round);
		assertEquals(map.get(position+10), Report.round);
		}
	}
	
	@Test
	void testUpdatePlayerMapForMoreShip() {
		
		int position = 23;
		PlayerMap map = new PlayerMap();
		map.set(22,Report.damage);
		map.set(21, Report.damage);
		Ship ship = new Ship(2,21); 
		ship.orientation = Orientation.horizontally;
		Game game = new Game(new BotMap(),map, false, ship,position);
		
		game.SetActive();
		game.UpdatePlayerMap(Report.kill);
		assertEquals(map.get(position), Report.kill);
		assertEquals(map.get(position-1), Report.kill);
		assertEquals(map.get(position-2), Report.kill);
		assertEquals(map.get(position-3), Report.round);
		assertEquals(map.get(position+1), Report.round);
		assertEquals(map.get(position-10), Report.round);
		assertEquals(map.get(position-11), Report.round);
		assertEquals(map.get(position-12), Report.round);
		assertEquals(map.get(position-13), Report.round);
		assertEquals(map.get(position-9), Report.round);
		assertEquals(map.get(position+10), Report.round);
	    assertEquals(map.get(position+9), Report.round);
		assertEquals(map.get(position+8), Report.round);
		assertEquals(map.get(position+7), Report.round);
		assertEquals(map.get(position+11), Report.round);
		}
	

@Test
void testShoot() {
	
	PlayerMap map = new PlayerMap();
	map.set(22,Report.damage);
	map.set(32, Report.damage);
	Ship ship = new Ship(2,22);
	ship.orientation = Orientation.vertically;
	Game game = new Game(new BotMap(),map, false, ship,32);
	game.SetActive();
	Tuple tuple = game.Shoot();
	assertEquals(tuple.X == 2 && (tuple.Y == 1 || tuple.Y == 4), true);
}

@Test
void testShootOneWay() {
	
	PlayerMap map = new PlayerMap();
	map.set(78,Report.damage);
	map.set(88,Report.miss);
	map.set(77,Report.miss);
	Ship ship = new Ship(1,78);
	Game game = new Game(new BotMap(),map, false, ship,77);
	game.SetActive();
	Tuple tuple = game.Shoot();
	assertEquals(tuple.X == 9 && tuple.Y == 7 || tuple.X == 8 && tuple.Y==6, true);
}
	
}	


