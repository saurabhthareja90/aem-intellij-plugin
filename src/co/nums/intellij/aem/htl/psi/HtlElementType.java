package co.nums.intellij.aem.htl.psi;

import co.nums.intellij.aem.htl.HtlLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class HtlElementType extends IElementType {

	public HtlElementType(@NotNull @NonNls String debugName) {
		super(debugName, HtlLanguage.INSTANCE);
	}

}
