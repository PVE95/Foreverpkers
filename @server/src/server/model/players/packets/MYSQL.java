package server.model.players.packets;
import java.sql.*;
import java.security.MessageDigest;

public class MYSQL {

        public static Connection con = null;
        public static Statement stm;

        public static void createConnection() {
                try {
                       // Class.forName("com.mysql.jdbc.Driver").newInstance();
                       // con = DriverManager.getConnection("jdbc:mysql://fpps1.db.7993694.hostedresource.com:3306/fpps1", "fpps1", "Chicken1");
                        
						//con = DriverManager.getConnection("jdbc:mysql://184.173.246.237:3306/foreverp_runelimited", "foreverp_runelim", "deathx222");
                        

				//stm = con.createStatement();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }


	public static ResultSet query(String s) throws SQLException {
		try {
			if (s.toLowerCase().startsWith("select")) {
				ResultSet rs = stm.executeQuery(s);
				return rs;
			} else {
				stm.executeUpdate(s);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	public static void destroyCon() {
		try {
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		public static int getDonatePoints(String playerName) {
		try {
			ResultSet group = stm.executeQuery("SELECT * FROM stats WHERE username = '"+playerName+"'");
			while(group.next()) {
				int dp = group.getInt("dp");
				return dp;
			}
		} catch(Exception e) {
		createConnection();
		return 0;
	}
	return 0;
	}
	
	/*public static boolean checkVotes(String playerName)
	{
		try {
			Statement statement = con.createStatement();
			String query = "SELECT * FROM votes WHERE username = '" + playerName + "'";
			ResultSet results = statement.executeQuery(query);
			while(results.next()) {
				int recieved = results.getInt("recieved");
				if(recieved == 0)
				{
				return true;
				}
				
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	*/


public static int getReward(String playerName) {
		try {
			ResultSet group = stm.executeQuery("SELECT * FROM stats WHERE username = '"+playerName+"'");
			while(group.next()) {
				int reward = group.getInt("reward");
				if(reward > 0){
				query("UPDATE stats SET reward=0 WHERE username = '"+playerName+"'");
				return reward;
				} else {
				return 0;
				}
			}
		} catch(Exception e) {
		createConnection();
		return 0;
	}
	return 0;
	}


	public static boolean updateKD(int kills, int deaths, String playerName)
	{
		createConnection();
		try	
		{
			query("INSERT INTO stats (username) VALUES ('"+playerName+"')");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean updatePlayers(int playersOnline)
	{
		createConnection();
		try	
		{
			query("INSERT INTO site_info (online) VALUES ('"+playersOnline+"')");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}