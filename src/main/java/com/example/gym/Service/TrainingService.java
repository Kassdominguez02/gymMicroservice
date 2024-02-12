package com.example.gym.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.gym.Entity.Trainee;
import com.example.gym.Entity.Trainer;
import com.example.gym.Entity.Training;
import com.example.gym.Entity.TrainingType;

import com.example.gym.Exception.TrainingNotFoundException;
import com.example.gym.Repository.TraineeRepository;
import com.example.gym.Repository.TrainerRepository;
import com.example.gym.Repository.TrainingRepository;
import com.example.gym.Repository.TrainingTypeRepository;
import com.example.gym.dto.TrainingRequestDto;



import java.util.logging.Logger;

@Service
public class TrainingService {
	
	
private static final Logger logger = Logger.getLogger(TrainingService.class.getName());

@Autowired
	private TrainingRepository trainingRepository;
	
	@Autowired
	private TraineeRepository traineeRepository;
	
	@Autowired
	private TrainerRepository trainerRepository;
	
	@Autowired
	private TrainingTypeRepository trainingTypeRepository;
	

	@Autowired
	private TraineeService traineeService;
	
	@Autowired
	private TrainerService trainerService;
	
	@Autowired
	private TrainingTypeService trainingTypeService;
	
	@Autowired
    private JmsTemplate jmsTemplate;
	
	//private final RestTemplate restTemplate;

    /**@Autowired
    public TrainingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }**/
	
	
	
	


	
	
	
	
	
	
	public void addTraining( TrainingRequestDto trainingDto ) {
		
        
        
        Training training = new Training();
        Trainee trainee = new Trainee();
        trainee = traineeService.findByUserName(trainingDto.getTraineeUsername());
        training.setTrainee(trainee);
        Trainer trainer = new Trainer();
        trainer=trainerService.findByUserName(trainingDto.getTrainerUsername());
        training.setTrainer(trainer);
        
        
        
        TrainingType trainingType = createDefaultTrainingType();
        training.setTrainingType(trainingType);
        

        System.out.println("Valor de trainer: " + trainer);
        
  
        System.out.println("Valor de trainee: " + trainee);
        
  
        System.out.println("Valor de training type: " + trainingType );
        
        System.out.println("Valor de training type ID: " + trainingType.getIdTrainingType());
        System.out.println("Valor de training type name: " + trainingType.getTrainingTypeName());
        
        training.setTrainingDate(trainingDto.getTrainingDate());
        training.setTrainingDuration(trainingDto.getTrainingDuration());
        training.setTrainingName(trainingDto.getTrainingName());
                
        
        // Guardar el nuevo entrenamiento en la base de datos
        trainingRepository.save(training);
        
    /** // Llamada al microservicio secundario
        String secondaryServiceUrl = "http://localhost:8085/trainer/workload";
        restTemplate.postForObject(secondaryServiceUrl, trainingDto, String.class);
    **/
        
        
     // Enviar mensaje a la cola de ActiveMQ
        jmsTemplate.convertAndSend("trainingQueue", trainingDto);
       
    }
	
    
       
	public TrainingType createDefaultTrainingType() {
		
		Long id=4L;
		String name="streching";
		
		TrainingType trainingType =new TrainingType(id,name);
		
		return trainingType;
		
	    
	}

	
	
	public List<Training>readTraining(){
		return trainingRepository.findAll();
		}
	

	
public Training updateTraining(Long idTraining, String trainingName, Date trainingDate, Trainee trainee, Trainer trainer, TrainingType trainingType)throws TrainingNotFoundException {
    Training training = trainingRepository.findById(idTraining)
        .orElseThrow(() -> new TrainingNotFoundException("Training not found with ID: " + idTraining));
    
    training.setTrainingName(trainingName);
    training.setTrainingDate(trainingDate);
    training.setTrainer(trainer);
    training.setTrainee(trainee);
    training.setTrainingType(trainingType);
    
    
 
    
    return trainingRepository.save(training);
    
    


}

}
