package dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import pojo.ProjectGroup;
import pojo.Student;

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
			groups = session.createQuery(hql, ProjectGroup.class).setParameter("groupName", "%" + name + "%")
					.getResultList();
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			session.close();
		}

		return groups;
	}

	public ProjectGroup findGroupByStudentId(int studentID) {
		Session session = sessionFactory.openSession();
		ProjectGroup projectGroup = null;

		try {
			Student student = session.get(Student.class, studentID);
			if (student != null) {
				projectGroup = student.getProjectGroup();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return projectGroup;
	}

	public boolean addMemberToGroup(int groupID, int studentID) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		boolean added = false;

		try {
			ProjectGroup projectGroup = session.get(ProjectGroup.class, groupID);
			Student student = session.get(Student.class, studentID);

			if (projectGroup != null && student != null && student.getProjectGroup() == null) {
				if (!projectGroup.getMembers().contains(student)) {
					projectGroup.getMembers().add(student);
					student.setProjectGroup(projectGroup);
					session.update(student);
					added = true;
				}
			}

			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null)
				transaction.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return added;
	}

	public int getMemberCount(int groupID) {
		Session session = sessionFactory.openSession();
		int memberCount = 0;

		try {
			String hql = "SELECT size(g.members) FROM ProjectGroup g WHERE g.groupID = :groupID";
			Integer count = session.createQuery(hql, Integer.class)
					.setParameter("groupID", groupID).uniqueResult();
			memberCount = (count != null) ? count : 0;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return memberCount;
	}
	
	public List<Student> getMembers(int groupID) {
        Session session = sessionFactory.openSession();
        List<Student> members = null;

        try {
            String hql = "SELECT s FROM Student s WHERE s.projectGroup.groupID = :groupID";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("groupID", groupID);
            members = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return members;
    }
    public String getGroupNameById(int groupID) {
        Session session = sessionFactory.openSession();
        String groupName = null;

        try {
            String hql = "SELECT g.groupName FROM ProjectGroup g WHERE g.groupID = :groupID";
            groupName = session.createQuery(hql, String.class)
                               .setParameter("groupID", groupID)
                               .uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return groupName != null ? groupName : "Unknown Group";
    }
}
