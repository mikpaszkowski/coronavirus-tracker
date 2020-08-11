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

        int totalCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();

        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();

        int totalNewRecoveryCases = allStatsRecovery.stream().mapToInt(stat -> stat.getDiffRecoveryCasesFromPrevDay()).sum();

        int totalRecoveryCases = allStatsRecovery.stream().mapToInt(stat -> stat.getLatestTotalRecoveryCases()).sum();

        NumberFormat numberFormat = NumberFormat.getInstance();

        model.addAttribute("locationStats", allStats);
        model.addAttribute("locationStatsRecovery", allStatsRecovery);
        model.addAttribute("totalReportedCases", numberFormat.format(totalCases));
        model.addAttribute("totalNewCases", numberFormat.format(totalNewCases));
        model.addAttribute("totalNewRecoveryCases", numberFormat.format(totalNewRecoveryCases));
        model.addAttribute("totalRecoveryCases", numberFormat.format(totalRecoveryCases));

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


}
