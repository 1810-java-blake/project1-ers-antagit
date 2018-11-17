package com.andy.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.andy.model.Reimbursement;

public interface IReimbursementService {
	IReimbursementService currentImplamentation = new ReimbursementServiceImpl();
	List<Reimbursement> findAllByEmployeeId(int id);

	
	List<Reimbursement> findAll();
	List<Reimbursement> findByUsername(String username);

	int saveReimbursement(HttpServletRequest req, Reimbursement reimb);
	
	List<Reimbursement> findById(int reimbId);
	
	List<Reimbursement> reject(int reimbId);
	List<Reimbursement> accept(int reimbId);
}
