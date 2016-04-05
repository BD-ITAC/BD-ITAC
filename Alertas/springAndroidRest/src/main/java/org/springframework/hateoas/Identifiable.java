package org.springframework.hateoas;

import java.io.Serializable;

/**
 * Interface to mark objects that are identifiable by an ID of any type.
 *
 * @author Oliver Gierke
 */
public interface Identifiable<ID extends Serializable> {

    /**
     * Returns the id identifying the object.
     *
     * @return the identifier or {@literal null} if not available.
     */
    ID getId();
}
