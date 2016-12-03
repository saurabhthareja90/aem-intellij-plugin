package co.nums.intellij.aem.htl.file;

import co.nums.intellij.aem.constants.JcrConstants;
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
		if (isHtml(file) && isInJcrRootDirectory(file)) {
			return HtlLanguage.INSTANCE;
		}
		return null;
	}

	private static boolean isHtml(@NotNull VirtualFile file) {
		return file.getFileType() == HtmlFileType.INSTANCE;
	}

	private static boolean isInJcrRootDirectory(@NotNull VirtualFile file) {
		VirtualFile parent = file.getParent();
		while (parent != null) {
			if (JcrConstants.JCR_ROOT_DIRECTORY_NAME.equals(parent.getName())) {
				return true;
			}
			parent = parent.getParent();
		}
		return false;
	}

}
