package com.github.johanfredin.llama.examples;

public interface LlamaExamples {

    String exInputDir();

    String exOutputDir();

    default String resultingFileName(String fileType) {
        return getClass().getSimpleName().toLowerCase() + "_result." + fileType;
    }

    default String getCompletionMessage() {
        return getClass().getSimpleName() + " FINISHED.";
    }

    default String routeId() {
        return this.getClass().getSimpleName();
    }

}
