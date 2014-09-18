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

/**
 * A base used to create {@link Params} objects from. Used for {@link Params}
 * implementations where parsing which is the same for multiple situations is
 * required - that parsing can be put into the {@link ParamsBase} and specific
 * parsing can be added to {@link #createParams(Arguments)} or a constructor in
 * {@link Params}.
 */
public interface ParamsBase {
    /**
     * Creates a new {@link Params} object using this {@link ParamsBase}, and
     * parsing the given {@link Arguments} to create the parameters map.
     *
     * @param args the {@link Arguments} to get parameter values from
     * @return a new {@link Params} object from this base and the given args
     */
    Params createParams(Arguments args);

    /**
     * Gets the total amount of parameters.
     *
     * @return the total amount of parameters in this ParamsBase
     */
    int length();

    /**
     * Gets the amount of required parameters.
     *
     * @return the amount of required parameters
     */
    int getAmtRequired();

    /**
     * Gets the amount of non-required (optional) parameters.
     *
     * @return the amount of optional parameters
     */
    default int getAmtOptional() {
        return length() - getAmtRequired();
    }
}
