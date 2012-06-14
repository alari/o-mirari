package mirari.util.validators

/**
 * @author alari
 * @since 10/28/11 1:55 PM
 */
class NameValidators {
    static final String MATCHER = /^[a-zA-Z0-9][-._a-zA-Z0-9]{0,14}[a-zA-Z0-9]$/

    static final Map CONSTRAINT_MATCHES = [matches: MATCHER, blank: false]
}
