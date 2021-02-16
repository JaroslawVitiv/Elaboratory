package com.busvancar.cinema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    private Connection jdbcConnection;
    int coins;
     
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
     
    public boolean insertMovie2db(Movie movie) throws SQLException {
        String sql = "INSERT INTO movie (title, description_en, description_uk, genre, duration, poster, default_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, movie.getTitle());
        statement.setString(2, movie.getDescriptionEn());
        statement.setString(3, movie.getDescriptionUa());
        statement.setInt(4, movie.getGenreId());
        statement.setInt(5, movie.getDuration());
        statement.setString(6, movie.getPoster());
        coins = (int) (movie.getPrice()*100);
        statement.setInt(7, coins);
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }
     
    public List<Movie> listAllMovies() throws SQLException {
        List<Movie> listMovies = new ArrayList<>();
        Movie movie = null;
        String sqlQuery = "SELECT * FROM movie ";
         
        connect();
         
        try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery);
        	ResultSet rs = statement.executeQuery();){
    			
				while(rs.next()) {
					movie = new Movie();
					movie.setId(rs.getInt("id"));
					movie.setTitle(rs.getString("title"));
					movie.setDescriptionEn(rs.getString("description_en"));
					movie.setDescriptionUa(rs.getString("description_uk"));
					movie.setDuration(rs.getInt("duration"));
					movie.setGenre(Genre.genres_en_GB[rs.getInt("genre")]);
					movie.setPoster(rs.getString("poster"));
					movie.setPrice(rs.getDouble("default_price")/100);
					
					listMovies.add(movie);
				}
			
        	statement.close();
        }catch (SQLException e) {
			e.printStackTrace();
		}
    
        disconnect();
         
        return listMovies;
    }
    
    public Movie getMovie(int movieNumber) throws SQLException  {
		Movie movie = null;
		   String sqlQuery = "SELECT * FROM movie WHERE id = ? ";
		  
	       
	        connect();
	         
	        try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){
	         	statement.setInt(1, movieNumber);
	   	        try(ResultSet rs = statement.executeQuery()){
	   	            movie = new Movie();
	   	            if(rs.next()) {
	   	            	movie.setId(movieNumber);
		   	        	movie.setTitle(rs.getString("title"));
						movie.setDescriptionEn(rs.getString("description_en"));
						movie.setDescriptionUa(rs.getString("description_uk"));
						movie.setDuration(rs.getInt("duration"));
						movie.setGenre(Genre.genres_en_GB[rs.getInt("genre")]);
						movie.setGenreUa(Genre.genres_en_GB[rs.getInt("genre")]);
						movie.setPoster(rs.getString("poster"));
						movie.setPrice(rs.getDouble("default_price"));
		   	        }
	   	       }
	   	        statement.close();
	        }catch (SQLException e) {
				e.printStackTrace();
			}
	   	
	         
	        
	        disconnect();
	        return movie;
	}

	public boolean removeMovie(Movie movie) throws SQLException {
		 String sql = "DELETE FROM movie WHERE id = ? ";
	        connect();
	         
	        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
	        statement.setInt(1, movie.getId());
	         
	        boolean rowRemoved = statement.executeUpdate() > 0;
	        statement.close();
	        disconnect();
	        return rowRemoved;

	}
    
	
}