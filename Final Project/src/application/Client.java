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
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Client extends Application{
	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 9090;
	private static PrintWriter out;
	public static String data;
	public static Controller controller;
	public static LogInController logInController;
	public Scene login;
	public Stage stage;
	public static ServerConnection serverConnection;
	public static void main(String[] args) throws IOException {
		Socket socket = new Socket(SERVER_IP, SERVER_PORT);
		ServerConnection serverConn = new ServerConnection(socket);
		serverConnection = serverConn;
		out = new PrintWriter(socket.getOutputStream(), true);
		
		new Thread(serverConn).start();
		launch(args);
	}
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		FXMLLoader fxmlLoaderLogIn = new FXMLLoader(getClass().getResource("LogIn.fxml"));
		Parent root = (Parent)fxmlLoaderLogIn.load();
        logInController = fxmlLoaderLogIn.getController();
        logInController.client = this;
        Scene scene = new Scene(root);
        login = scene;
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	public void sendBid(Item item, String username, String bid, String type) {
		out.println(item.getUniqueID() + " " + username + " " + bid + " " + type) ;
	}
	public void sendItem(String name, String bid, String lastBidder, String Sold, String description, String time, String buyitnowprice, String uniqueid) {
		out.println("XItemX " + name + " " + bid + " " + lastBidder + " " + Sold + "\n " + description + "\n" + time + " " + buyitnowprice + " " + uniqueid);
	}
	public void sendTime(String type) {
		out.println(type);
	}
	public void createController() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Final.fxml"));
		Parent root = (Parent)fxmlLoader.load();
	    controller = fxmlLoader.getController();
	    controller.client = this;
	    sendTime("Time");
        controller.setUserName(LogInController.account.getUserName());
        sendTime("Start");
	    Scene scene = new Scene(root);        
        Stage window = (Stage)(stage.getScene().getWindow());
        window.setScene(scene);
        window.show();
        
	}
}