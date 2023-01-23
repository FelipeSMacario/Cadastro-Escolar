import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Materia } from 'src/app/models/materia';
import { MateriasService } from 'src/app/services/materias.service';

@Component({
  selector: 'app-atualizar-materia',
  templateUrl: './atualizar-materia.component.html',
  styleUrls: ['./atualizar-materia.component.css']
})
export class AtualizarMateriaComponent implements OnInit{

  nome : string;
  formulario : FormGroup;
  materia : Materia = new Materia();

  constructor(
    private fb : FormBuilder,
    private materiaService : MateriasService,
    private activatedRoute : ActivatedRoute
    ){}

    ngOnInit(): void {   

      this.nome = this.activatedRoute.snapshot.params['nome'];
  
      this.materiaService.buscarMateriaPorNome(this.nome).subscribe({
        next: mat => {
         this.materia = mat;    
        },
        error : err => console.log("Error", err)
      });
  
      this.formulario = this.fb.group({
        nomeAntigo : [null],
        nomeNovo : [null]       
      });
  
    }

    salvarMateria(){
      this.materia.nome = this.formulario.controls["nomeNovo"].value;
        this.materiaService.atualizarMateria(this.materia).subscribe({
          next : mat => console.log("Matéria atualizada"),
          error : err => console.log(err)
        })
    }
}
