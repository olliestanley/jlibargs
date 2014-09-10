package pw.ollie.args;

/**
 * A flag which simply has a name and a value
 */
public class Flag {
    /**
     * Information about the flag
     */
    private final String flag;
    /**
     * The StringWrapper representing the value of this flag, which provides
     * various methods to use the value
     */
    private final StringWrapper valArg;

    public Flag(final String flag, final String value) {
        this.flag = flag;
        valArg = new StringWrapper(value);
    }

    public String getName() {
        return flag;
    }

    public StringWrapper getValue() {
        return valArg;
    }

    public String getRawValue() {
        return getValue().rawString();
    }
}
