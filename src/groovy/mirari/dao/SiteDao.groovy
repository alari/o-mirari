package mirari.dao

import mirari.Site
import org.springframework.stereotype.Component
import mirari.repo.SiteRepo
import mirari.security.Account

/**
 * @author alari
 * @since 5/4/12 6:08 PM
 */
@Component("siteRepo")
class SiteDao extends Dao<Site> implements SiteRepo {

    @Override
    Site getByName(String name) {
        Site.findByName(name)
    }

    @Override
    boolean nameExists(String name) {
        Site.countByName(name) > 0
    }

    @Override
    Site getByHost(String host) {
        Site.findByHost(host)
    }

    @Override
    boolean hostExists(String host) {
        Site.countByHost(host) > 0
    }

    @Override
    List<Site> listByAccount(final Account account) {
        Site.findAllByAccount(account)
    }
}
