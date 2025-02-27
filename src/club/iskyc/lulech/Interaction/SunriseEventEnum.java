package club.iskyc.lulech.Interaction;

import club.iskyc.lulech.layout.bean.SunriseMethodInfo;
import club.iskyc.lulech.layout.util.BaseConstantValue;
import club.iskyc.lulech.layout.util.SunriseMethodUtil;

/**
 * Enums for SunriseEvent marks.
 *
 * @author lulec
 * @since 1.0
 * */
public enum SunriseEventEnum implements SunriseEventConstantValue, BaseConstantValue {
    INVOKE_METHOD("invokeMethod"),
    CONSOLE_SHOW("consoleShow"),

    ;

    /**
     * String mark for SunriseEvent singable.
     * <em>Don't user {@code '#'} and {@code '.'} in mark string.</em>
     * */
    private final String mark;

    /**
     * Custom function returns String for custom mark get.
     * */
    private SunriseCustomMark customMark;

    /**
     * Create enum {@code SunriseEventEnum}.
     * @see #mark
     * */
    private SunriseEventEnum(String mark) {
        this.mark = mark;
        this.customMark = o -> {
            if (null == o) return "";
            return SunriseMethodUtil.getFullPathOfMethod((SunriseMethodInfo) o);
        };
    }

    /**
     * Create enum {@code SunriseEventEnum}.
     * @see #mark
     * @see #customMark
     * */
    private SunriseEventEnum(String mark, SunriseCustomMark customMark) {
        this.mark = mark;
        this.customMark = customMark;
    }

    /**
     * Get string mark for this event singable.
     * */
    public String getMark() {
        return mark;
    }

    /**
     * Get custom string mark for this event singable.
     * */
    public String getMark(Object o) {
        return mark + eventMarkSplitter + customMark.getCustomMark(o);
    }
}
