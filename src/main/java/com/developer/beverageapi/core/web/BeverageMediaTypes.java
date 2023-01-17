package com.developer.beverageapi.core.web;

import org.springframework.http.MediaType;

public class BeverageMediaTypes {

    public static final String V1_APPLICATION_JSON_VALUE = "application/vnd.beveragefood.v1+json";

    public static final MediaType V1_APPLICATION_JSON = MediaType.valueOf(V1_APPLICATION_JSON_VALUE);
}
