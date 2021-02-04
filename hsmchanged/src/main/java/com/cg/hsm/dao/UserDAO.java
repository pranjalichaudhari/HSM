package com.cg.hsm.dao;

import com.cg.hsm.domain.User;
/**
 * This UserDAO interface will perform the admin and user authentication
 * @author ANJUM
 *
 */
public interface UserDAO 
{
	/**
	 * This addAuthentication method provides authentication to new user
	 * @param user
	 */
   void addAuthentication(User user);
   /**
    * This checkAdminAuthentication method checks if the admin is authenticated or not
    * @param username
    * @param password
    * @return
    */
   boolean checkAdminAuthentication(String username,String password);
   /**
    * This checkAuthentication method checks if the user is authenticated or not
    * @param username
    * @param password
    * @return
    */
   boolean checkAuthentication(String username,String password);
   /**
    * This updateStatus method is used to update status of user from active to inactive
    * @param username
    */
   void updateStatus(String username);
}