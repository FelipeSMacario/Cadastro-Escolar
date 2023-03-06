import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Materia } from 'src/app/models/materia';
import { Notas } from 'src/app/models/notas';
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

  constructor(
    private fb : FormBuilder,
    private notasService : NotasService,
    private turmaSerivce : TurmaService,
    private materiaService : MateriasService,
    private router: Router
  ){}
  ngOnInit(): void {
    this. formularioVazio();
    this.listarTurmas();
    this. listarMateria();
  }

  formularioVazio(){
    this.formulario = this.fb.group({
      materia : [null],
      turma : [null],
      nome : [null]
    });
  }

  listarTurmas(){
    this.turmaSerivce.findAll().subscribe({
      next : tur => this.turmas = tur,
      error : err => console.log(err)
    })
  }

  listarMateria(){
    this.materiaService.listarMateria().subscribe({
      next : mat => this.materias = mat,
      error : err => console.log(err)
    })
  }

  buscarNotas(){
    this.notasService.findByTurmaAndNotas(this.formulario.value.turma, this.formulario.value.materia).subscribe({
      next : not => {
        this.notas = [];
        this.formulario.value.nome != undefined ? this.notas = not.filter(v => v.aluno.nome == this.formulario.value.nome) :  this.notas = not;
      },
      error : err => console.log(err)
    })   
  }

  atualizar(id : number){
    this.router.navigate(['notas/atualizar', id])
  }

}
