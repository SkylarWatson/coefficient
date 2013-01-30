package co.leantechniques.coefficient.scm;

import co.leantechniques.coefficient.scm.Commit;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static java.util.regex.Pattern.quote;

public class CommitLogParser {
    Scanner scanner;
    final String messageSeparator = "||";
    final String filesSeparator = "\\s+";

    public Set<Commit> getCommits(String logMessageStream) {
        scanner = new Scanner(logMessageStream);
        return getCommits();
    }

    public Commit getFirstCommit(String logMessageStream) {
        return getCommits(logMessageStream).iterator().next();
    }

    private Set<Commit> getCommits() {
        Set<Commit> commits = new HashSet<Commit>();
        while (hasMoreCommits()) {
            commits.add(nextCommit());
        }
        return commits;
    }

    private Commit createFrom(String message) {
        while(!doneReadingCommitMessage(message)){
            message += nextLine();
        }
        String[] commitData = breakOnMessageSeparator(message);
        if(containsFileList(commitData))
            return new Commit(commitData[0], commitData[1], commitData[2].split(filesSeparator));
        else
            return new Commit(commitData[0], commitData[1]);
    }

    private boolean containsFileList(String[] commitData) {
        return (commitData.length == 3);
    }

    private boolean doneReadingCommitMessage(String message) {
        return message.matches("(.*)\\|\\|(.*)\\|\\|(.*)");
    }

    private String[] breakOnMessageSeparator(String message) {
        return message.split(quote(messageSeparator));
    }

    private String nextLine() {
        return scanner.nextLine();
    }

    private boolean hasMoreCommits() {
        return scanner.hasNextLine();
    }

    private Commit nextCommit() {
        return createFrom(nextLine());
    }


}