package com.example.backend.controller;

import com.example.backend.entity.Career;
import com.example.backend.entity.Employee;
import com.example.backend.entity.Role;
import com.example.backend.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/careers")
public class CareerController {

    private final CareerService careerService;

    @Autowired
    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    // Créer ou mettre à jour une entrée Career
    @PostMapping
    public Career createOrUpdateCareer(@RequestBody Career career) {
        return careerService.saveCareer(career);
    }

    // Obtenir une Career par ID
    @GetMapping("/{id}")
    public ResponseEntity<Career> getCareerById(@PathVariable Long id) {
        Optional<Career> career = careerService.getCareerById(id);
        return career.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtenir toutes les Careers
    @GetMapping
    public ResponseEntity<List<Career>> getAllCareers() {
        List<Career> careers = careerService.getAllCareers();
        return new ResponseEntity<>(careers, HttpStatus.OK);
    }

    // Supprimer une Career par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCareer(@PathVariable Long id) {
        careerService.deleteCareer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Assigner un employé à un poste
    @PostMapping("/{id}/affectation")
    public ResponseEntity<Career> affectation(
            @PathVariable Long id,
            @RequestBody Employee employee,
            @RequestParam Role role) {
        Optional<Career> careerOptional = careerService.getCareerById(id);
        if (careerOptional.isPresent()) {
            Career career = careerOptional.get();
            careerService.affectation(employee, career, role);
            return new ResponseEntity<>(career, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Ajouter une action disciplinaire à une Career
    @PostMapping("/{id}/discipline")
    public ResponseEntity<Career> manageDiscipline(
            @PathVariable Long id, @RequestParam String discipline) {
        Optional<Career> careerOptional = careerService.getCareerById(id);
        if (careerOptional.isPresent()) {
            Career career = careerOptional.get();
            careerService.manageDiscipline(career, discipline);
            return new ResponseEntity<>(career, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Gérer le départ d'un Employee
    @PostMapping("/{id}/departure")
    public ResponseEntity<Career> handleDeparture(@PathVariable Long id) {
        Optional<Career> careerOptional = careerService.getCareerById(id);
        if (careerOptional.isPresent()) {
            Career career = careerOptional.get();
            careerService.handleDeparture(career);
            return new ResponseEntity<>(career, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
