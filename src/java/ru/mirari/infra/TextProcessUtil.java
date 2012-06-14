package ru.mirari.infra;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.springframework.core.io.ClassPathResource;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author alari
 * @since 2/2/12 5:33 PM
 */
public class TextProcessUtil {
    static private ThreadLocal<PegDownProcessor> processorThreadLocal = new ThreadLocal<PegDownProcessor>();
    static private ThreadLocal<Transformer> transformerThreadLocal = new ThreadLocal<Transformer>();
    static private final Logger log = Logger.getLogger(TextProcessUtil.class);

    static public PegDownProcessor getPegDownProcessor() {
        if (processorThreadLocal.get() == null) {
            processorThreadLocal.set(new PegDownProcessor(Extensions.ALL));
        }
        return processorThreadLocal.get();
    }

    static public Transformer getTransformer() {
        if (transformerThreadLocal.get() == null) {
            try {
                File xsltFile = new ClassPathResource("markdown.xsl", TextProcessUtil.class).getFile();

                Source xsltSource = new StreamSource(xsltFile);

                TransformerFactory transFact =
                        TransformerFactory.newInstance();
                Transformer trans = transFact.newTransformer(xsltSource);
                transformerThreadLocal.set(trans);
            } catch (Exception e) {
                log.error("Cannot create XSLT transformer!", e);
            }
        }
        transformerThreadLocal.get().reset();
        return transformerThreadLocal.get();
    }

    static public String cleanHtml(String unsafe) {
        return unsafe == null || unsafe.isEmpty() ? "" : Jsoup.clean(unsafe, Whitelist.basic());
    }

    static public String markdownToHtml(String text) {
        return text != null ? cleanHtml(getPegDownProcessor().markdownToHtml(text)) : "";
    }

    static public String htmlToMarkdown(String theHTML) {
        theHTML = "<?xml version='1.0' encoding='utf-8'?>\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><body>"
                .concat(Jsoup.clean(theHTML, Whitelist.relaxed()))
                .concat("</body></html>");
        try {

            Source xmlSource = new StreamSource(new StringReader(theHTML));

            StringWriter result = new StringWriter();
            getTransformer().transform(xmlSource, new StreamResult(result));

            return result.toString();
        } catch (Exception e) {
            log.error("Error while parsing html to markdown!", e);
        }
        return "";

    }
}
