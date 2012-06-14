package mirari.dao.security

import mirari.security.SecurityCode
import mirari.repo.security.SecurityCodeRepo
import org.springframework.stereotype.Component
import mirari.dao.Dao

/**
 * @author alari
 * @since 5/4/12 6:12 PM
 */
@Component("securityCodeRepo")
class SecurityCodeDao extends Dao<SecurityCode> implements SecurityCodeRepo {
    @Override
    SecurityCode getByToken(String token) {
        SecurityCode.findByToken(token)
    }
}
