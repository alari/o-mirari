
  ko.bindingHandlers.avatarUpload = {
    update: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      var box, enabled, input, params, progressbar, srcSize, url,
        _this = this;
      params = valueAccessor();
      url = params.url;
      srcSize = "" + params.size + "Src";
      enabled = ko.utils.unwrapObservable(params.enabled);
      box = $(element);
      input = box.find("input[type='file']");
      if (!enabled) box.addClass("avatar-upload-disable");
      progressbar = $(".ui-progressbar", box).fadeOut();
      if (enabled) {
        box.addClass("avatar-upload");
        box.removeClass("avatar-upload-disable");
        return input.fileupload({
          url: url,
          dataType: "json",
          dropZone: box,
          add: function(e, data) {
            return data.submit();
          },
          send: function(e, data) {
            progressbar.progressbar({
              value: 0
            }).fadeIn();
            return true;
          },
          progress: function(e, data) {
            return progressbar.progressbar({
              value: parseInt(data.loaded / data.total * 100, 10)
            });
          },
          stop: function(e, data) {
            return progressbar.fadeOut();
          },
          done: function(e, data) {
            return serviceReact(data.result, function(mdl) {
              return box.find("img").attr("src", mdl.avatar[srcSize] + "?" + new Date().getTime() + new Date().getUTCMilliseconds());
            });
          }
        });
      }
    }
  };
