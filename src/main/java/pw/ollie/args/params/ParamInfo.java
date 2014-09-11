package pw.ollie.args.params;

/**
 * Contains information about a parameter, which can be required or optional.
 */
public class ParamInfo {
    /**
     * The name of the parameter.
     */
    private final String name;
    /**
     * Whether this parameter is optional.
     */
    private final boolean optional;

    /**
     * Constructs a new {@link ParamInfo} with the given name.
     *
     * @param name the name of the parameter
     * @param optional whether the parameter is optional
     */
    public ParamInfo(String name, boolean optional) {
        this.name = name;
        this.optional = optional;
    }

    /**
     * Gets the name of this parameter.
     *
     * @return the name of this parameter
     */
    public String getName() {
        return name;
    }

    /**
     * Returns whether this parameter is optional.
     *
     * @return {@code true} if the parameter is optional, else {@code false}
     */
    public boolean isOptional() {
        return optional;
    }
}
