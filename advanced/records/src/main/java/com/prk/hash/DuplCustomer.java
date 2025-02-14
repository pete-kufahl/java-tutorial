package com.prk.hash;

public record DuplCustomer(long id, String name, String email) {

    // rule:
    // - when two objects are considered equal, their hashcodes must be the same

    // make equality conditional on the id field only
    @Override
    public boolean equals(Object o) {
        return o instanceof DuplCustomer other && this.id == other.id;
    }

    // make hashCode use the id field only
    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
