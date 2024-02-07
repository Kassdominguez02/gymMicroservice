package com.example.gym.dto;

import java.util.Date;

public class TrainerTrainingsResponseDto {
	
	private String trainingName;
    private Date trainingDate;
    private String trainingType;
    private double trainingDuration;
    private String traineeName;
    
    public TrainerTrainingsResponseDto() {
    	
    }
    
	public TrainerTrainingsResponseDto(String trainingName, Date trainingDate, String trainingType,
			double trainingDuration, String traineeName) {
		
		this.trainingName = trainingName;
		this.trainingDate = trainingDate;
		this.trainingType = trainingType;
		this.trainingDuration = trainingDuration;
		this.traineeName = traineeName;
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

	public String getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}

	public double getTrainingDuration() {
		return trainingDuration;
	}

	public void setTrainingDuration(double trainingDuration) {
		this.trainingDuration = trainingDuration;
	}

	public String getTraineeName() {
		return traineeName;
	}

	public void setTraineeName(String traineeName) {
		this.traineeName = traineeName;
	}

    
    

}
