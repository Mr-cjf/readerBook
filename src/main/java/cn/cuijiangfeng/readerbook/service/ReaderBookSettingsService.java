package cn.cuijiangfeng.readerbook.service;

import cn.cuijiangfeng.readerbook.persistentState.ReaderBookSettings;
import cn.cuijiangfeng.readerbook.tool.Nones;
import cn.cuijiangfeng.readerbook.ui.settings.ReaderBookConfigurable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;


@Service
public final class ReaderBookSettingsService {
    public String getBookReadDir() {
        ReaderBookSettings settings = ApplicationManager.getApplication().getService(ReaderBookSettings.class);
        if (Nones.isNull(settings)) {
            return "";
        }
        if (Nones.isNull(settings.getReaderBookBo())) {
            return "";
        }
        var bookReadDir = settings.getReaderBookBo().getBookReadDir();
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
