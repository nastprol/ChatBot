package chatbot;

import java.util.ArrayList;
import db.DataBase;
import db.DataItem;

import java.util.Arrays;
import java.util.Random;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Indenter;

public class BattleSea implements IGame {

	private PlayerMap PlayerMap;
	private boolean IsActive;
	private boolean FindNextShip;
	private PlayerShip CurrentShip;
	private int Position;
	private BotMap BotMap;
	private boolean isPlayerTurn;
	private int playerID;

	private String introductionMessage = "This is game Sea Battle. Your turn is first.\n "
			+ "You have field 10*10. There are one 4-deck, two 3-deck, three 2-deck \n"
			+ "and four 1-deck ships. Send me coordinates in format <Letter> <Number>";

	@Override
	public String GetIntroductionMessage() {
		return introductionMessage;
	}

	public BattleSea() {
		PlayerMap = new PlayerMap();
		IsActive = false;
		FindNextShip = true;
		CurrentShip = new PlayerShip(0, 0);
		Position = 0;
		BotMap = new BotMap();
		isPlayerTurn = true;
	}

	public BattleSea(int id) {

		PlayerMap = new PlayerMap();
		IsActive = false;
		FindNextShip = true;
		CurrentShip = new PlayerShip(0, 0);
		Position = 0;
		BotMap = new BotMap();
		isPlayerTurn = true;
		playerID = id;
	}


	public BattleSea(BotMap map) {
		PlayerMap = new PlayerMap();
		IsActive = false;
		FindNextShip = true;
		CurrentShip = new PlayerShip(0, 0);
		Position = 0;
		BotMap = map;
		isPlayerTurn = true;
	}

	public BattleSea(BotMap map, PlayerMap playerMap, boolean findNextShip, PlayerShip ship, int position, boolean playerTurn) {
		PlayerMap = playerMap;
		IsActive = false;
		FindNextShip = findNextShip;
		CurrentShip = ship;
		Position = position;
		BotMap = map;
		isPlayerTurn = playerTurn;
	}

	public boolean isPlayerTurn() {
		return isPlayerTurn;
	}

	public void setPlayerTurn() {
		isPlayerTurn = true;
	}

	public void setNotPlayerTurn() {
		isPlayerTurn = false;
	}

	@Override
	public void SetActive() {
		IsActive = true;
	}

	@Override
	public void SetInactive() {
		IsActive = false;
	}

	protected String Check(int x, int y) {
		Report report = this.BotMap.ChangeState(x, y);
		String answer = report.toString();
		if (report == Report.miss) {
			Tuple point = Shoot();
			answer += "\n" + point.toString();
		}
		IsActive = BotMap.countShipsAlive() != 0;
		return answer;
	}

	protected void UpdatePlayerMap(Report report) {
		this.isPlayerTurn = true;
		if (FindNextShip) {
			FindNextShip = report != Report.damage;
			if (report == Report.damage) {
				CurrentShip.FirstUpdate(Position);
				PlayerMap.Set(Position, Report.damage);
			} else if (report == Report.kill) {
				CurrentShip.FirstUpdate(Position);
				PlayerMap.Set(Position, Report.kill);
				PlayerMap.SelectionArea(CurrentShip, null);
				PlayerMap.fleet.RegisterKill(CurrentShip);
			} else {
				this.isPlayerTurn = false;
				PlayerMap.Set(Position, Report.miss);
			}
		} else {
			FindNextShip = report == Report.kill;
			if (report != Report.miss) {
				CurrentShip.MoveShip(Position);
				PlayerMap.Set(Position, Report.damage);

			}
			if (report == Report.kill) {
				PlayerMap.SelectionArea(CurrentShip, null);
				PlayerMap.fleet.RegisterKill(CurrentShip);
			}
			if (report == Report.miss)
				this.isPlayerTurn = false;
				PlayerMap.Set(Position, Report.miss);
		}
		IsActive = PlayerMap.fleet.Count() != 0;		
	}

	protected Tuple Shoot() {
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

	private void FillProbabilityMap(int[] probability) {
		PlayerShip ship = new PlayerShip(0, 0);
		ship.length = 0;
		for (int i = 0; i < 10; i++) {
			if (PlayerMap.fleet.Ships[i].position == 100 && PlayerMap.fleet.Ships[i].length > ship.length)
				ship.length = PlayerMap.fleet.Ships[i].length;
		}
		Tuple coordinat;
		for (int j = 0; j < 100; j++) {
			coordinat = PlayerMap.ChangePositionToCoordinates(j);
			ship.position = j;
			if (PlayerMap.CanStay(coordinat.X, ship.length, coordinat.Y, Orientation.horizontally, null))
				PlusHorizontally(ship, probability);
			if (PlayerMap.CanStay(coordinat.Y, ship.length, coordinat.X, Orientation.vertically, null))
				PlusVertically(ship, probability);
		}
	}

	private void PlusHorizontally(PlayerShip ship, int[] field) {
		for (int i = ship.position; i < ship.position + ship.length; i++)
			field[i]++;
	}

	private void PlusVertically(PlayerShip ship, int[] field) {
		int j = ship.position;
		while (j <= ship.position + (ship.length - 1) * 10) {
			field[j]++;
			j += 10;
		}
	}

	private int ChooseShotToBeat(PlayerShip ship) {
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

	private void FillDirections(ArrayList<Direction> directions, PlayerShip ship) {

		Tuple coordinat = PlayerMap.ChangePositionToCoordinates(ship.position);

		if (ship.orientation != Orientation.vertically) {
			if (ship.position - 1 >= 0 && PlayerMap.GetStateCell(ship.position - 1) == Report.empty)
				directions.add(Direction.left);

			if (PlayerMap.CanStay(coordinat.X, ship.length + 1, coordinat.Y, Orientation.horizontally, null))
				directions.add(Direction.right);
		}

		if (ship.orientation != Orientation.horizontally) {
			if (PlayerMap.CanStay(coordinat.Y, ship.length + 1, coordinat.X, Orientation.vertically, null))
				directions.add(Direction.down);
			int upPosition = ship.position - 10 * ship.length;
			if (upPosition >= 0 && PlayerMap.GetStateCell(upPosition) == Report.empty)
				directions.add(Direction.up);
		}
	}

	@Override
	public boolean isActive() {
		return this.IsActive;
	}
	
	public boolean EqualBattleSea(BattleSea game)
	{
		return this.BotMap.EqualMap(game.BotMap) &&
		this.PlayerMap.EqualMap(game.PlayerMap) &&
		this.IsActive== game.IsActive &&
		this.FindNextShip == game.FindNextShip &&
		this.Position== game.Position &&
		this.isPlayerTurn == game.isPlayerTurn &&
		this.playerID == game.playerID;
		
	}
	
	public boolean EqualBattleSeaBotMap(BattleSea game) {
		return this.BotMap.EqualMap(game.BotMap);
	}
}