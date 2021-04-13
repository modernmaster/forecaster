package uk.co.jamesmcguigan.forecaster.stock;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import uk.co.jamesmcguigan.forecaster.dao.GoogleSheetRepresentation;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class StockTransformerTest {

    private final List<List<String>> values = new ArrayList<>();
    private final List<List<String>> values_na = new ArrayList<>();
    private StockTransformer stockTransformer;

    @Before
    public void setUp() {
        stockTransformer = new StockTransformer();
        List<String> header = Lists.newArrayList("Admission Date",
                "Company Name",
                "Symbol",
                "ICB Industry",
                "ICB Super-Sector",
                "Country of Incorporation",
                "World Region",
                "Market",
                "International Issuer",
                "Company Market Cap (£m)",
                "Price",
                "% Change",
                "Avg Volume",
                "Volume",
                "PE",
                "High52",
                "Low52",
                "Delay");
        List<String> content = Lists.newArrayList(
                "02/08/2006",
                "1PM PLC",
                "LON:OPM",
                "Financials",
                "Financial Services",
                "United Kingdom",
                "Europe",
                "AIM ",
                "No",
                "£39,063,467.00",
                "47.3",
                "0.64",
                "126042",
                "8953",
                "8.49",
                "62",
                "39.75",
                "0");
        List<String> content_na = Lists.newArrayList(
                "02/08/2006",
                "1PM PLC",
                "LON:OPM",
                "Financials",
                "Financial Services",
                "United Kingdom",
                "Europe",
                "AIM ",
                "No",
                "£39,063,467.00",
                "47.3",
                "0.64",
                "126042",
                "8953",
                "",
                "62",
                "39.75",
                "0");
        values.add(header);
        values.add(content);
        values_na.add(header);
        values_na.add(content_na);
    }

    @Test
    public void testTransformFromGoogleRepresentationToStock() {
        GoogleSheetRepresentation googleSheetRepresentation = new GoogleSheetRepresentation();
        googleSheetRepresentation.setMajorDimension("dimension");
        googleSheetRepresentation.setRange("range");
        googleSheetRepresentation.setValues(values);
        List<Stock> stocks = stockTransformer.transform(googleSheetRepresentation);
        assertNotNull(stocks);
        assertThat(stocks.size(), equalTo(1));
        Stock stock = stocks.get(0);
        assertThat(stock.getAdmissionDate(), equalTo("02/08/2006"));
        assertThat(stock.getAvgVolume(), equalTo("126042"));
        assertThat(stock.getCompanyMarketCap(), equalTo("£39,063,467.00"));
        assertThat(stock.getCompanyName(), equalTo("1PM PLC"));
        assertThat(stock.getCountryOfIncorporation(), equalTo("United Kingdom"));
        assertThat(stock.getDelay(), equalTo("0"));
        assertThat(stock.getHigh52(), equalTo("62"));
        assertThat(stock.getIcbIndustry(), equalTo("Financials"));
        assertThat(stock.getIcbSuperSector(), equalTo("Financial Services"));
        assertThat(stock.getInternationalIssuer(), equalTo("No"));
        assertThat(stock.getLow52(), equalTo("39.75"));
        assertThat(stock.getMarket(), equalTo("AIM "));
        assertThat(stock.getPe(), equalTo("8.49"));
        assertThat(stock.getPercentageChange(), equalTo("0.64"));
        assertThat(stock.getPrice(), equalTo("47.3"));
        assertThat(stock.getSymbol(), equalTo("LON:OPM"));
        assertThat(stock.getVolume(), equalTo("8953"));
        assertThat(stock.getWorldRegion(), equalTo("Europe"));
    }

    @Test
    public void testNAInputForTransformFromGoogleRepresentationToStock() {
        GoogleSheetRepresentation googleSheetRepresentation = new GoogleSheetRepresentation();
        googleSheetRepresentation.setMajorDimension("dimension");
        googleSheetRepresentation.setRange("range");
        googleSheetRepresentation.setValues(values_na);
        List<Stock> stocks = stockTransformer.transform(googleSheetRepresentation);
        assertNotNull(stocks);
        assertThat(stocks.size(), equalTo(1));
        Stock stock = stocks.get(0);
        assertThat(stock.getAdmissionDate(), equalTo("02/08/2006"));
        assertThat(stock.getAvgVolume(), equalTo("126042"));
        assertThat(stock.getCompanyMarketCap(), equalTo("£39,063,467.00"));
        assertThat(stock.getCompanyName(), equalTo("1PM PLC"));
        assertThat(stock.getCountryOfIncorporation(), equalTo("United Kingdom"));
        assertThat(stock.getDelay(), equalTo("0"));
        assertThat(stock.getHigh52(), equalTo("62"));
        assertThat(stock.getIcbIndustry(), equalTo("Financials"));
        assertThat(stock.getIcbSuperSector(), equalTo("Financial Services"));
        assertThat(stock.getInternationalIssuer(), equalTo("No"));
        assertThat(stock.getLow52(), equalTo("39.75"));
        assertThat(stock.getMarket(), equalTo("AIM "));
        assertThat(stock.getPe(), is(""));
        assertThat(stock.getPercentageChange(), equalTo("0.64"));
        assertThat(stock.getPrice(), equalTo("47.3"));
        assertThat(stock.getSymbol(), equalTo("LON:OPM"));
        assertThat(stock.getVolume(), equalTo("8953"));
        assertThat(stock.getWorldRegion(), equalTo("Europe"));
    }

}
