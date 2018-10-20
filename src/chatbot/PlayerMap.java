package chatbot;

public class PlayerMap extends Map<Report> {
	
	private Report[] map;
	
	public PlayerMap()
	{
		super();
		this.map = new Report[100];
		fillMap(); 
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
	protected void ProccessCell(int position) {
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
	protected boolean CheckConditional(int position) {
		return map[position] != Report.empty && this.map[position] != Report.damage;
	}
}