package db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import chatbot.*;

class testJson {

	@Test
	void test() {
		BotMap botMap = new BotMap();
		Json json = new Json<BotMap>(BotMap.class);
		String jsonString = json.GetStringJson(botMap);
		BotMap botMarExpected = (BotMap)json.GetObject(jsonString);
		assertEquals(true, botMap.EqualMap(botMarExpected));
		assertEquals(true, botMap.getFleet().EqualFleet(botMarExpected.getFleet()));
	}
	
	@Test
	void testBattleSea() {
		BattleSea bs = new BattleSea();
		Json json = new Json<BattleSea>(BattleSea.class);
		String jsonString = json.GetStringJson(bs);
		BattleSea bsExpected = (BattleSea)json.GetObject(jsonString);
		assertEquals(true, bs.EqualBattleSea(bsExpected));
	}

}
