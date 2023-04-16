import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Pessoa } from 'src/app/models/pessoa';
import { MateriasService } from 'src/app/services/materias.service';
import { Materia } from 'src/app/models/materia';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PessoaService } from 'src/app/services/pessoa.service';
import { ResponseFiltroPessoaNome } from 'src/app/models/Response/responseFiltroPessoaNome';


@Component({
  selector: 'app-cadastrar-materias',
  templateUrl: './cadastrar-materias.component.html',
  styleUrls: ['./cadastrar-materias.component.css']
})
export class CadastrarMateriasComponent implements OnInit{
  formulario : FormGroup;
  private readonly cargo : string = "professor";
  submitted = false;
  pessoa : Pessoa[] = [];
  materia : Materia = new Materia();
  resposta : DefaultResponse;
  respostaPessoa : ResponseFiltroPessoaNome;

  ngOnInit(): void {
    this.formulario = this.fb.group({
      pessoa : [null],
      nome: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    });
    this.listarProfessores();
  }
  constructor(
    private professorService : PessoaService,
    private fb : FormBuilder,
    private materiaService: MateriasService,
    private _snackBar: MatSnackBar,
  ){}

  listarProfessores(){
    this.professorService.findAllAlunosSemPaginacao(this.cargo).subscribe({
      next : pessoa => {
        this.resposta = pessoa;

        if(this.resposta.success){
          this.pessoa =  this.resposta.data;
        }else {
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
        }        
      }
    });
  }
 
  defineMateria() : Materia {

    const newPessoa = new Pessoa();
    newPessoa.matricula = this.formulario.value.pessoa;
    this.materia.nome = this.formulario.value.nome;
    this.materia.professor = newPessoa;

    return this.materia;
  } 

  cadastrar(){
    this.materiaService.cadastrarMateria(this.defineMateria()).subscribe({
      next : async quad => {
        this.resposta = quad;
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000});
          await new Promise(f => setTimeout(f, 3000));
          window.location.reload()
      }
    })
  }

}
