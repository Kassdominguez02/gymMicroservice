package com.example.gym.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gym.Entity.Trainee;
import com.example.gym.Entity.Trainer;
import com.example.gym.Entity.Training;
import com.example.gym.Entity.TrainingType;
import com.example.gym.Entity.User;

import com.example.gym.Exception.TrainerNotFoundException;
import com.example.gym.Exception.UnauthorizedException;
import com.example.gym.Exception.TrainingTypeNotFoundException;
import com.example.gym.Repository.TrainerRepository;
import com.example.gym.Repository.TrainingRepository;
import com.example.gym.Repository.TrainingTypeRepository;
import com.example.gym.dto.ResponseDto;
import com.example.gym.dto.TraineeDto;
import com.example.gym.dto.TrainerDto;
import com.example.gym.dto.TrainerProfileDto;
import com.example.gym.dto.TrainerTrainingsRequestDto;
import com.example.gym.dto.TrainerTrainingsResponseDto;
import com.example.gym.dto.TrainerUpdateDto;

import jakarta.transaction.Transactional;

@Service
public class TrainerService {
	
	private TrainerRepository trainerRepository;

	@Autowired
	public TrainerService(TrainerRepository trainerRepository) {
		
		this.trainerRepository = trainerRepository;
	}
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private TrainingRepository trainingRepository;
	
	@Autowired
    private TrainingTypeRepository trainingTypeRepository;


	
	public Trainer findTrainerByUserName(String username){
        return trainerRepository.findByUser_userName(username);
    }
	
	public void saveTrainer(Trainer trainer) {
        trainerRepository.save(trainer);
    }
	
	
	public List<Trainer>readTrainer(){
		return trainerRepository.findAll();
		}
	
	 public Optional<Trainer> findByUserid(User user) {
		 return trainerRepository.findByUser(user);}
	 

	    public Trainer findByUserName(String username) {
	        User user = userService.findOneByUsername(username).orElse(new User());
	        return this.findByUserid(user).orElse(new Trainer());
	    }
	
public Trainer deleteTrainerById(Long idTrainer) throws TrainerNotFoundException{
		
		
		Trainer temp=null;
		
		if (trainerRepository.existsById(idTrainer)){
			temp=trainerRepository.findById(idTrainer).get();
			trainerRepository.deleteById(idTrainer);;
		}else {
			throw new TrainerNotFoundException("Trainee not found with ID: " + idTrainer);
		}
		
		return temp;
		
	}//delete
	
public Trainer updateTrainer(Long idTrainer,User user, TrainingType trainingType)throws TrainerNotFoundException {
    Trainer trainer = trainerRepository.findById(idTrainer)
        .orElseThrow(() -> new TrainerNotFoundException("Trainer not found with ID: " + idTrainer));
    
    trainer.setUser(user);
    trainer.setTrainingType(trainingType);
 
    
    return trainerRepository.save(trainer);


}

public Optional<Trainer> getById (Long trainerId) {
	
	return trainerRepository.findById(trainerId);
}

public String generateUsername(String firstName, String lastName) {
    String baseUsername = firstName + "." + lastName;
    int serialNumber = 1;
    while (usernameExists(baseUsername + serialNumber)) {
        serialNumber++;
    }
    return baseUsername + serialNumber;
}

private boolean usernameExists(String username) {
    return trainerRepository.existsByUser_userName(username);
}

public String generatePassword() {
    
	int length=10;
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuilder password = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
        password.append(characters.charAt(random.nextInt(characters.length())));
    }

    return password.toString();
}

// 1.- crear perfil

@Transactional
public ResponseDto createTrainerProfile(TrainerDto trainerDTO, Long idTrainingType) throws TrainingTypeNotFoundException {
    // Generar userName y password
    String generatedUsername = generateUsername(trainerDTO.getFirstName(), trainerDTO.getLastName());
    String generatedPassword = generatePassword();

    // Crear un nuevo usuario
    User user = new User();
    user.setFirstName(trainerDTO.getFirstName());
    user.setLastName(trainerDTO.getLastName());
    user.setUserName(generatedUsername);
    user.setPassword(generatedPassword);
    user.setActive(true);

    // Guardar el usuario
    User savedUser = userService.saveUser(user);
    
 // Obtener el tipo de entrenamiento existente por ID
    TrainingType trainingType = trainingTypeRepository.findById(idTrainingType)
            .orElseThrow(() -> new TrainingTypeNotFoundException("TrainingType not found with id: " + idTrainingType));

    // Crear un nuevo Trainer
    Trainer trainer = new Trainer();
    trainer.setUser(savedUser);
    trainer.setTrainingType(trainingType);

    // Guardar el Trainer
    trainerRepository.save(trainer);
    
 // Crear y devolver el DTO de respuesta
    ResponseDto responseDto = new ResponseDto();
    responseDto.setUsername(savedUser.getUserName());
    responseDto.setPassword(user.getPassword());

    return responseDto;
}


// validar las credenciales

public boolean validateTrainerCredentials(String username, String password) {
    Trainer trainer = trainerRepository.findByUser_userName(username);
    return trainer != null && trainer.getUser().getPassword().equals(password);
}



//3.- change password
public void changeTrainerPassword(String username, String currentPassword, String newPassword)throws UnauthorizedException  {
    boolean isValid = validateTrainerCredentials(username, currentPassword);
    if (isValid) {
        Trainer trainer = trainerRepository.findByUser_userName(username);
        trainer.getUser().setPassword(newPassword);
        trainerRepository.save(trainer);
    } else {
        throw new UnauthorizedException("Invalid Trainer credentials");
    }
}

//8.-GET Trainer Profile
    public TrainerProfileDto getTrainerProfile(String username) throws TrainerNotFoundException {
        Trainer trainer = trainerRepository.findByUser_userName(username);
        if (trainer != null) {
            return convertToTrainerProfileDto(trainer);
        } else {
            
            throw new TrainerNotFoundException("Trainer not found for username: " + username);
        }
    }

    private TrainerProfileDto convertToTrainerProfileDto(Trainer trainer) {
        TrainerProfileDto profileDto = new TrainerProfileDto();
        profileDto.setFirstName(trainer.getUser().getFirstName());
        profileDto.setLastName(trainer.getUser().getLastName());
        profileDto.setSpecialization(trainer.getTrainingType().getTrainingTypeName());
        profileDto.setActive(trainer.getUser().isActive());

        // Convertir la lista de trainees a TraineeDto y establecerla en el DTO del perfil del entrenador
        List<TraineeDto> traineeDtos = convertTraineesToTraineeDtos(trainer.getTrainees());
        profileDto.setTrainees(traineeDtos);

        return profileDto;
    }

    private List<TraineeDto> convertTraineesToTraineeDtos(Set<Trainee> trainees) {
        return trainees.stream()
                .map(trainee -> {
                    TraineeDto traineeDto = new TraineeDto();
                   
                    traineeDto.setFirstName(trainee.getUser().getFirstName());
                    traineeDto.setLastName(trainee.getUser().getLastName());
                    
                    return traineeDto;
                })
                .collect(Collectors.toList());
    }



//9.-update profile
    
    public TrainerProfileDto updateTrainerProfile(String username, TrainerUpdateDto trainerUpdateDto) throws TrainerNotFoundException {
        Trainer trainer = trainerRepository.findByUser_userName(username);
        if (trainer != null) {
            // Actualizar los atributos del entrenador
            trainer.getUser().setFirstName(trainerUpdateDto.getFirstName());
            trainer.getUser().setLastName(trainerUpdateDto.getLastName());
            trainer.getUser().setActive(trainerUpdateDto.isActive());

            // Guardar el entrenador actualizado
            Trainer updatedTrainer = trainerRepository.save(trainer);

            return convertToTrainerProfileDto(updatedTrainer);
        } else {
            // Manejar el caso en el que el entrenador no se encuentra
            throw new TrainerNotFoundException("Trainer not found for username: " + username);
        }
    }
    
//13.-Get Trainer trainings
    
    public List<TrainerTrainingsResponseDto> getTrainerTrainingsList(TrainerTrainingsRequestDto requestDto) throws TrainerNotFoundException {
        Trainer trainer = trainerRepository.findByUser_userName(requestDto.getUsername());

        if (trainer == null) {
            // Manejo de error si el entrenador no se encuentra
            throw new TrainerNotFoundException("Trainer not found");
        }

        List<TrainerTrainingsResponseDto> trainerTrainingsList = new ArrayList<>();

        // Lógica para obtener la lista de entrenamientos del entrenador
        // Supongamos que la entidad Trainer tiene una relación con Training
        List<Training> trainings = trainer.getTrainings();

        for (Training training : trainings) {
            // Filtrar por fecha si se proporcionan periodFrom y/o periodTo
            if ((requestDto.getPeriodFrom() == null || !training.getTrainingDate().before(requestDto.getPeriodFrom()))
                    && (requestDto.getPeriodTo() == null || !training.getTrainingDate().after(requestDto.getPeriodTo()))
                    && (requestDto.getTraineeName() == null || training.getTrainee().getUser().getUserName().equals(requestDto.getTraineeName()))) {

                // Crear un DTO de entrenamiento y agregarlo a la lista
                TrainerTrainingsResponseDto trainingDto = new TrainerTrainingsResponseDto();
                trainingDto.setTrainingName(training.getTrainingName());
                trainingDto.setTrainingDate(training.getTrainingDate());
                trainingDto.setTrainingType(training.getTrainingType().getTrainingTypeName());
                trainingDto.setTrainingDuration(training.getTrainingDuration());
                trainingDto.setTraineeName(training.getTrainee().getUser().getUserName());

                trainerTrainingsList.add(trainingDto);
            }
        }

        return trainerTrainingsList;
    }

// 15.- Activate or deactivate Trainer

public Trainer activateOrDeactivateTrainer(String username, String currentPassword, boolean activate)throws UnauthorizedException {
    boolean isValid = validateTrainerCredentials(username, currentPassword);
    if (isValid) {
        Trainer existingTrainer = trainerRepository.findByUser_userName(username);
        existingTrainer.getUser().setActive(activate);
        return trainerRepository.save(existingTrainer);
    } else {
        throw new UnauthorizedException("Invalid Trainer credentials");
    }
}

//get Training list in Trainer


//Add a training

public void addTraining(Long trainerId, Training training) throws TrainerNotFoundException {
    Trainer trainer = trainerRepository.findById(trainerId)
            .orElseThrow(() -> new TrainerNotFoundException("Trainer not found with ID: " + trainerId));

    // Establecer la relación entre el entrenamiento y el entrenador
    training.setTrainer(trainer);

    // Guardar el entrenamiento en la base de datos
    trainingRepository.save(training);
}

}
