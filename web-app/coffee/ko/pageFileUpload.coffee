ko.bindingHandlers.pageFileUpload =
  init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    unitAdder = $(element)
    progressbar = $(".progress", unitAdder.parent())

    progressbar = unitAdder
    progressbar = progressbar.parent() while not progressbar.find(".progress").length
    progressbar = progressbar.find(".progress")

    progressbar.hide()

    unitAdder.find("form").fileupload
        url: "/p/addFile"
        dataType: "json"
        dropZone: unitAdder
        sequentialUploads: yes
        multipart: true

        add: (e, data) =>
          data.submit()

        send: (e, data) =>
          return false if data.files.length > 1
          progressbar.find(".bar").css "width", 0
          progressbar.show()
          true

        progress: (e, data) =>
          progressbar.find(".bar").css "width", parseInt(data.loaded/data.total * 100, 10)+"%"

        stop: (e, data) =>
          progressbar.fadeOut()

        done: (e, data) =>
          serviceReact data.result, (mdl) =>
            viewModel.innersAct.addUnit mdl.unit

      success: (data, textStatus, jqXHR) ->
        serviceReact data, (mdl) -> console.log mdl

      error: (data, textStatus, jqXHR)->
        alert "Error"