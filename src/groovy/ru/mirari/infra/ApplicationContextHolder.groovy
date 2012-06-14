package ru.mirari.infra

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.GrailsPluginManager
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

import javax.servlet.ServletContext

@Singleton
class ApplicationContextHolder implements ApplicationContextAware {

    private ApplicationContext ctx

    private static final Map<String, Object> TEST_BEANS = [:]

    void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext
    }

    static ApplicationContext getApplicationContext() {
        getInstance().ctx
    }

    static Object getBean(String name) {
        TEST_BEANS[name] ?: getApplicationContext().getBean(name)
    }

    static GrailsApplication getGrailsApplication() {
        (GrailsApplication) getBean('grailsApplication')
    }

    static ConfigObject getConfig() {
        getGrailsApplication().config
    }

    static ServletContext getServletContext() {
        (ServletContext) getBean('servletContext')
    }

    static GrailsPluginManager getPluginManager() {
        (GrailsPluginManager) getBean('pluginManager')
    }

    // For testing
    static void registerTestBean(String name, bean) {
        TEST_BEANS[name] = bean
    }

    // For testing
    static void unregisterTestBeans() {
        TEST_BEANS.clear()
    }
}