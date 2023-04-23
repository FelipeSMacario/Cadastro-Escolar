package com.example.aulas;

import com.example.aulas.exception.*;
import com.example.aulas.service.AulaService;
import com.example.aulas.service.ValidacoesService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class AulasApplicationTests {

	@Autowired
	ValidacoesService validacoesService;

	@Autowired
	AulaService aulaService;

	@Test
	public void validBuscaPessoa(){
		Assert.assertThrows(UserNotFoundException.class, () -> validacoesService.buscaPessoaSemStatus(10L, "Aluno"));
	}

	@Test
	public void validaNotasId(){
		Assert.assertThrows(NotasNotFoundException.class, () -> validacoesService.buscaNotas(100L));
	}

	@Test
	public void validaAulasId(){
		Assert.assertThrows(QuadroHorarioNotFound.class, () -> validacoesService.buscaQuadroHorario(100L));
	}

	@Test
	public void validBuscaTurmaId(){
		Assert.assertThrows(TurmaNotFoundException.class, () -> validacoesService.buscaTurma(50L));
	}

	@Test
	public void validaMateriaId(){
		Assert.assertThrows(MateriaNotFoundException.class, () -> validacoesService.buscaMateriaPorId(45L));
	}

	@Test
	public void validaListarSemPaginacao(){
		Assert.assertTrue(aulaService.buscaHorarioPorId( 1L).getBody().isSuccess());
	}

	@Test
	public void validaMaterias(){
		Assert.assertTrue(aulaService.filtrarMaterias( 1L, 1L).getBody().isSuccess());
	}


}
