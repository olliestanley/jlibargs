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
import org.junit.Assert;
import org.junit.Test;

import pw.ollie.args.Arguments;
import pw.ollie.args.params.Params;
import pw.ollie.args.params.ParamsBase;

public class TestMain {
    @Test
    public void runTest() {
        // Test number one

        // Test the Arguments class
        Arguments args = new Arguments(
                "subcommand", "-f", "value", "off", "on");

        Assert.assertEquals("ARG: PARSE", args.getRaw(0), "subcommand");
        Assert.assertEquals("ARG: PARSE", args.getRaw(0), args.get(0).get());
        Assert.assertEquals("ARG: PARSE", args.getRaw(1, false), "off");
        Assert.assertEquals("ARG: PARSE", args.getRaw(2, false), "on");
        Assert.assertEquals("ARG: FLAG", args.getValueFlag("f").getRawValue(), "value");

        // Test the ParamsBase class
        ParamsBase paramsBase = ParamsBase.fromUsageString(
                "/command subcommand <-f lol> <option1> [optional]");
        Assert.assertEquals("PB: Length", paramsBase.length(), 2);
        // Each required flag counts as two required, hence 3 not 2
        Assert.assertEquals("PB: Req", paramsBase.getAmountRequired(), 1);
        Assert.assertEquals("PB: Opt", paramsBase.getAmountOptional(), 1);
        Assert.assertEquals("PB: B4", 1, paramsBase.getArgsBeforeParams());
        Assert.assertEquals("PB: FLA", 1, paramsBase.getAmountFlags());

        // Test the Params class
        Params params = args.withParams(paramsBase.createParams(args))
                .getParams();
        Assert.assertEquals("PAR: LKUP", params.get("option1").get(), "off");
        Assert.assertEquals("PAR: LKUP", params.get("optional").get(), "on");
        Assert.assertTrue("PAR: INV", params.valid());

        // Test number two

        // Test the Arguments class
        Arguments args1 = new Arguments("subcommand", "off", "on");

        Assert.assertEquals("ARG1: PARSE", args1.getRaw(0), "subcommand");
        Assert.assertEquals("ARG1: PARSE", args1.getRaw(0), args.get(0).get());
        Assert.assertEquals("ARG1: PARSE", args1.getRaw(1, false), "off");
        Assert.assertEquals("ARG1: PARSE", args1.getRaw(2, false), "on");

        // Test the ParamsBase class
        ParamsBase paramsBase1 = ParamsBase.fromUsageString(
                "/command subcommand <-f lol> <option1> [optional]");
        Assert.assertEquals("PB: Length", paramsBase1.length(), 2);
        Assert.assertEquals("PB1: Req", paramsBase1.getAmountRequired(), 1);
        Assert.assertEquals("PB1: Opt", paramsBase1.getAmountOptional(), 1);
        Assert.assertEquals("PB1: B4", 1, paramsBase1.getArgsBeforeParams());
        Assert.assertEquals("PB1: FLA", 1, paramsBase1.getAmountFlags());

        // Test the Params class
        Params params1 = args1.withParams(paramsBase1.createParams(args1))
                .getParams();
        Assert.assertEquals("PAR1: LKUP", params1.get("option1").get(), "off");
        Assert.assertEquals("PAR1: LKUP", params1.get("optional").get(), "on");
        Assert.assertTrue("PAR1: INV", !params1.valid());
    }
}
