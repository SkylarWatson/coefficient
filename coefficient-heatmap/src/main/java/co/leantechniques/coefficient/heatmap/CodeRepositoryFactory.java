package co.leantechniques.coefficient.heatmap;

import co.leantechniques.coefficient.heatmap.git.GitCodeRepository;
import co.leantechniques.coefficient.heatmap.mecurial.MercurialCodeRepository;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.util.HashMap;

public class CodeRepositoryFactory {

    public static final HashMap<String, Class<? extends CodeRepository>> SUPPORTED_ADAPTERS = new HashMap<String, Class<? extends CodeRepository>>();

    static {
        SUPPORTED_ADAPTERS.put("hg", MercurialCodeRepository.class);
        SUPPORTED_ADAPTERS.put("git", GitCodeRepository.class);
    }

    public CodeRepository build(WorkingDirectory workingDirectory, int pastDaysLimit) {
        try {
            Class<? extends CodeRepository> codeRepositoryClass = determineTypeOfCodeRepository(workingDirectory);
            return ConstructorUtils.invokeConstructor(codeRepositoryClass, workingDirectory, pastDaysLimit);
        } catch (Exception e) {
            throw new RuntimeException("A problem occurred when trying to construct an instance of the " + CodeRepository.class.getName(), e);
        }
    }

    private Class<? extends CodeRepository> determineTypeOfCodeRepository(WorkingDirectory workingDirectory) {
        return SUPPORTED_ADAPTERS.get(workingDirectory.getRepoDirectoryName().toLowerCase());
    }
}
