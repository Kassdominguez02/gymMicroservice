package com.example.gym.dto;

public class TrainingTypeDto {
	
	 private Long trainingTypeId;
	    private String trainingType;
	    
	    
	    
	    public TrainingTypeDto() {
	    	
	    }
		public TrainingTypeDto(Long trainingTypeId, String trainingType) {
		
			this.trainingTypeId = trainingTypeId;
			this.trainingType = trainingType;
		}
		public Long getTrainingTypeId() {
			return trainingTypeId;
		}
		public void setTrainingTypeId(Long trainingTypeId) {
			this.trainingTypeId = trainingTypeId;
		}
		public String getTrainingType() {
			return trainingType;
		}
		public void setTrainingType(String trainingType) {
			this.trainingType = trainingType;
		}
	    
	    

}
