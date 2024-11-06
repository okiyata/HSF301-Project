package repo.rating;

import java.util.List;

import dao.RatingDAO;
import pojo.Rating;

public class RatingRepositoryImpl implements RatingRepository {

	private RatingDAO dao;

	public RatingRepositoryImpl() {
		dao = new RatingDAO();
	}

	@Override
	public Rating save(Rating rating) {
		dao.save(rating);
		return dao.findById(rating.getRatingID());
	}

	@Override
	public Rating update(Rating rating) {
		dao.update(rating);
		return dao.findById(rating.getRatingID());
	}

	@Override
	public void delete(Rating rating) {
		dao.delete(rating);
	}

	@Override
	public void deleteById(Integer ratingId) {
		delete(dao.findById(ratingId));
	}

	@Override
	public Rating findById(Integer ratingId) {
		return dao.findById(ratingId);
	}

	@Override
	public List<Rating> findAll() {
		return dao.findAll();
	}

}
