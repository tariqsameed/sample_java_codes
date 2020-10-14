package com.fundsaccess.services.blueprint;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

public class BlueprintTest {

    @Test
    public void test_saysHelloToWorld() {
        assertThat("Hello World", is(not(nullValue())));
    }
    
}
