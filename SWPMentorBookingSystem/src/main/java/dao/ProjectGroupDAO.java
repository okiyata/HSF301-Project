package dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import pojo.ProjectGroup;

public class ProjectGroupDAO {
    private SessionFactory sessionFactory;
    private Session session;

    public ProjectGroupDAO() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    private Session getSession() {
        return sessionFactory.openSession();
    }

    public void save(ProjectGroup projectGroup) {
        session = getSession();
        Transaction tr = session.beginTransaction();

        try {
            session.save(projectGroup);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public void update(ProjectGroup projectGroup) {
        session = getSession();
        Transaction tr = session.beginTransaction();

        try {
            session.update(projectGroup);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public void delete(ProjectGroup projectGroup) {
        session = getSession();
        Transaction tr = session.beginTransaction();

        try {
            session.delete(projectGroup);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public ProjectGroup findById(int groupID) {
        session = getSession();
        ProjectGroup projectGroup = null;

        try {
            projectGroup = session.get(ProjectGroup.class, groupID);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            session.close();
        }

        return projectGroup;
    }

    public List<ProjectGroup> findAll() {
        session = getSession();
        List<ProjectGroup> list = new LinkedList<>();

        try {
            list = session.createQuery("from ProjectGroup", ProjectGroup.class).getResultList();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            session.close();
        }

        return list;
    }
    
    public List<ProjectGroup> findByName(String name) {
        session = getSession();
        List<ProjectGroup> groups = new LinkedList<>();

        try {
            String hql = "FROM ProjectGroup WHERE groupName LIKE :groupName";
            groups = session.createQuery(hql, ProjectGroup.class)
                            .setParameter("groupName", "%" + name + "%")
                            .getResultList();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            session.close();
        }

        return groups;
    }
}
