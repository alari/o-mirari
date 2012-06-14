package mirari.repo.security

import mirari.repo.Repo
import mirari.security.SecurityCode

/**
 * @author alari
 * @since 5/4/12 6:02 PM
 */
interface SecurityCodeRepo extends Repo<SecurityCode> {
    public SecurityCode getByToken(String token);
}
