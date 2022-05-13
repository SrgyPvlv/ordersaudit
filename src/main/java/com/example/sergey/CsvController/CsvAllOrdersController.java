package com.example.sergey.CsvController;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.sergey.MyDbConnection;

@Controller
public class CsvAllOrdersController { //скачивает из БД все заказы в файл .csv / загружает в БД все заказы из файла .csv

	@GetMapping("/admin/allOrdersAdmin") // переход на форму скачивания/загрузки всех заказов
	public String allOrdersAdmin() {
		
	   return "allOrdersAdmin";
	}
	
	@GetMapping("/admin/allOrdersToCSV") //скачивание всех заказов (всех подрядчиков) в файл .csv
	ResponseEntity<Resource> getFileCsv() throws SQLException, IOException{
		String filename="allorders.csv";
		InputStreamResource file=copyToFile();
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
		        .contentType(MediaType.parseMediaType("text/csv"))
		        .body(file);
	}
	
	public static InputStreamResource copyToFile() throws SQLException, IOException {  
	      
		  String urls = MyDbConnection.urls;
	      String username = MyDbConnection.username;
	      String password = MyDbConnection.password;
	      Connection conn = null;
	      String myQuery="COPY (select * from orderlist order by ordernumber) TO STDOUT WITH (FORMAT CSV, HEADER)";
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
	      return file;}
	
	@PostMapping("/admin/csvAllOrdersToDateBase") //загрузка всех заказов из файла .csv в БД
	public String uploadFile(@RequestParam("file") MultipartFile  file,Model model){
		
		try {
			copyToDateBase(file);
			String note="Успешно! Все заказы сохранены в базе данных!";
		    model.addAttribute("note", note);
	        return "noLoad";
	      } catch (Exception e) {
	        model.addAttribute("note", "Не удалось загрузить список БС в БД!");
	        return "noLoad";
	      }
	}
	
	public static void copyToDateBase(MultipartFile file) throws SQLException, IOException {  
	      
		  String urls = MyDbConnection.urls;
	      String username = MyDbConnection.username;
	      String password = MyDbConnection.password;
	      Connection conn = null;
	      String myQuery="COPY orderlist FROM STDIN WITH CSV";
	      
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
