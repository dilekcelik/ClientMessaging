// 48 - Testing the Servers Instant 

package middleJava;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame {
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	//Socket is a connections between two computers..
	private Socket connection;
	
	
	//constructor
	public Server() {
		super("Dileks Instant Messenger");
		userText = new JTextField();
		userText.setEditable(false);
		//adding action listener
		userText.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
							sendMessage(event.getActionCommand());
							userText.setText("");
							
					}
				}
		);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		setSize(300,150);
	}
	
	//set up and run the server
	public void startRunning(){
		try {
			server = new ServerSocket(6789, 100);
			while(true){
				try{
					//connect and have conversation
					//This program will make 3 main things 
					//1-wait to someone to connect with
					//2-setup connection
					//3-runs program
					waitForConnection();
					setupStreams();
					whileChatting();
					
				} catch (EOFException eofException) {
					showMessage("\n Server ended the connection! ");
				} finally {
					closeCrap();
				}
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	//Wait For Connection, then Display Connection Information
	private void waitForConnection() throws IOException{
		showMessage("Waiting for someone to connect... \n");
		connection = server.accept();
		showMessage(" Now Connected to " + connection.getInetAddress().getHostName());
	}
	
	//get stream to send and receive data
	private void setupStreams() throws IOException{
		//creating path way to connect with other computer
		output = new ObjectOutputStream(connection.getOutputStream());
		//buffer-sometimes data get over when you sent message..
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Atreams are now setup! \n");
	}
	
	//During the chat conversation
	private void whileChatting() throws IOException{
		String message = " You are now connected! ";
		sendMessage(message);
		ableToType(true);
		do{
			//have a concersation
			try {
				message = (String) input.readObject();
				showMessage("\n" + message);
			} catch (ClassNotFoundException classNotFoundException) {
				showMessage("\n idk wtf that user send!");
			}
		} while( !message.equals("CLIENT - END"));
	}
	
	//Close streams and sockets after you are done chatting
	private void closeCrap(){
		showMessage("\n Closing connections... \n");
		ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	//Send a message to a client
	private void sendMessage(String message){
		try {
				output.writeObject("SERVER - " + message);
				output.flush();
				showMessage("\nSERVER - " + message);
		} catch (IOException ioException) {
			chatWindow.append("\n ERROR: DUDE I CANT SEND THAT MESSAGE");
		}
	}
	
	//updates ChatWindow
	private void showMessage(final String text){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append(text);
					}
				}
		);
	}
	
	//let the user type stuff into their box
	private void ableToType(final boolean tof){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						userText.setEditable(tof);
					}
				}
		);
	}
	
	
	
}















