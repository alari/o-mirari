modules = {
    vendor_fileUpload {
        resource url: "/js/vendor/uploadr/jquery.iframe-transport.js", bundle: "uploadr-transport"
        resource url: "/js/vendor/uploadr/jquery.fileupload.js", bundle: "uploadr"
        resource url: "/css/file-upload.css"
        dependsOn "jqueryUi"
    }
    vendor_mediaelement {
        resource url: "/js/vendor/mediaelement/mediaelement-and-player.min.js"
        resource url: "/js/vendor/mediaelement/mediaelementplayer.min.css"
        resource url: "/js/ko/audio.js"
        dependsOn "jquery", "knockout"
    }

    app_alerts {
        resource url: "/js/Alerts.js"
        dependsOn "knockout", "ko_fadeOut"
    }

    /*   CUSTOM BINDINGS   */
    /*ko_feedUnit {
        resource url: "/js/ko/feedUnit.js"
        dependsOn "vm_feedUnit"
    }*/
    ko_pageFileUpload {
        resource url: "/js/ko/pageFileUpload.js"
        dependsOn "vendor_fileUpload", "knockout"
    }
    ko_sortableInners {
        resource url: "/js/ko/sortableInners.js"
        resource url: "/css/sortable-inners.css"
        dependsOn "knockout", "jqueryUi"
    }
    ko_fixFloat {
        resource url: "/js/ko/fixFloat.js"
        dependsOn "knockout"
    }
    ko_autoResize {
        resource url: "/js/ko/autoResize.js"
        resource url: "/js/vendor/autoResize.js"
        dependsOn "knockout", "jquery"
    }
    ko_carousel {
        resource url: "/js/ko/carousel.js"
        dependsOn "knockout", "jquery", "bootstrap"
    }
    ko_fadeOut {
        resource url: "/js/ko/fadeOut.js"
        dependsOn "knockout", "jquery"
    }
    ko_timestamp {
        resource url: "/js/ko/timestamp.js"
        dependsOn "knockout", "jquery"
    }
    /*ko_avatarUpload {
        resource url: "/js/ko/avatarUpload.js"
        resource url: "/css/avatar-upload.css"
        dependsOn "knockout", "vendor_fileUpload", "vm_avatar"
    }*/
    ko_autocomplete {
        resource url: "/js/ko/autocomplete.js"
        dependsOn "knockout", "jqueryUi"
    }
    ko_ctrlEnter {
        resource url: "/js/ko/ctrlEnter.js"
        dependsOn "knockout", "jquery"
    }
    ko_popover {
        resource url: "/js/ko/popover.js"
        dependsOn "knockout", "bootstrap"
    }
    ko_clickScroll {
        resource url: "/js/ko/clickScroll.js"
        dependsOn "knockout", "jquery"
    }
    /*ko_compound {
        resource url: "/js/ko/compound.js"
        dependsOn "vm_compoundUnit"
    } */
}