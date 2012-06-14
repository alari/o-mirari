package mirari.infra

import org.apache.log4j.Logger
import ru.mirari.infra.feed.Pagination

class MarkupTagLib {
    Logger log = Logger.getLogger(getClass())

    static namespace = "mk"

    def tmpl = {attrs, body ->
        out << /<script language="text\/html" type="text\/html" id="${attrs.id}">/ << body() << /<\/script>/
    }

    def pageHeader = {attrs, body ->
        out << /<div class="entry-header"><h1>/
        out << body()
        out << '</h1></div>'
    }

    def formLine = {attrs, body ->
        def bean = attrs.bean
        String field = attrs.field
        boolean isBlock = attrs.containsKey("isBlock")
        attrs.isBlock

        String label = ""
        if (attrs.labelCode) label = message(code: attrs.labelCode, default: attrs.label)
        else if (attrs.label) label = attrs.label
        else label = field

        String help = ""
        if (attrs.containsKey("helpCode") || attrs.containsKey("help")) {
            help = attrs.containsKey("helpCode") ? message(code: attrs.helpCode, default: attrs.help) : attrs.help
        }

        String beanHasErrors = hasErrors(bean: bean, field: field, " error")
        String helpSpan = ""
        if (beanHasErrors || help) {
            helpSpan = """
        <p class="help-${isBlock ? 'block' : 'inline'}">
          ${fieldError(bean: bean, field: field)}
          ${help}
        </p>
      """
        }

        out << """
    <div class="control-group${beanHasErrors}">
      <label class="control-label" for="${field}">${label}</label>
      <div class="controls">${body()}
        ${helpSpan}
      </div>
    </div>
    """
    }

    def formActions = {attrs, body ->
        out << /<div class="form-actions">/
        out << body()
        out << '</div>'
    }

    def twoBigColumns = {arrts, body ->
        request.mkSidebar = "span4"
        request.mkContent = "span8"
        out << '<div class="row">' + body() + '</div>'
    }

    def withSmallSidebar = {
        arrts, body ->
        request.mkSidebar = "span2"
        request.mkContent = "span10"
        out << '<div class="row">' + body() + '</div>'
    }

    def sidebar = {attrs, body ->
        out << /<div class="/ + request.mkSidebar + /">/
        out << body()
        out << '</div>'
    }

    def content = {attrs, body ->
        out << /<div class="/ + request.mkContent + /">/
        out << body()
        out << '<br/></div>'
    }

    def datetime = {attrs ->
        out << formatDate(date: attrs.date, format: "dd MM yyyy HH:mm")
    }

    /**
     * @attr Pagination pagination
     * @attr int rangeSize
     */
    def pagination = {attrs, body ->
        Pagination pagination = attrs.pagination
        int rangeSize = attrs.rangeSize ?: 2

        if (pagination.empty) {
            return;
        }

        out << /<div class="pagination"><ul>/

        out << /<li class="prev/ << (pagination.hasPrevious() ? '' : ' disabled') << /">/
        out << body(num: pagination.getPrevious(), text: '&larr; Previous')
        out << '</li>'

        pagination.getRange(rangeSize).each {int n ->
            out << /<li/ << (pagination.isActive(n) ? ' class="active"' : '') << />/
            out << body(num: n, text: n + 1)
            out << '</li>'
        }

        out << /<li class="next/ << (pagination.hasNext() ? '' : ' disabled') << /">/
        out << body(num: pagination.getNext(), text: 'Next &rarr;')
        out << '</li>'

        out << '</ul></div>'
    }
}
