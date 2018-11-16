package com.andy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.StampedLock;

import javax.naming.spi.DirStateFactory.Result;

import com.andy.model.Employee;
import com.andy.util.ConnectionUtil;

public class EmployeeJdbc implements IEmployeeDao {
	
	//Could refactor this and remove it but lazy
	@Override
	public Employee findById(int id) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String query = "select ers_users_id as id,\r\n" + 
					"ers_username as username,\r\n" + 
					"ers_password as password,\r\n" + 
					"user_first_name as firstname,\r\n" + 
					"user_last_name as lastname,\r\n" + 
					"user_email as email,\r\n" + 
					"user_role as role\r\n" + 
					"from ers_users\r\n" + 
					"inner join ers_user_roles on ers_users.user_role_id = ers_user_role_id WHERE ers_users_id  = ?;";
			PreparedStatement statment = conn.prepareStatement(query);
			statment.setInt(1, id);
			ResultSet resultSet = statment.executeQuery();
			while(resultSet.next()){
				return new Employee(
					resultSet.getInt("id"),
					resultSet.getString("username"),
					resultSet.getString("password"),
					resultSet.getString("firstname"),
					resultSet.getString("lastname"),
					resultSet.getString("email"),
					resultSet.getString("role")
					);
			}
			
			
		} 
		catch (SQLException sqle) {
			System.out.println(sqle);
		}
		return null;
	}

	@Override
	public Employee findByUserName(String username) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String query = "select ers_users_id as id,\r\n" + 
					"ers_username as username,\r\n" + 
					"ers_password as password,\r\n" + 
					"user_first_name as firstname,\r\n" + 
					"user_last_name as lastname,\r\n" + 
					"user_email as email,\r\n" + 
					"user_role as role\r\n" + 
					"from ers_users\r\n" + 
					"inner join ers_user_roles on ers_users.user_role_id = ers_user_role_id where ers_username = ?";
			
			PreparedStatement statment = conn.prepareStatement(query);
			statment.setString(1, username);
			ResultSet resultSet =  statment.executeQuery();
			
			if(resultSet.next()) { 
				System.out.println(
						resultSet.getString("username") +
						resultSet.getString("password") +
						resultSet.getString("firstname") +
						resultSet.getString("lastname") +
						resultSet.getString("email") +
						resultSet.getString("role"));
				return new Employee(
						resultSet.getInt("id"),
						resultSet.getString("username"),
						resultSet.getString("password"),
						resultSet.getString("firstname"),
						resultSet.getString("lastname"),
						resultSet.getString("email"),
						resultSet.getString("role")
						);
				}

			
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
		return null;
	}

	@Override
	public Employee findByPassword(String password) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String query = "select ers_users_id as id,\r\n" + 
					"ers_username as username,\r\n" + 
					"ers_password as password,\r\n" + 
					"user_first_name as firstname,\r\n" + 
					"user_last_name as lastname,\r\n" + 
					"user_email as email,\r\n" + 
					"user_role as role\r\n" + 
					"from ers_users\r\n" + 
					"inner join ers_user_roles on ers_users.user_role_id = ers_user_role_id where ers_password = ?";
			
			PreparedStatement statment = conn.prepareStatement(query);
			statment.setString(1, password);
			ResultSet resultSet =  statment.executeQuery();
			while(resultSet.next()) {
				return new Employee(
						resultSet.getInt("id"),
						resultSet.getString("username"),
						resultSet.getString("password"),
						resultSet.getString("firstname"),
						resultSet.getString("lastname"),
						resultSet.getString("email"),
						resultSet.getString("role")
						);
				}
			
			
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
		return null;
	}

	//searching for employee with given username and password
	@Override
	public Employee findByUsernameAndPassword(String username, String password) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "select ers_users_id as id,\r\n" + 
					"ers_username as username,\r\n" + 
					"ers_password as password,\r\n" + 
					"user_first_name as firstname,\r\n" + 
					"user_last_name as lastname,\r\n" + 
					"user_email as email,\r\n" + 
					"user_role as role\r\n" + 
					"from ers_users\r\n" + 
					"inner join ers_user_roles on ers_users.user_role_id = ers_user_role_id WHERE ers_username = ? AND ers_password = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet resultSet = ps.executeQuery();
			//not doing a loop here becuase each username because one to one relationship IE one username per account
			if (resultSet.next()) {
				return new Employee(
						resultSet.getInt("id"),
						resultSet.getString("username"),
						resultSet.getString("password"),
						resultSet.getString("firstname"),
						resultSet.getString("lastname"),
						resultSet.getString("email"),
						resultSet.getString("role")
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//return null if result set does not find  anything
		return null;
	}

}
