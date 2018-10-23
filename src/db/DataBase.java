package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class DataBase {

	private Connection c;

	public void connect() {
		try {
			String dbUrl = System.getenv("DB_URL");
			c = DriverManager.getConnection(dbUrl);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void tryConnect() {
		try {
			c.close();
			connect();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void initDatabase() {
		try {
			if (c.isClosed())
				tryConnect();

			Statement stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS seabattle(" + "user_id INT PRIMARY KEY NOT NULL, "
					+ "position INT, " + "player_map INT[100], " + "map INT[100], " + "find_next_ship BOOLEAN, "
					+ "cur_ship_length INT, " + "cur_ship_position INT, " + "cur_ship_orientation INT, "
					+ "plr_fleet_count INT, " + "plr_fleet_ships_pos INT[10], " + "plr_fleet_ships_ornt INT[10], "
					+ "count_alive_bot_ships INT, " + "bot_ornt_ships INT[10], " + "bot_pos_ships INT[10], "
					+ "bot_count_deck_ships INT[10], " + "bot_score_alive_ships INT[10], " + "is_active BOOLEAN)";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public DataItem getData(int userId) {
		try {
			if (c.isClosed())
				tryConnect();
			PreparedStatement stmt = c.prepareStatement("SELECT * FROM seabattle WHERE user_id = ?;");
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();

			rs.next();
			int id = rs.getInt("user_id");
			int pos = rs.getInt("position");

			int[] playerMap = (int[]) rs.getArray("player_map").getArray();
			int[] map = (int[]) rs.getArray("map").getArray();
			boolean find = rs.getBoolean("find_next_ship");

			int shipLength = rs.getInt("cur_ship_length");
			int shipPos = rs.getInt("cur_ship_position");
			int shipOrnt = rs.getInt("cur_ship_orientation");
			int fleetCount = rs.getInt("plr_fleet_count");
			int[] fleetPos = (int[]) rs.getArray("plr_fleet_ships_pos").getArray();
			int[] fleetOrnt = (int[]) rs.getArray("plr_fleet_ships_ornt").getArray();
			boolean active = rs.getBoolean("is_active");

			int BotCountAliveShips = rs.getInt("count_alive_bot_ships");
			int[] BotCountDeckShips = (int[]) rs.getArray("bot_count_deck_ships").getArray();
			int[] BotScoreAliveShips = (int[]) rs.getArray("bot_score_alive_ships").getArray();
			int[] BotOrientationShips = (int[]) rs.getArray("bot_ornt_ships").getArray();
			int[] BotPositionShips = (int[]) rs.getArray("bot_pos_ships").getArray();

			// я так и не придумала как из Array сделать int[]
			rs.close();
			stmt.close();
			return new DataItem(id, pos, map, playerMap, find, active, shipLength, shipOrnt, shipPos, fleetCount,
					fleetPos, fleetOrnt, BotPositionShips, BotOrientationShips, BotScoreAliveShips, BotCountDeckShips,
					BotCountAliveShips);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	private void removeUserData(int userId) {
		runSql(userId, "DELETE FROM seabattle WHERE user_id = ?");
	}

	private void runSql(int userId, String command) {
		try {
			if (c.isClosed())
				tryConnect();

			PreparedStatement stmt = c.prepareStatement(command);
			stmt.setInt(1, userId);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void setDataItem(int userId, DataItem userData) {
		try {
			if (c.isClosed())
				tryConnect();

			removeUserData(userId);

			PreparedStatement stmt;
			stmt = c.prepareStatement(
					"INSERT INTO seabattle () VALUES(?, ?, TRUE)");
			
			
			stmt.setInt(1, userData.UserID);
			stmt.setInt(2,userData.Position);
			stmt.setArray(3, c.createArrayOf("int",  this.changeIntToInteger(userData.PlayerMap)));
			stmt.setArray(4, c.createArrayOf("int",  this.changeIntToInteger(userData.Map)));
			stmt.setBoolean(5, userData.FindNextShip);
			stmt.setInt(6, userData.CurrShipLength);
			stmt.setInt(7, userData.CurrShipPosition);
			stmt.setInt(8, userData.CurrShipOrientation);
			stmt.setInt(9, userData.PlayerFleetCount);
			stmt.setArray(10, c.createArrayOf("int",  this.changeIntToInteger(userData.PlayerFleetShipsPosition)));
			stmt.setArray(11, c.createArrayOf("int",  this.changeIntToInteger(userData.PlayerFleetShipsOrientation)));
			stmt.setBoolean(12, userData.IsActive);
			stmt.setInt(13, userData.BotCountAliveShips);
			stmt.setArray(14, c.createArrayOf("int",  this.changeIntToInteger(userData.BotCountDeckShips)));
			stmt.setArray(15, c.createArrayOf("int",  this.changeIntToInteger(userData.BotOrientationShips)));
			stmt.setArray(16, c.createArrayOf("int",  this.changeIntToInteger(userData.BotPositionShips)));
			stmt.setArray(17, c.createArrayOf("int",  this.changeIntToInteger(userData.BotScoreAliveShips)));

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public Integer[] changeIntToInteger(int[] primitiveArray)
	{
		Integer[] objectArray = new Integer[primitiveArray.length];

		for(int ctr = 0; ctr < primitiveArray.length; ctr++) {
		    objectArray[ctr] = Integer.valueOf(primitiveArray[ctr]);
		}
		 return objectArray;
	}
	
	public boolean checkId(int idUser)
	{
		boolean isUserExists = false;
        try (PreparedStatement ps = c.prepareStatement("select 1 from seabattle where user_id = ?")) {
            ps.setInt(1, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    isUserExists = true;
                }
            }
        }
        finally {
        	return isUserExists;
        }
	}
}
