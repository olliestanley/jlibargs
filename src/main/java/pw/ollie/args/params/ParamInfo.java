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

/**
 * Contains information about a parameter, which can be required or optional.
 */
public class ParamInfo {
    /**
     * The name of the parameter.
     */
    private final String name;
    /**
     * Whether this parameter is optional.
     */
    private final boolean optional;

    /**
     * Constructs a new {@link ParamInfo} with the given name.
     *
     * @param name the name of the parameter
     * @param optional whether the parameter is optional
     */
    public ParamInfo(String name, boolean optional) {
        this.name = name;
        this.optional = optional;
    }

    /**
     * Gets the name of this parameter.
     *
     * @return the name of this parameter
     */
    public String getName() {
        return name;
    }

    /**
     * Returns whether this parameter is optional.
     *
     * @return {@code true} if the parameter is optional, else {@code false}
     */
    public boolean isOptional() {
        return optional;
    }
}
