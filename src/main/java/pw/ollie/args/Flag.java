package pw.ollie.args;

/**
 * A flag which simply has a name and a value.
 */
public class Flag {
    /**
     * The name of the flag.
     */
    private final String name;
    /**
     * The {@link StringWrapper} representing the value of this flag, which
     * provides various methods to use the value.
     */
    private final StringWrapper valArg;

    /**
     * Constructs a new {@link Flag} with the given name and the given value.
     *
     * @param name the name of the flag
     * @param value the value for the flag
     */
    public Flag(String name, StringWrapper value) {
        this.name = name;
        this.valArg = value;
    }

    /**
     * Constructs a new {@link Flag} with the given name and the given value.
     *
     * @param name the name of the flag
     * @param value the value for the flag
     * @deprecated use {@link #Flag(String, StringWrapper)}
     */
    public Flag(String name, String value) {
        this(name, new StringWrapper(value));
    }

    /**
     * Gets the name of this flag - for example, 'f' in '-f trees'.
     *
     * @return this flag's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the {@link StringWrapper} which represents the value provided for
     * this flag.
     *
     * @return this flag's value
     */
    public StringWrapper getValue() {
        return valArg;
    }

    /**
     * Gets the raw {@link String} value which was provided for this flag. For
     * example, 'trees' in '-f trees'.
     *
     * @return this flag's raw {@link String} value
     */
    public String getRawValue() {
        return getValue().get();
    }
}
