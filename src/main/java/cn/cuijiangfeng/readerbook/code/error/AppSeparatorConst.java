package cn.cuijiangfeng.readerbook.code.error;

import java.nio.file.FileSystems;

/**
 * 分隔符常量
 *
 * @author lty
 * @since 1.0
 */
public final class AppSeparatorConst {
    
    /**
     * 无分隔符
     */
    public static final String NO = "";
    /**
     * 冒号分隔符
     */
    public static final String COLON = ":";
    /**
     * 空格分隔符
     */
    public static final String SPACE = " ";
    /**
     * 下划线分隔符
     */
    public static final String UNDERLINE = "_";
    /**
     * 连字符/减号/中横线分隔符
     */
    public static final String HYPHEN = "-";
    /**
     * 栏杆分隔符
     */
    public static final String BAR = "|";
    /**
     * 句号分隔符
     */
    public static final String PERIOD = ".";
    /**
     * 逗号分隔符
     */
    public static final String COMMA = ",";
    /**
     * 等号分隔符
     */
    public static final String EQUAL = "=";
    /**
     * and分隔符
     */
    public static final String AMPERSAND = "&";
    /**
     * 井号分隔符
     */
    public static final String POUND = "#";
    
    /**
     * 系统换行符
     */
    public static final String LINE = System.lineSeparator();
    /**
     * 文件路径分隔符, 同义File.separator
     */
    public static final String FILE = FileSystems.getDefault().getSeparator();
    
    private AppSeparatorConst() {
        throw new AssertionError("Cannot instantiate utility class.");
    }
}

