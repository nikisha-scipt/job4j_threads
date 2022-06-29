package ru.job4j.concurrent.pools;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SearchValueTest {

    private SearchValue<Integer> searchValue;
    private List<Integer> largeList;
    private List<Integer> littleList;

    @Before
    public void init() {
        searchValue = new SearchValue<>();
        largeList = new ArrayList<>();
        littleList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            largeList.add(i);
        }
        for (int i = 0; i < 9; i++) {
            littleList.add(i);
        }
    }

    @Test
    public void whenSearchValue3() {
        assertThat(3, is(searchValue.search(largeList, 3)));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenSearchValue3AndException() {
        searchValue.search(largeList, 25);
    }

}