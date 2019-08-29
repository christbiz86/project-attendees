package com.attendee.attendee.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "src/main/resources/upload-dir";
    private String reportPath = "src/main/resources/report";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
}
