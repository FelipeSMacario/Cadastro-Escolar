import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { take } from 'rxjs';
import { Materia } from 'src/app/models/materia';
import { MateriasService } from 'src/app/services/materias.service';

@Component({
  selector: 'app-buscar-materia',
  templateUrl: './buscar-materia.component.html',
  styleUrls: ['./buscar-materia.component.css']
})
export class BuscarMateriaComponent implements OnInit{

  formulario : FormGroup;

  materias : Materia[] = [];
  
  materia : Materia = new Materia();

  constructor(
    private fb : FormBuilder,
    private materiaService : MateriasService,
    private router: Router
  ){}

  ngOnInit(): void {  
    this.formulario = this.fb.group({
      valor : [null],
      filtro : [null]
    })  
  }

  buscar(){
    if (this.formulario.controls["valor"].value == 1){
      this.materiaService.buscarMateriaPorNome(this.formulario.controls["filtro"].value).subscribe({
        next : mat => {
          this.materias = this.removerTodos();
          this.materias.push(mat);
        },
        error : err => console.log("Error", err)
      })
    } 
    if (this.formulario.controls["valor"].value == 2){
      this.listarTodos();
    }
    if (this.formulario.controls["valor"].value == 3){
      this.materia.nome = this.formulario.controls["filtro"].value;

     /* this.materiaService.cadastrarMateria(this.materia).pipe(take(1)).subscribe({
        next : user => {
          console.log("Cadastrado com sucesso", user);
          this.listarTodos();
        },
        error : err => console.log(err)
      }) */
    }
  }

  removerTodos() : Materia[] {
    this.materias = [];
    return  this.materias;
  }

  listarTodos(){
    this.materiaService.listarMateria().subscribe({
      next : mat => {
        this.materias = this.removerTodos();
        this.materias = mat;
      },
      error : err => console.log("Error", err)
    })
  }

  deletarMateria(id : number){
    this.materiaService.deletarMateria(id).subscribe({
      next : mat =>  {
        console.log("Deletado com sucesso")
        this.listarTodos()
      },
      error : err => console.log(err)
    })
  }
  editarMateria(nome : string){
    this.router.navigate(['materia/atualizar', nome])
  }
}
