package com.busvancar.cinema;

public class Movie {
	

	private int id;
	private String title;
	private String descriptionUa;
	private String descriptionEn;
	private int duration;
	private String genre;
	private String genreUa;
	private int genre_id;
	private String poster;
	private double price;


	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setGenreId(int genre_id) {
		this.genre_id = genre_id;
	}
	
	public int getGenreId() {
		return genre_id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setDescriptionUa(String descriptionUa) {
		this.descriptionUa = descriptionUa;
	}
	
	public String getDescriptionUa() {
		return descriptionUa;
	}
	
	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}
	
	public String getDescriptionEn() {
		return descriptionEn;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenreUa(String genreUa) {
		this.genreUa = genreUa;
	}
	
	public String getGenreUa() {
		return genreUa;
	}
	
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	public String getPoster() {
		return poster;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}
}
