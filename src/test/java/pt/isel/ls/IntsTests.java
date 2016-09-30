package pt.isel.ls;

import org.junit.Test;
import static org.junit.Assert.*;


public class IntsTests {

    @Test
    public void max_returns_greatest(){
        assertEquals(1, Ints.max(1, -2));
        assertEquals(1, Ints.max(-2,1));
        assertEquals(-1, Ints.max(-1,-2));
        assertEquals(-1, Ints.max(-2,-1));
    }

    @Test (expected=IllegalArgumentException.class)
    public void verify_array_boundaries() {
        int[] v = {1,2,3};
        Ints.indexOfBinary(v,-1,2,2);
        Ints.indexOfBinary(v,0,v.length+1,2);
    }

    @Test
    public void verify_valid_full(){
        int[] v = {2,4,6,8,10,12};
        int t0 = Ints.indexOfBinary(v,0,5,2);
        assertTrue(t0 == 0);
        int t1 = Ints.indexOfBinary(v,0,5,4);
        assertTrue(t1 == 1);
        int t2 = Ints.indexOfBinary(v,0,5,6);
        assertTrue(t2 == 2);
        int t3 = Ints.indexOfBinary(v,0,5,8);
        assertTrue(t3 == 3);
        int t4 = Ints.indexOfBinary(v,0,5,10);
        assertTrue(t4 == 4);
        int t5 = Ints.indexOfBinary(v,0,5,12);
        assertTrue(t5 == 5);
    }

    @Test
    public void verify_valid_left(){
        int[] v = {2,4,6,8,10,12};
        int t0 = Ints.indexOfBinary(v,0,3,2);
        assertTrue(t0 == 0);
        int t1 = Ints.indexOfBinary(v,0,3,4);
        assertTrue(t1 == 1);
        int t2 = Ints.indexOfBinary(v,0,3,6);
        assertTrue(t2 == 2);
        int t3 = Ints.indexOfBinary(v,0,3,8);
        assertTrue(t3 == 3);
        int t4 = Ints.indexOfBinary(v,0,3,10);
        assertTrue(t4 < 0);
        int t5 = Ints.indexOfBinary(v,0,3,12);
        assertTrue(t5 < 0);
    }

    @Test
    public void verify_valid_right(){
        int[] v = {2,4,6,8,10,12};
        int t0 = Ints.indexOfBinary(v,3,5,2);
        assertTrue(t0 < 0);
        int t1 = Ints.indexOfBinary(v,3,5,4);
        assertTrue(t1 < 0);
        int t2 = Ints.indexOfBinary(v,3,5,6);
        assertTrue(t2 < 0);
        int t3 = Ints.indexOfBinary(v,3,5,8);
        assertTrue(t3 == 3);
        int t4 = Ints.indexOfBinary(v,3,5,10);
        assertTrue(t4 == 4);
        int t5 = Ints.indexOfBinary(v,3,5,12);
        assertTrue(t5 == 5);
    }

    @Test
    public void verify_valid_middle(){
        int[] v = {2,4,6,8,10,12};
        int t0 = Ints.indexOfBinary(v,1,4,2);
        assertTrue(t0 < 0);
        int t1 = Ints.indexOfBinary(v,1,4,4);
        assertTrue(t1 == 1);
        int t2 = Ints.indexOfBinary(v,1,4,6);
        assertTrue(t2 == 2);
        int t3 = Ints.indexOfBinary(v,1,4,8);
        assertTrue(t3 == 3);
        int t4 = Ints.indexOfBinary(v,1,4,10);
        assertTrue(t4 == 4);
        int t5 = Ints.indexOfBinary(v,1,4,12);
        assertTrue(t5 < 0);
    }

    @Test
    public void indexOfBinary_returns_negative_if_not_found(){
        // Arrange
        int[] v = {1,2,3};

        // Act
        int ix = Ints.indexOfBinary(v, 0, 2, 4);
        //System.out.println(ix);

        // Assert
        assertTrue(ix < 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void indexOfBinary_throws_IllegalArgumentException_if_indexes_are_not_valid(){
        // Arrange
        int[] v = {1,2,3};

        // Act
        int ix = Ints.indexOfBinary(v, 2, 1, 4);
        //System.out.println(ix);

        // Assert
        assertTrue(ix < 0);
    }

    @Test
    public void indexOfBinary_right_bound_parameter_is_exclusive(){
        int[] v = {2,2,2};
        int ix = Ints.indexOfBinary(v, 1, 1, 2);
        //System.out.println(ix);
        assertTrue(ix == 1);
    }
}
