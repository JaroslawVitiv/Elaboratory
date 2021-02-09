package com.busvancar.cinema;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieSessionDAO {
    private Connection jdbcConnection;
	private final int ROWS = 12;
	
	public  MovieSessionDAO() {
    	System.out.println("New Movie Session DAO was created...");
    }
     
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
     
    public boolean insertMovieSession2db(MovieSession ms) throws SQLException {
        String sql = "INSERT INTO session (movie_id, time, price) VALUES (?, ?, ?)";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, ms.getMovieId());
        statement.setTimestamp(2, ms.getDateTime());
        double coins = ms.getPrice() * 100;
        int price2insert = (int) coins; 
        statement.setInt(3, price2insert);
       
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }

	public List<MovieSession> getAllMovieSessions(Movie movie) throws SQLException {
		 
		List<MovieSession> listAllMovieSessions = new ArrayList<>();
		 MovieSession movieSession = null;
		 double floatPrice;
	     String sqlQuery = "SELECT * FROM session WHERE movie_id = ? ORDER BY time DESC";
	         
	        connect();
	         
	        try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)) {
	        	
	        	statement.setInt(1, movie.getId());

	        	try(ResultSet rs = statement.executeQuery()){
	    			
					while(rs.next()) {
						movieSession = new MovieSession();
						movieSession.setSessionId(rs.getInt("session_id"));
						movieSession.setMovieId(rs.getInt("movie_id"));
						movieSession.setDateTime(rs.getTimestamp("time"));
						floatPrice = (double) rs.getInt("price");
						movieSession.setPrice(floatPrice/100);
						
						
						listAllMovieSessions.add(movieSession);
					}
	        	}
	        	statement.close();
	        }catch (SQLException e) {
				e.printStackTrace();
			}
	    
	        disconnect();
	         
	        return listAllMovieSessions;
	}

	public boolean removeMovieSession(MovieSession ms) throws SQLException {
		 String sql = "DELETE FROM session WHERE session_id = ? ";
	        connect();
	         
	        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
	        statement.setInt(1, ms.getSessionId());
	         
	        boolean rowRemoved = statement.executeUpdate() > 0;
	        statement.close();
	        disconnect();
	        return rowRemoved;
	   }

	public List<MovieSession> getSchedule() throws SQLException {
		List<MovieSession> schedule = new ArrayList<>();
		double floatPrice;
        MovieSession movieSession = null;
        String sqlQuery = "SELECT movie.id, movie.title, movie.description_en, movie.description_uk, "
				+ " movie.duration, genre.title, movie.poster, session.session_id, session.time, session.price FROM movie "
				+ " INNER JOIN genre ON movie.genre = genre.genre_id "
				+ " INNER JOIN session ON movie.id = session.movie_id;";
         
        connect();
         
        try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery);
        	ResultSet rs = statement.executeQuery();){
    			
				while(rs.next()) {
					movieSession = new MovieSession();
					movieSession.setMovieId(rs.getInt("movie.id"));
					movieSession.setMovieTitle(rs.getString("movie.title"));
					movieSession.setMovieDescriptionEn(rs.getString("movie.description_en"));
					movieSession.setMovieDescriptionUa(rs.getString("movie.description_uk"));
					movieSession.setMovieDuration(rs.getInt("movie.duration"));
					movieSession.setMovieGenre(rs.getString("genre.title"));
					movieSession.setMoviePoster(rs.getString("movie.poster"));
					movieSession.setSessionId(rs.getInt("session.session_id"));
					movieSession.setDateTime(rs.getTimestamp("session.time"));
					floatPrice = (double) rs.getInt("session.price");
					movieSession.setPrice(floatPrice/100);
					
					
					schedule.add(movieSession);
				}
			
        	statement.close();
        }catch (SQLException e) {
			e.printStackTrace();
		}
    
        disconnect();
         
		return schedule;
	}

	public MovieSession getMovieSession(int sessionID) throws SQLException {
		MovieSession movieSession = null;
		double floatPrice;
		   String sqlQuery = "SELECT movie.id, movie.title, movie.description_en, movie.description_uk, "
		   		+ " movie.duration, genre.title, movie.poster, session.session_id, session.time, session.price FROM movie "
		   		+ "	 INNER JOIN genre ON movie.genre = genre.genre_id "
		   		+ "	 INNER JOIN session ON movie.id = session.movie_id WHERE session.session_id = ? ";
		  
	       
	        connect();
	         
	        try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){
	         	statement.setInt(1, sessionID);
	   	        try(ResultSet rs = statement.executeQuery()){
	   	        	movieSession = new MovieSession();
	   	            if(rs.next()) {
	   	            	movieSession.setMovieId(rs.getInt("movie.id"));
	   	            	movieSession.setMovieTitle(rs.getString("movie.title"));
	   	            	movieSession.setMovieDescriptionEn(rs.getString("movie.description_en"));
	   	            	movieSession.setMovieDescriptionUa(rs.getString("movie.description_uk"));
	   	            	movieSession.setMovieDuration(rs.getInt("movie.duration"));
	   	            	movieSession.setMovieGenre(rs.getString("genre.title"));
	   	            	movieSession.setMoviePoster(rs.getString("movie.poster"));
	   	            	floatPrice = (double) rs.getInt("session.price");
						movieSession.setPrice(floatPrice/100);
		   	        	
			         	
	   	            }
	   	       }
	   	        statement.close();
	        }catch (SQLException e) {
				e.printStackTrace();
			}
	   	
	         
	        
	        disconnect();
	        return movieSession;
	}
	
	public int getMovieSessionBasePrice(int movieSession) {
		String sqlQuery = "SELECT price FROM session WHERE session_id = ? ";
		try {
			connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
   	 	try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){
         	statement.setInt(1, movieSession);
   	        try(ResultSet rs = statement.executeQuery()){
	         	   if(rs.next()) {
	         			return rs.getInt("price");
	         	   }
   	        }
        }catch (SQLException e) {
			e.printStackTrace();
		}
   	 	
	   	
		return 0;
	}
	
	
	public double getPrice(int movieSessionBasePrice, int seat) {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		
		int tempSeat = seat-1;
		double priceIncrementRate = 1;
		while(tempSeat > ROWS ) {
			priceIncrementRate += 0.049;
			tempSeat -= ROWS;
		}
		
		return Double.parseDouble(df.format(movieSessionBasePrice * priceIncrementRate / 100));
	}

 }