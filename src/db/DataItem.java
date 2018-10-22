package db;

import chatbot.PlayerShip;
import chatbot.Fleet;
import chatbot.Map;
import chatbot.Report;

public class DataItem {

	public int UserID;
	public int Position;
	
	public int[] PlayerMap;
	public int[] Map;
	
	public boolean FindNextShip;
	public boolean IsActive;
	
	public int CurrShipLength;
	public int CurrShipPosition;
	public int CurrShipOrientation;
	
	public int FleetCount;
	
	//TODO конструктор и поля fleet
	
	public DataItem(int id, int pos, int[] plMap, Map<Report> map, boolean find, boolean active, 
			PlayerShip ship, Fleet fleet) {
		UserID = id;
		Position = pos;
		PlayerMap = plMap;
		FindNextShip = find;
		IsActive = active;
		//TODO
	}
	
	public DataItem() {}
}
