package com.example.gym.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gym.Entity.Trainee;
import com.example.gym.Entity.Trainer;
import com.example.gym.Entity.Training;
import com.example.gym.Entity.TrainingType;
import com.example.gym.Repository.TrainingTypeRepository;
import com.example.gym.Service.TraineeService;
import com.example.gym.Service.TrainerService;
import com.example.gym.Service.TrainingTypeService;
import com.example.gym.dto.TraineeDto;
import com.example.gym.dto.TraineeProfileDto;
import com.example.gym.dto.TraineeResponseDto;
import com.example.gym.dto.TraineeUpdateDto;
import com.example.gym.dto.ResponseDto;
import com.example.gym.dto.TrainerDto;
import com.example.gym.dto.TrainerProfileDto;
import com.example.gym.dto.TrainerTrainingsResponseDto;
import com.example.gym.dto.TrainerUpdateDto;
import com.example.gym.dto.TrainingTypeDto;
import com.example.gym.dto.TrainerTrainingsRequestDto;
import com.example.gym.Exception.TraineeNotFoundException;
import com.example.gym.Exception.TrainerNotFoundException;
import com.example.gym.Exception.TrainingTypeNotFoundException;
import com.example.gym.Exception.UnauthorizedException;


@RestController
@CrossOrigin
@RequestMapping("/gym/profiles")
public class ProfileController {
	
	@Autowired
    private TraineeService traineeService;
	
	@Autowired
    private TrainerService trainerService;
	
	@Autowired
    private TrainingTypeRepository trainingTypeRepository;
	
	@Autowired
    private TrainingTypeService trainingTypeService;

	@Autowired
    public void setTraineeService(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

	
	
	
	//1.-Trainee registration

    @PostMapping("/create-trainee")
    public ResponseEntity<ResponseDto> createTraineeProfile(@RequestBody TraineeDto traineeDTO) {
         ResponseDto responseDto =traineeService.createTraineeProfile(traineeDTO);
        return ResponseEntity.ok(responseDto);
    }
    
    
  //home
  	@GetMapping("/home")
  	public String home(){
  		return "Hello";
  	}
  	
  
    //2.-Trainer resgistration
    
    @PostMapping("/create-trainer")
    public ResponseEntity<ResponseDto> createTrainerProfile(@RequestBody TrainerDto trainerDto, @RequestParam Long idTrainingType) throws TrainingTypeNotFoundException {
        
    	ResponseDto responseDto=trainerService.createTrainerProfile(trainerDto, idTrainingType);
        return ResponseEntity.ok(responseDto);
    }

    
    
    
    //3.- Login 
    
    @GetMapping("/trainee/login")
    public ResponseEntity<String> loginTrainee(
            @RequestParam String username,
            @RequestParam String password
    ) {
        boolean isValidCredentials = traineeService.validateTraineeCredentials(username, password);
        
        

        if (isValidCredentials) {
            return ResponseEntity.ok("Login successful"); 
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


   
    //4.-change password
    
    @PostMapping("/trainee/change-password")
    public ResponseEntity<String> changeTraineePassword(
            @RequestParam String username,
            @RequestParam String currentPassword,
            @RequestParam String newPassword
    ) throws UnauthorizedException {
        traineeService.changeTraineePassword(username, currentPassword, newPassword);
        return ResponseEntity.ok("Trainee password changed successfully");
    }
    
    @PostMapping("/trainer/change-password")
    public ResponseEntity<String> changeTrainerPassword(
            @RequestParam String username,
            @RequestParam String currentPassword,
            @RequestParam String newPassword
    ) throws UnauthorizedException {
        trainerService.changeTrainerPassword(username, currentPassword, newPassword);
        return ResponseEntity.ok("Trainer password changed successfully");
    }
    
    // 5.-GET trainee profile
    
    @GetMapping("/trainee/")
    public ResponseEntity<TraineeResponseDto> getTraineeProfile(
    		@RequestParam String username
            
            
    ) {
        TraineeResponseDto traineeProfile = traineeService.getTraineeProfile(username);
        return ResponseEntity.ok(traineeProfile);
    }

    
    
    //6.-Update Trainee profile
    
    @PutMapping("/trainee/update")
    public ResponseEntity<TraineeProfileDto> updateTraineeProfile(
            @RequestParam String username,
            @RequestBody TraineeUpdateDto traineeUpdateDto) throws TraineeNotFoundException {
        TraineeProfileDto updatedProfile = traineeService.updateTraineeProfile(username, traineeUpdateDto);
        return ResponseEntity.ok(updatedProfile);
    }
    
    //7.- Delete Trainee profile
    
    @DeleteMapping("/trainee/delete")
    public ResponseEntity<String> deleteTraineeProfileByUsername(
            @RequestParam String username,
            @RequestParam String currentPassword
    ) throws UnauthorizedException {
        traineeService.deleteTraineeProfileByUsername(username, currentPassword);
        return ResponseEntity.ok("Trainee profile deleted successfully");
    }
    
    
    
    // 8.- Get Trainer Profile
    
    @GetMapping("/trainer/{username}")
    public ResponseEntity<TrainerProfileDto> getTrainerProfile(@PathVariable String username) throws TrainerNotFoundException {
        TrainerProfileDto trainerProfile = trainerService.getTrainerProfile(username);
        return ResponseEntity.ok(trainerProfile);
    }
    
    
    //9.-Update Trainer
    
    @PutMapping("/trainer/update")
    public ResponseEntity<TrainerProfileDto> updateTrainerProfile(
            @RequestParam String username,
            @RequestBody TrainerUpdateDto trainerUpdateDto) throws TrainerNotFoundException {
        TrainerProfileDto updatedProfile = trainerService.updateTrainerProfile(username, trainerUpdateDto);
        return ResponseEntity.ok(updatedProfile);
    }
    
    
 
 
   //10.- Get not assigned on specific trainee active trainers list
    
    @GetMapping("/{traineeId}/not-assigned-active-trainers")
    public ResponseEntity<List<Trainer>> getNotAssignedActiveTrainers(@PathVariable Long traineeId) throws TraineeNotFoundException {
        List<Trainer> notAssignedActiveTrainers = traineeService.getNotAssignedActiveTrainers(traineeId);
        return ResponseEntity.ok(notAssignedActiveTrainers);
    }
   
    
    //11.- Update Trainee's trainers list
    
    @PutMapping("/{traineeId}/update-trainers-list")
    public ResponseEntity<String> updateTraineeTrainersList(
            @PathVariable Long traineeId,
            @RequestBody List<Long> trainerIds
    ) throws TraineeNotFoundException {
        traineeService.updateTraineeTrainersList(traineeId, trainerIds);
        return ResponseEntity.ok("Trainee's trainers list updated successfully");
    }

    // 12.-Get Trainee training list by criteria
    
    @GetMapping("/trainee/trainings/{username}")
    public ResponseEntity<List<Training>> getTraineeTrainingsByUsernameAndName(
            @PathVariable String username,
            @RequestParam(required = false) String trainingName) {
        List<Training> trainings = traineeService.getTraineeTrainingsByUsernameAndName(username, trainingName);
        return ResponseEntity.ok(trainings);
    }
    
  
    
    //13.- GET Trainer training list:
    
    @GetMapping("/trainer/trainings/{username}")
    public ResponseEntity<List<TrainerTrainingsResponseDto>> getTrainerTrainingsList(
            @PathVariable String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date periodFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date periodTo,
            @RequestParam(required = false) String traineeName) throws TrainerNotFoundException {

        TrainerTrainingsRequestDto requestDto = new TrainerTrainingsRequestDto(username, periodFrom, periodTo, traineeName);
        List<TrainerTrainingsResponseDto> trainerTrainingsList = trainerService.getTrainerTrainingsList(requestDto);

        return ResponseEntity.ok(trainerTrainingsList);
    }
    
    
    
    
  //14.- Add training in trainer
    @PostMapping("/trainer/trainings")
    public ResponseEntity<String> addTraining(
            @RequestParam Long trainerId,
            @RequestBody Training training
    ) throws TrainerNotFoundException {
        trainerService.addTraining(trainerId, training);
        return ResponseEntity.ok("Training added successfully");
    }
    
    
  //15.- Deactivate Trainee
    
    // Activate Trainee
    @PutMapping("/trainee/activate")
    public ResponseEntity<Trainee> activateTrainee(
            @RequestParam String username,
            @RequestParam String currentPassword
    ) throws UnauthorizedException {
        Trainee trainee = traineeService.activateOrDeactivateTrainee(username, currentPassword, true);
        return ResponseEntity.ok(trainee);
    }

   
    @PutMapping("/trainee/deactivate")
    public ResponseEntity<Trainee> deactivateTrainee(
            @RequestParam String username,
            @RequestParam String currentPassword
    ) throws UnauthorizedException {
        Trainee trainee = traineeService.activateOrDeactivateTrainee(username, currentPassword, false);
        return ResponseEntity.ok(trainee);
    }

    //16.-Activate Trainer
    @PutMapping("/trainer/activate")
    public ResponseEntity<Trainer> activateTrainer(
            @RequestParam String username,
            @RequestParam String currentPassword
    ) throws UnauthorizedException {
        Trainer trainer = trainerService.activateOrDeactivateTrainer(username, currentPassword, true);
        return ResponseEntity.ok(trainer);
    }

    //Deactivate Trainer
    @PutMapping("/trainer/deactivate")
    public ResponseEntity<Trainer> deactivateTrainer(
            @RequestParam String username,
            @RequestParam String currentPassword
    ) throws UnauthorizedException {
        Trainer trainer = trainerService.activateOrDeactivateTrainer(username, currentPassword, false);
        return ResponseEntity.ok(trainer);
    }
    
    // 17.- GET training types
    
    @GetMapping("/training-types")
    public ResponseEntity<List<TrainingTypeDto>> getTrainingTypes() {
        List<TrainingTypeDto> trainingTypes = trainingTypeService.getTrainingTypes();
        return new ResponseEntity<>(trainingTypes, HttpStatus.OK);
    }
    
}
