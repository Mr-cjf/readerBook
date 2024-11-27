package cn.cuijiangfeng.readerbook.readerbook;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import lombok.extern.slf4j.Slf4j;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class LocalReaderBookAction extends AnAction {
    
    @Override
    public void actionPerformed(AnActionEvent e) {
        System.out.println("打开二级菜单");
        // 创建一个文件选择器描述符，允许选择txt、pdf、epub文件
        FileChooserDescriptor descriptor = new FileChooserDescriptor(
                true,  // 是否可以选择文件
                false, // 是否可以选择文件夹
                false, // 是否可以选择JAR文件
                false, // 是否将JAR文件作为文件选择
                false, // 是否可以选择JAR文件内容
                false  // 是否可以选择多个文件
        );
        
        descriptor.setTitle("选择小说文件");
        descriptor.setDescription("选择txt、pdf或epub格式的小说文件");
        descriptor.setHideIgnored(false);
        descriptor.setShowFileSystemRoots(true);
        
        // 设置可选择的文件扩展名
        descriptor.withExtensionFilter("支持的文件格式", "txt", "pdf", "epub");
        
        // 打开文件选择器
        VirtualFile[] selectedFiles = FileChooser.chooseFiles(descriptor, e.getProject(), null);
        
        // 处理用户选择的文件
        for (VirtualFile file : selectedFiles) {
            String filePath = file.getPath();
            String fileName = file.getName();
            // 在这里添加处理文件的逻辑，例如打开文件进行阅读
            System.out.println("Selected file: " + filePath);
            // 读取并解析txt文件中的章节标题
            List<String> chapterTitles = readChapterTitles(file);
            if (!chapterTitles.isEmpty()) {
                StringBuilder message = new StringBuilder("小说《" + fileName + "》的章节目录：\n");
                for (String chapterTitle : chapterTitles) {
                    message.append(chapterTitle).append("\n");
                }
                Messages.showMessageDialog(e.getProject(), message.toString(), "章节目录", Messages.getInformationIcon());
            } else {
                Messages.showMessageDialog(e.getProject(), "未找到章节标题", "提示", Messages.getWarningIcon());
            }
        }
    }
    
    private List<String> readChapterTitles(VirtualFile file) {
        List<String> chapterTitles = new ArrayList<>();
        Charset charset = detectCharset(file);
        List<Pattern> patterns = getChapterPatterns();
        Map<Pattern, List<String>> matches = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), charset))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("读取到的行内容：{}", line);
                for (Pattern pattern : patterns) {
                    if (isChapterTitle(line, pattern)) {
                        matches.computeIfAbsent(pattern, k -> new ArrayList<>()).add(line.trim());
                    }
                }
            }
        } catch (Exception e) {
            log.error("读取文件时发生错误", e);
        }
        
        // 选择匹配数量最多的模式
        Optional<Map.Entry<Pattern, List<String>>> bestMatch = matches.entrySet().stream()
                                                                      .max(Map.Entry.comparingByValue(Comparator.comparingInt(List::size)));
        if (bestMatch.isPresent()) {
            chapterTitles = bestMatch.get().getValue();
        }
        
        return chapterTitles;
    }
    
    private boolean isChapterTitle(String line, Pattern pattern) {
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }
    
    private List<Pattern> getChapterPatterns() {
        List<Pattern> patterns = new ArrayList<>();
        patterns.add(Pattern.compile("^第\\d+章.*$")); // 匹配 "第X章"
        patterns.add(Pattern.compile("^\\d+\\.\\s+.+$")); // 匹配 "X. 内容"
        // 可以继续添加其他匹配规则
        return patterns;
    }
    
    private Charset detectCharset(VirtualFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            byte[] buf = new byte[4096];
            UniversalDetector detector = new UniversalDetector(null);
            
            int nread;
            while ((nread = inputStream.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            detector.dataEnd();
            
            String encoding = detector.getDetectedCharset();
            if (encoding != null) {
                return Charset.forName(encoding);
            }
        } catch (IOException e) {
            log.error("检测文件编码时发生错误", e);
        }
        return Charset.defaultCharset();
    }
}
