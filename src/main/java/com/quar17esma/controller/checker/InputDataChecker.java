package com.quar17esma.controller.checker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class InputDataChecker {

     protected boolean isMatches(Pattern pattern, String string) {
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
