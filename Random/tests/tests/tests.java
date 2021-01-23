package tests;

import Calc.CalcImpl;
import org.junit.Assert;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

public class tests {

    @Test
        public void test1() {
        // create
        CalcImpl calc = new CalcImpl(); //first object
        calc.add(3);
        Assert.assertEquals(3, calc.getValue());
        calc.save(); //speichert zustand in file

        CalcImpl calc1 = new CalcImpl(); //zweites Objekt wird erzeugt
        calc.restore();
        Assert.assertEquals(3, calc.getValue());
    }

}