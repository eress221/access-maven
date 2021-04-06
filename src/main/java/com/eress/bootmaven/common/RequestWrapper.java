package com.eress.bootmaven.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {

    public RequestWrapper(HttpServletRequest servletRequest) {
        super((servletRequest));
    }

    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;
    }

    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }

    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }

    private String cleanXSS(String value) {
        String[] result1 = {"SCRIPT", "OBJECT", "APPLET", "EMBED", "FORM", "IFRAME", "ALERT", "IMG", "ONLOAD", "META", "LINK", "STYLE", "DIV", "BACKGROUND", "PROMPT"};
        String[] result2 = {"<", ">", "&", "%", "SRC", "HTTP", "(", ")", "REL","eval\\((.*)\\)","../"};
        String temp1 = "";
        String temp2 = "";
        for (String s : result1) {
            temp1 = s;
            if ((value.toUpperCase()).contains(temp1)) {
                for (String item : result2) {
                    temp2 = item;
                    if ((value.toUpperCase()).contains(temp2)) {
                        value = value.toUpperCase().replaceAll(temp1, "");
                    }
                }
            }
        }
/*
		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("&", "&#38;");
		value = value.replaceAll("%", "&#37;");
		//value = value.replaceAll("!", "&#33;");
		//value = value.replaceAll("-", "&#45;"); //이거 하면 히스토리 날짜에 - 들어가서 리스트에서 안나옴

		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		//value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");//
*/
        return value;
    }
}
