import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Pessoa } from 'src/app/models/pessoa';
import { AlunosService } from 'src/app/services/alunos.service';

@Component({
  selector: 'app-buscar-alunos',
  templateUrl: './buscar-alunos.component.html',
  styleUrls: ['./buscar-alunos.component.css']
})
export class BuscarAlunosComponent implements OnInit{

  pessoas : Pessoa[] = [];
  formulario : FormGroup;

  constructor(
    private alunoService : AlunosService,
    private fb : FormBuilder){ }
  ngOnInit(): void {  
    this.formulario = this.fb.group({
      valor : [null],
      filtro : [null]
    })  
  }

  buscar(){
    
    if (this.formulario.controls["valor"].value == 1) {
      this.alunoService.findAlunosByNome(this.formulario.controls["filtro"].value).subscribe({
        next: pessoa => {
          this.pessoas = pessoa;
        },
        error : err => console.log("Error", err)
      }) 
    }

    if (this.formulario.controls["valor"].value == 2) {
      this.alunoService.findAlunosByMatricula(this.formulario.controls["filtro"].value).subscribe({
        next: pessoa => {
          this.pessoas = this.removeTodos();
          this.pessoas.push(pessoa);
        },
        error : err => console.log("Error", err)
      }) 
    }

    if (this.formulario.controls["valor"].value == 3) {
      this.alunoService.findAllAlunos().subscribe({
        next: pessoa => {
          this.pessoas = pessoa;
        },
        error : err => console.log("Error", err)
      }) 
    }    
    
  }

  removeTodos() : Pessoa[]{
    this.pessoas = [];
      return this.pessoas;
  }

}
