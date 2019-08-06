package com.attendee.attendee.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_shift_project")
public class UserShiftProject {
	@Id
	@Column(name = "id")
	private UUID id;
	
	@JoinColumn(name = "id_user_company", referencedColumnName = "id")
	@OneToOne(optional = false)
	private UserCompany userCompany;
	
	@JoinColumn(name = "id_shift_project", referencedColumnName = "id")
	@OneToOne(optional = false)
	private ShiftProject shiftProject;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UserCompany getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(UserCompany userCompany) {
		this.userCompany = userCompany;
	}

	public ShiftProject getShiftProject() {
		return shiftProject;
	}

	public void setShiftProject(ShiftProject shiftProject) {
		this.shiftProject = shiftProject;
	}
}
