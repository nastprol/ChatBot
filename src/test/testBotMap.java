package chatbot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testBotMap {

	int CountDamagedCell(BotMap botMap)
	{
		int curDamagedCell = 0;
		for (int i = 0; i < botMap.size; i++)
		{
			for (int j = 0; j < botMap.size; j++)
			{
				if (botMap.GetStateCell(i, j) == Report.damage)
					curDamagedCell++;
			}
		}
		return curDamagedCell;
	}
	@Test
	void testBotMapInit() {
		int damagedCell = 20;
		BotMap botMap= new BotMap();
		int curDamagedCell = this.CountDamagedCell(botMap);
		assertEquals(10, botMap.size);
		assertEquals(10, botMap.countShipsAlive());
		assertEquals(damagedCell, curDamagedCell);
	}
	
	@Test
	void testBotMapEnd() {
		int killedShip = 10;
		int curKilledShip = 0;
		BotMap botMap = new BotMap();
		for (int i = 0; i < botMap.size; i++)
		{
			for (int j = 0; j < botMap.size; j++)
			{
				if (botMap.GetStateCell(i, j) == Report.damage)
				{
					if (botMap.ChangeState(i, j) == Report.kill)
						curKilledShip++;
				}
			}
		}
		int curDamagedCell = this.CountDamagedCell(botMap);
		assertEquals(0, botMap.countShipsAlive());
		assertEquals(killedShip, curKilledShip);
		assertEquals(0, curDamagedCell);
	}
}
