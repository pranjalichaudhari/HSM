package com.cg.hsm.daoimpl;
import com.cg.hsm.dao.UserDAO;
import com.cg.hsm.domain.User;
import com.cg.hsm.util.DBUtil;
/**
 * This UserDAOImpl class implements classes of UserDAO Class
 * @author ANJUM
 *
 */
public class UserDAOImpl extends DBUtil implements UserDAO
{
/*
 *  This addAuthentication method will add credentials of the user in user table
 */
	@Override
	public void addAuthentication(User user) {
		
        entityManager.getTransaction().begin();
		
		entityManager.persist(user);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
	}
	/*
	 * This checkAuthentication method will check whether the user authentication is safe or not
	 */
	@Override
	public boolean checkAuthentication(String username, String password)
	{
		User user=entityManager.find(User.class, username);
		String res=user.getUserPassword();
		if(res.equals(password))
		{
			return true;
		}
		else
		{
			return false;
		}
	
	}
    /*
     * This checkAdminAuthentication method will check whether the admin authentication is safe or not
     */
	@Override
	public boolean checkAdminAuthentication(String username, String password)
	{
	  String adminUserName="HSMGroup";
	  String adminPassword="teamwork";
	  if(username.equals(adminUserName) && password.equals(adminPassword))
	  {
		  return true;
	  }
	  else 
	  {
		  return false;
		
	  }
	 }
	@Override
	public void updateStatus(String username) 
	{
entityManager.getTransaction().begin();
		
		User user = entityManager.find(User.class, username);
		user.setStatus("InActive");
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}     
}