package com.example.Dias;

import com.example.Dias.exception.MateriaNotFoundException;
import com.example.Dias.exception.UserNotFoundException;
import com.example.Dias.service.DiaService;
import com.example.Dias.service.HorasService;
import com.example.Dias.service.MateriaService;
import com.example.Dias.service.ValidacoesService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class DiasApplicationTests {

	@Autowired
	DiaService diaService;

	@Autowired
	HorasService horasService;

	@Autowired
	MateriaService materiaService;

	@Autowired
	ValidacoesService validacoesService;

	@Test
	public void validaDias(){
		Assert.assertTrue(diaService.listarDias().getBody().isSuccess());
	}

	@Test
	public void validaHoras(){
		Assert.assertTrue(horasService.listarHoras().getBody().isSuccess());
	}

	@Test
	public void validaListarMaterias(){
		Assert.assertTrue(materiaService.listarMateriasSemPaginacao().getBody().isSuccess());
	}

	@Test
	public void validaMateriaId(){
		Assert.assertThrows(MateriaNotFoundException.class, () -> validacoesService.buscaMateriaPorId(45L));
	}

	@Test
	public void validaMateriaNome(){
		Assert.assertThrows(MateriaNotFoundException.class, () -> validacoesService.buscaMateriaPorNome("Poções"));
	}

	@Test
	public void validBuscaPessoa(){
		Assert.assertThrows(UserNotFoundException.class, () -> validacoesService.buscaPessoa(10L, "Aluno"));
	}

}
