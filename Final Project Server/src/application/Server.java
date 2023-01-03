/*
 * EE422C Final Project submission by
 * Mehmet Uzgoren
 * msu259
 * 17615
 * Used 1 Slip Day
 * Fall 2022
 */
package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

class Server extends Observable {
	private static final int PORT = 9090;
	public static Map<Item, List<String>> UNIDHistory = new HashMap<Item, List<String>>();
	private static ArrayList<ClientHandler> clients = new ArrayList<>();
	private static ExecutorService pool = Executors.newFixedThreadPool(5);
	public static ObservableList<Item> tabledata;
	public static boolean flag = true;
	
	public static void main(String[] args) throws IOException{
		File file = new File("Input.txt");
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String line;
    	tabledata = FXCollections.observableArrayList();
		while(true) {
    		line = br.readLine();
    		if(line.equals("")) break;
    		String[] data = line.split(" ");
    		
    		String name = data[0];
    		String startingBid = data[1];
    		
    		String description = br.readLine();
    		String timePriceUNID = br.readLine();
    		
    		String [] timePriceUNIDArray = timePriceUNID.split(" ");
    		
    		String time = timePriceUNIDArray[0];
    		String BINP = timePriceUNIDArray[1];
    		String UniqueID = timePriceUNIDArray[2];
    		
    		Item item = new Item(name, startingBid, description, time, BINP, UniqueID);
    		tabledata.add(item);
    	}    	
		for(Item i:tabledata) {
			UNIDHistory.put(i, new ArrayList<>());
		}
		ServerSocket listener = new ServerSocket(PORT);
		while(true) {
			Socket client = listener.accept();
			ClientHandler clientThread = new ClientHandler(client, clients);	
			clients.add(clientThread);
			pool.execute(clientThread);
		}
	}
}