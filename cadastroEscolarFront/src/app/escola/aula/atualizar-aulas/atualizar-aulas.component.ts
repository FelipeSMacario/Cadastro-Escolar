import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Dia } from 'src/app/models/dia';
import { QuadroDTO } from 'src/app/models/DTO/quadroDTO';
import { Horas } from 'src/app/models/horas';
import { Materia } from 'src/app/models/materia';
import { QuadroHorario } from 'src/app/models/quadroHorario';
import { Sala } from 'src/app/models/sala';
import { Turma } from 'src/app/models/turma';
import { DiaService } from 'src/app/services/dia.service';
import { HorasService } from 'src/app/services/horas.service';
import { MateriasService } from 'src/app/services/materias.service';
import { QuadroHorariosService } from 'src/app/services/quadro-horarios.service';
import { SalaService } from 'src/app/services/sala.service';
import { TurmaService } from 'src/app/services/turma.service';

@Component({
  selector: 'app-atualizar-aulas',
  templateUrl: './atualizar-aulas.component.html',
  styleUrls: ['./atualizar-aulas.component.css']
})
export class AtualizarAulasComponent  implements OnInit{

  formulario : FormGroup;
  turma : Turma[] = [];
  sala : Sala[] = [];
  materias : Materia[] = [];
  dias : Dia[] = [];
  horas : Horas[] = [];
  quadro : QuadroDTO = new QuadroDTO();
  quadroHorario : QuadroHorario = new QuadroHorario();
  id : number;


  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id'];
    this.formularioVazio();
    this.defineQuadro(this.id);
  }
  
  constructor(
    private quadroHorarioService : QuadroHorariosService,
    private turmaService : TurmaService,
    private salaService : SalaService,
    private materiaService : MateriasService,
    private diaService : DiaService,
    private fb : FormBuilder,
    private horasService : HorasService,
    private activatedRoute : ActivatedRoute
  ){}

  defineQuadro(id : number) : QuadroHorario{
    this.quadroHorarioService.findById(id).subscribe({
      next : qua => {
        this.quadroHorario = qua;
        this.formulario = this.fb.group({
          turma : [qua.turma.id],
          sala :[qua.sala.id],
          materia : [qua.materia.id],
          dia : [qua.dia.id],
          hora : [qua.horas.id],
        })
      },
      error : err => console.log(err)
    })
    return this.quadroHorario
  }

  formularioVazio(){
    this.formulario = this.fb.group({
      turma : [null],
      sala :[null, [Validators.required]],
      materia : [null],
      dia : [null],
      hora : [null],
    })

    this.listarTurma();
    this.listarSalas();
    this.listarMaterias();
    this.listarDias();
    this.listarHoras();
  }

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

  atualizar(dia : number, sala : number){
    this.quadroHorarioService.findHorasLivres(dia, sala).subscribe({
      next : hor => this.horas = hor,
      error : err => console.log(err)
    })
  }

  resetaDia(){
    this.formulario.controls['dia'].setValue(null);
  }

  listarHoras(){
    this.horasService.findAll().subscribe({
      next : hora => {
        this.horas = hora;
      }, error : err => console.log(err)
    });
  }
  defineFormQuadro() : QuadroDTO{
    this.quadro.idQuadro = this.id;
    this.quadro.idTurma = this.formulario.value.turma
    this.quadro.idSala = this.formulario.value.sala
    this.quadro.idMateria = this.formulario.value.materia
    this.quadro.idDia = this.formulario.value.dia
    this.quadro.idHora = this.formulario.value.hora

    return this.quadro;
  }

  cadastrar(){   
    this.quadroHorarioService.updateQuadro(this.defineFormQuadro()).subscribe({
      next : quad => {
        console.log("Cadastrado com sucesso! " + quad);
      }, error : err => console.log(err)
    })
  }

}
