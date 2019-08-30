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

import com.attendee.attendee.dao.AnnualLeaveRecapDao;
import com.attendee.attendee.model.AnnualLeaveRecap;
import com.attendee.attendee.storage.StorageProperties;

import net.sf.jasperreports.engine.JRException;
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
	
	private StorageProperties properties;
	
	public List<AnnualLeaveRecap> getAll(String company, Date startDate, Date endDate) {
		List<AnnualLeaveRecap> list = alrDao.getAllRecap(company, startDate, endDate);
		return list;
	}
	
	@Transactional
	public byte[] generateReport(String company, Date startDate, Date endDate) throws JRException {

		// Compile the Jasper report from .jrxml to .japser
		JasperReport jasperReport = JasperCompileManager.compileReport(Paths.get(properties.getLocation()).resolve("ann-leave-recap-rpt.jrxml").toString());

		// Get your data source
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(alrDao.getAllRecap(company, startDate, endDate));

		// Add parameters
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("company", startDate);
		parameters.put("start_date", startDate);
		parameters.put("end_date", endDate);

		// Fill the report
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
				jrBeanCollectionDataSource);

		// Export the report to a PDF file
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

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

import com.attendee.attendee.dao.AnnualLeaveRecapDao;
import com.attendee.attendee.model.AnnualLeaveRecap;
import com.attendee.attendee.storage.StorageProperties;

import net.sf.jasperreports.engine.JRException;
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
	
	private StorageProperties properties;
	
	public List<AnnualLeaveRecap> getAll(String company, Date startDate, Date endDate) {
		List<AnnualLeaveRecap> list = alrDao.getAllRecap(company, startDate, endDate);
		return list;
	}
	
	@Transactional
	public byte[] generateReport(String company, Date startDate, Date endDate) throws JRException {

		// Compile the Jasper report from .jrxml to .japser
		JasperReport jasperReport = JasperCompileManager.compileReport(Paths.get(properties.getLocation()).resolve("ann-leave-recap-rpt.jrxml").toString());

		// Get your data source
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(alrDao.getAllRecap(company, startDate, endDate));

		// Add parameters
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("company", startDate);
		parameters.put("start_date", startDate);
		parameters.put("end_date", endDate);

		// Fill the report
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
				jrBeanCollectionDataSource);

		// Export the report to a PDF file
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

		return pdf;
	}
}
>>>>>>> 8e5a7197421146f62a06c7eafaa45d8e5be30006
