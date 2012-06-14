package ru.mirari.infra

import org.codehaus.groovy.grails.web.servlet.FlashScope
import ru.mirari.infra.json.ServiceResponse
import ru.mirari.infra.json.AlertLevel

class AlertsService {
    def transactional = false

    def alert(FlashScope flash, ServiceResponse resp) {
        if (!resp.alertCode) return;
        alert(flash, resp.level, resp.alertCode, resp.alertParams)
    }

    def alert(FlashScope flash, AlertLevel level, String code, List<String> args) {
        if (!flash.alerts) {
            flash.alerts = []
        }
        ((List) flash.alerts).add([level: level, code: code, params: args])
    }

    def warning(FlashScope flash, String code, List<String> args = []) {
        alert(flash, AlertLevel.WARNING, code, args)
    }

    def error(FlashScope flash, String code, List<String> args = []) {
        alert(flash, AlertLevel.ERROR, code, args)
    }

    def success(FlashScope flash, String code, List<String> args = []) {
        alert(flash, AlertLevel.SUCCESS, code, args)
    }

    def info(FlashScope flash, String code, List<String> args = []) {
        alert(flash, AlertLevel.INFO, code, args)
    }

    List getAlerts(FlashScope flash) {
        List alerts = (List) flash.alerts ?: []
        if (flash.error) alerts.add([code: flash.error, level: AlertLevel.ERROR])
        if (flash.message) alerts.add([code: flash.message, level: AlertLevel.INFO])
        alerts
    }

    void clean(FlashScope flash) {
        flash.alerts = []
    }
}
