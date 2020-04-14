package com.themagins.filemonitor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * This can provide Spring boot context to non spring boot components
 *
 * @author Andris Magins
 * @created 16/01/2020
 **/
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext appContext)
            throws BeansException {
        ctx = appContext;

    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
}
