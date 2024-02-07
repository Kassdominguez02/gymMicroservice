package com.example.gym.Repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.gym.Entity.Trainee;
import com.example.gym.Entity.Trainer;
import com.example.gym.Entity.Training;
import com.example.gym.Entity.User;



@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long>{

	boolean existsByUser_userName(String userName);
	
	public Trainer findByUser_userName (String userName);
	
	
	@Query("SELECT t FROM Trainer t JOIN t.user u WHERE u.isActive = true AND :trainee NOT MEMBER OF t.trainees")
	List<Trainer> findNotAssignedActiveTrainers(@Param("trainee") Trainee trainee);
	
	 Optional<Trainer> findByUser(User userid);

	
}
