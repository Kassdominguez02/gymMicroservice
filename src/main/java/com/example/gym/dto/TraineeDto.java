package com.example.gym.dto;

import java.util.Date;

import io.micrometer.common.lang.Nullable;

public class TraineeDto {
	
	private String firstName;
    private String lastName;
    
    @Nullable
    private String address;
   
    
    @Nullable
	private Date dateBirth;
	
	
	 public TraineeDto() {
			
			
		}

	 public TraineeDto(String firstName, String lastName, String address, Date dateBirth) {
			
			this.firstName = firstName;
			this.lastName = lastName;
			this.address = address;
			this.dateBirth = dateBirth;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}
	 
	 
	 
}
