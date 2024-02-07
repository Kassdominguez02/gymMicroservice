package com.example.gym.dto;

import java.time.LocalDate;
import java.util.Date;


public class TrainerTrainingsRequestDto {
	
	private String username;
    private Date periodFrom;
    private Date periodTo;
    private String traineeName;
    
    public TrainerTrainingsRequestDto() {
    	
    }
    
	public TrainerTrainingsRequestDto(String username, Date periodFrom, Date periodTo, String traineeName) {
		
		this.username = username;
		this.periodFrom = periodFrom;
		this.periodTo = periodTo;
		this.traineeName = traineeName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(Date periodFrom) {
		this.periodFrom = periodFrom;
	}

	public Date getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(Date periodTo) {
		this.periodTo = periodTo;
	}

	public String getTraineeName() {
		return traineeName;
	}

	public void setTraineeName(String traineeName) {
		this.traineeName = traineeName;
	}
    
    

}
