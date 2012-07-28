package mirari

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import mirari.struct.Entry
import mirari.struct.Pile
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.RandomStringUtils

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(PilesManagerService)
@Mock([Pile, Entry])
class PilesManagerServiceTests {

    def redisService

    void setUp() {
        service.redisService = redisService
    }

    void testPutDraw() {
        shouldFail(IllegalArgumentException) {
            service.put(null, null, true)
        }

        Entry entry = new Entry()
        Pile pile = new Pile()

        entry.id = "test id"
        entry.dateCreated = new Date()
        pile.id = "p"

        service.delete(pile)
        assert service.sizeOf(pile) == 0
        assert service.drawIds(pile, 1, 0).size() == 0

        service.put(entry, pile, true)
        assert service.sizeOf(pile) == 1

        def s = service.drawIds(pile, 1, 0)
        assert s[0] == entry.id

        entry.id = "test 2"
        service.put(entry, pile, true)
        assert service.sizeOf(pile) == 2

        s = service.drawIds(pile, 2, 0)
        assert s[0] == entry.id
        assert s[1] == "test id"

        entry.id = "test 3"
        service.put(entry, pile, false)
        assert service.sizeOf(pile) == 3

        s = service.drawIds(pile, 3, 0)
        assert s == ["test 2", "test id", "test 3"]

        entry.id = "test 4"
        entry.dateCreated.time -= 10

        service.put(entry, pile, false)
        assert service.sizeOf(pile) == 4

        s = service.drawIds(pile, 10, 0)
        assert s == ["test 2", "test id", "test 3", "test 4"]

        entry.id = "test_5"
        entry.dateCreated.time += 1000

        service.put(entry, pile, false)
        assert service.sizeOf(pile) == 5

        s = service.drawIds(pile, 100, 0)
        assert s == ["test 2", "test id", "test_5", "test 3", "test 4"]

        pile.id = "t"
        service.delete(pile)

        entry.id = "1"
        entry.dateCreated.time -= 10000
        service.put(entry, pile, false)
        assert service.sizeOf(pile) == 1
        s = service.drawIds(pile, 100, 0)
        assert s == [entry.id]

        entry.id = "2"
        service.put(entry, pile, true)
        assert service.sizeOf(pile) == 2
        s = service.drawIds(pile, 100, 0)
        assert s == [entry.id, "1"]

        s = service.drawIds(pile, 1, 0)
        assert s == [entry.id]
    }

    void testSort() {
        Pile pile = new Pile()
        pile.id = "p"

        List<Entry> entries = [
                newEntry,
                newEntry,
                newEntry,
                newEntry,
                newEntry,
        ]

        // sort top two ones
        service.delete(pile)
        assert service.sizeOf(pile) == 0
        assert service.drawIds(pile, 1, 0).size() == 0

        service.put(entries[0], pile, true)
        assert service.sizeOf(pile) == 1

        service.setPosition(entries[0], pile, 0)
        assert service.sizeOf(pile) == 1

        service.put(entries[1], pile, true)
        assert service.drawIds(pile, 2, 0) == [entries[1].id, entries[0].id]

        service.setPosition(entries[0], pile, 0)
        assert service.drawIds(pile, 2, 0) == [entries[0].id, entries[1].id]

        // sort one additional
        service.put(entries[2], pile, false)
        assert service.drawIds(pile, 20, 0) == [entries[0].id, entries[1].id, entries[2].id]

        // put first
        service.setPosition(entries[2], pile, 0)
        List ids = [entries[2].id, entries[0].id, entries[1].id]
        assert service.drawIds(pile, 3, 0) == ids

        service.dropPosition(entries[2], pile, false)
        assert service.drawIds(pile, 20, 0) == [entries[0].id, entries[1].id, entries[2].id]

        // put last
        service.setPosition(entries[2], pile, 2)
        ids = [entries[0].id, entries[1].id, entries[2].id]
        assert service.drawIds(pile, 20, 0) == ids

        service.dropPosition(entries[2], pile, false)
        assert service.drawIds(pile, 20, 0) == [entries[0].id, entries[1].id, entries[2].id]

        // put second
        service.setPosition(entries[2], pile, 1)
        ids = [entries[0].id, entries[2].id, entries[1].id]
        assert service.drawIds(pile, 3, 0) == ids

        service.dropPosition(entries[2], pile, false)
        assert service.drawIds(pile, 20, 0) == [entries[0].id, entries[1].id, entries[2].id]

        // add one more
        service.put(entries[3], pile, false)
        ids = [entries[0].id, entries[1].id, entries[3].id, entries[2].id]
        assert service.drawIds(pile, 4, 0) == ids

        service.put(entries[4], pile, false)
        ids = [entries[0].id, entries[1].id, entries[4].id, entries[3].id, entries[2].id]
        assert service.drawIds(pile, 20, 0) == ids

        // put second
        service.setPosition(entries[3], pile, 1)
        ids = [entries[0].id, entries[3].id, entries[1].id, entries[4].id, entries[2].id]
        assert service.drawIds(pile, 5, 0) == ids

        service.dropPosition(entries[3], pile, false)
        ids = [entries[0].id, entries[1].id, entries[4].id, entries[3].id, entries[2].id]
        assert service.drawIds(pile, 20, 0) == ids

        // test with tail
        service.metaClass.getItem = {String id->entries.find {it.id == id}}
        service.dropPosition(entries[0], pile, true)
        ids = [entries[4].id, entries[3].id, entries[2].id, entries[1].id, entries[0].id]
        assert service.drawIds(pile, 5, 0) == ids
    }

    private int entriesNum=0

    private Entry getNewEntry(double pilePos = 0) {
        Entry e = new Entry()
        e.id = "m-"+entriesNum
        e.dateCreated = new Date((int)pilePos ?: entriesNum, 0, 0)
        entriesNum++
        e
    }
}
