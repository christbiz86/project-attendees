package com.attendee.attendee.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.dao.AttendeeRecapDao;
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
	private CloudService cloudService;
	
	@Transactional
	public List<AttendeeRecap> getAll(String company, Date startDate, Date endDate) {
		List<AttendeeRecap> list = attendeeRecapDao.getAllRecap(company, startDate, endDate);
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

//		return ("Report successfully generated @path= " + reportPath);
		return pdf;
	}
}
