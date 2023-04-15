import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Page } from 'src/app/models/page';
import { Pessoa } from 'src/app/models/pessoa';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { ResponseFiltroPessoaNome } from 'src/app/models/Response/responseFiltroPessoaNome';
import { PessoaService } from 'src/app/services/pessoa.service';

@Component({
  selector: 'app-buscar-professores',
  templateUrl: './buscar-professores.component.html',
  styleUrls: ['./buscar-professores.component.css']
})
export class BuscarProfessoresComponent implements OnInit {

  private readonly cargo : string = "professor";

  pessoas : Pessoa[];
  resposta : DefaultResponse;
  formulario : FormGroup;
  pessoaResposta : ResponseFiltroPessoaNome;
  pagina : Page;

  constructor(
    private professorService : PessoaService,
    private fb : FormBuilder,
    private router: Router,
    private _snackBar: MatSnackBar){ }

    ngOnInit(): void {  
      this.formulario = this.fb.group({
        valor : [0, [Validators.required, Validators.min(1), Validators.max(3)]],
        filtro : [null, [Validators.required]]
      })  
    }

    buscar(){
    
      if (this.formulario.controls["valor"].value == 1) {
        this.professorService.findAlunosByNome(this.cargo, this.formulario.controls["filtro"].value).subscribe({
          next: pessoa => {
            this.resposta = pessoa;
            console.log(pessoa)

            if(this.resposta.success){
              this.pessoas = this.removeTodos();
              this.pagina = this.resposta.data;
              this.pessoas = this.pagina.content;
            } else {
              this._snackBar.open(this.resposta.messagem, "", {duration : 5000});
            }
          }
        }) 
      }
  
      if (this.formulario.controls["valor"].value == 2) {
        this.professorService.findAlunosByMatricula(this.cargo, this.formulario.controls["filtro"].value).subscribe({
          next: pessoa => {
            this.resposta = pessoa;

            if(this.resposta.success){
              this.pessoas = this.removeTodos();
              this.pessoas.push(this.resposta.data);
            }else {
              this._snackBar.open(this.resposta.messagem, "", {duration : 5000});
            }
           
          }
        }) 
      }
  
      if (this.formulario.controls["valor"].value == 3) {
        console.log(this.formulario.value.filtro)
        
         this.listarTodos();
      }          
    }

    listarTodos( pagina? : number){
      this.professorService.findAllAlunos(this.cargo, pagina).subscribe({
        next: pessoa => {
          this.resposta = pessoa;
        

          if(this.resposta.success){
            this.pessoas = this.removeTodos();
            this.pagina = this.resposta.data;           
            this.pessoas = this.pagina.content;
          } else {
            this._snackBar.open(this.resposta.messagem, "", {duration : 5000});
          }
        }
      })
    }

    mudanca(){
        if(this.formulario.value.valor == 3){
          this.formulario.controls['filtro'].setValue("Filtro");
        } else {
          this.formulario.controls['filtro'].setValue(null);
        }
    }

    removeTodos() : Pessoa[]{
      this.pessoas = [];
        return this.pessoas;
    }
  
    atualizar(matricula : number){
      this.router.navigate(['professores/atualizar', matricula])
    }

    proximo(){
      this.listarTodos(this.pagina.number + 1);
    }

    anterior(){
      this.listarTodos(this.pagina.number - 1);
    }

    definePagina(pagina : number){
      this.listarTodos(pagina - 1);

    }

}
