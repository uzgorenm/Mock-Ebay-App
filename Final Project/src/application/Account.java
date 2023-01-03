/*
 * EE422C Final Project submission by
 * Mehmet Uzgoren
 * msu259
 * 17615
 * Used 1 Slip Day
 * Fall 2022
 */
package application;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;

public class Account implements Observable{
	private final SimpleStringProperty UserName;
	private final SimpleStringProperty Password;
	public Account(String userName, String password){
		this.UserName = new SimpleStringProperty(userName);
		this.Password = new SimpleStringProperty(password);
	}
	public String getUserName() {
		return UserName.get();
	}
	public void setUserName(String userName) {
		UserName.set(userName);
	}
	public String getPassword() {
		return Password.get();
	}
	public void setPassword(String password) {
		Password.set(password);
	}
	@Override
	public void addListener(InvalidationListener listener) {
	}
	@Override
	public void removeListener(InvalidationListener listener) {
	}
}
