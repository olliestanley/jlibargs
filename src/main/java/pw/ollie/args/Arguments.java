package pw.ollie.args;

import pw.ollie.args.params.Parameter;
import pw.ollie.args.params.Params;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple and easy to use method of parsing arguments into different primitive
 * types and parsing flags.
 */
public class Arguments {
    /**
     * A List of all of the arguments in StringWrapper form.
     */
    private final List<StringWrapper> all;
    /**
     * A List of arguments, not including flag arguments, in StringWrapper form.
     */
    private final List<StringWrapper> arguments;
    /**
     * A List of all flags prefixed with -
     */
    private final List<Flag> flags;
    /**
     * A List of all flags prefixed with --
     */
    private final List<Flag> doubleFlags;
    /**
     * The raw String[] of arguments for this Arguments object.
     */
    private final String[] raw;

    /**
     * The Params object for this Arguments object. This contains a Map of
     * parameter names to ParamStringWrapper values for each registered parameter
     * for the command.
     */
    private Params parameters;

    /**
     * Creates a new Arguments object and immediately parses the given String[]
     * of arguments into StringWrappers and Flags.
     *
     * @param parse the String[] of raw arguments to parse
     */
    public Arguments(String... parse) {
        this.all = new ArrayList<>();
        this.arguments = new ArrayList<>();
        this.flags = new ArrayList<>();
        this.doubleFlags = new ArrayList<>();

        raw = parse;
        for (int i = 0; i < raw.length; i++) {
            final String arg = raw[i];
            all.add(new StringWrapper(arg));

            switch (arg.charAt(0)) {
                case '-':
                    if (arg.length() < 2) {
                        arguments.add(new StringWrapper(arg));
                        continue;
                    }
                    if (arg.charAt(1) == '-') {
                        if (arg.length() < 3) {
                            arguments.add(new StringWrapper(arg));
                            continue;
                        }
                        // flag with double -- (no value)
                        doubleFlags.add(new Flag(arg.substring(2, arg.length()),
                                null));
                    } else {
                        if (raw.length - 1 == i) {
                            arguments.add(new StringWrapper(arg));
                            continue;
                        }
                        // flag with single - (plus value)
                        flags.add(new Flag(arg.substring(1, arg.length()),
                                raw[++i]));
                    }
                    break;
                default:
                    // normal argument
                    arguments.add(new StringWrapper(raw[i]));
                    break;
            }
        }
    }

    /**
     * Gets the StringWrapper for the argument at the given index
     *
     * @param index the index to get the argument from
     * @return a StringWrapper object for the argument at the given index
     */
    public StringWrapper get(int index) {
        return get(index, true);
    }

    /**
     * Gets the StringWrapper for the argument at the given index.
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
     * Gets the Params for this set of Arguments.
     *
     * @return this Arguments object's Params object
     */
    public Params getParams() {
        return parameters;
    }

    /**
     * Checks whether Params are available for these Arguments.
     *
     * @return true if this Arguments object has a Params object, else false
     */
    public boolean hasParams() {
        return getParams() != null;
    }

    /**
     * Gets a Parameter value for the parameter with the given name, if
     * there is a Params object available for these Arguments and said Params
     * object contains a value for the given parameter. If either of these
     * conditions are not true, null is returned.
     *
     * @param parameter the parameter to get the ParamStringWrapper value for
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
     * is a Params object available for these Arguments and said Params object
     * contains a value for the given parameter. If either of these conditions
     * are not true, null is returned
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
     * Checks whether the given parameter is available in this Arguments' Params
     * object.
     *
     * @param parameter the parameter to check for the presence of
     * @return whether the given parameter is available
     */
    public boolean hasParam(String parameter) {
        return hasParams() && getParams().has(parameter);
    }

    /**
     * Gets the Flag object with the given name, or null if it doesn't exist.
     *
     * @param flag the name of the flag to get the Flag object for
     * @return the Flag object for the flag with the given name - null if there
     *         isn't one
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
     * name
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
     * Sets the Params object for this Arguments object. Should only be called
     * directly after creation. If this is called multiple times an
     * IllegalStateException will be thrown
     *
     * @param parameters the Params to set for this Arguments object
     * @return this Arguments object
     */
    public Arguments withParams(Params parameters) {
        if (this.parameters != null) {
            throw new IllegalStateException();
        }
        this.parameters = parameters;
        return this;
    }
}
