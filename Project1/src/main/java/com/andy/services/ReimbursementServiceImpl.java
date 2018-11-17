package com.andy.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.andy.dao.IReimbursementDao;
import com.andy.model.Reimbursement;

public class ReimbursementServiceImpl implements IReimbursementService{
	private IReimbursementDao reimbDao = IReimbursementDao.currentImplamentation;
	@Override
	public List<Reimbursement> findAllByEmployeeId(int id) {
		return reimbDao.findAllByEmployeeId(id);
		
	}

	@Override
	public int saveReimbursement(HttpServletRequest req, Reimbursement reimb) {
		return reimbDao.saveReimbursement(req,reimb);
	}

	@Override
	public List<Reimbursement> findAll() {
		return reimbDao.findAll();
	}

	@Override
	public List<Reimbursement> findByUsername(String username) {
		return reimbDao.findByUsername(username);
	}

	@Override
	public List<Reimbursement> reject(int reimbId) {
		 return reimbDao.reject(reimbId);
		
	}

	@Override
	public List<Reimbursement> accept(int reimbId) {
		return reimbDao.accept(reimbId);
		
	}

	@Override
	public List<Reimbursement> findById(int reimbId) {
		return reimbDao.accept(reimbId);
	}

}
