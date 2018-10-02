package chatbot;

import java.util.ArrayList;
import java.util.Random;

public class Game implements IGame {

	private Map<Report> PlayerMap;
	private Boolean IsActive;
	private Boolean FindNextShip;
	private Ship CurrentShip;
	private int Position;
	private Map<Integer> BotMap;

	private String introductionMessage = "This is game Sea Battle. Your turn is first.\n "
			+ "You have field 10*10. There are one 4-deck, two 3-deck, three 2-deck \n"
			+ "and four 1-deck ships. Send me coordinates in format <Letter> <Number>";

	@Override
	public String GetIntroductionMessage() {
		return introductionMessage;
	}
	
	@Override
	public String Play(String command) {
		switch (command) {
		case "damage": {
			UpdatePlayerMap(Report.damage);
			Tuple point = Shoot();
			return point.toString();
		}
		case "kill": {
			UpdatePlayerMap(Report.kill);
			Tuple point = Shoot();
			return point.toString();
		}
		case "miss": {
			UpdatePlayerMap(Report.miss);
			return "Your turn";
		}
		default: {
			String[] coord = command.split(" ");
			int y = Integer.parseInt(coord[1]) - 1;
			int x = (int) coord[0].charAt(0) - 96;
			return Check(x, y);
		}
		}
	}

	public Game() {
		PlayerMap =  new PlayerMap();
		IsActive = false;
		FindNextShip = true;
		CurrentShip = new Ship(0, 0);
		Position = 0;
		BotMap = new BotMap();
	}

	public Game(BotMap map,PlayerMap playerMap, Boolean findNextShip,Ship ship,int position) {
		PlayerMap = playerMap;
		IsActive = false;
		FindNextShip = findNextShip;
		CurrentShip = ship;
		Position = position;
		BotMap = map;
	}

	@Override
	public void SetActive() {
		IsActive = true;
	}

	@Override
	public void SetInactive() {
		IsActive = false;
	}

	public String Check(int x, int y) {
		Report report = this.BotMap.ChangeState(x, y);
		String answer = report.toString();
		if (report == Report.miss) {
			Tuple point = Shoot();
			answer += "\n" + point.toString();
		}
		IsActive = BotMap.countShipsAlive() != 0;
		return answer;
	}

	public void UpdatePlayerMap(Report report) {
		if (FindNextShip) {
			FindNextShip = report != Report.damage;
			if (report == Report.damage) {
				CurrentShip.FirstUpdate(Position);
				PlayerMap.set(Position,Report.damage);
			} else if (report == Report.kill) {
				CurrentShip.FirstUpdate(Position);
				PlayerMap.set(Position, Report.kill);
				PlayerMap.SelectionArea(CurrentShip);
				PlayerMap.fleet.RegisterKill(CurrentShip);
			} else {
				PlayerMap.set(Position,Report.miss);
			}
		} else {
			FindNextShip = report == Report.kill;
			if (report != Report.miss) {
				CurrentShip.MoveShip(Position);
				PlayerMap.set(Position,Report.damage);

			}
			if (report == Report.kill) {
				PlayerMap.SelectionArea(CurrentShip);
				PlayerMap.fleet.RegisterKill(CurrentShip);
			}
			if (report == Report.miss)
				PlayerMap.set(Position, Report.miss);
		}
		IsActive = PlayerMap.fleet.Count != 0;
	}

	public Tuple Shoot() {
		if (FindNextShip) {
			Position = FindNewShip();
		} else {
			Position = ChooseShotToBeat(CurrentShip);
		}
		return PlayerMap.ChangePositionToCoordinates(Position);

	}

	private Integer FindNewShip() {
		int[] probability = new int[100];
		FillProbabilityMap(probability);		
		int max = 0;
		ArrayList<Integer> bestShots = new ArrayList<Integer>();
		for (int k = 0; k < 100; k++) {
			if (probability[k] >= max) {
				if (probability[k] == max) 
					bestShots.add(k);
				else {
					max = probability[k];
					bestShots = new ArrayList<Integer>();
					bestShots.add(k);
				}
			}
		}

		int size = bestShots.size();
		int i = 0;
		if (size > 1) {
			Random random = new Random();
			i = random.nextInt(size - 1);
		}
		return bestShots.get(i);
	}
	
	private void FillProbabilityMap(int[] probability)
	{
		Ship ship = new Ship(0, 0);
		ship.length = 0;
		for (int i = 0; i < 10; i++) {
			if (PlayerMap.fleet.Ships[i].position == 100 && PlayerMap.fleet.Ships[i].length > ship.length)
				ship.length = PlayerMap.fleet.Ships[i].length;
		}
		Tuple coordinat;
		for (int j = 0; j < 100; j++) {
			coordinat = PlayerMap.ChangePositionToCoordinates(j);
			ship.position =j;
			if (PlayerMap.CanStay(coordinat.X, ship.length,coordinat.Y, Orientation.horizontally ))
				PlusHorizontally(ship, probability);
			if (PlayerMap.CanStay(coordinat.Y, ship.length, coordinat.X, Orientation.vertically))
				PlusVertically(ship, probability);
		}
	}
	
	private void PlusHorizontally(Ship ship, int[] field) {
		for (int i = ship.position; i < ship.position + ship.length; i++)
			field[i]++;
	}

	private void PlusVertically(Ship ship, int[] field) {
		int j = ship.position;
		while (j <= ship.position + (ship.length - 1) * 10) {
			field[j]++;
			j += 10;
		}
	}

	
	private int ChooseShotToBeat(Ship ship) {
		ArrayList<Direction> directions = new ArrayList<Direction>();
		int position = ship.position;
		FillDirections(directions, ship);
		
		
		int size = directions.size();
		Direction direction = directions.get(0);
		if (size > 1) {
			Random random = new Random();
			int ron = random.nextInt(size - 1);
			direction = directions.get(ron);
		}
		if (direction == Direction.left)
			position -= 1;
		else if (direction == Direction.right)
			position += ship.length;
		else if (direction == Direction.up)
			position -= 10;
		else if (direction == Direction.down)
			position += 10 * ship.length;

		return position;
	}
	
	private void FillDirections(ArrayList<Direction> directions, Ship ship)
	{
		
		Tuple coordinat = PlayerMap.ChangePositionToCoordinates(ship.position);

		if (ship.orientation != Orientation.vertically) {
			if (ship.position - 1 >= 0 && PlayerMap.get(ship.position - 1) == Report.empty)
				directions.add(Direction.left);
			
			if (PlayerMap.CanStay(coordinat.X, ship.length+1,coordinat.Y, Orientation.horizontally) )
				directions.add(Direction.right);
		}
		
		if (ship.orientation != Orientation.horizontally) {
			if (PlayerMap.CanStay(coordinat.Y,ship.length+1,coordinat.X,  Orientation.vertically) )
				directions.add(Direction.down);
			int upPosition = ship.position - 10 * ship.length;
			if (upPosition >= 0 && PlayerMap.get(upPosition) == Report.empty)
				directions.add(Direction.up);
		}
	}

	@Override
	public Boolean isActive() {
		return this.IsActive;
	}
}