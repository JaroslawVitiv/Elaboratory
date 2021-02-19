package com.busvancar.cinema;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDAO {

	private Connection jdbcConnection;
     
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
     
    public User findUser(String email, String password) {
    	User user = new User();
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

	public List<User> getTopKeyCustomers(int limit, LocalDate start, LocalDate end) throws SQLException {
		List<User> topKeyCustomers = new ArrayList<>();
		User user = null;
		int coins = 0;
		double revenue;
		 String sqlQuery = "SELECT ticket.time, user.first_name, user.last_name, SUM(ticket.price) AS totalRevenue FROM user INNER JOIN ticket ON user.id = ticket.purchaser_id  WHERE ticket.time BETWEEN ? AND DATE_ADD(? , INTERVAL 1 DAY)  GROUP BY ticket.purchaser_id  ORDER BY SUM(ticket.price) DESC  LIMIT ?";
	        connect();
	         
	        try(PreparedStatement statement = jdbcConnection.prepareStatement(sqlQuery)) {
	        	
	        	
	        	statement.setDate(1, Date.valueOf(start));
	         	statement.setDate(2, Date.valueOf(end));
	         	statement.setInt(3, limit);
	         	
	        	try(ResultSet rs = statement.executeQuery()){
	    			
					while(rs.next()) {
						user = new User();
						user.setFirstName(rs.getString("user.first_name"));
						user.setLastName(rs.getString("user.last_name"));
						coins = rs.getInt("totalRevenue");
						revenue = (double) coins/100;
						user.setRevenue(revenue);
						
						
						topKeyCustomers.add(user);
					}
	        	}
	        	statement.close();
	        }catch (SQLException e) {
				e.printStackTrace();
			}
	    
	        disconnect();
		return topKeyCustomers;
	}
     
  
}