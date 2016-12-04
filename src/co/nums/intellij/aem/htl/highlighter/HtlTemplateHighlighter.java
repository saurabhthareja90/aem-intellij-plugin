package co.nums.intellij.aem.htl.highlighter;

import co.nums.intellij.aem.htl.HtlLanguage;
import co.nums.intellij.aem.htl.psi.HtlTokenTypes;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.util.LayerDescriptor;
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.templateLanguages.TemplateDataLanguageMappings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HtlTemplateHighlighter extends LayeredLexerEditorHighlighter {

	public HtlTemplateHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile,
			@NotNull EditorColorsScheme colors) {
		super(new HtlSyntaxHighlighter(), colors);
		registerOuterLanguageHighlighter(project, virtualFile);
	}

	private void registerOuterLanguageHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile) {
		SyntaxHighlighter outerLanguageHighlighter = getOuterSyntaxHighlighter(project, virtualFile);
		registerLayer(HtlTokenTypes.HTML_FRAGMENT, new LayerDescriptor(outerLanguageHighlighter, ""));
	}

	private static SyntaxHighlighter getOuterSyntaxHighlighter(@Nullable Project project,
			@Nullable VirtualFile virtualFile) {
		FileType fileType = null;
		if (project == null || virtualFile == null) {
			fileType = StdFileTypes.PLAIN_TEXT;
		} else {
			Language language = TemplateDataLanguageMappings.getInstance(project).getMapping(virtualFile);
			if (language != null) {
				fileType = language.getAssociatedFileType();
			}
			if (fileType == null) {
				fileType = HtlLanguage.getDefaultTemplateLang();
			}
		}
		return SyntaxHighlighterFactory.getSyntaxHighlighter(fileType, project, virtualFile);
	}

}
