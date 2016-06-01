jlibargs
===

Argument parsing library for Java. Aimed at simple command systems with flag-based parameters and other arguments.

Usage
======

An Arguments object can easily be instantiated from a raw input string using the constructor Arguments(String...) - this will automatically parse the given arguments for value-flags (single - with a value following, e.g "-m hello") and simple flags (double -- with no value following, e.g "--m"). For something such as a command, with specific parameters which must be input, a SimpleParamsBase can be created with the names of the required objects. This can then be used to create Params objects from Arguments with the method ParamsBase.createParams(Arguments)

The library is fairly simple to use and can all be figured out from the JavaDocs.
