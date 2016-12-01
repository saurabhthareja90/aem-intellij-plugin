package co.nums.intellij.aem.htl.completion.provider.inserthandlers;

import com.intellij.codeInsight.AutoPopupController;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.util.text.CharArrayUtil;

public class HtlExpressionOptionQuotesInsertHandler implements InsertHandler<LookupElement> {

	public static final HtlExpressionOptionQuotesInsertHandler INSTANCE = new HtlExpressionOptionQuotesInsertHandler();

	@Override
	public void handleInsert(InsertionContext context, LookupElement item) {
		Editor editor = context.getEditor();
		Document document = editor.getDocument();
		int caretOffset = editor.getCaretModel().getOffset();
		if (!hasQuotes(document, caretOffset)) {
			insertQuotes(context, document, caretOffset);
			moveCaretIntoQuotes(editor, caretOffset);
			// FIXME: invoke only if needed
			AutoPopupController.getInstance(context.getProject()).scheduleAutoPopup(editor);
		}
	}

	private static boolean hasQuotes(Document document, int caretOffset) {
		CharSequence chars = document.getCharsSequence();
		return CharArrayUtil.regionMatches(chars, caretOffset, "=\"")
				|| CharArrayUtil.regionMatches(chars, caretOffset, "='");
	}

	private void insertQuotes(InsertionContext context, Document document, int caretOffset) {
		// check if HTL works when expression contains the same type of quote
		// as attribute value delimiter if not, then handle it here
		document.insertString(caretOffset, "=''");
		if (context.getCompletionChar() == '=') {
			context.setAddCompletionChar(false); // IDEA-19449
		}
	}

	private void moveCaretIntoQuotes(Editor editor, int caretOffset) {
		editor.getCaretModel().moveToOffset(caretOffset + 2);
		editor.getScrollingModel().scrollToCaret(ScrollType.RELATIVE);
		editor.getSelectionModel().removeSelection();
	}

}
