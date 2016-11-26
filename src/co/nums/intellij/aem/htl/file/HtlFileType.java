package co.nums.intellij.aem.htl.file;

import co.nums.intellij.aem.htl.HtlLanguage;
import co.nums.intellij.aem.htl.highlighter.HtlTemplateHighlighter;
import co.nums.intellij.aem.htl.icons.HtlIcons;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileTypeEditorHighlighterProviders;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.TemplateLanguageFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public class HtlFileType extends LanguageFileType implements TemplateLanguageFileType {

	public static final HtlFileType INSTANCE = new HtlFileType();

	@NonNls
	public static final String DEFAULT_EXTENSION = "htl";

	private HtlFileType() {
		this(HtlLanguage.INSTANCE);
	}

	private HtlFileType(Language language) {
		super(language);

		FileTypeEditorHighlighterProviders.INSTANCE.addExplicitExtension(
				this,
				(project, fileType, virtualFile, editorColorsScheme) ->
						new HtlTemplateHighlighter(project, virtualFile, editorColorsScheme)
		);
	}

	@NotNull
	@Override
	public String getName() {
		return "HTL";
	}

	@NotNull
	@Override
	public String getDescription() {
		return "HTML Template Language file";
	}

	@NotNull
	@Override
	public String getDefaultExtension() {
		return DEFAULT_EXTENSION;
	}

	@Override
	public Icon getIcon() {
		return HtlIcons.HTL_FILE;
	}

}
