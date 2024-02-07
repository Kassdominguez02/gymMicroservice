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
import com.example.gym.Service.TraineeService;

import com.example.gym.Entity.Trainee;

@SpringBootTest
public class TraineeServiceTest {
	

    @Mock
    private TraineeRepository traineeRepository;

    @InjectMocks
    private TraineeService traineeService;

    @Test
    public void testGenerateUsername() {
        // Configuración de datos de prueba
        String firstName = "John";
        String lastName = "Doe";

        
        // Lógica para simular que el nombre de usuario ya existe
        Mockito.when(traineeRepository.existsByUser_userName(Mockito.anyString())).thenReturn(true);

        // Ejecutar el método que se está probando
        String generatedUsername = traineeService.generateUsername(firstName, lastName);

        // Verificar que se haya generado un nombre de usuario único
        assertNotEquals("John.Doe1", generatedUsername);
        assertTrue(generatedUsername.startsWith("John.Doe"));
    }

    @Test
    public void testGeneratePassword() {
        // Ejecutar el método que se está probando
        String generatedPassword = traineeService.generatePassword();

        // Verificar que se haya generado una contraseña de la longitud correcta
        assertEquals(10, generatedPassword.length());
    }
    
    @Test
    public void testSaveTrainee() {
        // Configuración de datos de prueba
        Trainee trainee = new Trainee();
        trainee.setAdress("merida");

        // Configuración del comportamiento del repositorio
        when(traineeRepository.save(trainee)).thenReturn(trainee);

        // Ejecutar el método que se está probando
        traineeService.saveTrainee(trainee);

        // Verificar que el método save del repositorio fue llamado
        verify(traineeRepository, times(1)).save(trainee);
    }

    @Test
    public void testReadTrainee() {
        // Configuración de datos de prueba
        Trainee trainee1 = new Trainee();
        Trainee trainee2 = new Trainee();
        List<Trainee> trainees = new ArrayList<>();
        trainees.add(trainee1);
        trainees.add(trainee2);

        // Configuración del comportamiento del repositorio
        when(traineeRepository.findAll()).thenReturn(trainees);

        // Ejecutar el método que se está probando
        List<Trainee> result = traineeService.readTrainee();

        // Verificar que se devolvieron los trainees esperados
        assertEquals(trainees, result);
    }

}
