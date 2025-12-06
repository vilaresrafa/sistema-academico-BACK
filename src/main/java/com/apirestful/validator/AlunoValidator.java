package com.apirestful.validator;

import com.apirestful.model.Aluno;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AlunoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Aluno.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Aluno aluno = (Aluno) target;

        if (aluno.getNome() == null || aluno.getNome().isBlank()) {
            errors.rejectValue("nome", "nome.vazio", "O nome do aluno é obrigatório.");
        }

        if (aluno.getEmail() == null || aluno.getEmail().isBlank()) {
            errors.rejectValue("email", "email.vazio", "O email do aluno é obrigatório.");
        }

        if (aluno.getSlug() == null || aluno.getSlug().isBlank()) {
            errors.rejectValue("slug", "slug.vazio", "O slug do aluno é obrigatório.");
        }

        if (aluno.getTurma() == null) {
            errors.rejectValue("turma", "turma.vazia", "A turma do aluno é obrigatória.");
        }
    }
}
