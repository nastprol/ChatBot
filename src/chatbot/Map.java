package chatbot;

//import java.util.ArrayList;

public abstract class Map<T> {
	protected int size;
	//protected T[] map;
	protected Fleet fleet;


	public Map()
	{
		this.size = 10;
		this.fleet = new Fleet(10);
	}
	
	public Boolean CanStay( int checkCoordinats, int deck, Boolean conditional)
	{
		var result = true;
		for(int j = checkCoordinats; j < checkCoordinats + deck; j++)
		{
			if (j >= this.size && conditional)
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
	
	public void SelectionArea(int x, int y, Orientation orientation, int deck)
	{
		var startY = (y - 1 >= 0) ? y - 1 : y;
		var startX = (x - 1 >= 0) ? x - 1 : x;
		if (orientation == Orientation.horizontally)
			this.GoToMap(startX, startY, startX + 3, startY + deck + 2);
		else
			this.GoToMap(startX, startY, startX + deck + 2, startY + 3);
	}
	
	public void GoToMap(int startX, int startY, int endX, int endY)
	{
		for (int i = startX; i < endX; i++)
		{
			for(int j = startY; j < endY; j++)
			{
				if (j < 10 && i < 10)
				{
					var position = this.ChangeCoordinatesToPosition(i, j);
					this.DoSomthingInArea(position);
				}
			}
		}
	}
	public abstract void DoSomthingInArea(int position);
	public abstract Report GetStateCell(int x, int y);
	public abstract Report ChangeState(int x, int y);
	public int countShipsAlive()
	{
		return this.fleet.Count;
	}
}
