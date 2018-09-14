/*
+--------------------------------------------------------------------------
|   Mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
+---------------------------------------------------------------------------
*/
package com.unzen.web.message.web.formatter;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

/**
 * Created by langhsu on 2017/9/2.
 */
public class StringEscapeEditor extends PropertyEditorSupport {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private boolean escapeHTML;// 编码HTML
    private boolean escapeJavaScript;// 编码JavaScript

    public StringEscapeEditor() {
        super();
    }

    public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript) {
        super();
        this.escapeHTML = escapeHTML;
        this.escapeJavaScript = escapeJavaScript;
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : "";
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null) {
            setValue(null);
        } else {
            String value = text;
            if (escapeHTML) {
                value = HtmlUtils.htmlEscape(value);
//                logger.debug("value:" + value);
            }
            if (escapeJavaScript) {
//                value = StringEscapeUtils.escapeJavaScript(value);
//                logger.debug("value:" + value);
            }
            setValue(value);
        }
    }
}
