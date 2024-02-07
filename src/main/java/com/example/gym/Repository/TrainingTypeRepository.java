package com.example.gym.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.gym.Entity.TrainingType;


@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long>{

	
	TrainingType findByIdTrainingType(Long id);
	
	
}