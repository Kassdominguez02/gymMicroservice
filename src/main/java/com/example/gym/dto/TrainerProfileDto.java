package com.example.gym.dto;

import java.util.List;

public class TrainerProfileDto {
	
	private String firstName;
    private String lastName;
    private String specialization;
    private boolean isActive;
    private List<TraineeDto> trainees;
    
    
    
    public TrainerProfileDto() {
    	
    }
	public TrainerProfileDto(String firstName, String lastName, String specialization, boolean isActive,
			List<TraineeDto> trainees) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.specialization = specialization;
		this.isActive = isActive;
		this.trainees = trainees;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public List<TraineeDto> getTrainees() {
		return trainees;
	}
	public void setTrainees(List<TraineeDto> trainees) {
		this.trainees = trainees;
	}
    
    
    
    

}
