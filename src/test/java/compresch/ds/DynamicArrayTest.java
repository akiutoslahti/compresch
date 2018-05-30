/*
 * MIT License
 *
 * Copyright (c) 2018 Aki Utoslahti
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package compresch.ds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class DynamicArrayTest {

    @Test
    public void constructDynamicArrayTest() {
        DynamicArray arr = new DynamicArray();
        assertNotNull(arr);
    }

    @Test
    public void pushRandomTest() {
        Random rng = new Random();
        List<Integer> expected = new ArrayList<>();
        DynamicArray<Integer> actual = new DynamicArray<>();
        for (int i = 0; i < 997; i++) {
            int curr = rng.nextInt();
            expected.add(curr);
            actual.push(curr);
        }
        assertEquals(contents(expected), actual.contents());
        assertEquals(contents(expected), contents(actual));
    }

    @Test
    public void atNullPointerTest() {
        Random rng = new Random();
        DynamicArray<Integer> actual = new DynamicArray<>();
        for (int i = 0; i < 7; i++) {
            int curr = rng.nextInt();
            actual.push(curr);
        }
        try {
            actual.at(-1);
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        }
        try {
            actual.at(8);
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        }
    }

    private String contents(DynamicArray arr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.size(); i++) {
            builder.append(arr.at(i));
            builder.append(" ");
        }
        return builder.toString().substring(0, builder.length() - 1);
    }

    private String contents(List<Integer> arr) {
        StringBuilder builder = new StringBuilder();
        for (Integer i : arr) {
            builder.append(i);
            builder.append(" ");
        }
        return builder.toString().substring(0, builder.length() - 1);
    }

}