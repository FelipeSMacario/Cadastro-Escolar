import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AtualizarAlunosComponent } from './alunos/atualizar-alunos/atualizar-alunos.component';
import { BuscarAlunosComponent } from './alunos/buscar-alunos/buscar-alunos.component';
import { CadastroAlunosComponent } from './alunos/cadastro-alunos/cadastro-alunos.component';
import { HomeAlunosComponent } from './alunos/home-alunos/home-alunos.component';
import { AtualizarAulasComponent } from './escola/aula/atualizar-aulas/atualizar-aulas.component';
import { AulaHomeComponent } from './escola/aula/aula-home/aula-home.component';
import { BuscarAulasComponent } from './escola/aula/buscar-aulas/buscar-aulas.component';
import { CadastrarAulasComponent } from './escola/aula/cadastrar-aulas/cadastrar-aulas.component';
import { CadastrarMateriasComponent } from './escola/materia/cadastrar-materia/cadastrar-materias.component';
import { MateriaHomeComponent } from './escola/materia/materia-home/materia-home.component';
import { MinhasAulasComponent } from './escola/aula/minhas-aulas/minhas-aulas.component';
import { HomeEscolaComponent } from './escola/home-escola/home-escola.component';
import { AtualizarMateriaComponent } from './escola/materia/atualizar-materia/atualizar-materia.component';
import { BuscarMateriaComponent } from './escola/materia/buscar-materia/buscar-materia.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { AtualizarProfessoresComponent } from './professores/atualizar-professores/atualizar-professores.component';
import { BuscarProfessoresComponent } from './professores/buscar-professores/buscar-professores.component';
import { CadastroProfessoresComponent } from './professores/cadastro-professores/cadastro-professores.component';
import { HomeProfessoresComponent } from './professores/home-professores/home-professores.component';
import { CadastrarNotasComponent } from './escola/notas/cadastrar-notas/cadastrar-notas.component';
import { HomeNotasComponent } from './escola/notas/home-notas/home-notas.component';
import { BuscarNotasComponent } from './escola/notas/buscar-notas/buscar-notas.component';
import { AtualizarNotasComponent } from './escola/notas/atualizar-notas/atualizar-notas.component';
import { MinhasNotasComponent } from './escola/notas/minhas-notas/minhas-notas.component';

const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "login", component: LoginComponent},
  {path: "alunos", component: HomeAlunosComponent},
  {path: "alunos/buscar", component: BuscarAlunosComponent},
  {path: "alunos/cadastrar", component: CadastroAlunosComponent},
  {path: "alunos/atualizar/:matricula", component: AtualizarAlunosComponent},
  {path: "alunos/meusDados", component: AtualizarAlunosComponent},
  {path: "professores", component: HomeProfessoresComponent},
  {path: "professores/buscar", component: BuscarProfessoresComponent},
  {path: "professores/cadastrar", component: CadastroProfessoresComponent},
  {path: "professores/meusDados", component: AtualizarProfessoresComponent},
  {path: "professores/atualizar/:matricula", component: AtualizarProfessoresComponent},
  {path: "escola", component: HomeEscolaComponent},
  {path: "escola/materia/buscarMateria", component: BuscarMateriaComponent},
  {path: "escola/materia/cadastrarMateria", component: CadastrarMateriasComponent},
  {path: "materia/atualizar/:nome", component: AtualizarMateriaComponent},
  {path: "escola/aulas", component: AulaHomeComponent},
  {path: "escola/materia", component: MateriaHomeComponent},
  {path: "escola/aulas/minhasAulas", component: MinhasAulasComponent},
  {path: "escola/aulas/buscar", component: BuscarAulasComponent},
  {path: "aula/atualizar/:id", component: AtualizarAulasComponent},
  {path: "escola/aulas/cadastrar", component: CadastrarAulasComponent},
  {path: "escola/notas", component: HomeNotasComponent},
  {path: "escola/notas/cadastrar", component: CadastrarNotasComponent},
  {path: "escola/notas/buscar", component: BuscarNotasComponent},
  {path: "notas/atualizar/:id", component: AtualizarNotasComponent},
  {path: "escola/notas/minhasAulas", component: MinhasNotasComponent}
  
 
 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
