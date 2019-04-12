/*
 * This file is part of jlibargs, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014-2019 Oliver Stanley <http://ollie.pw>
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
package pw.ollie.args.params.impl;

import pw.ollie.args.Arguments;
import pw.ollie.args.params.ParamInfo;
import pw.ollie.args.params.Parameter;
import pw.ollie.args.params.ParamsBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * A base to create {@link SimpleParams} objects from - used so that we don't
 * parse the usage string every time a command is executed.
 */
public final class SimpleParamsBase implements ParamsBase {
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
    private final List<FlagInfo> requiredFlags;
    /**
     * All registered parameter processors.
     */
    private final List<BiFunction<ParamInfo, String, String>> processors;

    /**
     * Creates a new ParamsBase for the given {@link List} of params and the
     * given amount of arguments before the first parameter.
     *
     * @param params the parameters for this ParamsBase
     * @param argsBeforeParams the amount of arguments before the first param
     * @param amtRequired the amount of required parameters
     */
    private SimpleParamsBase(List<ParamInfo> params, int argsBeforeParams,
            int amtRequired, List<FlagInfo> requiredFlags) {
        this.params = params;
        this.argsBeforeParams = argsBeforeParams;
        this.amtRequired = amtRequired;
        this.requiredFlags = requiredFlags;
        this.processors = new ArrayList<>();
    }

    @Override
    public int length() {
        return params.size();
    }

    @Override
    public int getAmtRequired() {
        return amtRequired;
    }

    @Override
    public void registerProcessor(
            BiFunction<ParamInfo, String, String> processor) {
        processors.add(processor);
    }

    @Override
    public void unregisterProcessor(
            BiFunction<ParamInfo, String, String> processor) {
        processors.remove(processor);
    }

    /**
     * Gets the amount of flags required to satisfy the requirements of this
     * {@link ParamsBase}.
     *
     * @return the required amount of flags
     */
    public int getAmtRequiredFlags() {
        return requiredFlags.size();
    }

    /**
     * Gets the amount of arguments before the first parameter.
     *
     * @return the amount of arguments before the first parameter
     */
    public int getArgsBeforeParams() {
        return argsBeforeParams;
    }

    @Override
    public SimpleParams createParams(Arguments args) {
        Map<String, Parameter> map = new HashMap<>();
        int curArg = argsBeforeParams;
        int curParam = 0;
        int curFlag = 0;
        boolean valid = true;

        while (curArg < args.length(false) && curParam < params.size()) {
            if (curFlag < requiredFlags.size()) {
                // get through as many flags as possible in the same loop to
                // be supah-efficient (yes I know the performance gain is tiny)
                if (!args.hasValueFlag(requiredFlags.get(curFlag++).name)) {
                    // a required flag isn't present
                    valid = false;
                }
            }

            ParamInfo info = params.get(curParam);
            String val = process(info, args.getRaw(curArg, false));

            map.put(info.getName(), new Parameter(val, info));
            curArg++;
            curParam++;
        }

        while (curFlag < requiredFlags.size()) {
            // get through the rest of the flags...
            if (!args.hasValueFlag(requiredFlags.get(curFlag++).name)) {
                // a required flag isn't present
                valid = false;
            }
        }

        SimpleParams params = new SimpleParams(args, this, map);
        if (!valid || amtRequired > map.size()) {
            params.invalidate();
        }

        return params;
    }

    private String process(ParamInfo info, String argument) {
        for (BiFunction<ParamInfo, String, String> processor : processors) {
            String processed = processor.apply(info, argument);
            if (processed != null && !processed.isEmpty()) {
                argument = processed;
            }
        }
        return argument;
    }

    /**
     * Builds a new ParamsBase by parsing the given usage string for a command.
     *
     * @param usageString the command usage string to parse
     * @return a new ParamsBase created from parsing the given usage string
     */
    public static SimpleParamsBase fromUsageString(String usageString) {
        // list of parameters parsed
        List<ParamInfo> res = new ArrayList<>();
        // current parse status
        int status = NO_PARAMETER;
        // the builder used for building params
        StringBuilder builder = null;
        // whether we've reached the first param
        boolean reachedFirst = false;
        // the amount of arguments before the first param
        int before = 0;
        // whether we've passed the initial command
        boolean passedCommand = false;
        // the amount of required arguments
        int amtRequired = 0;
        // flags present
        List<FlagInfo> requiredFlags = new ArrayList<>();

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
                        }
                    }

                    if (!isOptional) {
                        requiredFlags.add(new FlagInfo(String.valueOf(next)));
                    }

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

        return new SimpleParamsBase(res, before, amtRequired, requiredFlags);
    }

    /**
     * Basic flag information, used only in {@link SimpleParamsBase}.
     */
    private static class FlagInfo {
        /**
         * Name (denotation) of the flag. This represents the first 'component'
         * of a flag. For example, in '-f val', this would be 'f'.
         */
        final String name;

        FlagInfo(String name) {
            this.name = name;
        }
    }
}
