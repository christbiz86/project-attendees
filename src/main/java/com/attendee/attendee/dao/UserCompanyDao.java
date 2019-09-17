package com.attendee.attendee.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.UserCompany;

@Repository
public class UserCompanyDao extends ParentDao {
	
	public void save(UserCompany userCompany) {
		super.entityManager.merge(userCompany);
	}

	public void delete(UUID id) {
		UserCompany userCompany = findById(id);
		super.entityManager.remove(userCompany);
	}

	@SuppressWarnings("unchecked")
	public UserCompany findById(UUID id) {

		List<UserCompany> list = super.entityManager.createQuery("from UserCompany where id=:id").setParameter("id", id)
				.getResultList();

		if (list.size() == 0) {
			return new UserCompany();
		} else {
			return (UserCompany) list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserCompany> findAll() {
		List<UserCompany> list = super.entityManager.createQuery("from UserCompany ").getResultList();
		return list;
	}

	public boolean isExist(UUID id) {
		if (findById(id).getId() == null) {
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
//	@Transactional
	public UserCompany findByBk(UUID idUser) {
		List<UserCompany> list = super.entityManager.createQuery("from UserCompany where idUser.id=:idUser")
				.setParameter("idUser", idUser).getResultList();

		if (list.size() == 0) {
			return new UserCompany();
		} else {
			return (UserCompany) list.get(0);
		}
	}

	public boolean isBkExist(UUID idUser) {
		if (findByBk(idUser).getId() == null) {
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserCompany> findByFilter(UserCompany userCompany) {
		StringBuilder sb = new StringBuilder("from UserCompany where 1=1 ");
		if (userCompany.getIdUser() != null) {
			if (userCompany.getIdUser().getNama() != null) {
				sb.append(" AND idUser.nama LIKE '%" + userCompany.getIdUser().getNama() + "%' ");
			}
			if (userCompany.getIdUser().getAlamat() != null) {
				sb.append(" AND idUser.alamat LIKE '%" + userCompany.getIdUser().getAlamat() + "%' ");
			}
			if (userCompany.getIdUser().getEmail() != null) {
				sb.append(" AND idUser.email LIKE '" + userCompany.getIdUser().getEmail() + "' ");
			}
			if (userCompany.getIdUser().getTglLahir() != null) {
				sb.append(" AND idUser.tglLahir LIKE '" + userCompany.getIdUser().getTglLahir() + "' ");
			}
			if (userCompany.getIdUser().getTelp() != null) {
				sb.append(" AND idUser.telp LIKE '" + userCompany.getIdUser().getTelp() + "' ");
			}
			if (userCompany.getIdUser().getIdStatus().getStatus() != null) {
				sb.append(" AND idUser.idStatus.status LIKE '" + userCompany.getIdUser().getIdStatus().getStatus() + "' ");
			}
		}
		if (userCompany.getIdTipeUser().getTipe() != null) {
			if (userCompany.getIdTipeUser().getTipe() != null) {
				sb.append(" AND idTipeUser.tipe LIKE '" + userCompany.getIdTipeUser().getTipe() + "' ");
			}
		}
		if (userCompany.getIdCompanyUnitPosisi() != null) {
			if (userCompany.getIdCompanyUnitPosisi().getIdUnit() != null) {
				if (userCompany.getIdCompanyUnitPosisi().getIdUnit().getUnit() != null) {
					sb.append(" AND idCompanyUnitPosisi.idUnit.unit LIKE '"
							+ userCompany.getIdCompanyUnitPosisi().getIdUnit().getUnit() + "' ");
				}
			}
			if (userCompany.getIdCompanyUnitPosisi().getIdPosisi() != null) {
				if (userCompany.getIdCompanyUnitPosisi().getIdPosisi().getPosisi() != null) {
					sb.append(" AND idCompanyUnitPosisi.idPosisi.posisi LIKE '"
							+ userCompany.getIdCompanyUnitPosisi().getIdPosisi().getPosisi() + "' ");
				}
			}
			if (userCompany.getIdCompanyUnitPosisi().getIdCompany() != null) {
				if (userCompany.getIdCompanyUnitPosisi().getIdCompany().getId() != null) {
					sb.append(" AND idCompanyUnitPosisi.idCompany.id = '"
							+ userCompany.getIdCompanyUnitPosisi().getIdCompany().getId() + "' ");
				}
			}
		}
		
		List<UserCompany> list = super.entityManager.createQuery(sb.toString()).getResultList();

		if (list.size() == 0) {
			return new ArrayList<UserCompany>();
		} else {
			return list;
		}
	}

	@SuppressWarnings("unchecked")
	public UserCompany findByUsername(String username) {
		List<UserCompany> list = super.entityManager.createQuery("from UserCompany where idUser.username=:username")
				.setParameter("username", username).getResultList();

		if (list.size() == 0) {
			return new UserCompany();
		} else {
			return (UserCompany) list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
//	@Transactional
	public UserCompany findByEmail(String email) {
		List<UserCompany> list = super.entityManager.createQuery("from UserCompany where idUser.email=:email")
				.setParameter("email", email).getResultList();

		if (list.size() == 0) {
			return new UserCompany();
		} else {
			return (UserCompany) list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<UserCompany> findByTipe(String tipe) {
		List<UserCompany> list = super.entityManager.createQuery("from UserCompany where idTipeUser.tipe not like :tipe")
				.setParameter("tipe", tipe).getResultList();

		if (list.size() == 0) {
			return new ArrayList<UserCompany>();
		} else {
			return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Integer countEmployee(UserCompany userCompany) {
		StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM UserCompany where 1=1 ");
		if (userCompany.getIdUser() != null) {
			if (userCompany.getIdUser().getNama() != null) {
				sb.append(" AND idUser.nama LIKE '%" + userCompany.getIdUser().getNama() + "%' ");
			}
			if (userCompany.getIdUser().getAlamat() != null) {
				sb.append(" AND idUser.alamat LIKE '%" + userCompany.getIdUser().getAlamat() + "%' ");
			}
			if (userCompany.getIdUser().getEmail() != null) {
				sb.append(" AND idUser.email LIKE '" + userCompany.getIdUser().getEmail() + "' ");
			}
			if (userCompany.getIdUser().getTglLahir() != null) {
				sb.append(" AND idUser.tglLahir LIKE '" + userCompany.getIdUser().getTglLahir() + "' ");
			}
			if (userCompany.getIdUser().getTelp() != null) {
				sb.append(" AND idUser.telp LIKE '" + userCompany.getIdUser().getTelp() + "' ");
			}
			if (userCompany.getIdUser().getIdStatus().getStatus() != null) {
				sb.append(" AND idUser.idStatus.status LIKE '" + userCompany.getIdUser().getIdStatus().getStatus() + "' ");
			}
		}
		if (userCompany.getIdTipeUser().getTipe() != null) {
			if (userCompany.getIdTipeUser().getTipe() != null) {
				sb.append(" AND idTipeUser.tipe LIKE '" + userCompany.getIdTipeUser().getTipe() + "' ");
			}
		}
		if (userCompany.getIdCompanyUnitPosisi() != null) {
			if (userCompany.getIdCompanyUnitPosisi().getIdCompany() != null) {
				if (userCompany.getIdCompanyUnitPosisi().getIdCompany().getId() != null) {
					sb.append(" AND idCompanyUnitPosisi.idCompany.id = '"
							+ userCompany.getIdCompanyUnitPosisi().getIdCompany().getId() + "' ");
				}
			}
			if (userCompany.getIdCompanyUnitPosisi().getIdUnit() != null) {
				if (userCompany.getIdCompanyUnitPosisi().getIdUnit().getUnit() != null) {
					sb.append(" AND idCompanyUnitPosisi.idUnit.unit LIKE '%"
							+ userCompany.getIdCompanyUnitPosisi().getIdUnit().getUnit() + "%' ");
				}
			}
			if (userCompany.getIdCompanyUnitPosisi().getIdPosisi() != null) {
				if (userCompany.getIdCompanyUnitPosisi().getIdPosisi().getPosisi() != null) {
					sb.append(" AND idCompanyUnitPosisi.idPosisi.posisi LIKE '%"
							+ userCompany.getIdCompanyUnitPosisi().getIdPosisi().getPosisi() + "%' ");
				}
			}
		}
		System.out.println(sb.toString());
		Long countEmployee = (Long)super.entityManager.createQuery(sb.toString()).getSingleResult();
		System.out.println(countEmployee);
		Integer count = countEmployee.intValue();
		System.out.println(count);
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserCompany> findLimit(UserCompany userCompany,int page,int jumlah) {
		
		StringBuilder sb = new StringBuilder(" SELECT uc.id,uc.id_user,uc.id_company_unit_posisi,uc.id_tipe_user FROM user_company uc ");
		sb.append(" RIGHT JOIN users u ON uc.id_user=u.id ");
		sb.append(" LEFT JOIN status s ON u.id_status=s.id ");
		sb.append(" LEFT JOIN company_unit_posisi cup ON uc.id_company_unit_posisi=cup.id ");
		sb.append(" LEFT JOIN company c ON cup.id_company=c.id ");
		sb.append(" LEFT JOIN posisi p ON cup.id_posisi=p.id ");
		sb.append(" LEFT JOIN unit un ON cup.id_unit=un.id ");
		sb.append(" LEFT JOIN tipe_user tu ON uc.id_tipe_user=tu.id ");
		sb.append(" WHERE 1=1 ");
		
		if (userCompany.getIdUser() != null) {
			if (userCompany.getIdUser().getNama() != null) {
				sb.append(" AND LOWER(u.nama) LIKE LOWER('%" + userCompany.getIdUser().getNama() + "%') ");
			}
			if (userCompany.getIdUser().getAlamat() != null) {
				sb.append(" AND u.alamat LIKE '%" + userCompany.getIdUser().getAlamat() + "%' ");
			}
			if (userCompany.getIdUser().getEmail() != null) {
				sb.append(" AND u.email LIKE '" + userCompany.getIdUser().getEmail() + "' ");
			}
			if (userCompany.getIdUser().getTglLahir() != null) {
				sb.append(" AND u.tglLahir LIKE '" + userCompany.getIdUser().getTglLahir() + "' ");
			}
			if (userCompany.getIdUser().getTelp() != null) {
				sb.append(" AND u.telp LIKE '" + userCompany.getIdUser().getTelp() + "' ");
			}
			if (userCompany.getIdUser().getIdStatus().getStatus() != null) {
				sb.append(" AND s.status LIKE '" + userCompany.getIdUser().getIdStatus().getStatus() + "' ");
			}
		}
		if (userCompany.getIdTipeUser().getTipe() != null) {
			if (userCompany.getIdTipeUser().getTipe() != null) {
				sb.append(" AND tu.tipe LIKE '" + userCompany.getIdTipeUser().getTipe() + "' ");
			}
		}
		if (userCompany.getIdCompanyUnitPosisi() != null) {
			if (userCompany.getIdCompanyUnitPosisi().getIdUnit() != null) {
				if (userCompany.getIdCompanyUnitPosisi().getIdUnit().getUnit() != null) {
					sb.append(" AND LOWER(un.unit) LIKE LOWER('%"
							+ userCompany.getIdCompanyUnitPosisi().getIdUnit().getUnit() + "%') ");
				}
			}
			if (userCompany.getIdCompanyUnitPosisi().getIdPosisi() != null) {
				if (userCompany.getIdCompanyUnitPosisi().getIdPosisi().getPosisi() != null) {
					sb.append(" AND LOWER(p.posisi) LIKE LOWER('%"
							+ userCompany.getIdCompanyUnitPosisi().getIdPosisi().getPosisi() + "%') ");
				}
			}
			if (userCompany.getIdCompanyUnitPosisi().getIdCompany() != null) {
				if (userCompany.getIdCompanyUnitPosisi().getIdCompany().getId() != null) {
					sb.append(" AND c.id = '"
							+ userCompany.getIdCompanyUnitPosisi().getIdCompany().getId() + "' ");
				}
			}
		}
		
		sb.append(" ORDER BY u.nama ASC LIMIT ");
		sb.append(String.valueOf(jumlah));
		sb.append(" OFFSET ");
		sb.append(String.valueOf(((page-1)*jumlah)));
		
		List<UserCompany> list = super.entityManager.createNativeQuery(sb.toString(),UserCompany.class).getResultList();

		if (list.size() == 0) {
			return new ArrayList<UserCompany>();
		} else {
			return list;
		}
	}

}
