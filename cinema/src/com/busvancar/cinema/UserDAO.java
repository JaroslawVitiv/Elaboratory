package com.busvancar.cinema;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDAO {

	private Connection jdbcConnection;
     
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
     
    public User findUser(String email, String password) {
    	User user = User.createUser();
    	 String sqlQuery = "SELECT * FROM user WHERE email = ? AND password = ?";
    	try {
			connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	 try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){
         		
    		 	statement.setString(1, email);
    	        statement.setString(2, password);
    	        
    	        try(ResultSet rs = statement.executeQuery()){
	         	   	if(rs.next()) {
	         	   		user.setId(rs.getInt("id"));
	         	   		user.setFirstName(rs.getString("first_name"));
	         	   		user.setLastName(rs.getString("last_name"));
	         	   		user.setEmail(email);
	         	   		user.setAdmin(rs.getInt("admin"));
	         	   		user.setPassword(password);
	         	   	}
    	        }
         	    statement.close();
         	    
         }catch (SQLException e) {
 			e.printStackTrace();
 			
 	     }
    	return user;
	}

    public String getMd5(String input) 
    { 
        try { 
  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }

	public boolean isValid(String email) {
		    Matcher matcher = Pattern.compile("[\\w]+@[\\w]+\\.[a-zA-Z]{2,3}", Pattern.CASE_INSENSITIVE).matcher(email);

		    return matcher.find();	
	}

	public boolean isInserted(String email) {
		String sqlQuery = "SELECT * FROM user WHERE email = ? ";
   	 	try {
			connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
   	 	
   	 	try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){
         	statement.setString(1, email);
   	        try(ResultSet rs = statement.executeQuery()){
	         	   return rs.next();
   	        }
        }catch (SQLException e) {
			e.printStackTrace();
		}
   	
		return false;
	}

	
    
   
    public boolean insertUser(User user) throws SQLException {
        String sql = "INSERT INTO user (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
       
		
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());


        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }
    

	public boolean updatePassword(String emailAddress, String newPsw) {
		 String sqlQuery = "UPDATE user SET password = ? WHERE email = ? ";
	         
	        try {
					connect();
		
			        PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery);
			        statement.setString(1, getMd5(newPsw));
			        statement.setString(2, emailAddress);
			        boolean rowUpdated = statement.executeUpdate() > 0;
			        statement.close();
			        disconnect();
			        return rowUpdated;
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    		}
	        return false;
	}
     
    /* public List<Movie> listAllMovies() throws SQLException {
        List<Movie> listMovies = new ArrayList<>();
        Movie movie = null;
        String sqlQuery = "SELECT movie.id, movie.title, movie.description_en, movie.description_uk, "
				+ " movie.duration, genre.title, movie.poster, movie.default_price FROM movie "
				+ "INNER JOIN genre ON movie.genre = genre.genre_id ;";
         
        connect();
         
        try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)){
        	try(ResultSet rs = statement.executeQuery();){
    			
				while(rs.next()) {
					movie = new Movie();
					movie.setId(rs.getInt("movie.id"));
					movie.setTitle(rs.getString("movie.title"));
					movie.setDescriptionEn(rs.getString("movie.description_en"));
					movie.setDescriptionUa(rs.getString("movie.description_uk"));
					movie.setDuration(rs.getInt("movie.duration"));
					movie.setGenre(rs.getString("genre.title"));
					movie.setPoster(rs.getString("movie.poster"));
					movie.setPrice(rs.getInt("movie.default_price"));
					
					listMovies.add(movie);
				}
			}
        	statement.close();
        }catch (SQLException e) {
			e.printStackTrace();
		}
    
        disconnect();
         
        return listMovies;
    }
     
    public boolean deleteMovie(Book book) throws SQLException {
        String sql = "DELETE FROM book where book_id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, book.getId());
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;     
    }
     
    public boolean updateBook(Book book) throws SQLException {
        String sql = "UPDATE book SET title = ?, author = ?, price = ?";
        sql += " WHERE book_id = ?";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setFloat(3, book.getPrice());
        statement.setInt(4, book.getId());
         
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;     
    }
     
    public Book getBook(int id) throws SQLException {
        Book book = null;
        String sql = "SELECT * FROM book WHERE book_id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
         
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            float price = resultSet.getFloat("price");
             
            book = new Book(id, title, author, price);
        }
         
        resultSet.close();
        statement.close();
         
        return book;
    }
    */
}