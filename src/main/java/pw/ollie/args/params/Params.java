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
package pw.ollie.args.params;

import pw.ollie.args.Arguments;

import java.util.Collection;
import java.util.Optional;

/**
 * Represents a map of named parameters to user-inputted values.
 */
public interface Params {
    /**
     * Gets the {@link Arguments} object which was parsed to create this {@link
     * Params} object.
     *
     * @return these {@link Params}' raw {@link Arguments}
     */
    Arguments getArguments();

    /**
     * Gets an {@link Optional} containing the {@link Parameter} value for the
     * parameter with the given name - this should return {@link
     * Optional#empty()} in the event of the given parameter name not having a
     * specified value.
     *
     * @param name the name of the parameter to get the value for
     * @return an {@link Optional} of the {@link Parameter} with the given name
     */
    Optional<Parameter> get(String name);

    /**
     * Returns whether the given parameter has a user-specified value in this
     * {@link Params} object.
     *
     * @param param the parameter to check for the presence of
     * @return {@code true} if the given parameter has a value, else {@code
     *         false}
     */
    boolean has(String param);

    /**
     * Gets a {@link Collection} of all parameter names present within these
     * {@link Params}.
     *
     * @return all parameter names present
     */
    Collection<String> parameters();

    /**
     * Gets a {@link Collection} of all {@link Parameter} values present within
     * these {@link Params}.
     *
     * @return all present {@link Parameter} values
     */
    Collection<Parameter> values();

    /**
     * Gets the {@link ParamsBase} which this {@link Params} object was built
     * from.
     *
     * @return this object's base {@link ParamsBase} object
     */
    ParamsBase getBase();

    /**
     * Returns whether these {@link Params} are valid, i.e whether they satisfy
     * requirements for required arguments and flags specified for these {@link
     * Params}' {@link ParamsBase} object.
     *
     * @return {@code true} if these params are valid, else {@code false}
     */
    boolean valid();
}
