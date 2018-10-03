package org.wicaksono.rest.nosql.servlet;

import com.google.inject.Injector;
import com.mongodb.MongoClient;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * @author cluttered.code@gmail.com
 */
public class ApiApplication extends ResourceConfig {

    public ApiApplication() {
        register(new ContainerLifecycleListener() {
            public void onStartup(Container container) {
                ServletContainer servletContainer = (ServletContainer)container;
                ServiceLocator serviceLocator = container.getApplicationHandler().getServiceLocator();
                GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
                GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
                Injector injector = (Injector) servletContainer.getServletContext().getAttribute(Injector.class.getName());
                guiceBridge.bridgeGuiceInjector(injector);

//                final Morphia morphia = new Morphia();
//
//                // tell Morphia where to find your classes
//                // can be called multiple times with different packages or classes
//                morphia.mapPackage("com.clutteredcode.rest.model");
//
//                // create the Datastore connecting to the default port on the local host
//                final Datastore datastore = morphia.createDatastore(new MongoClient("localhost", 27017), "morphia_example");
//                datastore.ensureIndexes();

            }
            public void onReload(Container container) {
            }
            public void onShutdown(Container container) {
            }
        });

        packages("com.clutteredcode.rest.resource");
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
    }
}