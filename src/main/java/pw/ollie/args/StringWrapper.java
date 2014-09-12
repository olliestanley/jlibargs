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
 * A wrapper around a {@link String} which allows for parsing of many primitive
 * data types as well as providing methods to check whether the argument is a
 * valid form of said primitive types.
 */
public class StringWrapper {
    /**
     * The raw string for the argument wrapped by this StringWrapper object.
     */
    private final String raw;

    /**
     * Creates a new StringWrapper, using the given String argument as a raw
     * string.
     *
     * @param arg the raw string for this StringWrapper
     */
    public StringWrapper(String arg) {
        this.raw = arg;
    }

    /**
     * Gets the raw string this StringWrapper wraps.
     *
     * @return this StringWrapper's raw String value
     */
    public String get() {
        return raw;
    }

    /**
     * Returns this StringWrapper's value parsed as an int.
     *
     * @return this StringWrapper's value parsed as an int
     * @throws NumberFormatException if the value isn't an int
     */
    public int asInt() {
        return Integer.parseInt(raw);
    }

    /**
     * Returns this StringWrapper's value parsed as a double.
     *
     * @return this StringWrapper's value parsed as a double
     * @throws NumberFormatException if the value isn't a double
     */
    public double asDouble() {
        return Double.parseDouble(raw);
    }

    /**
     * Returns this StringWrapper's value parsed as a float.
     *
     * @return this StringWrapper's value parsed as a float
     * @throws NumberFormatException if the argument isn't a float
     */
    public float asFloat() {
        return Float.parseFloat(raw);
    }

    /**
     * Returns this StringWrapper's value parsed as a long.
     *
     * @return this StringWrapper's value parsed as a long
     * @throws NumberFormatException if the value isn't a long
     */
    public long asLong() {
        return Long.parseLong(raw);
    }

    /**
     * Returns this StringWrapper's value parsed as a short.
     *
     * @return this StringWrapper's value parsed as a short
     * @throws NumberFormatException if the value isn't a short
     */
    public short asShort() {
        return Short.parseShort(raw);
    }

    /**
     * Returns this StringWrapper's value parsed as a boolean.
     *
     * @return this StringWrapper's value parsed as a boolean. Note that if the
     *         value of the StringWrapper isn't a valid boolean, false is returned
     */
    public Boolean asBoolean() {
        return Boolean.valueOf(raw);
    }

    /**
     * Checks whether this StringWrapper's value can be parsed as an integer.
     *
     * @return whether this StringWrapper's value can be parsed as an integer
     */
    public boolean isInt() {
        try {
            asInt();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks whether this StringWrapper's value can be parsed as a double.
     *
     * @return whether this StringWrapper's value can be parsed as a double
     */
    public boolean isDouble() {
        try {
            asDouble();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks whether this StringWrapper's value can be parsed as a float.
     *
     * @return whether this StringWrapper's value can be parsed as a float
     */
    public boolean isFloat() {
        try {
            asFloat();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks whether this StringWrapper's value can be parsed as a long.
     *
     * @return whether this StringWrapper's value can be parsed as a long
     */
    public boolean isLong() {
        try {
            asLong();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks whether this StringWrapper's value can be parsed as a short.
     *
     * @return whether this StringWrapper's value can be parsed as a short
     */
    public boolean isShort() {
        try {
            asShort();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks whether this StringWrapper's value can be parsed as a boolean.
     *
     * @return whether this StringWrapper's value can be parsed as a boolean
     */
    public boolean isBoolean() {
        return raw.equals("true") || raw.equals("false");
    }
}
