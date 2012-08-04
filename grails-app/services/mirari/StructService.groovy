package mirari

import mirari.repo.struct.EntryRepo
import mirari.repo.struct.PileRepo
import mirari.struct.Entry
import mirari.struct.Pile

class StructService {

    def pilesManagerService
    EntryRepo entryRepo
    PileRepo pileRepo

    String createEntry(Entry entry, Site portal, String[] pileTitles) {
        if (entryRepo.save(entry)) {
            Pile pile = pileRepo.getBySiteAndTitle(entry.owner, entry.title)
            pilesManagerService.put(entry, pile, true)

            pileTitles.each {
                pilesManagerService.put(entry, pileRepo.getBySiteAndTitle(portal, it), false)
            }

            return pile.url
        }
        null
    }


}
