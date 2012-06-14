package ru.mirari.infra

import org.springframework.context.ApplicationEvent

/**
 * @author alari
 * @since 5/5/12 12:38 PM
 */
abstract class EventFire extends ApplicationEvent {
    EventFire(Object source) {
        super(source)
    }

    void fire() {
        println "fire ${this}"
        ApplicationContextHolder.applicationContext.publishEvent(this)
    }
}
