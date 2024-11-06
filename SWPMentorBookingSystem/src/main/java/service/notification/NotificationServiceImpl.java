package service.notification;

import java.util.List;

import pojo.Notification;
import repo.notification.NotificationRepository;
import repo.notification.NotificationRepositoryImpl;

public class NotificationServiceImpl implements NotificationService{
	
	private NotificationRepository repo;
	
	public NotificationServiceImpl () {
		repo = new NotificationRepositoryImpl();
	}
	
	@Override
	public Notification save(Notification notification) {
		return repo.save(notification);
	}

	@Override
	public Notification update(Notification notification) {
		return repo.update(notification);
	}

	@Override
	public void delete(Notification notification) {
		repo.delete(notification);
	}
	
	@Override
	public void deleteById (Integer notificationId) {
		repo.deleteById(notificationId);
	}

	@Override
	public Notification findById(Integer notificationId) {
		return repo.findById(notificationId);
	}

	@Override
	public List<Notification> findAll() {
		return repo.findAll();
	}

}
