package com.example.gym.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gym.Entity.Trainee;
import com.example.gym.Entity.Trainer;
import com.example.gym.Entity.Training;
import com.example.gym.Entity.User;
import com.example.gym.Exception.TraineeNotFoundException;
import com.example.gym.Exception.TrainerNotFoundException;
import com.example.gym.Exception.UnauthorizedException;
import com.example.gym.dto.TraineeDto;
import com.example.gym.dto.TraineeProfileDto;
import com.example.gym.dto.TraineeResponseDto;
import com.example.gym.dto.TraineeUpdateDto;
import com.example.gym.dto.TrainerProfileDto;
import com.example.gym.dto.TrainerResponseDto;
import com.example.gym.dto.TrainerUpdateDto;
import com.example.gym.dto.ResponseDto;

import jakarta.transaction.Transactional;

import com.example.gym.Repository.TraineeRepository;
import com.example.gym.Repository.TrainerRepository;
import com.example.gym.Repository.TrainingRepository;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.Optional;


@Service
public class TraineeService {
	
	
	private static final Logger transactionLogger = Logger.getLogger("TransactionLogger");
    private static final Logger operationLogger = Logger.getLogger("OperationLogger");

    private String currentTransactionId;
	

	
	private TraineeRepository traineeRepository;

	@Autowired
	public TraineeService(TraineeRepository traineeRepository) {
		
		this.traineeRepository = traineeRepository;
	}
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private TrainingRepository trainingRepository;
	
	@Autowired
    private TrainerRepository trainerRepository;
	
	//Save Trainee
	public void saveTrainee(Trainee trainee) {
        traineeRepository.save(trainee);
        
    }
	
	

    public Trainee findByUserName(String username){
        User user = userService.findOneByUsername(username).orElse(new User());
        return this.findByUserid(user).orElse(new Trainee());
    }
	
	
	 public Optional<Trainee> findByUserid(User user){
	        return traineeRepository.findByUser(user);
	    }


	public List<Trainee>readTrainee(){
		return traineeRepository.findAll();
		}
	//delete by id
public Trainee deleteTraineeById(Long idTrainee) throws TraineeNotFoundException{
		
		
		Trainee temp=null;
		
		if (traineeRepository.existsById(idTrainee)){
			temp=traineeRepository.findById(idTrainee).get();
			traineeRepository.deleteById(idTrainee);;
			
			
		}else {
			throw new TraineeNotFoundException("Trainee not found with ID: " + idTrainee);
		}
		
		return temp;
	}//delete


//Generate Username
public String generateUsername(String firstName, String lastName) {
    String baseUsername = firstName + "." + lastName;
    int serialNumber = 1;
    while (usernameExists(baseUsername + serialNumber)) {
        serialNumber++;
    }
    return baseUsername + serialNumber;
}

private boolean usernameExists(String username) {
    return traineeRepository.existsByUser_userName(username);
}

//Genertate Password
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

//1.- create trainee
@Transactional
public ResponseDto createTraineeProfile(TraineeDto traineeDTO) {
	// Generar userName y password
    String generatedUsername = generateUsername(traineeDTO.getFirstName(), traineeDTO.getLastName());
    String generatedPassword = generatePassword();
    
 // Generar un ID de transacción único
    String transactionId = generateTransactionId();

    // Establecer la transacción actual
    this.currentTransactionId = transactionId;

    try {
    	
    	// Log a nivel de transacción
        transactionLogger.log(Level.INFO, "Transaction {0} started", transactionId);

    // Crear un nuevo usuario
    User user = new User();
    user.setFirstName(traineeDTO.getFirstName());
    user.setLastName(traineeDTO.getLastName());
    user.setUserName(generatedUsername);
    user.setPassword(generatedPassword);
    user.setActive(true); 

    // Guardar el usuario
    User savedUser = userService.saveUser(user);

    // Crear un nuevo Trainee
    Trainee trainee = new Trainee();
    trainee.setUser(savedUser);
    trainee.setAdress(traineeDTO.getAddress());
    trainee.setDateBirth(traineeDTO.getDateBirth());
    
    
    // Guardar el Trainee
    traineeRepository.save(trainee);
    
 // Crear y devolver el DTO de respuesta
    ResponseDto responseDto = new ResponseDto();
    responseDto.setUsername(savedUser.getUserName());
    responseDto.setPassword(user.getPassword());

    return responseDto;
    }catch (Exception e){
    	// Log de error a nivel de transacción
        transactionLogger.log(Level.SEVERE, "Transaction {0} failed", transactionId );

        
        throw e;
    } finally {
        // Limpiar la transacción actual después de su uso
        this.currentTransactionId = null;
    }
    	
    	
    
    
}


private String generateTransactionId() {
    
    return UUID.randomUUID().toString();
}
// 3.- validate credencials

public boolean validateTraineeCredentials(String username, String password) {
	 
    Trainee trainee = traineeRepository.findByUser_userName(username);
    return trainee != null && trainee.getUser().getPassword().equals(password);
}



//4.- change login or password
public void changeTraineePassword(String username, String currentPassword, String newPassword)throws UnauthorizedException {
    boolean isValid = validateTraineeCredentials(username, currentPassword);
    if (isValid) {
        Trainee trainee = traineeRepository.findByUser_userName(username);
        trainee.getUser().setPassword(newPassword);
        traineeRepository.save(trainee);
    } else {
        throw new UnauthorizedException("Invalid Trainee credentials");
    }
}

//5.-GET Trainee profile

public TraineeResponseDto getTraineeProfile(String username)  {
   
        // Obtener el trainee por nombre de usuario
        Trainee trainee = traineeRepository.findByUser_userName(username);

        // Crear la respuesta de perfil del trainee
        TraineeResponseDto traineeProfile = new TraineeResponseDto();
        traineeProfile.setFirstName(trainee.getUser().getFirstName());
        traineeProfile.setLastName(trainee.getUser().getLastName());
        traineeProfile.setDateOfBirth(trainee.getDateBirth());
        traineeProfile.setAddress(trainee.getAdress());
        traineeProfile.setActive(trainee.getUser().isActive());

        // Obtener la lista de entrenadores
        Set<Trainer> trainers = trainee.getTrainers();
        List<TrainerResponseDto> trainerResponses = new ArrayList<>();

        for (Trainer trainer : trainers) {
            TrainerResponseDto trainerResponse = new TrainerResponseDto();
            trainerResponse.setUsername(trainer.getUser().getUserName());
            trainerResponse.setFirstName(trainer.getUser().getFirstName());
            trainerResponse.setLastName(trainer.getUser().getLastName());
            trainerResponse.setSpecialization(trainer.getTrainingType().getTrainingTypeName());

            trainerResponses.add(trainerResponse);
        }

        traineeProfile.setTrainers(trainerResponses);

        return traineeProfile;
    
}



//6.-Update Trainee profile

public TraineeProfileDto updateTraineeProfile(String username, TraineeUpdateDto traineeUpdateDto) throws TraineeNotFoundException {
  Trainee trainee = traineeRepository.findByUser_userName(username);
  if (trainee != null) {
      // Actualizar los atributos del entrenador
      trainee.getUser().setFirstName(traineeUpdateDto.getFirstName());
      trainee.getUser().setLastName(traineeUpdateDto.getLastName());
      trainee.getUser().setActive(traineeUpdateDto.isActive());

      // Guardar el entrenador actualizado
      Trainee updatedTrainee = traineeRepository.save(trainee);

      return convertToTraineeProfileDto(updatedTrainee);
  } else {
      // Manejar el caso en el que el entrenador no se encuentra
      throw new TraineeNotFoundException("Trainer not found for username: " + username);
  }
}


private TraineeProfileDto convertToTraineeProfileDto(Trainee trainee) {
    TraineeProfileDto profileDto = new TraineeProfileDto();
    profileDto.setFirstName(trainee.getUser().getFirstName());
    profileDto.setLastName(trainee.getUser().getLastName());
    profileDto.setDateOfBirth(trainee.getDateBirth());
    profileDto.setAddress(trainee.getAdress());
    profileDto.setActive(trainee.getUser().isActive());

    // Convertir la lista de trainers a TrainerDto y establecerla en el DTO del perfil del trainee
    List<TrainerResponseDto> trainerDtos = convertTrainersToTrainerDtos(trainee.getTrainers());
    profileDto.setTrainers(trainerDtos);

    return profileDto;
}

private List<TrainerResponseDto> convertTrainersToTrainerDtos(Set<Trainer> trainers) {
    return trainers.stream()
            .map(trainer -> {
                TrainerResponseDto trainerDto = new TrainerResponseDto();
               
                trainerDto.setFirstName(trainer.getUser().getFirstName());
                trainerDto.setLastName(trainer.getUser().getLastName());
                
                return trainerDto;
            })
            .collect(Collectors.toList());
}


//Activate or deactivate Trainee

public Trainee activateOrDeactivateTrainee(String username, String currentPassword, boolean activate)throws UnauthorizedException  {
    boolean isValid = validateTraineeCredentials(username, currentPassword);
    
    System.out.println("el valor de validar credenciales activate metodo es"+ isValid);
    if (isValid) {
        Trainee existingTrainee = traineeRepository.findByUser_userName(username);
        existingTrainee.getUser().setActive(activate);
        return traineeRepository.save(existingTrainee);
    } else {
        throw new UnauthorizedException("Invalid Trainee credentials");
    }
}

// Delete Trainee by Username

@Transactional
public void deleteTraineeProfileByUsername(String username, String currentPassword) throws UnauthorizedException{
    boolean isValid = validateTraineeCredentials(username, currentPassword);
    if (isValid) {
        Trainee trainee = traineeRepository.findByUser_userName(username);
        
     // Obtener la lista de entrenamientos asociados al trainee y eliminarlos
        List<Training> trainings = trainee.getTrainings();
        trainings.forEach(training -> trainingRepository.delete(training));
        
        // Eliminar el trainee
        traineeRepository.delete(trainee);
    } else {
        throw new UnauthorizedException("Invalid Trainee credentials");
    }
}

//get Training list in Trainee
public List<Training> getTraineeTrainingsByUsernameAndName(String username, String trainingName) {
    Trainee trainee = traineeRepository.findByUser_userName(username);
    if (trainee != null) {
        return trainingRepository.findByTraineeUserUserNameAndTrainingName(trainee, trainingName);
    }
    return Collections.emptyList();
}
// Get not assigned on specific trainee active trainers list.

public List<Trainer> getNotAssignedActiveTrainers(Long traineeId) throws TraineeNotFoundException {
    Trainee trainee = traineeRepository.findById(traineeId)
            .orElseThrow(() -> new TraineeNotFoundException("Trainee not found with ID: " + traineeId));

    return trainerRepository.findNotAssignedActiveTrainers(trainee);
}


//Update Trainee's trainers list

public void updateTraineeTrainersList(Long traineeId, List<Long> trainerIds) throws TraineeNotFoundException {
    Trainee trainee = traineeRepository.findById(traineeId)
            .orElseThrow(() -> new TraineeNotFoundException("Trainee not found with ID: " + traineeId));

    // Obtener la lista de entrenadores seleccionados
    List<Trainer> trainers = trainerRepository.findAllById(trainerIds);

    // Actualizar la lista de entrenadores del trainee
    trainee.getTrainers().clear(); // Limpiar la lista actual
    trainee.getTrainers().addAll(trainers);

    // Guardar el trainee actualizado en la base de datos
    traineeRepository.save(trainee);
}
}
