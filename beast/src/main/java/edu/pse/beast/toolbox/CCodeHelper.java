package edu.pse.beast.toolbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.celectiondescriptioneditor.electiontemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 * This class contains functionality to generate C Code from internal data
 * structures.
 *
 * @author Holger Klein
 */
public final class CCodeHelper {
    /** The Constant AUTO. */
    public static final String AUTO = "auto";
    /** The Constant BREAK. */
    public static final String BREAK = "break";
    /** The Constant CASE. */
    public static final String CASE = "case";
    /** The Constant CHAR. */
    public static final String CHAR = "char";
    /** The Constant CONST. */
    public static final String CONST = "const";
    /** The Constant CONTINUE. */
    public static final String CONTINUE = "continue";
    /** The Constant DEFAULT. */
    public static final String DEFAULT = "default";
    /** The Constant DO. */
    public static final String DO = "do";
    /** The Constant DOUBLE. */
    public static final String DOUBLE = "double";
    /** The Constant ELSE. */
    public static final String ELSE = "else";
    /** The Constant ENUM. */
    public static final String ENUM = "enum";
    /** The Constant EXTERN. */
    public static final String EXTERN = "extern";
    /** The Constant FLOAT. */
    public static final String FLOAT = "float";
    /** The Constant FOR. */
    public static final String FOR = "for";
    /** The Constant GOTO. */
    public static final String GOTO = "goto";
    /** The Constant IF. */
    public static final String IF = "if";
    /** The Constant INLINE. */
    public static final String INLINE = "inline";
    /** The Constant INT. */
    public static final String INT = "int";
    /** The Constant LONG. */
    public static final String LONG = "long";
    /** The Constant REGISTER. */
    public static final String REGISTER = "register";
    /** The Constant RESTRICT. */
    public static final String RESTRICT = "restrict";
    /** The Constant RETURN. */
    public static final String RETURN = "return";
    /** The Constant SHORT. */
    public static final String SHORT = "short";
    /** The Constant SIGNED. */
    public static final String SIGNED = "signed";
    /** The Constant SIZE_OF. */
    public static final String SIZE_OF = "sizeof";
    /** The Constant STATIC. */
    public static final String STATIC = "static";
    /** The Constant STRUCT. */
    public static final String STRUCT = "struct";
    /** The Constant SWITCH. */
    public static final String SWITCH = "switch";
    /** The Constant TYPE_OF. */
    public static final String TYPE_DEF = "typedef";
    /** The Constant UNION. */
    public static final String UNION = "union";
    /** The Constant UNSIGNED. */
    public static final String UNSIGNED = "unsigned";
    /** The Constant VOID. */
    public static final String VOID = "void";
    /** The Constant VOLATILE. */
    public static final String VOLATILE = "volatile";
    /** The Constant WHILE. */
    public static final String WHILE = "while";
    /** The Constant ALIGN_AS. */
    public static final String ALIGN_AS = "_Alignas";
    /** The Constant ALIGN_OF. */
    public static final String ALIGN_OF = "_Alignof";
    /** The Constant ATOMIC. */
    public static final String ATOMIC = "_Atomic";
    /** The Constant BOOL. */
    public static final String BOOL = "_Bool";
    /** The Constant COMPLEX. */
    public static final String COMPLEX = "_Complex";
    /** The Constant GENERIC. */
    public static final String GENERIC = "_Generic";
    /** The Constant IMAGINARY. */
    public static final String IMAGINARY = "_Imaginary";
    /** The Constant NO_RETURN. */
    public static final String NO_RETURN = "_Noreturn";
    /** The Constant STATIC_ASSERT. */
    public static final String STATIC_ASSERT = "_Static_assert";
    /** The Constant THREAD_LOCAL. */
    public static final String THREAD_LOCAL = "_Thread_local";

    /** The BLANK symbol. */
    private static final String BLANK = " ";
    /** The line comment symbol(s). */
    private static final String LINE_COMMENT = "//";
    /** The colon symbol. */
    private static final String COLON = ":" + BLANK;
    /** The comma symbol. */
    private static final String COMMA = "," + BLANK;
    /** The right parenthesis and left brace symbol. */
    private static final String PAREN_R_BRACE_L = ") {";
    /** The uint loop start string. */
    private static final String UINT_LOOP_START = "(unsigned int" + BLANK;

    /** The RESULT constant. */
    private static final String RESULT = "RESULT";
    /** The VOTES constant. */
    private static final String VOTES = "VOTES";
    /** The "amountVotes" constant. */
    private static final String AMOUNT_VOTES = "amountVotes";
    /** The "_exp" string. */
    private static final String EXP = "_exp";

    // String that only allows string in valid C format (they can still contain
    // identifiers)

    /** The character regex. */
    private static String characterRegex = "[_a-zA-Z][_a-zA-Z0-9]{0,30}";

    /** The reserved words. */
    private static String[] reservedWords =
        {
        AUTO, BREAK, CASE, CHAR,
        CONST, CONTINUE, DEFAULT,
        DO, DOUBLE, ELSE, ENUM,
        EXTERN, FLOAT, FOR, GOTO,
        IF, INLINE, INT, LONG,
        REGISTER, RESTRICT, RETURN,
        SHORT, SIGNED, SIZE_OF,
        STATIC, STRUCT, SWITCH,
        TYPE_DEF, UNION, UNSIGNED,
        VOID, VOLATILE, WHILE,
        ALIGN_AS, ALIGN_OF, ATOMIC,
        BOOL, COMPLEX, GENERIC,
        IMAGINARY, NO_RETURN,
        STATIC_ASSERT, THREAD_LOCAL
        };

    /** The c reserved words. */
    private static List<String> cReservedWords =
            new ArrayList<String>(Arrays.asList(reservedWords));

    /**
     * Instantiates a new c code helper.
     */
    private CCodeHelper() { }

    /**
     * Returns the C constant which is the max amount of elements in a given
     * list container.
     *
     * @param cont
     *            the type container representing the list
     * @return the size of the container in C Code
     */
    public static String getListSize(final InternalTypeContainer cont) {
        final String size;
        if (cont.getAccessTypeIfList() == InternalTypeRep.CANDIDATE) {
            size = UnifiedNameContainer.getCandidate();
        } else if (cont.getAccessTypeIfList() == InternalTypeRep.VOTER) {
            size = UnifiedNameContainer.getVoter();
        } else if (cont.getAccessTypeIfList() == InternalTypeRep.SEAT) {
            size = UnifiedNameContainer.getSeats();
        } else {
            size = "";
        }
        if ("".equals(size)) {
            ErrorLogger.log("");
        }
        return size;
    }
    //
    // /**
    // * Creates the C-Type text representation of the given internal type
    // * container.
    // * Arrays are created as arrays: "unsigned int votes[" +
    // * UnifiedNameContainer.getVoter() + "][" +
    // * UnifiedNameContainer.getCandidate()
    // * + "]", for example
    // *
    // * @param electionContainer the container for which the C type should be
    // *                          created
    // * @param name the name of the variable
    // * @return the c type
    // */
    // public static String getCType(ElectionTypeContainer electionContainer,
    //                               String name) {
    //     String decl = "unsigned int " + name;
    //     decl = decl + electionContainer.getInputType().getSimpleType(true);
    //     return decl;
    // }
    //
    // /**
    // * Creates the C-Type text representation of the given
    // * Internaltypecontainer.
    // * Arrays are created as pointers: "unsigned int *", for example.
    // *
    // * @param electionContainer the container for which the C type should be
    // *                          created
    // * @return the c type
    // */
    // public static String getCTypePointer(ElectionTypeContainer
    //                                          electionContainer) {
    //     String decl = electionContainer.getOutputType().getSimpleType(true);
    //     return decl;
    // }

    /**
     * If the given InternaltypeContainer represents a list, it generates the
     * String representing a corresponding C-Array. Ex return: "[" +
     * UnifiedNameContainer.getCandidate() + "]"
     *
     * @param cont
     *            the container for which the C type should be created
     * @return the amount of square brackets and length constants needed
     */
    public static String getCArrayType(final InternalTypeContainer cont) {
        InternalTypeContainer currentContainer = cont;
        String decl = "";
        while (currentContainer.isList()) {
            decl += "[" + getListSize(currentContainer) + "]";
            currentContainer = currentContainer.getListedType();
        }
        return decl;
    }

    /**
     * Generates the declaration String for a voting function depending on its
     * input and result type. This is the voting method which will be presented
     * to the user, so it should not contain structs, but just simple data types
     * (except if it cannot be helped).
     *
     * @param container
     *            the input format of the voting array passed to the function
     * @return the voting function declaration line
     */
    public static String generateSimpleDeclString(final ElectionTypeContainer container) {
        String decl = RESULT + BLANK
                + UnifiedNameContainer.getVotingMethod()
                + UINT_LOOP_START + AMOUNT_VOTES + COMMA + VOTES + PAREN_R_BRACE_L;

        String[] sizeOfDimensions =
                container.getInputType().getSizeOfDimensions();

        if (sizeOfDimensions.length > 0) {
            sizeOfDimensions[0] = AMOUNT_VOTES;
        }

        decl = decl.replace(RESULT,
                container.getInputType().getDataTypeAndSign() + container
                        .getOutputType().getDimensionDescriptor(true));
        decl = decl.replace(VOTES,
                container.getInputType().getDataTypeAndSign() + BLANK
                        + UnifiedNameContainer.getVotingArray()
                        + container.getInputType()
                                .getDimensionDescriptor(sizeOfDimensions));

        return decl;
    }

    /**
     * Generates the declaration String for a voting function depending on its
     * input and result type.
     *
     * @param container
     *            the input format of the voting array passed to the function
     * @param voteStructName
     *            the vote struct name
     * @return the voting function declaration line
     */
    public static String generateStructDeclString(final ElectionTypeContainer container,
                                                  final String voteStructName) {
        String decl = RESULT + BLANK
                + UnifiedNameContainer.getVotingMethod()
                + UINT_LOOP_START + AMOUNT_VOTES + COMMA + VOTES + PAREN_R_BRACE_L;

        decl = decl.replace(RESULT,
                container.getOutputStruct().getStructAccess());
        decl = decl.replace(VOTES,
                container.getInputStruct().getStructAccess() + BLANK
                        + voteStructName);

        return decl;
    }

    /**
     * Generates the complete function block which is placed in the C editor if
     * the user creates a new election description. It adds the explanatory
     * comments and the closing curly bracket
     *
     * @param container
     *            the input format of the voting array passed to the function
     * @param name
     *            the name of the election
     * @param templateHandler
     *            the Template handler which generated input and result types
     * @param stringResourceLoader
     *            the string resource loader currently used
     * @return the complete voting function
     */
    public static ElectionDescription
            generateElectionDescription(final ElectionTypeContainer container,
                                        final String name,
                                        final ElectionTemplateHandler templateHandler,
                                        final StringResourceLoader stringResourceLoader) {
        ArrayList<String> code = new ArrayList<String>();
        final String inputIdInFile = container.getInputType().getInputIDinFile();
        final String outputIdInFile = container.getOutputType().getOutputIDinFile();

        code.add(LINE_COMMENT + stringResourceLoader.getStringFromID(inputIdInFile)
                + COLON
                + stringResourceLoader.getStringFromID(inputIdInFile + EXP));
        code.add(LINE_COMMENT + stringResourceLoader.getStringFromID(outputIdInFile)
                + COLON + stringResourceLoader
                        .getStringFromID(outputIdInFile + EXP));
        code.add(generateSimpleDeclString(container));
        code.add("}" + BLANK);
        final ElectionDescription description =
                new ElectionDescription(name,
                                        container.getInputType(),
                                        container.getOutputType(),
                                        0, 0, 0, true);
        description.setCode(code);
        return description;
    }

    /**
     * Returns the max value an element of the given ElectionTypeContainer can
     * have.
     *
     * @param container
     *            the list whose elements max value needs to be determined
     * @return max value an element of the given ElectionTypeContainer can have
     */
    public static String getMax(final ElectionTypeContainer container) {
        return container.getInputType().getMaximalValue();
    }

    /**
     * Returns the minimum value an element of the given ElectionTypeContainer
     * can have.
     *
     * @param container
     *            the list whose elements min value needs to be determined
     * @return minimum value an element of the given ElectionTypeContainer can
     *         have
     */
    public static String getMin(final ElectionTypeContainer container) {
        return container.getInputType().getMinimalValue();
    }

    /**
     * Checks if is valid C name.
     *
     * @param name
     *            the name
     * @return true, if is valid C name
     */
    public static boolean isValidCName(final String name) {
        if (name.matches(characterRegex)) {
            if (!cReservedWords.stream().anyMatch(str -> str.equals(name))) {
                // it is not a reserved word
                return true;
            }
        }
        System.out.println("The given symbolic variable name"
                            + " is not valid in C.");
        return false;
    }
}
