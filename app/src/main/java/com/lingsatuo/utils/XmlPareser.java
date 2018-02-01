package com.lingsatuo.utils;

import java.io.InputStream;
import java.util.List;

/**
 * Created by 15176 on 2017/6/29.
 */

public interface XmlPareser {
    List<LibsMaven> parse(InputStream is) throws Exception;
}
