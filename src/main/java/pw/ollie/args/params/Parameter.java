package pw.ollie.args.params;

import pw.ollie.args.StringWrapper;

/**
 * An extension of {@link StringWrapper}, used for parameters.
 */
public class Parameter extends StringWrapper {
    /**
     * The information (name, whether it is optional) for this parameter
     */
    private final ParamInfo info;

    /**
     * Creates a new {@link Parameter}, using the given {@link String} argument
     * as a raw string.
     *
     * @param arg the raw string for this {@link Parameter}
     * @param info information about this parameter
     */
    public Parameter(String arg, ParamInfo info) {
        super(arg);
        this.info = info;
    }

    /**
     * Gets the {@link ParamInfo} for this {@link Parameter}.
     *
     * @return this {@link Parameter}'s {@link ParamInfo} object
     */
    public ParamInfo getInfo() {
        return info;
    }
}
