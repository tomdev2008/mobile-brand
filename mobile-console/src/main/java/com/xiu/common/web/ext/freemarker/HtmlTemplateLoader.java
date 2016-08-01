package com.xiu.common.web.ext.freemarker;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.io.IOUtils;

import freemarker.cache.TemplateLoader;

/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : mike@xiu.com 
 * @DATE :2013-7-24 上午10:11:03
 * </p>
 **************************************************************** 
 */
public class HtmlTemplateLoader implements TemplateLoader {
    private static final String HTML_ESCAPE_PREFIX = "<#escape x as x?html>";
    private static final String HTML_ESCAPE_SUFFIX = "</#escape>";

    private final TemplateLoader delegate;

    public HtmlTemplateLoader(TemplateLoader delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object findTemplateSource(String name) throws IOException {
        return delegate.findTemplateSource(name);
    }

    @Override
    public long getLastModified(Object templateSource) {
        return delegate.getLastModified(templateSource);
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        Reader reader = delegate.getReader(templateSource, encoding);
        String templateText = IOUtils.toString(reader);
        //templateText = covertHtmlString(templateText);
        return new StringReader(HTML_ESCAPE_PREFIX + templateText + HTML_ESCAPE_SUFFIX);
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {
        delegate.closeTemplateSource(templateSource);
    }
}
