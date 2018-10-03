package org.wicaksono.rest.nosql.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.classpath.ClasspathConfigurationSource;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by Yoyok_T on 28/09/2018.
 */
public class ConfigurationManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);

    static final Object singletonLockObject = new Object();
    static ConfigurationManager instance;
    private AppConfigData appConfigData = null;
    private MongoConfigData mongoConfigData = null;

    private ConfigurationManager(){
        // Specify which files to load. Configuration from both files will be merged.
        ConfigFilesProvider configFilesProvider = () -> Arrays.asList(
                Paths.get("app.properties"),
                Paths.get("database.properties")
                // ,
                // Paths.get("mysql.properties"),
                // Paths.get("mongo.properties")
        );

        // Use classpath as configuration store
        ConfigurationSource source = new ClasspathConfigurationSource(configFilesProvider);

        // Create provider
        ConfigurationProvider configProvider = new ConfigurationProviderBuilder()
                .withConfigurationSource(source)
                .build();

        logger.info("Load app.properties");
        appConfigData = configProvider.bind("app", AppConfigData.class);
        logger.info("Load database.properties");
        mongoConfigData = configProvider.bind("mongo", MongoConfigData.class);
        // this.mysqlConfigData = configProvider.bind("mysql", MysqlConfigData.class);
        // this.mongoConfigData = configProvider.bind("app", MongoConfigData.class);
    }

    public static ConfigurationManager getInstance(){
        if( instance == null ){
            synchronized (singletonLockObject){
                if( instance == null ){
                    logger.info("Load Configuration Manager");
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    public AppConfigData getAppConfigData(){
        return appConfigData;
    }

    public MongoConfigData getMongoConfigData(){
        return mongoConfigData;
    }
}
