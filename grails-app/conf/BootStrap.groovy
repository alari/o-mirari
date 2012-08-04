import ru.mirari.infra.ApplicationContextHolder
import mirari.Site
import mirari.repo.SiteRepo
import mirari.security.SiteKind
import grails.util.Environment
import grails.plugin.redis.RedisService

class BootStrap {

    def init = { servletContext ->

        SiteRepo siteRepo = (SiteRepo)ApplicationContextHolder.getBean("siteRepo")

        String mainHost = ApplicationContextHolder.config.mirari.mainPortal.host
        String mainTitle = ApplicationContextHolder.config.mirari.mainPortal.displayName

        if(siteRepo.getByHost(mainHost)) return;

        Site portal = new Site(kind: SiteKind.PORTAL, host: mainHost, name: mainHost, displayName: mainTitle)
        siteRepo.save(portal)
        if(portal.hasErrors()) {
            throw new Exception(portal.errors.toString())
        }
        println "Portal saved: "+portal

        if(Environment.isDevelopmentMode()) {
            RedisService redisService = (RedisService)ApplicationContextHolder.getBean("redisService");
            redisService.deleteKeysWithPattern("*")
            println "Deleting All Redis Keys"
        }

    }
    def destroy = {
    }
}
