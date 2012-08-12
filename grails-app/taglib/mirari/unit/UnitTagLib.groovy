package mirari.unit

import mirari.model.card.Entry
import mirari.beans.content.internal.PageReferenceContentStrategy
import mirari.vm.UnitVM

class UnitTagLib {
    static namespace = "unit"

    def rightsService

    PageReferenceContentStrategy pageReferenceContentStrategy

    def renderPage = {attrs ->
        UnitVM u = (UnitVM) attrs.for
        boolean isOnly = attrs.containsKey("only") ? attrs.only : true
        if (u != null)
            out << g.render(template: "/unit-render/page-".concat(u.type), model: [viewModel: u, only: isOnly])
    }

    def withPageReferenceUnit = {attrs, body ->
        UnitVM u = (UnitVM) attrs.unit
        Entry page = pageReferenceContentStrategy.entry(u)

        if (!page || !rightsService.canView(page)) {
            out << body()
            return
        }
        out << body(page: page)
    }

}
