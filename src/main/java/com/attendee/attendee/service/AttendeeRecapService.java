package com.attendee.attendee.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.dao.AttendeeDao;
import com.attendee.attendee.dao.AttendeeRecapDao;
import com.attendee.attendee.model.Attendee;
import com.attendee.attendee.model.AttendeeRecap;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class AttendeeRecapService {
	@Autowired
	private AttendeeRecapDao attendeeRecapDao;
	
	@Autowired
	private AttendeeDao attendeeDao;
	
	@Autowired
	private CloudService cloudService;
	
	@Transactional
	public List<AttendeeRecap> getAll(String company, Date startDate, Date endDate) {
		List<AttendeeRecap> list = attendeeRecapDao.getAllRecap(company, startDate, endDate);
		return list;
	}
	
	@Transactional
	public List<Attendee> getByUSP(Attendee att) {
		List<Attendee> list = attendeeDao.findByUSP(att.getIdUserShiftProject().getId());
		return list;
	}
	
	@Transactional
	public byte[] generateReport(String company, Date startDate, Date endDate) throws Exception {
		// Compile the Jasper report from .jrxml to .japser
		JasperReport jasperReport = JasperCompileManager.compileReport(cloudService.loadReport("AttRecapRpt"));

		// Get your data source
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(attendeeRecapDao.getAllRecap(company, startDate, endDate));
		
		// Add parameters
		Map<String, Object> parameters = new HashMap<>();
		
		parameters.put("company", company);
		parameters.put("startDate", startDate);
		parameters.put("endDate", endDate);

		// Fill the report
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
				jrBeanCollectionDataSource);

		// Export the report to a PDF file
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

		return pdf;
	}
	
// JXL
//	public void writeExcel() {
//
//	    String EXCEL_FILE_LOCATION = "src/main/resources/tmp/JXL.xlsx";
//
//	        //1. Create an Excel file
//	    WritableWorkbook myFirstWbook = null;
//	        
//        try {
//
//            myFirstWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));
//
//            // create an Excel sheet
//            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);
//
//            // add something into the Excel sheet
//            Label label = new Label(0, 0, "Test Count");
//            excelSheet.addCell(label);
//
//            Number number = new Number(0, 1, 1);
//            excelSheet.addCell(number);
//
//            label = new Label(1, 0, "Result");
//            excelSheet.addCell(label);
//
//            label = new Label(1, 1, "Passed");
//            excelSheet.addCell(label);
//
//            number = new Number(0, 2, 2);
//            excelSheet.addCell(number);
//
//            label = new Label(1, 2, "Passed 2");
//            excelSheet.addCell(label);
//
//            myFirstWbook.write();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (WriteException e) {
//            e.printStackTrace();
//        } finally {
//
//            if (myFirstWbook != null) {
//                try {
//                    myFirstWbook.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (WriteException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//	}
	
	
// Apache POI
//	public void writeReport() {
//		String filename = "src/main/resources/tmp/ApachePOI.xlsx";
//		
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
//        Object[][] datatypes = {
//                {null,"Datatype", "Type", "Size(in bytes)"},
//                {null,"int", "Primitive", 2},
//                {null,"float", "Primitive", 4},
//                {null,"double", "Primitive", 8},
//                {null,"char", "Primitive", 1},
//                {null,"String", "Non-Primitive", "No fixed size"}
//        };
//
//        int rowNum = 0;
//        System.out.println("Creating excel");
//
//        for (Object[] datatype : datatypes) {
//            Row row = sheet.createRow(rowNum++);
//            int colNum = 0;
//            for (Object field : datatype) {
//                Cell cell = row.createCell(colNum++);
//                if (field instanceof String) {
//                    cell.setCellValue((String) field);
//                } else if (field instanceof Integer) {
//                    cell.setCellValue((Integer) field);
//                }
//            }
//        }
//
//        try {
//            FileOutputStream outputStream = new FileOutputStream(filename);
//            workbook.write(outputStream);
//            workbook.close();
//            System.out.println("Done");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//	}
	
	public void writePOI(String company, Date startDate, Date endDate) throws Exception {
		Workbook workbook = new XSSFWorkbook();
		
		System.out.println(startDate);
		System.out.println(endDate);
		
		Sheet sheet = workbook.createSheet("Persons");
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);
		
		Row title = sheet.createRow(0);
		Row header = sheet.createRow(2);
		Row tableHeader = sheet.createRow(3);
		
		CellStyle titleStyle = workbook.createCellStyle();
		CellStyle headerStyle = workbook.createCellStyle();
		CellStyle tableHeaderStyle = workbook.createCellStyle();
		
		tableHeaderStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		tableHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		tableHeaderStyle.setBorderTop(BorderStyle.MEDIUM);
		tableHeaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		tableHeaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		tableHeaderStyle.setBorderRight(BorderStyle.MEDIUM);
		 
		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 14);
		font.setBold(true);
		
		XSSFFont fontHeader = ((XSSFWorkbook) workbook).createFont();
		fontHeader.setFontName("Arial");
		fontHeader.setFontHeightInPoints((short) 12);
		
		CreationHelper createHelper = workbook.getCreationHelper();
		CellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd"));
		dateStyle.setFont(fontHeader);
		dateStyle.setAlignment(HorizontalAlignment.RIGHT);
		dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		titleStyle.setFont(font);
		titleStyle.setWrapText(true);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		headerStyle.setFont(fontHeader);
		headerStyle.setAlignment(HorizontalAlignment.RIGHT);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		tableHeaderStyle.setFont(font);
		tableHeaderStyle.setWrapText(true);
		tableHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
		tableHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		Cell titleCell = title.createCell(2);
		titleCell.setCellValue(company);
		titleCell.setCellStyle(titleStyle);
		
		Cell headerCell1 = header.createCell(2);
		headerCell1.setCellValue(startDate);
		headerCell1.setCellStyle(dateStyle);
		
		Cell headerCell2 = header.createCell(3);
		headerCell2.setCellValue("sampai");
		headerCell2.setCellStyle(headerStyle);
		
		Cell headerCell3 = header.createCell(4);
		headerCell3.setCellValue(endDate);
		headerCell3.setCellStyle(dateStyle);
		
		Cell tableHeaderCell = tableHeader.createCell(0);
		tableHeaderCell.setCellValue("Nama");
		tableHeaderCell.setCellStyle(tableHeaderStyle);
		 
		tableHeaderCell = tableHeader.createCell(1);
		tableHeaderCell.setCellValue("Unit");
		tableHeaderCell.setCellStyle(tableHeaderStyle);
		
		tableHeaderCell = tableHeader.createCell(2);
		tableHeaderCell.setCellValue("Posisi");
		tableHeaderCell.setCellStyle(tableHeaderStyle);
		
		tableHeaderCell = tableHeader.createCell(3);
		tableHeaderCell.setCellValue("Jumlah Terlambat");
		tableHeaderCell.setCellStyle(tableHeaderStyle);
		
		tableHeaderCell = tableHeader.createCell(4);
		tableHeaderCell.setCellValue("Jumlah Masuk");
		tableHeaderCell.setCellStyle(tableHeaderStyle);
		
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		
		ArrayList<AttendeeRecap> list = (ArrayList<AttendeeRecap>) getAll(company, startDate, endDate);
		int rowNum = 4;
		
        for (AttendeeRecap field : list) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
        	AttendeeRecap att = (AttendeeRecap) field; 
        	for (colNum=0; colNum<5; colNum++) {
        		Cell c1 = row.createCell(colNum++);
        		c1.setCellValue(att.getName());
                c1.setCellStyle(style);
                
                Cell c2 = row.createCell(colNum++);
                c2.setCellValue(att.getUnit());
                c2.setCellStyle(style);
                
                Cell c3 = row.createCell(colNum++);
                c3.setCellValue(att.getPosisi());
                c3.setCellStyle(style);
                
                Cell c4 = row.createCell(colNum++);
                c4.setCellValue(att.getTerlambat());
                c4.setCellStyle(style);
                
                Cell c5 = row.createCell(colNum++);
                c5.setCellValue(att.getMasuk());
                c5.setCellStyle(style);
            }
        }
		
		File currDir = new File("src/main/resources/tmp");
		String path = currDir.getAbsolutePath();
		System.out.println(path);
		String fileLocation = path + "/ApachePOIFormat.xlsx";
		 
		FileOutputStream outputStream = new FileOutputStream(fileLocation);
		workbook.write(outputStream);
		workbook.close();
		System.out.println("Done");
	}
}
