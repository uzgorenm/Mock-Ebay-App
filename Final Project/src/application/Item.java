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

public class Item implements Observable{
	private final SimpleStringProperty ItemName,Bid, LastBidder, Sold, Description, Time, BuyItNowPrice, UniqueID;
	public Item(String name, String bid, String lastBidder, String Sold, String description, String time, String buyitnowprice, String uniqueid) {
		this.LastBidder = new SimpleStringProperty(lastBidder);
		this.Sold = new SimpleStringProperty(Sold);
		this.ItemName = new SimpleStringProperty(name);
		this.Bid = new SimpleStringProperty(bid);
		this.Description = new SimpleStringProperty(description);
		this.Time = new SimpleStringProperty(time);
		this.BuyItNowPrice = new SimpleStringProperty(buyitnowprice);
		this.UniqueID = new SimpleStringProperty(uniqueid);
	}
	public Item(String name, String bid, String description, String time, String buyitnowprice, String uniqueid) {
		LastBidder = new SimpleStringProperty("N/A");
		Sold = new SimpleStringProperty("false");
		this.ItemName = new SimpleStringProperty(name);
		this.Bid = new SimpleStringProperty(bid);
		this.Description = new SimpleStringProperty(description);
		this.Time = new SimpleStringProperty(time);
		this.BuyItNowPrice = new SimpleStringProperty(buyitnowprice);
		this.UniqueID = new SimpleStringProperty(uniqueid);
	}
	public String getItemName() {
		return ItemName.get();
	}
	public void setItemName(String name) {
		this.ItemName.set(name);
	}
	public String getBid() {
		return Bid.get();
	}
	public void setBid(String bid) {
		this.Bid.set(bid);
	}
	public String getDescription() {
		return Description.get();
	}
	public void setDescription(String description) {
		this.Description.set(description);
	}
	public String getLastBidder() {
		return LastBidder.get();
	}
	public void setLastBidder(String lastbidder) {
		this.LastBidder.set(lastbidder);
	}
	public String getSold() {
		return Sold.get();
	}
	public void setSold(String sold) {
		this.Sold.set(sold);
	}
	public String getTime() {
		return Time.get();
	}
	public void setTime(String time) {
		this.Time.set(time);
	}
	public String getBuyItNowPrice() {
		return BuyItNowPrice.get();
	}
	public void setBuyItNowPrice(String buyitnowprice) {
		this.BuyItNowPrice.set(buyitnowprice);
	}
	public String getUniqueID() {
		return UniqueID.get();
	}
	public void setUniqueID(String uniqueId) {
		this.UniqueID.set(uniqueId);
	}
	@Override
	public void addListener(InvalidationListener arg0) {
	}
	@Override
	public void removeListener(InvalidationListener arg0) {
	}
	
	
}
