import { Moment } from 'moment';
import { IElderly } from 'app/shared/model/elderly.model';
import { IAllergies } from 'app/shared/model/allergies.model';
import { IPathologies } from 'app/shared/model/pathologies.model';

export interface ICaseFile {
  id?: number;
  idCaseFile?: number;
  creationDate?: Moment;
  fullNameElderly?: string;
  age?: number;
  religion?: string;
  nationality?: string;
  weight?: string;
  height?: string;
  birth?: Moment;
  gender?: string;
  bloodType?: string;
  resuscitation?: string;
  organDonation?: string;
  state?: string;
  elderly?: IElderly;
  alergies?: IAllergies[];
  pathologies?: IPathologies[];
}

export class CaseFile implements ICaseFile {
  constructor(
    public id?: number,
    public idCaseFile?: number,
    public creationDate?: Moment,
    public fullNameElderly?: string,
    public age?: number,
    public religion?: string,
    public nationality?: string,
    public weight?: string,
    public height?: string,
    public birth?: Moment,
    public gender?: string,
    public bloodType?: string,
    public resuscitation?: string,
    public organDonation?: string,
    public state?: string,
    public elderly?: IElderly,
    public alergies?: IAllergies[],
    public pathologies?: IPathologies[]
  ) {}
}
