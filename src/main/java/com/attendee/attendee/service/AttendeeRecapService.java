<<<<<<< HEAD
package com.attendee.attendee.service;

import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.dao.AttendeeRecapDao;
import com.attendee.attendee.model.AttendeeRecap;
import com.attendee.attendee.storage.StorageProperties;

import net.sf.jasperreports.engine.JRException;
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
	
	private StorageProperties properties;
	
	@Transactional
	public List<AttendeeRecap> getAll(Date startDate, Date endDate) {
		List<AttendeeRecap> list = attendeeRecapDao.getAllRecap(startDate, endDate);
		return list;
	}
	
	@Transactional
	public byte[] generateReport(Date startDate, Date endDate) throws JRException {
		// Compile the Jasper report from .jrxml to .japser
		JasperReport jasperReport = JasperCompileManager.compileReport(Paths.get(properties.getLocation()).resolve("att-recap-rpt.jrxml").toString());

		// Get your data source
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(attendeeRecapDao.getAllRecap(startDate, endDate));

		// Add parameters
		Map<String, Object> parameters = new HashMap<>();

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
=======
package com.attendee.attendee.service;

import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.dao.AttendeeRecapDao;
import com.attendee.attendee.model.AttendeeRecap;
import com.attendee.attendee.storage.StorageProperties;

import net.sf.jasperreports.engine.JRException;
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
	
	private StorageProperties properties;
	
	@Transactional
	public List<AttendeeRecap> getAll(Date startDate, Date endDate) {
		List<AttendeeRecap> list = attendeeRecapDao.getAllRecap(startDate, endDate);
		return list;
	}
	
	@Transactional
	public byte[] generateReport(Date startDate, Date endDate) throws JRException {
		// Compile the Jasper report from .jrxml to .japser
		JasperReport jasperReport = JasperCompileManager.compileReport(Paths.get(properties.getLocation()).resolve("att-recap-rpt.jrxml").toString());

		// Get your data source
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(attendeeRecapDao.getAllRecap(startDate, endDate));

		// Add parameters
		Map<String, Object> parameters = new HashMap<>();

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
>>>>>>> 8e5a7197421146f62a06c7eafaa45d8e5be30006
