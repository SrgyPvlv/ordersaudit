package com.example.sergey.CsvController;

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

import com.example.sergey.MyDbConnection;

@Controller
public class CsvControllerVolot {

	@PostMapping("/admin/csvToDateBase/volot")
	public String uploadFileVolot(@RequestParam("file") MultipartFile  file,Model model){
		
		try {
			copyToDateBase(file);
	        return "redirect:/priceItems/volot";
	      } catch (Exception e) {
	        model.addAttribute("note", "Не удалось загрузить ТЦП в БД!");
	        return "noUpload";
	      }
	}
	
	public static void copyToDateBase(MultipartFile file) throws SQLException, IOException {  
	      
		  String urls = MyDbConnection.urls;
	      String username = MyDbConnection.username;
	      String password = MyDbConnection.password;
	      Connection conn = null;
	      String myQuery="COPY volot (ppnumber,workname,unitmeasure,price,comment) FROM STDIN WITH CSV";
	      
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