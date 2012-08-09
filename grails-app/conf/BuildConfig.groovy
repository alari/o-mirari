grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.plugin.location.'infra-knock-coffee' = "infra-knock-coffee"
grails.plugin.location.'infra-file-storage' = "infra-file-storage"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'hibernate'
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
        compile 'org.pegdown:pegdown:latest.release'

        runtime 'stax:stax:1.2.0'

        compile 'org.codehaus.gpars:gpars:0.12'

        compile "org.jsoup:jsoup:latest.release"

        compile "rome:rome:latest.release"
    }

    plugins {
        compile ":hibernate:$grailsVersion"

        runtime ":jquery:latest.integration"
        runtime ":resources:1.2-RC1"

        compile ":mongodb:latest.integration"

        runtime ":zipped-resources:latest.integration"
        //runtime ":cached-resources:1.0"
        runtime ":yui-minify-resources:latest.integration"

        test ":spock:latest.integration"

        compile ":mail:latest.integration"

        // SECURITY
        runtime(':spring-security-core:latest.integration')

        compile(":twitter-bootstrap:2.0.2.25") {
            excludes "svn"
        }
        compile ":fields:latest.integration"

        compile ":redis:latest.integration"

        //compile ":spring-events:1.2"
        //compile ":bootstrap-file-upload:2.1.1"

        build ":tomcat:$grailsVersion"
    }
}
