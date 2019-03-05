package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@JsonPropertyOrder({"SampleTime", "TimeFrame", "RequestedDataSetType", "ChartPriceType", "Key", "OffSet", "FromDate", "ToDate", "UseDelay", "KeyType", "KeyType2", "Language"})
public class Request {
    @Key
    private String SampleTime;
    @Key
    private String TimeFrame;
    @Key
    private String RequestedDataSetType;
    @Key
    private String ChartPriceType;
    @Key
    private String Key;
    @Key
    private Integer OffSet;
    @Key
    private String FromDate = null;
    @Key
    private String ToDate = null;
    @Key
    private boolean UseDelay;
    @Key
    private String KeyType;
    @Key
    private String KeyType2;
    @Key
    private String Language;
}