import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    List<Patient> getPatients(){
        return patientRepository.findAll();
    }

    Optional<Patient> getPatientByEmail(String email){
        return patientRepository.findByEmail(email);
    }
}
