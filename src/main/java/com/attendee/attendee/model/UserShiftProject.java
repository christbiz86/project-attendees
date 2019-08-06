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
	
	private ShiftProject shiftProject;
}
