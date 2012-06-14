package mirari.dao

import mirari.security.SecurityCode
import mirari.repo.security.SecurityCodeRepo
import org.springframework.stereotype.Component

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
