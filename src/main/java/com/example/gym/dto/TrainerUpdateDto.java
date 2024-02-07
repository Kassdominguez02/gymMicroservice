package com.example.gym.dto;

public class TrainerUpdateDto {
	
	 private String firstName;
	    private String lastName;
	    private boolean isActive;
	    
	    public TrainerUpdateDto() {
	    	
	    }
	    
		public TrainerUpdateDto(String firstName, String lastName, boolean isActive) {
			
			this.firstName = firstName;
			this.lastName = lastName;
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

		public boolean isActive() {
			return isActive;
		}

		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}
	    
	    
	    
	

}
