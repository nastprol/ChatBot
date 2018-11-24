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
	void testUpdatePlayerMapForOneShip() {
		Random random = new Random();
		for(int i = 0; i <10;i++)
		{
		int x = random.nextInt(8)+1;
		int y = random.nextInt(8)+1;
		int position = x*10+y;
		PlayerMap map = new PlayerMap();
		
		BattleSea game = new BattleSea(new BotMap(),map, true,new PlayerShip(0,0),position, true);
		
		game.SetActive();
		game.UpdatePlayerMap(Report.kill);
		assertEquals(map.GetStateCell(position),Report.kill);
		assertEquals(map.GetStateCell(position-1),Report.round);
		assertEquals(map.GetStateCell(position+1), Report.round);
		assertEquals(map.GetStateCell(position-9), Report.round);
		assertEquals(map.GetStateCell(position-11), Report.round);
		assertEquals(map.GetStateCell(position-10),Report.round);
		assertEquals(map.GetStateCell(position+11), Report.round);
		assertEquals(map.GetStateCell(position+9), Report.round);
		assertEquals(map.GetStateCell(position+10), Report.round);
		}
	}
	
	@Test
	void testUpdatePlayerMapForMoreShip() {
		
		int position = 23;
		PlayerMap map = new PlayerMap();
		map.Set(22,Report.damage);
		map.Set(21, Report.damage);
		PlayerShip ship = new PlayerShip(3,21); 
		ship.orientation = Orientation.horizontally;
		BattleSea game = new BattleSea(new BotMap(),map, false, ship,position, false);
		
		game.SetActive();
		game.UpdatePlayerMap(Report.kill);
		//assertEquals(map.GetStateCell(position), Report.kill);
		assertEquals(map.GetStateCell(position-1), Report.kill);
		assertEquals(map.GetStateCell(position-2), Report.kill);
		assertEquals(map.GetStateCell(position-3), Report.round);
		assertEquals(map.GetStateCell(position+1), Report.round);
		assertEquals(map.GetStateCell(position-10), Report.round);
		assertEquals(map.GetStateCell(position-11), Report.round);
		assertEquals(map.GetStateCell(position-12), Report.round);
		assertEquals(map.GetStateCell(position-13), Report.round);
		assertEquals(map.GetStateCell(position-9), Report.round);
		assertEquals(map.GetStateCell(position+10), Report.round);
	    assertEquals(map.GetStateCell(position+9), Report.round);
		assertEquals(map.GetStateCell(position+8), Report.round);
		assertEquals(map.GetStateCell(position+7), Report.round);
		assertEquals(map.GetStateCell(position+11), Report.round);
		}
	

@Test
void testShoot() {
	
	PlayerMap map = new PlayerMap();
	map.Set(22,Report.damage);
	map.Set(32, Report.damage);
	PlayerShip ship = new PlayerShip(2,22);
	ship.orientation = Orientation.vertically;
	BattleSea game = new BattleSea(new BotMap(),map, false, ship,32,true);
	game.SetActive();
	Tuple tuple = game.Shoot();
	assertEquals(tuple.X == 2 && (tuple.Y == 1 || tuple.Y == 4), true);
}

@Test
void testShootOneWay() {
	
	PlayerMap map = new PlayerMap();
	map.Set(78,Report.damage);
	map.Set(88,Report.miss);
	map.Set(77,Report.miss);
	PlayerShip ship = new PlayerShip(1,78);
	BattleSea game = new BattleSea(new BotMap(),map, false, ship,77,true);
	game.SetActive();
	Tuple tuple = game.Shoot();
	assertEquals(tuple.X == 9 && tuple.Y == 7 || tuple.X == 8 && tuple.Y==6, true);
}
	
}	


