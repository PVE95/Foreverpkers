package server;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import server.util.Misc;
import server.event.EventManager;
import server.Server;
import server.model.players.PlayerHandler;
import server.model.players.Player;
import server.model.players.Client;

public class ServerGUI extends JFrame implements KeyListener{
	
	public static JTextField commandInput;
	public static JTextArea commandOutput;
	public static JTextArea suggestionOutput;
	public static JScrollPane suggestionOutputScroll;
	public static JScrollPane commandOutputScroll;
	
	public static JButton button1 = new JButton("Clear Command Output Box");
	public static JButton button2 = new JButton("Clear Command Message Box");
	
	public static String suggestionText;
	public static String outputText;
	public static String inputText;
										 
	public static int currentSuggestion = 0;
	public static int realSuggestion = 0;
	
	public boolean hasMaxSuggestions = false;
	public boolean hasMaxYells = false;
	
	public static boolean openForSuggestions = false;
	public static boolean openForChat = true;
	public static boolean openForYell = true;
	public static boolean firstOpened = true;
	
	private Client c;
	
	public void resetSuggestions(){
		suggestionOutput.setText("");
	}
	
	public void handleGUI(){
	
		setTitle("Server Command Box - Version 0.2 - Coded by Ryan");
		setSize(900, 600);
		
		commandInput = new JTextField(73);
		commandOutput = new JTextArea(30, 39);
		suggestionOutput = new JTextArea(30, 39);
		
		suggestionOutput.setCaretPosition( suggestionOutput.getText().length() );
		
		commandOutputScroll = new JScrollPane(commandOutput);
		suggestionOutputScroll = new JScrollPane(suggestionOutput);
		
		if(firstOpened){
			commandOutput.setText("Type 'help' for some info");
			firstOpened = false;
		}
		
		commandOutput.setEditable(false);
		suggestionOutput.setEditable(false);
		
		JLabel label = new JLabel("  Version 0.2  ");
		
		Panel guiPanel = new Panel();
		guiPanel.add(commandInput);
		guiPanel.add(commandOutputScroll);
		guiPanel.add(suggestionOutputScroll);
		guiPanel.add(button1);
		guiPanel.add(label);
		guiPanel.add(button2);
		
		button1.addActionListener(new ActionListener(){
		
			public void actionPerformed(ActionEvent e){
				commandOutput.setText("");
			}
		
		}); //End of Action Listener
		
		button2.addActionListener(new ActionListener(){
		
			public void actionPerformed(ActionEvent e){
				suggestionOutput.setText("");
			}
		
		}); //End of Action Listener
		
		commandInput.addKeyListener(this);
		add(guiPanel);
		setVisible(true);
		
	}

	public void keyTyped(KeyEvent e){  }
	
	public void keyReleased(KeyEvent e){  }
	
	public void keyPressed(KeyEvent k) {
		
		int key = k.getKeyCode();
		
		suggestionText = suggestionOutput.getText().trim();
		outputText = commandOutput.getText().trim();
		inputText = commandInput.getText().trim();
		
		if (key == KeyEvent.VK_ENTER) {
			commandInput.setText("");
			
			if(inputText.equalsIgnoreCase("help")){
				commandOutput.setText(outputText + "\n" + "Welcome to the Server Command Box GUI." + "\n" + 
														  "This is a server tool coded by Ryan." + "\n" +
														  "The box you are currently reading from is the Command Output Box" + "\n" + 
														  "If you would like to know some commands, type 'commands'." + "\n" + 
														  "The box on the right is the Message Display Box." + "\n" + 
														  "Oh yeah, and james, if you get on and I haven't done it yet" + "\n" +
														  "Please make the Message Display Box display yells" + "\n" +
														  "I didn't have time to implement it myself :'( ");
			
			} else if(inputText.equalsIgnoreCase("commands")){
				commandOutput.setText(outputText + "\n" + "~ playersOnline - Prints the server's player count" 
												 + "\n" + "~ clear - Clears the text of the output box (The box you're reading from)" 
												 + "\n" + "~ kickUser [playername] - Kicks a player directly from the command box (Case Sensitive)"
												 + "\n" + "~ banUser [playername] - Bans a player directly from the command box (Case Sensitive)"
												 + "\n" + "~ unBanUser [playername] - Un-Bans a player directly from the command box (Case Sensitive)"
												 + "\n" + "~ ipBanUser [playername] -  IP Bans a user directly from the command box(Case Sensitive)"
												 + "\n" + "~ message [text] - Sends a message to the server chatbox directly from the command box (Case Sensitive)"
												 + "\n" + "~ takeSuggestions [ON/OFF] - If on, it will print and accept player suggestions to the command box (Case Sensitive)"
												 + "\n" + "~ takeYell [ON/OFF] - If on, it will print player yells to the command box (Case Sensitive)"
												 + "\n" + "~ takeChat [ON/OFF] - If on, it will print player chat to the command box (Case Sensitive)");							   	
			
			
			} else if(inputText.equalsIgnoreCase("clear")){
				commandOutput.setText("");
				
				
			} else if(inputText.equalsIgnoreCase("playersOnline")){
				commandOutput.setText(outputText + "\n" + "There are currently " + Server.playerHandler.playerCount + " players online!");
				
				
			} else if(inputText.startsWith("banUser")){
				kickUser(inputText.substring(8));
				Connection.addNameToBanList(inputText.substring(8));
				Connection.addNameToFile(inputText.substring(8));
				commandOutput.setText(outputText + "\n" + "You have added username \"" + inputText.substring(8) + "\" to the ban log.");
			
			
			} else if(inputText.startsWith("unbanUser")){
				Connection.removeNameFromBanList(inputText.substring(10));
				commandOutput.setText(outputText + "\n" + "You have removed username \"" + inputText.substring(10) + "\" from the ban log.\n"
									+ "This account won't be officialy unbanned until the server reboots.");
			
			} else if(inputText.startsWith("ipBanUser")){
				String playerToBan = inputText.substring(10);
	
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addIpToBanList(Server.playerHandler.players[i].connectedFrom);
								Connection.addIpToFile(Server.playerHandler.players[i].connectedFrom);
								commandOutput.setText(outputText + "\n" + "You have added username: "+Server.playerHandler.players[i].playerName+" and his/her IP: "
													  +Server.playerHandler.players[i].connectedFrom + " to the ban log.");
								Server.playerHandler.players[i].disconnected = true;
							} 
						}
				}
				
				
			} else if(inputText.startsWith("message")){
				String message = inputText.substring(8);
				
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.sendMessage("@bla@[@dbl@Server@bla@]: @dre@" + Misc.optimizeText(message));
					}
				}
				
				commandOutput.setText(outputText + "\n" + "You have sent the message: \"" + message + "\" into the server chatroom.");

} else if(inputText.startsWith("update")){

PlayerHandler.updateSeconds = 60;
				PlayerHandler.updateAnnounced = false;
				PlayerHandler.updateRunning = true;
				PlayerHandler.updateStartTime = System.currentTimeMillis();

for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.sendMessage("Server update started by [@red@Server@bla@]");
					}
				}
commandOutput.setText(outputText + "\n" + "Update in 60 seconds.");
				
				
			} else if(inputText.startsWith("takeSuggestions")){
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						
						
						if(inputText.substring(16).equalsIgnoreCase("ON") && !openForSuggestions){
							c2.sendMessage("@dre@We are currently taking/reading suggestions! Type ::suggest [message] to send us one!");
							commandOutput.setText(outputText + "\n" + "You have successfully turned suggestions on!");
							currentSuggestion = 0;
							realSuggestion = 0;
							openForSuggestions = true;
						
						} else if(inputText.substring(16).equalsIgnoreCase("OFF") && openForSuggestions){
							c2.sendMessage("@dre@We are no longer taking suggestions, at the moment");
							commandOutput.setText(outputText + "\n" + "You have successfully turned suggestions off!");
							openForSuggestions = false;
						}
						
					}
				}
			
			} else if(inputText.startsWith("takeYells")){
			
				if(inputText.substring(10).equalsIgnoreCase("ON") && !openForYell){
					commandOutput.setText(outputText + "\n" + "You have successfuly turned yell logging on!");
					openForYell = true;
				} else if(inputText.substring(10).equalsIgnoreCase("OFF") && openForYell){
					commandOutput.setText(outputText + "\n" + "You have successfully turned yell logging off!");
					openForYell = false;
				} else if(inputText.substring(10).equalsIgnoreCase("ON") && openForYell){
					commandOutput.setText(outputText + "\n" + "Yell logging is already enabled.");
				} else if(inputText.substring(10).equalsIgnoreCase("OFF") && !openForYell){
					commandOutput.setText(outputText + "\n" + "Yell logging is already disabled.");
				}
			
			} else if(inputText.startsWith("takeChat")){
				
				if(inputText.substring(9).equalsIgnoreCase("ON") && !openForChat){
					commandOutput.setText(outputText + "\n" + "You have successfuly turned chat logging on!");
					openForChat = true;
				} else if(inputText.substring(9).equalsIgnoreCase("OFF") && openForChat){
					commandOutput.setText(outputText + "\n" + "You have successfully turned chat logging off!");
					openForChat = false;
				} else if(inputText.substring(9).equalsIgnoreCase("ON") && openForChat){
					commandOutput.setText(outputText + "\n" + "Chat logging is already enabled.");
				} else if(inputText.substring(9).equalsIgnoreCase("OFF") && !openForChat){
					commandOutput.setText(outputText + "\n" + "Chat logging is already disabled.");
				}
				
			}
		
		} //end of key_enter statement
		
	} //end of void

	void kickUser(String userName){
		for(int i = 0; i < Config.MAX_PLAYERS; i++) {
			if(Server.playerHandler.players[i] != null) {
				if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(userName)) {
					Client c2 = (Client)Server.playerHandler.players[i];
					c2.logout();
					break;
					
				} 
			}
		}
		
	}
	
}













