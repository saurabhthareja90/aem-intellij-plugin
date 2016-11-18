package co.nums.intellij.aem.htl.file;

import co.nums.intellij.aem.htl.HtlLanguage;
import co.nums.intellij.aem.htl.psi.HtlTokenTypes;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.LanguageSubstitutors;
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.PsiFileImpl;
import com.intellij.psi.templateLanguages.ConfigurableTemplateLanguageFileViewProvider;
import com.intellij.psi.templateLanguages.TemplateDataElementType;
import com.intellij.psi.templateLanguages.TemplateDataLanguageMappings;
import com.intellij.util.containers.ContainerUtil;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class HtlFileViewProvider extends MultiplePsiFilesPerDocumentFileViewProvider
		implements ConfigurableTemplateLanguageFileViewProvider {

	private static final ConcurrentMap<String, TemplateDataElementType> TEMPLATE_DATA_TO_LANG = ContainerUtil.newConcurrentMap();

	private final Language baseLanguage;
	private final Language templateLanguage;

	public HtlFileViewProvider(PsiManager manager, VirtualFile file, boolean physical, Language baseLanguage,
			Language templateLanguage) {
		super(manager, file, physical);
		this.baseLanguage = baseLanguage;
		this.templateLanguage = templateLanguage;
	}

	public HtlFileViewProvider(PsiManager manager, VirtualFile file, boolean physical, Language baseLanguage) {
		this(manager, file, physical, baseLanguage, getTemplateDataLanguage(manager, file));
	}

	@NotNull
	private static Language getTemplateDataLanguage(PsiManager manager, VirtualFile file) {
		Language dataLanguage = TemplateDataLanguageMappings.getInstance(manager.getProject()).getMapping(file);
		if (dataLanguage == null) {
			dataLanguage = HtlLanguage.getDefaultTemplateLang().getLanguage();
		}

		Language substituteLanguage = LanguageSubstitutors.INSTANCE.substituteLanguage(dataLanguage, file,
				manager.getProject());

		// only use a substituted language if it's templateable
		if (TemplateDataLanguageMappings.getTemplateableLanguages().contains(substituteLanguage)) {
			dataLanguage = substituteLanguage;
		}
		return dataLanguage;
	}

	@Override
	public boolean supportsIncrementalReparse(@NotNull Language rootLanguage) {
		return false;
	}

	@NotNull
	@Override
	public Language getBaseLanguage() {
		return baseLanguage;
	}

	@NotNull
	@Override
	public Language getTemplateDataLanguage() {
		return templateLanguage;
	}

	@NotNull
	@Override
	public Set<Language> getLanguages() {
		return new THashSet<>(Arrays.asList(new Language[] { baseLanguage, getTemplateDataLanguage() }));
	}

	@Override
	protected MultiplePsiFilesPerDocumentFileViewProvider cloneInner(VirtualFile virtualFile) {
		return new HtlFileViewProvider(getManager(), virtualFile, false, baseLanguage, templateLanguage);
	}

	@Override
	protected PsiFile createFile(@NotNull Language language) {
		ParserDefinition parserDefinition = getDefinition(language);
		if (parserDefinition == null) {
			return null;
		}

		if (language.is(getTemplateDataLanguage())) {
			PsiFileImpl file = (PsiFileImpl) parserDefinition.createFile(this);
			file.setContentElementType(getTemplateDataElementType(getBaseLanguage()));
			return file;
		} else if (language.isKindOf(getBaseLanguage())) {
			return parserDefinition.createFile(this);
		} else {
			return null;
		}
	}

	private ParserDefinition getDefinition(Language language) {
		ParserDefinition parserDefinition;
		if (language.isKindOf(getBaseLanguage())) {
			parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(
					language.is(getBaseLanguage()) ? language : getBaseLanguage());
		} else {
			parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(language);
		}
		return parserDefinition;
	}

	private static TemplateDataElementType getTemplateDataElementType(Language lang) {
		TemplateDataElementType result = TEMPLATE_DATA_TO_LANG.get(lang.getID());
		if (result != null) {
			return result;
		}
		TemplateDataElementType created = new TemplateDataElementType("HTL_TEMPLATE_DATA", lang,
				HtlTokenTypes.HTML_FRAGMENT, HtlTokenTypes.HTL_FRAGMENT);
		TemplateDataElementType prevValue = TEMPLATE_DATA_TO_LANG.putIfAbsent(lang.getID(), created);

		return prevValue == null ? created : prevValue;
	}

}
