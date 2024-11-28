package cn.cuijiangfeng.readerbook.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author 崔江枫
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
public class BookReadParseBo implements Serializable {
    /**
     * 小说名称
     */
    private String bookName;
    /**
     * 当前阅读章节
     */
    private String bookReadChapter;
    /**
     * 当前阅读章节内容
     */
    private String bookReadContent;
    /**
     * 小说章节目录
     */
    private List<String> bookReadParse;
}