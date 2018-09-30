package chatbot;

import java.util.ArrayList;
import java.util.Random;

public class BotMap extends Map<Integer> {
	
	private ArrayList<Integer> visetedCell;
	private int[] map;
	
	BotMap()
	{
		super();
		this.InitializePossibleCell();
		this.fleet = new Fleet(10, "BotShips");
		this.map = new int[100];
		fillMap();
	}
	
	public void InitializePossibleCell()
	{
		this.visetedCell = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++)
			this.visetedCell.add(i);
	}
	
	public void fillMap()
	{
		var random = new Random();
		var check = false;
		for(var i = 4; i > 0; i--)
		{
			for (var j = 1; j <= 5 - i; j ++)
			{
				while (!check)
				{	
					var index = random.nextInt(this.visetedCell.size());
					var orientation = (random.nextInt(2) == 0)
							? Orientation.horizontally
							: Orientation.vertically;
					var position = this.visetedCell.get(index);
					check = (orientation == Orientation.horizontally)
							? this.CanStay(position / this.size, i, true)
							: this.CanStay(position % this.size, i, true);
					if (check)
					{
						this.fleet.Count++;
						var coordinates = this.ChangePositionToCoordinates(position);
						this.fleet.BotShips[this.fleet.Count - 1] = new BotShip(i, position, orientation, this.fleet.Count, coordinates.X, coordinates.Y);
						this.visetedCell.remove(index);
						this.markShips(this.fleet.BotShips[this.fleet.Count - 1]);
						this.SelectionArea(position % this.size, position / this.size, orientation, i);
					}
				}
				check = false;
			}
		}
	}
	
	private void markShips(BotShip ship)
	{
		if (ship.Orientation() == Orientation.horizontally)
		{
			for (var j = ship.Y; j < ship.Y + ship.CountDeck(); j++)
				this.map[this.ChangeCoordinatesToPosition(ship.X, j)] = ship.IdNumber;
		}
		else
		{
			for (var i = ship.X; i < ship.X + ship.CountDeck(); i++)
				this.map[this.ChangeCoordinatesToPosition(i, ship.Y)] = ship.IdNumber;
		}
	}
	
	public int countShipsAlive()
	{
		return this.fleet.Count;
	}
	
	
	@Override
	public void DoSomthingInArea(int position) {
		var index = this.visetedCell.indexOf(position);
		if (index != -1)
			this.visetedCell.remove(index);		
	}

	@Override
	public Report GetStateCell(int x, int y) {
		var position = this.ChangeCoordinatesToPosition(x, y);
		return (this.map[position] == 0)
			? Report.miss
			: Report.damage;
	}

	@Override
	public Report ChangeState(int x, int y) {
		var position = this.ChangeCoordinatesToPosition(x, y);
		if (this.map[position] == 0)
		{
			return Report.miss;
		}
		else
		{
			this.fleet.BotShips[this.map[position] - 1].ÑhageState();
			var currentState = this.fleet.BotShips[this.map[position] - 1].State();
			this.map[position] = 0;
			if (currentState == State.killed)
			{
				this.fleet.Count--;
				return Report.kill;
			}
			else
			{
				return Report.damage;
			}
		}
	}
}