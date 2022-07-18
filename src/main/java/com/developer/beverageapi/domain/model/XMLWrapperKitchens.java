package com.developer.beverageapi.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@JacksonXmlRootElement(localName = "kitchens")
@Data
public class XMLWrapperKitchens {

    @JsonProperty("kitchen")
    @JacksonXmlElementWrapper(useWrapping = false)
    @NonNull //It generates the constructor method
    private List<Kitchen> kitchens;
}
