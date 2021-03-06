package uk.co.jamesmcguigan.forecaster.facade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class GoogleSheetRepresentation {

    private String range;
    private String majorDimension;
    private List<List<String>> values;

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getMajorDimension() {
        return majorDimension;
    }

    public void setMajorDimension(String majorDimension) {
        this.majorDimension = majorDimension;
    }

    public List<List<String>> getValues() {
        return values;
    }

    public void setValues(List<List<String>> values) {
        this.values = values;
    }
}
