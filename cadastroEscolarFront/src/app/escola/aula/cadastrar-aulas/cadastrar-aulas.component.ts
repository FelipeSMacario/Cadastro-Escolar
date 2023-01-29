import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Dia } from 'src/app/models/dia';
import { Horas } from 'src/app/models/horas';
import { Materia } from 'src/app/models/materia';
import { QuadroHorario } from 'src/app/models/quadroHorario';
import { Sala } from 'src/app/models/sala';
import { Turma } from 'src/app/models/turma';
import { DiaService } from 'src/app/services/dia.service';
import { MateriasService } from 'src/app/services/materias.service';
import { QuadroHorariosService } from 'src/app/services/quadro-horarios.service';
import { SalaService } from 'src/app/services/sala.service';
import { TurmaService } from 'src/app/services/turma.service';

@Component({
  selector: 'app-cadastrar-aulas',
  templateUrl: './cadastrar-aulas.component.html',
  styleUrls: ['./cadastrar-aulas.component.css']
})
export class CadastrarAulasComponent implements OnInit{

  formulario : FormGroup;
  turma : Turma[] = [];
  sala : Sala[] = [];
  materias : Materia[] = [];
  dias : Dia[] = [];
  horas : Horas[] = [];
  quadroHorario : QuadroHorario = new QuadroHorario();

  ngOnInit(): void {
    this.formulario = this.fb.group({
      turma : [0],
      sala :[0],
      materia : [0],
      dia : [0],
    });
    this.listarTurma();
    this.listarSalas();
    this.listarMaterias();
    this.listarDias();
  }
  
  constructor(
    private quadroHorarioService : QuadroHorariosService,
    private turmaService : TurmaService,
    private salaService : SalaService,
    private materiaService : MateriasService,
    private diaService : DiaService,
    private fb : FormBuilder,
  ){}


  listarTurma(){
    this.turmaService.findAll().subscribe({
      next : tur => {
        this.turma = tur;
      }, error : err => console.log(err)
    });
  }

  
  listarSalas(){
    this.salaService.findAll().subscribe({
      next : sal => {
        this.sala = sal;
      }, error : err => console.log(err)
    });
  }

  listarMaterias(){
    this.materiaService.listarMateria().subscribe({
      next : mat => {
        this.materias = mat;
      }, error : err => console.log(err)
    });
  }
  listarDias(){
    this.diaService.findAll().subscribe({
      next : dia => {
        this.dias = dia;
      }, error : err => console.log(err)
    });
  }

  teste(){
    this.quadroHorarioService.findHorasLivres(this.formulario.controls["dia"].value, this.formulario.controls["sala"].value).subscribe({
      next : hor => this.horas = hor,
      error : err => console.log(err)
    })
  }


}
