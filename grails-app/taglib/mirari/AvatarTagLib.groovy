package mirari

//import mirari.model.Avatar

class AvatarTagLib {
    static namespace = "avatar"

    def securityService
    def avatarService

    def large = {attrs ->
        def holder = null
        if (attrs.for) {
            holder = attrs.for
        } else if (securityService.isLoggedIn()) {
            holder = securityService.profile
        }
        if (!holder) {
            log.error "Cannot get large avatar for unknown space"
            return;
        }

        String url = avatarService.getUrl(holder, Avatar.IM_SMALL)

        out << /<img src="${url}"\/>/
    }
}
