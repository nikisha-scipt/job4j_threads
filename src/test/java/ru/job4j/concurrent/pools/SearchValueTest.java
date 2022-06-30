package ru.job4j.concurrent.pools;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SearchValueTest {

    private final Integer[] largeList = new Integer[20];
    private final Integer[] littleList = new Integer[9];

    @Before
    public void init() {
        for (int i = 0; i < largeList.length; i++) {
            largeList[i] = i;
        }
        for (int i = 0; i < littleList.length; i++) {
            littleList[i] = i;
        }
    }

    @Test
    public void whenSearchValue3() {
        assertThat(3, is(SearchValue.search(largeList, 3)));
    }

    @Test()
    public void whenSearchValue3AndException() {
        int res = SearchValue.search(largeList, 25);
        int expected = -1;
        assertThat(expected, is(res));
    }

    @Test
    public void whenSearchValue2InLittleArray() {
        assertThat(2, is(SearchValue.search(littleList, 2)));
    }

    @Test()
    public void whenSearchValue3AndExceptionInLittleArray() {
        int res = SearchValue.search(littleList, 25);
        int expected = -1;
        assertThat(expected, is(res));
    }

}