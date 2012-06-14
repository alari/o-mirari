exports = this
$ = exports.jQuery

class exports.AlertsVM
  constructor: ->
    @alerts = ko.observableArray([])
  alert: (json)->
    @alerts.push new AlertMessageVM(json)

  success: (message)->
    @alert
      message: message
      level: "success"
  info: (message)->
    @alert
      message: message
      level: "info"
  warning: (message)->
    @alert
      message: message
      level: "warning"
  error: (message)->
    @alert
      message: message
      level: "error"

class AlertMessageVM
  constructor: (json)->
    @level = json.level
    @message = json.message
  remove: =>
    alertsVM.alerts.remove this

exports.alertsVM = new AlertsVM()

exports.alertsCallback = (successCallback, finallyCallback) ->
  return (data, textStatus, jqXHR)->
    (return window.location.href = json.srv.redirect) if json.srv.redirect?
    alertsVM.alert(alert) for alert in json.srv.alerts if json.srv.alerts?
    successCallback json.mdl if json.srv.ok
    alertsVM.error("Ajax Error") if not json.srv.ok? and not json.srv.alerts?
    finallyCallback() if finallyCallback

exports.serviceReact = (json, callback) ->
  (return window.location.href = json.srv.redirect) if json.srv.redirect?
  alertsVM.alert(alert) for alert in json.srv.alerts if json.srv.alerts?
  callback json.mdl if json.srv.ok is true
  alertsVM.error("Ajax Error") if not json.srv.ok? and not json.srv.alerts?

exports.jsonGetReact = (url, callback) ->
  $.ajax url,
    type: "get",
    dataType: "json",
    success: (data, textStatus, jqXHR) =>
      serviceReact data, callback
    error: (data, textStatus, jqXHR)->
      alert "Error"

exports.jsonPostReact = (url, data, callback) ->
  $.ajax url,
    type: "post"
    dataType: "json"
    data: data
    success: (data, textStatus, jqXHR) =>
      serviceReact data, callback
    error: (data, textStatus, jqXHR)->
      alert "Error"