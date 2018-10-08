package chatbot; 

public class Fleet {
	private int count = 0;
	public PlayerShip[] Ships;
	public BotShip[] BotShips;

	public Fleet(int size){
		Ships = new PlayerShip[size];
		count = size;
		for (int i = 0; i < 4; i++) {
			this.Ships[i] = new PlayerShip(1, 100);
		}
		;
		for (int i = 0; i < 3; i++) {
			this.Ships[4 + i] = new PlayerShip(2, 100);
		}
		;
		for (int i = 0; i < 2; i++) {
			this.Ships[7 + i] = new PlayerShip(3, 100);
		}
		;
		this.Ships[9] = new PlayerShip(4, 100);
	}
	
	public int Count()
	{
		return this.count;
	}
	
	public Fleet(int size, String report)
	{
		if (report.compareTo("BotShips") == 0)
		{
			this.BotShips = new BotShip[size];
			count = 0;
		}
	}
	
	public void RegisterKill(PlayerShip ship) {

		for (int i = 0; i < 10; i++) {
			if (this.Ships[i].length == ship.length && this.Ships[i].position == 100) {
				this.Ships[i] = ship;
				break;
			}
			
		}
		this.DownCount();
	}
	
	public void UpCount()
	{
		this.count++;
	}
	
	public void DownCount()
	{
		this.count--;
	}
}
