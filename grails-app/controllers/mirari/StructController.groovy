package mirari

import mirari.struct.Entry
import mirari.repo.struct.EntryRepo
import mirari.repo.struct.PileRepo
import mirari.struct.Pile

class StructController extends UtilController {

    def pilesManagerService
    EntryRepo entryRepo
    PileRepo pileRepo

    def createEntry() {
        Entry entry = new Entry(owner: _site)
        if (request.post) {
            entry.title = params.title
            entry.owner = _site
            if (entryRepo.save(entry)) {
                Pile pile = pileRepo.getBySiteAndTitle(entry.owner, entry.title)
                pilesManagerService.put(entry, pile, true)

                ((String)params.piles).split(/,/).each {
                    pilesManagerService.put(entry, pileRepo.getBySiteAndTitle(_site, it), false)
                }

                redirect uri: pile.url
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
