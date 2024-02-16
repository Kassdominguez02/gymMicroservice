package com.example.gym.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteTrainingRequestDTO {
	
	
	 @JsonProperty("trainingId")
	public Long trainingId;
	
public DeleteTrainingRequestDTO() {
		
	}

	public DeleteTrainingRequestDTO(Long trainingId) {
		
		this.trainingId = trainingId;
	}

	public Long getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(Long trainingId) {
		this.trainingId = trainingId;
	}
	
	

}
