import { IMortuary } from 'app/shared/model/mortuary.model';

export interface IFuneralPackages {
  id?: number;
  idFuneralPackages?: number;
  name?: string;
  description?: string;
  price?: number;
  state?: string;
  mortuary?: IMortuary;
}

export class FuneralPackages implements IFuneralPackages {
  constructor(
    public id?: number,
    public idFuneralPackages?: number,
    public name?: string,
    public description?: string,
    public price?: number,
    public state?: string,
    public mortuary?: IMortuary
  ) {}
}
