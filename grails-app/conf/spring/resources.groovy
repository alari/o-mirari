import ru.mirari.infra.I18n
import ru.mirari.infra.ApplicationContextHolder
import grails.util.Environment
import java.util.concurrent.Executors
import org.springframework.context.event.SimpleApplicationEventMulticaster
import mirari.security.UserDetailsService

// Place your Spring DSL code here
beans = {
    userDetailsService(UserDetailsService)

    // Misc
    i18n(I18n)
    localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
        defaultLocale = new Locale("ru","RU")
        java.util.Locale.setDefault(defaultLocale)
    }

    applicationContextHolder(ApplicationContextHolder) { bean ->
        bean.factoryMethod = 'getInstance'
    }

    // Events multicaster
    applicationEventMulticaster(SimpleApplicationEventMulticaster) {
        taskExecutor = Executors.newCachedThreadPool()
    }
}
