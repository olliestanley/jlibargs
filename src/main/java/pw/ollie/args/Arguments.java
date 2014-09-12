package pw.ollie.args;

import pw.ollie.args.params.Parameter;
import pw.ollie.args.params.Params;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple and easy to use method of parsing arguments into different primitive
 * types and parsing flags.
 */
public class Arguments {
    /**
     * A {@link List} of all of the arguments in StringWrapper form.
     */
    private final List<StringWrapper> all;
    /**
     * A List of arguments, not including flag arguments, in StringWrapper form.
     */
    private final List<StringWrapper> arguments;
    /**
     * A {@link List} of all flags prefixed with -
     */
    private final Set<Flag> flags;
    /**
     * A {@link List} of all flags prefixed with --
     */
    private final Set<Flag> doubleFlags;
    /**
     * The raw String[] of arguments for this Arguments object.
     */
    private final String[] raw;

    /**
     * The {@link Params} object for this Arguments object. This contains a
     * {@link Map} of parameter names to ParamStringWrapper values for each
     * registered parameter for the command.
     */
    private Params parameters;

    /**
     * Creates a new Arguments object and immediately parses the given String[]
     * of arguments into {@link StringWrapper}s and {@link Flag}s.
     *
     * @param parse the String[] of raw arguments to parse
     */
    public Arguments(String... parse) {
        this.all = new ArrayList<>(parse.length);
        this.arguments = new ArrayList<>(parse.length);
        this.flags = new HashSet<>();
        this.doubleFlags = new HashSet<>();
        this.raw = parse;

        for (int i = 0; i < raw.length; i++) {
            String arg = raw[i];
            boolean isLastArg = i == raw.length - 1;
            // construct a single StringWrapper for the arg
            StringWrapper sw = new StringWrapper(arg);

            // add to the list of all arguments
            all.add(sw);

            boolean flag = arg.charAt(0) == '-';
            if (!flag || arg.length() < 2) {
                // normal argument, or flag with no name (e.g, "-")
                arguments.add(sw);
                continue;
            }

            boolean doubleFlag = arg.charAt(1) == '-';
            if (doubleFlag && arg.length() < 3) {
                // arg is "--" - no name given for flag
                arguments.add(sw);
                continue;
            }

            // flag argument handling
            if (doubleFlag) {
                // double flag (--, no value)
                doubleFlags.add(new Flag(arg.substring(2, arg.length()), null));
                continue;
            }

            if (isLastArg) {
                // single flag but no value given (this is the last arg)
                arguments.add(sw);
                continue;
            }

            // single flag (-, value)
            flags.add(new Flag(arg.substring(1, arg.length()), raw[++i]));
        }
    }

    /**
     * Gets the {@link StringWrapper} for the argument at the given index.
     *
     * @param index the index to get the argument from
     * @return a StringWrapper object for the argument at the given index
     */
    public StringWrapper get(int index) {
        return get(index, true);
    }

    /**
     * Gets the {@link StringWrapper} for the argument at the given index.
     *
     * @param index the index to get the argument from
     * @param includeFlagArgs whether to include flag args in the index
     * @return a StringWrapper object for the argument at the given index
     */
    public StringWrapper get(int index, boolean includeFlagArgs) {
        if (includeFlagArgs) {
            return all.get(index);
        } else {
            return arguments.get(index);
        }
    }

    /**
     * Gets the raw string for the argument at the given index.
     *
     * @param index the index to get the argument from
     * @return a raw String for the argument at the given index
     */
    public String getRaw(int index) {
        return getRaw(index, true);
    }

    /**
     * Gets the raw string for the argument at the given index.
     *
     * @param index the index to get the argument from
     * @param includeFlagArgs whether to include flag args in the index
     * @return a raw String for the argument at the given index
     */
    public String getRaw(int index, boolean includeFlagArgs) {
        return get(index, includeFlagArgs).rawString();
    }

    /**
     * Gets the {@link Params} for this set of Arguments. May be {@code null}.
     *
     * @return this Arguments object's Params object
     */
    public Params getParams() {
        return parameters;
    }

    /**
     * Checks whether {@link Params} are available for these Arguments.
     *
     * @return {@code true} if this Arguments object has a Params object, else
     *         {@code false}
     */
    public boolean hasParams() {
        return getParams() != null;
    }

    /**
     * Gets a {@link Parameter} value for the parameter with the given name, if
     * there is a {@link Params} object available for these Arguments and said
     * {@link Params} object contains a value for the given parameter. If either
     * of these conditions are not {@code true}, {@code null} is returned.
     *
     * @param parameter the parameter to get the Parameter value for
     * @return a Parameter for the given parameter, or null if there isn't one
     */
    public Parameter param(String parameter) {
        if (!hasParams()) {
            return null;
        }
        return getParams().get(parameter);
    }

    /**
     * Gets the raw string value for the parameter with the given name, if there
     * is a {@link Params} object available for these Arguments and said Params
     * object contains a value for the given parameter. If either of these
     * conditions are not {@code true}, {@code null} is returned.
     *
     * @param parameter the parameter to get the raw string value for
     * @return a string value for the given parameter, or null if there isn't
     *         one
     */
    public String rawParam(String parameter) {
        Parameter param = param(parameter);
        if (param == null) {
            return null;
        }
        return param.rawString();
    }

    /**
     * Checks whether the given parameter is available in this Arguments' {@link
     * Params} object.
     *
     * @param parameter the parameter to check for the presence of
     * @return whether the given parameter is available
     */
    public boolean hasParam(String parameter) {
        return hasParams() && getParams().has(parameter);
    }

    /**
     * Gets the {@link Flag} object with the given name, or {@code null} if it
     * doesn't exist.
     *
     * @param flag the name of the flag to get the {@link Flag} object for
     * @return the {@link Flag} object for the flag with the given name - {@code
     *         null} if there isn't one
     */
    public Flag getValueFlag(String flag) {
        for (Flag f : flags) {
            if (f.getName().equalsIgnoreCase(flag)) {
                return f;
            }
        }
        return null;
    }

    /**
     * Checks whether these arguments contain a flag with a value with the given
     * name.
     *
     * @param flag the name of the flag to check for
     * @return whether these arguments contain a value flag with the given name
     */
    public boolean hasValueFlag(String flag) {
        for (Flag f : flags) {
            if (f.getName().equalsIgnoreCase(flag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether these arguments contain a flag with no value with the
     * given name.
     *
     * @param flag the name of the flag to check for
     * @return whether these arguments contain a non-value flag with the given
     *         name
     */
    public boolean hasNonValueFlag(String flag) {
        for (Flag f : doubleFlags) {
            if (f.getName().equalsIgnoreCase(flag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the length of the arguments.
     *
     * @return the amount of arguments in this Arguments object
     */
    public int length() {
        return length(true);
    }

    /**
     * Gets the length of the arguments.
     *
     * @param includeFlagArgs whether to include flag args in the arg count
     * @return the amount of arguments in this Arguments object
     */
    public int length(boolean includeFlagArgs) {
        if (includeFlagArgs) {
            return all.size();
        } else {
            return arguments.size();
        }
    }

    /**
     * Converts this Arguments object to a raw String[] of arguments.
     *
     * @return a raw String[] of arguments for this object
     */
    public String[] toStringArray() {
        String[] result = new String[raw.length];
        System.arraycopy(raw, 0, result, 0, raw.length);
        return result;
    }

    /**
     * Sets the {@link Params} object for this Arguments object. Should only be
     * called directly after creation. If this is called multiple times an
     * {@link IllegalStateException} will be thrown.
     *
     * @param parameters the {@link Params} to set for this Arguments object
     * @return this {@link Arguments} object
     */
    public Arguments withParams(Params parameters) {
        if (this.parameters != null) {
            throw new IllegalStateException();
        }
        this.parameters = parameters;
        return this;
    }
}
