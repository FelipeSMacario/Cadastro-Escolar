package com.example.pessoa;

import com.example.pessoa.controller.AlunoController;
import com.example.pessoa.dto.EntradaDTO;
import com.example.pessoa.exceptions.FieldDuplicateException;
import com.example.pessoa.exceptions.UserNotFoundException;
import com.example.pessoa.service.PessoaService;
import com.example.pessoa.service.ValidacoesService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
class PessoaApplicationTests {
    @Autowired
    ValidacoesService validacoesService;
    @Autowired
    PessoaService pessoaService;
    @Autowired
    AlunoController alunoController;
    @Test
    public void validaCPFDuplicado(){
        Assert.assertThrows(FieldDuplicateException.class, () -> validacoesService.buscaPessoaCpf("72282318056"));
    }

    @Test
    public void validEmailDuplicado(){
        Assert.assertThrows(FieldDuplicateException.class, () -> validacoesService.buscaEmail("charlesXavier@email.com"));
    }

    @Test
    public void validBuscaPessoa(){
        Assert.assertThrows(UserNotFoundException.class, () -> validacoesService.buscaPessoaSemCargo(10L));
    }

    @Test
    public void validBuscaPessoaComCargo(){
        Assert.assertThrows(UserNotFoundException.class, () -> validacoesService.buscaPessoa(10L, "Aluno"));
    }

    @Test
    public void validaBuscarService(){
        Assert.assertTrue(pessoaService.buscar(2L, "Professor").getBody().isSuccess());
    }

    @Test
    public void validaListarSemPaginacao(){
        Assert.assertTrue(pessoaService.listarSemPaginacao( "Professor").getBody().isSuccess());
    }

    @Test
    public void validaEntradasInvalidas(){
        Assert.assertThrows(UserNotFoundException.class, () -> alunoController.atualizarAluno(new EntradaDTO(null, "21452367898", null, 1, "sobrenome", "email", LocalDate.now(), null, "Ativo" )));
    }

}
