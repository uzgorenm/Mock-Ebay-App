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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javafx.collections.ObservableList;

public class ServerConnection implements Runnable{
	private Socket server;
	private BufferedReader in;
	private PrintWriter out;
	public ServerConnection(Socket s) throws IOException{
		server = s;
    	in = new BufferedReader(new InputStreamReader(server.getInputStream()));
    	out = new PrintWriter(server.getOutputStream(), true);
	}
	@Override
	public void run() {
			try {
				while(true) {
						System.out.println(Client.controller);
						if(!(Client.controller == null)) {						
							String serverResponse = in.readLine();
							if(!(serverResponse == null)) {
								System.out.println(serverResponse);
								if(serverResponse.contains("Error") && serverResponse.contains("0")) {
									Client.controller.setConsoleText("That item has run out of bidding time");
								}
								if(serverResponse.contains("Error") && serverResponse.contains("sold")) {
									Client.controller.setConsoleText("That item has already been sold");
								}
								if(serverResponse.contains("Error") && serverResponse.contains("bid")) {
									Client.controller.setConsoleText("That bid is too low for that item");
								}
								if(serverResponse.contains("Error") && serverResponse.contains("notinteger")) {
									Client.controller.setConsoleText("Please enter a valid double for bid");
								}
								if(serverResponse.contains("Bid")) {
									String[] data = serverResponse.split(" ");
									for(Item item :Client.controller.getTable()) {
										if(item.getUniqueID().equals(data[2])) {
											item.setBid(data[3]);
											item.setLastBidder(data[1]);
											Client.controller.tableRefresh();														
										}
									}							
								}
								if(serverResponse.contains("Bought")) {
									String[] data = serverResponse.split(" ");
									for(Item item :Client.controller.getTable()) {
										if(item.getUniqueID().equals(data[2])) {
											item.setBid(data[3]);
											item.setSold("true");
											item.setLastBidder(data[1]);
											Client.controller.tableRefresh();										
										}
									}
								}
								if(serverResponse.contains("Time")) {													
										String[] data = serverResponse.split(" ");
										for(Item item : Client.controller.getTable()) {
											if(item.getUniqueID().equals(data[1])) {
												item.setTime(data[2]);
											}						
										}
										Client.controller.tableRefresh();
								}
								if(serverResponse.contains("Start")) {
										String[] data = serverResponse.split(" ");
										String description = in.readLine();
										String TimeBuyitNow = in.readLine();
										String[] data2 = TimeBuyitNow.split(" ");
										Item item = new Item(data[1], data[2], data[3], data[4], description, data2[0], data2[1], data2[2]);
										Client.controller.tabledata.add(item);
								}
								if(serverResponse.contains("Update")) {
									System.out.println(Client.controller.tabledata);
									Client.controller.setTable(Client.controller.tabledata);
									Client.controller.searchnarrow();
									Client.controller.tableRefresh();									
								}
								if(serverResponse.contains("History")) {
									String[] data = serverResponse.split(" ");																	
									for(Item item:Client.controller.getTable()) {
										if(item.getUniqueID().equals(data[1])) {
											String histText = "Previous bid amounts of " + item.getItemName() + ": \n";
											String newbid;
											if(Integer.parseInt(data[2]) > 0) {
												String firstbid = data[3] + " " + data[4] + " " + data[5] + "\n";
												histText += firstbid;
											}
											while((Integer.parseInt(data[2]) > 1) && !((newbid = in.readLine()).length() == 0)) {
												String addtext = newbid.substring(2) + "\n";
												System.out.println(newbid);
												histText+=addtext;
											}
							    			Client.controller.setHistory(histText);
							    			break;
										}																	
									}									
								}
								if(serverResponse.contains("Sold")) {
									String[] data = serverResponse.split(" ");	
									for(Item item:Client.controller.getTable()) {
										if(item.getUniqueID().equals(data[1])) {
											item.setSold("true");
											Client.controller.tableRefresh();
										}
									}
								}
								if(serverResponse.contains("XItemX")){
									String[] data = serverResponse.split(" ");
									String description = in.readLine();
									String TimeBuyitNow = in.readLine();
									String[] data2 = TimeBuyitNow.split(" ");
									Item item = new Item(data[1], data[2], data[3], data[4], description, data2[0], data2[1], data2[2]);
									Client.controller.tabledata.add(item);
								}
									
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
}
