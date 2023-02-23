import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Materia } from 'src/app/models/materia';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
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
  resposta : DefaultResponse;

  constructor(
    private fb : FormBuilder,
    private materiaService : MateriasService,
    private activatedRoute : ActivatedRoute
    ){}

    ngOnInit(): void {   

      this.nome = this.activatedRoute.snapshot.params['nome'];
  
      this.materiaService.buscarMateriaPorNome(this.nome).subscribe({
        next: mat => {
          this.resposta = mat;
          if(this.resposta.success){
            this.materia = this.resposta.data;
          }else {
            console.log("Error", this.resposta.messagem);
        }}
      });
  
      this.formulario = this.fb.group({
        nomeAntigo : [null],
        nomeNovo : [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]]       
      });
  
    }

    salvarMateria(){
      this.materia.nome = this.formulario.controls["nomeNovo"].value;
        this.materiaService.atualizarMateria(this.materia).subscribe({
          next : mat => {
            this.resposta = mat;

            console.log(this.resposta.messagem)},
          error : err => console.log(err)
        })
    }
}
