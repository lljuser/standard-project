package com.heyi.core.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PageResult<T> implements Iterable<T> {

    private static final long serialVersionUID = 6036524294344406L;

    private long total;
    private int number;
    private int size;

    private List<T> content = new ArrayList();

    public static <T> PageResult<T> build(Iterable<T> page) {
        PageResult<T> result = new PageResult<T>();
        String clsName = page.getClass().getName();

        final String exception = "`page` is not assignable from org.springframework.data.domain.PageImpl";
        final String checkType = "org.springframework.data.domain.PageImpl";

        if (!clsName.equals(checkType)) throw new RuntimeException(exception);

        try {
            Method getNumber = page.getClass().getMethod("getNumber");
            Method getSize = page.getClass().getMethod("getSize");
            Method getTotalElements = page.getClass().getMethod("getTotalElements");
            Method getContent = page.getClass().getMethod("getContent");

            result.number = (int) getNumber.invoke(page);
            result.total = (long) getTotalElements.invoke(page);
            result.size = (int) getSize.invoke(page);
            result.content = (List<T>) getContent.invoke(page);

        } catch (Exception e) {
            throw new RuntimeException(exception);
        }

        return result;
    }

    private PageResult() {
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    public long getTotal() {
        return total;
    }

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return size;
    }

    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }

    public boolean hasPrevious() {
        return this.getNumber() > 0;
    }

    public List<T> getContent() {
        return content;
    }

//    public boolean isFirst() {
//        return !this.hasPrevious();
//    }
//
//    public boolean isLast() {
//        return !this.hasNext();
//    }

    private boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }
}
