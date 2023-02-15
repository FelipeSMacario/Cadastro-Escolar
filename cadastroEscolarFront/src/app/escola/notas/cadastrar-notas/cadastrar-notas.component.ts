import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatriculaNotasDTO } from 'src/app/models/DTO/matriculaNotaDTO';
import { NotasDTO } from 'src/app/models/DTO/notasDTO';
import { Materia } from 'src/app/models/materia';
import { Pessoa } from 'src/app/models/pessoa';
import { Turma } from 'src/app/models/turma';
import { MateriasService } from 'src/app/services/materias.service';
import { NotasService } from 'src/app/services/notas.service';
import { PessoaService } from 'src/app/services/pessoa.service';
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

  constructor(
    private fb : FormBuilder,
    private materiaService : MateriasService,
    private turmaService : TurmaService,
    private notasService : NotasService,
    private _snackBar: MatSnackBar
  ){}

  ngOnInit(): void {
      this.formularioVazio();
      this.listarMaterias();
      this.listaTurmas();
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
    this.materiaService.listarMateria().subscribe({
      next : mat => {
        this.materias = mat;
      },
      error : err => console.log(err)
    })
  }

  buscaProfessor(materia : number){
    this.formulario.controls['professor'].setValue(this.buscaMateria(materia).professor.nome);
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


  buscaAlunos(id : number){
    console.log(id)
    
    this.turmaService.findByTurma(id).subscribe({
      next : alu => {
        this.alunos = alu.alunos;
        this.buildFormAluno(this.alunos);
      },
      error : err => console.log(err)
    })
  }

  listaTurmas(){
    this.turmaService.findAll().subscribe({
      next : tur => {
        this.turmas = tur
      },
      error : err => console.log(err)
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

  
}
