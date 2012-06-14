package mirari

class BenchmarkFilters {

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                request.startTime = System.currentTimeMillis()
            }
            after = { Map model ->
                if(model) model.startTime = request.startTime
                else model = [startTime: request.startTime]
            }
            afterView = { Exception e ->

            }
        }
    }
}
