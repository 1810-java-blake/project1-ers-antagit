package com.andy.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.postgresql.jdbc2.optional.SimpleDataSource;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.andy.model.Employee;
import com.andy.model.Reimbursement;
import com.andy.services.IReimbursementService;
import com.andy.util.ConnectionUtil;


public class ReimbursementJdbc implements IReimbursementDao {
	IReimbursementService reimbService = IReimbursementService.currentImplamentation;
	//retirning all of the reimb with given where ers_users = id
	@Override
	public List<Reimbursement> findAllByEmployeeId(int id) {
		
		//a list that will be holding all of or reimb 
		List<Reimbursement> reimbList = new ArrayList<>();
		
		//setting up our connection with our diserd database
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			//setting up query string
			 String query = "SELECT ers_users_id as employeeId,\r\n" + 
			 		"reimb_amount as amount,\r\n" + 
			 		"reimb_submitted as submitted,\r\n" + 
			 		"reimb_resolved as resolved,\r\n" + 
			 		"reimb_description as description,\r\n" + 
			 		"reimb_receipt as receipt,\r\n" + 
			 		"reimb_status as status,\r\n" + 
			 		"reimb_type as type \r\n" + 
			 		"FROM ERS_REIMBURSEMENT\r\n" + 
			 		"INNER JOIN ERS_REIMBURSEMENT_STATUS ON ers_reimbursement.reimb_status_id = ERS_REIMBURSEMENT_STATUS.reimb_status_id\r\n" + 
			 		"INNER JOIN ERS_REIMBURSEMENT_TYPE ON ers_reimbursement.reimb_type_id = ERS_REIMBURSEMENT_TYPE.reimb_type_id\r\n" + 
			 		"INNER JOIN ERS_USERS ON ers_reimbursement.reimb_author = ERS_USERS.ers_users_id \r\n" + 
			 		"where ers_users_id = ?";
			PreparedStatement statment = conn.prepareStatement(query);
			statment.setInt(1, id);
			ResultSet resultSet = statment.executeQuery();
			
			//getting the first result. REMEMBER THAT the index will be before our desired index thus we use a do while loop 

			while(resultSet.next()) {
				reimbList.add(new Reimbursement (resultSet.getInt("employeeId"),
						resultSet.getDouble("amount"),
						resultSet.getDate("submitted"),
						resultSet.getDate("resolved"),
						resultSet.getString("description"),
						resultSet.getString("receipt"),
						resultSet.getString("status"),
						resultSet.getString("type")
						
						));
			}
			return reimbList;
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
		return null;
	}

	@Override
	public int saveReimbursement(HttpServletRequest req, Reimbursement reimb) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			IEmployeeDao employeeDao = IEmployeeDao.currentImplamentation;
			Employee employee = employeeDao.findByUserName(req.getSession().getAttribute("username").toString());
			String query = "INSERT INTO ERS_REIMBURSEMENT("
					+ "reimb_amount,"
					+ "reimb_submitted,"
					+ "reimb_resolved,"
					+ "reimb_description,"
					+ "reimb_receipt,"
					+ "reimb_author,"
					+ "reimb_resolver,"
					+ "reimb_status_id,"
					+ "reimb_type_id)"
					+ "VALUES(?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement statment = conn.prepareStatement(query);
			statment.setDouble(1, reimb.getAmount()); // done
			statment.setDate(2, Date.valueOf(LocalDate.now().toString())); //done
			statment.setDate(3, reimb.getResolved()); //done
			statment.setString(4, reimb.getDescription()); //done
			statment.setString(5, reimb.getReceipt()); //done
			statment.setInt(6, employee.getId());//done
			statment.setInt(7, 2); //done
			statment.setInt(8, 1);// done
			statment.setInt(9, reimb.getTypeId()); //done
			
			statment.executeUpdate();
		return 1;
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
		return 0;
	}

	@Override
	public List<Reimbursement> findAll() {
		//a list that will be holding all of or reimb 
		List<Reimbursement> reimbList = new ArrayList<>();
		
		//setting up our connection with our diserd database
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			//setting up query string
			 String query = "SELECT reimb_id as reimbId,\r\n" + 
			 		"	user_first_name as firstname,\r\n" + 
			 		"	user_last_name as lastname,\r\n" + 
			 		"	reimb_amount as amount,\r\n" + 
			 		"	reimb_submitted as submitted,\r\n" + 
			 		"	reimb_resolved as resolved,\r\n" + 
			 		"	reimb_description as description,\r\n" + 
			 		"	reimb_receipt as receipt,\r\n" + 
			 		"	reimb_status as status,\r\n" + 
			 		"	reimb_type as type \r\n" + 
			 		"	FROM ERS_REIMBURSEMENT\r\n" + 
			 		"	INNER JOIN ERS_REIMBURSEMENT_STATUS ON ers_reimbursement.reimb_status_id = ERS_REIMBURSEMENT_STATUS.reimb_status_id\r\n" + 
			 		"	INNER JOIN ERS_REIMBURSEMENT_TYPE ON ers_reimbursement.reimb_type_id = ERS_REIMBURSEMENT_TYPE.reimb_type_id\r\n" + 
			 		"	INNER JOIN ERS_USERS ON ers_reimbursement.reimb_author = ERS_USERS.ers_users_id WHERE user_role_id = 2";
			 
				PreparedStatement statment = conn.prepareStatement(query);
				ResultSet resultSet = statment.executeQuery();
					//getting the first result. REMEMBER THAT the index will be before our desired index thus we use a do while loop 
					while(resultSet.next()) {
						reimbList.add(new Reimbursement (
								resultSet.getInt("reimbId"),
								resultSet.getString("firstname"),
								resultSet.getString("lastname"),
								resultSet.getDouble("amount"),
								resultSet.getDate("submitted"),
								resultSet.getDate("resolved"),
								resultSet.getString("description"),
								resultSet.getString("receipt"),
								resultSet.getString("status"),
								resultSet.getString("type")
								
								));
					}
					return reimbList;
				} catch (SQLException sqle) {
					System.out.println(sqle);
				}
		return null;
	}

	@Override
	public List<Reimbursement> findByUsername(String username) {
		//a list that will be holding all of or reimb 
		List<Reimbursement> reimbList = new ArrayList<>();
		//setting up our connection with our diserd database
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			//setting up query string
			 String query = "SELECT reimb_id as reimb_id,\r\n" + 
			 		"reimb_amount as amount,\r\n" + 
			 		"reimb_submitted as submitted,\r\n" + 
			 		"reimb_resolved as resolved,\r\n" + 
			 		"reimb_description as description,\r\n" + 
			 		"reimb_receipt as receipt,\r\n" + 
			 		"reimb_status as status,\r\n" + 
			 		"reimb_type as type \r\n" + 
			 		"FROM ERS_REIMBURSEMENT\r\n" + 
			 		"INNER JOIN ERS_REIMBURSEMENT_STATUS ON ers_reimbursement.reimb_status_id = ERS_REIMBURSEMENT_STATUS.reimb_status_id\r\n" + 
			 		"INNER JOIN ERS_REIMBURSEMENT_TYPE ON ers_reimbursement.reimb_type_id = ERS_REIMBURSEMENT_TYPE.reimb_type_id\r\n" + 
			 		"INNER JOIN ERS_USERS ON ers_reimbursement.reimb_author = ERS_USERS.ers_users_id WHERE ers_username = ?";
			 
				PreparedStatement statment = conn.prepareStatement(query);
				statment.setString(1, username);
				ResultSet resultSet = statment.executeQuery();
					//getting the first result. REMEMBER THAT the index will be before our desired index thus we use a do while loop 
					while(resultSet.next()) {
						reimbList.add(new Reimbursement (
								resultSet.getInt("reimb_id"),
								resultSet.getDouble("amount"),
								resultSet.getDate("submitted"),
								resultSet.getDate("resolved"),
								resultSet.getString("description"),
								resultSet.getString("receipt"),
								resultSet.getString("status"),
								resultSet.getString("type")
								
								));
					}
					return reimbList;
				} catch (SQLException sqle) {
					System.out.println(sqle);
				}
		return null;
	}

	@Override
	public List<Reimbursement> reject(int reimbId) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String query = "update ers_reimbursement\r\n" + 
					"set reimb_status_id = 3 , reimb_resolved = ?\r\n" + 
					"where reimb_id = ?";
			PreparedStatement statment = conn.prepareStatement(query);
			statment.setDate(1, Date.valueOf(LocalDate.now().toString()));
			statment.setInt(2, reimbId);
			 
			statment.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this.findAll();
		
	}

	@Override
	public List<Reimbursement> accept(int reimbId) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String query = "update ers_reimbursement\r\n" + 
					"set reimb_status_id = 2, reimb_resolved = ?\r\n" + 
					"where reimb_id = ?";
			PreparedStatement statment = conn.prepareStatement(query);
			statment.setDate(1, Date.valueOf(LocalDate.now().toString()));
			statment.setInt(2, reimbId);
			 
			statment.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.findAll();	
	}

	@Override
	public List<Reimbursement> findById(int reimbID) {
		//a list that will be holding all of or reimb 
				List<Reimbursement> reimbList = new ArrayList<>();
				//setting up our connection with our diserd database
				try(Connection conn = ConnectionUtil.getConnection()) {
					
					//setting up query string
					 String query = "SELECT reimb_id as reimb_id,\r\n" + 
					 		"reimb_amount as amount,\r\n" + 
					 		"reimb_submitted as submitted,\r\n" + 
					 		"reimb_resolved as resolved,\r\n" + 
					 		"reimb_description as description,\r\n" + 
					 		"reimb_receipt as receipt,\r\n" + 
					 		"reimb_status as status,\r\n" + 
					 		"reimb_type as type \r\n" + 
					 		"FROM ERS_REIMBURSEMENT\r\n" + 
					 		"INNER JOIN ERS_REIMBURSEMENT_STATUS ON ers_reimbursement.reimb_status_id = ERS_REIMBURSEMENT_STATUS.reimb_status_id\r\n" + 
					 		"INNER JOIN ERS_REIMBURSEMENT_TYPE ON ers_reimbursement.reimb_type_id = ERS_REIMBURSEMENT_TYPE.reimb_type_id\r\n" + 
					 		"INNER JOIN ERS_USERS ON ers_reimbursement.reimb_author = ERS_USERS.ers_users_id WHERE reimb_id = ?";
					 
						PreparedStatement statment = conn.prepareStatement(query);
						statment.setInt(1, reimbID);
						ResultSet resultSet = statment.executeQuery();
							//getting the first result. REMEMBER THAT the index will be before our desired index thus we use a do while loop 
							while(resultSet.next()) {
								reimbList.add(new Reimbursement (
										resultSet.getInt("reimb_id"),
										resultSet.getDouble("amount"),
										resultSet.getDate("submitted"),
										resultSet.getDate("resolved"),
										resultSet.getString("description"),
										resultSet.getString("receipt"),
										resultSet.getString("status"),
										resultSet.getString("type")
										
										));
							}
							return reimbList;
						} catch (SQLException sqle) {
							System.out.println(sqle);
						}
				return null;
			}
	
	
	
	
}
