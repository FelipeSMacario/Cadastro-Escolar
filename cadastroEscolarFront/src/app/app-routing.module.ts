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
import { AuthGuard } from './guards/auth.guard';
import { PageNotFoundComponent } from './shared/page-not-found/page-not-found.component';
import { AlunoGuard } from './guards/aluno.guard';
import { ProfessorGuard } from './guards/professor.guard';
import { HomeTurmaComponent } from './escola/turma/home-turma/home-turma.component';
import { CadastrarTurmaComponent } from './escola/turma/cadastrar-turma/cadastrar-turma.component';
import { FiltrarTurmaComponent } from './escola/turma/filtrar-turma/filtrar-turma.component';

const routes: Routes = [
  {path: '',  redirectTo: 'home',  pathMatch: 'full'},  
  {path: "home", component: HomeComponent, canActivate: [AuthGuard]},
  {path: "login", component: LoginComponent},
  {path: "alunos", component: HomeAlunosComponent, canActivate: [AuthGuard, ProfessorGuard]},
  {path: "alunos/buscar", component: BuscarAlunosComponent, canActivate: [AuthGuard, AlunoGuard, ProfessorGuard]},
  {path: "alunos/cadastrar", component: CadastroAlunosComponent, canActivate: [AuthGuard, AlunoGuard, ProfessorGuard]},
  {path: "alunos/atualizar/:matricula", component: AtualizarAlunosComponent, canActivate: [AuthGuard, AlunoGuard, ProfessorGuard]},
  {path: "alunos/meusDados", component: AtualizarAlunosComponent, canActivate: [AuthGuard, ProfessorGuard]},
  {path: "professores", component: HomeProfessoresComponent, canActivate: [AuthGuard, AlunoGuard]},
  {path: "professores/buscar", component: BuscarProfessoresComponent, canActivate: [AuthGuard, AlunoGuard, ProfessorGuard]},
  {path: "professores/cadastrar", component: CadastroProfessoresComponent, canActivate: [AuthGuard, AlunoGuard, ProfessorGuard]},
  {path: "professores/meusDados", component: AtualizarProfessoresComponent, canActivate: [AuthGuard, AlunoGuard]},
  {path: "professores/atualizar/:matricula", component: AtualizarProfessoresComponent, canActivate: [AuthGuard, AlunoGuard, ProfessorGuard]},
  {path: "escola", component: HomeEscolaComponent, canActivate: [AuthGuard]},
  {path: "escola/materia/buscarMateria", component: BuscarMateriaComponent, canActivate: [AuthGuard, AlunoGuard, ProfessorGuard]},
  {path: "escola/materia/cadastrarMateria", component: CadastrarMateriasComponent, canActivate: [AuthGuard, AlunoGuard, ProfessorGuard]},
  {path: "materia/atualizar/:nome", component: AtualizarMateriaComponent, canActivate: [AuthGuard, AlunoGuard, ProfessorGuard]},
  {path: "escola/aulas", component: AulaHomeComponent, canActivate: [AuthGuard]},
  {path: "escola/materia", component: MateriaHomeComponent, canActivate: [AuthGuard, AlunoGuard, ProfessorGuard]},
  {path: "escola/aulas/minhasAulas", component: MinhasAulasComponent, canActivate: [AuthGuard]},
  {path: "escola/aulas/buscar", component: BuscarAulasComponent, canActivate: [AuthGuard, AlunoGuard]},
  {path: "aula/atualizar/:id", component: AtualizarAulasComponent, canActivate: [AuthGuard, AlunoGuard, ProfessorGuard]},
  {path: "escola/aulas/cadastrar", component: CadastrarAulasComponent, canActivate: [AuthGuard, AlunoGuard, ProfessorGuard]},
  {path: "escola/notas", component: HomeNotasComponent, canActivate: [AuthGuard]},
  {path: "escola/notas/cadastrar", component: CadastrarNotasComponent, canActivate: [AuthGuard, AlunoGuard]},
  {path: "escola/notas/buscar", component: BuscarNotasComponent, canActivate: [AuthGuard, AlunoGuard]},
  {path: "notas/atualizar/:id", component: AtualizarNotasComponent, canActivate: [AuthGuard, AlunoGuard]},
  {path: "escola/notas/minhasNotas", component: MinhasNotasComponent, canActivate: [AuthGuard]},
  {path: "escola/turmas", component: HomeTurmaComponent},
  {path: "escola/turmas/cadastrar", component: CadastrarTurmaComponent},
  {path: "escola/turmas/buscar", component: FiltrarTurmaComponent},
  {path: '**',  component: PageNotFoundComponent}
  
 
 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
