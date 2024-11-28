package cn.cuijiangfeng.readerbook.model;

import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 崔江枫
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@State(
        name = "ReaderBookSettings",
        storages = @Storage(value = "readerBookSettings.xml")
)
public class ReaderBookBo implements java.io.Serializable {
    /**
     * 小说读取目录
     */
    private String bookReadDir;
    /**
     * 当前阅读小说
     */
    private String bookReadName;
    /**
     * 当前阅读章节
     */
    private String bookReadChapter;
    /**
     * 小说阅读记录
     */
    private List<BookReadParseBo> bookReadParseBoList;
}