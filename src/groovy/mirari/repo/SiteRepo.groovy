package mirari.repo

import mirari.repo.Repo
import mirari.Site
import mirari.security.Account

/**
 * @author alari
 * @since 5/4/12 6:02 PM
 */
interface SiteRepo extends Repo<Site> {
    public Site getByName(String name);

    public boolean nameExists(String name);

    public Site getByHost(String host);

    public boolean hostExists(String host);

    public List<Site> listByAccount(Account account);
}
