package com.example.gym.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gym.Entity.Training;
import com.example.gym.Service.TrainingService;
import com.example.gym.dto.DeleteTrainingRequestDTO;
import com.example.gym.dto.TrainingRequestDto;

import jakarta.jms.Message;
import java.util.logging.Logger;

@RestController
@RequestMapping("/gym/profiles/training")
public class TrainingController {
	
	private static final Logger logger = Logger.getLogger(TrainingController.class.getName());
	
	

	@Autowired
    private TrainingService trainingService;
	
	@Autowired
    private JmsTemplate jmsTemplate;
	
	@Autowired
    private MessageConverter jacksonJmsMessageConverter; // Agrega la inyección del convertidor
    

    @PostMapping("/add")
    public ResponseEntity<String> addTraining(@RequestBody TrainingRequestDto trainingDto) {
        try {
            // Llamada al servicio para agregar entrenamiento
            trainingService.addTraining(trainingDto);
            
       
            
            return ResponseEntity.ok("Training added correctly");
        } catch (Exception e) {
            logger.severe("Error al agregar entrenamiento: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTraining(@RequestBody DeleteTrainingRequestDTO request){
    	// Registro de mensajes de depuración
        System.out.println("sending request to delete training. Training ID: {}"+ request.getTrainingId());
      
        
        jmsTemplate.convertAndSend("DeleteQueue", request);
        
    	
        return ResponseEntity.ok("OK");
    }
    

    
    
}
