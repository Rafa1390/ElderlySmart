import { ICaseFile } from 'app/shared/model/case-file.model';

export interface IAllergies {
  id?: number;
  idAllergies?: number;
  name?: string;
  description?: string;
  caseFile?: ICaseFile;
}

export class Allergies implements IAllergies {
  constructor(
    public id?: number,
    public idAllergies?: number,
    public name?: string,
    public description?: string,
    public caseFile?: ICaseFile
  ) {}
}
