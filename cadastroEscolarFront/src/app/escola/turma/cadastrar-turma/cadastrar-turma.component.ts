import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EntradaTurmaAlunoDTO } from 'src/app/models/DTO/entradaAlunoTurmaDTO';
import { PessoaEntradaDTO } from 'src/app/models/DTO/pessoaEntradaDTO';
import { FormularioTurma } from 'src/app/models/formularioTurma';
import { Pessoa } from 'src/app/models/pessoa';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { Turma } from 'src/app/models/turma';
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

  ngOnInit(): void {
    this.formularioVazio();
    this.listarTurmas();
  }

  constructor(
    private fb : FormBuilder,
    private turmaService : TurmaService,
    private _snackBar: MatSnackBar
  ){}

  atualizaAlunos(turma : number){
    this.turmaFiltrada = this.turmas.filter(t => t.id === turma)[0]
    this.turmaService.findAlunoByAno(this.turmaFiltrada.ano).subscribe({
      next : alu => this.listarAlunos(alu),
      error : err => console.log(err)
    })
  }

  listarTurmas(){
    this.turmaService.findAll().subscribe({
      next : tur => this.turmas = tur,
      error : err => console.log(err)
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
  teste(){
    this.turmaService.saveTurma(this.montaTurmaAlunoSaida()).subscribe({
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
