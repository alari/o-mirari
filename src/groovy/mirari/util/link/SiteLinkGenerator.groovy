package mirari.util.link

import org.codehaus.groovy.grails.web.mapping.DefaultLinkGenerator
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest

import javax.servlet.http.HttpServletRequest

/**
 * @author alari
 * @since 1/3/12 10:07 PM
 */
class SiteLinkGenerator extends DefaultLinkGenerator {
    SiteLinkGenerator(String serverBaseURL, String contextPath) {
        super(serverBaseURL, contextPath)
    }

    SiteLinkGenerator(String serverBaseURL) {
        super(serverBaseURL)
    }

    /**
     * {@inheritDoc}
     * @attr for Site, Entry, Unit
     * @attr forSite boolean
     */
    String link(Map attrs, String encoding = 'UTF-8') {
        if (attrs.containsKey("plain")) {
            attrs.remove("plain")
            return super.link(attrs, encoding)
        }
        attrs.params = attrs.params ?: [:]
        attrs.action = attrs.action ?: ""
        def forObject = attrs.remove("for")
        if (!forObject) {
            forObject = attrs.containsKey("forSite") ? request.getAttribute("_site") : request.getAttribute("_portal")
            attrs.remove("forSite")
        }
        if (forObject instanceof LinkAttributesFitter) {
            ((LinkAttributesFitter) forObject).fitLinkAttributes(attrs)
        }
        super.link(attrs, encoding)
    }

    private HttpServletRequest getRequest() {
        GrailsWebRequest.lookup()?.currentRequest
    }
}
