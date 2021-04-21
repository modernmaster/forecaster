package uk.co.jamesmcguigan.forecaster.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import uk.co.jamesmcguigan.forecaster.repository.StockProjection;
import uk.co.jamesmcguigan.forecaster.stock.Stock;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

//    @Autowired
//    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration repositoryRestConfiguration) {
        ExposureConfiguration config = repositoryRestConfiguration.getExposureConfiguration();
        config.forDomainType(Stock.class).withItemExposure((metadata, httpMethods) ->
                httpMethods.disable(HttpMethod.DELETE));
//        repositoryRestConfiguration.exposeIdsFor(
//                entityManager.getMetamodel().getEntities().stream()
//                        .map(Type::getJavaType)
//                        .toArray(Class[]::new));
        repositoryRestConfiguration.getProjectionConfiguration()
                .addProjection(StockProjection.class);
    }

}

