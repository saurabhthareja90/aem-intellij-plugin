package co.nums.intellij.aem.htl.completion.provider;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.util.text.CharArrayUtil;

/**
 * Base for HTL blocks that will likely define value.
 */
abstract class HtlValueBlockInsertHandler implements InsertHandler<LookupElement> {

	/**
	 * Text to insert after HTL block name, eg.
	 * {@code ="${}"} in {@code data-sly-test="${}"} or
	 * {@code =""} in {@code data-sly-use=""}
	 *
	 * @return insertion text
	 */
	protected abstract String getInsertionString();

	/**
	 * Offset that caret should be scrolled to after text insertion.
	 *
	 * @return caret offset after insertion
	 */
	protected abstract int getInsertionOffset();

	@Override
	public void handleInsert(InsertionContext context, LookupElement item) {
		Editor editor = context.getEditor();
		Document document = editor.getDocument();
		int caretOffset = editor.getCaretModel().getOffset();
		if (!hasBlockValue(document, caretOffset)) {
			insertBlockValue(context, document, caretOffset);
			moveCaretToBlockValue(editor, caretOffset);
		}
	}

	private static boolean hasBlockValue(Document document, int caretOffset) {
		CharSequence chars = document.getCharsSequence();
		return CharArrayUtil.regionMatches(chars, caretOffset, "=\"")
				|| CharArrayUtil.regionMatches(chars, caretOffset, "='");
	}

	private void insertBlockValue(InsertionContext context, Document document, int caretOffset) {
		// check if HTL works when expression contains the same type of quote
		// as attribute value delimiter if not, then handle it here
		String insertionString = getInsertionString();
		if (caretOffset >= document.getTextLength()
				|| "/> \n\t\r".indexOf(document.getCharsSequence().charAt(caretOffset)) < 0) {
			insertionString += " "; // it's copied from IDEA community, what is it for?
		}
		document.insertString(caretOffset, insertionString);
		if (context.getCompletionChar() == '=') {
			context.setAddCompletionChar(false); // IDEA-19449
		}
	}

	private void moveCaretToBlockValue(Editor editor, int caretOffset) {
		editor.getCaretModel().moveToOffset(caretOffset + getInsertionOffset());
		editor.getScrollingModel().scrollToCaret(ScrollType.RELATIVE);
		editor.getSelectionModel().removeSelection();
	}

}
