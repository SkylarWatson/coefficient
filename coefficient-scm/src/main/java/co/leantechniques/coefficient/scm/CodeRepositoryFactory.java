package co.leantechniques.coefficient.scm;

import co.leantechniques.coefficient.scm.git.GitCodeRepository;
import co.leantechniques.coefficient.scm.mercurial.MercurialCodeRepository;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.util.HashMap;

public class CodeRepositoryFactory {

    public static final HashMap<String, Class<? extends CodeRepository>> SUPPORTED_ADAPTERS = new HashMap<String, Class<? extends CodeRepository>>();

    static {
        SUPPORTED_ADAPTERS.put(".hg", MercurialCodeRepository.class);
        SUPPORTED_ADAPTERS.put(".git", GitCodeRepository.class);
    }

    public CodeRepository build(WorkingDirectory workingDirectory, int pastDaysLimit) {
        Class<? extends CodeRepository> codeRepositoryClass = determineTypeOfCodeRepositoryIn(workingDirectory);
        try {
            return ConstructorUtils.invokeConstructor(codeRepositoryClass, workingDirectory, pastDaysLimit);
        } catch (Exception e) {
            throw new RuntimeException("A problem occurred when trying to construct an instance of the " + CodeRepository.class.getName(), e);
        }
    }

    private Class<? extends CodeRepository> determineTypeOfCodeRepositoryIn(WorkingDirectory workingDirectory) {

        for (String scmDirectory : SUPPORTED_ADAPTERS.keySet()) {
            if (workingDirectory.directoryExists(scmDirectory)) {
                return SUPPORTED_ADAPTERS.get(scmDirectory);
            }
        }

        throw new UnsupportedOperationException("Unable to determine repository type found in " + workingDirectory);
    }

}
