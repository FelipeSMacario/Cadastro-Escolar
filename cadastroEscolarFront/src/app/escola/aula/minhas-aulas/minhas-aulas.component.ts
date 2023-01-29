import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { QuadroHorario } from 'src/app/models/quadroHorario';
import { QuadroHorariosService } from 'src/app/services/quadro-horarios.service';

@Component({
  selector: 'app-minhas-aulas',
  templateUrl: './minhas-aulas.component.html',
  styleUrls: ['./minhas-aulas.component.css']
})
export class MinhasAulasComponent implements OnInit{

  quadroHorario : QuadroHorario [] = [];
  quadroHorarioFiltro : QuadroHorario [] = [];
  formulario : FormGroup;

  ngOnInit(): void {
    this.formulario = this.fb.group({
      filtro : [0]
    });
    this.quadroHorarioService.findByMatricula(1).subscribe({
    next : quad => {
      this.quadroHorario = quad;
    }, error : err => console.log(err)
   })
  
  }

  constructor(
    private quadroHorarioService : QuadroHorariosService,
    private fb : FormBuilder,
  ){}

  filtrar(valor : number){
    this.quadroHorarioFiltro = this.quadroHorario;
    this.quadroHorarioFiltro = this.quadroHorario.filter(v => this.filtroDia(v.dia.id, valor.toString()));
  }

  filtroDia(dia : number, filtro : string){
    return dia === parseInt(filtro);
  }
   

}
