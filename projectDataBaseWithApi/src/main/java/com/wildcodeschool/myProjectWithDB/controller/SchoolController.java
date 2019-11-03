package com.wildcodeschool.myProjectWithDB.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
@ResponseBody
public class SchoolController {
	private final static String DB_URL = "jdbc:mysql://localhost:3306/wild_db_quest?serverTimezone=GMT";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "Your pass of sql";
   private Connection connection;
   private PreparedStatement statement;
   private  ResultSet resulSet;
     
    @GetMapping("/api/schools")
    
  public List<School> getSchool(@RequestParam (required = false ,defaultValue = "%")String country) {
    	List<School> school;
    	
    	
    	try {
			connection = DriverManager.getConnection( DB_URL, DB_USER, DB_PASSWORD);
			statement = connection.prepareStatement( "SELECT * FROM school WHERE country LIKE ?");
			
			  statement.setString(1, country);	
			
			
	    	resulSet = statement.executeQuery();
		
    	
	    	school= new ArrayList<School>();

			while(resulSet.next()){
			    int id = resulSet.getInt("id");
			    String name = resulSet.getString("name");
			    int capacity =resulSet.getInt("capacity");
			    String countrie =resulSet.getString("country");
			    school.add(new School(id,name,capacity,countrie));
			}
    	}
    	catch (SQLException e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "", e
            );
        }

    	return school;

    	
    }
    

}

