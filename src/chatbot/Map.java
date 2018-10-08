package chatbot;

public abstract class Map<T> {
	protected int size;
	protected Fleet fleet;


	public Map()
	{
		this.size = 10;
		this.fleet = new Fleet(10);
	}
	
	public boolean CanStay( int checkCoordinats, int deck, int secondCoordinates, Orientation orientation)
	{
		boolean result = true;
		for(int j = checkCoordinats; j < checkCoordinats + deck; j++)
		{
			int position = (orientation == Orientation.vertically)
					? this.ChangeCoordinatesToPosition(secondCoordinates, j)
					: this.ChangeCoordinatesToPosition(j, secondCoordinates);
			if (j >= this.size ||  this.CheckConditional(position))
			{
				result = false;
				break;
			}
		}
		return result;
	}
	
	public int ChangeCoordinatesToPosition(int x, int y)
	{
		return y*this.size + x;
	}
	
	public Tuple ChangePositionToCoordinates(int position)
	{
		return new Tuple(position % this.size, position / this.size);
	}
	
	public void SelectionArea(Ship ship)
	{
		Tuple coordinate = this.ChangePositionToCoordinates(ship.position);
		int y = coordinate.Y;
		int x = coordinate.X;
		int startY = (y - 1 >= 0) ? y - 1 : y;
		int startX = (x - 1 >= 0) ? x - 1 : x;
		if (ship.orientation == Orientation.horizontally)
			this.GoToMap(startX, startY, startX + ship.length + 2, startY + 3);
		else
			this.GoToMap(startX, startY, startX + 3, startY + ship.length + 2);
	}
	
	public void GoToMap(int startX, int startY, int endX, int endY)
	{
		for (int i = startX; i < endX; i++)
		{
			for(int j = startY; j < endY; j++)
			{

				if (j < 10 && i < 10)

				{

					int position = this.ChangeCoordinatesToPosition(i, j);
					this.DoSomthingInArea(position);
				}
			}
		}
	}
	public abstract void DoSomthingInArea(int position);
	public abstract Report GetStateCell(int x, int y);
	public abstract Report ChangeState(int x, int y);
	public abstract void fillMap();
	public abstract boolean CheckConditional(int position);
	public abstract Report GetStateCell(int position);
	public abstract void Set(int position, T report);
	public int countShipsAlive()
	{
		return this.fleet.Count();
	}
}
