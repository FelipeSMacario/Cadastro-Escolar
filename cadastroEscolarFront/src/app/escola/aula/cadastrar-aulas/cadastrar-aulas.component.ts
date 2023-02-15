import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
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
  quadro : QuadroDTO = new QuadroDTO();
  quadroHorario : QuadroHorario = new QuadroHorario();

  ngOnInit(): void {
    this.formularioVazio();
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
    private _snackBar: MatSnackBar
  ){}

  formularioVazio(){
    this.formulario = this.fb.group({
      turma : [null, [Validators.required]],
      sala :[null, [Validators.required]],
      materia : [null, [Validators.required]],
      dia : [null, [Validators.required]],
      hora : [null, [Validators.required]],
    });
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

  cadastrar(){   
    this.quadroHorarioService.saveHorasLivres(this.defineQuadro()).subscribe({
      next : quad => {
        this._snackBar.open("Aula cadastrada com sucesso", "", {duration : 5000});
        this.formularioVazio();
      }, 
      error : err => this._snackBar.open(err, "", {duration : 5000})
    })
  }

  defineQuadro() : QuadroDTO{
    this.quadro.idTurma = this.formulario.value.turma
    this.quadro.idSala = this.formulario.value.sala
    this.quadro.idMateria = this.formulario.value.materia
    this.quadro.idDia = this.formulario.value.dia
    this.quadro.idHora = this.formulario.value.hora

    return this.quadro;
  }

}
