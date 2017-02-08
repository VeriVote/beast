package edu.pse.beast.saverloader;

import edu.pse.beast.datatypes.ElectionCheckParameter;
import edu.pse.beast.datatypes.TimeOut;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

import java.util.ArrayList;

/**
 * @author NikolaiLMS
 */
public class ElectionCheckParameterSaverLoader implements SaverLoader{

    public String createSaveString(Object electionCheckParameter) {
        String amountVotersMin = "<amountVotersMin>\n" +
                ((ElectionCheckParameter) electionCheckParameter).getAmountVoters().get(0) +
                "\n</amountVotersMin>\n";
        String amountVotersMax = "<amountVotersMax>\n" +
                ((ElectionCheckParameter) electionCheckParameter).getAmountVoters().get(
                        ((ElectionCheckParameter) electionCheckParameter).getAmountVoters().size()-1) +
                "\n</amountVotersMax>\n";
        String amountCandidatesMin = "<amountCandidatesMin>\n" +
                ((ElectionCheckParameter) electionCheckParameter).getAmountCandidates().get(0) +
                "\n</amountCandidatesMin>\n";
        String amountCandidatesMax = "<amountCandidatesMax>\n" +
                ((ElectionCheckParameter) electionCheckParameter).getAmountCandidates().get(
                        ((ElectionCheckParameter) electionCheckParameter).getAmountCandidates().size()-1) +
                "\n</amountCandidatesMax>\n";
        String amountSeatsMin = "<amountSeatsMin>\n" +
                ((ElectionCheckParameter) electionCheckParameter).getAmountSeats().get(0) +
                "\n</amountSeatsMin>\n";
        String amountSeatsMax = "<amountSeatsMax>\n" +
                ((ElectionCheckParameter) electionCheckParameter).getAmountSeats().get(
                        ((ElectionCheckParameter) electionCheckParameter).getAmountSeats().size()-1) +
                "\n</amountSeatsMax>\n";
        String timeout = "<timeout>\n" + TimeOutSaverLoader.createSaveString(
                ((ElectionCheckParameter) electionCheckParameter).getTimeout()) + "\n</timeout>\n";
        String processes = "<processes>\n" +
                ((ElectionCheckParameter) electionCheckParameter).getProcesses() + "\n</processes>\n";
        String argument = "<argument>\n" +
                ((ElectionCheckParameter) electionCheckParameter).getArgument() + "\n</argument>\n";
        return (amountVotersMin + amountVotersMax + amountCandidatesMin + amountCandidatesMax + amountSeatsMin +
                amountSeatsMax + timeout + processes + argument);
    }

    public Object createFromSaveString(String s) throws Exception{

        String split[] = s.split("\n</amountVotersMin>\n");
        int amountVotersMin = Integer.parseInt(split[0].replace("<amountVotersMin>\n", ""));
        split = split[1].split("\n</amountVotersMax>\n");
        int amountVotersMax = Integer.parseInt(split[0].replace("<amountVotersMax>\n", ""));
        ArrayList<Integer> amountVoters = new ArrayList<>();
        for (int i = amountVotersMin; i <= amountVotersMax; i++) {
            amountVoters.add(i);
        }
        split = split[1].split("\n</amountCandidatesMin>\n");
        int amountCandidatesMin = Integer.parseInt(split[0].replace("<amountCandidatesMin>\n", ""));
        split = split[1].split("\n</amountCandidatesMax>\n");
        int amountCandidatesMax = Integer.parseInt(split[0].replace("<amountCandidatesMax>\n", ""));
        ArrayList<Integer> amountCandidates = new ArrayList<>();
        for (int i = amountCandidatesMin; i <= amountCandidatesMax; i++) {
            amountCandidates.add(i);
        }
        split = split[1].split("\n</amountSeatsMin>\n");
        int amountSeatsMin = Integer.parseInt(split[0].replace("<amountSeatsMin>\n", ""));
        split = split[1].split("\n</amountSeatsMax>\n");
        int amountSeatsMax = Integer.parseInt(split[0].replace("<amountSeatsMax>\n", ""));
        ArrayList<Integer> amountSeats = new ArrayList<>();
        for (int i = amountSeatsMin; i <= amountSeatsMax; i++) {
            amountSeats.add(i);
        }
        split = split[1].split("\n</timeout>\n");
        TimeOut timeout = TimeOutSaverLoader.createFromSaveString(split[0].replace("<timeout>\n", ""));
        split = split[1].split("\n</processes>\n");
        int processes = Integer.parseInt(split[0].replace("<processes>\n", ""));
        split = split[1].split("\n</argument>\n");
        String argument = split[0].replace("<argument>\n", "");



        return new ElectionCheckParameter(amountVoters, amountCandidates, amountSeats, timeout, processes, argument);
    }
}
