package chatbot;

import java.util.ArrayList;

public class PlayerMap extends Map<Report> {
	
	private Report[] map;
	
	public PlayerMap()
	{
		super();
		this.map = new Report[100];
		fillMap(); 
	}
	
	public PlayerMap(int[] playerMap, int PlayerFleetCount, 
			int[] fleetShipsPosition, int[] fleetShipOrientation )
	{
		this.fleet = new Fleet(PlayerFleetCount, fleetShipsPosition, fleetShipOrientation);
		this.ChangeIntToReport(playerMap);
	}
	
	@Override
	protected void Set(int position,Report report)
	{
		map[position]= report;
	}
	
	@Override
	protected Report GetStateCell(int position)
	{
		return map[position];
	}
	
	private void fillMap()
	{
		for (int i = 0; i < 100; i++) {
			this.map[i] = Report.empty;
		}
	}
	
	@Override
	public int[] ChangeReportToInt() {
		int[] result = new int[map.length];
		for(int i = 0; i<map.length;i++)
			result[i] = map[i].ordinal();
		return result;
		
	}
	
	private void ChangeIntToReport( int[] mapOfInt) {
		for(int i = 0; i< mapOfInt.length;i++)
			map[i] = Report.values()[mapOfInt[i]];
		
	}
	@Override
	protected void ProccessCell(int position, ArrayList<Report> array) {
		if (this.map[position] == Report.empty)
			this.map[position] = Report.round;
		else
			this.map[position] = Report.kill;	
	}

	@Override
	protected Report GetStateCell(int x, int y) {
		return this.GetStateCell(this.ChangeCoordinatesToPosition(x, y));
	}

	@Override
	protected Report ChangeState(int x, int y) {
		return Report.empty;
		
	}

	@Override
	protected boolean CheckConditional(int position, ArrayList<Report> array) {
		return map[position] != Report.empty && this.map[position] != Report.damage;
	}

	protected boolean EqualMap(PlayerMap playerMap) {
		boolean result = true;
		
		for(int i = 0; i < this.map.length; i++)
			result = result && playerMap.map[i] == this.map[i];
		return result;
	}
}