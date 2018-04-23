package uk.co.jamesmcguigan.forecaster.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {

    private @Id
    @GeneratedValue
    Long id;
    private String admissionDate;
    private String companyName;
    private String symbol;
    private String icbIndustry;
    private String icbSuperSector;
    private String countryOfIncorporation;
    private String worldRegion;
    private String market;
    private String internationalIssuer;
    private String companyMarketCap;
    private String price;
    private String percentageChange;
    private String volume;
    private String avgVolume;
    private String pe;
    private String high52;
    private String low52;
    private String delay;

    public Stock() {}

    public Stock(String admissionDate, String companyName, String symbol, String icbIndustry, String icbSuperSector, String countryOfIncorporation, String worldRegion, String market, String internationalIssuer, String companyMarketCap, String price, String percentageChange, String avgVolume, String volume, String pe, String high52, String low52, String delay) {
        this.admissionDate = admissionDate;
        this.companyName = companyName;
        this.symbol = symbol;
        this.icbIndustry = icbIndustry;
        this.icbSuperSector = icbSuperSector;
        this.countryOfIncorporation = countryOfIncorporation;
        this.worldRegion = worldRegion;
        this.market = market;
        this.internationalIssuer = internationalIssuer;
        this.companyMarketCap = companyMarketCap;
        this.price = price;
        this.percentageChange = percentageChange;
        this.volume = volume;
        this.avgVolume = avgVolume;
        this.pe = pe;
        this.high52 = high52;
        this.low52 = low52;
        this.delay = delay;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getIcbIndustry() {
        return icbIndustry;
    }

    public String getIcbSuperSector() {
        return icbSuperSector;
    }

    public String getCountryOfIncorporation() {
        return countryOfIncorporation;
    }

    public String getWorldRegion() {
        return worldRegion;
    }

    public String getMarket() {
        return market;
    }

    public String getInternationalIssuer() {
        return internationalIssuer;
    }

    public String getCompanyMarketCap() {
        return companyMarketCap;
    }

    public String getPrice() {
        return price;
    }

    public String getPercentageChange() {
        return percentageChange;
    }

    public String getVolume() {
        return volume;
    }

    public String getAvgVolume() {
        return avgVolume;
    }

    public String getPe() {
        return pe;
    }

    public String getHigh52() {
        return high52;
    }

    public String getLow52() {
        return low52;
    }

    public String getDelay() {
        return delay;
    }

    public String getSymbol() {
        return symbol;
    }
}
