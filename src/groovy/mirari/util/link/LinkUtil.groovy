package mirari.util.link

import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import ru.mirari.infra.ApplicationContextHolder

/**
 * @author alari
 * @since 2/4/12 7:23 PM
 */
class LinkUtil {
    static protected transient LinkGenerator grailsLinkGenerator

    static {
        grailsLinkGenerator = (LinkGenerator) ApplicationContextHolder.getBean("grailsLinkGenerator")
    }

    static public String getUrl(Map attrs) {
        grailsLinkGenerator.link(attrs)
    }
}
