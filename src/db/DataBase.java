package db;

import java.sql.*;
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
			String sql = "CREATE TABLE IF NOT EXISTS seabattle(" 
					+ "user_id INT PRIMARY KEY NOT NULL, "
					+ "position INT, " + "player_map INT[100], " 
					+ "map INT[100], " 
					+ "find_next_ship BOOLEAN, "
					+ "cur_ship_length INT, " 
					+ "cur_ship_position INT, " 
					+ "cur_ship_orientation INT, "
					+ "fleet_count INT, " // TODO: fleet
					+ "is_active BOOLEAN)";
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
			PreparedStatement stmt = c.prepareStatement("SELECT * FROM seabattle WHERE id = ?;");
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();

			rs.next();
			int id = rs.getInt("id");
			// TODO извлечь поля

			rs.close();
			stmt.close();

			return new DataItem();// TODO конструктор
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	private void removeUserData(int userId) {
        runSql(userId, "DELETE FROM seabattle WHERE id = ?");
    }
	
	private void runSql (int userId, String command) {
        try {
            if (c.isClosed())
                tryConnect();

            PreparedStatement stmt = c.prepareStatement(command);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
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
					"INSERT INTO seabattle (id, current_question_id, game_active) VALUES(?, ?, TRUE)");
			stmt.setInt(1, userData.UserID);//TODO остальные данные

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
