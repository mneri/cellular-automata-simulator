package me.mneri.ca.diagram;

import me.mneri.ca.automaton.Automaton;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum DiagramEnum {
    STATE("State", StateDiagram.class),
    ENTROPY("Entropy", EntropyDiagram.class),
    JOINT_ENTROPY("Joint Entropy", JointEntropyDiagram.class),
    CONDITIONAL_ENTROPY("Conditional Entropy", ConditionalEntropyDiagram.class),
    ENTROPY_RATE("Entropy Rate", EntropyRateDiagram.class);

    private static final Map<String, DiagramEnum> STRING_ENUM_MAP;

    private Class<? extends Diagram> mClass;
    private String mName;

    DiagramEnum(String name, Class<? extends Diagram> clazz) {
        mName = name;
        mClass = clazz;
    }

    public DiagramEnum get(String name) {
        return STRING_ENUM_MAP.get(name);
    }

    public Diagram toDiagram(Automaton history) {
        try {
            return mClass.getConstructor(Automaton.class).newInstance(history);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return mName;
    }

    static {
        Map<String, DiagramEnum> map = new ConcurrentHashMap<>();

        for (DiagramEnum value : DiagramEnum.values())
            map.put(value.toString(), value);

        STRING_ENUM_MAP = Collections.unmodifiableMap(map);
    }
}
