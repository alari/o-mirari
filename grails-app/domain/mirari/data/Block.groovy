package mirari.data

import mirari.Site
import mirari.data.content.ContentHolder
import mirari.data.content.ContentPolicy
import ru.mirari.infra.file.FileInfo

class Block implements ContentHolder {
    static mapWith = "mongo"

    String id

    String type
    String title

    Date dateCreated
    Date lastUpdated

    Site owner

    @Override
    void setContentFile(FileInfo fileInfo) {
        contentPolicy.strategy.setContentFile(this, fileInfo)
    }

    @Override
    void setContentUrl(String url) {
        contentPolicy.strategy.buildContentByUrl(this, url)
    }

    @Override
    void deleteContent() {
        contentPolicy.strategy.deleteContent(this)
    }

    ContentPolicy getContentPolicy() {
        ContentPolicy.getByName(type)
    }

    void setContentPolicy(ContentPolicy contentPolicy) {
        type = contentPolicy.name
    }

    static hasOne = [content: BlockContent]
    static belongsTo = [holder: BlocksHolder]

    static transients = ['contentPolicy']

    static constraints = {
        content nullable: true
    }
}
