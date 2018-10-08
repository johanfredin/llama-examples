package se.fredin.llama.examples;

import se.fredin.llama.processor.ResultType;
import se.fredin.llama.utils.Endpoint;

public interface LlamaExamples {

    String exInputDir();

    String exOutputDir();

    default String resultingFileName(String fileType) {
        return getClass().getSimpleName().toLowerCase() + "_result." + fileType;
    }

    default String getCompletionMessage() {
        return getClass().getSimpleName() + " FINISHED.";
    }

}
