import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EntradaTurmaAlunoDTO } from 'src/app/models/DTO/entradaAlunoTurmaDTO';
import { PessoaEntradaDTO } from 'src/app/models/DTO/pessoaEntradaDTO';
import { FormularioTurma } from 'src/app/models/formularioTurma';
import { Pessoa } from 'src/app/models/pessoa';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { ResponseFiltroPessoaNome } from 'src/app/models/Response/responseFiltroPessoaNome';
import { ResponseFiltroTurma } from 'src/app/models/Response/responseFiltroTurma';
import { Turma } from 'src/app/models/turma';
import { TurmaAlunoService } from 'src/app/services/turma-aluno.service';
import { TurmaService } from 'src/app/services/turma.service';

@Component({
  selector: 'app-cadastrar-turma',
  templateUrl: './cadastrar-turma.component.html',
  styleUrls: ['./cadastrar-turma.component.css']
})
export class CadastrarTurmaComponent implements OnInit{

  formulario : FormGroup;
  turmas : Turma[] = [];
  turmaFiltrada : Turma; 
  alunos : Pessoa[];
  entrada : EntradaTurmaAlunoDTO;
  formularioFiltrado : FormularioTurma[];
  pessoa : PessoaEntradaDTO[];
  resposta : DefaultResponse;
  respostaTurma : ResponseFiltroTurma;
  respostaPessoa : ResponseFiltroPessoaNome;

  ngOnInit(): void {
    this.formularioVazio();
    this.listarTurmas();
  }

  constructor(
    private fb : FormBuilder,
    private turmaAlunoService : TurmaAlunoService,
    private turmaService : TurmaService,
    private _snackBar: MatSnackBar
  ){}

  atualizaAlunos(turma : number){
    this.turmaFiltrada = this.turmas.filter(t => t.id === turma)[0]
    this.turmaAlunoService.findAlunoByAno(this.turmaFiltrada.ano).subscribe({
      next : alu => {
        this.resposta = alu;

        if(this.resposta.success){
          this.respostaPessoa = this.resposta.data;
          this.listarAlunos(this.respostaPessoa.pessoaList)
        } else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
      }
    })
  }

  listarTurmas(){
    this.turmaService.findAll().subscribe({
      next : tur => {
        this.resposta = tur;
        if(this.resposta.success){
          this.respostaTurma = this.resposta.data;
          this.turmas = this.respostaTurma.turmaList;
        } else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
      } 
    })
  }

  formularioVazio(){
    this.formulario = this.fb.group({
      turma : [null, [Validators.required]],
      matriculasTurmas : new FormArray([])
    });
  }

  clearFormArray = (formArray: FormArray) => {
    while (formArray.length !== 0) {
      formArray.removeAt(0)
    }
  }

  buildFormAluno(alunos :  Pessoa[]){
    let formArray =  this.formulario.get("matriculasTurmas") as FormArray

    this.clearFormArray(formArray);

    alunos.forEach(v => {
      formArray.push(
        this.fb.group({
          matriculaAluno : [v.matricula],
          nome : [v.nome + " " + v.sobreNome],
          notas : [false, [Validators.required]]
        })
      )
    })
  }

  get matriculasTurmas(){
    return this.formulario.get("matriculasTurmas") as FormArray
  }

  listarAlunos(alunos : Pessoa[]){
    this.alunos = [];
    this.alunos = alunos
    this.buildFormAluno(this.alunos);
  }

  montaTurmaAlunoSaida() : EntradaTurmaAlunoDTO{
    this.formularioFiltrado = [];
    this.pessoa = [];

    this.formularioFiltrado = this.formulario.value.matriculasTurmas;
    this.formularioFiltrado = this.formularioFiltrado.filter(v => v.notas);
    
    this.formularioFiltrado.forEach(v => this.pessoa.push(new PessoaEntradaDTO(v.matriculaAluno)));

    return new EntradaTurmaAlunoDTO(this.formulario.value.turma, this.pessoa);
  }
  cadastrar(){
    this.turmaAlunoService.saveTurma(this.montaTurmaAlunoSaida()).subscribe({
      next : res => {
        this.resposta = res;
        if(this.resposta.success){
          this._snackBar.open(this.resposta.messagem, "", {duration : 5000});
          this.formularioVazio();
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 5000});
        }
      }
    })
  }

}
