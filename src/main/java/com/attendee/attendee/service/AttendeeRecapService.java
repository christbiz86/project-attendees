package com.attendee.attendee.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.apache.poi.ss.usermodel.HorizontalAlignment;
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

import com.attendee.attendee.dao.AttendeeDao;
import com.attendee.attendee.dao.AttendeeRecapDao;
import com.attendee.attendee.dao.AttendeeRecapDetailDao;
import com.attendee.attendee.model.Attendee;
import com.attendee.attendee.model.AttendeeRecap;
import com.attendee.attendee.model.AttendeeRecapDetail;

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
	private AttendeeRecapDetailDao attendeeRecapDtlDao;
	
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
	public List<AttendeeRecapDetail> getAllDetail(String company, Date startDate, Date endDate) {
		List<AttendeeRecapDetail> list = attendeeRecapDtlDao.getAll(company, startDate, endDate);
		return list;
	}
	
	@Transactional
	public List<Attendee> getByUSP(Attendee att) {
		LocalDate startDate = new java.sql.Date(att.getMasuk().getTime()).toLocalDate();
		LocalDate endDate = new java.sql.Date(att.getPulang().getTime()).toLocalDate();
		LocalDate endD = endDate.plusDays(1);
		java.util.Date sD = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.util.Date eD = Date.from(endD.atStartOfDay(ZoneId.systemDefault()).toInstant());

		List<Attendee> list = attendeeDao.findByUSP(att.getIdUserShiftProject().getUserCompany().getIdUser().getId(), sD, eD);
		return list;
	}
	
	@Transactional
	public byte[] generateReportPdf(String company, Date startDate, Date endDate) throws Exception {
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
	
	@Transactional
	public byte[] generateReportDtlPdf(String company, Date startDate, Date endDate) throws Exception {
		// Compile the Jasper report from .jrxml to .japser
		JasperReport jasperReport = JasperCompileManager.compileReport(cloudService.loadReport("AttRecapDtlRpt"));

		// Get your data source
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(attendeeRecapDtlDao.getAll(company, startDate, endDate));
		
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
	
	public byte[] generateReportXls(String company, Date startDate, Date endDate) throws Exception {
		Workbook workbook = new XSSFWorkbook();
		
		Sheet sheet = workbook.createSheet("AttRecap");
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		
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
		
		titleStyle.setFont(font);
		titleStyle.setWrapText(true);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		headerStyle.setAlignment(HorizontalAlignment.RIGHT);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		tableHeaderStyle.setFont(fontHeader);
		tableHeaderStyle.setWrapText(true);
		tableHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
		tableHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		
		Cell titleCell = title.createCell(0);
		titleCell.setCellValue(company);
		titleCell.setCellStyle(titleStyle);
		
		Cell headerCell1 = header.createCell(3);
		headerCell1.setCellValue(startDate);
		headerCell1.setCellStyle(dateStyle);
		
		Cell headerCell2 = header.createCell(4);
		headerCell2.setCellValue("sampai");
		headerCell2.setCellStyle(headerStyle);
		
		Cell headerCell3 = header.createCell(5);
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
		tableHeaderCell.setCellValue("Hari Kerja");
		tableHeaderCell.setCellStyle(tableHeaderStyle);
		
		tableHeaderCell = tableHeader.createCell(4);
		tableHeaderCell.setCellValue("Jumlah Masuk");
		tableHeaderCell.setCellStyle(tableHeaderStyle);
		
		tableHeaderCell = tableHeader.createCell(5);
		tableHeaderCell.setCellValue("Jumlah Terlambat");
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
                c2.setCellValue(att.getUnit()==null ? "-" : att.getUnit());
                c2.setCellStyle(style);
                
                Cell c3 = row.createCell(colNum++);
                c3.setCellValue(att.getPosisi()==null ? "-" : att.getPosisi());
                c3.setCellStyle(style);
                
                Cell c4 = row.createCell(colNum++);
                c4.setCellValue(att.getHariKerja());
                c4.setCellStyle(style);
                
                Cell c5 = row.createCell(colNum++);
                c5.setCellValue(att.getMasuk());
                c5.setCellStyle(style);
                
                Cell c6 = row.createCell(colNum++);
                c6.setCellValue(att.getTerlambat());
                c6.setCellStyle(style);
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
	
	public byte[] generateReportDtlXls(String company, Date startDate, Date endDate) throws Exception {
		Workbook workbook = new XSSFWorkbook();
		
		Sheet sheet = workbook.createSheet("DetailAttRecap");
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
		
		titleStyle.setFont(font);
		titleStyle.setWrapText(true);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		headerStyle.setAlignment(HorizontalAlignment.RIGHT);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		tableHeaderStyle.setFont(fontHeader);
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
		tableHeaderCell.setCellValue("Kode User");
		tableHeaderCell.setCellStyle(tableHeaderStyle);
		 
		tableHeaderCell = tableHeader.createCell(1);
		tableHeaderCell.setCellValue("Nama User");
		tableHeaderCell.setCellStyle(tableHeaderStyle);
		
		tableHeaderCell = tableHeader.createCell(2);
		tableHeaderCell.setCellValue("Tanggal");
		tableHeaderCell.setCellStyle(tableHeaderStyle);
		
		tableHeaderCell = tableHeader.createCell(3);
		tableHeaderCell.setCellValue("Clock In");
		tableHeaderCell.setCellStyle(tableHeaderStyle);
		
		tableHeaderCell = tableHeader.createCell(4);
		tableHeaderCell.setCellValue("Clock Out");
		tableHeaderCell.setCellStyle(tableHeaderStyle);
		
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		
		CellStyle dStyle = workbook.createCellStyle();
		dStyle.setWrapText(true);
		dStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd"));
		dStyle.setBorderTop(BorderStyle.THIN);
		dStyle.setBorderBottom(BorderStyle.THIN);
		dStyle.setBorderLeft(BorderStyle.THIN);
		dStyle.setBorderRight(BorderStyle.THIN);
		
		CellStyle tStyle = workbook.createCellStyle();
		tStyle.setWrapText(true);
		tStyle.setDataFormat(createHelper.createDataFormat().getFormat("HH:MM:ss"));
		tStyle.setBorderTop(BorderStyle.THIN);
		tStyle.setBorderBottom(BorderStyle.THIN);
		tStyle.setBorderLeft(BorderStyle.THIN);
		tStyle.setBorderRight(BorderStyle.THIN);
		
		ArrayList<AttendeeRecapDetail> list = (ArrayList<AttendeeRecapDetail>) attendeeRecapDtlDao.getAll(company, startDate, endDate);
		int rowNum = 4;
		
        for (AttendeeRecapDetail field : list) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            AttendeeRecapDetail att = (AttendeeRecapDetail) field; 
        	for (colNum=0; colNum<5; colNum++) {
        		Cell c1 = row.createCell(colNum++);
        		c1.setCellValue(att.getKode());
                c1.setCellStyle(style);
                
                Cell c2 = row.createCell(colNum++);
                c2.setCellValue(att.getName());
                c2.setCellStyle(style);
                
                Cell c3 = row.createCell(colNum++);
                c3.setCellValue(att.getTanggal());
                c3.setCellStyle(dStyle);
                
                Cell c4 = row.createCell(colNum++);
                c4.setCellValue(att.getClockIn());
                c4.setCellStyle(tStyle);
                
                Cell c5 = row.createCell(colNum++);
                c5.setCellValue(att.getClockOut());
                c5.setCellStyle(tStyle);
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
