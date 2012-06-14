package ru.mirari.infra.event

import org.springframework.context.ApplicationListener
import ru.mirari.infra.EventFire

class EventLoggerService implements ApplicationListener<EventFire> {
    static transactional = false

    @Override
    void onApplicationEvent(EventFire e) {
        println("event:"+e.class.canonicalName)
    }
}
