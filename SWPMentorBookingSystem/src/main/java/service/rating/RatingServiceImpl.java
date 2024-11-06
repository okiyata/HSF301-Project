package service.rating;

import java.util.List;

import pojo.Rating;
import repo.rating.RatingRepository;
import repo.rating.RatingRepositoryImpl;

public class RatingServiceImpl implements RatingService{
	
	private RatingRepository repo;
	
	public RatingServiceImpl () {
		repo = new RatingRepositoryImpl();
	}
	
	@Override
	public Rating save(Rating rating) {
		return repo.save(rating);
	}

	@Override
	public Rating update(Rating rating) {
		return repo.update(rating);
	}

	@Override
	public void delete(Rating rating) {
		repo.delete(rating);
	}
	
	@Override
	public void deleteById (Integer ratingId) {
		repo.deleteById(ratingId);
	}

	@Override
	public Rating findById(Integer ratingId) {
		return repo.findById(ratingId);
	}

	@Override
	public List<Rating> findAll() {
		return repo.findAll();
	}

}
