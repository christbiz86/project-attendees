package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.UserShiftProject;

@Repository
public class UserShiftProjectDao extends ParentDao {
	@Transactional
	public void save(UserShiftProject userShiftProject) {
		super.entityManager.merge(userShiftProject);
	}

	@Transactional
	public void delete(UUID id) {
		UserShiftProject userShiftProject = findById(id);
		super.entityManager.remove(userShiftProject);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public UserShiftProject findById(UUID id) {
		List<UserShiftProject> list = super.entityManager
				.createQuery("FROM UserShiftProject WHERE id=:id")
				.setParameter("id", id)
				.getResultList();

		if (list.size() == 0) {
			return new UserShiftProject();
		} else {
			return (UserShiftProject) list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<UserShiftProject> findAll() {
		List<UserShiftProject> list = super.entityManager
				.createQuery("FROM UserShiftProject ")
				.getResultList();

		return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<UserShiftProject> filterUserShift(String worktime) {
		List<UserShiftProject> userShiftList = super.entityManager
				.createQuery("FROM UserShiftProject worktime=:worktime")
				.setParameter("worktime", worktime)
				.getResultList();
		if (userShiftList.size() == 0) {
			return new ArrayList<UserShiftProject>();
		} else {
			return userShiftList;
		}
	}

	public boolean isExist(UUID id) {
		if (findById(id).getId() == null) {
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public UserShiftProject findByBk(UUID userCompany, UUID shiftProject) {
		List<UserShiftProject> list = super.entityManager
				.createNativeQuery("SELECT * "
						+ "FROM user_shift_project "
						+ "WHERE id_user_company=:userCompany AND id_shift_project=:shiftProject",
						UserShiftProject.class)
				.setParameter("userCompany", userCompany)
				.setParameter("shiftProject", shiftProject)
				.getResultList();

		if (list.size() == 0) {
			return new UserShiftProject();
		} else {
			return (UserShiftProject) list.get(0);
		}
	}

	public boolean isBkExist(UUID userCompany, UUID shiftProject) {
		if (findByBk(userCompany, shiftProject).getId() == null) {
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public UserShiftProject findByUser(UUID userCompany) {
		List<UserShiftProject> list = super.entityManager
				.createNativeQuery("SELECT * " 
						+ "FROM user_shift_project " 
						+ "WHERE id_user_company=:userCompany ",
						UserShiftProject.class)
				.setParameter("userCompany", userCompany)
				.getResultList();

		if (list.size() == 0) {
			return new UserShiftProject();
		} else {
			return (UserShiftProject) list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<UserShiftProject> findByFilter(UserShiftProject usp) {
		StringBuilder sb = new StringBuilder("from UserShiftProject WHERE 1=1 ");
		if(usp.getUserCompany() != null) {
			if(usp.getUserCompany().getIdUser() != null) {
				if(usp.getUserCompany().getIdUser().getId() != null) {
					sb.append("AND userCompany.idUser.id ='"+usp.getUserCompany().getIdUser().getId()+"' ");
				}
				if (usp.getUserCompany().getIdUser().getNama() != null) {
					sb.append(" AND userCompany.idUser.nama LIKE '%" + usp.getUserCompany().getIdUser().getNama() + "%' ");
				}
				if (usp.getUserCompany().getIdUser().getAlamat() != null) {
					sb.append(" AND userCompany.idUser.alamat LIKE '%" + usp.getUserCompany().getIdUser().getAlamat() + "%' ");
				}
				if (usp.getUserCompany().getIdUser().getEmail() != null) {
					sb.append(" AND userCompany.idUser.email LIKE '" + usp.getUserCompany().getIdUser().getEmail() + "' ");
				}
				if (usp.getUserCompany().getIdUser().getTglLahir() != null) {
					sb.append(" AND userCompany.idUser.tglLahir LIKE '" + usp.getUserCompany().getIdUser().getTglLahir() + "' ");
				}
				if (usp.getUserCompany().getIdUser().getTelp() != null) {
					sb.append(" AND userCompany.idUser.telp LIKE '" + usp.getUserCompany().getIdUser().getTelp() + "' ");
				}
				if (usp.getUserCompany().getIdUser().getIdStatus() != null) {
					if (usp.getUserCompany().getIdUser().getIdStatus().getStatus() != null) {
						sb.append(" AND userCompany.idUser.idStatus.status LIKE '" + usp.getUserCompany().getIdUser().getIdStatus().getStatus() + "' ");
					}
				}
			}
			if(usp.getUserCompany().getIdCompanyUnitPosisi() != null) {
				if(usp.getUserCompany().getIdCompanyUnitPosisi().getIdCompany() != null) {
					if(usp.getUserCompany().getIdCompanyUnitPosisi().getIdCompany().getId() != null) {
						sb.append("AND userCompany.idCompanyUnitPosisi.idCompany.id = '"+usp.getUserCompany().getIdCompanyUnitPosisi().getIdCompany().getId()+"' ");
					}
				}
				if (usp.getUserCompany().getIdCompanyUnitPosisi().getIdUnit() != null) {
					if (usp.getUserCompany().getIdCompanyUnitPosisi().getIdUnit().getUnit() != null) {
						sb.append(" AND userCompany.idCompanyUnitPosisi.idUnit.unit LIKE '"
								+ usp.getUserCompany().getIdCompanyUnitPosisi().getIdUnit().getUnit() + "' ");
					}
				}
				if (usp.getUserCompany().getIdCompanyUnitPosisi().getIdPosisi() != null) {
					if (usp.getUserCompany().getIdCompanyUnitPosisi().getIdPosisi().getPosisi() != null) {
						sb.append(" AND userCompany.idCompanyUnitPosisi.idPosisi.posisi LIKE '"
								+ usp.getUserCompany().getIdCompanyUnitPosisi().getIdPosisi().getPosisi() + "' ");
					}
				}

			}
			if (usp.getUserCompany().getIdTipeUser().getTipe() != null) {
				if (usp.getUserCompany().getIdTipeUser().getTipe() != null) {
					sb.append(" AND userCompany.idTipeUser.tipe LIKE '" + usp.getUserCompany().getIdTipeUser().getTipe() + "' ");
				}
			}
		}
		
		if(usp.getShiftProject() != null) {
			if(usp.getShiftProject().getShift() != null) {
				if(usp.getShiftProject().getShift().getMasuk() != null) {
					sb.append("AND shiftProject.shift.masuk = '"+usp.getShiftProject().getShift().getMasuk()+"' ");
				}
				if(usp.getShiftProject().getShift().getPulang() != null) {
					sb.append("AND shiftProject.shift.keluar = '"+usp.getShiftProject().getShift().getPulang()+"' ");
				}
				if(usp.getShiftProject().getShift().getStatus().getStatus() != null) {
					sb.append("AND shiftProject.shift.status.status = '"+usp.getShiftProject().getShift().getStatus().getStatus()+"' ");
				}
			}
			if(usp.getShiftProject().getProject() != null) {
				if(usp.getShiftProject().getProject().getNamaProject() != null) {
					sb.append("AND shiftProject.project.namaProject LIKE '"+usp.getShiftProject().getProject().getNamaProject()+"' ");
				}
				if(usp.getShiftProject().getProject().getLokasi() != null) {
					sb.append("AND shiftProject.project.lokasi LIKE '"+usp.getShiftProject().getProject().getLokasi()+"' ");
				}
				if(usp.getShiftProject().getProject().getStatus().getStatus() != null) {
					sb.append("AND shiftProject.project.status.status = '"+usp.getShiftProject().getProject().getStatus().getStatus()+"' ");
				}
			}
		}
		
		if(usp.getWorktime() != null) {
			if(usp.getWorktime().getStatus() != null) {
				sb.append("AND worktime.status LIKE '"+usp.getWorktime().getStatus()+"' ");
			}
		}
		
		List<UserShiftProject> list = super.entityManager.createQuery(sb.toString()).getResultList();
		
		if(list.size() == 0) {
			return new ArrayList<UserShiftProject>();
		} else {
			return list;
		}
	}
}
