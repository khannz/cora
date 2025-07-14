package com.epam.cora.rest;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PagedResult<T> { // TODO: refactor to extend Result class
    private T elements; // TODO; refactor to contain a list of T
    private int totalCount;

    public PagedResult(T elements, int totalCount) {
        this.elements = elements;
        this.totalCount = totalCount;
    }

    public T getElements() {
        return elements;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
