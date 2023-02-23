import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { take } from 'rxjs';
import { Pessoa } from 'src/app/models/pessoa';
import { DefaultResponse } from 'src/app/models/Response/defaultResponse';
import { PessoaService } from 'src/app/services/pessoa.service';

@Component({
  selector: 'app-atualizar-professores',
  templateUrl: './atualizar-professores.component.html',
  styleUrls: ['./atualizar-professores.component.css']
})
export class AtualizarProfessoresComponent  implements OnInit{

  pessoa : Pessoa = new Pessoa();
  pessoaLogada : Pessoa;
  formulario : FormGroup;
  matricula : number;
  private readonly cargo : string = "professor";
  urlFoto : SafeResourceUrl;
  imagem2 : any;
  titulo : string;
  resposta : DefaultResponse;

  constructor(
    private fb : FormBuilder,
    private professorService : PessoaService,
    private activatedRoute : ActivatedRoute,
    private router: Router,
    private sanitizer : DomSanitizer,
    private _snackBar: MatSnackBar
    ){}

    ngOnInit(): void {    

      this.formularioVazio();
      this.matricula = this.activatedRoute.snapshot.params['matricula'];

      if(this.matricula){
        this.titulo = "Atualizar professores";
        this.professorService.findAlunosByMatricula(this.cargo, this.matricula).subscribe({
          next : alu => {
            this.resposta = alu;

            if(this.resposta.success){
              this.formularioPreenchido(this.resposta.data);
            } else {
              console.log(this.resposta.messagem);
            }
            }
        })
      } else {
        this.titulo = "Meus dados";
        this.pessoa = JSON.parse(localStorage.getItem("pessoa")!);
        this.formularioPreenchido(this.pessoa);   
      }
    }

    salvarAluno(){
      this.professorService.updateAlunos(this.cargo, this.formulario.value).pipe(take(1)).subscribe({
        next : async user => {
          this._snackBar.open("Professor atualizado com sucesso", "", {duration : 5000});
          await new Promise(f => setTimeout(f, 5000));
          this.router.navigate(['professores/buscar'])
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
        urlFoto : [null],
        email : [null],
        ano : [0]
      });
    }

  

    formularioPreenchido(pessoa : Pessoa){
      
      this.formulario = this.fb.group({
        matricula : [pessoa.matricula],
        cpf : [pessoa.cpf, [Validators.required, Validators.minLength(11), Validators.maxLength(11)]],
        nome : [pessoa.nome, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
        sobreNome : [pessoa.sobreNome, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
        dataNascimento : [pessoa.dataNascimento, [Validators.required]],
        dataCadastro : [pessoa.dataCadastro, [Validators.required]],
        cargo : [pessoa.cargo, [Validators.required]],
        status : [pessoa.status, [Validators.required]],
        urlFoto : [pessoa.urlFoto],
        email : [pessoa.email, [Validators.required, Validators.email, Validators.maxLength(100)]],
        ano : [0]
      });
      this.urlFoto = pessoa.urlFoto;
    }

    isReadOnly() : boolean{
      this.pessoaLogada = JSON.parse(localStorage.getItem("pessoa")!);
     return this.pessoaLogada.cargo === "Professor" ? true : false;
    }

}
