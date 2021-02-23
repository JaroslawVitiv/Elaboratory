package com.busvancar.cinema;
/**
 * Main User class with its getters and setters
 * @author Vitiv
 *
 */

public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private int admin;
	private double revenue;
	private long generatedLong;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}
	
	public double getRevenue() {
		return revenue;
	}

	public void setConfirmationCode(long generatedLong) {
		this.generatedLong = generatedLong;
	}
	
	public long getConfirmationCode() {
		return generatedLong;
	}
}
