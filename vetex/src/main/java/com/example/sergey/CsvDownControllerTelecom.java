package com.example.sergey;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//сохранение базы заказов в exel

@Controller
public class CsvDownControllerTelecom {

	@GetMapping("/dateBaseToCSV/telecom")
	ResponseEntity<Resource> getFileCsv() throws SQLException, IOException{
		String filename="telecom.csv";
		InputStreamResource file=copyToFile();
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
		        .contentType(MediaType.parseMediaType("text/csv"))
		        .body(file);
	}
	
	public static InputStreamResource copyToFile() throws SQLException, IOException {  
	      
		  String urls = new String("jdbc:postgresql://ec2-52-208-221-89.eu-west-1.compute.amazonaws.com:5432/d1vmia1uqp5a6k");
	      String username = new String("bnyjzqxtkbdodi");
	      String password = new String("8a98dd7cfcc0a26e5653cd0e3aa75d7b770a596483f2be5c257978fbab58b5d6");
	      Connection conn = null;
	      String myQuery="COPY (select ppnumber,workname,unitmeasure,price,comment from telecom order by ppnumber) TO STDOUT WITH (FORMAT CSV, HEADER)";
	      InputStreamResource file;
	      
	      try {  
	    	  conn = DriverManager.getConnection(urls, username, password);
	          CopyManager copyManager = new CopyManager((BaseConnection) conn);
	          ByteArrayOutputStream out = new ByteArrayOutputStream();
	          copyManager.copyOut(myQuery, out);
	          ByteArrayInputStream in=new ByteArrayInputStream(out.toByteArray());
	          file=new InputStreamResource(in);
	      } finally {      
	          if (conn != null) {
	              try {
	                  conn.close();
	              } catch (SQLException e) { e.printStackTrace();}
	          }
	}
	      return file;
	}
	
}

