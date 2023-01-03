/*
 * EE422C Final Project submission by
 * Mehmet Uzgoren
 * msu259
 * 17615
 * Used 1 Slip Day
 * Fall 2022
 */
package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController {
	public Client client;
	public static Account account;
    @FXML
    private TextField userName;

    @FXML
    private Button LogInButton;
    @FXML
    private Button guestLogin;

    @FXML
    private TextField password;

    @FXML
    void LogInButtonPressed(ActionEvent event) throws IOException {
    	String password1 = PasswordEncrypter.EncryptPassword(userName.getText());
    	account = new Account(userName.getText(), password1);
    	if(password.getText().equals(password1)) {
    		client.createController();
    	}
    	
    }
    @FXML
    void guestLoginButtonPressed(ActionEvent event) throws IOException {
    	account = new Account("Guest", "");
    	client.createController();    	
    }

}

