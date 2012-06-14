package mirari.security

import mirari.Site
import org.springframework.web.context.request.RequestContextHolder
import mirari.repo.security.AccountRepo
import mirari.repo.SiteRepo

class SecurityService {

    static transactional = false

    def springSecurityService
    SiteRepo siteRepo
    AccountRepo accountRepo

    Account getAccount() {
        loggedIn ? accountRepo.getById(id) : null
    }

    private Site get_site() {
        (Site) RequestContextHolder.currentRequestAttributes().getCurrentRequest()?._site
    }

    // TODO: cache user profiles in UserDetailsService
    Site getProfile() {
        Account account = account
        if (account) {
            if (_site?.account == account) {
                return _site
            }
            return account.mainProfile
        }
        null
    }

    Iterable<Site> getAllProfiles() {
        siteRepo.listByAccount(getAccount())
    }

    List<Site> getRestProfiles() {
        Site mainProfile = getProfile()
        List<Site> rest = []
        for (Site s in getAllProfiles()) {
            if (s == mainProfile) continue;
            rest.add s
        }
        rest
    }

    boolean hasRole(String role) {
        if (!isLoggedIn()) return false;
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role.toUpperCase()
        }
        account.authorities*.authority.contains(role)
    }

    String getName() {
        loggedIn ? springSecurityService.principal.username : null
    }

    String getId() {
        loggedIn ? springSecurityService.principal.id : null
    }

    boolean isLoggedIn() {
        springSecurityService.isLoggedIn()
    }

    String encodePassword(String password) {
        springSecurityService.encodePassword(password)
    }

    def reauthenticate(String username) {
        springSecurityService.reauthenticate(username)
    }

    void logout() {}
}
