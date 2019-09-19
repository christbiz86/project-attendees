package com.attendee.attendee.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.dao.AnnualLeaveRecapDao;
import com.attendee.attendee.dao.RequestDao;
import com.attendee.attendee.model.AnnualLeaveRecap;
import com.attendee.attendee.model.Request;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class AnnualLeaveRecapService {
	@Autowired
	private AnnualLeaveRecapDao alrDao;
	
	@Autowired
	private RequestDao reqDao;
	
	@Autowired
	private CloudService cloudService;
	
	@Transactional
	public List<AnnualLeaveRecap> getAll(String company, Date startDate, Date endDate) {
		List<AnnualLeaveRecap> list = alrDao.getAllRecap(company, startDate, endDate);
		return list;
	}
	
	@Transactional
	public List<Request> getByUC(Request request) {
		LocalDate startDate = new java.sql.Date(request.getTglMulai().getTime()).toLocalDate();
		LocalDate endDate = new java.sql.Date(request.getTglAkhir().getTime()).toLocalDate();

		java.util.Date sD = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.util.Date eD = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		List<Request> list = reqDao.findByUserCompanyAndDate(request.getUserCompany().getIdUser().getId(), sD, eD);
		return list;
	}
	
	@Transactional
	public byte[] generateReport(String company, Date startDate, Date endDate) throws Exception {
		// Compile the Jasper report from .jrxml to .jasper
		JasperReport jasperReport = JasperCompileManager.compileReport(cloudService.loadReport("AnnLeaveRecapRpt"));
		
		// Get your data source
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(alrDao.getAllRecap(company, startDate, endDate));
		
		// Add parameters
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("company", company);
		parameters.put("start_date", startDate);
		parameters.put("end_date", endDate);

		// Fill the report
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				jasperReport, 
				parameters,
				jrBeanCollectionDataSource);

		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

		return pdf;
	}
	
	@Transactional
	public byte[] writePOI(String company, Date startDate, Date endDate) throws Exception {
		Workbook workbook = new XSSFWorkbook();
		
		Sheet sheet = workbook.createSheet("Persons");
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);
		
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                4  //last column  (0-based)
        ));
		
		Row title = sheet.createRow(0);
		Row header = sheet.createRow(2);
		Row tableHeader = sheet.createRow(3);
		
		CellStyle titleStyle = workbook.createCellStyle();
		CellStyle headerStyle = workbook.createCellStyle();
		CellStyle tableHeaderStyle = workbook.createCellStyle();
		
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
		
		titleStyle.setFont(fontHeader);
		titleStyle.setWrapText(true);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		headerStyle.setAlignment(HorizontalAlignment.RIGHT);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		tableHeaderStyle.setFont(font);
		tableHeaderStyle.setWrapText(true);
		tableHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
		tableHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		Cell titleCell = title.createCell(0);
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
		tableHeaderCell.setCellValue("Sisa Cuti");
		tableHeaderCell.setCellStyle(tableHeaderStyle);
		
		tableHeaderCell = tableHeader.createCell(4);
		tableHeaderCell.setCellValue("Tahun");
		tableHeaderCell.setCellStyle(tableHeaderStyle);
		
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		
		ArrayList<AnnualLeaveRecap> list = (ArrayList<AnnualLeaveRecap>) getAll(company, startDate, endDate);
		int rowNum = 4;
		
        for (AnnualLeaveRecap field : list) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            AnnualLeaveRecap ann = (AnnualLeaveRecap) field; 
        	for (colNum=0; colNum<5; colNum++) {
        		Cell c1 = row.createCell(colNum++);
        		c1.setCellValue(ann.getNamaUser());
                c1.setCellStyle(style);
                
                Cell c2 = row.createCell(colNum++);
                c2.setCellValue(ann.getUnit()==null ? "-" : ann.getUnit());
                c2.setCellStyle(style);
                
                Cell c3 = row.createCell(colNum++);
                c3.setCellValue(ann.getPosisi()==null ? "-" : ann.getPosisi());
                c3.setCellStyle(style);
                
                Cell c4 = row.createCell(colNum++);
                c4.setCellValue(ann.getSisaCuti());
                c4.setCellStyle(style);
                
                Cell c5 = row.createCell(colNum++);
                c5.setCellValue(ann.getTahun());
                c5.setCellStyle(style);
            }
        }

	    ByteArrayOutputStream bos = new ByteArrayOutputStream();

	    try {
	    	workbook.write(bos);
	    } finally {
	        bos.close();
	        workbook.close();
	    }

	    byte[] bytes = bos.toByteArray();
	    
	    return bytes;
	}
}
