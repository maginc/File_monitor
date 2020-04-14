package com.themagins.filemonitor.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Andris Magins
 * @created 31-Mar-20
 **/

@SpringBootTest
public class ContextLoadTest {

    @Autowired
    private CatalogController catalogController;

    @Test
    public void loadContext(){
    }

    @Test
    public void check_if_catalogController_is_loaded() throws Exception{
        assertThat(catalogController).isNotNull();
    }
}
