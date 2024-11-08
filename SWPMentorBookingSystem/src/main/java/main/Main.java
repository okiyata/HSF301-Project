package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pojo.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            List<Account> mentorAccounts = new ArrayList<>();
            List<Account> studentAccounts = new ArrayList<>();
            List<Account> studentNoGroupAccounts = new ArrayList<>();
            List<Mentor> mentors = new ArrayList<>();
            List<Student> students = new ArrayList<>();
            List<Student> studentsNoGroup = new ArrayList<>();
            List<ProjectGroup> projectGroups = new ArrayList<>();
            List<Appointment> appointments = new ArrayList<>();
            List<Rating> ratings = new ArrayList<>();

            for (int i = 1; i <= 5; i++) {
                Account mentorAccount = new Account("mentor" + i, "1234", "MENTOR");
                Account studentAccount = new Account("student" + i, "1234", "STUDENT");

                mentorAccounts.add(mentorAccount);
                studentAccounts.add(studentAccount);

                session.save(mentorAccount);
                session.save(studentAccount);
            }
            
            for (int i = 6; i <= 10; i++) {
                Account studentNoGroupAccount = new Account("student" + i, "1234", "STUDENT");
                studentNoGroupAccounts.add(studentNoGroupAccount);
                session.save(studentNoGroupAccount);
            }
            
            Account adminAccount = new Account("admin", "admin123", "ADMIN");
            session.save(adminAccount);

            for (int i = 1; i <= 5; i++) {
                Mentor mentor = new Mentor("Mentor" + i, "Java, Spring", "Weekdays", i * 10, "AVAILABLE", mentorAccounts.get(i - 1));
                mentors.add(mentor);
                session.save(mentor);
            }

            for (int i = 1; i <= 5; i++) {
                Student student = new Student("Student" + i, studentAccounts.get(i - 1), null);
                students.add(student);
                studentAccounts.get(i - 1).setStudent(student);
                session.save(student);
            }
            
            for (int i = 6; i <= 10; i++) {
                Student studentNoGroup = new Student("Student" + i, studentNoGroupAccounts.get(i - 6), null);
                studentsNoGroup.add(studentNoGroup);
                studentNoGroupAccounts.get(i - 6).setStudent(studentNoGroup);
                session.save(studentNoGroup);
            }

            for (int i = 1; i <= 5; i++) {
                ProjectGroup projectGroup = new ProjectGroup("ProjectGroup" + i, "FA24_Nhom" + i, students.get(i - 1), "In Progress", i * 100);
                projectGroups.add(projectGroup);

                projectGroup.addMember(students.get(i - 1));
                students.get(i - 1).setProjectGroup(projectGroup);

                session.save(projectGroup);
            }

            for (int i = 1; i <= 5; i++) {
                Appointment appointment = new Appointment(projectGroups.get(i - 1), mentors.get(i - 1), LocalDateTime.now().plusDays(i), "Spring Framework", mentors.get(i - 1).getBookingFee(), "FINISHED");
                appointments.add(appointment);
                session.save(appointment);
            }

            for (int i = 1; i <= 5; i++) {
            	int randomRating = ThreadLocalRandom.current().nextInt(1, 6);
            	
                Rating mentorRating = new Rating(randomRating, "Great mentor!", "MENTOR", appointments.get(i - 1));
                Rating groupRating = new Rating(randomRating, "Good project progress", "GROUP", appointments.get(i - 1));

                ratings.add(mentorRating);
                ratings.add(groupRating);

                session.save(mentorRating);
                session.save(groupRating);
            }

            transaction.commit();
            System.out.println("Data saved successfully!");

        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}
