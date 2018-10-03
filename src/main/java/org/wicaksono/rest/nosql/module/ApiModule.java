package org.wicaksono.rest.nosql.module;

import org.wicaksono.rest.nosql.config.ConfigurationManager;
import org.wicaksono.rest.nosql.dao.ConnectionManager;
import org.wicaksono.rest.nosql.filter.CrossOriginResourceSharingFilter;
import org.wicaksono.rest.nosql.servlet.ApiApplication;
import org.wicaksono.rest.nosql.servlet.SwaggerBootstrap;
import com.google.inject.servlet.ServletModule;
import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cluttered.code@gmail.com
 */
public class ApiModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(ConnectionManager.class).asEagerSingleton();
        bind(ServletContainer.class).asEagerSingleton();
        bind(CrossOriginResourceSharingFilter.class).asEagerSingleton();

        initializeApplicationServlet();
        initializeSwaggerBootstrap();

        // CORS Filter
        filter("/*").through(CrossOriginResourceSharingFilter.class);
    }

    private void initializeApplicationServlet() {
        final Map<String, String> props = new HashMap<>();
        props.put("javax.ws.rs.Application", ApiApplication.class.getName());
        props.put("jersey.config.server.wadl.disableWadl", "true");
        serve("/v1/*").with(ServletContainer.class, props);
    }

    private void initializeSwaggerBootstrap() {
        serve("").with(SwaggerBootstrap.class);
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0-SNAPSHOT");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setTitle("Todo API");
        beanConfig.setDescription("Description of Todo API");
        beanConfig.setHost(ConfigurationManager.getInstance().getAppConfigData().host() + ":" + ConfigurationManager.getInstance().getAppConfigData().port().toString());
        beanConfig.setBasePath("/api/v1");
        beanConfig.setResourcePackage("org.wicaksono.rest.nosql.resource");
        beanConfig.setScan(true);
    }
}