package mirari.unit

import mirari.model.site.Site
import mirari.model.page.PageType
import mirari.beans.content.internal.FeedContentStrategy
import mirari.repo.SiteRepo
import mirari.vm.UnitVM
import ru.mirari.infra.feed.FeedQuery
import mirari.model.card.Entry

class FeedUnitTagLib {
    static namespace = "unit"

    SiteRepo siteRepo
    def rightsService

    FeedContentStrategy feedContentStrategy

    def withFeedUnit = {attrs, body ->
        UnitVM u = (UnitVM) attrs.unit

        Site owner = siteRepo.getById u.owner.id

        FeedQuery<Entry> feedQuery = feedContentStrategy.feed(u)
        FeedQuery<Entry> drafts = null
        if (rightsService.canSeeDrafts(owner)) {
            drafts = feedContentStrategy.drafts(u)
        }

        int num = Integer.parseInt(u.params.num)
        if (u.params.style in ["blog_grid", "full_grid"]) {
            if (num == 0) num = 1
        }
        feedQuery.paginate(0, num)

        if (!feedQuery.total && (!drafts || !drafts.total)) {
            return;
        }

        out << body(feedParams: [feed: feedQuery, drafts: drafts, num: num, owner: owner])
    }

    def renderFeed = {attrs ->
        Map feedParams = attrs.feedParams
        if (!feedParams) return;
        UnitVM u = (UnitVM) attrs.unit
        if (!u) {
            return
        }
        FeedQuery<Entry> drafts = feedParams.drafts

        FeedQuery<Entry> feedQuery = feedParams.feed

        boolean showTypes = !(u.params.source in PageType.values()*.name)
        Site notShowOwner = feedParams.owner

        if (drafts != null) {
            out << render(template: "/pages-feed/drafts", model: [drafts: drafts, showTypes: showTypes])
        }

        Iterator<Entry> feed = feedQuery.iterator()

        if (u.params.last && u.params.last != feedContentStrategy.STYLE_NONE) {
            Entry first = feed.next()
            Map lastModel = [feed: [first], showTypes: showTypes, notShowOwner: notShowOwner]
            switch (u.params.last) {
                case feedContentStrategy.STYLE_BLOG:
                    out << g.render(template: "/pages-feed/blog", model: lastModel)
                    break
                case feedContentStrategy.STYLE_FULL:
                    out << g.render(template: "/pages-feed/full", model: lastModel)
                    break;
                case feedContentStrategy.STYLE_WIDE:
                default:
                    out << g.render(template: "/pages-feed/wide", model: lastModel)
                    break;
            }
            if (!feed.hasNext()) {
                return;
            }
        }

        Map feedModel = [feed: feed, showTypes: showTypes, notShowOwner: notShowOwner]
        out << g.render(template: "/pages-feed/links", model: feedModel)
    }

}
