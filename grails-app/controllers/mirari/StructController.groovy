package mirari

import mirari.struct.Entry
import mirari.repo.struct.PileRepo
import mirari.struct.Pile
import grails.plugins.springsecurity.Secured

class StructController extends UtilController {

    def pilesManagerService
    PileRepo pileRepo
    def structService

    @Secured("ROLE_USER")
    def createEntry() {
        Entry entry = new Entry(owner: _site)
        if (request.post) {
            entry.title = params.title
            String url = structService.createEntry(entry, _portal, ((String)params.piles).split(/,/))
            if (url) {
                redirect url: url
            }
        }
        [entry: entry]
    }

    def pile(String pileName) {
        Pile pile = pileRepo.findBySiteAndName(_site, pileName)
        [entries: pilesManagerService.draw(pile, 20, 0), pile: pile]
    }

    def entry(String id) {

    }
}
