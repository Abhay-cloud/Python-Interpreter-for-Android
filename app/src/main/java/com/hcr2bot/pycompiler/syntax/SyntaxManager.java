package com.hcr2bot.pycompiler.syntax;

import android.content.Context;

import com.amrdeveloper.codeview.CodeView;

public class SyntaxManager {
    public static void applyMonokaiTheme(Context context, CodeView codeView, Language language) {
        switch (language) {
            case PYTHON:
                PythonSyntaxUtils.applyMonokaiTheme(context, codeView);
                break;
        }
    }

    public static void applyNoctisWhiteTheme(Context context, CodeView codeView, Language language) {
        switch (language) {
            case PYTHON:
                PythonSyntaxUtils.applyNoctisWhiteTheme(context, codeView);
                break;
        }
    }

    public static void applyFiveColorsDarkTheme(Context context, CodeView codeView, Language language) {
        switch (language) {
            case PYTHON:
                PythonSyntaxUtils.applyFiveColorsDarkTheme(context, codeView);
                break;
        }
    }

    public static void applyOrangeBoxTheme(Context context, CodeView codeView, Language language) {
        switch (language) {
            case PYTHON:
                PythonSyntaxUtils.applyOrangeBoxTheme(context, codeView);
                break;
        }
    }
}
