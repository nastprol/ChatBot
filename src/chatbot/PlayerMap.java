package chatbot;

public class PlayerMap extends Map<Report> {
	
	
	private Report[] map;
	
	
	PlayerMap()
	{
		super();
		this.map = new Report[100];
		fillMap();
	}
	
	@Override
	public void Set(int position,Report report)
	{
		map[position]= report;
	}
	
	@Override
	public Report GetStateCell(int position)
	{
		return map[position];
	}
	
	@Override
	public void fillMap()
	{
		for (int i = 0; i < 100; i++) {
			this.map[i] = Report.empty;
		}
	}
	
	
	
	@Override
	public void DoSomthingInArea(int position) {
		if (this.map[position] == Report.empty)
			this.map[position] = Report.round;
		else
			this.map[position] = Report.kill;	
	}

	@Override
	public Report GetStateCell(int x, int y) {
		return this.GetStateCell(this.ChangeCoordinatesToPosition(x, y));
	}

	@Override
	public Report ChangeState(int x, int y) {
		return Report.empty;
		
	}


	@Override
	public boolean CheckConditional(int position) {
		return map[position] != Report.empty && this.map[position] != Report.damage;
	}
}