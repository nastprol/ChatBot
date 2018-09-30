package chatbot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testBotMap {

	int CountDamagedCell(BotMap botMap)
	{
		var curDamagedCell = 0;
		for (var i = 0; i < botMap.size; i++)
		{
			for (var j = 0; j < botMap.size; j++)
			{
				if (botMap.GetStateCell(i, j) == Report.damage)
					curDamagedCell++;
			}
		}
		return curDamagedCell;
	}
	@Test
	void testBotMapInit() {
		var damagedCell = 20;
		var botMap= new BotMap();
		var curDamagedCell = this.CountDamagedCell(botMap);
		assertEquals(10, botMap.size);
		assertEquals(10, botMap.countShipsAlive());
		assertEquals(damagedCell, curDamagedCell);
	}
	
	@Test
	void testBotMapEnd() {
		var killedShip = 10;
		var curKilledShip = 0;
		var botMap = new BotMap();
		for (var i = 0; i < botMap.size; i++)
		{
			for (var j = 0; j < botMap.size; j++)
			{
				if (botMap.GetStateCell(i, j) == Report.damage)
				{
					if (botMap.ChangeState(i, j) == Report.kill)
						curKilledShip++;
				}
			}
		}
		var curDamagedCell = this.CountDamagedCell(botMap);
		assertEquals(0, botMap.countShipsAlive());
		assertEquals(killedShip, curKilledShip);
		assertEquals(0, curDamagedCell);
	}
}
