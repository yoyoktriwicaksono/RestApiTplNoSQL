package org.wicaksono.rest.nosql;

import org.wicaksono.rest.nosql.config.ConfigurationManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ApiServer implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ApiServer.class);

    private final int port;
    private final Server server;

    public ApiServer(final int port) {
        this.port = port;

        String resourceBaseApi = "src/main/swagger";
        if (Files.notExists(Paths.get(resourceBaseApi))) {
            resourceBaseApi = ApiServer.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
        }

        final String xmlDescriptor = ApiServer.class.getResource("/WEB-INF/web.xml").toExternalForm();

        final WebAppContext webAppContextApi = new WebAppContext(resourceBaseApi, "/api/");
        webAppContextApi.setDescriptor(xmlDescriptor);

        String resourceBaseUI = "src/main/ui";
        if (Files.notExists(Paths.get(resourceBaseUI))) {
            resourceBaseUI = ApiServer.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
        }
        final WebAppContext webAppContextUI = new WebAppContext(resourceBaseUI, "/");

        server = new Server(port);

        final HandlerList handlers = new HandlerList();
        handlers.addHandler( webAppContextApi );
        handlers.addHandler( webAppContextUI );

        server.setHandler(handlers);
    }

    @Override
    public void run() {
        try {
            server.start();
            LOG.info("Server listening on localhost:{}", port);
            server.join();
        } catch (final InterruptedException ie) {
            LOG.warn("Server was Interrupted");
        } catch (final Exception e) {
            LOG.error("Server was unable to start", e);
        }
    }

    public void stop() throws Exception {
        LOG.info("Stopping Server");
        server.stop();
    }

    public static void main(final String[] args) {
        final ApiServer server = new ApiServer(
                ConfigurationManager.
                        getInstance().
                        getAppConfigData().
                        port()
        );
        server.run();
    }
}