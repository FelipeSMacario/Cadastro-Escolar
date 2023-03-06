import { PessoaEntradaDTO } from "./pessoaEntradaDTO"

export class EntradaTurmaAlunoDTO{
    pessoas : PessoaEntradaDTO[];
    turmaId : number

    constructor(turma : number, pessoa : PessoaEntradaDTO[]){
        this.turmaId = turma;
        this.pessoas = pessoa;
    }
}