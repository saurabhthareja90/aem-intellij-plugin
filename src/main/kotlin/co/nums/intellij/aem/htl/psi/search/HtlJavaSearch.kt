package co.nums.intellij.aem.htl.psi.search

import co.nums.intellij.aem.htl.definitions.HtlGlobalObject
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.searches.*

class HtlJavaSearch private constructor() {

    private val useInterfaces = arrayOf("io.sightly.java.api.Use", "org.apache.sling.scripting.sightly.pojo.Use")
    private val slingModelAnnotation = "org.apache.sling.models.annotations.Model"
    private val globalObjectsTypesByNames: Map<String, String> = HtlGlobalObject.values()
            .associate { Pair(it.identifier, it.type) }

    fun useApiClasses(project: Project) = useApiImplementers(project) + slingModels(project)

    private fun useApiImplementers(project: Project): Collection<PsiClass> =
            useInterfaces
                    .mapNotNull { it.toPsiClass(project) }
                    .flatMap { project.findImplementers(it) }
                    .filterNot { it.hasModifierProperty(PsiModifier.ABSTRACT) }

    private fun slingModels(project: Project): Collection<PsiClass> =
            slingModelAnnotation
                    .toPsiClass(project)
                    ?.let { project.findAnnotatedClasses(it) }
                    .orEmpty()

    private fun String.toPsiClass(project: Project) =
            JavaPsiFacade.getInstance(project).findClass(this, GlobalSearchScope.allScope(project))

    private fun Project.findImplementers(implementedInterface: PsiClass): Collection<PsiClass> =
            ClassInheritorsSearch.search(implementedInterface, GlobalSearchScope.allScope(this), true).findAll()

    private fun Project.findAnnotatedClasses(annotation: PsiClass) =
            AnnotatedElementsSearch.searchPsiClasses(annotation, GlobalSearchScope.allScope(this)).findAll()

    /**
     * Returns Java PSI class for HTL global object with given name.
     *
     * @param project project to find class in
     * @param globalObjectName global object name
     */
    fun globalObjectJavaClass(project: Project, globalObjectName: String): PsiClass? {
        return globalObjectsTypesByNames[globalObjectName]?.let {
            project.findPsiClass(it)
        }
    }

    private fun Project.findPsiClass(qualifiedClassName: String) =
            JavaPsiFacade.getInstance(this).findClass(qualifiedClassName, GlobalSearchScope.allScope(this))

}
