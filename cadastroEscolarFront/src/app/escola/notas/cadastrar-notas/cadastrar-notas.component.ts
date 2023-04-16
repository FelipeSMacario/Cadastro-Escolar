import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatriculaNotasDTO } from 'src/app/models/DTO/matriculaNotaDTO';
import { NotasDTO } from 'src/app/models/DTO/notasDTO';
import { Materia } from 'src/app/models/materia';
import { Notas } from 'src/app/models/notas';
import { Pessoa } from 'src/app/models/pessoa';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { ResponseFiltroPessoaNome } from 'src/app/models/Response/responseFiltroPessoaNome';
import { ResponseFiltroTurma } from 'src/app/models/Response/responseFiltroTurma';
import { ResponseMaterias } from 'src/app/models/Response/responseMaterias';
import { RespostaNotas } from 'src/app/models/Response/respostaNotas';
import { Turma } from 'src/app/models/turma';
import { MateriasService } from 'src/app/services/materias.service';
import { NotasService } from 'src/app/services/notas.service';
import { TurmaAlunoService } from 'src/app/services/turma-aluno.service';
import { TurmaService } from 'src/app/services/turma.service';

@Component({
  selector: 'app-cadastrar-notas',
  templateUrl: './cadastrar-notas.component.html',
  styleUrls: ['./cadastrar-notas.component.css']
})
export class CadastrarNotasComponent implements OnInit{
  formulario : FormGroup;
  materias : Materia[] = [];
  materiaFiltrada : Materia[] = [];
  alunos : Pessoa[];
  formularioArray : FormArray;
  notas : MatriculaNotasDTO[] = [];
  turmas : Turma[] = [];
  notasAlunos : NotasDTO = new NotasDTO();
  resposta : DefaultResponse;
  respostaMateria : ResponseMaterias;
  respostaTurma : ResponseFiltroTurma;
  respostaALunos : ResponseFiltroPessoaNome;
  respostaNotas : RespostaNotas;
  notasFiltrada : Notas[];
  pessoa : Pessoa;
  alunosFIltrados : Pessoa[];

  constructor(
    private fb : FormBuilder,
    private materiaService : MateriasService,
    private turmaService : TurmaAlunoService,
    private turmaServico : TurmaService,
    private notasService : NotasService,
    private _snackBar: MatSnackBar
  ){}

  ngOnInit(): void {
      this.formularioVazio();
      this.listaTurmas();
      this. listarMaterias();
  }

  formularioVazio(){
    this.formulario = this.fb.group({
      materia : [null, [Validators.required]],
      professor : [null, [Validators.required]],
      turma : [null, [Validators.required]],
      matriculasNotas : new FormArray([])
    });
  }

  listarMaterias(){
    this.materiaService.listarMateriaSemPaginacao().subscribe({
      next : mat => {
        this.resposta = mat;
        if(this.resposta.success){
          this.materias = this.resposta.data;
          this.filtrarMateriasProfessor();
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
      }
    })
  }

  buscaProfessor(materia : number){
    let materiaFiltro = this.buscaMateria(materia);
    this.formulario.controls['professor'].setValue(materiaFiltro.professor.nome + ' ' + materiaFiltro.professor.sobreNome);
  }

  buscaMateria(materia : number) : Materia{
    let materiaFiltrada = this.materiaFiltrada = this.materias.filter(v => v.id === materia);
    return materiaFiltrada[0];
  }


  clearFormArray = (formArray: FormArray) => {
    while (formArray.length !== 0) {
      formArray.removeAt(0)
    }
  }

  buildFormAluno(alunos :  Pessoa[]){
    let formArray =  this.formulario.get("matriculasNotas") as FormArray

    this.clearFormArray(formArray);

    alunos.forEach(v => {
      formArray.push(
        this.fb.group({
          matriculaAluno : [v.matricula],
          nome : [v.nome + " " + v.sobreNome],
          notas : [0, [Validators.required]]
        })
      )
    })
  }

  get matriculasNotas(){
    return this.formulario.get("matriculasNotas") as FormArray
  }


  buscaAlunos(idTurma : number, idMateria : number){   
    if(idTurma  && idMateria){ 
      this.notasService.filtraAlunos(idTurma, idMateria).subscribe({
        next : alu => {   
          this.resposta = alu;
          if(this.resposta.success){
            this.alunos = [];
            this.respostaALunos = this.resposta.data;
            this.alunos = this.respostaALunos.pessoaList;
            this.buildFormAluno(this.alunos);            

          }else {
            this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
          }         
        }
      });
    }    
  }


  listaTurmas(){
    this.turmaServico.findAll().subscribe({
      next : tur => {
        this.resposta = tur;

        if (this.resposta.success){
          this.turmas = this.resposta.data;
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
      }
    })
  }

  salvarNotas(){
    let materiaFiltrada = this.buscaMateria(this.formulario.value.materia);
    this.notasAlunos.matriculaProfessor = materiaFiltrada.professor.matricula;
    this.notasAlunos.materia = materiaFiltrada.nome;
    this.notasAlunos.turmaId = this.formulario.value.turma;
    this.notasAlunos.matriculasNotas = this.formulario.value.matriculasNotas;


   this.notasService.salvarNotas(this.notasAlunos).subscribe({
    next : not => {
      this._snackBar.open("Notas cadastradas com sucesso!", "", {duration : 5000});
      this.formularioVazio();
    },
    error : err => this._snackBar.open(err, "", {duration : 5000})
   }) 
  }

  filtrarMateriasProfessor(){
    this.pessoa = JSON.parse(localStorage.getItem("pessoa")!);
    if(this.pessoa.cargo === "Professor"){
      this.materias = this.materias.filter(v => v.professor.matricula == this.pessoa.matricula);
      console.log(this.materias)
    } 
  }

  
}
