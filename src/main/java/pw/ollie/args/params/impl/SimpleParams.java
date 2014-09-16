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
package pw.ollie.args.params.impl;

import pw.ollie.args.Arguments;
import pw.ollie.args.params.Parameter;
import pw.ollie.args.params.Params;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

/**
 * A wrapper around a {@link Map} to provide a set of parameters for commands
 * and similar.
 */
public class SimpleParams implements Params {
    /**
     * The base {@link Arguments} parsed to create these {@link SimpleParams}.
     */
    private final Arguments arguments;
    /**
     * Base information for these {@link SimpleParams}.
     */
    private final SimpleParamsBase base;
    /**
     * A {@link Map} of all of the parameters.
     */
    private final Map<String, Parameter> params;

    /**
     * Whether this set of parameters is valid.
     */
    private boolean valid = true;

    /**
     * Creates a new set of {@link SimpleParams} from the given {@link Map} of
     * parameters to values.
     *
     * @param base base information for these params
     * @param params the parameters and their values for this Params object
     */
    public SimpleParams(Arguments arguments, SimpleParamsBase base,
            Map<String, Parameter> params) {
        this.arguments = arguments;
        this.params = params;
        this.base = base;
    }

    @Override
    public Arguments getArguments() {
        return arguments;
    }

    @Override
    public Optional<Parameter> get(String parameter) {
        Parameter val = params.get(parameter);
        if (val == null) {
            return Optional.empty();
        }
        return Optional.of(val);
    }

    @Override
    public boolean has(String parameter) {
        return params.containsKey(parameter);
    }

    @Override
    public Set<String> parameters() {
        return new HashSet<>(params.keySet());
    }

    @Override
    public Set<Parameter> values() {
        return new HashSet<>(params.values());
    }

    @Override
    public SimpleParamsBase getBase() {
        return base;
    }

    @Override
    public boolean valid() {
        return valid;
    }

    /**
     * Gets a {@link Set} of all of the entries to the {@link Map} of parameters
     * contained by this {@link SimpleParams} object.
     *
     * @return a {@link Set} of all entries to the parameters {@link Map}
     */
    public Set<Entry<String, Parameter>> entries() {
        return new HashSet<>(params.entrySet());
    }

    /**
     * Invalidates this set of parameters. Should only be used in automatic
     * validation in {@link SimpleParamsBase#createParams(Arguments)}.
     */
    void invalidate() {
        valid = false;
    }
}
