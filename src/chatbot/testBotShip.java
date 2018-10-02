package chatbot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testBotShip {

	int sizeMap = 10;
	int countDeck = 4;
	int position = 20;
	Orientation orientation = Orientation.horizontally;
	int number = 1;
	int x = position % sizeMap;
	int y = position / sizeMap;

	@Test
	void testBotShipInit() {
		BotShip ship = new BotShip(countDeck, position, orientation, number, x, y);
		assertEquals(State.alive, ship.State());
		assertEquals(countDeck, ship.CountDeck());
		assertEquals(position, ship.Position());
		assertEquals(orientation, ship.Orientation());
		assertEquals(number, ship.IdNumber);
		assertEquals(x, ship.X);
		assertEquals(y, ship.Y);
		assertEquals(countDeck, ship.ScoreAlive());
	}
	
	@Test
	void testBotShipChangeStateDamaged() {
		BotShip ship = new BotShip(countDeck, position, orientation, number, x, y);
		ship.ÑhageState();
		assertEquals(State.damaged, ship.State());
		assertEquals(countDeck - 1, ship.ScoreAlive());
		assertEquals(countDeck, ship.CountDeck());
		assertEquals(position, ship.Position());
		assertEquals(orientation, ship.Orientation());
		assertEquals(number, ship.IdNumber);
		assertEquals(x, ship.X);
		assertEquals(y, ship.Y);
	}
	
	@Test
	void testBotShipChangeStateKilled() {
		BotShip ship = new BotShip(countDeck, position, orientation, number, x, y);
		for (int i = 0; i < countDeck; i++)
			ship.ÑhageState();
		assertEquals(State.killed, ship.State());
		assertEquals(0, ship.ScoreAlive());
		assertEquals(countDeck, ship.CountDeck());
		assertEquals(position, ship.Position());
		assertEquals(orientation, ship.Orientation());
		assertEquals(number, ship.IdNumber);
		assertEquals(x, ship.X);
		assertEquals(y, ship.Y);
	}

}
