package edu.pse.beast.saverloader;

import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;

import java.util.Arrays;

/**
 * @author NikolaiLMS
 */
public class ElectionDescriptionSaverLoader {

    public static String createSaveString(ElectionDescription electionDescription) {
        String created = "";
        String name = "<name>\n" + electionDescription.getName() + "\n</name>\n";
        String votingDecLine = "<votingDecLine>\n" + electionDescription.getVotingDeclLine() + "\n</votingDecLine>\n";
        String code = "<code>\n";
        for (String s : electionDescription.getCode()) {
            code += s + "\n";
        }
        code += "\n</code>\n";
        String inputType = "<inputType>\n"
                + electionDescription.getInputType().getId()
                + "\n</inputType>\n";
        String outputType = "<outputType>\n"
                + electionDescription.getOutputType().getId()
                + "\n</outputType>\n";
        created += name + votingDecLine + code + inputType + outputType;
        return created;
    }

    public static ElectionDescription createFromSaveString(String s) {
        ElectionTemplateHandler electionTemplateHandler = new ElectionTemplateHandler();

        String split[] = s.split("\n</name>\n");
        String name = split[0].replace("<name>\n", "");
        split = split[1].split("\n</votingDecLine>\n");
        int votingDecLine = Integer.parseInt(split[0].replace("<votingDecLine>\n", ""));
        split = split[1].split("\n</code>\n");
        String code = split[0].replace("<code>\n", "");
        String [] codeArray = code.split("\n");
        split = split[1].split("\n</inputType>\n");
        ElectionTypeContainer inputType = electionTemplateHandler.getById(split[0].replace("<inputType>\n", ""));
        split = split[1].split("\n</outputType>\n");
        ElectionTypeContainer outputType = electionTemplateHandler.getById(split[0].replace("<outputType>\n", ""));
        ElectionDescription electionDescription = new ElectionDescription(name, inputType, outputType, votingDecLine);
        electionDescription.setCode(Arrays.asList(codeArray));
        return electionDescription;
    }
}
