package dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import pojo.Rating;

public class RatingDAO {
    private SessionFactory sessionFactory;
    private Session session;

    public RatingDAO() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    private Session getSession() {
        return sessionFactory.openSession();
    }

    public void save(Rating rating) {
        session = getSession();
        Transaction tr = session.beginTransaction();

        try {
            session.save(rating);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public void update(Rating rating) {
        session = getSession();
        Transaction tr = session.beginTransaction();

        try {
            session.update(rating);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public void delete(Rating rating) {
        session = getSession();
        Transaction tr = session.beginTransaction();

        try {
            session.delete(rating);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public Rating findById(int ratingID) {
        session = getSession();
        Rating rating = null;

        try {
            rating = session.get(Rating.class, ratingID);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            session.close();
        }

        return rating;
    }

    public List<Rating> findAll() {
        session = getSession();
        List<Rating> list = new LinkedList<>();

        try {
            list = session.createQuery("from Rating", Rating.class).getResultList();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            session.close();
        }

        return list;
    }
}
