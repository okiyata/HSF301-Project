package dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import pojo.Notification;

public class NotificationDAO {
    private SessionFactory sessionFactory;
    private Session session;

    public NotificationDAO() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    private Session getSession() {
        return sessionFactory.openSession();
    }

    public void save(Notification notification) {
        session = getSession();
        Transaction tr = session.beginTransaction();

        try {
            session.save(notification);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public void update(Notification notification) {
        session = getSession();
        Transaction tr = session.beginTransaction();

        try {
            session.update(notification);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public void delete(Notification notification) {
        session = getSession();
        Transaction tr = session.beginTransaction();

        try {
            session.delete(notification);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public Notification findById(int notificationID) {
        session = getSession();
        Notification notification = null;

        try {
            notification = session.get(Notification.class, notificationID);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            session.close();
        }

        return notification;
    }

    public List<Notification> findAll() {
        session = getSession();
        List<Notification> list = new LinkedList<>();

        try {
            list = session.createQuery("from Notification", Notification.class).getResultList();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            session.close();
        }

        return list;
    }
}
