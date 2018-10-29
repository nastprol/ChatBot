package db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import chatbot.*;

class testJson {

	@Test
	void test() {
		BotMap fleet = new BotMap();
		Json json = new Json<BotMap>(BotMap.class);
		String jsonString = json.GetStringJson(fleet);
		BotMap fleetExpected = (BotMap)json.GetObject(jsonString);
		assertEquals(true, fleet.EqualMap(fleetExpected));
		assertEquals(true, fleet.getFleet().EqualFleet(fleetExpected.getFleet()));
	}
	
	@Test
	void testBattleSea() {
		BattleSea bs = new BattleSea();
		Json json = new Json<BattleSea>(BattleSea.class);
		String jsonString = json.GetStringJson(bs);
		BattleSea fleetExpected = (BattleSea)json.GetObject(jsonString);
		assertEquals(true, bs.EqualBattleSea(fleetExpected));
		//assertEquals(true, bs.BotMap.getFleet().EqualFleet(fleetExpected.BotMap.getFleet()));
	
	}

}
