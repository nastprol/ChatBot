package chatbot;

import java.util.ArrayList;
import java.util.Random;

public class MapMyShips {
	private int[][] map;
	private Ship[] ships;
	private ArrayList<Tuple> visetedCell;
	private int numberShip;
	
	MapMyShips()
	{
		fillMap();
	}
	
	public void initializeViseted()
	{
		this.visetedCell = new ArrayList<Tuple>();
		for (int i = 0; i < 10; i++)
			for (var j = 0; j < 10; j++)
				this.visetedCell.add(new Tuple(i, j));
	}
	
	public void fillMap()
	{
		var random = new Random();
		while(true)
		{
			this.map = new int[10][10];
			this.initializeViseted();
			ships = new Ship[10];
			this.numberShip = 0;
loop3:		for(var i = 4; i > 0; i--)
			{
				for (var j = 1; j <= 5 - i; j ++)
				{
					while (true)
					{	
						var index = random.nextInt(this.visetedCell.size());
						var direction = (random.nextInt(2) == 0)
								? Direction.horizontally
								: Direction.vertically;
						var cell = this.visetedCell.get(index);
						var check = (direction == Direction.horizontally)
								? free(cell.y, i)
								: free(cell.x, i);
						if (check)
						{
							this.numberShip++;
							this.ships[this.numberShip - 1] = new Ship(i, cell.x, cell.y, direction, this.numberShip);
							this.visetedCell.remove(index);
							this.markShips(this.ships[this.numberShip - 1]);
							this.delte(cell.x, cell.y, direction, i);
							break;
						}
						if (this.visetedCell.size() == 0)
							break loop3;
					}
				}
			}
			if (this.numberShip == 10)
				break;
		}
	}
	
	private void delte(int X, int Y, Direction direction, int deck)
	{
		var startY = (Y - 1 >= 0) ? Y - 1 : Y;
		var startX = (X - 1 >= 0) ? X - 1 : X;
		if (direction == Direction.horizontally)
		{
			for (int i = startX; i < startX + 3; i++)
			{
				for(int j = startY; j < startY + deck + 2; j++)
				{
					if (j < 10 && i < 10)
					{
						var index = this.visetedCell.indexOf(new Tuple(i, j));
						if (index == -1)
							continue;
						this.visetedCell.remove(index);
					}

				}
			}
		}
		else
		{
			for (int i = startX; i < startX + deck + 2; i++)
			{
				for (int j = startY; j < startY + 3; j++)
				{
					if (j < 10 && i < 10)
					{
						var index = this.visetedCell.indexOf(new Tuple(i, j));
						if (index == -1)
							continue;
						this.visetedCell.remove(index);
					}
				}
			}
		}
	}
	
	private Boolean free( int checkCoordinats, int deck)
	{
		var result = true;
		for(int j = checkCoordinats; j < checkCoordinats + deck; j++)
		{
			if (j >= 10)
			{
				result = false;
				break;
			}
		}
		return result;
	}
	
	private void markShips(Ship ship)
	{
		if (ship.direction() == Direction.horizontally)
		{
			for (var j = ship.Y(); j < ship.Y() + ship.countDeck(); j++)
				this.map[ship.X()][j] = ship.number();
		}
		else
		{
			for (var i = ship.X(); i < ship.X() + ship.countDeck(); i++)
				this.map[i][ship.Y()] = ship.number();
		}
	}
	
	public int countShipsAlive()
	{
		return this.numberShip;
	}
	
	public State changeState(int X, int Y)
	{
		var currentState = State.alive;
		if (this.map[X][Y] == 0)
		{
			return State.alive;
		}
		else
		{
			this.ships[this.map[X][Y] - 1].chageState();
			currentState = this.ships[this.map[X][Y] - 1].state();
			if (currentState == State.dead)
				this.numberShip--;
			this.map[X][Y] = 0;
		}
		return currentState;
	}
}