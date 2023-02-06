import { Pessoa } from "../pessoa";
import { Turma } from "../turma";

export class SaidaTurmaAlunoDTO {
   turma : Turma;
   alunos : Pessoa[];
}