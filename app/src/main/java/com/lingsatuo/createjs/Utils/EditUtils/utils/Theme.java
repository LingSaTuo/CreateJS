package com.lingsatuo.createjs.Utils.EditUtils.utils;

import android.graphics.Color;

import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.utils.Utils;
import com.myopicmobile.textwarrior.common.ColorScheme;

/**
 * Created by Administrator on 2017/12/1.
 */

public class Theme extends ColorScheme {
    private final String path;

    public Theme(String path) throws CreateJSIOException {
        this.path = path;
        createColor();
    }

    void createColor() throws CreateJSIOException {
        String str = Utils.readStringFromFile(this.path);
        String[] key = str.split("\n");
        int line = 0;
        for (int a = 0; a < key.length; a++) {
            line++;
            key[a] = key[a].replaceAll(" ", "");
            key[a] = key[a].replaceAll("\n", "");
            key[a] = key[a].replaceAll("\t", "");
            key[a] = key[a].replaceAll("\r", "");
            key[a] = key[a].replaceAll(";", "");
            if (key[a].indexOf("//")==0) continue;
            if (key[a].equals("")) continue;
            String[] values = key[a].split("=");
            if (values.length < 2)
                values = key[a].split(":");
            try {
                if (values.length < 2) {
                    throw new Exception();
                }
                String colors = values[1];
                if (colors.startsWith("#")) {
                    int color = Color.parseColor(colors);
                    setColor(values[0], color);
                } else if (colors.toLowerCase().indexOf("0x") == 0) {
                    String s;
                    if (colors.length() != 10)
                        s = colors.toLowerCase().replaceAll("0x", "#00");
                    else
                        s = colors.toLowerCase().replaceAll("0x", "#");
                    int color = Color.parseColor(s);
                    setColor(values[0], color);
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                if (values.length < 2)
                    throw new CreateJSIOException("Illegal color :\"" + key[a] +
                            "\""+" , at line " + line);
                throw new CreateJSIOException("Illegal color :\"" + values[1] +
                        "\" at name " + values[0] + ", color " + values[1] + " , at line " + line);
            }
        }
    }

    @Override
    public boolean isDark() {
        return true;
    }

    void setColor(String key, int color) {
        switch (key) {
            case "DEFAULT.KEY_WORDS":
                setColor(Colorable.KEYWORD, color);//前景色);
                color();
                break;
            case "DEFAULT.PARENTHESES":
                setColor(Colorable.LR, color);//前景色);
                color();
                break;


            case "EDITOR.DEFAULT_TEXT":
                setColor(Colorable.FOREGROUND, color);//前景色);
                break;
            case "EDITOR.BACKGROUND":
                setColor(Colorable.BACKGROUND, color);//前景色);
                System.out.println(color);
                break;
            case "EDITOR.LINE_NUMBER":
                setColor(Colorable.NON_PRINTING_GLYPH, color);//前景色);
                break;
            case "EDITOR.INPUT_LINE":
                setColor(Colorable.LINE_HIGHLIGHT, color);//前景色);
                break;
            case "EDITOR.SELECT_TEXT":
                setColor(Colorable.SELECTION_FOREGROUND, color);//前景色);
                break;
            case "EDITOR.SELECT_BACKGROUND":
                setColor(Colorable.SELECTION_BACKGROUND, color);//前景色);
                break;////////
            case "EDITOR.CARET_DISABLED":
                setColor(Colorable.CARET_DISABLED, color);//前景色);
                break;
            case "EDITOR.CARET_BACKGROUND":
                setColor(Colorable.CARET_BACKGROUND, color);//前景色);
                break;
            case "EDITOR.CARET_FOREGROUND":
                setColor(Colorable.CARET_FOREGROUND, color);//前景色);
                break;


            case "KEYWORDS.BREAK":
                setColor(Colorable.BREAK, color);//前景色);
                break;
            case "KEYWORDS.CASE":
                setColor(Colorable.CASE, color);//前景色);
                break;
            case "KEYWORDS.CATCH":
                setColor(Colorable.CATCH, color);//前景色);
                break;
            case "KEYWORDS.CLASS":
                setColor(Colorable.CLASS, color);//前景色);
                break;
            case "KEYWORDS.CONST":
                setColor(Colorable.CONST, color);//前景色);
                break;
            case "KEYWORDS.CONTINUE":
                setColor(Colorable.CONTINUE, color);//前景色);
                break;
            case "KEYWORDS.DEBUGGER":
                setColor(Colorable.DEBUGGER, color);//前景色);
                break;
            case "KEYWORDS.DEFAULT":
                setColor(Colorable.DEFAULT, color);//前景色);
                break;
            case "KEYWORDS.DELETE":
                setColor(Colorable.DELETE, color);//前景色);
                break;
            case "KEYWORDS.DO":
                setColor(Colorable.DO, color);//前景色);
                break;
            case "KEYWORDS.ELSE":
                setColor(Colorable.ELSE, color);//前景色);
                break;
            case "KEYWORDS.EXPORT":
                setColor(Colorable.EXPORT, color);//前景色);
                break;
            case "KEYWORDS.EXTEDNS":
                setColor(Colorable.EXTEDNS, color);//前景色);
                break;
            case "KEYWORDS.FINALLY":
                setColor(Colorable.FINALLY, color);//前景色);
                break;
            case "KEYWORDS.FOR":
                setColor(Colorable.FOR, color);//前景色);
                break;
            case "KEYWORDS.FUNCTION":
                setColor(Colorable.FUNCTION, color);//前景色);
                break;
            case "KEYWORDS.IF":
                setColor(Colorable.IF, color);//前景色);
                break;
            case "KEYWORDS.IMPORT":
                setColor(Colorable.IMPORT, color);//前景色);
                break;
            case "KEYWORDS.IN":
                setColor(Colorable.IN, color);//前景色);
                break;
            case "KEYWORDS.INSTANCEOF":
                setColor(Colorable.INSTANCEOF, color);//前景色);
                break;
            case "KEYWORDS.NEW":
                setColor(Colorable.NEW, color);//前景色);
                break;
            case "KEYWORDS.RETURN":
                setColor(Colorable.RETURN, color);//前景色);
                break;
            case "KEYWORDS.SUPER":
                setColor(Colorable.SUPER, color);//前景色);
                break;
            case "KEYWORDS.SWITCH":
                setColor(Colorable.SWITCH, color);//前景色);
                break;
            case "KEYWORDS.THIS":
                setColor(Colorable.THIS, color);//前景色);
                break;
            case "KEYWORDS.THROW":
                setColor(Colorable.THROW, color);//前景色);
                break;
            case "KEYWORDS.TRY":
                setColor(Colorable.TRY, color);//前景色);
                break;
            case "KEYWORDS.TYPEOF":
                setColor(Colorable.TYPEOF, color);//前景色);
                break;
            case "KEYWORDS.VAR":
                setColor(Colorable.VAR, color);//前景色);
                break;
            case "KEYWORDS.VOID":
                setColor(Colorable.VOID, color);//前景色);
                break;
            case "KEYWORDS.WHILE":
                setColor(Colorable.WHILE, color);//前景色);
                break;
            case "KEYWORDS.LET":
                setColor(Colorable.LET, color);//前景色);
                break;
            case "KEYWORDS.WITH":
                setColor(Colorable.WITH, color);//前景色);
                break;
            case "KEYWORDS.YIELD":
                setColor(Colorable.YIELD, color);//前景色);
                break;
            case "KEYWORDS.LIBS_INTHIS":
                setColor(Colorable.LIBS_INTHIS, color);//前景色);
                break;
            case "KEYWORDS.TRUE":
                setColor(Colorable.TRUE, color);//前景色);
                break;
            case "KEYWORDS.FALSE":
                setColor(Colorable.FALSE, color);//前景色);
                break;
            case "KEYWORDS.NULL":
                setColor(Colorable.NULL, color);//前景色);
                break;
            case "KEYWORDS.UNDEFINED":
                setColor(Colorable.UNDEFINED, color);//前景色);
                break;

            case "OTHER.INTEGER_LITERAL":
                setColor(Colorable.INTEGER_LITERAL, color);//前景色);
                break;
            case "OTHER.FLOATING_POINT_LITERAL":
                setColor(Colorable.INTEGER_LITERAL, color);//前景色);
                break;
            case "OTHER.COMMENTS":
                setColor(Colorable.COMMENT, color);//前景色);
                break;
            case "OTHER.STRING_LITERAL":
                setColor(Colorable.STRING, color);//前景色);
                break;
            case "OTHER.VARNAME":
                setColor(Colorable.VARNAME, color);//前景色);
                break;
            case "OTHER.FUNCTIONNAME":
                setColor(Colorable.FUNCTIONNAME, color);//前景色);
                break;

            case "PARENTHESES.LPAREN":
            case "PARENTHESES.RPAREN":
            case "PARENTHESES.LBRACE":
            case "PARENTHESES.RBRACE":
            case "PARENTHESES.LBRACK":
            case "PARENTHESES.RBRACK":
                setColor(Colorable.LR, color);//前景色);
                break;
            case "PARENTHESES.SEMICOLON":
            case "PARENTHESES.COMMA":
            case "PARENTHESES.DOT":
            case "PARENTHESES.EQ":
            case "PARENTHESES.SPACE":
            case "PARENTHESES.DIV":
            case "PARENTHESES.GT":
            case "PARENTHESES.LT":
            case "PARENTHESES.NOT":
            case "PARENTHESES.COMP":
            case "PARENTHESES.QUESTION":
            case "PARENTHESES.AT":
            case "PARENTHESES.COLON":
            case "PARENTHESES.PLUS":
            case "PARENTHESES.MINUS":
            case "PARENTHESES.MULT":
            case "PARENTHESES.AND":
            case "PARENTHESES.OR":
            case "PARENTHESES.XOR":
            case "PARENTHESES.MOD":
                setColor(Colorable.OPERATOR, color);//前景色);
                break;
        }
    }
}
