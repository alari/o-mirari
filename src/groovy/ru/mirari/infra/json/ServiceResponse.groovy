package ru.mirari.infra.json

/**
 * @author Dmitry Kurinskiy
 * @since 19.08.11 13:44
 */
class ServiceResponse {
    AlertLevel level = AlertLevel.INFO
    String alertCode
    List<String> alertParams = []
    Map redirect
    Map model

    ServiceResponse() {}

    ServiceResponse redirect(String redirectUri) {
        this.redirect = [url: redirectUri]
        this
    }

    ServiceResponse redirect(Map redirect) {
        this.redirect = redirect
        this
    }

    ServiceResponse model(final Map modelAttrs) {
        if(!model) {
            model = [:]
        }
        model.putAll(modelAttrs)
        this
    }

    ServiceResponse success(String code = null, List<String> params = []) {
        alert AlertLevel.SUCCESS, code, params
    }

    ServiceResponse info(String code = null, List<String> params = []) {
        alert AlertLevel.INFO, code, params
    }

    ServiceResponse warning(String code = null, List<String> params = []) {
        alert AlertLevel.WARNING, code, params
    }

    ServiceResponse error(String code = null, List<String> params = []) {
        alert AlertLevel.ERROR, code, params
    }

    private ServiceResponse alert(AlertLevel level, String alertCode, List<String> params) {
        this.level = level
        this.alertCode = alertCode
        this.alertParams = params
        this
    }

    boolean isOk() {
        level?.isOk()
    }
}
