package com.example.gym.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.gym.Repository.TraineeRepository;
import com.example.gym.Repository.TrainerRepository;
import com.example.gym.Service.TraineeService;
import com.example.gym.Service.TrainerService;
import com.example.gym.Entity.Trainer;
import com.example.gym.Entity.TrainingType;
import com.example.gym.Entity.User;

@SpringBootTest
public class TrainerServiceTest {
	

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerService trainerService;

    @Test
    public void testGenerateUsername() {
        // Configuración de datos de prueba
        String firstName = "John";
        String lastName = "Doe";

        // Lógica para simular que el nombre de usuario ya existe
        Mockito.when(trainerRepository.existsByUser_userName(Mockito.anyString())).thenReturn(true);

        // Ejecutar el método que se está probando
        String generatedUsername = trainerService.generateUsername(firstName, lastName);

        // Verificar que se haya generado un nombre de usuario único
        assertNotEquals("John.Doe", generatedUsername);
        assertTrue(generatedUsername.startsWith("John.Doe"));
    }

    @Test
    public void testGeneratePassword() {
        // Ejecutar el método que se está probando
        String generatedPassword = trainerService.generatePassword();

        // Verificar que se haya generado una contraseña de la longitud correcta
        assertEquals(10, generatedPassword.length());
    }
    
    

    @Test
    public void testReadTrainer() {
        // Configuración de datos de prueba
        Trainer trainer1 = new Trainer();
        Trainer trainer2 = new Trainer();
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer1);
        trainers.add(trainer2);

        // Configuración del comportamiento del repositorio
        when(trainerRepository.findAll()).thenReturn(trainers);

        // Ejecutar el método que se está probando
        List<Trainer> result = trainerService.readTrainer();

        // Verificar que se devolvieron los trainees esperados
        assertEquals(trainers, result);
    }

}
