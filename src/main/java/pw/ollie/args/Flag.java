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
package pw.ollie.args;

/**
 * A flag which simply has a name and a value.
 */
public class Flag {
    /**
     * The name of the flag.
     */
    private final String name;
    /**
     * The {@link StringWrapper} representing the value of this flag, which
     * provides various methods to use the value.
     */
    private final StringWrapper valArg;

    /**
     * Constructs a new {@link Flag} with the given name and the given value.
     *
     * @param name the name of the flag
     * @param value the value for the flag
     */
    public Flag(String name, StringWrapper value) {
        this.name = name;
        this.valArg = value;
    }

    /**
     * Constructs a new {@link Flag} with the given name and the given value.
     *
     * @param name the name of the flag
     * @param value the value for the flag
     * @deprecated use {@link #Flag(String, StringWrapper)}
     */
    public Flag(String name, String value) {
        this(name, new StringWrapper(value));
    }

    /**
     * Gets the name of this flag - for example, 'f' in '-f trees'.
     *
     * @return this flag's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the {@link StringWrapper} which represents the value provided for
     * this flag.
     *
     * @return this flag's value
     */
    public StringWrapper getValue() {
        return valArg;
    }

    /**
     * Gets the raw {@link String} value which was provided for this flag. For
     * example, 'trees' in '-f trees'.
     *
     * @return this flag's raw {@link String} value
     */
    public String getRawValue() {
        return getValue().get();
    }
}
