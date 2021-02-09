package com.busvancar.cinema;

import java.sql.Timestamp;

public class Ticket {
	

	private int ticketId;
	private int seat;
	private int sessionId;
	private double price;
	private String genre;
	private int purchaserId;
	private String sessionToken;
	private Timestamp time; 
	


	public Ticket() {
		System.out.print("New ticket was created...");
	}



	public int getTicketId() {
		return ticketId;
	}



	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}



	public int getSeat() {
		return seat;
	}



	public void setSeat(int seat) {
		this.seat = seat;
	}



	public int getSessionId() {
		return sessionId;
	}



	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public String getGenre() {
		return genre;
	}



	public void setGenre(String genre) {
		this.genre = genre;
	}



	public int getPurchaserId() {
		return purchaserId;
	}



	public void setPurchaserId(int purchaserId) {
		this.purchaserId = purchaserId;
	}



	public String getSessionToken() {
		return sessionToken;
	}



	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}



	public Timestamp getTime() {
		return time;
	}



	public void setTime(Timestamp time) {
		this.time = time;
	}
}
