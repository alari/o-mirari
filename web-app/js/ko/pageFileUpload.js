
  ko.bindingHandlers.pageFileUpload = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      var progressbar, unitAdder,
        _this = this;
      unitAdder = $(element);
      progressbar = $(".progress", unitAdder.parent());
      progressbar = unitAdder;
      while (!progressbar.find(".progress").length) {
        progressbar = progressbar.parent();
      }
      progressbar = progressbar.find(".progress");
      progressbar.hide();
      unitAdder.find("form").fileupload({
        url: "/p/addFile",
        dataType: "json",
        dropZone: unitAdder,
        sequentialUploads: true,
        multipart: true,
        add: function(e, data) {
          return data.submit();
        },
        send: function(e, data) {
          if (data.files.length > 1) return false;
          progressbar.find(".bar").css("width", 0);
          progressbar.show();
          return true;
        },
        progress: function(e, data) {
          return progressbar.find(".bar").css("width", parseInt(data.loaded / data.total * 100, 10) + "%");
        },
        stop: function(e, data) {
          return progressbar.fadeOut();
        },
        done: function(e, data) {
          return serviceReact(data.result, function(mdl) {
            return viewModel.innersAct.addUnit(mdl.unit);
          });
        }
      });
      return {
        success: function(data, textStatus, jqXHR) {
          return serviceReact(data, function(mdl) {
            return console.log(mdl);
          });
        },
        error: function(data, textStatus, jqXHR) {
          return alert("Error");
        }
      };
    }
  };
