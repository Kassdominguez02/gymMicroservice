package com.example.gym.dto;

import java.util.Date;

public class TraineeUpdateDto {
	
	
	    private String firstName;
	    private String lastName;
	    private Date dateBirth;
	    private String address;
	    private boolean isActive;
	    
	    
	    
	     public TraineeUpdateDto() { 
	    	 
	     }
	    
		public TraineeUpdateDto(String firstName, String lastName, Date dateBirth, String address,
				boolean isActive) {
			
			
			this.firstName = firstName;
			this.lastName = lastName;
			this.dateBirth = dateBirth;
			this.address = address;
			this.isActive = isActive;
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

		public Date getDateBirth() {
			return dateBirth;
		}

		public void setDateBirth(Date dateBirth) {
			this.dateBirth = dateBirth;
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
	    
	    
	    

}
