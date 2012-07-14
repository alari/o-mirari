grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        excludes 'hibernate'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()

        // uncomment these to enable remote dependency resolution from public Maven repositories
        //mavenCentral()
        //mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        mavenRepo "http://repository.jboss.com/maven2/"

        // For pegdown markdown
        mavenRepo "http://scala-tools.org/repo-releases"

        // For file storage
        mavenRepo "http://www.jets3t.org/maven2"
    }
    dependencies {
        // markdown
        compile 'org.pegdown:pegdown:1.1.0'

        runtime 'stax:stax:1.2.0'

        compile 'org.codehaus.gpars:gpars:0.12'

        compile('eu.medsea.mimeutil:mime-util:2.1.3') {
            exclude "slf4j-log4j12"
        }

        compile "org.jsoup:jsoup:1.6.2"

        compile "rome:rome:1.0"

        // File storage
        compile "net.java.dev.jets3t:jets3t:0.8.1"
    }

    plugins {
        runtime ":jquery:1.7.2"
        runtime ":resources:1.2-RC1", {
            excludes "hibernate"
        }

        compile ":mongodb:1.0.0.GA"

        runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        runtime ":yui-minify-resources:0.1.5"

        test ":spock:0.6", {
            excludes "hibernate"
        }

        compile ":mail:1.0"

        // SECURITY
        runtime(':spring-security-core:1.2.7.3') {
            excludes "hibernate"
        }

        compile ":twitter-bootstrap:2.0.2.25"
        compile ":fields:1.1"

        //compile ":spring-events:1.2"
        //compile ":bootstrap-file-upload:2.1.1"

        build ":tomcat:$grailsVersion"
    }
}
