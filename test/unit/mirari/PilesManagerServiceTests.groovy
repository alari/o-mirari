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

    void testPutDraw() {
        shouldFail(IllegalArgumentException) {
            pilesManagerService.put(null, null, true)
        }

        Entry entry = new Entry()
        Pile pile = new Pile()

        entry.id = "test id"
        entry.metaClass.getPilePosition = {2.75d}
        pile.id = "p"

        pilesManagerService.delete(pile)
        assert pilesManagerService.sizeOf(pile) == 0
        assert pilesManagerService.drawIds(pile, 1, 0).size() == 0

        pilesManagerService.put(entry, pile, true)
        assert pilesManagerService.sizeOf(pile) == 1

        def s = pilesManagerService.drawIds(pile, 1, 0)
        assert s[0] == entry.id

        entry.id = "test 2"
        pilesManagerService.put(entry, pile, true)
        assert pilesManagerService.sizeOf(pile) == 2

        s = pilesManagerService.drawIds(pile, 2, 0)
        assert s[0] == entry.id
        assert s[1] == "test id"

        entry.id = "test 3"
        pilesManagerService.put(entry, pile, false)
        assert pilesManagerService.sizeOf(pile) == 3

        s = pilesManagerService.drawIds(pile, 3, 0)
        assert s == ["test 2", "test id", "test 3"]

        entry.id = "test 4"
        assert entry.pilePosition == 2.75d
        entry.metaClass.getPilePosition = {1d}
        assert entry.pilePosition == 1d

        pilesManagerService.put(entry, pile, false)
        assert pilesManagerService.sizeOf(pile) == 4

        s = pilesManagerService.drawIds(pile, 10, 0)
        assert s == ["test 2", "test id", "test 3", "test 4"]

        entry.id = "test_5"
        entry.metaClass.getPilePosition = {100d}

        pilesManagerService.put(entry, pile, false)
        assert pilesManagerService.sizeOf(pile) == 5

        s = pilesManagerService.drawIds(pile, 100, 0)
        assert s == ["test 2", "test id", "test_5", "test 3", "test 4"]

        pile.id = "t"
        pilesManagerService.delete(pile)

        entry.id = "1"
        entry.metaClass.getPilePosition = {1d}
        pilesManagerService.put(entry, pile, false)
        assert pilesManagerService.sizeOf(pile) == 1
        s = pilesManagerService.drawIds(pile, 100, 0)
        assert s == [entry.id]

        entry.id = "2"
        pilesManagerService.put(entry, pile, true)
        assert pilesManagerService.sizeOf(pile) == 2
        s = pilesManagerService.drawIds(pile, 100, 0)
        assert s == [entry.id, "1"]

        s = pilesManagerService.drawIds(pile, 1, 0)
        assert s == [entry.id]
    }
}
