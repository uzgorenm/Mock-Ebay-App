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
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.*;

import javafx.collections.ObservableList;

class ClientHandler implements Runnable {
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private ArrayList<ClientHandler> clients;
	private boolean keepgoing = false;

    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
    	this.client = clientSocket;
    	in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    	out = new PrintWriter(client.getOutputStream(), true);
    	this.clients = clients;
    }
    public boolean checkTime() {
    	for(Item k:Server.tabledata) {
    		if(!k.getTime().equals("0")) {
    			return true;
    		}
    	}
    	return false;
    }
  @Override
  public void run() {
	  try {
			while(true) {
				String received = in.readLine();
				if(!(received == null)) {
					if(received.contains("Time") ) {
						if(Server.flag) {
							Timer timer = new Timer();
							timer.scheduleAtFixedRate(new TimerTask() {
					            public void run() {
					                for(Item i:Server.tabledata) {						                
					                	if(i.getTime().equals("0")) {
					                		for(Item j:Server.tabledata) {
					                			if(!(j.getBid().equals("1"))) {
						                			j.setSold("true");
						                			outToAll("Sold" + " " + j.getUniqueID());
						                		}
					                		}
					                		if(!checkTime()) {
						                		timer.cancel();
						                		break;		
					                		}
					                	}
					                	else {
					                		i.setTime("" + (Integer.parseInt(i.getTime()) - 1));		
					                		outToAll("Time" + " " + i.getUniqueID() + " "+ i.getTime());
					                	}
					                }					                
					            }
					        }, 1000, 1000);
							Server.flag = false;
							continue;
						}
					}
					else if(received.contains("History")) {
						String[] data = received.split(" ");
						for(Item item: Server.tabledata) {
							if(item.getUniqueID().equals(data[1])) {							
								String sl = "History " + item.getUniqueID() + " " +Server.UNIDHistory.get(item).size()+ " " + Server.UNIDHistory.get(item).toString().substring(1, Server.UNIDHistory.get(item).toString().length() - 1);
								System.out.println(sl);
								out.println(sl);								
								break;
							}
						}
					}
					else if(received.contains("Start")) {
						  for(Item item:Server.tabledata) {
						  		out.println("Start " + item.getItemName() + " " + item.getBid() + " "+ item.getLastBidder() + " "+ item.getSold()+ "\n"+  item.getDescription() + "\n"+ item.getTime() + " "+ item.getBuyItNowPrice() + " " + item.getUniqueID()); 
						  	}
						  	out.println("Update");
					}
					else if(received.contains("XItemX")) {
						String[] data = received.split(" ");
						String description = in.readLine();
						String TimeBuyitNow = in.readLine();
						String[] data2 = TimeBuyitNow.split(" ");
						Item item = new Item(data[1], data[2], data[3], data[4], description, data2[0], data2[1], data2[2]);				
						Server.tabledata.add(item);
						Server.UNIDHistory.put(item, new ArrayList<>());
						outToAll("XItemX " + item.getItemName() + " " + item.getBid() + " "+ item.getLastBidder() + " "+ item.getSold()+ "\n"+  item.getDescription() + "\n"+ item.getTime() + " "+ item.getBuyItNowPrice() + " " + item.getUniqueID());
						outToAll("Update");
					}
					else {
						String[] data = received.split(" ");
						String uniqueID = data[0];
						String username = data[1];
						String bid = data[2];
						String type = data[3];
						if(type.equals("BID")) {
							for(Item item: Server.tabledata) {
								try{Double.parseDouble(bid);
								}catch(Exception e) {
								out.println("Error - notinteger");
								break;
								}							
								if(item.getUniqueID().equals(uniqueID)) {
									System.out.println(item.getTime());
									if(item.getSold().equals("true")){
					    				out.println("Error - sold");
					    			}
					    			else if(Integer.parseInt(item.getTime()) <= 0) {
					    				out.println("Error - 0");
					    			}
					    			else if(Double.parseDouble(bid) > Double.parseDouble(item.getBid())){
					    				if(Double.parseDouble(bid) >= Double.parseDouble(item.getBuyItNowPrice())) {
					    					item.setSold("true");
					    					outToAll("Bought " + username + " " + uniqueID + " "+ bid);
					    					Server.UNIDHistory.get(item).add(data[1] + " - " + "$" + data[2] + " (Instant Sold)" +  "\n");	
					    					item.setBid(bid);
						    				item.setLastBidder(username);
					    				}
					    				else {
						    				outToAll("Bid " + username + " " + uniqueID + " "+ bid);
						    				item.setBid(bid);
						    				item.setLastBidder(username);
						    				Server.UNIDHistory.get(item).add(data[1] + " - " + "$" + data[2] + "\n");
					    				}
					    			}
					    			else if(Double.parseDouble(bid) <= Double.parseDouble(item.getBid())) {
					    				out.println("Error - bid");
					    			}
								}
							}
						}					  
					}
				}
			}
		}catch(IOException e){
			System.err.println("IO exception in client handler");
			System.err.println(e.getStackTrace());
		}finally {
			out.close();
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
  }
public void outToAll(String msg) {
	for(ClientHandler aClient: clients) {
		aClient.out.println(msg);
	}
}

}