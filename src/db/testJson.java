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

}
