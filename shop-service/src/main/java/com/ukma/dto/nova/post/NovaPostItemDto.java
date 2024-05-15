package com.ukma.dto.nova.post;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class NovaPostItemDto {

    @JsonIgnore
    String Ref;

    @JsonIgnore
    String Description;

    @JsonGetter("id")
    public String getId() {
        return Ref;
    }

    @JsonGetter("name")
    public String getName() {
        return Description;
    }

    @JsonSetter("Description")
    public void setName(String name) {
        this.Description = name;
    }

    @JsonSetter("Ref")
    public void setRef(String id) {
        this.Ref = id;
    }
}
