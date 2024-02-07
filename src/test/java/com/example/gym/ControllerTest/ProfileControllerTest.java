package com.example.gym.ControllerTest;

import com.example.gym.Controller.ProfileController;
import com.example.gym.Repository.TrainingTypeRepository;
import com.example.gym.dto.TraineeDto;
import com.example.gym.dto.TrainerDto;
import com.example.gym.dto.TraineeUpdateDto;
import com.example.gym.dto.TrainerUpdateDto;
import com.example.gym.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.gym.Service.TraineeService;
import com.example.gym.Service.TrainerService;
import com.example.gym.Service.TrainingTypeService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;


@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper sirve para convertir objetos a JSON

    @MockBean
    private TraineeService traineeService;
    
    @MockBean
    private TrainerService trainerService;
    
    
    @MockBean
    private TrainingTypeRepository trainingTypeRepository;
	
    @MockBean
    private TrainingTypeService trainingTypeService;
    
    

    @Test
    public void testCreateTraineeProfile() throws Exception {
    	
    	
        // Datos de ejemplo para el cuerpo de la solicitud
        TraineeDto traineeDto = new TraineeDto();
        traineeDto.setFirstName("name");
        traineeDto.setLastName("lastname");
        traineeDto.setAddress("adress");
        
     // Crea un objeto Date para la fecha de nacimiento
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = dateFormat.parse("1987-12-03");

        // Establece la fecha de nacimiento en el TraineeDto
        traineeDto.setDateBirth(dateOfBirth);
        
        
        // Respuesta esperada del servicio
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMensaje("Registro exitoso");

        // Configuración del servicio mock
        Mockito.when(traineeService.createTraineeProfile(Mockito.any(TraineeDto.class)))
               .thenReturn(responseDto);

        // Realiza la solicitud POST y espera una respuesta exitosa (200)
        mockMvc.perform(MockMvcRequestBuilders.post("/gym/profiles/trainee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(traineeDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(responseDto)));
    }
    
    
    @Test
    public void testCreateTrainerProfile() throws Exception {
    	
    	// Datos de ejemplo para el cuerpo de la solicitud
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setFirstName("name");
        trainerDto.setLastName("lastName");
        
     // Id de tipo de entrenamiento
        Long idTrainingType = 4L;
        
        
        
     // Respuesta esperada del servicio
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMensaje("Perfil del entrenador creado exitosamente");
        
        // Configuración del servicio mock
        Mockito.when(trainerService.createTrainerProfile(trainerDto, idTrainingType))
               .thenReturn(responseDto);

        // Realiza la solicitud POST y espera una respuesta exitosa (200)
        mockMvc.perform(MockMvcRequestBuilders.post("/gym/profiles/trainer")
                .param("idTrainingType", idTrainingType.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainerDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
                
    }
    
    @Test
    public void testLoginTraineeSuccess() throws Exception {
    	
    	String username = "usuario1";
        String password = "contraseña1";
    	Mockito.when(traineeService.validateTraineeCredentials(username, password)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/gym/profiles/trainee/login")
                .param("username", username)
                .param("password", password))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Login successful"));
                
    }

    @Test
    public void testLoginTraineeFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/gym/profiles/trainee/login")
                .param("username", "invalidUsername")
                .param("password", "invalidPassword")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Invalid credentials"));
    }
    
    
    @Test
    public void testChangeTraineePasswordSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/gym/profiles/trainee/change-password")
                .param("username", "user")
                .param("currentPassword", "exrSdI43YZ")
                .param("newPassword", "exrSdI43YC"))
                .andExpect(MockMvcResultMatchers.status().isOk());
            
    }
    
    @Test
    public void testGetTraineeProfileSuccess() throws Exception {
        // Definir el nombre de usuario para la prueba
        String username = "testUser";

        // Simular la llamada al endpoint y verificar la respuesta
        mockMvc.perform(MockMvcRequestBuilders.get("/gym/profiles/trainee/")
                .param("username", username)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
                
    }
    
    
    @Test
    public void testGetTraineeProfileFailure() throws Exception {
        // Definir el nombre de usuario para la prueba
        String username = null;

        // Simular la llamada al endpoint y verificar la respuesta
        mockMvc.perform(MockMvcRequestBuilders.get("/gym/profiles/trainee/")
                .param("username", username)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
                
    }
    
    @Test
    public void testUpdateTraineeProfile_Success() throws Exception {
        String validUsername = "testUser";
        TraineeUpdateDto updateDto = new TraineeUpdateDto();
        
        updateDto.setAddress("adress");
        updateDto.setFirstName("name");
        updateDto.setLastName("Lastname");
        updateDto.setActive(true);
        
        
        // Crea un objeto Date para la fecha de nacimiento
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           Date dateOfBirth = dateFormat.parse("1987-12-03");

        updateDto.setDateBirth(dateOfBirth);

        mockMvc.perform(MockMvcRequestBuilders.put("/gym/profiles/trainee/update")
                .param("username", validUsername)
                .content(objectMapper.writeValueAsString(updateDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
                
    }
    
    @Test
    public void testUpdateTraineeProfile_Failure() throws Exception {
        String validUsername = null;
        TraineeUpdateDto updateDto = new TraineeUpdateDto();
        
        updateDto.setAddress("adress");
        updateDto.setFirstName("name");
        updateDto.setLastName("Lastname");
        updateDto.setActive(true);
        
        
        // Crea un objeto Date para la fecha de nacimiento
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           Date dateOfBirth = dateFormat.parse("1987-12-03");

        updateDto.setDateBirth(dateOfBirth);

        mockMvc.perform(MockMvcRequestBuilders.put("/gym/profiles/trainee/update")
                .param("username", validUsername)
                .content(objectMapper.writeValueAsString(updateDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
                
    }
    
    //7.-Detele Trainee
    
    @Test
    public void testDeleteTraineeProfile_Success() throws Exception {
        String validUsername = "testUser";
        String validPassword = "validPassword";

        mockMvc.perform(MockMvcRequestBuilders.delete("/gym/profiles/trainee/delete")
                .param("username", validUsername)
                .param("currentPassword", validPassword)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Trainee profile deleted successfully"));
    }
    
    @Test
    public void testDeleteTraineeProfile_Failure() throws Exception {
        String invalidUsername = "nonExistentUser";
        String invalidPassword = null;
        
        Mockito.when(traineeService.validateTraineeCredentials(invalidUsername, invalidPassword)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/gym/profiles/trainee/delete")
                .param("username", invalidUsername)
                .param("currentPassword", invalidPassword)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    // / 8.- Get Trainer Profile
    
    @Test
    public void testGetTrainerProfile_Success() throws Exception {
        String validUsername = "existingTrainer";

        mockMvc.perform(MockMvcRequestBuilders.get("/gym/profiles/trainer/{username}", validUsername)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
                
    }
    
    @Test
    public void testGetTrainerProfile_TrainerNotFound() throws Exception {
        String username=null;
        String password= "password";
        
      

        mockMvc.perform(MockMvcRequestBuilders.get("/gym/profiles/trainer/{username}", username)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    //9-.Update Trainer
    
    @Test
    public void testUpdateTrainerProfile_Success() throws Exception {
        String validUsername = "existingTrainer";
        TrainerUpdateDto updateDto = new TrainerUpdateDto();
        updateDto.setFirstName("name");
        updateDto.setLastName("lastname");
        updateDto.setActive(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/gym/profiles/trainer/update")
                .param("username", validUsername)
                .content(objectMapper.writeValueAsString(updateDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
                
    }
    
    @Test
    public void testUpdateTrainerProfile_Failure() throws Exception {
        String validUsername = null;
        TrainerUpdateDto updateDto = new TrainerUpdateDto();
        updateDto.setFirstName("name");
        updateDto.setLastName("lastname");
        updateDto.setActive(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/gym/profiles/trainer/update")
                .param("username", validUsername)
                .content(objectMapper.writeValueAsString(updateDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
                
    }
    
    // Update Trainee's trainers list
    @Test
    public void testUpdateTraineeTrainersList_Success() throws Exception {
        Long validTraineeId = 1L;
        List<Long> validTrainerIds = Arrays.asList(2L, 3L, 4L);

        mockMvc.perform(MockMvcRequestBuilders.put("/gym/profiles/{traineeId}/update-trainers-list", validTraineeId)
                .content(objectMapper.writeValueAsString(validTrainerIds))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Trainee's trainers list updated successfully"));
    }

}
    

	

