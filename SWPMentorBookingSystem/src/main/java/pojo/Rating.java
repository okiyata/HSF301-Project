package pojo;

import javax.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ratingID;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = true)
    private String feedback;

    @Column(nullable = false)
    private String ratingType; // "MENTOR" hoặc "GROUP" để phân biệt loại đánh giá

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mentorID")
    private Mentor mentor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupID")
    private ProjectGroup projectGroup;

    public Rating() {
    }

    public Rating(int rating, String feedback, String ratingType, Mentor mentor, ProjectGroup projectGroup) {
        this.rating = rating;
        this.feedback = feedback;
        this.ratingType = ratingType;
        this.mentor = mentor;
        this.projectGroup = projectGroup;
    }

	public int getRatingID() {
		return ratingID;
	}

	public void setRatingID(int ratingID) {
		this.ratingID = ratingID;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getRatingType() {
		return ratingType;
	}

	public void setRatingType(String ratingType) {
		this.ratingType = ratingType;
	}

	public Mentor getMentor() {
		return mentor;
	}

	public void setMentor(Mentor mentor) {
		this.mentor = mentor;
	}

	public ProjectGroup getProjectGroup() {
		return projectGroup;
	}

	public void setProjectGroup(ProjectGroup projectGroup) {
		this.projectGroup = projectGroup;
	}

	@Override
	public String toString() {
		return "Rating [ratingID=" + ratingID + ", rating=" + rating + ", feedback=" + feedback + ", ratingType="
				+ ratingType + "]";
	}
	

}