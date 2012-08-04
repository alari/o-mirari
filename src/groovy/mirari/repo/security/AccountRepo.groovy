package mirari.repo.security

import mirari.security.Account
import mirari.repo.Repo

/**
 * @author alari
 * @since 5/4/12 6:02 PM
 */
interface AccountRepo extends Repo<Account> {
    Account getById(id)

    boolean emailExists(String email)

    Account getByEmail(String email)
}
