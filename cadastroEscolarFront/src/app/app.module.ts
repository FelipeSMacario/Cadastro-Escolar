import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
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
import { BuscarMateriaComponent } from './escola/buscar-materia/buscar-materia.component';
import { AtualizarMateriaComponent } from './escola/atualizar-materia/atualizar-materia.component';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    HomeComponent,
    FooterComponent,
    HomeAlunosComponent,
    CadastroAlunosComponent,
    BuscarAlunosComponent,
    AtualizarAlunosComponent,
    HomeProfessoresComponent,
    CadastroProfessoresComponent,
    BuscarProfessoresComponent,
    AtualizarProfessoresComponent,
    HomeEscolaComponent,
    BuscarMateriaComponent,
    AtualizarMateriaComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers: [PessoaService],
  bootstrap: [AppComponent]
})
export class AppModule { }
