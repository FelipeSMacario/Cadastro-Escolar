import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Pessoa } from 'src/app/models/pessoa';
import { PessoaService } from 'src/app/services/pessoa.service';

@Component({
  selector: 'app-buscar-alunos',
  templateUrl: './buscar-alunos.component.html',
  styleUrls: ['./buscar-alunos.component.css']
})
export class BuscarAlunosComponent implements OnInit{

  pessoas : Pessoa[] = [];
  
  formulario : FormGroup;

  private readonly cargo : string = "alunos";

  constructor(
    private alunoService : PessoaService,
    private fb : FormBuilder,
    private router: Router){ }

  ngOnInit(): void {  
    this.formulario = this.fb.group({
      valor : [null],
      filtro : [null]
    })  
  }

  buscar(){
    
    if (this.formulario.controls["valor"].value == 1) {
      this.alunoService.findAlunosByNome(this.cargo, this.formulario.controls["filtro"].value).subscribe({
        next: pessoa => {
          this.pessoas = this.removeTodos();
          this.pessoas = pessoa;
        },
        error : err => console.log("Error", err)
      }) 
    }

    if (this.formulario.controls["valor"].value == 2) {
      this.alunoService.findAlunosByMatricula(this.cargo, this.formulario.controls["filtro"].value).subscribe({
        next: pessoa => {
          this.pessoas = this.removeTodos();
          this.pessoas.push(pessoa);
        },
        error : err => console.log("Error", err)
      }) 
    }

    if (this.formulario.controls["valor"].value == 3) {
      this.alunoService.findAllAlunos(this.cargo).subscribe({
        next: pessoa => {
          this.pessoas = this.removeTodos();
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

  atualizar(matricula : number){
    this.router.navigate(['alunos/atualizar', matricula])
  }

}
