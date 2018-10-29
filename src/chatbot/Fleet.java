 package chatbot; 

public class Fleet {
	private int count = 0;
	protected PlayerShip[] Ships;
	protected BotShip[] BotShips;

	public Fleet() {
	}
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
	
	public Fleet(int size, int[] positions, int[] indexesOfOrintation){
		Ships = new PlayerShip[10];
		count = size;
		Orientation[] orientations = Orientation.values();
		for (int i = 0; i < 4; i++) {
			this.Ships[i] = new PlayerShip(positions[i],orientations[indexesOfOrintation[i]], 1);
		}
		;
		for (int i = 0; i < 3; i++) {
			this.Ships[4+i] = new PlayerShip(positions[4+i],orientations[indexesOfOrintation[4+i]], 2);
		}
		;
		for (int i = 0; i < 2; i++) {
			this.Ships[i+7] = new PlayerShip(positions[7+i],orientations[indexesOfOrintation[7+i]], 3);
		}
		;
		this.Ships[9] = new PlayerShip(positions[9],orientations[indexesOfOrintation[9]], 4);
	}
	
	public Fleet(int count, int[] positions, int[] orientations, int[] countDecks, int[]scoreAlive)
	{
		this.count = count;
		this.BotShips = new BotShip[10];
		for(int i = 0; i< positions.length; i++)
		{
			Orientation orientation = (orientations[i] == Orientation.horizontally.ordinal())
					? Orientation.horizontally
					: Orientation.vertically;
			this.BotShips[i] = new BotShip(countDecks[i], positions[i], orientation, i + 1, scoreAlive[i]);
		}
	}
	
	public Ship getPlayerShip(int index) {
		return Ships[index];
	}
	
	public BotShip getBotShip(int index)
	{
		return this.BotShips[index];
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
	
	protected void RegisterKill(PlayerShip ship) {

		for (int i = 0; i < 10; i++) {
			if (this.Ships[i].length == ship.length && this.Ships[i].position == 100) {
				this.Ships[i] = ship;
				break;
			}
			
		}
		this.DownCount();
	}
	
	protected void UpCount()
	{
		this.count++;
	}
	
	protected void DownCount()
	{
		this.count--;
	}
	
	public boolean EqualFleet(Fleet fleet)
	{
		boolean result = true;
		for(int i = 0; i < fleet.BotShips.length; i++)
		{
			result = result &&(fleet.BotShips[i].IdNumber == this.BotShips[i].IdNumber)&&
					(fleet.BotShips[i].length == this.BotShips[i].length)&&
					(fleet.BotShips[i].orientation == this.BotShips[i].orientation);
		}
		return result;
	}
}
