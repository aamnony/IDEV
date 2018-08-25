//package com.github.amnonya.hdleditor.vhdl.psi;
//
//import com.intellij.openapi.application.QueryExecutorBase;
//import com.intellij.openapi.application.ReadAction;
//import com.intellij.openapi.util.text.StringUtil;
//import com.intellij.psi.PsiReference;
//import com.intellij.psi.search.GlobalSearchScope;
//import com.intellij.psi.search.SearchScope;
//import com.intellij.psi.search.UsageSearchContext;
//import com.intellij.psi.search.searches.ReferencesSearch;
//import com.intellij.util.Processor;
//
//import org.jetbrains.annotations.NotNull;
//
//import com.github.amnonya.hdleditor.vhdl.fileTypes.VhdlFileType;
//
//public class VhdlIdentifierReferenceSearchExecutor extends QueryExecutorBase<PsiReference, ReferencesSearch.SearchParameters> {
//    /*public void processQuery(@NotNull ReferencesSearch.SearchParameters queryParameters, @NotNull final Processor<? super PsiReference> consumer) {
//        PyClass pyClass = PyUtil.as(queryParameters.getElementToSearch(), PyClass.class);
//        if (pyClass == null) {
//            return;
//        }
//
//        ReadAction.run(() -> {
//            final String className = pyClass.getName();
//            if (StringUtil.isEmpty(className)) {
//                return;
//            }
//            final PyFunction initMethod = pyClass.findMethodByName(PyNames.INIT, false, null);
//            if (initMethod == null) {
//                return;
//            }
//            SearchScope searchScope = queryParameters.getEffectiveSearchScope();
//            if (searchScope instanceof GlobalSearchScope) {
//                searchScope = GlobalSearchScope.getScopeRestrictedByFileTypes((GlobalSearchScope)searchScope, PythonFileType.INSTANCE);
//            }
//            queryParameters.getOptimizer().searchWord(className, searchScope, UsageSearchContext.IN_CODE, true, initMethod);
//        });
//    }*/
//
//    @Override
//    public void processQuery(@NotNull ReferencesSearch.SearchParameters searchParameters, @NotNull Processor<PsiReference> processor) {
//        VhdlNamedElement e = (VhdlNamedElement) searchParameters.getElementToSearch();
//
//        ReadAction.run(() -> {
//            String name = e.getName();
//            if (StringUtil.isEmpty(name)) {
//                return;
//            }
//            SearchScope searchScope = searchParameters.getEffectiveSearchScope();
//            if (searchScope instanceof GlobalSearchScope) {
//                searchScope = GlobalSearchScope.getScopeRestrictedByFileTypes((GlobalSearchScope) searchScope, VhdlFileType.INSTANCE);
//            }
//            searchParameters.getOptimizer().searchWord(name, searchScope, UsageSearchContext.IN_CODE, false, e);
//        });
//    }
//}