package repo.notification;

import java.util.List;

import dao.NotificationDAO;
import pojo.Notification;

public class NotificationRepositoryImpl implements NotificationRepository {

	private NotificationDAO dao;

	public NotificationRepositoryImpl() {
		dao = new NotificationDAO();
	}

	@Override
	public Notification save(Notification notification) {
		dao.save(notification);
		return dao.findById(notification.getNotificationID());
	}

	@Override
	public Notification update(Notification notification) {
		dao.update(notification);
		return dao.findById(notification.getNotificationID());
	}

	@Override
	public void delete(Notification notification) {
		dao.delete(notification);
	}

	@Override
	public void deleteById(Integer notificationId) {
		delete(dao.findById(notificationId));
	}

	@Override
	public Notification findById(Integer notificationId) {
		return dao.findById(notificationId);
	}

	@Override
	public List<Notification> findAll() {
		return dao.findAll();
	}

}
