ko.bindingHandlers.avatarUpload =
  update: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    params = valueAccessor()

    url = params.url
    srcSize = "#{params.size}Src"
    enabled = ko.utils.unwrapObservable params.enabled

    box = $(element)
    input = box.find("input[type='file']")

    if not enabled
      box.addClass "avatar-upload-disable"

    progressbar = $(".ui-progressbar", box).fadeOut()

    if(enabled)
      box.addClass "avatar-upload"
      box.removeClass "avatar-upload-disable"
      input.fileupload
        url: url
        dataType: "json"
        dropZone: box

        add: (e, data) =>
          data.submit()

        send: (e, data) =>
          progressbar.progressbar({value: 0}).fadeIn()
          true

        progress: (e, data) =>
          progressbar.progressbar({value: parseInt(data.loaded/data.total * 100, 10)})

        stop: (e, data) =>
          progressbar.fadeOut()

        done: (e, data) =>
          serviceReact data.result, (mdl) =>
            box.find("img").attr("src", mdl.avatar[srcSize]+"?"+new Date().getTime() + new Date().getUTCMilliseconds())