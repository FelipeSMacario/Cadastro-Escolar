import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AtualizarAlunosComponent } from './alunos/atualizar-alunos/atualizar-alunos.component';
import { BuscarAlunosComponent } from './alunos/buscar-alunos/buscar-alunos.component';
import { CadastroAlunosComponent } from './alunos/cadastro-alunos/cadastro-alunos.component';
import { HomeAlunosComponent } from './alunos/home-alunos/home-alunos.component';
import { AtualizarMateriaComponent } from './escola/atualizar-materia/atualizar-materia.component';
import { BuscarMateriaComponent } from './escola/buscar-materia/buscar-materia.component';
import { HomeEscolaComponent } from './escola/home-escola/home-escola.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { AtualizarProfessoresComponent } from './professores/atualizar-professores/atualizar-professores.component';
import { BuscarProfessoresComponent } from './professores/buscar-professores/buscar-professores.component';
import { CadastroProfessoresComponent } from './professores/cadastro-professores/cadastro-professores.component';
import { HomeProfessoresComponent } from './professores/home-professores/home-professores.component';

const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "login", component: LoginComponent},
  {path: "alunos", component: HomeAlunosComponent},
  {path: "alunos/buscar", component: BuscarAlunosComponent},
  {path: "alunos/cadastrar", component: CadastroAlunosComponent},
  {path: "alunos/atualizar/:matricula", component: AtualizarAlunosComponent},
  {path: "professores", component: HomeProfessoresComponent},
  {path: "professores/buscar", component: BuscarProfessoresComponent},
  {path: "professores/cadastrar", component: CadastroProfessoresComponent},
  {path: "professores/atualizar/:matricula", component: AtualizarProfessoresComponent},
  {path: "escola", component: HomeEscolaComponent},
  {path: "escola/buscarMateria", component: BuscarMateriaComponent},
  {path: "materia/atualizar/:nome", component: AtualizarMateriaComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
