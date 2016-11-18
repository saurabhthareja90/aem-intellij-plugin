package co.nums.intellij.aem.htl;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.psi.templateLanguages.TemplateLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HtlLanguage extends Language implements TemplateLanguage {

	private static final String HTL_LANGUAGE_ID = "HTL";

	public static final HtlLanguage INSTANCE = new HtlLanguage();

	public HtlLanguage() {
		super(HTL_LANGUAGE_ID);
	}

	public HtlLanguage(@Nullable Language baseLanguage, @NotNull @NonNls final String id, @NotNull @NonNls final String... mimeTypes) {
		super(baseLanguage, id, mimeTypes);
	}

	@SuppressWarnings("SameReturnValue") // ideally this would be public static, but the static inits in the tests get cranky when we do that
	public static LanguageFileType getDefaultTemplateLang() {
		return StdFileTypes.HTML;
	}

}
