import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ProfessorService } from 'src/app/services/professor.service';
import { Pessoa } from 'src/app/models/pessoa';
import { MateriasService } from 'src/app/services/materias.service';
import { Materia } from 'src/app/models/materia';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-cadastrar-materias',
  templateUrl: './cadastrar-materias.component.html',
  styleUrls: ['./cadastrar-materias.component.css']
})
export class CadastrarMateriasComponent implements OnInit{
  formulario : FormGroup;
  submitted = false;
  pessoa : Pessoa[] = [];
  materia : Materia = new Materia();
  resposta : DefaultResponse;

  ngOnInit(): void {
    this.formulario = this.fb.group({
      pessoa : [null],
      nome: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    });
    this.listarProfessores();
  }
  constructor(
    private professorService : ProfessorService,
    private fb : FormBuilder,
    private materiaService: MateriasService,
    private _snackBar: MatSnackBar,
  ){}

  listarProfessores(){
    this.professorService.findAll().subscribe({
      next : pessoa => {
        this.pessoa = pessoa;
      }, error : err => console.log(err)
    });
  }
 
  defineMateria() : Materia {

    const newPessoa = new Pessoa();
    newPessoa.matricula = 458;
    this.materia.nome = this.formulario.value.nome;
    this.materia.professor = newPessoa;

    return this.materia;
  } 

  cadastrar(){
    this.materiaService.cadastrarMateria(this.defineMateria()).subscribe({
      next : quad => {
        this.resposta = quad;
          this._snackBar.open(this.resposta.messagem, "", {duration : 3000})
      }
    })
  }

  onSubmit(){
    this.submitted = true;
    if(this.formulario.invalid){
      alert("O nome deve ser preenchido!");
      return
    } else {
      this.cadastrar();
    }
    alert("Cadastro realizado com sucesso!");
  }
}
