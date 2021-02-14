package com.busvancar.cinema;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieSessionDAO {
    private Connection jdbcConnection;
	private final int ROWS = 12;
	
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
        String sql = "INSERT INTO session (movie_id, session_time, price) VALUES (?, ?, ?)";
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
	     String sqlQuery = "SELECT * FROM session WHERE movie_id = ? ORDER BY session_time DESC";
	         
	        connect();
	         
	        try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)) {
	        	
	        	statement.setInt(1, movie.getId());

	        	try(ResultSet rs = statement.executeQuery()){
	    			
					while(rs.next()) {
						movieSession = new MovieSession();
						movieSession.setSessionId(rs.getInt("session_id"));
						movieSession.setMovieId(rs.getInt("movie_id"));
						movieSession.setDateTime(rs.getTimestamp("session_time"));
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

	
	
	
	public List<MovieSession> getSchedule(int sortBy, int ascDesc, int genreIndex, LocalDate chosendate) throws SQLException {
		String[] ascendingDescending = {"DESC", "ASC"};
		String[] sortingBy = {"session.session_time", "session.price", "session.available"};
		String genre = "";
		String time = " session.session_time BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 1 WEEK) ";
		if(chosendate.isAfter(LocalDate.now())) {
			time = " session.session_time BETWEEN '"+chosendate+"' AND DATE_ADD('"+chosendate+"', INTERVAL 1 DAY) ";
		}
		
		if(genreIndex > 0 && genreIndex < Genre.genres_en_GB.length) {
			genre = " movie.genre = '"+genreIndex+"' AND ";
		}
		List<MovieSession> schedule = new ArrayList<>();
		double floatPrice;
        MovieSession movieSession = null;
        String sqlQuery = "SELECT movie.id, movie.title, movie.description_en, movie.description_uk, "
				+ " movie.duration, movie.genre, movie.poster, session.session_id, session.session_time, session.price, session.prepaid, session.available FROM movie "
				+ " INNER JOIN session ON movie.id = session.movie_id  "
				+ " WHERE "+genre+" "+time+"  ORDER BY  "+sortingBy[sortBy]+"  "+ascendingDescending[ascDesc]+" ; ";
         
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
					movieSession.setMovieGenre(Genre.genres_en_GB[rs.getInt("movie.genre")]);
					movieSession.setMovieGenreUa(Genre.genres_uk_UA[rs.getInt("movie.genre")]);
					movieSession.setMoviePoster(rs.getString("movie.poster"));
					movieSession.setSessionId(rs.getInt("session.session_id"));
					movieSession.setDateTime(rs.getTimestamp("session.session_time"));
					floatPrice = (double) rs.getInt("session.price");
					movieSession.setPrice(floatPrice/100);
					movieSession.setPrepaidSeats(rs.getInt("session.prepaid"));
					movieSession.setAvailableSeats(rs.getInt("session.available"));

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
		   		+ " movie.duration, movie.genre, movie.poster, session.session_id, session.session_time, session.price FROM movie "
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
	   	            	movieSession.setMovieGenre(Genre.genres_en_GB[rs.getInt("movie.genre")]);
	   	            	movieSession.setMovieGenreUa(Genre.genres_uk_UA[rs.getInt("movie.genre")]);
	   	            	movieSession.setMoviePoster(rs.getString("movie.poster"));
	   	            	floatPrice = (double) rs.getInt("session.price");
						movieSession.setPrice(floatPrice/100);
						movieSession.setDateTime(rs.getTimestamp("session.session_time"));
		   	        	
			         	
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

	public List<MovieSession> getSchedule(int sortBy, int ascDesc, String search) throws SQLException {
		String[] ascendingDescending = {"DESC", "ASC"};
		String[] sortingBy = {"session.session_time", "session.price", "session.available"};
		String searchParameter = "";
		if(!search.isBlank()) {
			searchParameter = "  movie.title LIKE '%"+search+"%'  AND  ";
		}
		
		List<MovieSession> schedule = new ArrayList<>();
		double floatPrice;
        MovieSession movieSession = null;
        String sqlQuery = "SELECT movie.id, movie.title, movie.description_en, movie.description_uk, "
				+ " movie.duration, movie.genre, movie.poster, session.session_id, session.session_time, session.price, session.prepaid, session.available FROM movie "
				+ " INNER JOIN session ON movie.id = session.movie_id  "
				+ " WHERE  "+searchParameter+"  session.session_time >= NOW()   ORDER BY  "+sortingBy[sortBy]+"  "+ascendingDescending[ascDesc]+" ; ";
			//	+ " WHERE  movie.title LIKE  '%?%' AND session.session_time >= NOW()   ORDER BY ?   ?   ; ";
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
					movieSession.setMovieGenre(Genre.genres_en_GB[rs.getInt("movie.genre")]);
					movieSession.setMovieGenreUa(Genre.genres_uk_UA[rs.getInt("movie.genre")]);
					movieSession.setMoviePoster(rs.getString("movie.poster"));
					movieSession.setSessionId(rs.getInt("session.session_id"));
					movieSession.setDateTime(rs.getTimestamp("session.session_time"));
					floatPrice = (double) rs.getInt("session.price");
					movieSession.setPrice(floatPrice/100);
					movieSession.setPrepaidSeats(rs.getInt("session.prepaid"));
					movieSession.setAvailableSeats(rs.getInt("session.available"));

					schedule.add(movieSession);
				}
        	
        	
        	statement.close();
        }catch (SQLException e) {
			e.printStackTrace();
		}
    
        disconnect();
         
		return schedule;
	}

	

 }