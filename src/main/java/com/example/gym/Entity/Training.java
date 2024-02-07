package com.example.gym.Entity;

import java.util.Date;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name="trainning")
public class Training {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trainning", unique = true, nullable = false)
	private Long idTraining;
	
	@Column(name = "training_name")
	private String trainingName;
	
	@Column(name = "training_date")
	public Date trainingDate;
	
	@Column(name = "training_duration")
	public double trainingDuration;
	
	@ManyToOne
    @JoinColumn(name = "trainee_id_trainee", referencedColumnName = "id_trainee")
	public Trainee trainee;
	
	@ManyToOne (targetEntity = Trainer.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "traineer_id_traineer", referencedColumnName = "id_traineer")
	public Trainer trainer;
	
	@ManyToOne (targetEntity = TrainingType.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "training_type_id_training_type", referencedColumnName = "id_training_type")
	public TrainingType trainingType;
	
	public Training() {
	}

	public Training(Long idTraining, String trainingName, Date trainingDate, double trainingDuration, Trainee trainee,
			Trainer trainer, TrainingType trainingType) {
	
		this.idTraining = idTraining;
		this.trainingName = trainingName;
		this.trainingDate = trainingDate;
		this.trainingDuration = trainingDuration;
		this.trainee = trainee;
		this.trainer = trainer;
		this.trainingType = trainingType;
	}

	public Long getIdTraining() {
		return idTraining;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public Date getTrainingDate() {
		return trainingDate;
	}

	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}

	public double getTrainingDuration() {
		return trainingDuration;
	}

	public void setTrainingDuration(double trainingDuration) {
		this.trainingDuration = trainingDuration;
	}

	public Trainee getTrainee() {
		return trainee;
	}

	public void setTrainee(Trainee trainee) {
		this.trainee = trainee;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public TrainingType getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(TrainingType trainingType) {
		this.trainingType = trainingType;
	}
	
	
	
	
}

	
	
	
	
	
	
	

	
	
	
	
	
	


