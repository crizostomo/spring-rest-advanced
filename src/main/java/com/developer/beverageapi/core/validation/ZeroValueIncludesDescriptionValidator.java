package com.developer.beverageapi.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ZeroValueIncludesDescriptionValidator implements ConstraintValidator<ZeroValueIncludesDescription, Object> {

    private String fieldValue;
    private String fieldDescription;
    private String mandatoryField;

    @Override
    public void initialize(ZeroValueIncludesDescription constraint) {
        this.fieldValue = constraint.fieldValue();
        this.fieldDescription = constraint.fieldDescription();
        this.mandatoryField = constraint.mandatoryField();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), fieldValue)
                    .getReadMethod().invoke(objetoValidacao);

            String descricao = (String) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), fieldDescription)
                    .getReadMethod().invoke(objetoValidacao);

            if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                valid = descricao.toLowerCase().contains(this.mandatoryField.toLowerCase());
            }

            return valid;

        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}
