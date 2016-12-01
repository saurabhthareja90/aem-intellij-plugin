package co.nums.intellij.aem.htl.completion.provider.inserthandlers;

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
public abstract class HtlValueBlockInsertHandler implements InsertHandler<LookupElement> {

	private final String insertionString;

	private final int insertionCaretOffset;

	/**
	 * @param insertionString
	 * 		text to insert after HTL block name, eg.
	 * 		{@code ="${}"} in {@code data-sly-test="${}"} or
	 * 		{@code =""} in {@code data-sly-use=""}
	 * @param insertionCaretOffset
	 * 		offset that caret should be moved to after string insertion
	 */
	HtlValueBlockInsertHandler(String insertionString, int insertionCaretOffset) {
		this.insertionString = insertionString;
		this.insertionCaretOffset = insertionCaretOffset;
	}

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
		String toInsert = insertionString;
		if (caretOffset >= document.getTextLength()
				|| "/> \n\t\r".indexOf(document.getCharsSequence().charAt(caretOffset)) < 0) {
			toInsert += " "; // it's copied from IDEA community, what is it for?
		}
		document.insertString(caretOffset, toInsert);
		if (context.getCompletionChar() == '=') {
			context.setAddCompletionChar(false); // IDEA-19449
		}
	}

	private void moveCaretToBlockValue(Editor editor, int caretOffset) {
		editor.getCaretModel().moveToOffset(caretOffset + insertionCaretOffset);
		editor.getScrollingModel().scrollToCaret(ScrollType.RELATIVE);
		editor.getSelectionModel().removeSelection();
	}

}
