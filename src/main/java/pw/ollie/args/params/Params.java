package pw.ollie.args.params;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A wrapper around a {@link Map} to provide a set of parameters for commands
 * and similar.
 */
public class Params {
    /**
     * A {@link Map} of all of the parameters.
     */
    private final Map<String, Parameter> params;
    /**
     * Base information for these {@link Params}.
     */
    private final ParamsBase base;

    /**
     * Whether this set of parameters is valid.
     */
    private boolean valid = true;

    /**
     * Creates a new set of {@link Params} from the given {@link Map} of
     * parameters to values.
     *
     * @param base base information for these params
     * @param params the parameters and their values for this Params object
     */
    public Params(ParamsBase base, Map<String, Parameter> params) {
        this.params = params;
        this.base = base;
    }

    /**
     * Gets a {@link Parameter} value for the given parameter.
     *
     * @param parameter the parameter to get the value for
     * @return a {@link Parameter} for the given parameter
     */
    public Parameter get(String parameter) {
        return params.get(parameter);
    }

    /**
     * Gets the {@link ParamsBase} object for this set of {@link Params}.
     * Contains basic info about the parameters involved.
     *
     * @return basic information about these {@link Params}
     */
    public ParamsBase getBaseInfo() {
        return base;
    }

    /**
     * Checks whether these {@link Params} contain a value for the given
     * parameter.
     *
     * @param parameter the parameter to check for the presence of
     * @return whether the given parameter has a value in this Params object
     */
    public boolean has(String parameter) {
        return params.containsKey(parameter);
    }

    /**
     * Gets a {@link Set} of all of the parameter names contained by this {@link
     * Params} object's {@link Map} of parameters.
     *
     * @return a {@link Set} of all of the parameter names for these parameters
     */
    public Set<String> parameters() {
        return new HashSet<>(params.keySet());
    }

    /**
     * Gets a {@link Set} of all of the parameter values contained by this
     * object's {@link Map} of parameters.
     *
     * @return a {@link Set} of all of the parameter values for these parameters
     */
    public Set<Parameter> values() {
        return new HashSet<>(params.values());
    }

    /**
     * Gets a {@link Set} of all of the entries to the {@link Map} of parameters
     * contained by this {@link Params} object.
     *
     * @return a {@link Set} of all entries to the parameters {@link Map}
     */
    public Set<Entry<String, Parameter>> entries() {
        return new HashSet<>(params.entrySet());
    }

    /**
     * Checks whether this set of parameters is valid for the base it was
     * constructed for. Used for automatic validation of arguments.
     *
     * @return whether this set of parameters is valid
     */
    public boolean valid() {
        return valid;
    }

    /**
     * Invalidates this set of parameters. Should only be used in automatic
     * validation.
     */
    void invalidate() {
        valid = false;
    }
}
