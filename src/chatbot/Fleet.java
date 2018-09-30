package chatbot; 

public class Fleet {
	public int Count = 0;
	public Ship[] Ships;
	public BotShip[] BotShips;

	public Fleet(int size){
		Ships = new Ship[size];
		Count = size;
	}
	
	public Fleet(int size, String report)
	{
		if (report.compareTo("BotShips") == 0)
		{
			this.BotShips = new BotShip[size];
			Count = 0;
		}
	}
}
