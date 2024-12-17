package cn.cuijiangfeng.readerbook.service;

import cn.cuijiangfeng.readerbook.persistent.ReaderBookSettings;
import cn.cuijiangfeng.readerbook.tool.Nones;
import cn.cuijiangfeng.readerbook.ui.settings.ReaderBookConfigurable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;


@Service
public final class ReaderBookSettingsService {
    /**
     * 获取小说读取目录
     * <p>
     * 该方法使用ApplicationManager的实例来获取ReaderBookSettings的实例 如果settings为null或者settings.getReaderBookBo()为null，则返回空字符串
     * <p>
     * 如果bookReadDir不为空，则返回bookReadDir 如果bookReadDir为空，则返回空字符串
     *
     * @return 小说读取目录
     */
    public String getBookReadDir() {
        // 获取ReaderBookSettings实例
        ReaderBookSettings settings = ApplicationManager.getApplication().getService(ReaderBookSettings.class);
        // 检查settings和settings.getReaderBookBo()是否为null
        if (Nones.isNull(settings) || Nones.isNull(settings.getReaderBookBo())) return "";
        // 获取小说读取目录
        var bookReadDir = settings.getReaderBookBo().getBookReadDir();
        // 根据bookReadDir是否为空，返回相应的结果
        return Nones.nonBlank(bookReadDir) ? bookReadDir : "";
    }
    
    
    /**
     * 打开设置界面
     * <p>
     * 该方法使用ShowSettingsUtil的实例来编辑与项目相关的配置它创建了一个ReaderBookConfigurable实例， 以便在设置界面中显示相关的配置选项
     *
     * @param project 可选参数，表示当前操作的项目如果为null，表示没有特定的项目上下文
     */
    public void openSettings(@NotNull Project project) {
        ShowSettingsUtil.getInstance().showSettingsDialog(project, new ReaderBookConfigurable());
    }
}
