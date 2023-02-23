import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { take } from 'rxjs';
import { ModalConfirmacaoComponent } from 'src/app/modal/modal-confirmacao/modal-confirmacao.component';
import { Materia } from 'src/app/models/materia';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
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

  resposta : DefaultResponse;

  constructor(
    private fb : FormBuilder,
    private materiaService : MateriasService,
    private router: Router,
    private dialog : MatDialog,
    private _snackBar: MatSnackBar,
  ){}

  ngOnInit(): void {  
    this.formulario = this.fb.group({
      valor : [0],
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

  modalDeletar(id : number){
    const dialogRef = this.dialog.open(ModalConfirmacaoComponent, {
      data: "Tem certeza que deseja excluir a matéria?",
    });
  
    dialogRef.afterClosed().subscribe((result : boolean) => {
      if(result){
        this.deletarMateria(id);      
      }      
    });
  }

  deletarMateria(id : number){
    this.materiaService.deletarMateria(id).subscribe({
      next : mat =>  {
         this.resposta = mat;
         
         if(this.resposta.success){
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
          this.listarTodos()
         } else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
         }
      }
    })
  }
  editarMateria(nome : string){
    this.router.navigate(['materia/atualizar', nome])
  }
}
