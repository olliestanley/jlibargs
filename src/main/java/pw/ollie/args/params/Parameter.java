package pw.ollie.args.params;

import pw.ollie.args.StringWrapper;

/**
 * An extension of StringWrapper, used for parameters.
 */
public class Parameter extends StringWrapper {
    /**
     * The information (name, whether it is optional) for this parameter
     */
    private final ParamInfo info;

    /**
     * Creates a new Parameter, using the given String argument as a raw string.
     *
     * @param arg the raw string for this ParamStringWrapper
     * @param info information about this parameter
     */
    public Parameter(String arg, ParamInfo info) {
        super(arg);
        this.info = info;
    }

    /**
     * Gets the ParamInfo for this Parameter
     *
     * @return this Parameter's ParamInfo object
     */
    public ParamInfo getInfo() {
        return info;
    }
}
