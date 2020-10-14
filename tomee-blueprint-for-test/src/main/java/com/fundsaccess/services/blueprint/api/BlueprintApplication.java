package com.fundsaccess.services.blueprint.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * JAX-RS application. Any REST root resources must be listed here.
 */
@ApplicationPath("service")
public class BlueprintApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList(
            BlueprintResource.class
        ));
    }
}
