package service.rating;

import java.util.List;

import pojo.Rating;

public interface RatingService {
	Rating save(Rating rating);

	Rating update(Rating rating);

	void delete(Rating rating);

	void deleteById(Integer ratingId);

	Rating findById(Integer ratingId);

	List<Rating> findAll();
}
