package com.alenma3apps.backendTastyMeal.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alenma3apps.backendTastyMeal.dto.request.ReportRequest;
import com.alenma3apps.backendTastyMeal.dto.response.ValidationResponse;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.ReportModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.security.JwtService;
import com.alenma3apps.backendTastyMeal.services.RecipeService;
import com.alenma3apps.backendTastyMeal.services.ReportService;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase controladora que conté els endpoints de gestió de reports.
 * @author Albert Borras
 */
@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private JwtService jwtService;

    /**
     * Endpoint per afegir un report a la recepta del paràmetre id.
     * @param header Capçalera de la petició http.
     * @param id Id de la recepta a afegir el report.
     * @param request El report.
     * @return El report afegit o en cas d'error retornarà un missatge d'estat.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @PostMapping("/recipe/{id}/report")
    public ResponseEntity<?> addReport(HttpServletRequest header, @PathVariable Long id, @RequestBody ReportRequest request) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);
        if (!validationResponse.isValid()) {
            return SpringResponse.invalidToken();
        }

        String userName = validationResponse.getUsername();
        Optional<UserModel> userOptional = userRepository.findByUsername(userName);
        if (userOptional.isEmpty()) {
            return SpringResponse.userNotFound();
        }

        UserModel user = userOptional.get();

        RecipeModel recipe = recipeService.getRecipeById(id);

        ReportModel report = reportService.createReport(request, user, recipe);

        if (report != null) {
            return ResponseEntity.ok(report);
        } else {
            return SpringResponse.errorReportNotCreated();
        }
    }

    /**
     * Endpoint per obtenir els reports de la recepta del paràmetre id.
     * @param id Id de la recepta a obtenir els reports.
     * @return Llistat dels reports que te la recepta.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @GetMapping("/recipe/{id}/reports")
    public ResponseEntity<?> getReports(@PathVariable Long id) {
        RecipeModel recipe = recipeService.getRecipeById(id);
        List<ReportModel> reports = reportService.getReports(recipe);

        if (reports != null) {
            return ResponseEntity.ok(reports);
        } else {
            return SpringResponse.reportsNotFound();
        }
    }

    /**
     * Endpoint per editar un report passat per id que 
     * sigui de l'usuari que fa la petició.
     * @param header Capçalera de la petició http.
     * @param request Paràmetres per editar el report.
     * @param id Id del report a editar.
     * @return Missatge notificant si s'ha editat o no el report.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @PutMapping("/report/{id}")
    public ResponseEntity<?> editMyReport(HttpServletRequest header, @RequestBody ReportRequest request, @PathVariable Long id) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);
        if (!validationResponse.isValid()) {
            return SpringResponse.invalidToken();
        }

        String userName = validationResponse.getUsername();
        Optional<UserModel> userOptional = userRepository.findByUsername(userName);
        if (userOptional.isEmpty()) {
            return SpringResponse.userNotFound();
        }

        UserModel user = userOptional.get();
        return reportService.editMyReport(id, user, request);
    }

    /**
     * Endpoint per eliminar reports pel paràmetre id
     * que siguin de l'usuari que fa la petició.
     * @param header Capçalera de la petició http.
     * @param id Id del report a eliminar.
     * @return Missatge notificant si s'ha eliminat o no el report.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @DeleteMapping("/report/{id}")
    public ResponseEntity<?> deleteMyReport(HttpServletRequest header, @PathVariable Long id) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);
        if (!validationResponse.isValid()) {
            return SpringResponse.invalidToken();
        }

        String userName = validationResponse.getUsername();
        Optional<UserModel> userOptional = userRepository.findByUsername(userName);
        if (userOptional.isEmpty()) {
            return SpringResponse.userNotFound();
        }

        UserModel user = userOptional.get();

        return reportService.deleteMyReport(id, user);
    }

    /**
     * Endpoint per eliminar un report amb el paràmetre id.
     * @param id Id del report a eliminar.
     * @return Missatge notificant si s'ha eliminat o no el report.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @DeleteMapping("/report/a/{id}")
    public ResponseEntity<?> deleteReportById(@PathVariable Long id) {
        return reportService.deleteReportById(id);
    }
}