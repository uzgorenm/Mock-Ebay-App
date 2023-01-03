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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Controller {
	public static Map<Item, List<String>> UNIDHistory = new HashMap<Item, List<String>>();
	public static ObservableList<Item> tabledata;
	
	public Client client;
	@FXML
    private TextField historyUNID;
	@FXML
    private TextField createItemName;
	@FXML
    private TextField createItemBid;
	@FXML
    private TextField createItemDescription;
	@FXML
    private TextField createItemTime;
	@FXML
    private TextField createItemBUY;
	@FXML
    private TextField createItemUNID;
	@FXML
	private Button createItemButton;
    @FXML
    private Label userName;
    @FXML
    private Button findHistory;

    @FXML
    private Label historyLabel;

    @FXML
    private TextField narrowSearch;

    @FXML
    private Button searchButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button quitButton;
    @FXML
    private Label consoleText;
    @FXML
    private TableView<Item> table;

    @FXML
    private TableColumn<Item, String> itemName;
    
    @FXML
    private TableColumn<Item, String> currentBid;

    @FXML
    private TableColumn<Item, String> lastBidder;

    @FXML
    private TableColumn<Item, String> sold;

    @FXML
    private TableColumn<Item, String> Description;

    @FXML
    private TableColumn<Item, String> timeLeft;

    @FXML
    private TableColumn<Item, String> buyItNowPrice;

    @FXML
    private TableColumn<Item, String> uniqueId;

    @FXML
    private TextField bidUNID;

    @FXML
    private TextField bidPrice;

    @FXML
    private Button bidButton;

    @FXML
    public void quitButtonPressed(ActionEvent event) {
    	System.exit(1);
    }
    @FXML
    public void createItemButtonPressed(ActionEvent event) {
    	for(Item items:table.getItems()) {
    		if(items.getUniqueID().equals(createItemUNID.getText())) {
    			return;
    		}
    	}
    	client.sendItem(createItemName.getText(),createItemBid.getText(), "N/A", "false", createItemDescription.getText(),createItemTime.getText(),createItemBUY.getText(), createItemUNID.getText());
    }
    @FXML
    public void logoutButtonPressed(ActionEvent event) throws IOException {
        Stage window = client.stage;
        window.setScene(client.login);
        window.show();
    }
    @FXML
    public void bidButtonPress(ActionEvent event) {
    	for(Item i: table.getItems()) {
    		if(bidUNID.getText().equals(i.getUniqueID())) {
    			client.sendBid(i, userName.getText(), bidPrice.getText(), "BID");
    		}
    	}
    }

    @FXML
    void findHistoryPressed(ActionEvent event) {
    	for(Item item:table.getItems()) {
    		if(item.getUniqueID().equals(historyUNID.getText())){
    			client.sendTime("History" + " " + historyUNID.getText());
    		}
    	}    	
//    	for(Item i: table.getItems()) {
//    		if(i.getUniqueID().equals(historyUNID.getText())) {
//    			String[] historyLabelText = UNIDHistory.get(i).toString().substring(1, UNIDHistory.get(i).toString().length() - 1).split(",");
//    			String histText = "Previous bid amounts of " + i.getItemName() + ": \n";
//    			for(String username: historyLabelText) {
//    				histText += username;
//    			}
//    			historyLabel.setText(histText);
//    		}
//    	}
    }
    public void setHistory(String string) {
    	Platform.runLater(new Runnable() {
            @Override public void run() {
            	historyLabel.setText(string);
            }
        });
    	
    }
    public ObservableList<Item> getTable() {
    	return table.getItems();
    }
    public void setTable(ObservableList<Item> datatobeinserted) {
    	table.setItems(datatobeinserted);
    }
    public void tableRefresh() {
    	table.refresh();
    }
    public void setConsoleText(String string) {
    	Platform.runLater(new Runnable() {
            @Override public void run() {
            	String str = consoleText.getText();
            	str+= "\n";
            	str += string;
            	consoleText.setText(str);
            }
        });
    }
    public void setUserName(String username) {
    	userName.setText(username);
    }
    public void initialize() throws IOException {    	
    	tabledata = FXCollections.observableArrayList();
    	itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("ItemName"));
    	currentBid.setCellValueFactory(new PropertyValueFactory<Item, String>("Bid"));
    	Description.setCellValueFactory(new PropertyValueFactory<Item, String>("Description"));
    	lastBidder.setCellValueFactory(new PropertyValueFactory<Item, String>("LastBidder"));
    	sold.setCellValueFactory(new PropertyValueFactory<Item, String>("Sold"));
    	timeLeft.setCellValueFactory(new PropertyValueFactory<Item, String>("Time"));
    	buyItNowPrice.setCellValueFactory(new PropertyValueFactory<Item, String>("BuyItNowPrice"));
    	uniqueId.setCellValueFactory(new PropertyValueFactory<Item, String>("UniqueID"));
    	table.setVisible(true);
    	table.refresh();
    	
    }
    public void searchnarrow() {
    	FilteredList<Item> filteredData = new FilteredList<Item>(tabledata, b->true);
    	narrowSearch.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredData.setPredicate(Item -> {
    			if(newValue == null || newValue.isEmpty()) {
        			return true;
        		}
    			
    			String lowerCaseFilter = newValue.toLowerCase();
    			if(Item.getItemName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
    				return true;
    			}
    			else if(Item.getUniqueID().indexOf(lowerCaseFilter) != -1) {
    				return true;
    			}
    			else if(Item.getLastBidder().indexOf(lowerCaseFilter) != -1) {
    				return true;
    			}
    			else {
    				return false;
    			}
    		});
    	});
    	SortedList<Item> sortedData = new SortedList<>(filteredData);
    	sortedData.comparatorProperty().bind(table.comparatorProperty());
    	table.setItems(sortedData);
    }

}
