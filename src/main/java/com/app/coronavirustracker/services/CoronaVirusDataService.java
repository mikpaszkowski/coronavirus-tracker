package com.app.coronavirustracker.services;

//25960
import com.app.coronavirustracker.model.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


@Service
public class CoronaVirusDataService {

    private final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private final String VIRUS_DATA_URL_RECOVERY = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";
    private final String VIRUS_DATA_URL_DEATHS = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";


    private List<LocationStats> allStats = new ArrayList<>();
    private List<LocationStats> allStatsRecovery = new ArrayList<>();
    private List<LocationStats> allStatsDeaths = new ArrayList<>();

    public List<LocationStats> getAllStatsDeaths() {
        return allStatsDeaths;
    }

    public void setAllStatsDeaths(List<LocationStats> allStatsDeaths) {
        this.allStatsDeaths = allStatsDeaths;
    }


    @PostConstruct
    @Scheduled(cron = "0 0 5,12 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {


        List<LocationStats> newStats = new ArrayList<>();


        //READING DATA FOR CONFIRMED CASES OF CORONAVIRUS
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());

        //parsing CSV file with HEADER AUTO DETECTION
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : records) {

            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            locationStats.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));



            int latestTotalCases = Integer.parseInt(record.get(record.size()-1));
            int differenceFromPrevDay = latestTotalCases - Integer.parseInt(record.get(record.size() - 2));

            locationStats.setDiffFromPrevDay(differenceFromPrevDay);
            newStats.add(locationStats);
        }
        this.allStats = newStats;
    }


    @PostConstruct
    @Scheduled(cron = "0 0 5,12 * * *")
    public void fetchVirusDataRecovery() throws IOException, InterruptedException {

        List<LocationStats> newRecoveryStats = new ArrayList<>();

        //READING DATA FROM DIFFERENT IRL ADDRESS TO GAIN NUMBER OF RECOVERIES
        HttpClient httpClient1 = HttpClient.newHttpClient();
        HttpRequest httpRequest1 = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL_RECOVERY))
                .build();
        HttpResponse<String> httpResponse = httpClient1.send(httpRequest1, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();

            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            //setting value of latestTotalRecoveryCases in LocationStats class
            locationStats.setLatestTotalRecoveryCases(Integer.parseInt(record.get(record.size()-1)));

            int latestTotalRecoveryCases = Integer.parseInt(record.get(record.size()-1));
            int differenceFromPrevDay = latestTotalRecoveryCases - Integer.parseInt(record.get(record.size() - 2));

            locationStats.setDiffRecoveryCasesFromPrevDay(differenceFromPrevDay);
            newRecoveryStats.add(locationStats);
        }
        this.allStatsRecovery = newRecoveryStats;
    }

    @PostConstruct
    @Scheduled(cron = "0 0 5,12 * * *")
    public void fetchVirusDataDeathCases() throws IOException, InterruptedException {

        List<LocationStats> newDeathStats = new ArrayList<>();

        //READING DATA FROM DIFFERENT IRL ADDRESS TO GAIN NUMBER OF RECOVERIES
        HttpClient httpClient1 = HttpClient.newHttpClient();
        HttpRequest httpRequest1 = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL_DEATHS))
                .build();
        HttpResponse<String> httpResponse = httpClient1.send(httpRequest1, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();

            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));

            //setting value of latestTotalRecoveryCases in LocationStats class
            locationStats.setLatestTotalDeathCases(Integer.parseInt(record.get(record.size()-1)));

            int latestTotalRecoveryCases = Integer.parseInt(record.get(record.size()-1));
            int differenceFromPrevDay = latestTotalRecoveryCases - Integer.parseInt(record.get(record.size() - 2));

            locationStats.setDiffFromPrevDay(differenceFromPrevDay);
            newDeathStats.add(locationStats);
        }
        this.allStatsDeaths= newDeathStats;
    }

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    public void setAllStats(List<LocationStats> allStats) {
        this.allStats = allStats;
    }

    public List<LocationStats> getAllStatsRecovery() {
        return allStatsRecovery;
    }

    public void setAllStatsRecovery(List<LocationStats> allStatsRecovery) {
        this.allStatsRecovery = allStatsRecovery;
    }
}
