package mirari.infra

import grails.util.Environment

class TestTagLib {
    static namespace = "test"

    def echo = {attrs, body ->
        if (!Environment.isWarDeployed()) {
            out << body()
        }
    }
}
