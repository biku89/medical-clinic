package com.biku89.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {
    private DoctorService doctorService;

    @GetMapping
    public List<DoctorDTO> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{email}")
    public ResponseEntity<DoctorDTO> getDoctorByEmail(@PathVariable("email")String email){
        return doctorService.getDoctorByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DoctorDTO addDoctor(@RequestBody DoctorDTO doctor){
        return doctorService.addDoctor(doctor);
    }

    @PutMapping("/{email}")
    public DoctorDTO updateDoctor(@PathVariable("email") String email, @RequestBody DoctorUpdateCommand doctorUpdate){
        return doctorService.updateDoctor(email, doctorUpdate);
    }

    @DeleteMapping("/email")
    public void deleteDoctorByEmail(@PathVariable("email")String email){
        doctorService.deleteDoctorByEmail(email);
    }
}
