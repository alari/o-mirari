package mirari.util.named

import org.apache.commons.lang.RandomStringUtils

/**
 * @author alari
 * @since 2/14/12 5:12 PM
 */
class TitleNameSetter {
    static void setNameFromTitle(TitleNamedDomain domain) {
        if (domain.title) {
            domain.name = domain.title.replaceAll(" ", "_").replaceAll(/[!?&*{}\[\]^\$#@~<>\\|'":;`#]/, "").replaceAll(/[_\+]+/, "_")
            if (domain.name.size() < 2) domain.name = domain.name.concat(RandomStringUtils.randomAlphanumeric(domain.name.size() > 2 ? domain.name.size() - 2 : 1).toLowerCase())
            if(domain.name.size() > 110) domain.name = domain.name.substring(0, 109)
        } else if (!domain.name || domain.name.size() != 5) {
            domain.name = RandomStringUtils.randomAlphanumeric(5).toLowerCase()
        }
    }
}
