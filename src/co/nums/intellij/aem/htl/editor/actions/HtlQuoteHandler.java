package co.nums.intellij.aem.htl.editor.actions;

import co.nums.intellij.aem.htl.psi.HtlTokenTypes;
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;

public class HtlQuoteHandler extends SimpleTokenSetQuoteHandler {

	public HtlQuoteHandler() {
		super(HtlTokenTypes.SINGLE_QUOTED_STRING, HtlTokenTypes.DOUBLE_QUOTED_STRING);
	}

}
