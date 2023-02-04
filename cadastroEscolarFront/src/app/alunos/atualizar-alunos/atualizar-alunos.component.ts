import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SafeResourceUrl, DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { take } from 'rxjs';
import { Pessoa } from 'src/app/models/pessoa';
import { PessoaService } from 'src/app/services/pessoa.service';

@Component({
  selector: 'app-atualizar-alunos',
  templateUrl: './atualizar-alunos.component.html',
  styleUrls: ['./atualizar-alunos.component.css']
})
export class AtualizarAlunosComponent implements OnInit{

  pessoa : Pessoa = new Pessoa();
  formulario : FormGroup;
  matricula : number;
  private readonly cargo : string = "alunos";
  urlFoto : SafeResourceUrl;
  imagem2 : any;

  constructor(
    private fb : FormBuilder,
    private alunoService : PessoaService,
    private activatedRoute : ActivatedRoute,
    private router: Router,
    private sanitizer : DomSanitizer,
    private _snackBar: MatSnackBar
    ){}
  
  ngOnInit(): void {    

    this.matricula = this.activatedRoute.snapshot.params['matricula'];

    this.alunoService.findAlunosByMatricula(this.cargo, this.matricula).subscribe({
      next: pessoa => {
       this.pessoa = pessoa;    
      },
      error : err => console.log("Error", err)
    });

   this.formularioVazio();
   this. buscarPessoa(this.matricula);

  }


  salvarAluno(){
    this.alunoService.updateAlunos(this.cargo, this.formulario.value).pipe(take(1)).subscribe({
      next : async user => {
        this._snackBar.open("Aluno atualizado com sucesso", "", {duration : 5000});
        await new Promise(f => setTimeout(f, 5000));
        this.router.navigate(['alunos/buscar'])
      },
      error : err => this._snackBar.open(err, "", {duration : 5000})
    })
  }

  carregaImagem(event : Event){
    let valor  = this.base(event);
  }

  base(event : Event) : any{
    const target = event.target as HTMLInputElement;
  
    const file : File = (target.files as FileList)[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
        this.imagem2 = reader.result;
        this.urlFoto = this.sanitizer.bypassSecurityTrustResourceUrl(reader.result as string)
        this.formulario.controls['urlFoto'].setValue( reader.result as string)
  
    };
    return reader.result;
  }

  formularioVazio(){
    this.formulario = this.fb.group({
      matricula : [null],
      cpf : [null],
      nome : [null],
      sobreNome : [null],
      dataNascimento : [null],
      dataCadastro : [null],
      cargo : [null],
      status : [null],
      urlFoto : [null]
    });
  }

  buscarPessoa(matricula : number){
    this.alunoService.findAlunosByMatricula(this.cargo, this.matricula).subscribe({
      next: pessoa => {
       this.pessoa = pessoa;   
       this.formulario = this.fb.group({
        matricula : [pessoa.matricula],
        cpf : [pessoa.cpf],
        nome : [pessoa.nome],
        sobreNome : [pessoa.sobreNome],
        dataNascimento : [pessoa.dataNascimento],
        dataCadastro : [pessoa.dataCadastro],
        cargo : [pessoa.cargo],
        status : [pessoa.status],
        urlFoto : [pessoa.urlFoto]
      });

      this.urlFoto = pessoa.urlFoto;

      },
      error : err => console.log("Error", err)
    });
  }


}
