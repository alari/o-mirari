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
        dependsOn "jquery", "vendor_ko"
    }
    vendor_ko {
        resource url: "/js/vendor/ko/knockout-2.1.0.pre.js"
    }
    vendor_ko_mapping {
        resource url: "/js/vendor/ko/knockout-mapping.2.0.3.js"
        dependsOn "vendor_ko"
    }

    app_alerts {
        resource url: "/js/Alerts.js"
        dependsOn "vendor_ko", "ko_fadeOut"
    }

    /*   CUSTOM BINDINGS   */
    /*ko_feedUnit {
        resource url: "/js/ko/feedUnit.js"
        dependsOn "vm_feedUnit"
    }*/
    ko_pageFileUpload {
        resource url: "/js/ko/pageFileUpload.js"
        dependsOn "vendor_fileUpload", "vendor_ko"
    }
    ko_sortableInners {
        resource url: "/js/ko/sortableInners.js"
        resource url: "/css/sortable-inners.css"
        dependsOn "vendor_ko", "jqueryUi"
    }
    ko_fixFloat {
        resource url: "/js/ko/fixFloat.js"
        dependsOn "vendor_ko"
    }
    ko_autoResize {
        resource url: "/js/ko/autoResize.js"
        resource url: "/js/vendor/autoResize.js"
        dependsOn "vendor_ko", "jquery"
    }
    ko_carousel {
        resource url: "/js/ko/carousel.js"
        dependsOn "vendor_ko", "jquery", "bootstrap"
    }
    ko_fadeOut {
        resource url: "/js/ko/fadeOut.js"
        dependsOn "vendor_ko", "jquery"
    }
    ko_timestamp {
        resource url: "/js/ko/timestamp.js"
        dependsOn "vendor_ko", "jquery"
    }
    /*ko_avatarUpload {
        resource url: "/js/ko/avatarUpload.js"
        resource url: "/css/avatar-upload.css"
        dependsOn "vendor_ko", "vendor_fileUpload", "vm_avatar"
    }*/
    ko_autocomplete {
        resource url: "/js/ko/autocomplete.js"
        dependsOn "vendor_ko", "jqueryUi"
    }
    ko_ctrlEnter {
        resource url: "/js/ko/ctrlEnter.js"
        dependsOn "vendor_ko", "jquery"
    }
    ko_popover {
        resource url: "/js/ko/popover.js"
        dependsOn "vendor_ko", "bootstrap"
    }
    ko_clickScroll {
        resource url: "/js/ko/clickScroll.js"
        dependsOn "vendor_ko", "jquery"
    }
    /*ko_compound {
        resource url: "/js/ko/compound.js"
        dependsOn "vm_compoundUnit"
    } */
}