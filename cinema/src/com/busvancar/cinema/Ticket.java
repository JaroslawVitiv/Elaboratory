package com.busvancar.cinema;
/**
 * @author Vitiv
 * Main ticket class
 * with its getters and setters
 */

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
	private String movieTitle;
	private int movieDuration;
	private Timestamp movieSessionTime;
	private int row; 
	

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
	
	public String getMovieTitle() {
		return movieTitle;
	}



	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	
	public int getMovieDuration() {
		return movieDuration;
	}

	public void setMovieDuration(int movieDuration) {
		this.movieDuration = movieDuration;
	}

	public Timestamp getMovieSessionTime() {
		return movieSessionTime;
	}


	public void setMovieSessionTime(Timestamp movieSessionTime) {
		this.movieSessionTime = movieSessionTime;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	
}
