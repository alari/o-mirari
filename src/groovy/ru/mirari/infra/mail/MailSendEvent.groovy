package ru.mirari.infra.mail

import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.ApplicationEvent
import ru.mirari.infra.EventFire

/**
 * @author alari
 * @since 2/13/12 11:02 PM
 */
class MailSendEvent extends EventFire {
    Locale locale = LocaleContextHolder.getLocale()
    String view
    String to
    String subject
    Map<String,Object> model = [:]

    MailSendEvent(Object source) {
        super(source)
    }

    MailSendEvent view(String view) {
        this.view = view
        this
    }

    MailSendEvent model(Map<String, Object> model) {
        this.model = model
        this
    }

    MailSendEvent to(String to) {
        this.to = to
        this
    }

    MailSendEvent subject(String subject) {
        this.subject = subject
        this
    }
}
