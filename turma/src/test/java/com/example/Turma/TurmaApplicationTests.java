package com.example.Turma;

import com.example.Turma.exception.AlunoNoRegisterException;
import com.example.Turma.exception.TurmaNotFoundException;
import com.example.Turma.exception.UserNotFoundException;
import com.example.Turma.service.TurmaAlunoService;
import com.example.Turma.service.ValidacoesService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class TurmaApplicationTests {

	@Autowired
	ValidacoesService validacoesService;

	@Autowired
	TurmaAlunoService turmaAlunoService;

	@Test
	void contextLoads() {
		Assert.assertThrows(UserNotFoundException.class, () -> validacoesService.buscaPessoaSemStatus(10L, "Aluno"));
	}

	@Test
	public void validBuscaPessoa(){
		Assert.assertThrows(UserNotFoundException.class, () -> validacoesService.buscaPessoa(10L, "Aluno"));
	}

	@Test
	public void validBuscaTurmaNumero(){
		Assert.assertThrows(TurmaNotFoundException.class, () -> validacoesService.buscaTurmaPorNumero(4002));
	}

	@Test
	public void validBuscaTurmaId(){
		Assert.assertThrows(TurmaNotFoundException.class, () -> validacoesService.buscaTurma(50L));
	}

	@Test
	public void validBuscaTurmaMatricula(){
		Assert.assertThrows(AlunoNoRegisterException.class, () -> validacoesService.buscaTurmaPorMatricula(50L));
	}

	@Test
	public void validaAlunosTurma(){
		Assert.assertTrue(turmaAlunoService.listarAlunosTurma().getBody().isSuccess());
	}

	@Test
	public void validaAlunosTurmaNome(){
		Assert.assertTrue(turmaAlunoService.listarturmaAlunoPorNome("Felipe").getBody().isSuccess());
	}


}
