package com.alenma3apps.backendTastyMeal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alenma3apps.backendTastyMeal.dto.request.ReportRequest;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.ReportModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IReportRepository;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse;

/**
 * Classe que gestiona la lògica dels reports.
 * @author Albert Borras
 */
@Service
public class ReportService {
    @Autowired
    private IReportRepository reportRepository;

     /**
     * Mètode per crear un report en una recepta.
     * @param request report.
     * @param user usuari que crea el report.
     * @param recipe recepta on va dirigida el report.
     * @return report creat.
     * @author Albert Borras
     */
    public ReportModel createReport(ReportRequest request, UserModel user, RecipeModel recipe) {
        ReportModel newReport = new ReportModel();
        newReport.setReport(request.getReport());
        newReport.setRecipe(recipe);
        newReport.setUser(user);

        ReportModel reportSaved = reportRepository.save(newReport);

        return reportSaved;
    }

    /**
     * Mètode per obtenir el llistat de reports de la recepta passada per paràmetre.
     * @param recipe Recepta la qual obtenir els seus reports.
     * @return Llistat de reports que tingui la recepta.
     * @author Albert Borras
     */
    public List<ReportModel> getReports(RecipeModel recipe) {
        List<ReportModel> reports;
        reports = reportRepository.findByRecipe(recipe);
        return reports;
    }

     /**
     * Edita el report passat pel paràmetre id.
     * @param reportId Id del report a editar.
     * @param user Usuari que edita el report.
     * @param request Paràmetres del report a editar.
     * @return Missatge confirmant si s'ha editat o no el report.
     */
    public ResponseEntity<?> editMyReport(Long reportId, UserModel user, ReportRequest request ){
        Optional<ReportModel> reportOptional = reportRepository.findById(reportId);
        if (!reportOptional.isPresent()){
            return SpringResponse.reportNotFound();
        }

        ReportModel report = reportOptional.get();
        if (report.getUser() != user) {
            return SpringResponse.notOwnerReport();
        }

        try {
            report.setReport(request.getReport());

            reportRepository.save(report);
            return SpringResponse.reportUpdated();
        } catch (Exception ex) {
            return SpringResponse.errorUpdatingReport();
        }
    }

    /**
     * Elimina un report passat pel paràmetre id que sigui de l'usuari que ho demana.
     * @param reportId Id del report a eliminar.
     * @param user Usuari que ho demana.
     * @return Missatge confirmant si s'ha eliminat o no el report.
     */
    public ResponseEntity<?> deleteMyReport(Long reportId, UserModel user) {
        Optional<ReportModel> reportOptional = reportRepository.findById(reportId);
        if (!reportOptional.isPresent()){
            return SpringResponse.reportNotFound();
        }

        ReportModel report = reportOptional.get();
        if (report.getUser() != user) {
            return SpringResponse.notOwnerReport();
        } else {
            try {
                reportRepository.delete(report);
                return SpringResponse.reportDeleted();
            } catch (Exception ex) {
                return SpringResponse.errorDeletingReport();
            }   
        }  
    }

    /**
     * Elimina un report passat pel paràmetre id.
     * @param reportId Id del report a eliminar.
     * @return Missatge confirmant si s'ha eliminat o no el report.
     */
    public ResponseEntity<?> deleteReportById(Long reportId) {
        Optional<ReportModel> reportOptional = reportRepository.findById(reportId);
        if (!reportOptional.isPresent()){
            return SpringResponse.reportNotFound();
        }

        ReportModel report = reportOptional.get();
        
        try {
            reportRepository.delete(report);
            return SpringResponse.reportDeleted();
        } catch (Exception ex) {
            return SpringResponse.errorDeletingReport();
        }
    }
}