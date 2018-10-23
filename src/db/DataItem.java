package db;

import chatbot.PlayerShip;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Indenter;

import chatbot.BotShip;
import chatbot.Fleet;
import chatbot.Map;
import chatbot.Report;
import chatbot.Ship;

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
	
	public int PlayerFleetCount;
	public int[] PlayerFleetShipsPosition;
	public int[] PlayerFleetShipsOrientation;
	
	public int BotCountAliveShips;
	public int[] BotCountDeckShips;
	public int[] BotScoreAliveShips;
	public int[] BotOrientationShips;
	public int[] BotPositionShips;

	public DataItem(int id, int pos, Map<Integer> botMap, Map<Report> playerMap, boolean find, boolean active, 
			PlayerShip ship, Fleet playerFleet, Fleet botFleet) {
		UserID = id;
		Position = pos;
		PlayerMap = playerMap.ChangeReportToInt();
		FindNextShip = find;
		IsActive = active;
		CurrShipLength = ship.getLength();
		CurrShipPosition = ship.getPosition();
		CurrShipOrientation = ship.getOrientationToInt();
		PlayerFleetCount = playerFleet.Count();
		PlayerFleetShipsPosition = new int[PlayerFleetCount];
		PlayerFleetShipsOrientation = new int [PlayerFleetCount];
		Map = botMap.ChangeReportToInt();
		for(int i = 0; i < PlayerFleetCount; i ++)
		{
			Ship fleetShip = playerFleet.getPlayerShip(i);
			PlayerFleetShipsPosition[i]= fleetShip.getPosition();
			PlayerFleetShipsOrientation[i] = fleetShip.getOrientationToInt();
		}
		for(int i = 0; i < 10; i ++)
		{
			BotShip fleetShip = playerFleet.getBotShip(i);
			BotPositionShips[i]= fleetShip.Position();
			BotOrientationShips[i] = fleetShip.getOrientationToInt();
			BotScoreAliveShips[i] = fleetShip.ScoreAlive();
			BotCountDeckShips[i] = fleetShip.CountDeck();
		}
		BotCountAliveShips = playerFleet.Count();
	}
	
	public DataItem(int id, int pos, int[] map, int[] playerMap, boolean find, boolean active, 
			int shipLength, int shipOrnt, int shipPos, int fleetCount, int[] fleetPos, int[] fleetOrnt, int[] botFleetPos,
			int[] botFleetOrnt, int[] scoreAlive, int[] botShipsDeck, int BotCountAliveShips) {
		UserID = id;
		Position = pos;
		PlayerMap = playerMap;
		FindNextShip = find;
		IsActive = active;
		CurrShipLength = shipLength;
		CurrShipPosition = shipPos;
		CurrShipOrientation = shipOrnt;
		PlayerFleetCount = fleetCount;
		PlayerFleetShipsPosition = fleetPos;
		PlayerFleetShipsOrientation = fleetOrnt;
		BotPositionShips = botFleetPos;
		BotOrientationShips = botFleetOrnt;
		BotScoreAliveShips = scoreAlive;
		BotCountDeckShips = botShipsDeck;
		this.BotCountAliveShips = BotCountAliveShips;
	}
	
	public DataItem() {}
}
