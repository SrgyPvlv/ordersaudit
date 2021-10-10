package com.example.sergey;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CsvController {

	@PostMapping("/admin/csvToDateBase")
	public String uploadFile(@RequestParam("file") MultipartFile  file){
		
		try {
			copyToDateBase(file);
	        return "okUpload";
	      } catch (Exception e) {
	        return "noUpload";
	      }
	}
	
	public static void copyToDateBase(MultipartFile file) throws SQLException, IOException {  
	      
		  String urls = new String("jdbc:postgresql://localhost:5432/sergey");
	      String username = new String("postgres");
	      String password = new String("180578lord");
	      Connection conn = null;
	      String myQuery="COPY vetex (ppnumber,workname,unitmeasure,price,comment) FROM STDIN WITH CSV";
	      
	      try {  
	    	  conn = DriverManager.getConnection(urls, username, password);
	          CopyManager copyManager = new CopyManager((BaseConnection) conn);
	          InputStream inputStream =  new BufferedInputStream(file.getInputStream());
	          copyManager.copyIn(myQuery,inputStream);  
	          
	      }catch (Exception e) {
	          }
	      finally {      
	          if (conn != null) {
	              try {
	                  conn.close();
	              } catch (SQLException e) { e.printStackTrace();}
	          }
	}
	}
}