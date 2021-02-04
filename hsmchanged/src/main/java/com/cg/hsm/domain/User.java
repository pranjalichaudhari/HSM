package com.cg.hsm.domain;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**
 * This User class will create users table in the database
 * @author ANJUM
 *
 */
@Entity
@Table(name="users")
public class User
{
	/*
	 * Id of the user 
	 */
	@Id
    private String userId;
	/*
	 * password of the user
	 */
    private String userPassword;
    /*
     * status of the user
     */
    private String status;
    /*
     * Has who created the user
     */
    private String createdBy;
    /*
     * on which date user got created
     */
    private Date createdOn;
	/*
	 * onetoone relation with the doctor table
	 */
	@OneToOne
    private Doctor doctor;
	//getters and setter
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userName) {
		this.userId = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String pwd) {
		this.userPassword = pwd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
    
}
