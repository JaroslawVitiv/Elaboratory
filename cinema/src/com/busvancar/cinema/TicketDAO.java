package com.busvancar.cinema;

/**
 * @author Vitiv
 * Data Access Object to Ticket class
 * Interacts with db MySQL
 */
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
	private static final int SEATS_IN_ROW = 12;
	private Connection jdbcConnection;
	private final int SEATS = 96;
	
    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
               Class.forName("com.mysql.cj.jdbc.Driver");
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
		} catch (SQLException ex) {
			ex.printStackTrace();
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

	

	public boolean removeTicket(Ticket ticket) {
		String sql = "DELETE FROM ticket WHERE seat = ? AND session_id = ? AND purchaser_id = ? AND session_token = ?";
		
	     	try {
				connect();
			} catch (SQLException e) {
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

	public Ticket[] getAllTickets(int movieSession) {
		Ticket[] listAllTickets = new Ticket[SEATS];
		 Ticket ticket = null;
		 double floatPrice;
	     String sqlQuery = "SELECT * FROM ticket WHERE session_id = ? ORDER BY seat ASC";
	         
	        try {
				connect();
			} catch (SQLException ex) {
				ex.printStackTrace();
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
				e.printStackTrace();
			}
	         
	        return listAllTickets;
	}

	public int getTicketCount(String sessionToken) {
		int total = 0;
		String sqlQuery = "SELECT COUNT(ticket_id) AS total FROM ticket WHERE session_token = ? AND purchaser_id = 0 ";
		try {
			connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
   	 	
   	 	try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){
         	statement.setString(1, sessionToken);
   	        try(ResultSet rs = statement.executeQuery()){
   	        	if(rs.next())
	         	   total = rs.getInt("total");
   	        }
        }catch (SQLException e) {
			e.printStackTrace();
		}
   	   try {
			disconnect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return total;
	}

	public List<Ticket> getBookedUnpaidTickets(String sessionToken) {
		List<Ticket> bookedUnpaidList = new ArrayList<>();
		double floatPrice = 0;
	     String sqlQuery = "SELECT ticket.ticket_id, ticket.session_id, ticket.seat, ticket.price, ticket.purchaser_id, ticket.session_token, ticket.time, "
	     		+ " session.movie_id, session.session_time, movie.title, movie.duration "
	     		+ " FROM ticket "
	     		+ " INNER JOIN session ON ticket.session_id = session.session_id "
	     		+ " INNER JOIN movie ON movie.id = session.movie_id "
	     		+ "  WHERE ticket.session_token = ? AND ticket.purchaser_id = 0 ORDER BY time ASC";
	         Ticket ticket = null;
	        try {
				connect();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	         
	        try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)) {
	        	
	        	statement.setString(1, sessionToken);

	        	try(ResultSet rs = statement.executeQuery()){
	    			
					while(rs.next()) {
						ticket = new Ticket();
						ticket.setMovieTitle(rs.getString("movie.title"));
						ticket.setMovieDuration(rs.getInt("movie.duration"));
						ticket.setTicketId(rs.getInt("ticket.ticket_id"));
						ticket.setSessionId(rs.getInt("ticket.session_id"));
						ticket.setSeat(rs.getInt("ticket.seat"));
						floatPrice = (double) rs.getInt("ticket.price");
						ticket.setPrice(floatPrice/100);
						ticket.setPurchaserId(rs.getInt("ticket.purchaser_id"));
						ticket.setSessionToken(rs.getString("ticket.session_token"));
						ticket.setTime(rs.getTimestamp("ticket.time"));
						ticket.setMovieSessionTime(rs.getTimestamp("session.session_time"));
						
						bookedUnpaidList.add(ticket);
					}
	        	}
	        	statement.close();
	        }catch (SQLException e) {
				e.printStackTrace();
			}
	    
	        try {
				disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	         
		return bookedUnpaidList;
	}

	public boolean removeFromInvoice(Ticket ticket) {
		 String sql = "DELETE FROM ticket WHERE ticket_id = ? AND session_token = ?";
         
	        try {
				connect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	         
	        try( PreparedStatement statement = jdbcConnection.prepareStatement(sql)){
		        statement.setInt(1, ticket.getTicketId());
		        statement.setString(2, ticket.getSessionToken());

		         
		        boolean rowDeleted = statement.executeUpdate() > 0;
		        statement.close(); 
		        return rowDeleted;
	        } catch (SQLException ex) {
				ex.printStackTrace();
			}
	        try {
				disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        return false;  
		
	}

	public void purgeAllUnpaidTickets(String sessionToken) {
		String sql = "DELETE FROM ticket WHERE purchaser_id = 0 AND session_token = ?";
        
        try {
			connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
         
        try( PreparedStatement statement = jdbcConnection.prepareStatement(sql)){
	        statement.setString(1, sessionToken);
	        
	        statement.executeUpdate();
	        statement.close(); 
        } catch (SQLException ex) {
			ex.printStackTrace();
		}
        try {
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public boolean setUserId2pay(User user, String sessionToken) {
		String sql = "UPDATE ticket SET purchaser_id = ? WHERE purchaser_id = 0 AND session_token = ?";
        
        try {
			connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
         
        try( PreparedStatement statement = jdbcConnection.prepareStatement(sql)){
	        statement.setInt(1, user.getId());
	        statement.setString(2, sessionToken);

	         
	        boolean rowUpdated = statement.executeUpdate() > 0;
	        statement.close(); 
	        return rowUpdated;
        } catch (SQLException ex) {
			ex.printStackTrace();
		}
        try {
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return false;  
	
	}

	public double getTodaysSum(User user, String sessionToken) {
		double total = 0;
		String sqlQuery = "SELECT SUM(price) AS total FROM ticket WHERE purchaser_id = ? AND session_token = ? ";
		try {
			connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
   	 	
   	 	try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){
         	statement.setInt(1, user.getId());
         	statement.setString(2, sessionToken);
   	        try(ResultSet rs = statement.executeQuery()){
   	        	if(rs.next()) {
   	        		double floatPrice = (double) rs.getInt("total");
					total =  floatPrice/100;
   	        	}
   	        }
        }catch (SQLException e) {
			e.printStackTrace();
		}
   	 	
   	   try {
			disconnect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
   	   
		return total;
	}

	public void clearAllUnpaid() {
        String sql = "DELETE FROM ticket WHERE time < DATE_SUB( NOW(), INTERVAL 15 MINUTE ) AND purchaser_id = 0 ";
        
        try {
			connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
         
        try( PreparedStatement statement = jdbcConnection.prepareStatement(sql)){
	        
	        statement.executeUpdate();
	        statement.close(); 
        } catch (SQLException ex) {
			ex.printStackTrace();
		}
        try {
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	public void updateMovieSessionAvailableSeats(int movieSession, int availableSeats) {
		 String sql = "UPDATE session SET available = ?  WHERE session_id = ? ";
	        
	        try {
				connect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	         
	        try( PreparedStatement statement = jdbcConnection.prepareStatement(sql)){
	        	statement.setInt(1, availableSeats);
	        	statement.setInt(2, movieSession);
	        	
		        statement.executeUpdate();
		        statement.close(); 
	        } catch (SQLException ex) {
				ex.printStackTrace();
			}
	        try {
				disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}

	public int getBookedSeats(int movieSession) {
		int bookedSeats = 0;
		String sqlQuery = "SELECT COUNT(ticket_id) AS avSeats FROM ticket WHERE session_id = ? ";
		try {
			connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
   	 	
   	 	try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){
         	statement.setInt(1, movieSession);
         	
   	        try(ResultSet rs = statement.executeQuery()){
   	        	if(rs.next()) {
   	        		bookedSeats  = rs.getInt("avSeats");
				}
   	        }
        }catch (SQLException e) {
			e.printStackTrace();
		}
   	 	
   	   try {
			disconnect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
   	   
		return bookedSeats;
	}

	public List<Ticket> getAllTickets(User user) {
		List<Ticket> allUsersTickets = new ArrayList<>();
		Ticket ticket;
		String sqlQuery = " SELECT ticket.ticket_id, ticket.session_id, ticket.seat, ticket.price, ticket.purchaser_id, ticket.session_token, ticket.time, "
				+ "	session.movie_id, session.session_time, movie.title, movie.duration FROM ticket   "
				+ " INNER JOIN session ON ticket.session_id = session.session_id "
	     		+ " INNER JOIN movie ON movie.id = session.movie_id "
				+ "   WHERE purchaser_id = ? ORDER BY time ASC";
         
	    try {
	    	connect();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	         
	    try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)) {
	        	
	       	statement.setInt(1, user.getId());

	       	try(ResultSet rs = statement.executeQuery()){
	    			
				while(rs.next()) {
					ticket = new Ticket();
					ticket.setMovieTitle(rs.getString("movie.title"));
					ticket.setMovieDuration(rs.getInt("movie.duration"));
					ticket.setTicketId(rs.getInt("ticket.ticket_id"));
					ticket.setSessionId(rs.getInt("ticket.session_id"));
					ticket.setSeat(rs.getInt("ticket.seat"));
					double floatPrice = (double) rs.getInt("ticket.price");
					ticket.setPrice(floatPrice/100);
					ticket.setPurchaserId(rs.getInt("ticket.purchaser_id"));
					ticket.setSessionToken(rs.getString("ticket.session_token"));
					ticket.setTime(rs.getTimestamp("ticket.time"));
					ticket.setMovieSessionTime(rs.getTimestamp("session.session_time"));
					int row = (((ticket.getSeat()-((ticket.getSeat()-1) % SEATS_IN_ROW))/SEATS_IN_ROW)+1);
					ticket.setRow(row);
						
					allUsersTickets.add(ticket);
				}
	       	}
	       	statement.close();
	    }catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    try {
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 	return allUsersTickets;
	}

	public Ticket getTicket(int ticketId, String sessionToken) {
		Ticket ticket = new Ticket();
		String sqlQuery = " SELECT ticket.ticket_id, ticket.session_id, ticket.seat, ticket.price, ticket.purchaser_id, ticket.session_token, ticket.time, "
				+ "	session.movie_id, session.session_time, movie.title, movie.duration FROM ticket   "
				+ " INNER JOIN session ON ticket.session_id = session.session_id "
	     		+ " INNER JOIN movie ON movie.id = session.movie_id "
				+ "   WHERE ticket.ticket_id = ? AND ticket.session_token = ? ";
         
	    try {
	    	connect();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	         
	    try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)) {
	        	
	       	statement.setInt(1, ticketId);
	       	statement.setString(2, sessionToken);


	       	try(ResultSet rs = statement.executeQuery()){
	    			
				if(rs.next()) {
					ticket.setMovieTitle(rs.getString("movie.title"));
					ticket.setMovieDuration(rs.getInt("movie.duration"));
					ticket.setTicketId(rs.getInt("ticket.ticket_id"));
					ticket.setSessionId(rs.getInt("ticket.session_id"));
					ticket.setSeat(rs.getInt("ticket.seat"));
					double floatPrice = (double) rs.getInt("ticket.price");
					ticket.setPrice(floatPrice/100);
					ticket.setPurchaserId(rs.getInt("ticket.purchaser_id"));
					ticket.setSessionToken(rs.getString("ticket.session_token"));
					ticket.setTime(rs.getTimestamp("ticket.time"));
					ticket.setMovieSessionTime(rs.getTimestamp("session.session_time"));
					int row = (((ticket.getSeat()-((ticket.getSeat()-1) % SEATS_IN_ROW))/SEATS_IN_ROW)+1);
					ticket.setRow(row);
						
				}
	       	}
	       	statement.close();
	    }catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    try {
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ticket;
	}

	public double getIncomes(LocalDate start, LocalDate end) {
		double total = 0;
		double floatPrice = 0;
		String sqlQuery = "SELECT SUM(price) AS total FROM ticket WHERE time BETWEEN ? AND DATE_ADD(? , INTERVAL 1 DAY)  ";
		try {
			connect();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
   	 	
   	 	try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){
         	statement.setDate(1, Date.valueOf(start));
         	statement.setDate(2, Date.valueOf(end));
   	        
         	try(ResultSet rs = statement.executeQuery()){
   	        	if(rs.next()) {
   	        		floatPrice = (double) rs.getInt("total");
					total =  floatPrice/100;
   	        	}
   	        }
        }catch (SQLException e) {
			e.printStackTrace();
		}
   	 	
   	   try {
			disconnect();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
   	   
		return total;
	}

	public int getTicketsBought(LocalDate start, LocalDate end) {
		int total = 0;
		String sqlQuery = "SELECT COUNT(price) AS total FROM ticket WHERE time BETWEEN ? AND DATE_ADD(? , INTERVAL 1 DAY)  ";
		try {
			connect();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
   	 	
   	 	try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){
         	statement.setDate(1, Date.valueOf(start));
         	statement.setDate(2, Date.valueOf(end));
   	        
         	try(ResultSet rs = statement.executeQuery()){
   	        	if(rs.next()) {
   	        		total = rs.getInt("total");
   	        	}
   	        }
        }catch (SQLException e) {
			e.printStackTrace();
		}
   	 	
   	   try {
			disconnect();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
   	   
		return total;
	}

	public double getAveTicketPrice(LocalDate start, LocalDate end) {
		double total = 0;
		double floatPrice = 0;
		String sqlQuery = "SELECT AVG(price) AS total FROM ticket WHERE time BETWEEN ? AND DATE_ADD(? , INTERVAL 1 DAY)  ";
		try {
			connect();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
   	 	
   	 	try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){
         	statement.setDate(1, Date.valueOf(start));
         	statement.setDate(2, Date.valueOf(end));
   	        
         	try(ResultSet rs = statement.executeQuery()){
   	        	if(rs.next()) {
   	        		floatPrice = (double) rs.getInt("total");
					total =  floatPrice/100;
   	        	}
   	        }
        }catch (SQLException e) {
			e.printStackTrace();
		}
   	 	
   	   try {
			disconnect();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
   	   
		return total;
	}

	

	public Ticket getTicket(int ticketId) {
		 Ticket ticket;
		 double floatPrice = 0;
		  String sqlQuery = " SELECT movie.title, ticket.ticket_id, ticket.session_id, ticket.seat, ticket.price, ticket.purchaser_id, ticket.session_token, ticket.time, "
		  		+ "	session.movie_id, session.session_time, movie.title, movie.duration FROM ticket   "
		  		+ " INNER JOIN session ON ticket.session_id = session.session_id "
		  		+ " INNER JOIN movie ON movie.id = session.movie_id "
		  		+ "  WHERE ticket.ticket_id = ? ";
	         
	        try {
				connect();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
	         
	        try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)) {
	        	
	        	statement.setInt(1, ticketId);

	        	try(ResultSet rs = statement.executeQuery()){
	    			
					if(rs.next()) {
						ticket = new Ticket();
						ticket.setTicketId(ticketId);
						ticket.setMovieTitle(rs.getString("movie.title"));
						ticket.setSessionId(rs.getInt("ticket.session_id"));
						ticket.setMovieDuration(rs.getInt("movie.duration"));
						ticket.setSeat(rs.getInt("ticket.seat"));
						floatPrice = (double) rs.getInt("ticket.price");
						ticket.setPrice(floatPrice/100);
						ticket.setPurchaserId(rs.getInt("ticket.purchaser_id"));
						ticket.setSessionToken(rs.getString("ticket.session_token"));
						ticket.setTime(rs.getTimestamp("session.session_time"));
						int row = (((ticket.getSeat()-((ticket.getSeat()-1) % SEATS_IN_ROW))/SEATS_IN_ROW)+1);
						ticket.setRow(row);
						return ticket;
					}
	        	}
	        	statement.close();
	        }catch (SQLException e) {
				e.printStackTrace();
			}
	    
	        try {
				disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	         
	        return null;
	}
	
}
