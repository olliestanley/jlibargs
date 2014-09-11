package pw.ollie.args.params;

/**
 * Basic flag information, used only in {@link ParamsBase}.
 */
public class FlagInfo {
    /**
     * Name (denotation) of the flag. This represents the first 'component' of a
     * flag. For example, in '-f val', this would be 'f'.
     */
    private final String name;
    /**
     * A short description of the flag. Should be one word. This represents the
     * second 'component' of a flag. In '-f val', this would be 'val'.
     */
    private final String desc;
    /**
     * Whether this flag is optional.
     */
    private final boolean optional;

    public FlagInfo(String name, String desc, boolean optional) {
        this.name = name;
        this.desc = desc;
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isOptional() {
        return optional;
    }
}
