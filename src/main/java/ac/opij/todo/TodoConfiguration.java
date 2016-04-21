package ac.opij.todo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by dino on 20/04/16.
 */
public class TodoConfiguration extends Configuration {
    @JsonProperty("swagger")
    private SwaggerBundleConfiguration swaggerBundleConfiguration = new SwaggerBundleConfiguration();

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
        return swaggerBundleConfiguration;
    }

    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }
}
