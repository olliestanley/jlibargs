/*
 * This file is part of jlibargs, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 Oliver Stanley <http://ollie.pw>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pw.ollie.args.params;

import pw.ollie.args.Arguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A base to create {@link Params} objects from - used so that we don't parse
 * the usage string every time a command is executed.
 */
public class ParamsBase {
    /**
     * The character which implies the beginning of a required parameter.
     */
    public static final char REQUIRED_OPEN_DENOTATION = '<';
    /**
     * The character which implies the closing of a required parameter.
     */
    public static final char REQUIRED_CLOSE_DENOTATION = '>';
    /**
     * The character which implies the opening of an optional parameter.
     */
    public static final char OPTIONAL_OPEN_DENOTATION = '[';
    /**
     * The character which implies the closing of an optional parameter.
     */
    public static final char OPTIONAL_CLOSE_DENOTATION = ']';
    /**
     * The character which separates arguments.
     */
    public static final char ARGUMENT_SEPARATOR = ' ';
    /**
     * The status code for no parameter currently being parsed.
     */
    private static final int NO_PARAMETER = 0;
    /**
     * The status code for a required parameter currently being parsed.
     */
    private static final int REQUIRED_PARAMETER = 1;
    /**
     * The status code for an optional parameter currently being parsed.
     */
    private static final int OPTIONAL_PARAMETER = 2;

    /**
     * A list of all of the parameters.
     */
    private final List<ParamInfo> params;
    /**
     * The number of arguments before the first parameter.
     */
    private final int argsBeforeParams;
    /**
     * The amount of arguments required for a valid Params object for this
     * ParamsBase, used for argument validation.
     */
    private final int amtRequired;
    /**
     * Flag information for validation.
     */
    private final List<FlagInfo> flags;

    /**
     * Creates a new ParamsBase for the given {@link List} of params and the
     * given amount of arguments before the first parameter.
     *
     * @param params the parameters for this ParamsBase
     * @param argsBeforeParams the amount of arguments before the first param
     * @param amtRequired the amount of required parameters
     */
    private ParamsBase(List<ParamInfo> params, int argsBeforeParams,
            int amtRequired, List<FlagInfo> flags) {
        this.params = params;
        this.argsBeforeParams = argsBeforeParams;
        this.amtRequired = amtRequired;
        this.flags = flags;
    }

    /**
     * Gets the amount of parameters.
     *
     * @return the amount of parameters in this ParamsBase
     */
    public int length() {
        return params.size();
    }

    /**
     * Gets the amount of arguments before the first parameter.
     *
     * @return the amount of arguments before the first parameter
     */
    public int getArgsBeforeParams() {
        return argsBeforeParams;
    }

    /**
     * Gets the amount of required parameters.
     *
     * @return the amount of required parameters
     */
    public int getAmountRequired() {
        return amtRequired;
    }

    /**
     * Gets the amount of non-required (optional) parameters.
     *
     * @return the amount of optional parameters
     */
    public int getAmountOptional() {
        return length() - getAmountRequired();
    }

    public int getAmountFlags() {
        return flags.size();
    }

    /**
     * Creates a set of parameters for this base using the given arguments.
     *
     * @param args the arguments to create the parameters from
     * @return a set of parameters for the given arguments
     */
    public Params createParams(Arguments args) {
        Map<String, Parameter> paramsMap = new HashMap<>();
        int curArgument = argsBeforeParams;
        int curParam = 0;
        int curFlag = 0;
        boolean invalid = false;

        while (curArgument < args.length(false) && curParam < params.size()) {
            if (curFlag < flags.size()) {
                if (!args.hasValueFlag(flags.get(curFlag++).getName())) {
                    invalid = true;
                }
            }

            String val = args.getRaw(curArgument, false);
            ParamInfo info = params.get(curParam);

            paramsMap.put(info.getName(), new Parameter(val, info));
            curArgument++;
            curParam++;
        }

        while (curFlag < flags.size()) {
            if (!args.hasValueFlag(flags.get(curFlag++).getName())) {
                invalid = true;
            }
        }

        Params params = new Params(this, paramsMap);
        if (invalid || amtRequired > paramsMap.size()) {
            params.invalidate();
        }

        return params;
    }

    /**
     * Builds a new ParamsBase by parsing the given usage string for a command.
     *
     * @param usageString the command usage string to parse
     * @return a new ParamsBase created from parsing the given usage string
     */
    public static ParamsBase fromUsageString(String usageString) {
        List<ParamInfo> res = new ArrayList<>();
        int status = NO_PARAMETER;
        StringBuilder builder = null;
        boolean reachedFirst = false;
        int before = 0;
        boolean passedCommand = false;
        int amtRequired = 0;
        List<FlagInfo> flags = new ArrayList<>();

        final char[] characters = usageString.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            final char ch = characters[i];

            if (!reachedFirst && ch == ARGUMENT_SEPARATOR) {
                if (passedCommand) {
                    before++;
                }
                passedCommand = true;
                continue;
            }

            if (status == REQUIRED_PARAMETER
                    && ch == REQUIRED_CLOSE_DENOTATION) {
                status = NO_PARAMETER;
                res.add(new ParamInfo(builder.toString(), false));
                amtRequired++;
                builder = null;
                continue;
            }

            if (status == OPTIONAL_PARAMETER
                    && ch == OPTIONAL_CLOSE_DENOTATION) {
                status = NO_PARAMETER;
                res.add(new ParamInfo(builder.toString(), true));
                builder = null;
                continue;
            }

            if (status == REQUIRED_PARAMETER || status == OPTIONAL_PARAMETER) {
                final char next = characters[i + 1];
                if (ch == '-' && next != REQUIRED_CLOSE_DENOTATION
                        && next != OPTIONAL_CLOSE_DENOTATION
                        && characters[i + 2] == ' ') {
                    StringBuilder desc = new StringBuilder();
                    int breakPoint = Integer.MAX_VALUE;
                    boolean isOptional = false;
                    for (int j = 3; j < breakPoint; j++) {
                        char toAppend = characters[i + j];
                        if (toAppend == REQUIRED_CLOSE_DENOTATION
                                || toAppend == OPTIONAL_CLOSE_DENOTATION) {
                            if (toAppend == OPTIONAL_CLOSE_DENOTATION) {
                                isOptional = true;
                            }
                            breakPoint = j;
                            continue;
                        }

                        desc.append(toAppend);
                    }

                    flags.add(new FlagInfo(String.valueOf(next),
                            desc.toString(), isOptional));

                    i += 2;
                    status = NO_PARAMETER;
                    builder = null;
                    continue;
                }

                builder.append(ch);
                continue;
            }

            if (ch == REQUIRED_OPEN_DENOTATION) {
                reachedFirst = true;
                status = REQUIRED_PARAMETER;
                builder = new StringBuilder();
            } else if (ch == OPTIONAL_OPEN_DENOTATION) {
                reachedFirst = true;
                status = OPTIONAL_PARAMETER;
                builder = new StringBuilder();
            }
        }

        return new ParamsBase(res, before, amtRequired, flags);
    }

    /**
     * Basic flag information, used only in {@link ParamsBase}.
     */
    private static class FlagInfo {
        /**
         * Name (denotation) of the flag. This represents the first 'component' of a
         * flag. For example, in '-f val', this would be 'f'.
         */
        private final String name;
        /**
         * A short description of the flag. Should be one word. This represents the
         * second 'component' of a flag. In '-f val', this would be 'val'.
         */
        private final String desc;
        /**
         * Whether this flag is optional.
         */
        private final boolean optional;

        public FlagInfo(String name, String desc, boolean optional) {
            this.name = name;
            this.desc = desc;
            this.optional = optional;
        }

        public String getName() {
            return name;
        }

        public String getDesc() {
            return desc;
        }

        public boolean isOptional() {
            return optional;
        }
    }
}
