package com.example.gym.Repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.gym.Entity.Trainee;
import com.example.gym.Entity.Training;
import com.example.gym.Entity.User;




@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long>{


    boolean existsByUser_userName(String userName);
	
    public Trainee findByUser_userName (String userName);
    
    Optional<Trainee> findByUser(User userid);
    

   
    
}