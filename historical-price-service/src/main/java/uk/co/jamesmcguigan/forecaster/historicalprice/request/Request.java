package uk.co.jamesmcguigan.forecaster.historicalprice.request;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
    @com.google.api.client.util.Key
    private String SampleTime;
    @com.google.api.client.util.Key
    private String TimeFrame;
    @com.google.api.client.util.Key
    private String RequestedDataSetType;
    @com.google.api.client.util.Key
    private String ChartPriceType;
    @com.google.api.client.util.Key
    private String Key;
    @com.google.api.client.util.Key
    private Integer OffSet;
    @com.google.api.client.util.Key
    @Builder.Default
    private String FromDate = null;
    @com.google.api.client.util.Key
    @Builder.Default
    private String ToDate = null;
    @com.google.api.client.util.Key
    private boolean UseDelay;
    @com.google.api.client.util.Key
    private String KeyType;
    @com.google.api.client.util.Key
    private String KeyType2;
    @com.google.api.client.util.Key
    private String Language;
}