package utils;

import java.util.HashMap;
import java.util.Map;

public final class SuperscriptUtil {

    public static Map<Integer, SuperscriptEnum> digitToSuperscript = new HashMap<>(){
        {
            put(0, SuperscriptEnum.ZERO);
            put(1, SuperscriptEnum.ONE);
            put(2, SuperscriptEnum.TWO);
            put(3, SuperscriptEnum.THREE);
            put(4, SuperscriptEnum.FOUR);
            put(5, SuperscriptEnum.FIVE);
            put(6, SuperscriptEnum.SIX);
            put(7, SuperscriptEnum.SEVEN);
            put(8, SuperscriptEnum.EIGHT);
            put(9, SuperscriptEnum.NINE);

        }
    };
}
