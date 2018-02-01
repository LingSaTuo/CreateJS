package com.lingsatuo.utils;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15176 on 2017/6/29.
 */

public class xmlreader implements XmlPareser  {
    @Override
    public List<LibsMaven> parse(InputStream is) throws Exception {
        List<LibsMaven> books = null;
        LibsMaven book = null;

//      XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//      XmlPullParser parser = factory.newPullParser();

        XmlPullParser parser = Xml.newPullParser(); //由android.util.Xml创建一个XmlPullParser实例
        parser.setInput(is, "UTF-8");               //设置输入流 并指明编码方式

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    books = new ArrayList<LibsMaven>();
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("lib")) {
                        book = new LibsMaven();
                    } else if (parser.getName().equals("name")) {
                        eventType = parser.next();
                        book.Name = parser.getText();
                    } else if (parser.getName().equals("download")) {
                        eventType = parser.next();
                        book.DownUri=parser.getText();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("lib")) {
                        books.add(book);
                        book = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return books;
    }
}
