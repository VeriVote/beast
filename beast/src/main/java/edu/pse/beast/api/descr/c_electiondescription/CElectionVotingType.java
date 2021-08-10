package edu.pse.beast.api.descr.c_electiondescription;

import java.util.List;

/*
 * so far:
 *
 * input:
 * approval int [V][C]
 * preference int [V][C]
 * single choice int[V]
 * single choice stack int[C]
 * TODO Parameter für range (0-1), (0-100)
 * weighted approval int[V][C]
 *
 * output:
 * candidate list int [C]
 * parliament int [C]
 * parliament stack int [C]
 * single cand int
 */

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CElectionVotingType {
    private int listDimensions;
    private CElectionSimpleType simpleType;
    private List<CBMCVars> listSizes;
    private boolean uniqueVotes;

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + listDimensions;
        result = prime * result
                + ((simpleType == null) ? 0 : simpleType.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final CElectionVotingType other = (CElectionVotingType) obj;
        return listDimensions == other.listDimensions
                && simpleType == other.simpleType;
    }

    public final int getListDimensions() {
        return listDimensions;
    }

    public final List<CBMCVars> getListSizes() {
        return listSizes;
    }

    public final CElectionSimpleType getSimpleType() {
        return simpleType;
    }

    public final boolean isUniqueVotes() {
        return uniqueVotes;
    }

    public final boolean canAccess(final CBMCVars var) {
        return !listSizes.isEmpty() && listSizes.get(0) == var;
    }

    public final CElectionVotingType getTypeOneDimLess() {
        final CElectionVotingType created = new CElectionVotingType();
        created.listDimensions = this.listDimensions - 1;
        created.simpleType = this.simpleType;
        created.listSizes = this.listSizes.subList(1, this.listSizes.size());
        return created;
    }

    public static final CElectionVotingType of(final VotingOutputTypes outType) {
        final CElectionVotingType created = new CElectionVotingType();
        switch (outType) {
        case CANDIDATE_LIST:
            created.simpleType = CElectionSimpleType.UNSIGNED_INT;
            created.listDimensions = 1;
            created.listSizes = List.of(CBMCVars.AMOUNT_CANDIDATES);
            break;
        case PARLIAMENT:
            created.simpleType = CElectionSimpleType.UNSIGNED_INT;
            created.listDimensions = 1;
            created.listSizes = List.of(CBMCVars.AMOUNT_CANDIDATES);
            break;
        case PARLIAMENT_STACK:
            created.simpleType = CElectionSimpleType.UNSIGNED_INT;
            created.listDimensions = 1;
            created.listSizes = List.of(CBMCVars.AMOUNT_CANDIDATES);
            break;
        case SINGLE_CANDIDATE:
            created.simpleType = CElectionSimpleType.UNSIGNED_INT;
            created.listDimensions = 0;
            created.listSizes = List.of();
            break;
        default:
            break;
        }
        return created;
    }

    public static final CElectionVotingType of(final VotingInputTypes inType) {
        final CElectionVotingType created = new CElectionVotingType();
        switch (inType) {
        case APPROVAL:
            created.simpleType = CElectionSimpleType.UNSIGNED_INT;
            created.listDimensions = 2;
            created.listSizes =
                    List.of(CBMCVars.AMOUNT_VOTERS, CBMCVars.AMOUNT_CANDIDATES);
            break;
        case PREFERENCE:
            created.simpleType = CElectionSimpleType.UNSIGNED_INT;
            created.listDimensions = 2;
            created.listSizes =
                    List.of(CBMCVars.AMOUNT_VOTERS, CBMCVars.AMOUNT_CANDIDATES);
            created.uniqueVotes = true;
            break;
        case SINGLE_CHOICE:
            created.simpleType = CElectionSimpleType.UNSIGNED_INT;
            created.listDimensions = 1;
            created.listSizes = List.of(CBMCVars.AMOUNT_VOTERS);
            break;
        case SINGLE_CHOICE_STACK:
            created.simpleType = CElectionSimpleType.UNSIGNED_INT;
            created.listDimensions = 1;
            created.listSizes = List.of(CBMCVars.AMOUNT_VOTERS);
            break;
        case WEIGHTED_APPROVAL:
            created.simpleType = CElectionSimpleType.UNSIGNED_INT;
            created.listDimensions = 2;
            created.listSizes =
                    List.of(CBMCVars.AMOUNT_VOTERS, CBMCVars.AMOUNT_CANDIDATES);
            break;
        default:
            break;
        }
        return created;
    }

    public static final CElectionVotingType simple() {
        final CElectionVotingType created = new CElectionVotingType();
        created.simpleType = CElectionSimpleType.UNSIGNED_INT;
        created.listDimensions = 0;
        created.listSizes = List.of();
        return created;
    }
}
