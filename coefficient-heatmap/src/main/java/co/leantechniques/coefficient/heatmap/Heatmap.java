package co.leantechniques.coefficient.heatmap;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class Heatmap {
    private Writer writer;
    private CodeRepository codeRepository;

    public Heatmap(CodeRepository codeRepository, Writer writer) {
        this.codeRepository = codeRepository;
        this.writer = writer;
	}
    
	public String generate() {
        try {
            ChangesetAnalyzer changesetAnalyzer = new ChangesetAnalyzer(codeRepository);
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
