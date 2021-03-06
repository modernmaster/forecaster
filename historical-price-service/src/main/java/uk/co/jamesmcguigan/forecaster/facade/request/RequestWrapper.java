package uk.co.jamesmcguigan.forecaster.facade.request;

import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestWrapper {

    @Key
    Request request;
}
