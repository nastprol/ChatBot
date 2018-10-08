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
	@Override
	public void fillMap()
	{
		Random random = new Random();
		Boolean check = false;
		for(int i = 4; i > 0; i--)
		{
			for (int j = 1; j <= 5 - i; j ++)
			{
				while (!check)
				{	
					int index = random.nextInt(this.visetedCell.size());
					Orientation orientation = (random.nextInt(2) == 0)
							? Orientation.horizontally
							: Orientation.vertically;
					int position = this.visetedCell.get(index);
					Tuple coordinates = this.ChangePositionToCoordinates(position);
					check = (orientation == Orientation.vertically)
							? this.CanStay(coordinates.Y, i,coordinates.X, orientation)
							: this.CanStay(coordinates.X, i, coordinates.Y, orientation);
					if (check)
					{
						this.fleet.UpCount();
						this.fleet.BotShips[this.fleet.Count() - 1] = new BotShip(i, position, orientation, this.fleet.Count(), coordinates.X, coordinates.Y);
						BotShip curShip = this.fleet.BotShips[this.fleet.Count() - 1];
						this.markShips(curShip);
						this.SelectionArea(curShip);
					}
				}
				check = false;
			}
		}
	}
	
	private void markShips(BotShip ship)
	{
		if (ship.Orientation() == Orientation.vertically)
		{
			for (int j = ship.Y; j < ship.Y + ship.CountDeck(); j++)
				this.map[this.ChangeCoordinatesToPosition(ship.X, j)] = ship.IdNumber;
		}
		else
		{
			for (int i = ship.X; i < ship.X + ship.CountDeck(); i++)
				this.map[this.ChangeCoordinatesToPosition(i, ship.Y)] = ship.IdNumber;
		}
	}
	
	public int countShipsAlive()
	{
		return this.fleet.Count();
	}
	
	
	@Override
	public void DoSomthingInArea(int position) {
		int index = this.visetedCell.indexOf(position);
		if (index != -1)
			this.visetedCell.remove(index);		
	}

	@Override
	public Report GetStateCell(int x, int y) {
		int position = this.ChangeCoordinatesToPosition(x, y);
		return GetStateCell(position);
	}

	@Override
	public Report ChangeState(int x, int y) {
		int position = this.ChangeCoordinatesToPosition(x, y);
		if (this.map[position] == 0)
		{
			return Report.miss;
		}
		else
		{
			this.fleet.BotShips[this.map[position] - 1].ÑhageState();
			State currentState = this.fleet.BotShips[this.map[position] - 1].State();
			this.map[position] = 0;
			if (currentState == State.killed)
			{
				this.fleet.DownCount();
				return Report.kill;
			}
			else
			{
				return Report.damage;
			}
		}
	}

	@Override
	public Boolean CheckConditional(int position) {
		return this.visetedCell.indexOf(position) == -1;
	}

	@Override
	public Report GetStateCell(int position) {
		return (this.map[position] == 0)
				? Report.miss
				: Report.damage; 
	}

	@Override
	public void Set(int position, Integer report) {
		// TODO Auto-generated method stub
		
	}


}