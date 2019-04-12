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

import pw.ollie.args.Argument;

/**
 * An extension of {@link Argument}, used for parameters.
 */
public class Parameter extends Argument {
    /**
     * The information (name, whether it is optional) for this parameter
     */
    private final ParamInfo info;

    /**
     * Creates a new {@link Parameter}, using the given {@link String} argument
     * as a raw string.
     *
     * @param arg the raw string for this {@link Parameter}
     * @param info information about this parameter
     */
    public Parameter(String arg, ParamInfo info) {
        super(arg);
        this.info = info;
    }

    /**
     * Gets the {@link ParamInfo} for this {@link Parameter}.
     *
     * @return this {@link Parameter}'s {@link ParamInfo} object
     */
    public ParamInfo getInfo() {
        return info;
    }
}
