import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { take } from 'rxjs';
import { Pessoa } from 'src/app/models/pessoa';
import { PessoaService } from 'src/app/services/pessoa.service';

@Component({
  selector: 'app-atualizar-professores',
  templateUrl: './atualizar-professores.component.html',
  styleUrls: ['./atualizar-professores.component.css']
})
export class AtualizarProfessoresComponent  implements OnInit{

  pessoa : Pessoa = new Pessoa();
  formulario : FormGroup;
  matricula : number;
  private readonly cargo : string = "professor";
  urlFoto : SafeResourceUrl;
  imagem2 : any;

  constructor(
    private fb : FormBuilder,
    private alunoService : PessoaService,
    private activatedRoute : ActivatedRoute,
    private sanitizer : DomSanitizer
    ){}

    ngOnInit(): void {    

      this.matricula = this.activatedRoute.snapshot.params['matricula'];
      this.formularioVazio();
      this. buscarPessoa(this.matricula);
    }

    salvarAluno(){
      this.alunoService.updateAlunos(this.cargo, this.formulario.value).pipe(take(1)).subscribe({
        next : user => console.log("Cadastrado com sucesso", user),
        error : err => console.log(err)
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
