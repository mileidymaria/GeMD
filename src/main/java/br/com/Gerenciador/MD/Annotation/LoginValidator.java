package br.com.Gerenciador.MD.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginValidator implements ConstraintValidator<LoginValidation, String> {

	//verifica se a expressão contem números e letras, requisito essencial para as expressões do login e da matrícula serem válidas
	
    @Override
    public boolean isValid (String value, ConstraintValidatorContext context) {
        if(value==null) return false;
        if(value.contains(" ")) return false;
        return value.matches("[a-zA-Z0-9]+");
    }		
}
