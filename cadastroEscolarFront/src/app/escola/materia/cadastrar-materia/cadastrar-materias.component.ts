import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ProfessorService } from 'src/app/services/professor.service';
import { Pessoa } from 'src/app/models/pessoa';
import { MateriasService } from 'src/app/services/materias.service';
import { Materia } from 'src/app/models/materia';


@Component({
  selector: 'app-cadastrar-materias',
  templateUrl: './cadastrar-materias.component.html',
  styleUrls: ['./cadastrar-materias.component.css']
})
export class CadastrarMateriasComponent implements OnInit{
  formulario : FormGroup;
  pessoa : Pessoa[] = [];
  materia : Materia = new Materia();

  ngOnInit(): void {
    this.formulario = this.fb.group({
      pessoa : [null],
      nome: [null],
    });
    this.listarProfessores();
  }

  
  constructor(
    private professorService : ProfessorService,
    private fb : FormBuilder,
    private materiaService: MateriasService,
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
    newPessoa.matricula = this.formulario.value.pessoa;
    this.materia.nome = this.formulario.value.nome;
    this.materia.professor = newPessoa;

    return this.materia;
  } 

  cadastrar(){

    this.materiaService.cadastrarMateria(this.defineMateria()).subscribe({
      next : quad => {
        console.log("Cadastrado com sucesso! " + quad);
      }, error : err => console.log(err)
    })
  }
  
}
