package cn.cuijiangfeng.readerbook.persistent;

import cn.cuijiangfeng.readerbook.model.ReaderBookBo;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@State(
        name = "ReaderBookSettings",
        storages = @Storage(value = "readerBookSettings.xml")
)
@Service
public final class ReaderBookSettings implements PersistentStateComponent<ReaderBookSettings> {
    
    private ReaderBookBo readerBookBo = new ReaderBookBo();
    
    @Override
    public @NotNull ReaderBookSettings getState() {
        return this;
    }
    
    @Override
    public void loadState(@NotNull ReaderBookSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }
    
}
   