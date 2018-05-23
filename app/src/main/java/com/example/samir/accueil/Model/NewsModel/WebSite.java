package com.example.samir.accueil.Model.NewsModel;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class WebSite {
    private String status;
    private List<Source> sources;

    public WebSite() {
        this.sources = new List<Source>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                if(this.size()==0)
                    return true;
                else
                    return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Source> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean add(Source source) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends Source> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends Source> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Source get(int i) {
                return null;
            }

            @Override
            public Source set(int i, Source source) {
                return null;
            }

            @Override
            public void add(int i, Source source) {

            }

            @Override
            public Source remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<Source> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Source> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<Source> subList(int i, int i1) {
                return null;
            }
        };
    }

    public WebSite(String status, List<Source> sources) {
        this.status = status;
        this.sources = sources;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
