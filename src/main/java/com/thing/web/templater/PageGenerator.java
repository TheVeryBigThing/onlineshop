package com.thing.web.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private static final String pageDir = "src\\main\\resources";

    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    public static PageGenerator instance(){
        if (pageGenerator == null){
            return new PageGenerator();
        }

        return pageGenerator;
    }

    public String getPage(String fileName){
        return getPage(fileName, null);
    }

    public String getPage(String fileName, Map<String, Object> data) {
        try(Writer stream = new StringWriter()) {

            Template template = cfg.getTemplate(pageDir + File.separator + fileName);
            template.process(data, stream);

            return stream.toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    private PageGenerator() {
        cfg = new Configuration();
    }
}
