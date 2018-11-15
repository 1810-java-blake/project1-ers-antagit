package com.andy.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.andy.model.Reimbursement;

public interface IReimbursementDao {
	IReimbursementDao currentImplamentation = new ReimbursementJdbc();
	
	List<Reimbursement> findAllByEmployeeId(int id);

	
	List<Reimbursement> findAll();
	List<Reimbursement> findByUsername(String username);

	int saveReimbursement(HttpServletRequest req, Reimbursement reimb);
}
