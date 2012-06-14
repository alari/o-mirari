// environment specific settings
environments {
    development {
    }
    test {
    }
    production {
        grails {
            mongo {
                host = "mongodb-mirari.j.rsnx.ru"
                port = 27017
                username = "mirari"
                password = "rU4uC3ED"
                databaseName = "mirari"
            }
        }
    }
}
