package com.app.coronavirustracker.model;



public class LocationStats {

    private String state;
    private String country;
    private int latestTotalCases;
    private int diffFromPrevDay;
    private int latestTotalRecoveryCases;

    public int getDiffRecoveryCasesFromPrevDay() {
        return diffRecoveryCasesFromPrevDay;
    }

    public void setDiffRecoveryCasesFromPrevDay(int diffRecoveryCasesFromPrevDay) {
        this.diffRecoveryCasesFromPrevDay = diffRecoveryCasesFromPrevDay;
    }

    private int diffRecoveryCasesFromPrevDay;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                '}';
    }

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public int getLatestTotalRecoveryCases() {
        return latestTotalRecoveryCases;
    }

    public void setLatestTotalRecoveryCases(int latestTotalRecoveryCases) {
        this.latestTotalRecoveryCases = latestTotalRecoveryCases;
    }
}
