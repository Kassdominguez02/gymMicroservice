package com.example.gym.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name="trainingtype")
public class TrainingType {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_training_type", unique = true, nullable = false)
	private Long idTrainingType;
	
	@Column(name = "training_type_name")
	public String TrainingTypeName;
	
	public TrainingType() {
		
	}

	public TrainingType(Long idTrainingType, String trainingTypeName) {
		
		this.idTrainingType = idTrainingType;
		TrainingTypeName = trainingTypeName;
	}

	public String getTrainingTypeName() {
		return TrainingTypeName;
	}

	public Long getIdTrainingType() {
		return idTrainingType;
	}

	

	public void setTrainingTypeName(String trainingTypeName) {
		TrainingTypeName = trainingTypeName;
	}
	
	
	
	
	

}
