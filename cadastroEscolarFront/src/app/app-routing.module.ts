import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AtualizarAlunosComponent } from './alunos/atualizar-alunos/atualizar-alunos.component';
import { BuscarAlunosComponent } from './alunos/buscar-alunos/buscar-alunos.component';
import { CadastroAlunosComponent } from './alunos/cadastro-alunos/cadastro-alunos.component';
import { HomeAlunosComponent } from './alunos/home-alunos/home-alunos.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "alunos", component: HomeAlunosComponent},
  {path: "alunos/buscar", component: BuscarAlunosComponent},
  {path: "alunos/cadastrar", component: CadastroAlunosComponent},
  {path: "alunos/atualizar/:matricula", component: AtualizarAlunosComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
