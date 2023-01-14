package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Utils;

/**
 * The type Template.
 */
public class Template {
    private String templateBody;

    public Template(String templateBodyName) {
        this.templateBody = Utils.readFileAsString(templateBodyName);
    }

    public String getTemplateBody() {
        return templateBody;
    }

    public void setTemplateBody(String templateBody) {
        this.templateBody = templateBody;
    }
}
