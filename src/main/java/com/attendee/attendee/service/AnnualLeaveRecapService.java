package com.attendee.attendee.service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.dao.AnnualLeaveRecapDao;
import com.attendee.attendee.model.AnnualLeaveRecap;
import com.attendee.attendee.storage.StorageFileNotFoundException;

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
	
	public List<AnnualLeaveRecap> getAll(String company, Date startDate, Date endDate) {
		List<AnnualLeaveRecap> list = alrDao.getAllRecap(company, startDate, endDate);
		return list;
	}
	
	@Transactional
	public byte[] generateReport(String company, Date startDate, Date endDate) throws JRException {
		String reportPath = "src\\main\\report";

		// Compile the Jasper report from .jrxml to .japser
		JasperReport jasperReport = JasperCompileManager.compileReport(reportPath + "\\ann-leave-recap-rpt.jrxml");

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
//		JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath + "\\Ann-Leave-Recap-Rpt.pdf");
		byte[] pdf=JasperExportManager.exportReportToPdf(jasperPrint);

		System.out.println("Done");
		
		return pdf;
	}
	
	public Resource getReport() {
		try {
            Path file = load("Ann-Leave-Recap-Rpt.pdf");
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: Ann-Leave-Recap-Rpt.pdf" );

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: Ann-Leave-Recap-Rpt.pdf", e);
        }

	}

    public Path load(String filename) {
    	Path rootLocation=Paths.get("src\\main\\report\\");
        return rootLocation.resolve(filename);
    }
	
}
