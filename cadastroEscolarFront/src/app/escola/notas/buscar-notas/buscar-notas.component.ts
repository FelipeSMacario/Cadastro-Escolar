import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Materia } from 'src/app/models/materia';
import { Notas } from 'src/app/models/notas';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { ResponseFiltroTurma } from 'src/app/models/Response/responseFiltroTurma';
import { ResponseMaterias } from 'src/app/models/Response/responseMaterias';
import { RespostaNotas } from 'src/app/models/Response/respostaNotas';
import { Turma } from 'src/app/models/turma';
import { MateriasService } from 'src/app/services/materias.service';
import { NotasService } from 'src/app/services/notas.service';
import { TurmaService } from 'src/app/services/turma.service';

@Component({
  selector: 'app-buscar-notas',
  templateUrl: './buscar-notas.component.html',
  styleUrls: ['./buscar-notas.component.css']
})
export class BuscarNotasComponent implements OnInit{

  formulario : FormGroup;
  materias : Materia[] = [];
  turmas : Turma[] = [];
  notas : Notas[];
  resposta : DefaultResponse;
  respostaTurma : ResponseFiltroTurma;
  respostaMateria : ResponseMaterias;
  respostaNotas : RespostaNotas;

  constructor(
    private fb : FormBuilder,
    private notasService : NotasService,
    private materiaService : MateriasService,
    private turmaService : TurmaService,
    private router: Router,
    private _snackBar: MatSnackBar,
  ){}
  ngOnInit(): void {
    this. formularioVazio();
    this.listarTurmas();
    this. listarMateria();
  }

  formularioVazio(){
    this.formulario = this.fb.group({
      materia : [null, [Validators.required]],
      turma : [null, [Validators.required]],
      nome : [null]
    });
  }

  listarTurmas(){
    this.turmaService.findAll().subscribe({
      next : tur => {
        this.resposta = tur;
        if(this.resposta.success){
          this.respostaTurma = this.resposta.data;
          this.turmas = this.respostaTurma.turmaList;
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
      } 
    })
  }

  listarMateria(){
    this.materiaService.listarMateria().subscribe({
      next : mat => {
        this.resposta = mat;
        if(this.resposta.success){
          this.respostaMateria = this.resposta.data;
          this.materias = this.respostaMateria.materias
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }
      }
    })
  }

  buscarNotas(){
    this.notasService.findByTurmaAndNotas(this.formulario.value.turma, this.formulario.value.materia).subscribe({
      next : not => {
        this.resposta = not;
        if(this.resposta.success){
          this.notas = [];
          this.respostaNotas =  this.resposta.data;
          this.notas = this.respostaNotas.notas;
        this.formulario.value.nome != undefined ? this.notas =  this.notas.filter(v => v.aluno.nome == this.formulario.value.nome) :  this.notas =  this.notas;
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }

      }
    })   
  }

  atualizar(id : number){
    this.router.navigate(['notas/atualizar', id])
  }

}
