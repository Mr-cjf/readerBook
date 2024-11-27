package cn.cuijiangfeng.readerbook.readerbook;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class ReaderBookAction extends AnAction {
    
    
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("打开二级菜单");
        
    }
    
    
}

