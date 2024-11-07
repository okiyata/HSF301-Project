package repo.rating;

import java.util.List;

import pojo.Rating;

public interface RatingRepository {
	Rating save(Rating rating);

	Rating update(Rating rating);

	void delete(Rating rating);

	void deleteById(Integer ratingId);

	Rating findById(Integer ratingId);

	List<Rating> findAll();
	
	List<Rating> findRatingsByType(String ratingType);
}
