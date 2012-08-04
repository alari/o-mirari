package mirari.dao.security

import mirari.security.Account
import mirari.repo.security.AccountRepo
import org.springframework.stereotype.Component
import mirari.dao.Dao

/**
 * @author alari
 * @since 5/4/12 6:21 PM
 */
@Component("accountRepo")
class AccountDao extends Dao<Account> implements AccountRepo {
    @Override
    Account getById(id) {
        Account.get(id)
    }

    @Override
    boolean emailExists(String email) {
        Account.countByEmail(email) > 0
    }

    @Override
    Account getByEmail(String email) {
        Account.findByEmail(email)
    }
}
