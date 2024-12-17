package cn.cuijiangfeng.readerbook.ui.settings;

import cn.cuijiangfeng.readerbook.persistent.ReaderBookSettings;
import cn.cuijiangfeng.readerbook.service.ReaderBookSettingsService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ui.JBUI;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class ReaderBookConfigurable implements SearchableConfigurable {
    private JPanel myPanel;
    private JTextField lastReadBookPathField;
    
    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "ReaderBook Settings";
    }
    
    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }
    
    @Nullable
    @Override
    public JComponent createComponent() {
        myPanel = new JPanel();
        myPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = JBUI.insets(5); // 设置组件之间的间距
        
        JLabel label = new JLabel("小说读取路径:");
        lastReadBookPathField = new JTextField(1); // 减少列数，使输入框更小
        JButton browseButton = new JButton("浏览");
        
        // 设置标签的位置
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0;
        myPanel.add(label, gbc);
        
        // 设置输入框的位置
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        myPanel.add(lastReadBookPathField, gbc);
        
        // 设置按钮的位置
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        myPanel.add(browseButton, gbc);
        
        // 添加一个占位符组件，确保后续组件不会影响顶部组件的位置
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3; // 占据三列
        gbc.weighty = 1.0; // 垂直方向上占据剩余空间
        myPanel.add(new JPanel(), gbc);
        
        browseButton.addActionListener(e -> {
            FileChooserDescriptor descriptor = new FileChooserDescriptor(false, true, false, false, false, false);
            VirtualFile[] selectedFiles = FileChooser.chooseFiles(descriptor, null, null);
            if (selectedFiles.length > 0) {
                VirtualFile selectedFile = selectedFiles[0];
                lastReadBookPathField.setText(selectedFile.getPath());
            }
        });
        
        return myPanel;
    }
    
    
    @Override
    public boolean isModified() {
        return !lastReadBookPathField.getText().equals(getBookReadDir());
    }
    
    @Override
    public void apply() {
        ReaderBookSettings settings = ApplicationManager.getApplication().getService(ReaderBookSettings.class);
        settings.getReaderBookBo().setBookReadDir(lastReadBookPathField.getText());
    }
    
    @Override
    public void reset() {
        lastReadBookPathField.setText(getBookReadDir());
    }
    
    @Override
    public void disposeUIResources() {
        myPanel = null;
        lastReadBookPathField = null;
    }
    
    @NotNull
    @Override
    public String getId() {
        return "ReaderBookSettings";
    }
    
    @Override
    public Runnable enableSearch(String option) {
        return null;
    }
    
    private String getBookReadDir() {
        ReaderBookSettingsService readerBookSettingsService = ApplicationManager.getApplication()
                                                                                .getService(ReaderBookSettingsService.class);
        return readerBookSettingsService.getBookReadDir();
    }
}
     