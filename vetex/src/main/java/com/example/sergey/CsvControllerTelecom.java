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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CsvControllerTelecom {

	@PostMapping("/admin/csvToDateBase/telecom")
	public String uploadFileTelecom(@RequestParam("file") MultipartFile  file,Model model){
		
		try {
			copyToDateBase(file);
	        return "redirect:/priceItems/telecom";
	      } catch (Exception e) {
	        model.addAttribute("note", "Не удалось загрузить ТЦП в БД!");
	        return "noUpload";
	      }
	}
	
	public static void copyToDateBase(MultipartFile file) throws SQLException, IOException {  
	      
		  String urls = new String("jdbc:postgresql://ec2-52-208-221-89.eu-west-1.compute.amazonaws.com:5432/d1vmia1uqp5a6k");
	      String username = new String("bnyjzqxtkbdodi");
	      String password = new String("8a98dd7cfcc0a26e5653cd0e3aa75d7b770a596483f2be5c257978fbab58b5d6");
	      Connection conn = null;
	      String myQuery="COPY telecom (ppnumber,workname,unitmeasure,price,comment) FROM STDIN WITH CSV";
	      
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