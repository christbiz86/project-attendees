package com.attendee.attendee.excel;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.dao.AnnualLeaveRecapDao;
import com.attendee.attendee.model.AnnualLeaveRecap;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

@Service
public class ExcelService {

	@Autowired
	private AnnualLeaveRecapDao alrDao;
	
	@Transactional
	public File generateReport(String company, Date startDate, Date endDate) {

		List<AnnualLeaveRecap> list=alrDao.getAllRecap(company, startDate, endDate);
		
		WritableWorkbook myFirstWbook = null;
		File file=new File("report.xls");
        try {
		myFirstWbook = Workbook.createWorkbook(file);

        // create an Excel sheet
        WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);

        // add something into the Excel sheet
        Label label = new Label(2, 1, company);
        excelSheet.addCell(label);
        excelSheet.addCell(new Label(0, 2, "Nama"));
        excelSheet.addCell(new Label(1, 2, "Unit"));
        excelSheet.addCell(new Label(2, 2,"Posisi"));
        excelSheet.addCell(new Label(3, 2,"Sisa Cuti"));
        excelSheet.addCell(new Label(4, 2, "Tahun"));
        
        for(int i=3;i<list.size()+3;i++) {
	 
	        excelSheet.addCell(new Label(0, i, list.get(i-3).getNamaUser()));
	        excelSheet.addCell(new Label(1, i, list.get(i-3).getUnit()));
	        excelSheet.addCell(new Label(2, i, list.get(i-3).getPosisi()));
	        excelSheet.addCell(new Number(3, i, list.get(i-3).getSisaCuti()));
	        excelSheet.addCell(new Number(4, i, list.get(i-3).getTahun()));
        }
        
        myFirstWbook.write();
        
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }

        }
		return file;
    
	}
}
