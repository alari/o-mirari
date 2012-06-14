package mirari.infra

class AlertsTagLib {
    def alertsService

    def alerts = {attrs ->
        alertsService.getAlerts(flash).each { alert ->
            out << "alertsVM.".concat(alert.level.toString()).concat("('" + message(code: alert.code, args: alert.params).encodeAsJavaScript() + "');\n")
        }
        alertsService.clean(flash)
    }
}
