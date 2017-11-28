package org.reactome.server.analysis.parser.tools;

import org.reactome.server.analysis.core.model.Proteoform;
import org.reactome.server.analysis.parser.*;
import org.reactome.server.analysis.parser.Parser.ProteoformFormat;
import org.reactome.server.analysis.parser.response.Response;

import java.util.List;

import static org.reactome.server.analysis.parser.Parser.ProteoformFormat.NONE;
import static org.reactome.server.analysis.parser.ParserProteoformNeo4j.matches_Proteoform_Neo4j_With_Expression_Values;
import static org.reactome.server.analysis.parser.tools.InputPatterns.*;
import static org.reactome.server.analysis.parser.ParserProteoformPRO.matches_Proteoform_Pro;
import static org.reactome.server.analysis.parser.ParserProteoformPRO.matches_Proteoform_Pro_With_Expression_Values;
import static org.reactome.server.analysis.parser.ParserProteoformSimple.matches_Proteoform_Simple;
import static org.reactome.server.analysis.parser.ParserProteoformSimple.matches_Proteoform_Simple_With_Expression_Values;

public class ProteoformsProcessor {

    public static Proteoform getProteoform(String line){
        Parser.ProteoformFormat format = checkForProteoforms(line);
        Proteoform proteoform = null;
        switch (format) {
            case NEO4J:
                proteoform = ParserProteoformNeo4j.getProteoform(line);
                break;
            case SIMPLE:
                    proteoform = ParserProteoformSimple.getProteoform(line);
                break;
            case PRO:
                    proteoform = ParserProteoformPRO.getProteoform(line);
                break;
        }
        return proteoform;
    }

    public static Proteoform getProteoform(String line, ProteoformFormat format, int i, List<String> warnings) {
        Proteoform proteoform = null;
        switch (format) {
            case SIMPLE:
                if (!matches_Proteoform_Simple_With_Expression_Values(line)) {
                    warnings.add(Response.getMessage(Response.INLINE_PROBLEM, i + 1, 1));
                } else {
                    proteoform = ParserProteoformSimple.getProteoform(line, i + 1, warnings);
                }
                break;
            case PRO:
                if (!matches_Proteoform_Pro_With_Expression_Values(line)) {
                    warnings.add(Response.getMessage(Response.INLINE_PROBLEM, i + 1, 1));
                } else {
                    proteoform = ParserProteoformPRO.getProteoform(line, i + 1);
                }
                break;
            case NEO4J:
                if(!matches_Proteoform_Neo4j_With_Expression_Values(line)){
                    warnings.add(Response.getMessage(Response.INLINE_PROBLEM, i + 1, 1));
                } else {
                    proteoform = ParserProteoformNeo4j.getProteoform(line, i + 1, warnings);
                }
            case NONE:
                throw new RuntimeException("The line " + i + " should marked as a proteoform.");
        }
        return proteoform;
    }

    /**
     * Verifies if a set of content lines follows a proteoform format.
     * Decides using the first 5 lines. If all first lines match the same proteoform format it returns that type.
     * If they all do not match in format, even if they are all proteoforms, it throws a format exception.
     *
     * @param content     Array of content lines to process
     * @param startOnLine Offset to ignore the first 'startOnLine' lines.
     * @return ProteoformFormat instance indicating the format. If ProteoformFormat.NONE is returned,
     * the content lines are not proteoforms and should be processed as usual in Reactome V2. Returns unknown if all lines
     * are empty.
     */
    public static ProteoformFormat checkForProteoforms(String[] content, int startOnLine) {

        ProteoformFormat resultFormat = ProteoformFormat.UNKNOWN;

        int linesChecked = 0;
        for (int i = startOnLine; i < content.length && linesChecked < 5; ++i) {
            String line = content[i].trim();
            if (line.isEmpty()) {
                //warningResponses.add(Response.getMessage(Response.EMPTY_LINE, i + 1));
                continue;
            }
            if (matches_Proteoform_Simple(line)) {
                if (resultFormat == ProteoformFormat.UNKNOWN) {
                    resultFormat = ProteoformFormat.SIMPLE;
                } else if (resultFormat != ProteoformFormat.SIMPLE) {
                    //errorResponses.add(Response.getMessage(Response.PROTEOFORM_MISMATCH, i + 1));
                    return NONE;
                }
            } else if (matches_Proteoform_Pro(line)) {
                if (resultFormat == ProteoformFormat.UNKNOWN) {
                    resultFormat = ProteoformFormat.PRO;
                } else if (resultFormat != ProteoformFormat.PRO) {
                    return NONE;
                }
            } else if (matches_Proteoform_Pir(line)) {
                if (resultFormat == ProteoformFormat.UNKNOWN) {
                    resultFormat = ProteoformFormat.PIR_ID;
                } else if (resultFormat != ProteoformFormat.PIR_ID) {
                    return NONE;
                }
            } else if (matches_Proteoform_Gpmdb(line)) {
                if (resultFormat == ProteoformFormat.UNKNOWN) {
                    resultFormat = ProteoformFormat.GPMDB;
                } else if (resultFormat != ProteoformFormat.GPMDB) {
                    return NONE;
                }
            } else {
                if (resultFormat == ProteoformFormat.UNKNOWN) {
                    resultFormat = NONE;
                } else if (resultFormat != NONE) {
                    return NONE;
                }
            }
            linesChecked++;
        }

        return resultFormat;
    }

    /**
     * Check if the line has at least one proteoform.
     * @param line
     * @return
     */
    public static ProteoformFormat checkForProteoforms(String line){
        if(ParserProteoformPRO.check(line)){
            return ProteoformFormat.PRO;
        } else if(ParserProteoformSimple.check(line)){
            return ProteoformFormat.SIMPLE;
        } else if(ParserProteoformNeo4j.check(line)){
            return ProteoformFormat.NEO4J;
        }
        return NONE;
    }

    public static ProteoformFormat checkForProteoformsWithExpressionValues(String[] content, int startOnLine) {
        ProteoformFormat resultFormat = ProteoformFormat.UNKNOWN;

        int linesChecked = 0;
        for (int i = startOnLine; i < content.length && linesChecked < 5; ++i) {
            String line = content[i].trim();
            if (line.isEmpty()) {
                //warningResponses.add(Response.getMessage(Response.EMPTY_LINE, i + 1));
                continue;
            }
            if (matches_Proteoform_Simple_With_Expression_Values(line)) {
                if (resultFormat == ProteoformFormat.UNKNOWN) {
                    resultFormat = ProteoformFormat.SIMPLE;
                } else if (resultFormat != ProteoformFormat.SIMPLE) {
                    //errorResponses.add(Response.getMessage(Response.PROTEOFORM_MISMATCH, i + 1));
                    return NONE;
                }
            } else if (matches_Proteoform_Pro_With_Expression_Values(line)) {
                if (resultFormat == ProteoformFormat.UNKNOWN) {
                    resultFormat = ProteoformFormat.PRO;
                } else if (resultFormat != ProteoformFormat.PRO) {
                    return NONE;
                }
            } else if (matches_Proteoform_Pir_With_Expression_Values(line)) {
                if (resultFormat == ProteoformFormat.UNKNOWN) {
                    resultFormat = ProteoformFormat.PIR_ID;
                } else if (resultFormat != ProteoformFormat.PIR_ID) {
                    return NONE;
                }
            } else if (matches_Proteoform_Gpmdb_With_Expression_Values(line)) {
                if (resultFormat == ProteoformFormat.UNKNOWN) {
                    resultFormat = ProteoformFormat.GPMDB;
                } else if (resultFormat != ProteoformFormat.GPMDB) {
                    return NONE;
                }
            } else {
                if (resultFormat == ProteoformFormat.UNKNOWN) {
                    resultFormat = NONE;
                } else if (resultFormat != NONE) {
                    return NONE;
                }
            }
            linesChecked++;
        }

        return resultFormat;
    }
}
