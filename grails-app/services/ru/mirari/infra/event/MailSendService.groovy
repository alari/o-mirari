package ru.mirari.infra.event

import ru.mirari.infra.mail.MailSendEvent
import org.springframework.context.ApplicationListener
import grails.gsp.PageRenderer
import grails.plugin.mail.MailService

class MailSendService implements ApplicationListener<MailSendEvent> {
    static transactional = false

    PageRenderer groovyPageRenderer
    MailService mailService

    MailSendEvent compose(Object source=null) {
        new MailSendEvent(source)
    }

    @Override
    void onApplicationEvent(MailSendEvent e) {
        e.model.put("locale", e.locale)
        mailService.sendMail {
            to e.to
            subject e.subject
            html groovyPageRenderer.render(view: e.view, model: e.model)
        }
    }
}
