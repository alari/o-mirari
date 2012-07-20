package mirari

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import mirari.struct.Entry
import mirari.struct.Pile
import ru.mirari.infra.ApplicationContextHolder

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(PilesManagerService)
@Mock([Pile, Entry])
class PilesManagerServiceTests {

    PilesManagerService pilesManagerService = (PilesManagerService) ApplicationContextHolder.getBean("pilesManagerService")

    void testSomething() {
        shouldFail(IllegalArgumentException) {
            pilesManagerService.put(null, null, true)
        }

        Entry entry = new Entry()
        Pile pile = new Pile()

        entry.id = "test id"
        entry.metaClass.getPilePosition = {2.75d}
        pile.id = "pile id"

        pilesManagerService.delete(pile)
        assert pilesManagerService.drawIds(pile, 1, 0).size() == 0

        pilesManagerService.put(entry, pile, true)

        def s = pilesManagerService.drawIds(pile, 1, 0)
        assert s[0] == entry.id

        entry.id = "test 2"
        pilesManagerService.put(entry, pile, true)

        s = pilesManagerService.drawIds(pile, 2, 0)
        assert s[0] == entry.id
        assert s[1] == "test id"

        entry.id = "test 3"
        pilesManagerService.put(entry, pile, false)

        s = pilesManagerService.drawIds(pile, 3, 0)
        assert s == ["test 2", "test id", "test 3"]

        entry.id = "test 4"
        assert entry.pilePosition == 2.75d
        entry.metaClass.getPilePosition = {1d}
        assert entry.pilePosition == 1d

        pilesManagerService.put(entry, pile, false)

        s = pilesManagerService.drawIds(pile, 10, 0)
        //assert s == ["test 2", "test id", "test 4", "test 3"]

        entry.id = "test 5"
        entry.metaClass.getPilePosition = {100d}

        pilesManagerService.put(entry, pile, false)

        s = pilesManagerService.drawIds(pile, 10, 0)
        assert s == ["test 2", "test id", "test 4", "test 3", "test 5"]
    }
}
