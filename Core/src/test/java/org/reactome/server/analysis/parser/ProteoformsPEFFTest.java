package org.reactome.server.analysis.parser;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.reactome.server.analysis.parser.exception.ParserException;
import org.springframework.context.annotation.Description;

import java.io.File;

import static org.reactome.server.analysis.parser.tools.ParserFactory.createParser;
import static org.reactome.server.analysis.parser.util.ConstantHolder.REPETITIONS;
import static org.reactome.server.analysis.parser.util.FileUtils.getString;

class ProteoformsPEFFTest {

    private static final String PATH_VALID = "target/test-classes/analysis/input/ProteoformsPEFF/Valid/";
    private static final String PATH_INVALID = "target/test-classes/analysis/input/ProteoformsPEFF/Invalid/";

    @Test
    @Tag("Valid")
    void examplePEFF_Minimal_ValidTest(TestInfo testInfo) {
        String data = getString(PATH_VALID + "PEFF_Minimal_Valid.peff");
        Parser p = createParser(data);
        try {
            Assert.assertEquals(p.getClass(), ParserProteoformPEFF.class);
            p.parseData(data);
        } catch (ParserException e) {
            Assert.fail(testInfo.getDisplayName() + " has failed");
        }

        Assert.assertEquals(1, p.getHeaderColumnNames().size());
        Assert.assertEquals(1, p.getAnalysisIdentifierSet().size());
        Assert.assertEquals(0, p.getWarningResponses().size());
    }

    @Test
    @Tag("Feature")
    @Description("Test file with a single sequence")
    void singleSequenceTest() {
    }

    @Test
    @Tag("Feature")
    @Description("Test possibility of multiple sequences in one file")
    void multipleSequenceTest() {
    }

    @Test
    @Tag("Feature")
    @Description("The file must contain a Header and an Individual sequence entries section")
    void generalFileStructureTest() {

    }

    @Test
    @Tag("Feature")
    @Description("The file header section MUST start with a file description block that MUST be followed by at least one sequence database description block.")
    void headerStructureTest() {

    }

    @Test
    @Tag("Feature")
    @Description("All lines in the file header section start with the character #, followed by a space (ASCII 32) character. ")
    void headerLinesStartAndEndCharacterTest() {

    }

    @Test
    @Tag("Feature")
    @Description("The first line of the header section is also the first line of the file: # PEFF N.N, e.g. 1.0")
    void firstLineTest() {

    }

    @Test
    @Tag("Feature")
    @Description("Parsers SHOULD check PEFF version in the first line and react if supported or not.")
    void supportedPEFFVersionTest() {

    }

    @Test
    @Tag("Feature")
    @Description("Optional general comments can appear after the first header line. Nowhere else. They must be nonempty. Structure of the comments is '# GeneralComment=value'")
    void generalCommentsStructureTest() {

    }

    @Test
    @Tag("Feature")
    @Description("Sequence database description lines appear after first header line or after the general comments.")
    void sequenceDatabaseLinesLocationTest() {

    }

    @Test
    @Tag("Feature")
    @Description("Sequence database description lines structure is: '# key=value'. 'PEFFKey' attached to a CV term in the PSI-MS CV under the “PEFF keyword” branch.")
    void sequenceDatabaseLinesContentTest() {

    }

    @Test
    @Tag("Feature")
    @Description("Each sequence database block MUST start with a sequence database line description and follow the following format: # DbName=value, (where value is the database name)")
    void sequenceDatabaseFirstLineTest() {

    }

    @Test
    @Tag("Feature")
    @Description("Each sequence database block must contain meta-information about the database itself: Prefix; DbVersion, DbSource, NumberOfEntries, SequenceType.")
    void sequenceDatabaseMandatoryInformationTest() {
    }

    @Test
    @Tag("Feature")
    @Description("Each sequence database block may contain optional meta-information defined after the mandatory database keys using the SpecificKey key")
    void sequenceDatabaseOptionalTest() {
    }

    @Test
    @Tag("Feature")
    @Description("Each sequence database block MUST end with the following separation line: '# //'")
    void sequenceDatabaseLastLineTest() {
    }


    @Test
    @Tag("Feature")
    @Description("Test compatibility with MIAPE guidelines")
    void compliantWithMIAPETest() {

    }

    @Test
    @Tag("Feature")
    @Description("Test possibility to encode exact proteoforms")
    void encodeExactProteoformsTest() {

    }

    @Test
    @Tag("Feature")
    @Description("All lines in the file MUST end with LF (ASCII 10). A CR (ASCII 13) MAY precede the LF and SHOULD be ignored by parsers.")
    void lineEndingTest() {

    }

    @Test
    @Tag("Feature")
    @Description("The characters allowed are the set of ASCII characters")
    void charactersAllowedTest() {

    }

    @Test
    @Tag("Feature")
    @Description("Possibility for multiple database sources; indicated in the Header section and with individual sequence entries.")
    void multipleDatabasesTest() {

    }

    private static final String PATH = "analysis/parser/PEFF/";

    @RepeatedTest(REPETITIONS)
    @DisplayName("Tiny valid example")
    void isValid_Tiny_Example_true() {
        File file = getFileFromResources(PATH.concat("Valid/PEFF_Tiny_Valid.peff"));

    }

    private File getFileFromResources(String concat) {
        return null;
    }

    @RepeatedTest(REPETITIONS)
    @DisplayName("Minimal valid example")
    void isValid_Minimal_Example_true() {
        File file = getFileFromResources(PATH.concat("Valid/PEFF_Minimal_Valid.peff"));
    }

    @RepeatedTest(REPETITIONS)
    @DisplayName("Tiny invalid example")
    void isValid_Tiny_Example_false() {
        File file = getFileFromResources(PATH.concat("Invalid/PEFF_Tiny_INValid1.peff"));
    }

    @RepeatedTest(REPETITIONS)
    @DisplayName("Minimal invalid example")
    void isValid_Minimal_Example_false() {
        File file = getFileFromResources(PATH.concat("Invalid/PEFF_Minimal_INValid1.peff"));
    }

    @RepeatedTest(REPETITIONS)
    @DisplayName("Uses lower case valid")
    void isValid_lowerCase_true() {

    }

}