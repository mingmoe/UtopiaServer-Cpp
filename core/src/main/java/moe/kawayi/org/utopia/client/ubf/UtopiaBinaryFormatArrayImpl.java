//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The UtopiaBinaryFormatArrayImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.client.ubf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * {@link UtopiaBinaryFormatArray}的线程不安全实现
 */
public class UtopiaBinaryFormatArrayImpl implements UtopiaBinaryFormatArray {

    private final ArrayList<UtopiaBinaryFormatValue> array = new ArrayList<>();

    @Override
    public int getLength() {
        return array.size();
    }

    @Override
    public UtopiaBinaryFormatValue get(int index) {
        return array.get(index);
    }

    @Override
    public void set(int index, UtopiaBinaryFormatValue value) {
        Objects.requireNonNull(value, "value must not be null");

        array.set(index, value);
    }

    @Override
    public void remove(int index) {
        array.remove(index);
    }

    @Override
    public void add(UtopiaBinaryFormatValue value) {
        Objects.requireNonNull(value, "value must not be null");

        array.add(value);
    }

    @Override
    public UtopiaBinaryFormatValue[] asArray() {
        var array = new UtopiaBinaryFormatValue[this.array.size()];

        this.array.toArray(array);

        return array;
    }

    @Override
    public Iterator<UtopiaBinaryFormatValue> iterator() {
        return array.iterator();
    }
}
