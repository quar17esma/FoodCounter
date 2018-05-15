package com.quar17esma.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@SuppressWarnings("serial")
public class AbsValue extends TagSupport {

    private int value;

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            int absValue = Math.abs(value);

            pageContext.getOut().write(String.valueOf(absValue));

        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }
}
