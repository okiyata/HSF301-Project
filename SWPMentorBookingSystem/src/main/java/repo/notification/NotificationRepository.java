package repo.notification;

import java.util.List;

import pojo.Notification;

public interface NotificationRepository {
	Notification save(Notification notification);

	Notification update(Notification notification);

	void delete(Notification notification);

	void deleteById(Integer notificationId);

	Notification findById(Integer notificationId);

	List<Notification> findAll();
}
