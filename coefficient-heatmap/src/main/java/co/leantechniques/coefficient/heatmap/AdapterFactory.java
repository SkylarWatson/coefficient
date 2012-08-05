package co.leantechniques.coefficient.heatmap;

import co.leantechniques.coefficient.heatmap.mecurial.MercurialCodeRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class AdapterFactory {

    public static final HashMap<String, Class<? extends CodeRepository>> SUPPORTED_ADAPTERS = new HashMap<String, Class<? extends CodeRepository>>();

    static {
        SUPPORTED_ADAPTERS.put("hg", MercurialCodeRepository.class);
    }

    public CodeRepository adapterFor(WorkingDirectory workingDirectory, int pastDaysLimit) {
        try {
            return SUPPORTED_ADAPTERS.get(workingDirectory.getRepoDirectoryName().toLowerCase()).getDeclaredConstructor(int.class).newInstance(pastDaysLimit);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
