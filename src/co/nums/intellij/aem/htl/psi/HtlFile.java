package co.nums.intellij.aem.htl.psi;

import co.nums.intellij.aem.htl.HtlLanguage;
import co.nums.intellij.aem.htl.file.HtlFileType;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class HtlFile extends PsiFileBase {

	public HtlFile(@NotNull FileViewProvider viewProvider) {
		this(viewProvider, HtlLanguage.INSTANCE);
	}

	public HtlFile(@NotNull FileViewProvider viewProvider, Language lang) {
		super(viewProvider, lang);
	}

	@NotNull
	@Override
	public FileType getFileType() {
		return HtlFileType.INSTANCE;
	}

	@Override
	public String toString() {
		return "HTL File:" + getName();
	}

}
