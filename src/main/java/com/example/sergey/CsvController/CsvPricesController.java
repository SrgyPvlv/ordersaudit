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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sergey.MyDbConnection;


@Controller
public class CsvPricesController {

	@PostMapping("/admin/csvToDateBase") //копирование(сохранение) тцп из файла .csv в базу данных
	public String uploadFile(@RequestParam("file") MultipartFile  file,@RequestParam(name="contractor") String contractor,
			@RequestParam(name="contractname") String contractname,Model model,RedirectAttributes redirectAttr){
		
		try {
			copyToDateBase(file);
			
			redirectAttr.addAttribute("contractor", contractor);
			redirectAttr.addAttribute("contractname", contractname);
						
	        return "redirect:/admin/priceItems";
	      } catch (Exception e) {
	    	  
	        model.addAttribute("note", "Не удалось загрузить ТЦП в БД!");
	        
	        return "noLoad";
	      }
	}
	
	public static void copyToDateBase(MultipartFile file) throws SQLException, IOException {  
	      
		  String urls = MyDbConnection.urls;
	      String username = MyDbConnection.username;
	      String password = MyDbConnection.password;
	      Connection conn = null;
	      String myQuery="COPY prices (appendnumber,tablenumber,ppnumber,workname,unitmeasure,price,comment,contractor,contractname) FROM STDIN WITH CSV";
	      
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
	
	@GetMapping("/dateBaseToCSV") //сохранение тцп данного подрядчика из базы данных в файл .csv
	ResponseEntity<Resource> getFileCsv(@RequestParam(name="contractor") String contractor) throws SQLException, IOException{
		String filename="prices_"+contractor+".csv";
		InputStreamResource file=copyToFile(contractor);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
		        .contentType(MediaType.parseMediaType("text/csv"))
		        .body(file);
	}
	
	public static InputStreamResource copyToFile(String contractor) throws SQLException, IOException {  
	      
		  String urls = MyDbConnection.urls;
	      String username = MyDbConnection.username;
	      String password = MyDbConnection.password;
	      Connection conn = null;
	      String myQuery="COPY (select appendnumber,tablenumber,ppnumber,workname,unitmeasure,price,comment,contractor,contractname from prices where contractor like '"+contractor+
	     "') TO STDOUT WITH (FORMAT CSV, HEADER)";
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