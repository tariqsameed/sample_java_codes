package com.fundsaccess.services.blueprint.infrastructure.bootstrap;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls all steps that are necessary during startup of the serivce.
 */
@Singleton
@Startup
public class BlueprintStartupBean {

    private static final Logger LOG = LoggerFactory.getLogger(BlueprintStartupBean.class);

    protected final ServiceRegistryService serviceRegistryService;

    protected BlueprintStartupBean() {
        this.serviceRegistryService = null;
    }

    /**
     * Creates an instance and takes all class dependencies.
     *
     * @param serviceRegistryService The service registry service.
     */
    @Inject
    public BlueprintStartupBean(ServiceRegistryService serviceRegistryService) {
        this.serviceRegistryService = serviceRegistryService;
    }

    /**
     * Called by CDI after the instance creation.
     */
    @PostConstruct
    public void init() {

        LOG.info("Starting services :: blueprint");

        serviceRegistryService.registerService();

        LOG.info("Started services :: blueprint");
    }

}
