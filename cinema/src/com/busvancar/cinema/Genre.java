package com.busvancar.cinema;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Genre {
	
	public static String[] genres_en_GB = {"All genres","comedy", "action", "drama", "historic", "cartoon","criminal"};
	public static String[] genres_uk_UA = {"Всі жанри","комедія", "пригодницький", "драма", "історичний", "мультфільм", "кримінал" };
	
	
	public static List<String> getGenreList(String[] genres)  {
		return Arrays.asList(genres);
	}
	
	public static String getGenreOptions(String[] genres)  {
		StringBuilder options = new StringBuilder();
		for(int g=0; g < genres.length; g++) {
			options.append("<li><a class=\"dropdown-item\" href=\"/cinema/?genre="+g+"\">"+genres[g]+"</a></li><li><hr/></li>");
		}
		
		return options.toString();
	}

	
	 public static String getGenreSelectOptions(String[] genres) {
		 StringBuilder genreOptions = new StringBuilder();

		 	for(int g=0; g < genres.length; g++) {
		 		genreOptions.append("<option value=\"");
		 		genreOptions.append(g);
		 		genreOptions.append("\">");
		 		genreOptions.append(genres[g]);
		 		genreOptions.append("</option>");
		 	}
	   return genreOptions.toString();
	  }

}
