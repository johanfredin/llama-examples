package com.github.johanfredin.llama.examples;

/**
 * Helper interface used only to aid in creating examples.
 * @author Johan Fredin
 */
public interface LlamaExamples {

    /**
     * Should return the url to the input directory for the example
     * @return the url to the input directory for the example.
     */
    String exInputDir();

    /**
     * Should return the url to the output directory for the example
     * @return the url to the output directory for the example.
     */
    String exOutputDir();

    /**
     * Creates a default output file name so users don't have to think of something :)
     * File name will get the name of the class + _result.fileType param. Example
     * <b>Ex1_CSV_result.csv</b>
     *
     * @param fileType what type of file, csv, xml etc.
     * @return the name of the class + _result.fileType param.
     */
    default String resultingFileName(String fileType) {
        return getClass().getSimpleName().toLowerCase() + "_result." + fileType;
    }

    /**
     * A default completion message.
     * @return name of the class + " FINISHED"
     */
    default String getCompletionMessage() {
        return getClass().getSimpleName() + " FINISHED.";
    }

    /**
     * A default route id that will be the name of the example. Must be unique.
     * @return the name of the class as route id
     */
    default String exampleRouteId() {
        return exampleRouteId("");
    }

    /**
     * A default route id that will be the name of the example. Must be unique.
     * @param additionalId any additional name to append to the id e.g "_read-something"
     * @return the name of the class + additional name that makes this id unique.
     */
    default String exampleRouteId(String additionalId) {
        return this.getClass().getSimpleName() + (additionalId.isEmpty() ? "" : "_" + additionalId);
    }

}
