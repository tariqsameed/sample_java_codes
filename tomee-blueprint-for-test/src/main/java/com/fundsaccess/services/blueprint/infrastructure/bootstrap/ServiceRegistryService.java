package com.fundsaccess.services.blueprint.infrastructure.bootstrap;

import javax.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registers the service with the service registry.
 */
@ApplicationScoped
public class ServiceRegistryService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceRegistryService.class);

//    private ServiceRegistration serviceRegistration;

    /**
     * Registers the Service.
     */
    public void registerService() {
// Only left for reference purposes, not necessary for exams
//        serviceRegistration = ServiceRegistry
//            .createRegistration("com.fundsaccess.services.blueprint:v1", "/service/")
//            .build();
//
//        ServiceRegistry.submitRegistration(serviceRegistration);

//        LOG.info("Service registered as []", serviceRegistration);
    }

    /**
     * Returns the service registration.
     * <p>
     * Also a CDI producer so {@link ServiceRegistration} can be injected everywhere.
     *
     * @return ServiceRegistration
     */
//    @Produces
//    @ApplicationScoped
//    public ServiceRegistration getServiceRegistration() {
//        return this.serviceRegistration;
//    }

}
