package com.tristanruecker.interviewexampleproject.config.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ErrorFieldList extends ArrayList<ErrorField> {

    @Override
    public boolean add(ErrorField e) {
        if (isElementAlreadyInList(e)) {
            return false;
        }
        return super.add(e);
    }

    @Override
    public void add(int index, ErrorField element) {
        super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends ErrorField> c) {
        Collection<? extends ErrorField> f = c.stream().filter(ccc -> !isElementAlreadyInList(ccc)).collect(Collectors.toList());
        return super.addAll(f);
    }

    @Override
    public boolean addAll(int index, Collection<? extends ErrorField> c) {
        Collection<? extends ErrorField> f = c.stream().filter(ccc -> !isElementAlreadyInList(ccc)).collect(Collectors.toList());
        return super.addAll(index, f);
    }

    private boolean isElementAlreadyInList(ErrorField element) {
        return stream().anyMatch(list -> list.getField().equals(element.getField()));
    }

}
