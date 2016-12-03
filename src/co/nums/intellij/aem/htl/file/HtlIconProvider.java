package co.nums.intellij.aem.htl.file;

import co.nums.intellij.aem.htl.icons.HtlIcons;
import co.nums.intellij.aem.htl.psi.HtlFile;
import com.intellij.ide.IconProvider;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class HtlIconProvider extends IconProvider {

	@Nullable
	@Override
	public Icon getIcon(@NotNull PsiElement element, @Iconable.IconFlags int flags) {
		if (element instanceof HtlFile) {
			return HtlIcons.HTL_FILE;
		}
		return null;
	}

}
