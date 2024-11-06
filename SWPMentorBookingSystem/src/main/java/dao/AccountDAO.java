package dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import pojo.Account;

public class AccountDAO {
    private SessionFactory sessionFactory;
    private Session session;
    
    public AccountDAO () {
    	Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

	private Session getSession() {
        return sessionFactory.openSession();
    }

    public void save(Account account) {
    	session = getSession();
    	Transaction tr = session.beginTransaction();
    	
    	try {
    		session.save(account);
    		tr.commit();
    	} catch (Exception ex) {
    		tr.rollback();
    		System.out.println(ex);
    	} finally {
    		session.close();
    	}
    }

    public void update(Account account) {
    	session = getSession();
    	Transaction tr = session.beginTransaction();
    	
    	try {
    		session.update(account);
    		tr.commit();
    	} catch (Exception ex) {
    		tr.rollback();
    		System.out.println(ex);
    	} finally {
    		session.close();
    	}
    }

    public void delete(Account account) {
    	session = getSession();
    	Transaction tr = session.beginTransaction();
    	
    	try {
    		session.delete(account);
    		tr.commit();
    	} catch (Exception ex) {
    		tr.rollback();
    		System.out.println(ex);
    	} finally {
    		session.close();
    	}
    }

    public Account findById(int accountID) {
    	session = getSession();
    	Account acc = null;
    	
    	try {
    		acc = session.get(Account.class, accountID);
    	} catch (Exception ex) {
    		System.out.println(ex);
    	} finally {
    		session.close();
    	}
    	
        return acc;
    }
    
    public Account findByUserName (String username) {
    	session = getSession();
    	Account acc = null;
    	
    	try {
    		Query<Account> query = session
    				.createQuery("from Account where username = :username", Account.class)
    				.setParameter("username", username);
    		acc = query.uniqueResult();
    	} catch (Exception ex) {
    		System.out.println(ex);
    	} finally {
    		session.close();
    	}
    	
        return acc;    	
    }

    public List<Account> findAll() {
    	session = getSession();
    	List<Account> list = new LinkedList<>();
    	
    	try {
    		list = session.createQuery("from Account", Account.class).getResultList();
    	} catch (Exception ex) {
    		System.out.println(ex);
    	} finally {
    		session.close();
    	}
    	
        return list;
    }
    
    
}
