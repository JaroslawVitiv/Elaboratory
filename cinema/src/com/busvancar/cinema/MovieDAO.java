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
        String sqlQuery = "SELECT movie.id, movie.title, movie.description_en, movie.description_uk, "
				+ " movie.duration, genre.title, movie.poster, movie.default_price FROM movie "
				+ "INNER JOIN genre ON movie.genre = genre.genre_id ;";
         
        connect();
         
        try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery);
        	ResultSet rs = statement.executeQuery();){
    			
				while(rs.next()) {
					movie = new Movie();
					movie.setId(rs.getInt("movie.id"));
					movie.setTitle(rs.getString("movie.title"));
					movie.setDescriptionEn(rs.getString("movie.description_en"));
					movie.setDescriptionUa(rs.getString("movie.description_uk"));
					movie.setDuration(rs.getInt("movie.duration"));
					movie.setGenre(rs.getString("genre.title"));
					movie.setPoster(rs.getString("movie.poster"));
					movie.setPrice(rs.getDouble("movie.default_price"));
					
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
		   String sqlQuery = "SELECT movie.title, movie.description_en, movie.description_uk, "
					+ " movie.duration, genre.title, movie.poster, movie.default_price FROM movie "
					+ " INNER JOIN genre ON movie.genre = genre.genre_id WHERE movie.id = ? ";
		  
	       
	        connect();
	         
	        try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){
	         	statement.setInt(1, movieNumber);
	   	        try(ResultSet rs = statement.executeQuery()){
	   	            movie = new Movie();
	   	            if(rs.next()) {
	   	            	movie.setId(movieNumber);
		   	        	movie.setTitle(rs.getString("movie.title"));
						movie.setDescriptionEn(rs.getString("movie.description_en"));
						movie.setDescriptionUa(rs.getString("movie.description_uk"));
						movie.setDuration(rs.getInt("movie.duration"));
						movie.setGenre(rs.getString("genre.title"));
						movie.setPoster(rs.getString("movie.poster"));
						movie.setPrice(rs.getDouble("movie.default_price"));
		   	        	
			         	
	   	            }
	   	       }
	   	        statement.close();
	        }catch (SQLException e) {
				e.printStackTrace();
			}
	   	
	         
	        
	        disconnect();
	        return movie;
	}
    
    public String getGenreOptions() throws SQLException {
    	StringBuilder genreOptions = new StringBuilder();
    	String sqlQuery = "SELECT * FROM genre ";

        connect();
         
        try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){

        	try(ResultSet rs = statement.executeQuery()){
        		genreOptions.append("<option value=\"0\">Genre</option>");
   	            while(rs.next()) {
   	            	genreOptions.append("<option value=\"");
   	            	genreOptions.append(rs.getString("genre_id"));
   	            	genreOptions.append("\">");
   	            	genreOptions.append(rs.getString("title"));
   	            	genreOptions.append("</option>");
   	            }
   	       }
   	        statement.close();
        }catch (SQLException e) {
			e.printStackTrace();
		}
   	
         
        
        disconnect();
    	
    	return genreOptions.toString();
    }

	    
    
     /*
    public boolean deleteMovie(Movie movie) throws SQLException {
        String sql = "DELETE FROM movie where id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, movie.getId());
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;     
    }
     
    public boolean updateMovie(Movie movie) throws SQLException {
        String sql = "UPDATE movie SET title = ?, price = ?";
        sql += " WHERE book_id = ?";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, movie.getTitle());
        statement.setString(2, movie.getDuration());
        statement.setFloat(3, movie.getPrice());
        statement.setInt(4, movie.getId());
         
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;     
    }
     
    public Book getMovie(int id) throws SQLException {
        Book book = null;
        String sql = "SELECT * FROM movie WHERE book_id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
         
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            float price = resultSet.getFloat("price");
             
            movie = new Movie(id, title, author, price);
        }
         
        resultSet.close();
        statement.close();
         
        return book;
    }
    */

	
}