package com.flash.common.util.xml;

import com.thoughtworks.xstream.XStream;

/**
 * @author: Edward
 * Date: 13-9-25
 * Time: 下午3:09
 * Description:注解方式的XStreamUtil
 */
public class XStreamUtil {

    public static Object convert2Object(String xml, Class<?> t, String tag) throws Exception {
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        if (tag != null) {
            xStream.alias(tag, t);
        }
        return xStream.fromXML(xml);
    }

    /**
     * object 2 XML格式
     *
     * @param t      对象对应的元素
     * @param o      对象
     * @return
     */
    public static String convert2Xml(Object o, String tag, Class<?> t) {
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        if (tag != null) {
            xStream.alias(tag, t);
        }
        return xStream.toXML(o);
    }
}
