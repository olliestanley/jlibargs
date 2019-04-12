jlibargs
===

Simple argument parsing library for Java.

Usage Guide
======

jlibargs has two primary sections - argument parsing, and parameters.

An Arguments object can be instantiated by passing an array of the arguments to parse into the constructor. From here, the simplest use case is to obtain an Argument object for each element of the array. This allows easy conversion of an argument to primitive types including methods to test whether they can be converted to these types.

Flags are also a feature of the argument parsing element of jlibargs. An argument beginning with -- will be parsed as a non-value flag. An argument beginning with - will be parsed as a value flag with a value of the following argument, unless there is no following argument in which case it is parsed as a non-value flag.

~~~~
Arguments arguments = new Arguments("hello", "world", "--doubleprint", "-print", "7");

Argument first = arguments.get(0); // returns an Argument with a raw value of "hello"
String firstRaw = arguments.getString(0); // returns "hello"

boolean doublePrint = arguments.hasNonValueFlag("doubleprint"); // returns true

Flag printFlag = arguments.getValueFlag("print");
Argument printValue = printFlag.getValue();
if (printValue.isInt()) {
    System.out.println(printValue.asInt()); // printValue.asInt() returns the int 7
    if (doublePrint) {
        System.out.println(printValue.asInt());
    }
}
~~~~

The more complex part of jlibargs is the parameters system. Arguments can be created (through the appropriate constructor) or modified (through the withParams method) to be built on top of a ParamsBase object. This allows for simplification of code using jlibargs in situations where the arguments you are parsing are expected to be in a certain format. To achieve this a ParamsBase object must be created (such as a SimpleParamsBase) which has specified required and optional arguments and flags. ParamsBase objects can be generated through a usage string. Arguments enclosed by <> indicate a required argument and those enclosed by [] indicate an optional argument.

~~~~
SimpleParamsBase base = SimpleParamsBase.fromUsageString("/command <-f number> <option1> [optional]");

Arguments args = new Arguments(base, "-f", "17", "off", "on");
Params params = args.getParams();

boolean valid = params.valid(); // returns true here, but would return false if any required arguments were not present
if (valid) {
    Parameter option1 = params.get("option1"); // as the Params is valid we know this is present

    String optionalValue = "default";
    if (params.has("optional")) { // we need to check this as it's an optional parameter
        Parameter optional = params.get("optional");
        optionalValue = optional.getString();
    }

    // the validity of the params also confirms that the required value flag 'f' is present in the arguments
    Argument fFlag = args.getValueFlag("f").getValue();
    if (fFlag.isInt()) {
        System.out.println("f: " + f.asInt());
    }
}
~~~~

Parameter extends Argument, meaning the primitive type checking / parsing methods are available for the values of parameters.

Dependencies
=======

jlibargs requires Java 8.
