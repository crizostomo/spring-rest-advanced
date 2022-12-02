package com.developer.beverageapi.api.model.input;

import com.developer.beverageapi.core.validation.FileContentType;
import com.developer.beverageapi.core.validation.FileSize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductPhotoInput {

    @ApiModelProperty(value = "File of the product photo (max 500KB, only JPG and PNG", required = true)
    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    private MultipartFile file;

    @ApiModelProperty(value = "Product photo description", required = true)
    @NotBlank
    private String description;
}
