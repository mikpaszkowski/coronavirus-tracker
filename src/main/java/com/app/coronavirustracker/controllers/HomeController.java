package com.app.coronavirustracker.controllers;


import com.app.coronavirustracker.model.LocationStats;
import com.app.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.NumberFormat;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService = new CoronaVirusDataService();

    @GetMapping("/")
    public String home(Model model){

        List<LocationStats> allStats = coronaVirusDataService.getAllStats();

        List<LocationStats> allStatsRecovery = coronaVirusDataService.getAllStatsRecovery();

        List<LocationStats> allStatsDeaths = coronaVirusDataService.getAllStatsDeaths();

        int totalCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();

        int totalNewRecoveryCases = allStatsRecovery.stream().mapToInt(stat -> stat.getDiffRecoveryCasesFromPrevDay()).sum();
        int totalRecoveryCases = allStatsRecovery.stream().mapToInt(stat -> stat.getLatestTotalRecoveryCases()).sum();

        int totalDeathCases = allStatsDeaths.stream().mapToInt(stat -> stat.getLatestTotalDeathCases()).sum();
        int totalNewDeathCases  = allStatsDeaths.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();


        NumberFormat numberFormat = NumberFormat.getInstance();

        model.addAttribute("totalReportedCases", numberFormat.format(totalCases));
        model.addAttribute("totalNewCases", numberFormat.format(totalNewCases));
        model.addAttribute("totalNewRecoveryCases", numberFormat.format(totalNewRecoveryCases));
        model.addAttribute("totalRecoveryCases", numberFormat.format(totalRecoveryCases));
        model.addAttribute("totalDeathCases", numberFormat.format(totalDeathCases));
        model.addAttribute("totalNewDeathCases", numberFormat.format(totalNewDeathCases));


        return "home";
    }

    @GetMapping("/reported_cases")
    public String reportedCases(Model model){

        List<LocationStats> allStats = coronaVirusDataService.getAllStats();

        List<LocationStats> allStatsRecovery = coronaVirusDataService.getAllStatsRecovery();

        NumberFormat numberFormat = NumberFormat.getInstance();

        model.addAttribute("locationStats", allStats);
        model.addAttribute("locationStatsRecovery", allStatsRecovery);


        return "reportedCases";
    }

    @GetMapping("/reported_cases_recovery")
    public String reportedCasesRecovery(Model model){

        List<LocationStats> allStatsRecovery = coronaVirusDataService.getAllStatsRecovery();

        model.addAttribute("locationStatsRecovery", allStatsRecovery);


        return "reportedCasesRecovery";
    }

    @GetMapping("/daily_report")
    public String dailyReport(Model model){

        List<LocationStats> allStatsRecovery = coronaVirusDataService.getAllStatsRecovery();
        List<LocationStats> allStatsDeaths = coronaVirusDataService.getAllStatsDeaths();
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();


        LocationStats MaxDeathsLocationStats = findTheMaximum(allStatsDeaths, "death");
        LocationStats MaxRecoveryLocationStats = findTheMaximum(allStatsRecovery, "recovery");
        LocationStats MaxConfirmedLocationStats = findTheMaximum(allStats, "confirmed");

        model.addAttribute("maxRecovery", MaxRecoveryLocationStats);
        model.addAttribute("maxDeaths", MaxDeathsLocationStats);
        model.addAttribute("maxConfirmed", MaxConfirmedLocationStats);

        return "dailyReport";
    }

    @GetMapping("/reported_cases_deaths")
    public String reportedCasesDeaths(Model model){

        List<LocationStats> allStatsDeaths = coronaVirusDataService.getAllStatsDeaths();;

        model.addAttribute("locationStatsDeaths", allStatsDeaths);

        return "reportedCasesDeaths";
    }


    public LocationStats findTheMaximum(List<LocationStats> locationStats, String recoveryOrDeaths){

        int temp = 0;
        int a = 0;
        int b = 0;

        LocationStats maxLocationStats = new LocationStats();

        switch (recoveryOrDeaths){

            case "recovery":

                int maxRecovery = locationStats.get(0).getDiffRecoveryCasesFromPrevDay();

                for(int i = 1; i < locationStats.size(); i++){

                    if(locationStats.get(i).getDiffRecoveryCasesFromPrevDay() > maxRecovery){
                        maxRecovery = locationStats.get(i).getDiffRecoveryCasesFromPrevDay();
                        maxLocationStats = locationStats.get(i);
                    }
                }
                break;

            case "death":

                int maxDeath = locationStats.get(0).getDiffFromPrevDay();

                for(int i = 1; i < locationStats.size(); i++){

                    if(locationStats.get(i).getDiffFromPrevDay() > maxDeath){
                        System.out.println(locationStats.get(i).getDiffFromPrevDay());
                        maxDeath = locationStats.get(i).getDiffFromPrevDay();
                        maxLocationStats = locationStats.get(i);
                    }
                }
                break;

            case "confirmed":

                int maxConfirmed = locationStats.get(0).getDiffFromPrevDay();
                for(int i = 1; i < locationStats.size(); i++){

                    if(locationStats.get(i).getDiffFromPrevDay() > maxConfirmed){
                        maxConfirmed = locationStats.get(i).getDiffFromPrevDay();
                        maxLocationStats = locationStats.get(i);
                    }
                }
                break;
            default:
                System.out.println("There is no such word-case in the function.");
                System.out.println("Write these three cases : ");
                System.out.println("recovery\ndeath\nconfirmed");
                return null;
        }
        return maxLocationStats;
    }


}
