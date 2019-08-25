package server.model.players;

import java.util.Calendar;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;

import server.model.items.Item;
import server.Server;

/**
* TradeLog class
* @author Aintaro
*/

public class TradeLog {

	private Client c;
	
	
	
	public TradeLog(Client Client) {
		this.c = Client;
	}
	
	/**
	* Will write what kind of item a player has received.
	* MONTH = 0 = January
	* DAY OF MONTH = 30 || 31
	*/
	public void tradeReceived(String itemName, int itemAmount) {
	Client o = (Client) Server.playerHandler.players[c.tradeWith];
	Calendar C = Calendar.getInstance();
		try {
				BufferedWriter bItem = new BufferedWriter(new FileWriter("./Data/trades/received/" + c.playerName + ".txt", true));
				try {			
					bItem.newLine();
					bItem.write(c.currentTime + checkTimeOfDay()+" Year : " + C.get(Calendar.YEAR) + "\tMonth : " + C.get(Calendar.MONTH) + "\tDay : " + C.get(Calendar.DAY_OF_MONTH) + " "+ c.connectedFrom);
					bItem.newLine();
					bItem.write("Received " + itemAmount + " " + itemName + " From " + o.playerName);
					bItem.newLine();
					bItem.write("--------------------------------------------------");
					} finally {
						bItem.close();
					}
				} catch (IOException e) {
                    e.printStackTrace();
            }
	}
	
	/**
	* Will write what kind of item a player has traded with another player.
	* MONTH = 0 = January
	* DAY OF MONTH = 30 || 31
	*/

	public void writeDropLog(String data, int ID, int itemX, int itemY) {
        checkDateAndTime();
        String filePath = "./Data/trades/dropped/" + c.playerName + ".txt";
        BufferedWriter bw = null;
        try {
                bw = new BufferedWriter(new FileWriter(filePath, true));
                bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " "
                                + checkTimeOfDay() + "]: " + "[" + c.connectedFrom + "]: "
                                + "" + data + " " + " " + Item.getItemName(ID) + " ("
                                + itemX + "/" + itemY + ").");
                bw.newLine();
                bw.flush();
        } catch (IOException ioe) {
                ioe.printStackTrace();
        } finally {
                if (bw != null) {
                        try {
                                bw.close();
                        } catch (IOException ioe2) {
                   }
             }
        }
	}

	public void writePickupLog(String data, int ID, int itemX, int itemY) {
        checkDateAndTime();
        String filePath = "./Data/trades/pickedup/" + c.playerName + ".txt";
        BufferedWriter bw = null;
        try {
                bw = new BufferedWriter(new FileWriter(filePath, true));
                bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " "
                                + checkTimeOfDay() + "]: " + "[" + c.connectedFrom + "]: "
                                + "" + data + " " + " " + Item.getItemName(ID) + " ("
                                + itemX + "/" + itemY + ").");
                bw.newLine();
                bw.flush();
        } catch (IOException ioe) {
                ioe.printStackTrace();
        } finally {
                if (bw != null) {
                        try {
                                bw.close();
                        } catch (IOException ioe2) {
                   }
             }
        }
	}


	public void tradeGive(String itemName, int itemAmount) {
	Client o = (Client) Server.playerHandler.players[c.tradeWith];
	Calendar C = Calendar.getInstance();
		 try {
				BufferedWriter bItem = new BufferedWriter(new FileWriter("./Data/trades/gave/" + c.playerName + ".txt", true));
				try {			
					bItem.newLine();
					bItem.write(c.currentTime + checkTimeOfDay()+" Year : " + C.get(Calendar.YEAR) + "\tMonth : " + C.get(Calendar.MONTH) + "\tDay : " + C.get(Calendar.DAY_OF_MONTH) + " " + c.connectedFrom);
					bItem.newLine();
					bItem.write("Gave " + itemAmount + " " + itemName + " To " + o.playerName);
					bItem.newLine();
					bItem.write("--------------------------------------------------");
				} finally {
					bItem.close();
				}
			} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void checkDateAndTime() {
        Calendar cal = new GregorianCalendar();
        String day, month, hour, minute, second;

        int YEAR = cal.get(Calendar.YEAR);
        int MONTH = cal.get(Calendar.MONTH) + 1;
        int DAY = cal.get(Calendar.DAY_OF_MONTH);
        int HOUR = cal.get(Calendar.HOUR_OF_DAY);
        int MIN = cal.get(Calendar.MINUTE);
        int SECOND = cal.get(Calendar.SECOND);

        day = DAY < 10 ? "0" + DAY : "" + DAY;
        month = MONTH < 10 ? "0" + MONTH : "" + MONTH;
        hour = HOUR < 10 ? "0" + HOUR : "" + HOUR;
        minute = MIN < 10 ? "0" + MIN : "" + MIN;
        second = SECOND < 10 ? "0" + SECOND : "" + SECOND;

        c.date = day + "" + month + "" + YEAR;
        c.currentTime = hour + ":" + minute + ":" + second;
    }

    public String checkTimeOfDay() {
        Calendar cal = new GregorianCalendar();
        int TIME_OF_DAY = cal.get(Calendar.AM_PM);
        return TIME_OF_DAY > 0 ? "PM" : "AM";
    }

}