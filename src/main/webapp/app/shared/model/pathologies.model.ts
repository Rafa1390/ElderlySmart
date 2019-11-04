import { ICaseFile } from 'app/shared/model/case-file.model';

export interface IPathologies {
  id?: number;
  idPathologies?: number;
  name?: string;
  description?: string;
  caseFile?: ICaseFile;
}

export class Pathologies implements IPathologies {
  constructor(
    public id?: number,
    public idPathologies?: number,
    public name?: string,
    public description?: string,
    public caseFile?: ICaseFile
  ) {}
}
