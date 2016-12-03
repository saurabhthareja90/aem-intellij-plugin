package co.nums.intellij.aem.htl.editor.actions;

import co.nums.intellij.aem.htl.HtlLanguage;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class HtlTypedHandler extends TypedHandlerDelegate {

	@Override
	public Result beforeCharTyped(char charTyped, Project project, Editor editor, PsiFile file, FileType fileType) {
		if (charTyped == '}' && isHtl(file)) {
			int offset = editor.getCaretModel().getOffset();
			if (offset < 3 || offset >= editor.getDocument().getTextLength()) {
				return Result.CONTINUE;
			}
			String nextChar = editor.getDocument().getText(new TextRange(offset, offset + 1));
			if ("}".equals(nextChar)) {
				editor.getCaretModel().moveToOffset(offset + 1);
				editor.getScrollingModel().scrollToCaret(ScrollType.RELATIVE);
				editor.getSelectionModel().removeSelection();
				return Result.STOP;
			}
		}
		return Result.CONTINUE;
	}

	@Override
	public Result charTyped(char charTyped, Project project, @NotNull Editor editor, @NotNull PsiFile file) {
		if (charTyped == '{' && isHtl(file)) {
			int offset = editor.getCaretModel().getOffset();
			if (offset < 2 || offset > editor.getDocument().getTextLength()) {
				return Result.CONTINUE;
			}
			String previousChar = editor.getDocument().getText(new TextRange(offset - 2, offset - 1));
			if ("$".equals(previousChar)) {
				editor.getDocument().insertString(offset, "}");
			}
		}
		return Result.CONTINUE;
	}

	private static boolean isHtl(@NotNull PsiFile file) {
		FileViewProvider provider = file.getViewProvider();
		return provider.getBaseLanguage().isKindOf(HtlLanguage.INSTANCE);
	}

}
