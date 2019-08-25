package server.model.players;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import server.Server;
import server.util.Misc;

public class PacketLogger
{
	FileWriter packetLogger;

	public void open(String playerName){
		try
		{
			packetLogger = new FileWriter("./Data/packetlogs/"+ playerName +".txt", true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}
	
	public void close(){
		try
		{
			packetLogger.close();
		} catch (Exception e) {
			System.out.println("Exception error closing FileWriter!");
		}
	}
	
	public void print(String s){
		try
		{
			packetLogger.write(s + "\r\n");
		} catch (Exception e) {
			System.out.println("Exception error writing on the fucking FileWriter!");
		}
	}
	
}