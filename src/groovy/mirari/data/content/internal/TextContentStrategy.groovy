package mirari.data.content.internal


import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.TextProcessUtil
import ru.mirari.infra.file.FileInfo
import mirari.data.content.ContentHolder
import mirari.data.BlockContent
import mirari.repo.data.BlockContentRepo
/**
 * @author alari
 * @since 1/6/12 5:41 PM
 */
class TextContentStrategy extends InternalContentStrategy {
    @Autowired private BlockContentRepo blockContentRepo

    /*@Override
    void attachContentToViewModel(ContentHolder holder, BlockVM blockVM) {
        blockVM.params.text = holder.content?.text
        blockVM.params.html = TextProcessUtil.markdownToHtml(blockVM.params.text)
    }

    @Override
    void setViewModelContent(ContentHolder holder, BlockVM blockVM) {
        if (!holder.content) holder.content = new BlockContent()
        holder.content.text = blockVM.params.text
    }          */

    @Override
    void setContentFile(ContentHolder unit, FileInfo fileInfo) {
        if (!isContentFileSupported(fileInfo)) return;
        if (!unit.content) unit.content = new BlockContent()
        
        unit.title = fileInfo.title
        
        if(fileInfo.extension.equalsIgnoreCase("txt")) {
            unit.content.text = fileInfo.file.getText()
        } else if(fileInfo.extension in ["htm", "html"]) {
            unit.content.text = TextProcessUtil.htmlToMarkdown(fileInfo.file.getText())
        }
    }

    @Override
    boolean isEmpty(ContentHolder unit) {
        !unit?.content || !unit.content?.text
    }

    @Override
    boolean isContentFileSupported(FileInfo type) {
        type.extension in ["txt", "htm", "html"]
    }

    @Override
    void saveContent(ContentHolder unit) {
        if (unit.content) {
            blockContentRepo.save(unit.content)
        }
    }

    @Override
    void deleteContent(ContentHolder unit) {
        if (unit.content) {
            blockContentRepo.delete(unit.content)
            unit.content = null
        }
    }
}
