// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }


grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']


// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = ["mirari.dao"]
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password','password2']

// enable query caching by default
grails.hibernate.cache.queries = true

String mainHost = "metamir.com"

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        mainHost = "mirari.ru"
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

mirari.mainPortal.host = mainHost
mirari.mainPortal.displayName = "Mirari"
grails.serverURL = "http://".concat(mainHost)
grails.app.context = "/"

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
}

// Added by the Spring Security Core plugin:

grails {
    plugins {
        springsecurity {
            auth {
                loginFormUrl = "/x/login"
                ajaxLoginFormUrl = '/x/login/auth-ajax'
            }
            apf {
                filterProcessesUrl = "/-checklogin"
                usernameParameter = "jname"
                passwordParameter = "jpwd"
            }
            logout {
                filterProcessesUrl = "/-checklogout"
            }
            adh {
                errorPage = '/x/login/denied'
                ajaxErrorPage = '/x/login/ajax-denied'
            }
            rememberMe {
                parameter = "remember_me"
                cookieName = "mirari_remember"
                key = "omnea_mirari"
            }
            userLookup {
                usernamePropertyName = "email"
                userDomainClassName = 'mirari.security.Account'
            }
            authority {
                className = 'mirari.security.Role'
            }
            password {
                algorithm = 'md5'
            }
            failureHandler {
                defaultFailureUrl = "/x/login/authfail"
                ajaxAuthFailUrl = '/x/login/authfail?ajax=true'
            }
            successHandler {
                ajaxSuccessUrl = '/x/login/ajax-success'
                targetUrlParameter = 'm-redirect'
                defaultTargetUrl = "/"
                alwaysUseDefault = true
            }
        }
    }
}




grails.plugins.twitterbootstrap.fixtaglib = true

mirari {
    infra {
        file {
            local {
                localRoot = "./web-app/f/"
                defaultBucket = "storage"
                urlRoot = "/f/"
            }
            s3 {
                accessKey = "AKIAINSHY2QZWHPJLZ5A"
                secretKey = "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"

                defaultBucket = "s.mirari.ru"
                urlRoot = "http://s.mirari.ru/"

                buckets {
                    mirariavatars = "http://a.mirari.ru/"
                    mirarisounds = "http://h.mirari.ru/"
                    mirariimages = "http://i.mirari.ru/"
                }
            }
        }
    }
}

grails {
    mirari {
        sec {
            defaultRoleNames = ['ROLE_USER', 'ROLE_TALK', 'ROLE_ADD_PAGES']
            url {
                defaultTarget = "/"
                emailVerified = [controller: "settings"]
                passwordResetted = "/"
            }
        }
    }
}

grails {
    mail {
        host = "email-smtp.us-east-1.amazonaws.com"
        port = 465
        username = "AKIAJXLEGNP65A3AW3YQ"
        password = "Apzj4OVyPaL4Cr+2hrQlCTQl/+I58Yk4VJUjR0xQG56O"
        props = ["mail.smtp.auth":"true",
                "mail.smtp.socketFactory.port":"465",
                "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                "mail.smtp.socketFactory.fallback":"false"]

    }
}
grails.mail.default.from="noreply@mirari.ru"