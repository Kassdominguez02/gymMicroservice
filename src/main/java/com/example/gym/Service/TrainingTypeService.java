package com.example.gym.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.gym.dto.TrainingTypeDto;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gym.Entity.Trainer;
import com.example.gym.Entity.TrainingType;
import com.example.gym.Repository.TrainingTypeRepository;

@Service
public class TrainingTypeService {
	@Autowired
    private TrainingTypeRepository trainingTypeRepository;
	
	private final MeterRegistry meterRegistry;
	
	@Autowired
    public TrainingTypeService( MeterRegistry meterRegistry) {
       
        this.meterRegistry = meterRegistry;
    }
	
	public List<TrainingTypeDto> getTrainingTypes() {
		
		// Incrementa la métrica por cada llamada al methodo getTrainingTypes()
        Counter.builder("custom_training_type_service_requests")
               .register(meterRegistry)
               .increment();
        
        List<TrainingType> trainingTypes = trainingTypeRepository.findAll();
        return trainingTypes.stream()
                .map(trainingType -> new TrainingTypeDto(trainingType.getIdTrainingType(), trainingType.getTrainingTypeName()))
                .collect(Collectors.toList());
    }
	
	public TrainingType getById (Long trainingTypeId) {
		
		return trainingTypeRepository.findByIdTrainingType(trainingTypeId);
	}

	
	
}
