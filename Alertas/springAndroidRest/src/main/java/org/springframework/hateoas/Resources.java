package org.springframework.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * General helper to easily create a wrapper for a collection of entities.
 *
 * @author Oliver Gierke
 */
public class Resources<T> extends ResourceSupport implements Iterable<T> {

    private final Collection<T> content;

    /**
     * Creates an empty {@link Resources} instance.
     */
    protected Resources() {
        this(new ArrayList<T>());
    }

    /**
     * Creates a {@link Resources} instance with the given content and {@link Link}s (optional).
     *
     * @param content must not be {@literal null}.
     * @param links the links to be added to the {@link Resources}.
     */
    public Resources(Iterable<T> content, Link... links) {
        this(content, Arrays.asList(links));
    }

    /**
     * Creates a {@link Resources} instance with the given content and {@link Link}s.
     *
     * @param content must not be {@literal null}.
     * @param links the links to be added to the {@link Resources}.
     */
    public Resources(Iterable<T> content, Iterable<Link> links) {

        Assert.notNull(content);

        this.content = new ArrayList<T>();

        for (T element : content) {
            this.content.add(element);
        }
        this.add(links);
    }

    /**
     * Creates a new {@link Resources} instance by wrapping the given domain class instances into a {@link Resource}.
     *
     * @param content must not be {@literal null}.
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Resource<S>, S> Resources<T> wrap(Iterable<S> content) {

        Assert.notNull(content);
        ArrayList<T> resources = new ArrayList<T>();

        for (S element : content) {
            resources.add((T) new Resource<S>(element));
        }

        return new Resources<T>(resources);
    }

    /**
     * Returns the underlying elements.
     *
     * @return the content will never be {@literal null}.
     */
    @JsonProperty("content")
    public Collection<T> getContent() {
        return Collections.unmodifiableCollection(content);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.ResourceSupport#toString()
     */
    @Override
    public String toString() {
        return String.format("Resources { content: %s, %s }", getContent(), super.toString());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.ResourceSupport#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }

        Resources<?> that = (Resources<?>) obj;

        boolean contentEqual = this.content == null ? that.content == null : this.content.equals(that.content);
        return contentEqual ? super.equals(obj) : false;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.ResourceSupport#hashCode()
     */
    @Override
    public int hashCode() {

        int result = super.hashCode();
        result += content == null ? 0 : 17 * content.hashCode();

        return result;
    }
}