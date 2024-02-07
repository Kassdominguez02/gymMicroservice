package com.example.gym.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table (name="traineer")
public class Trainer {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_traineer", unique = true, nullable = false)
	private Long idTrainer;
	
	@ManyToOne
	@JoinColumn (name = "user_id_user", referencedColumnName = "id_user")
	 private User user;
	
	@ManyToOne
	@JoinColumn (name = "training_type_id_training_type", referencedColumnName = "id_training_type")
	public TrainingType trainingType;
	
	@ManyToMany(mappedBy = "trainers")
    private Set<Trainee> trainees = new HashSet<>();
	
	@OneToMany(mappedBy = "trainer")
	private List<Training> trainings = new ArrayList<>();


	

	public Trainer() {
		
	}
	
	public Trainer(Long idTrainer, User user, TrainingType trainingType) {
		super();
		this.idTrainer = idTrainer;
		this.user = user;
		this.trainingType = trainingType;
	}

	public Long getIdTrainer() {
		return idTrainer;
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TrainingType getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(TrainingType trainingType) {
		this.trainingType = trainingType;
	}
	
	public Set<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(Set<Trainee> trainees) {
        this.trainees = trainees;
    }
	
    
    public List<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Training> trainings) {
		this.trainings = trainings;
	}

	@Override
	public String toString() {
		return "Trainer [idTrainer=" + idTrainer + ", user=" + user + ", trainingType=" + trainingType + "]";
	}
	
	

}
