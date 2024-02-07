package com.example.gym.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table (name="trainee")
public class Trainee {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trainee", unique = true, nullable = false)
	private Long idTrainee;
	
	private String adress;
	private Date dateBirth;
	
	@ManyToOne
    @JoinColumn(name = "user_id_user", referencedColumnName = "id_user")
    private User user;
	
	@ManyToMany
    @JoinTable(
        name = "trainee_has_traineer",
        joinColumns =  @JoinColumn(name = "trainee⁯_id", referencedColumnName = "id_trainee"),
        inverseJoinColumns = @JoinColumn(name = "trainer_id", referencedColumnName = "id_traineer")
    )
    private Set<Trainer> trainers = new HashSet<>();
	
	
	/**cascade = CascadeType.ALL indica que todas las operaciones (persistencia, actualización, eliminación) en un Trainee se deben propagar a la lista de entrenamientos asociada. Además, orphanRemoval = true permite que los entrenamientos huérfanos (que ya no están asociados a ningún Trainee) se eliminen automáticamente.**/
	@OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Training> trainings = new ArrayList<>();

	
	
	public Trainee() {
		
	}
	public Trainee(Long idTrainee, String adress, Date dateBirth, User user) {
		
		this.idTrainee = idTrainee;
		this.adress = adress;
		this.dateBirth = dateBirth;
		this.user = user;
	}
	public Long getIdTrainee() {
		return idTrainee;
	}
	
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public Date getDateBirth() {
		return dateBirth;
	}
	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	

	 public Set<Trainer> getTrainers() {
	        return trainers;
	    }

	    public void setTrainers(Set<Trainer> trainers) {
	        this.trainers = trainers;
	    }
	
	    
	    public List<Training> getTrainings() {
			return trainings;
		}
		public void setTrainings(List<Training> trainings) {
			this.trainings = trainings;
		}
	@Override
	public String toString() {
		return "Trainee [idTrainee=" + idTrainee + ", adress=" + adress + ", dateBirth=" + dateBirth + ", user=" + user
				+ "]";
	}
	
	
	
	

}
