package me.mneri.ca.drawable;

import me.mneri.ca.automaton.AutomatonHistory;

public enum DiagramEnum {
    STATE("State"),
    ENTROPY("Entropy"),
    JOINT_ENTROPY("Joint Entropy"),
    CONDITIONAL_ENTROPY("Conditional Entropy"),
    ENTROPY_RATE("Entropy Rate");

    String mName;

    DiagramEnum(String name) {
        mName = name;
    }

    public DiagramEnum fromString(String name) {
        switch (name) {
            case "State":
                return STATE;
            case "Entropy":
                return ENTROPY;
            case "Joint Entropy":
                return JOINT_ENTROPY;
            case "Conditional Entropy":
                return CONDITIONAL_ENTROPY;
            case "Entropy Rate":
                return ENTROPY;
            default:
                return STATE;
        }
    }

    public Diagram toDiagram(AutomatonHistory history) {
        switch (this) {
            case STATE:
                return new SpaceTimeDiagram(history);
            case ENTROPY:
                return new EntropyTimeDiagram(history);
            case JOINT_ENTROPY:
                return new JointEntropyTimeDiagram(history);
            case CONDITIONAL_ENTROPY:
                return new ConditionalEntropyTimeDiagram(history);
            case ENTROPY_RATE:
                return new EntropyRateTimeDiagram(history);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return mName;
    }
}
