package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pojo.*;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        // Khởi tạo SessionFactory từ hibernate.cfg.xml
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        // Tạo session để thực hiện các thao tác CRUD
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            // Tạo Account cho Mentor và Students
            Account mentorAccount = new Account("mentor1", "1234", "MENTOR");
            Account studentAccount = new Account("student1", "1234", "STUDENT");
            Account studentAccount2 = new Account("student2", "1234", "STUDENT");

            // Tạo Mentor và ProjectGroup
            Mentor mentor = new Mentor("Mentor1", "Java, Spring", "Weekdays", 5, "AVAILABLE", mentorAccount);

            // Tạo Students và liên kết với Account
            Student student = new Student("Student1", studentAccount, null);
            studentAccount.setStudent(student);

            Student student2 = new Student("Student2", studentAccount2, null);
            studentAccount2.setStudent(student2);

            // Tạo ProjectGroup và thêm thành viên
            ProjectGroup projectGroup = new ProjectGroup("Jewelry Product", "FA24_LamNN15_Nhom1", "In Progress", 10);
            projectGroup.addMember(student);
            student.setProjectGroup(projectGroup);

            // Lưu các đối tượng Account, Mentor, và ProjectGroup
            session.save(mentorAccount);
            session.save(studentAccount);
            session.save(studentAccount2);

            session.save(mentor);
            session.save(student);
            session.save(student2);

            session.save(projectGroup);

            // Tạo Appointment giữa ProjectGroup và Mentor
            Appointment appointment = new Appointment(projectGroup, mentor, LocalDateTime.now().plusDays(1),
                    "Spring Framework", mentor.getBookingFee(), "BOOKED");
            session.save(appointment);

            // Tạo Rating cho Mentor và ProjectGroup
            Rating mentorRating = new Rating(5, "Great mentor!", "MENTOR", mentor, null);
            Rating groupRating = new Rating(4, "Good project progress", "GROUP", null, projectGroup);
            session.save(mentorRating);
            session.save(groupRating);

            // Tạo Notification cho Mentor và ProjectGroup
            Notification mentorNotification = new Notification("Your session is scheduled.", false, "MENTOR", mentor,
                    null);
            Notification groupNotification = new Notification("Project review is scheduled soon.", false, "GROUP", null,
                    projectGroup);
            session.save(mentorNotification);
            session.save(groupNotification);

            // Commit transaction để lưu các đối tượng vào cơ sở dữ liệu
            transaction.commit();
            System.out.println("Data saved successfully!");

        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            // Đóng session
            session.close();
            sessionFactory.close();
        }
    }
}

