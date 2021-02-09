package com.busvancar.cinema;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
	private Connection jdbcConnection;
	private final int ROWS = 12;
	private final int SEATS = 96;
	
    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = ConnectionPool.getInstance().getConnection();
        }
    }
     
    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }
    
	public boolean isCreated(Ticket ticket)  {
		String sqlQuery = "SELECT * FROM ticket WHERE seat = ? AND session_id = ? ";
		try {
			connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
   	 	
   	 	try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){
         	statement.setInt(1, ticket.getSeat());
	        statement.setInt(2, ticket.getSessionId());
   	        try(ResultSet rs = statement.executeQuery()){
	         	   return rs.next();
   	        }
        }catch (SQLException e) {
			e.printStackTrace();
		}
   	
		return false;
	}

	
	public boolean createTicket(Ticket ticket) {
		String sql = "INSERT INTO ticket (seat, session_id, price, purchaser_id, session_token, time) VALUES (?, ?, ?, ?, ?, ?)";
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		
	     	try {
				connect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	         
	        try(PreparedStatement statement = jdbcConnection.prepareStatement(sql);){
	       
			
		        statement.setInt(1, ticket.getSeat());
		        statement.setInt(2, ticket.getSessionId());
		        double price =  Double.parseDouble(df.format(ticket.getPrice() * 100));
		        int coins = (int) price;
		        statement.setInt(3, coins);
		        statement.setInt(4, ticket.getPurchaserId());
		        statement.setString(5, ticket.getSessionToken());
		        statement.setTimestamp(6, ticket.getTime());
	
		        boolean rowInserted = statement.executeUpdate() > 0;
		        statement.close();
		        return rowInserted;
	        } catch(SQLException ex) {
	        	ex.printStackTrace();
	        }

	        
	        
	        try {
				disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        
	       return false;
	 }

	public double getPrice(int movieSessionBasePrice, int seat) {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		
		int tempSeat = seat-1;
		double priceIncrementRate = 1;
		while(tempSeat > ROWS) {
			priceIncrementRate += 0.049;
			tempSeat -= ROWS;
		}
		
		return Double.parseDouble(df.format(movieSessionBasePrice * priceIncrementRate / 100));
	}

	public boolean removeTicket(Ticket ticket) {
		String sql = "DELETE FROM ticket WHERE seat = ? AND session_id = ? AND purchaser_id = ? AND session_token = ?";
		
	     	try {
				connect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	         
	        try(PreparedStatement statement = jdbcConnection.prepareStatement(sql);){
	       
			
		        statement.setInt(1, ticket.getSeat());
		        statement.setInt(2, ticket.getSessionId());
		        statement.setInt(3, ticket.getPurchaserId());
		        statement.setString(4, ticket.getSessionToken());
		        
		        boolean rowRemoved = statement.executeUpdate() > 0;
		        statement.close();
		        return rowRemoved;
	        } catch(SQLException ex) {
	        	ex.printStackTrace();
	        }

	        
	        
	        try {
				disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        
	       return false;
	}

	public Ticket[] geAllTickets(int movieSession) {
		Ticket[] listAllTickets = new Ticket[SEATS];
		 Ticket ticket = null;
		 double floatPrice;
	     String sqlQuery = "SELECT * FROM ticket WHERE session_id = ? ORDER BY seat ASC";
	         
	        try {
				connect();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	         
	        try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)) {
	        	
	        	statement.setInt(1, movieSession);

	        	try(ResultSet rs = statement.executeQuery()){
	    			
					while(rs.next()) {
						ticket = new Ticket();
						ticket.setSessionId(rs.getInt("session_id"));
						ticket.setSeat(rs.getInt("seat"));
						floatPrice = (double) rs.getInt("price");
						ticket.setPrice(floatPrice/100);
						ticket.setPurchaserId(rs.getInt("purchaser_id"));
						ticket.setSessionToken(rs.getString("session_token"));
						ticket.setTime(rs.getTimestamp("time"));
						
						listAllTickets[rs.getInt("seat")-1] = ticket;
					}
	        	}
	        	statement.close();
	        }catch (SQLException e) {
				e.printStackTrace();
			}
	    
	        try {
				disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
	        return listAllTickets;
	}
}
