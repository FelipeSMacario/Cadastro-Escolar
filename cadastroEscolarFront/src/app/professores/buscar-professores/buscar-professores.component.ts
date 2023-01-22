import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Pessoa } from 'src/app/models/pessoa';
import { PessoaService } from 'src/app/services/pessoa.service';

@Component({
  selector: 'app-buscar-professores',
  templateUrl: './buscar-professores.component.html',
  styleUrls: ['./buscar-professores.component.css']
})
export class BuscarProfessoresComponent implements OnInit {

  private readonly cargo : string = "professor";

  pessoas : Pessoa[] = [];
  
  formulario : FormGroup;

  constructor(
    private professorService : PessoaService,
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
        this.pessoas = this.removeTodos();
        this.professorService.findAlunosByNome(this.cargo, this.formulario.controls["filtro"].value).subscribe({
          next: pessoa => {
            this.pessoas = pessoa;
          },
          error : err => console.log("Error", err)
        }) 
      }
  
      if (this.formulario.controls["valor"].value == 2) {
        this.pessoas = this.removeTodos();
        this.professorService.findAlunosByMatricula(this.cargo, this.formulario.controls["filtro"].value).subscribe({
          next: pessoa => {
            this.pessoas = this.removeTodos();
            this.pessoas.push(pessoa);
          },
          error : err => console.log("Error", err)
        }) 
      }
  
      if (this.formulario.controls["valor"].value == 3) {
        this.professorService.findAllAlunos(this.cargo).subscribe({
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
  
    atualizar(matricula : number){
      this.router.navigate(['professores/atualizar', matricula])
    }


}
