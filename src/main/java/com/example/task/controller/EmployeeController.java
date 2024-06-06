package com.example.task.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.task.model.Employee;
import com.example.task.service.EmployeeService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/")
	public String test() {
		return "API is up!";
	}
	
	@PostMapping("/save")	
	public ResponseEntity<?> save(@RequestBody Employee employee) {
		String response = employeeService.save(employee);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@GetMapping("/get_all_employees")
	public ResponseEntity<?> getAll() {
		List<Employee> list = null;
		list = employeeService.getEmployees();
		
		
		String tableContent = "";
		String table_body = "";
		
		for(int i=0;i<list.size();i++)
		{
			String personal_details = "<tr>"
					+ "            \"    <th>Employee - "+(i+1)+"</th>"
					+ "            \"  </tr>" 
					+ "            \"  <tr>" 
					+ "            \"    <th>First Name</th>" +"\r"
					+ "            \"    <th>Last Name</th>" +"\r"
					+ "            \"    <th>Gender</th>" +"\r"
					+ "            \"    <th>DOB</th>" +"\r"
					+ "            \"    <th>Salary</th>" +"\r"
					+ "            \"  </tr>" +"\r"
					+ "            \"  <tr>" +"\r"
					+ "            \"    <td>"+list.get(i).getFirstname()+"</td>\" +\r"
					+ "            \"    <td>"+list.get(i).getLastname()+"</td>\" +\r"
					+ "            \"    <td>"+list.get(i).getGender()+"</td>\" +\r"
					+ "            \"    <td>"+list.get(i).getDob()+"</td>\" +\r"
					+ "            \"    <td>"+list.get(i).getSalary()+"</td>\" +\r"
					+ "            \"  </tr>\" +\r";
			
			String experiance_details = "";
			
			for(int j=0;j<list.get(i).getExperianceDetails().size();j++) {
				experiance_details = "            \"  <tr>\" +\r"
						+ "            \"    <th>Experiance Details</th>\\" +"\r"
						+ "            \"  </tr>\" +\r"
						+ "            \"  <tr>\" +\r"
						+ "            \"    <th>Organization Name</th>\" +\r"
						+ "            \"    <th>From Date</th>\" +\r"
						+ "            \"    <th>To Date</th>\" +\r"
						+ "            \"  </tr>\" +\r"
						+ "            \"  <tr>\\" +"\r"
						+ "            \"    <td>"+list.get(i).getExperianceDetails().get(j).getOrganisation()+"</td>\\" +"\r"
						+ "            \"    <td>"+list.get(i).getExperianceDetails().get(j).getFromDate()+"</td>\\" +"\r"
						+ "            \"    <td>"+list.get(i).getExperianceDetails().get(j).getToDate()+"</td>\\" +"\r"
						+ "            \"  </tr>\\" +"\r";
			}
			
			table_body = personal_details+experiance_details;
			
			personal_details="";
			experiance_details="";
			
		}
		
		tableContent = "<table>"+table_body+"</table>";
		try {
			String dirPath = "C:\\Users\\Public\\Downloads";
			//Setting destination 
	        FileOutputStream fileOutputStream = new FileOutputStream(new File(dirPath + "/sample.pdf"));
	        
	        PdfWriter pdfWriter = new PdfWriter(fileOutputStream);
	        ConverterProperties converterProperties = new ConverterProperties();
	        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

	        //For setting the PAGE SIZE
	        pdfDocument.setDefaultPageSize(new PageSize(PageSize.A3));
	        
	        Document document = HtmlConverter.convertToDocument(tableContent, pdfDocument, converterProperties);
	        document.close();
		} catch(Exception e) {
			e.printStackTrace();
			
		}
		
		
		return new ResponseEntity<List<Employee>>(list,HttpStatus.OK);
	}
	
	  
	
}
