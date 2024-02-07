package com.example.gym.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gym.Entity.Trainee;
import com.example.gym.Entity.Trainer;
import com.example.gym.Entity.Training;


@Repository
public interface TrainingRepository extends JpaRepository<Training, Long>{

	 List<Training> findByTraineeUserUserNameAndTrainingName(Trainee trainee, String trainingName);
	 
	 List<Training> findAllByTrainee(Trainee trainee);
	    List<Training> findAllByTrainer(Trainer trainer);
	    
	 
	 
	 
	
}