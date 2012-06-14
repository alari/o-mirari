class UrlMappings {

	static mappings = {
		"/x/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        "/robots.txt"(controller: "root", action: "robots")

		"/"(controller: "root", action: "index")


		"500"(view:'/error')
	}
}
