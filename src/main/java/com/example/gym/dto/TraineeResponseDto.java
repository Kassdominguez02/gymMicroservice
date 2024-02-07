package com.example.gym.dto;

import java.util.Date;
import java.util.List;

public class TraineeResponseDto {
	
	private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private boolean isActive;
    private List<TrainerResponseDto> trainers;
    
    
    public TraineeResponseDto() {
    	
    }
	public TraineeResponseDto(String firstName, String lastName, Date dateOfBirth, String address, boolean isActive,
			List<TrainerResponseDto> trainers) {
	
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.isActive = isActive;
		this.trainers = trainers;
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
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public List<TrainerResponseDto> getTrainers() {
		return trainers;
	}
	public void setTrainers(List<TrainerResponseDto> trainers) {
		this.trainers = trainers;
	}
    
	
	
   
	 
}
