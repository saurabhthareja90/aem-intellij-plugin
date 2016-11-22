package co.nums.intellij.aem.htl.editor.braces;

import co.nums.intellij.aem.htl.psi.HtlTokenTypes;
import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HtlBraceMatcher implements PairedBraceMatcher {

	private static final BracePair[] PAIRS = {
			new BracePair(HtlTokenTypes.LEFT_BRACKET, HtlTokenTypes.RIGHT_BRACKET, false),
			new BracePair(HtlTokenTypes.LEFT_PARENTH, HtlTokenTypes.RIGHT_PARENTH, false)
	};

	@Override
	public BracePair[] getPairs() {
		return PAIRS;
	}

	@Override
	public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType,
			@Nullable IElementType contextType) {
		return true;
	}

	@Override
	public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
		return openingBraceOffset;
	}

}
