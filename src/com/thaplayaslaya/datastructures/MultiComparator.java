package com.thaplayaslaya.datastructures;

import java.util.Comparator;
import java.util.List;

public class MultiComparator<T> implements Comparator<T> {
	private List<Comparator<T>> comparators;

    public MultiComparator(List<Comparator<T>> comparators) {
        this.comparators = comparators;
    }
    
    @Override
    public int compare(T o1, T o2) {
        for (Comparator<T> comparator : comparators) {
            int comparison = comparator.compare(o1, o2);
            if (comparison != 0) return comparison;
        }
        return 0;
    }
}
