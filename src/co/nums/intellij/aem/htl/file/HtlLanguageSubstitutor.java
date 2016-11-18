package co.nums.intellij.aem.htl.file;

import co.nums.intellij.aem.htl.HtlLanguage;
import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.LanguageSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HtlLanguageSubstitutor extends LanguageSubstitutor {

	@Nullable
	@Override
	public Language getLanguage(@NotNull VirtualFile file, @NotNull Project project) {
		// TODO: only files under jcr root should be considered as HTL, it can be also configurable by user
		if (file.getFileType() == HtmlFileType.INSTANCE) {
			return HtlLanguage.INSTANCE;
		}
		return null;
	}

}
