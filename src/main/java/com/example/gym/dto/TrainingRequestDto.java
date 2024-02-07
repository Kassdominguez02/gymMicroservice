package com.example.gym.dto;

import java.util.Date;

public class TrainingRequestDto {
	
	 private String trainingName;
	    private Date trainingDate;
	    private double trainingDuration;
	    private String traineeUsername; 
		private String trainerUsername;
		private Long trainingTypeId;
		private String trainingTypename;
		
		
		

		public TrainingRequestDto() {
			
		}
		
		public TrainingRequestDto(String trainingName, Date trainingDate, double trainingDuration,
				String traineeUsername, String trainerUsername, Long trainingTypeId, String trainingTypename) {
			
			this.trainingName = trainingName;
			this.trainingDate = trainingDate;
			this.trainingDuration = trainingDuration;
			this.traineeUsername = traineeUsername;
			this.trainerUsername = trainerUsername;
			this.trainingTypeId = trainingTypeId;
			this.trainingTypename=trainingTypename;
		}

		public String getTrainingTypename() {
			return trainingTypename;
		}

		public void setTrainingTypename(String trainingTypename) {
			this.trainingTypename = trainingTypename;
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

		public String getTraineeUsername() {
			return traineeUsername;
		}

		public void setTraineeUsername(String traineeUsername) {
			this.traineeUsername = traineeUsername;
		}

		public String getTrainerUsername() {
			return trainerUsername;
		}

		public void setTrainerUsername(String trainerUsername) {
			this.trainerUsername = trainerUsername;
		}

		public Long getTrainingTypeId() {
			return trainingTypeId;
		}

		public void setTrainingTypeId(Long trainingTypeId) {
			this.trainingTypeId = trainingTypeId;
		}

		
		
		
		
		
}
	    