package com.busvancar.cinema;

import java.sql.Timestamp;

public class MovieSession {
	private int sessionId;
	private int movieId;
	private double price;
	private Timestamp dateTime;
	private String moviePoster;
	private String movieTitle;
	private String movieDescriptionEn;
	private String movieDescriptionUa;
	private int movieDuration;
	private String movieGenre;
	
	
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp timestamp) {
		this.dateTime = timestamp;
	}
	public String getMoviePoster() {
		return moviePoster;
	}
	public void setMoviePoster(String moviePoster) {
		this.moviePoster = moviePoster;
	}
	
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	
	public String getMovieDescriptionEn() {
		return movieDescriptionEn;
	}
	public void setMovieDescriptionEn(String movieDescriptionEn) {
		this.movieDescriptionEn = movieDescriptionEn;
	}
	
	public String getMovieDescriptionUa() {
		return movieDescriptionUa;
	}
	
	public void setMovieDescriptionUa(String movieDescriptionUa) {
		this.movieDescriptionUa = movieDescriptionUa;
	}
	
	public String getMovieGenre() {
		return movieGenre;
	}
	
	public void setMovieGenre(String movieGenre) {
		this.movieGenre = movieGenre;
	}
	
	
	public int getMovieDuration() {
		return movieDuration;
	}
	
	public void setMovieDuration(int movieDuration) {
		this.movieDuration =  movieDuration;
	}
	
	
}
