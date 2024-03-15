package com.email.sender.application.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
public class EmailSenderHelper {
	
	@Autowired
	SheetsQuickstart sheetQuickStart;
	
	public List<Customer> readExcelFile() throws IOException, GeneralSecurityException {
	    Scanner myObj = new Scanner(System.in);
	    System.out.println("Either press 1 for Downloaded Excel Sheet or 2 for Google Sheets");
	    int emailList = myObj.nextInt();
	    List<Customer> custList=new ArrayList<>();
	    if(emailList==1)
	    {
		    Scanner inputPath = new Scanner(System.in);
		    System.out.println("Please enter the path of excel sheet");
		    String path = inputPath.nextLine();
	    	custList=readXLSXFile(path);
		}
	    else if(emailList==2)
	    {
	    	custList=new SheetsQuickstart().getDataFromSheet();
	    }
	    else
	    {
	    	System.out.print("Please enter a valid input");
	    }
		return custList;
	}
	
	   private static List<Customer> readXLSXFile(String file) {
			  List<Customer> listCust = new ArrayList<Customer>();
			  try {
			   XSSFWorkbook work = new XSSFWorkbook(new FileInputStream(file));
			   
			   XSSFSheet sheet = work.getSheet("Sheet1");
			   
			   int numberOfRows = sheet.getPhysicalNumberOfRows();
			   int i = 0;
			   while(i<numberOfRows){
				    XSSFRow row = sheet.getRow(i);

				   if (row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
				        break; // Break the loop if the first cell is null or empty
				    }

			    String custName;
			    String custEmail = null;
			    String company=null;
			    try{
			     custName = row.getCell(0).getStringCellValue();
			    }
			    catch(Exception e){custName = null;}
			    try{
			     company = row.getCell(1).getStringCellValue();
			    }
			    catch(Exception e){custEmail = null;}
			    try{
			     custEmail = row.getCell(2).getStringCellValue();
			    }
			    catch(Exception e){company = null;}
			    Customer cust = new Customer();
			    cust.setEmail(custEmail);
			    cust.setCompany(company);
			    cust.setName(custName);
			    listCust.add(cust);
			     i++;    
			   }
			   work.close();
			  } catch (IOException e) {
			   System.out.println("Exception is Customer fetch data :: "+e.getMessage());
			   e.printStackTrace();
			  }
			  return listCust;
			 
	}
	   
	 

}
