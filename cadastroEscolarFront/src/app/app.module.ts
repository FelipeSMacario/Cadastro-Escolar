import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import { FooterComponent } from './footer/footer.component';
import { HomeAlunosComponent } from './alunos/home-alunos/home-alunos.component';
import { CadastroAlunosComponent } from './alunos/cadastro-alunos/cadastro-alunos.component';
import { BuscarAlunosComponent } from './alunos/buscar-alunos/buscar-alunos.component';
import { PessoaService } from './services/pessoa.service';
import { AtualizarAlunosComponent } from './alunos/atualizar-alunos/atualizar-alunos.component';
import { HomeProfessoresComponent } from './professores/home-professores/home-professores.component';
import { CadastroProfessoresComponent } from './professores/cadastro-professores/cadastro-professores.component';
import { BuscarProfessoresComponent } from './professores/buscar-professores/buscar-professores.component';
import { AtualizarProfessoresComponent } from './professores/atualizar-professores/atualizar-professores.component';
import { HomeEscolaComponent } from './escola/home-escola/home-escola.component';
import { LoginComponent } from './login/login.component';
import { AtualizarMateriaComponent } from './escola/materia/atualizar-materia/atualizar-materia.component';
import { BuscarMateriaComponent } from './escola/materia/buscar-materia/buscar-materia.component';
import { AulaHomeComponent } from './escola/aula/aula-home/aula-home.component';
import { MinhasAulasComponent } from './escola/aula/minhas-aulas/minhas-aulas.component';
import { BuscarAulasComponent } from './escola/aula/buscar-aulas/buscar-aulas.component';
import { AtualizarAulasComponent } from './escola/aula/atualizar-aulas/atualizar-aulas.component';
import { CadastrarAulasComponent } from './escola/aula/cadastrar-aulas/cadastrar-aulas.component';
import { MateriaHomeComponent } from './escola/aula/materia-home/materia-home.component';
import { CadastrarMateriasComponent } from './escola/materia/cadastrar-materia/cadastrar-materias.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { ModalConfirmacaoComponent } from './modal/modal-confirmacao/modal-confirmacao.component';
import { ModalInformacaoComponent } from './modal/modal-informacao/modal-informacao.component';
import { HomeNotasComponent } from './escola/notas/home-notas/home-notas.component';
import { CadastrarNotasComponent } from './escola/notas/cadastrar-notas/cadastrar-notas.component';
import { BuscarNotasComponent } from './escola/notas/buscar-notas/buscar-notas.component';
import {MatIconModule} from '@angular/material/icon';
import { AtualizarNotasComponent } from './escola/notas/atualizar-notas/atualizar-notas.component';
import {MatMenuModule} from '@angular/material/menu';
import { TokenInterceptorService } from './services/token-interceptor.service';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    HomeComponent,
    FooterComponent,
    HomeAlunosComponent,
    MateriaHomeComponent,
    CadastroAlunosComponent,
    CadastrarMateriasComponent,
    BuscarAlunosComponent,
    AtualizarAlunosComponent,
    HomeProfessoresComponent,
    CadastroProfessoresComponent,
    BuscarProfessoresComponent,
    AtualizarProfessoresComponent,
    HomeEscolaComponent,
    BuscarMateriaComponent,

    AtualizarMateriaComponent,
    LoginComponent,
    AulaHomeComponent,
    MinhasAulasComponent,
    BuscarAulasComponent,
    AtualizarAulasComponent,
    CadastrarAulasComponent,
    ModalConfirmacaoComponent,
    ModalInformacaoComponent,
    HomeNotasComponent,
    CadastrarNotasComponent,
    BuscarNotasComponent,
    AtualizarNotasComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatDialogModule,
    MatSnackBarModule,
    MatIconModule,
    MatMenuModule
  ],
  providers: [PessoaService, ModalInformacaoComponent,  {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptorService,
    multi: true,
  },],
  bootstrap: [AppComponent]
})
export class AppModule { }
