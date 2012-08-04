package mirari

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import mirari.struct.Entry
import mirari.struct.Pile

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

        entry.id = 1
        entry.dateCreated = new Date()
        pile.id = 1

        service.delete(pile)
        assert service.sizeOf(pile) == 0
        assert service.drawIds(pile, 1, 0).size() == 0

        service.put(entry, pile, true)
        assert service.sizeOf(pile) == 1

        def s = service.drawIds(pile, 1, 0)
        assert s[0] == entry.stringId

        entry.id = 2
        service.put(entry, pile, true)
        assert service.sizeOf(pile) == 2

        s = service.drawIds(pile, 2, 0)
        assert s[0] == entry.stringId
        assert s[1] == 1.toString()

        entry.id = 3
        service.put(entry, pile, false)
        assert service.sizeOf(pile) == 3

        s = service.drawIds(pile, 3, 0)
        assert s == [2, 1, 3]*.toString()

        entry.id = 4
        entry.dateCreated.time -= 10

        service.put(entry, pile, false)
        assert service.sizeOf(pile) == 4

        s = service.drawIds(pile, 10, 0)
        assert s == [2, 1, 3, 4]*.toString()

        entry.id = 5
        entry.dateCreated.time += 1000

        service.put(entry, pile, false)
        assert service.sizeOf(pile) == 5

        s = service.drawIds(pile, 100, 0)
        assert s == [2, 1, 5, 3, 4]*.toString()

        pile.id = 2
        service.delete(pile)

        entry.id = 1
        entry.dateCreated.time -= 10000
        service.put(entry, pile, false)
        assert service.sizeOf(pile) == 1
        s = service.drawIds(pile, 100, 0)
        assert s == [entry.stringId]

        entry.id = 2
        service.put(entry, pile, true)
        assert service.sizeOf(pile) == 2
        s = service.drawIds(pile, 100, 0)
        assert s == [entry.stringId, "1"]

        s = service.drawIds(pile, 1, 0)
        assert s == [entry.stringId]
    }

    void testSort() {
        Pile pile = new Pile()
        pile.id = 3

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
        assert service.drawIds(pile, 2, 0) == [entries[1].stringId, entries[0].stringId]

        service.setPosition(entries[0], pile, 0)
        assert service.drawIds(pile, 2, 0) == [entries[0].stringId, entries[1].stringId]

        // sort one additional
        service.put(entries[2], pile, false)
        assert service.drawIds(pile, 20, 0) == [entries[0].stringId, entries[1].stringId, entries[2].stringId]

        // put first
        service.setPosition(entries[2], pile, 0)
        List ids = [entries[2].stringId, entries[0].stringId, entries[1].stringId]
        assert service.drawIds(pile, 3, 0) == ids

        service.dropPosition(entries[2], pile, false)
        assert service.drawIds(pile, 20, 0) == [entries[0].stringId, entries[1].stringId, entries[2].stringId]

        // put last
        service.setPosition(entries[2], pile, 2)
        ids = [entries[0].stringId, entries[1].stringId, entries[2].stringId]
        assert service.drawIds(pile, 20, 0) == ids

        service.dropPosition(entries[2], pile, false)
        assert service.drawIds(pile, 20, 0) == [entries[0].stringId, entries[1].stringId, entries[2].stringId]

        // put second
        service.setPosition(entries[2], pile, 1)
        ids = [entries[0].stringId, entries[2].stringId, entries[1].stringId]
        assert service.drawIds(pile, 3, 0) == ids

        service.dropPosition(entries[2], pile, false)
        assert service.drawIds(pile, 20, 0) == [entries[0].stringId, entries[1].stringId, entries[2].stringId]

        // add one more
        service.put(entries[3], pile, false)
        ids = [entries[0].stringId, entries[1].stringId, entries[3].stringId, entries[2].stringId]
        assert service.drawIds(pile, 4, 0) == ids

        service.put(entries[4], pile, false)
        ids = [entries[0].stringId, entries[1].stringId, entries[4].stringId, entries[3].stringId, entries[2].stringId]
        assert service.drawIds(pile, 20, 0) == ids

        // put second
        service.setPosition(entries[3], pile, 1)
        ids = [entries[0].stringId, entries[3].stringId, entries[1].stringId, entries[4].stringId, entries[2].stringId]
        assert service.drawIds(pile, 5, 0) == ids

        service.dropPosition(entries[3], pile, false)
        ids = [entries[0].stringId, entries[1].stringId, entries[4].stringId, entries[3].stringId, entries[2].stringId]
        assert service.drawIds(pile, 20, 0) == ids

        // test with tail
        service.metaClass.getItemById = {String id -> entries.find {it.stringId == id}
        }
        service.dropPosition(entries[0], pile, true)
        ids = [entries[4].stringId, entries[3].stringId, entries[2].stringId, entries[1].stringId, entries[0].stringId]
        assert service.drawIds(pile, 5, 0) == ids
    }

    private int entriesNum = 0

    private Entry getNewEntry(double pilePos = 0) {
        Entry e = new Entry()
        e.id = entriesNum
        e.dateCreated = new Date((int) pilePos ?: entriesNum, 0, 0)
        entriesNum++
        e
    }
}
