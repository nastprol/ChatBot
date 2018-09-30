package chatbot;

import java.util.ArrayList;
import java.util.Random;

public class Game implements IGame {

	private Report[] PlayerMap;
	private Fleet PlayerFleet;
	private Boolean IsActive;
	private Boolean FindNextShip;
	private Ship CurrentShip;
	private int Position;
	private BotMap BotMap;

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
			var point = Shoot();
			return point.toString();
		}
		case "kill": {
			UpdatePlayerMap(Report.kill);
			var point = Shoot();
			return point.toString();
		}
		case "miss": {
			UpdatePlayerMap(Report.miss);
			return "Your turn";
		}
		default: {
			var coord = command.split(" ");
			var y = Integer.parseInt(coord[1]) - 1;
			var x = (int) coord[0].charAt(0) - 96;
			return Check(x, y);
		}
		}
	}

	public Game() {
		PlayerMap = new Report[100];
		PlayerFleet = new Fleet(10);
		IsActive = false;
		FindNextShip = true;
		CurrentShip = new Ship(0, 0);
		ClearSea(PlayerMap);
		ClearFleet(PlayerFleet);
		Position = 0;
		BotMap = new BotMap();
	}

	public Game(BotMap map) {
		PlayerMap = new Report[100];
		PlayerFleet = new Fleet(10);
		IsActive = false;
		FindNextShip = true;
		CurrentShip = new Ship(0, 0);
		ClearSea(PlayerMap);
		ClearFleet(PlayerFleet);
		Position = 0;
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
		var answer = report.toString();
		if (report == Report.miss) {
			var point = Shoot();
			answer += "\n" + point.toString();
		}
		IsActive = BotMap.countShipsAlive() != 0;
		return answer;
	}

	public void UpdatePlayerMap(Report report) {
		if (FindNextShip) {
			FindNextShip = report != Report.damage;
			if (report == Report.damage) {
				UpdateShip(Position, CurrentShip);
				PlayerMap[Position] = Report.damage;
			} else if (report == Report.kill) {
				UpdateShip(Position, CurrentShip);
				SurroundShip(PlayerMap, CurrentShip);
				RegisterKill(CurrentShip);
			} else {
				PlayerMap[Position] = Report.miss;
			}
		} else {
			FindNextShip = report == Report.kill;
			if (report != Report.miss) {
				MoveShip(Position, CurrentShip);
				PlayerMap[Position] = Report.damage;

			}
			if (report == Report.kill) {
				SurroundShip(PlayerMap, CurrentShip);
				RegisterKill(CurrentShip);
			}
			if (report == Report.miss)
				PlayerMap[Position] = Report.miss;
		}
		IsActive = PlayerFleet.Count != 0;
	}

	public Tuple Shoot() {
		if (FindNextShip) {
			Position = FindNewShip();
		} else {
			Position = ChooseShotToBeat(CurrentShip);
		}
		return new Tuple(Position / 10, Position % 10);

	}

	private void ClearSea(Report[] field) {
		for (int i = 0; i < 100; i++) {
			field[i] = Report.empty;
		}
		;
	}

	private void ClearFleet(Fleet fleet) {
		for (int i = 0; i < 4; i++) {
			fleet.Ships[i] = new Ship(1, 100);
		}
		;
		for (int i = 0; i < 3; i++) {
			fleet.Ships[4 + i] = new Ship(2, 100);
		}
		;
		for (int i = 0; i < 2; i++) {
			fleet.Ships[7 + i] = new Ship(3, 100);
		}
		;
		fleet.Ships[9] = new Ship(4, 100);

	}

	private Integer FindNewShip() {
		int[] probability = new int[100];

		Ship ship = new Ship(0, 0);
		ship.length = 0;
		for (int i = 0; i < 10; i++) {
			if (PlayerFleet.Ships[i].position == 100 && PlayerFleet.Ships[i].length > ship.length)
				ship.length = PlayerFleet.Ships[i].length;
		}
		for (int j = 0; j < 100; j++) {
			ship.position = j;
			if (MayHorizontally(ship, PlayerMap))
				PlusHorizontally(ship, probability);
			if (MayVertically(ship, PlayerMap))
				PlusVertically(ship, probability);
		}
		int max = 0;
		ArrayList<Integer> bestShots = new ArrayList<Integer>();
		for (int k = 0; k < 100; k++) {
			if (probability[k] >= max) {
				if (probability[k] == max) {
					bestShots.add(k);

				} else {
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

	private boolean MayHorizontally(Ship ship, Report[] field) {
		int i = ship.position;
		boolean possible = ship.position % 10 + ship.length - 1 < 10;
		while (i < ship.position + ship.length && possible) {
			possible = (field[i] == Report.empty) || (field[i] == Report.damage);
			i++;
		}
		return possible;
	}

	private boolean MayVertically(Ship ship, Report[] field) {
		int j = ship.position;
		int lastPosition = ship.position + 10 * ship.length;
		boolean possible = lastPosition < 110;
		while (j < lastPosition && possible) {
			possible = (field[j] == Report.empty) || (field[j] == Report.damage);
			j += 10;
		}
		return possible;
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

		int shipLength = ship.length;
		int position = ship.position;

		if (ship.orientation != Orientation.vertically) {
			if (position - 1 >= 0 && PlayerMap[position - 1] == Report.empty)
				directions.add(Direction.left);
			if (MayHorizontally(new Ship(ship.length + 1, ship.position), PlayerMap))
				directions.add(Direction.right);
		}
		
		if (ship.orientation != Orientation.horizontally) {
			if (MayVertically(new Ship(ship.length + 1, ship.position), PlayerMap))
				directions.add(Direction.down);
			int upPosition = ship.position - 10 * ship.length;
			if (upPosition >= 0 && PlayerMap[upPosition] == Report.empty)
				directions.add(Direction.up);
		}
		
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
			position += shipLength;
		else if (direction == Direction.up)
			position -= 10;
		else if (direction == Direction.down)
			position += 10 * shipLength;

		return position;
	}

	private void MoveShip(int position, Ship ship) {
		if (ship.orientation == Orientation.unknown) {
			if (ship.position % 10 == position % 10)
				ship.orientation = Orientation.vertically;
			else
				ship.orientation = Orientation.horizontally;
		}
		if (ship.position > position)
			ship.position = position;
		ship.length++;

	}

	private void SurroundShip(Report[] field, Ship ship) {
		int lt = 0;
		int rb = 0;
		if (ship.orientation == Orientation.horizontally) {
			lt = ship.position - 11;
			rb = ship.position + ship.length + 10;
		} else {
			lt = ship.position - 11;
			rb = ship.position + (ship.length) * 10 + 1;
		}
		if (ship.position < 10)
			lt += 10;
		if (ship.position > 89)
			rb -= 10;
		if (ship.position % 10 == 0)
			lt += 1;
		if (ship.orientation == Orientation.horizontally) {
			if (ship.position % 10 + ship.length > 9)
				rb -= 1;
		} else {
			if (ship.position % 10 == 9)
				rb--;
		}
		int pos = 0;
		for (int i = lt / 10; i <= rb / 10; i++)
			for (int j = lt % 10; j <= rb % 10; j++) {
				pos = i * 10 + j;
				if (field[pos] == Report.empty)
					field[pos] = Report.round;
				if (field[pos] == Report.damage)
					field[pos] = Report.kill;
			}
	}

	private void RegisterKill(Ship ship) {

		for (int i = 0; i < 10; i++) {
			if (PlayerFleet.Ships[i].length == ship.length && PlayerFleet.Ships[i].position == 100) {
				PlayerFleet.Ships[i] = ship;
				break;
			}
			
		}
		PlayerFleet.Count--;
	}

	private void UpdateShip(int position, Ship ship) {
		ship.position = position;
		ship.orientation = Orientation.unknown;
		ship.length = 1;
	}

	@Override
	public Boolean isActive() {
		return this.IsActive;
	}
}