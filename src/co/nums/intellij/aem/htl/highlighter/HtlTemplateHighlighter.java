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

	private SyntaxHighlighter getOuterSyntaxHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile) {
		FileType type = null;
		if (project == null || virtualFile == null) {
			type = StdFileTypes.PLAIN_TEXT;
		} else {
			Language language = TemplateDataLanguageMappings.getInstance(project).getMapping(virtualFile);
			if (language != null) {
				type = language.getAssociatedFileType();
			}
			if (type == null) {
				type = HtlLanguage.getDefaultTemplateLang();
			}
		}
		// TODO: deprecated in IDEA 12, still needed in IDEA 11, remove when IDEA 11 support is dropped
		return SyntaxHighlighter.PROVIDER.create(type, project, virtualFile);
	}

}
