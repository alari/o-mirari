class UrlMappings {

	static mappings = {
        final Map nameCheck = [matches: '^[%a-zA-Z0-9][-._%a-zA-Z0-9]{0,375}[a-zA-Z0-9]$']

        "/pile/$pileName" {
            constraints {
                pileName nameCheck
            }
            controller = "struct"
            action = "pile"
        }

		"/x/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        "/robots.txt"(controller: "root", action: "robots")

		"/"(controller: "root", action: "index")


		"500"(view:'/error')
        "404"(view:'/error')
	}
}
