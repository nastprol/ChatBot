package chatbot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BotMap extends Map<Integer> {
	
	private int[] map;
	
	public BotMap()
	{ 
		super();
		ArrayList<Integer> visetedCell = this.InitializePossibleCell();
		this.fleet = new Fleet(10, "BotShips");
		this.map = new int[100];
		fillMap(visetedCell);
	}
	
	BotMap(int[] map, int count, int[] positions, int[] orientations, int[] countDecks, int[]scoreAlive)
	{
		this.map = Arrays.copyOf(map, map.length);
		this.fleet = new Fleet(count, positions, orientations, countDecks, scoreAlive);
	}
	
	private ArrayList<Integer> InitializePossibleCell()
	{
		ArrayList<Integer> visetedCell = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++)
			visetedCell.add(i);
		return visetedCell;
	}
	
	private void fillMap(ArrayList<Integer> visetedCell)
	{
		Random random = new Random();
		boolean check = false;
		for(int i = 4; i > 0; i--)
		{
			for (int j = 1; j <= 5 - i; j ++)
			{
				while (!check)
				{	
					int index = random.nextInt(visetedCell.size());
					Orientation orientation = (random.nextInt(2) == 0)
							? Orientation.horizontally
							: Orientation.vertically;
					int position = visetedCell.get(index);
					Tuple coordinates = this.ChangePositionToCoordinates(position);
					check = (orientation == Orientation.vertically)
							? this.CanStay(coordinates.Y, i,coordinates.X, orientation, visetedCell)
							: this.CanStay(coordinates.X, i, coordinates.Y, orientation, visetedCell);
					if (check)
					{
						this.fleet.UpCount();
						this.fleet.BotShips[this.fleet.Count() - 1] = new BotShip(i, position, orientation, this.fleet.Count(), coordinates.X, coordinates.Y);
						BotShip curShip = this.fleet.BotShips[this.fleet.Count() - 1];
						this.markShips(curShip);
						this.SelectionArea(curShip, visetedCell);
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
	protected void ProccessCell(int position, ArrayList<Integer> visetedCell) {
		int index = visetedCell.indexOf(position);
		if (index != -1)
			visetedCell.remove(index);		
	}

	@Override
	protected Report GetStateCell(int x, int y) {
		int position = this.ChangeCoordinatesToPosition(x, y);
		return GetStateCell(position);
	}

	@Override
	protected Report ChangeState(int x, int y) {
		int position = this.ChangeCoordinatesToPosition(x, y);
		if (this.map[position] == 0)
		{
			return Report.miss;
		}
		else
		{
			this.fleet.BotShips[this.map[position] - 1].changState();
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
	protected boolean CheckConditional(int position, ArrayList<Integer> visetedCell) {
		return visetedCell.indexOf(position) == -1;
	}

	@Override
	protected Report GetStateCell(int position) {
		return (this.map[position] == 0)
				? Report.miss
				: Report.damage; 
	}

	@Override
	protected void Set(int position, Report report) {
		if (Report.damage == report || Report.kill == report)
			this.map[position] = 0;
	}

	@Override
	public int[] ChangeReportToInt() {
		int[] result = new int[map.length];
		for (int i = 0; i < map.length; i++)
			result[i] = map[i];
		return result;
	}
	
	public boolean EqualMap(BotMap botMap) {
		boolean result = true;
		for(int i = 0; i < this.map.length; i++)
			result = result && botMap.map[i] == this.map[i];
		return result;
	}

	
}