package mirari

class RootController extends UtilController {
    def robots() {
        response.contentType = "text/plain"
        render "User-agent: *\nDisallow: /x/"
        println "Robot: " + request.getHeader("user-agent")
    }

    def index() {

    }
}
