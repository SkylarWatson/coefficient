package co.leantechniques.coefficient.heatmap;

import co.leantechniques.coefficient.scm.CodeRepository;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class Heatmap {
    private Writer writer;
    private CodeRepository codeRepository;
    private final String defectDiscriminator;

    public Heatmap(CodeRepository codeRepository, Writer writer, String defectDiscriminator) {
        this.codeRepository = codeRepository;
        this.writer = writer;
        this.defectDiscriminator = defectDiscriminator;
    }

    public String generate() {
        try {
            ChangesetAnalyzer changesetAnalyzer = new ChangesetAnalyzer(codeRepository, defectDiscriminator);
            Map<String, FileStatistics> files = changesetAnalyzer.changesPerFile();
            String results = render(files);
            save(results);
            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String render(Map<String, FileStatistics> files) {
        return new HtmlRenderer(files).render();
    }

    private void save(String results) throws IOException {
        writer.write(results);
        writer.close();
    }
}
